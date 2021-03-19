
#include <string>
#include <iostream>
#include <thread>
#include <iostream>
#include <chrono>
#include <iomanip>
#include <fstream>
#include <filesystem>
#include <ctime>
#include <sys/stat.h>
#include "./File.cpp"
#include <vector>
#include <sstream>

using namespace std;
namespace fs = std::filesystem;

static struct stat st;

static string DIRECTORY_PATH;

class FileChecker{
public:
    /**
     * Create or overwrite zeropoint-time in ChangedFiles.txt.
     * @param filepath [string]: the root filepath of project
     */
    static void createLastModifiedFile(){

        string FILE = DIRECTORY_PATH + "ChangedFiles.txt"; //Change path to where last modified files is stored

        std::time_t now = std::time(nullptr); //Set time to be now

        int projectLastUpdated = now;
        char data[100];

        if(std::filesystem::exists(FILE)){ //File exists - overwrite current content
            ofstream outfile;

            outfile.open(FILE); // open a file in write mode.
            outfile << projectLastUpdated;
            outfile.close();  // close the opened file.

        } else { //File does not exist - create and write to file.
            ofstream outfile(FILE);  // Create and open a text file
            outfile << projectLastUpdated;  // Write to the file
            outfile.close(); // Close the file
        }
    }


    /**
     * Get update of project
     * @param filepath [string]: the root filepath of project
     * @return [long] time of last update
     */
    static long getTimeOfProjectUpdate(){
        long firstLine = -1;
        try{
            ifstream infile( DIRECTORY_PATH + "ChangedFiles.txt");
            if (infile.good()) {
                string sLine;
                getline(infile, sLine);
                firstLine = stol(sLine, nullptr, 10);
            }
            infile.close();
        }catch(std::exception const& e){ //If there is no pull/creation time in ChangedFiles.txt
            cout << "There was an error" << e.what() << endl;
            createLastModifiedFile();
        }
        return firstLine;
    }

    /**
    * Write to ChangedFiles.txt whenever a file is modified compared to update of project
    * @param filepath [string]: the root filepath of project
    */
    static void writeAllModifiedFilesToFile() {

        //Check if file does not exists
        if(!std::filesystem::exists(DIRECTORY_PATH + "ChangedFiles.txt")){
            cout << "Doesnt exist, creating file...";
            createLastModifiedFile();
        }

        //Set updated time for project
        long updatedDate = getTimeOfProjectUpdate();

        std::string path = DIRECTORY_PATH;
        std::ofstream outfile;
        try{
            //Open write stream to file
            outfile.open(DIRECTORY_PATH + "ChangedFiles.txt", std::ios_base::trunc); // overwrite

            //Write last updated time for project
            outfile << updatedDate;

            for (const auto & entry : fs::recursive_directory_iterator(path)){ //Go recursive through all directories
                stat(entry.path().c_str(), &st); //Using unix system call
                long lmTime = st.st_mtime; //set last modified time

                if(updatedDate < lmTime){ //Check if file has been modified since update
                    if(entry.path().filename().string() != "ChangedFiles.txt"){ //If true write this to file
                        outfile << ";" << entry.path().filename().string() << ":" << lmTime;
                    }
                }
            }
        }
        catch(std::ofstream::failure &writeErr){
            cout << "Error occured!" << endl;
        }
        outfile.close();
    }

    /**
     * Get a vector of all files in project
     * @param filepath [string]: the root filepath of project
     * @return vector<File>: vector of File objects
     * @see File
     */
    static vector<File> getAllFilesInProject(){
        vector<File> files;

        for (const auto & entry : fs::recursive_directory_iterator(DIRECTORY_PATH)){ //Go recursive through all directories
            stat(entry.path().c_str(), &st);
            long lmTime = st.st_mtime;

                if(entry.path().filename().string() != "ChangedFiles.txt"){
                    File a(entry.path().filename().string(), lmTime);
                    files.emplace_back(a);
                }
        }
        return files;
    }

