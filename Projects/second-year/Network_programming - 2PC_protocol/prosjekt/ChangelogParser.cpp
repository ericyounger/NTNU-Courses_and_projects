#include <vector>
#include "./File.cpp"
#include <iomanip>
#include <fstream>
#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include <cstring>
#include <sstream>

using namespace std;

/**
 * @class Changelogparser
 * Changelogparser, has only one static method, which is used for parsing files
 * with two delimiters, a FileDelimiter, and a VariableDelimiter.  Method returns a vector with File objects
 * @see File
 * */
class ChangelogParser{
public:

    static vector<File> parseToFilesFromString(string fileString, char fileDelimiter, char variableDelimiter){
        vector<File> files;

        //PARSE STRING INTO TOKENS
        istringstream ss(fileString);
        string token;

        int header = 0; // First token is header file.

        while(getline(ss, token, fileDelimiter)) {
            //First token is header
            if(header == 0){
                File a("projectUpdated", atoll(token.c_str()));
                files.emplace_back(a);
                header++;
            }
                //Ordinary files
            else {
                istringstream fileVars(token);
                string variable;

                //SPLITS FILE TOKENS INTO VARIABLE TOKENS
                string filename;
                long date;
                int whichVariable = 0;

                while(getline(fileVars, variable, variableDelimiter)){
                    if(whichVariable == 0){
                        filename = variable;
                        whichVariable++;
                    } else {
                        date = atoll(variable.c_str());
                    }
                }
                File a(filename, date);
                files.emplace_back(a);
            }
        }
        return files;
    }

    /**
     * parseToFiles is a static method that parses a string, splits into variables using two delimiters:
     * FileDelimiter and VariableDelimiter
     * method returns a vector of File objects.
     * @see File
     * */
    static vector<File> parseToFiles(string filepath, char fileDelimiter, char variableDelimiter){
        //Takes stringinput like( file;date ) seperates with delimiter ";"
        //Tokenizer for string, splits string into tokens and assign to variables.
        vector<File> files;
        string fileString;

        //READ IN FROM FILE TO STRING
        try{
            ifstream infile( filepath + "ChangedFiles.txt");
            if (infile.good()) {
                string sLine;
                getline(infile, fileString);
            }
            infile.close();
        }catch(std::exception const& e){ //If there is no pull/creation time in ChangedFiles.txt
            cout << "There was an error" << e.what() << endl;
        }

        return parseToFilesFromString(fileString, fileDelimiter, variableDelimiter);


    }

};
