#include "./File.cpp"
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include "./FileComparator.cpp"
#include "./FileChecker.cpp"
#include "./ChangelogParser.cpp"

using namespace std;

int main(){
    vector<File> changedFiles = ChangelogParser::parseToFilesFromPath("./", ';', ':');

    //ONLY FILE IN CHANGEDFILES IS projectUpdated
    if(changedFiles.size() == 1){
        cout << "No changed files from server" << endl;
    } else{
        vector<File> myFiles = FileChecker::getAllFilesInProject("./");

        for(auto file : changedFiles){
            for(auto myFile: myFiles){
                //Should probably use a different identifier than filename, too late for it now.
                if(file.getFilename() == myFile.getFilename()){
                    cout << file.getFilename() << endl;
                    if(FileComparator::fileDiff(file,myFile)){
                        cout << "File differs" << endl;
                        //Commit failed?
                        int newest = FileComparator::newestFile(file, myFile);

                        if(newest == 1){
                            //Changedfile is newest
                        }
                        else if(newest == -1){
                            //My file is newer than server file.
                        }
                    } else {
                        //No files have conflifts
                        cout << "No files have conflict" << endl;
                    }
                }
            }
        }
    }





}