    /**
    * Get number of files in ChangedFiles.txt
    * @param filepath [string]: the root filepath of project
    * @return [int]: number of files modified since last update
    */
    static int getFilesChanged(){
        string str = "";
        try{ //Get modified files in ChangedFiles.txt
            ifstream infile( DIRECTORY_PATH + "ChangedFiles.txt");
            if (infile.good()) {
                string sLine;
                getline(infile, sLine);
                str = sLine;
            }
            infile.close();

            int filesCounter = 0;
            for(auto x : str){ //Loop through and count file dividers given by ";"
                //Increment if found
                if(x == ';'){
                    filesCounter++;
                }
            }
            return filesCounter;

        }catch(std::exception const& e){
            cout << "There was an error" << e.what() << endl;
        }
        return -1;
    }

    /**
    * Split a string into a vector
    * @param s [string]: string to be delimited
    * @return [vector<string>]: vector of strings based on delimiter
    */
    static vector<string> split (const string &s, char delim) {
        vector<string> result;
        stringstream ss (s);
        string item;

        while (getline (ss, item, delim)) {
            result.push_back (item);
        }

        return result;
    }

    /**
    * Get all names of modified files
    * @return [vector<string>]: vector with all names of modified files
    */
    static vector<string> getAllChangedFiles(){
        return getAllChangedFiles(DIRECTORY_PATH);
    }

    /**
    * Get all names of modified files. Used on server.
    * @param filepath [string]: the root filepath of project
    * @return [vector<string>]: vector with all names of modified files
    */
    static vector<string> getAllChangedFiles(const string &filepath){
        vector<string> files;
        string str = "";
        try{
            //Get line with all modified dates from file
            ifstream infile( filepath + "ChangedFiles.txt");
            if (infile.good()) {
                string sLine;
                getline(infile, sLine);
                str = sLine;
            }
            infile.close();

            vector<string> v = split (str, ';');
            v.erase(v.begin()); //Remove time of project update in first position
            std::size_t found = 0;
            string filename = "";
            //Lopp through, remove content(time) before filename
            for (auto i : v) {
                found = i.find(':');

                filename = i.erase(found);
                files.push_back(filename);
            }

        }catch(std::exception const& e){
            cout << "There was an error" << e.what() << endl;
        }
        return files;
    }




    /**
    * Get content of file
    * @param filepath [string]: file path of a specific file
    * @return [string]: content of file
    */
    static string getFileContent(const string& filepath){
        string content;
        //open and read stream of data from file
        std::ifstream file(filepath);
        if (file.is_open()) {
            std::string line;
            while (std::getline(file, line)) {
                content += line;
            }
            file.close();
        }
        return content;
    }

    /**
    * Get all modified files as objects in a vector
    * @return [vector<File>]: vector with all modified files
    * @see File.cpp
    */
    static vector<File> getChangedFiles(){
        return getChangedFiles(DIRECTORY_PATH);
    }

    /**
     * Get all modified files as objects in a vector. Used on the server where the file position is constant.
     * @param filepath [string]: Position of changelog file
     * @return  [vector<File<]: vector with all modified files
     */
    static vector<File> getChangedFiles(const string& filepath){
        vector<string> filenames = getAllChangedFiles(filepath);

        vector<File> files;

        for (const auto & entry : fs::recursive_directory_iterator(filepath)){ //Go recursive through all directories
            stat(entry.path().c_str(), &st);
            long lmTime = st.st_mtime;

            //check for matching filenames
            for (const auto& i : filenames) {
                if(entry.path().filename().string() == i){
                    //Create File object and add to vector
                    //TODO: EVEN
                    string str1 = entry.path().string().erase(entry.path().string().length() - i.length(), entry.path().string().length());
                    File a(i, getFileContent(entry.path()), str1);
                    //File a(i, getFileContent(entry.path()), entry.path());
                    files.emplace_back(a);
                }
            }
        }
        return files;
    }

    static void printAllFiles(){
        for (auto i : getAllChangedFiles()) {
            cout << i << endl;
        }
    }

    static void printFiles(){
        for (File i : getChangedFiles()) {
            cout << "Filename: " << i.getFilename() << endl;
            cout << "Path: " << i.getPath() << endl;
            cout << "Body: " << i.getBody() << endl;
        }
    }

};


