#include <sys/socket.h>
#include <map>
#include <iostream>
#include <cstdio>
#include <string>
#include <unistd.h>
#include <cstdio>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <mutex>
#include <thread>
#include <cstring>
#include "./Worker.cpp"
#include "./File.cpp"
#include "./FileChecker.cpp"

#include <vector>
#include <fstream>
#include <sstream>
#include <filesystem>

using namespace std;

/**
 * The class containing the server socket.
 */
class ServerSocket{

public:
    /**
     *
     * @param PORT [int]: The port on the machine that run the server
     * @param ip_address [string]: The local IP address of the machine the server is running on.
     */
    ServerSocket(int PORT, const string& ip_address){

        mutex connect_lock;
        Worker requestEvents(1);
        Worker generalWorkers(10);

        //We define the server socket to run on TCP
        int serverFd = socket(AF_INET, SOCK_STREAM, 0); // File descriptor, -1 on error
        if(serverFd == -1) cerr << "Socket creation failed" << endl;
        bool runThreads = true;

        //Datastructure for storing address
        struct sockaddr_in server;

        int serverLength = sizeof(server);

        //Initializes the address datastructure with appropriate addresses.
        server.sin_family = AF_INET;
        server.sin_addr.s_addr = INADDR_ANY;
        server.sin_port = htons(PORT);
        inet_pton(AF_INET, ip_address.c_str(), &server.sin_addr);


        // reuse SOCKET AND ADDRESS/PORT
        int opt = 0;
        if(setsockopt(serverFd, SOL_SOCKET, SO_REUSEADDR , &opt, sizeof(opt))) perror("setsockopt failed");

        //Bind socket to port/ip
        bind(serverFd, (sockaddr*)&server, sizeof(server));


        //Listen for incoming connections
        int listenStatus = listen(serverFd, SOMAXCONN);  //SOMAXCONN max queue length acc. to os.
        if(listenStatus == -1) perror("listen error");

        //A map containing the read buffers for each connection
        //(map because the file descriptor for the connection socket doesn't necessarily increment from 1.
        map<int, array<char, 65536>> inputBuffers;

        cout << "Server is running. ";

        requestEvents.start();
        generalWorkers.start();

        //Create changelog
        remove(CHANGELOG_FILENAME.c_str());
        writeChange(to_string(time(nullptr)));

        //While the server is running. Could close when all clients have said to close this explicitly.
        while(runThreads) {
            //The client struct
            sockaddr_in client;
            socklen_t clientSize = sizeof(client);


            cout << "Waiting for clients..." << endl;
            //We hold until we get a new connection
            int currentConnection = accept(serverFd, (struct sockaddr *)&server, (socklen_t*)&serverLength);

            if (currentConnection == -1){
                perror("Client could not connect, accept failed");
            }
            else{
                cout << "New client has connected" << endl;

                generalWorkers.post([this, &currentConnection, &inputBuffers, &connect_lock] {
                    int readStatus;
                    string commandText;
                    char command;
                    {
                        lock_guard<mutex> lock(connect_lock);
                        //We add the connection file descriptor to our list of connections
                        connections.emplace_back(currentConnection);
                        //Add a new read buffer for this client
                        inputBuffers.insert(pair<int, array<char, 65536>>(currentConnection, {0}));

                        if (read(currentConnection, inputBuffers.find(currentConnection)->second.data(), READ_REQUEST_SIZE) < 0){
                            perror("Could not read clients request");
                        }
                        commandText = string(const_cast<char*>(inputBuffers.find(currentConnection)->second.data()));
                        command = commandText[0];
                        if (commandText.size() >= 3){
                            commandText = commandText.substr(2);
                        }
                        else{
                            commandText = "";
                        }
                    }

                    //Commit request
                    if (command == '0'){
                        char mediumBuffer[65536];

                        read(currentConnection, mediumBuffer, sizeof(mediumBuffer));

                        if (commitRequest(currentConnection, string(mediumBuffer))){
                            cout << "Commit is complete" << endl;
                            string message = "0 1";
                            send(currentConnection, message.c_str(), strlen(message.c_str()), 0);
                        }
                        else{
                            string message = "0 0";
                            send(currentConnection, message.c_str(), strlen(message.c_str()), 0);
                        }
                    }
                    //Pull request
                    else if (command == '1'){
                        ifstream changeFile(CHANGELOG_FILENAME);
                        string serverChangelog((istreambuf_iterator<char>(changeFile)), istreambuf_iterator<char>());
                        serverChangelog = string("1 ").append(serverChangelog);
                        send(currentConnection, serverChangelog.c_str(), strlen(serverChangelog.c_str()), 0);

                        char tinyBuffer[16]{};
                        read(currentConnection, tinyBuffer, 16);
                        if (tinyBuffer[0] == '1'){
                            vector<File> serverFiles = FileChecker::getChangedFiles("./" + CHANGELOG_FILENAME);
                            for (File file : serverFiles){
                                string message = "1" + file.getPath() + "\r\n" + file.getFilename() + "\r\n" + file.getBody() + "\r\n";
                                send(currentConnection, message.c_str(), strlen(message.c_str()), 0);
                            }
                        }
                    }
                });
            }
        }
    }

private:
    string CHANGELOG_FILENAME = "ServerChangedFiles.txt";
    //64KB size for client .changelog filesize
    const int READ_REQUEST_SIZE = 65536;
    //1MB max size for each file
    const int READ_FILE_SIZE = 1048576;
    vector<int> connections;

