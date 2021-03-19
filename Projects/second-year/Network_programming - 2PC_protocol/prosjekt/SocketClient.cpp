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


using namespace std;


class SocketClient{
public:
    SocketClient(int PORT, string ip_addr){

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

            char readBuffer[1024];
            int readResult = 0;

            //TODO: Change to actual name later
            sendCommit(sock_fd, ".changelog");

            while(readResult > -1){
                readResult = recv(sock_fd, readBuffer, 1024,0);
                cout << "read with status:  " << readResult << endl;
                printf("%s\n", readBuffer );
            }
            cout << "Done reading";


        }

    void commit(const string& changelogPath) const{
        sendCommit(socketDescriptor, changelogPath);
    }

private:
    int socketDescriptor;
    static bool sendCommit(int sock_fd, const string& changelogPath){
        cout << "Sending changelog to server" << endl;
        ifstream in(changelogPath);
        string contents((istreambuf_iterator<char>(in)),
                istreambuf_iterator<char>());
        if (send(sock_fd, contents.c_str(), strlen(contents.c_str()), 0) > 0){
            cout << "Data sent to server for commitment" << endl;
            return true;
        }
        else{
            perror("Could not send data to server");
            return false;
        }
    }

};