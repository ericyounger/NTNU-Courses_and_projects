#include <string>

#include <vector>
#include <stdlib.h>
#include <iostream>
#include <stdlib.h>
#pragma once

using namespace std;

/**
 * @class File
 * Class for storing information on filename and lastUpdated date/time from strings.
 * */
class File{
public:

    /**
     * Empty constructor for File
     */
    File(){}

    /**
     * Constructor for File for header files
     * @param filename [string]: a string with the filename
     * @param date  [long]: a date from when file was last modified
     */
    File(string filename, long date){
        this->filename = filename;
        this->lastModified =date;
    }

    /**
     * Constructor for setting all member variables on initialisation
     * @param filename [string] : filename of file
     * @param date [long] : filedate from lastmodified
     * @param body [string] : The file contents of the file
     */
    File(string filename, long date, string body, string path){
        this->filename = filename;
        this->lastModified = date;
        this->body = body;
        this->filepath = path;
    }
    /**
     * Constructor for main attributes
     * @param filename [string] : filename of file
     * @param body [string] : The file contents of the file
     * @param path [string] : path to file
     */
    File(string filename, string body, string path){
        this->filename = filename;
        this->body = body;
        this->filepath = path;
    }

    /**
     * Public method to get filename
     * @return filename[string]
     */
    string getFilename(){
        return this->filename;
    }

    /**
     * Public method to get lastModified date
     * @return [long]
     */
    long getLastModified(){
        return this->lastModified;
    }

    /**
     * Public method to get body/content of a file
     * @return [string]
     */
    string getBody(){
        return this->body;
    }

    /**
     * Public method to get file path
     * @return [string]
     */
    string getPath(){
        return this->filepath;
    }

    /**
     * Public method to set filename of file
     * @param filename [string] : Takes a string of filename as input
     */
    void setFilename(string filename){
        this->filename = filename;
    }

    /**
     * Public method to set lastmodified date of file
     * @param date [long]: Takes a long as input for lastModified
     */
    void setLastModified(long date){
        this->lastModified = date;
    }

    /**
     * Public method to set body content of file
     * @param body [string]: Takes a string as input for file body
     */
    void setBody(string body){
        this->body = body;
    }

    /**
     * Public method to set filepath of file
     * @param path [string]: Takes a string as input for filepath
     */
    void setFilePath(string path){
        this->filepath = path;
    }


private:
    string filename;
    long lastModified;
    string body;
    string filepath;

};