    /**
     * Checks if a connection still exists in the list
     * @param con [int] the file descriptor / connection value
     * @return [bool] True if the connection exists
     */
    bool connectionExists(int con){
        for (int c : connections){
            if (c == con){
                return true;
            }
        }
        return false;
    }

    void writeChange(const string& change){
        ofstream outfile(CHANGELOG_FILENAME);
        outfile << change << endl;
        outfile.close();
    }

    bool commitRequest(int currentConnection, string request){
        //For every connection
        if (connections.size() > 1){
            //Create a set of worker threads to send the request to each client and receive the answer
            Worker workers((int) connections.size());
            workers.start();

            cout << "Asking clients for commit votes" << endl;
            string errorMessage;
            bool anyError = false;
            for (auto &con : connections){
                //For every connection receiving the request
                if (con != currentConnection){
                    //Send a request in it's own thread
                    workers.post([this, &con, &request, &anyError, &errorMessage] {
                        request = string("2 ").append(request);
                        send(con, request.c_str(), strlen(request.c_str()), 0);
                        char smallBuffer[1024];
                        read(con, smallBuffer, sizeof(smallBuffer));
                        if (smallBuffer[0] == '2' && smallBuffer[2] == '0'){
                            cout << "Merge error!" << endl;
                            if (!anyError){
                                anyError = true;
                            }
                            errorMessage += "Error on connection " + to_string(con) + "\n";
                        }
                    });
                }
            }
            workers.join();
            if (anyError){
                send(currentConnection, errorMessage.c_str(), strlen(errorMessage.c_str()), 0);
                return false;
            }
            else{
                string message = "0 ---";
                writeChange(request);
                send(currentConnection, message.c_str(), strlen(message.c_str()), 0);
                return updateServerFiles(currentConnection);
            }
        }
        else{
            cout << "No other clients connected. Instant accept" << endl;
            writeChange(request);
            string message = "0 ---";
            send(currentConnection, message.c_str(), strlen(message.c_str()), 0);
            cout << "Client has been told of this." << endl;
            return updateServerFiles(currentConnection);
        }
    }

    bool updateServerFiles(int currentConnection){
        cout << "Updating server files" << endl;
        char tinyBuffer[16];
        cout << endl;
        if (read(currentConnection, tinyBuffer, sizeof(tinyBuffer)) > -1){
            int firstNonNumber = 0;
            for (int i = 2; i < sizeof(tinyBuffer); i++){
                if (tinyBuffer[i] < 48 || tinyBuffer[i] > 57){
                    firstNonNumber = i - 2;
                    break;
                }
            }

            if (string(tinyBuffer).size() > 3){
                string fileAmountStr = string(tinyBuffer).substr(2, firstNonNumber);
                cout << fileAmountStr << " files" << endl;
                int files = stoi(fileAmountStr);

                send(currentConnection, string("0 ").append(fileAmountStr).c_str(), strlen(string("0 ").append(fileAmountStr).c_str()), 0);
                char largeBuffer[READ_FILE_SIZE];
                read(currentConnection, tinyBuffer, sizeof(tinyBuffer));
                cout << "Ready to take in files" << endl;
                if (tinyBuffer[0] != '-'){
                    for (int i = 0; i < files; i++) {
                        if (read(currentConnection, largeBuffer, READ_FILE_SIZE) > -1) {
                            string returnValue = string("0 ").append(to_string(string(largeBuffer).size()));
                            send(currentConnection, returnValue.c_str(), strlen(returnValue.c_str()), 0);
                            char *pathCharArray = strtok(largeBuffer, "\r\n");

                            string path = pathCharArray[0] == '/' ? string(("files" + string(pathCharArray))) : string(
                                    ("files/" + string(pathCharArray)));
                            string name = string(strtok(nullptr, "\r\n"));

                            if (path.find("cmake-build-debug") == string::npos){
                                string content = string(strtok(nullptr, "\r\r"));
                                try{
                                    if (static_cast<unsigned char>(path[0]) < 128){
                                        if (!filesystem::exists(path)){
                                            string recursiveCreation = "mkdir -p ./" + path;
                                            system(recursiveCreation.c_str());
                                        }
                                        if (!filesystem::exists(path + name)) {
                                            ofstream outfile(path + name);
                                            content = replaceAll(content, "\n", "\\\n");
                                            outfile << content;
                                            outfile.close();
                                        } else {
                                            ofstream outfile;
                                            outfile.open(path + name);
                                            content = replaceAll(content, "\n", "\n\n");
                                            outfile << strtok(nullptr, "\r\r");
                                            outfile.close();
                                        }
                                    }

                                }
                                catch (filesystem::filesystem_error &error) {
                                    cout << "error" << endl;
                                }
                            }
                        }
                    }
                    cout << "Successful in this" << endl;
                    return true;
                }
            }
        }
        cout << "Unsuccessful in this" << endl;
        return false;
    }
    /**
     * SOURCE: https://stackoverflow.com/questions/2896600/how-to-replace-all-occurrences-of-a-character-in-string, 21/04-2020
     */
    std::string replaceAll(std::string str, const std::string& from, const std::string& to) {
        size_t start_pos = 0;
        while((start_pos = str.find(from, start_pos)) != std::string::npos) {
            str.replace(start_pos, from.length(), to);
            start_pos += to.length(); // Handles case where 'to' is a substring of 'from'
        }
        return str;
    }
};