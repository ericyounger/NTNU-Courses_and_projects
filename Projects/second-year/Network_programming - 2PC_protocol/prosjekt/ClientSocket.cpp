#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <netinet/in.h>
#include <iostream>
#include <string>
#include <cstring>
#include <fstream>
#include <array>

#include "./FileComparator.cpp"
#include "./ChangelogParser.cpp"
#include "./Worker.cpp"


using namespace std;



/**
 * @Class SocketClient connects a client to the server, It contains methods to commit and pull changes.
 */
class SocketClient{
public:
    /**
     * The constructor creates a new SocketClient ready to send commands and informations
     * @param PORT [int]: the port of the server we're connecting the client to
     * @param ip_addr [string]: the ip address of the server we're connecting the client to
     */
    SocketClient(int PORT, string ip_addr){

        Worker reader(1);

        //Create socket
        socketDescriptor = socket(AF_INET, SOCK_STREAM, 0);
        if(socketDescriptor == -1) cerr << "Socket creation failed" << endl;

        //For storing information about address and port etc.
        struct sockaddr_in address;

        //Initializing address etc.
        address.sin_family = AF_INET;
        address.sin_port = htons(PORT);
        address.sin_addr.s_addr = inet_addr(ip_addr.c_str());

        cout << "Client is running" << endl;

        //Connect to serversocket
        int connection = connect(socketDescriptor, (struct sockaddr*)&address, sizeof(address));
        if(connection == -1) {
            perror("Cannot connect");
        }
        else{
            cout << "Connected with status " << connection << endl;
            commitRequestWorkers.start();
        }
    }

    /**
     * Commits the changes you've made to the server
     * @param changelogPath [const string&]: The path to the changes that have been made
     * @return [bool] True if the commit is successful. Fails if the clients changes are not compatible with the servers.
     */
    bool commit(const string& changelogPath){
        initRequest(socketDescriptor, "0");
        return sendCommit(socketDescriptor, changelogPath);
    }

    /**
     * Pulls the changes from the server to the client
     * @return [bool]: True if the pull is successful. Fails if the servers changes are not compatible with the clients.
     */
    bool pull(const string& localChangelogPath){
        initRequest(socketDescriptor, "1");
        return acceptPull(socketDescriptor, localChangelogPath);
    }

private:
    int socketDescriptor;
    Worker commitRequestWorkers{1};
    vector<string> commitReads;
    vector<string> pullReads;
    vector<string> commitRequestReads;

    string readSocket(int sock_fd, char* buffer){
        if (read(sock_fd, buffer, sizeof(buffer)) > -1){
            string message = string(buffer);
            //Commit or pull request data
            while (message[0] == '2'){
                string incomingLog = message.substr(2);
                commitRequestWorkers.post([this, &incomingLog, &sock_fd]{
                    string message;
                    if (performVote(incomingLog)){
                        message = "2 1";
                    }
                    else{
                        message = "2 0";
                    }
                    send(sock_fd, message.c_str(), strlen(message.c_str()), 0);
                });
                if (read(sock_fd, buffer, sizeof(buffer)) > -1){
                    message = string(message.substr(2));
                } else{
                    perror("Could not read from server");
                }
            }
            return message.substr(2);
        } else{
            perror("Could not read from server");
        }
        return "-1";
    }

    static bool performVote(const string& incomingLog){
        vector<File> incomingFiles = FileChecker::getChangedFiles(incomingLog);
        vector<File> ourFiles = FileChecker::getChangedFiles();
        for (File incoming : incomingFiles){
            for (File ours : ourFiles){
                if (incoming.getFilename() == ours.getFilename()){
                    return false;
                }
            }
        }
        return true;
    }

    static int initRequest(int sock_fd, const string& command){
        return send(sock_fd, command.c_str(), strlen(command.c_str()), 0);
    }

    bool acceptPull(int sock_fd, const string& localChangelogPath) {
        cout << "Receiving changelog from server" << endl;
        char readBuffer[65536]{};
        string message = readSocket(sock_fd, readBuffer);
        if (message != "-1"){
            vector<File> serverFiles = ChangelogParser::parseToFilesFromString(message, ';', ':');
            bool result = !FileComparator::allErrors(localChangelogPath, serverFiles).empty();
            message = result ? "1 1" : "1 0";
            send(sock_fd, message.c_str(), strlen(message.c_str()), 0);
            if (result){
                char largeBuffer[1000000]{};
                int files = serverFiles.size();
                for (int i = 0; i < files; i++){
                    //Here we're only interested in the resulting buffer
                    string check = readSocket(sock_fd, largeBuffer);
                    if (check != "-1"){
                        char* path = strtok(largeBuffer, "\r\r");
                        string name = string(strtok(nullptr, "\r\r"));
                        system(path);
                        if (!filesystem::exists(path + name)){
                            ofstream outfile(path + name);
                            outfile << strtok(nullptr, "\r\r");
                            outfile.close();
                        }
                        else{
                            ofstream outfile;
                            outfile.open(path + name);
                            outfile << strtok(nullptr, "\r\r");
                            outfile.close();
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    bool sendCommit(int sock_fd, const string& changelogPath){
        cout << "Sending changelog to server" << endl;
        ifstream in(changelogPath + "ChangedFiles.txt");
        string contents((istreambuf_iterator<char>(in)),
                istreambuf_iterator<char>());

        char readBuffer[1024];
        if (send(sock_fd, contents.c_str(), strlen(contents.c_str()), 0) > -1){
            cout << "Data sent to server for commitment" << endl;

            char smallBuffer[1024];
            string nextMessage = readSocket(sock_fd, smallBuffer);

            if (nextMessage != "-1"){
                //Success message
                if (nextMessage[0] == '-' && nextMessage[1] == '-' && nextMessage[2] == '-') {
                    cout << "Commit is accepted" << endl;
                    vector<File> allFiles = FileChecker::getChangedFiles(changelogPath);
                    string fileAmountStr = to_string(allFiles.size());

                    cout << fileAmountStr.c_str() << " files are being sent to server" << endl;

                    fileAmountStr = string("0 ").append(fileAmountStr);

                    send(sock_fd, fileAmountStr.c_str(), strlen(fileAmountStr.c_str()), 0);

                    nextMessage = readSocket(sock_fd, smallBuffer);



                    int firstNonNumber = 0;
                    for (int i = 0; i < nextMessage.size(); i++){
                        if (nextMessage[i] < 48 || nextMessage[i] > 57){
                            firstNonNumber = i;
                            break;
                        }
                    }
                    if (nextMessage.substr(0, firstNonNumber) == fileAmountStr.substr(2)){
                        string result = "+";
                        send(sock_fd, result.c_str(), strlen(result.c_str()), 0);
                        for (File file : allFiles){
                            string message = file.getPath() + "\r\r" + file.getFilename() + "\r\r" + file.getBody() + "\r\r";
                            send(sock_fd, message.c_str(), strlen(message.c_str()), 0);
                            nextMessage = readSocket(sock_fd, readBuffer);
                        }

                        nextMessage = readSocket(sock_fd, smallBuffer);
                        if (nextMessage != "-1"){
                            cout << "The upload was successful, keep on working.";
                            return true;
                        }
                        else{
                            perror("Could not read from server");
                        }
                    } else{
                        string message = "-";
                        send(sock_fd, message.c_str(), strlen(message.c_str()), 0);
                        perror("Server did not read files correctly");
                    }


                }
                else{
                    cout << "We have a merge error that needs resolution before commit can take place " << endl;
                }

            }
            else{
                perror("Could not read from server");
            }
        }
        return false;
    }

};