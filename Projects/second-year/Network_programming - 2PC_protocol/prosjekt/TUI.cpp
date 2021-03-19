#include <iostream>
#include <thread>
#include <functional>
#include "./ClientSocket.cpp"
using namespace std;
/*!
 * The entry point for the client application. Connects the client to a server, and sets up the initial project. \\
 * The input takes 1: Commit, 2: Pull or 3: Exit
 */

int quitProgram(){
    cout << "Exiting" << endl;
    exit(0);
}

int main(){
    bool runProgram = true; // If false, affects main loop and thread loop
    bool acceptedPath = false;
    string filepath;

    SocketClient client = SocketClient(4000, "127.0.0.1");

    while(!acceptedPath){
        cout << "\nEnter project filepath:" << endl;
        getline(cin, filepath);

        if(filepath == "exit"){
            quitProgram();
        }

        if(filesystem::exists(filepath)){
            cout << "The filepath is valid" << endl;
            acceptedPath = true;
            if (!filepath.empty() && filepath.back() != '/') {
                filepath += "/";
            }
            DIRECTORY_PATH = filepath;
        }
        else{
            string answer;
            while(answer != "y" || answer != "n"){
                cout << "\n" << filepath << " does not exist, do you wish to create the directory" << endl;
                cout << "Y/N?" << endl;

                cin >> answer;

                for (char &c: answer){
                    c = tolower(c);
                }

                if(answer == "y"){
                    //Create directory
                    cout << "Creating file" << endl;
                    ofstream(filepath.c_str()).put(' '); // create file
                    acceptedPath = true;
                    break;
                } else if(answer == "n"){
                    break;
                } else if(answer == "exit"){
                    quitProgram();
                } else{
                    cout << answer << " is not a valid choice" << endl;
                }
            }
        }
    }

    cout << "Starting up.." << endl;

    Worker modifiedChecker = Worker(1);
    modifiedChecker.start();

    //CREATES A THREAD FOR CHECKING CHANGED FILES AND SAVING IT TO CHANGEDFILES.TXT
    thread t([&runProgram, &modifiedChecker] {
        while (runProgram){
            //TODO: ADD MUTEX???
            modifiedChecker.post_timeout(5000, []{
                cout << "Checking files";
                FileChecker::writeAllModifiedFilesToFile();
            });
        }
    });
    cout << "Ready"<< endl;

    //USER INTERFACE
    while(runProgram){
        cout << "\nWhat would you like to do?" << endl;
        cout << "1. Commit  | 2. Pull  | 3. Exit" << endl;

        string choice;
        cin >> choice;

        cout << "You chose: " << choice << "\n" << endl;

        if(!(choice == "1" or choice == "2" or choice == "3")){
            //Convert to lowercase if not number
            for (char &c: choice){
                c = tolower(c);
            }
        }

        //COMMIT
        if(choice == "1" || choice == "commit"){
            cout << "Sending commit message, awaiting response.." << endl;

            modifiedChecker.stop();

            FileChecker::writeAllModifiedFilesToFile();

            if(client.commit(filepath)){
                remove((filepath + "ChangedFiles.txt").c_str());
                ofstream file(filepath + "ChangedFiles.txt");
                file << to_string(time(nullptr));
                file.close();
            }

            modifiedChecker.start();
        }

        //PULL
        else if(choice == "2" || choice == "pull" ){
            cout << "this function will come to a 2-CP-protocol near you soon!" << endl;
            /*
             * UFERDIG KODE
            cout << "Pulling from server" << endl;

            modifiedChecker.stop();

            if (client.pull(filepath + "ChangedFiles.txt")){
                remove((filepath + "ChangedFiles.txt").c_str());
                ofstream file(filepath + "ChangedFiles.txt");
                file << to_string(time(nullptr));
                file.close();
                cout << "Pull successful" << endl;
            }
            else{
                cout << "Pull unsuccessful, there are errors between the server and your logs" << endl;
            }

            modifiedChecker.start();

            cout << "Finished pull" << endl;
             */
        }

        //EXIT
        else if(choice == "3" || choice == "exit"){
            cout << "Shutting down.." << endl;
            runProgram = false; // Closes down main loop and thread loop
            modifiedChecker.join();
        }


        else{
            cout << choice << " is not a valid input" << endl;
        }

    }

    return 0;
}