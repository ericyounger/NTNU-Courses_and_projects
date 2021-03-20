#include <string>
#include "./File.cpp"
#include <stdlib.h>
#include <stdio.h>
#include <map>
#include "./FileChecker.cpp"

using namespace std;

/**
* @class FileComparator
 * Is a class with static methods for file comparison of File objects.
 * @see File
* */
class FileComparator
{
public:
    /**
     * fileDiff is a static method that checks if there are differences between File objects.
     * @param file1[File]: Takes a File object to compare with.
     * @param file2[File]: Takes a File object to compare with.
     * @return [bool]: True if files differs, false if similar
     * @see File
     * */
    static bool fileDiff(File file1, File file2) {
        //Returns 0 on no change, similar dates. And 1 for changed

        if (file1.getFilename().compare(file2.getFilename()) != 0) {
            //Returns 1 if filename differs
            return true;
        }

        if (file1.getLastModified()-file2.getLastModified() != 0) {
            //Returns 1 if file last modified differs.
            return true;
        }

        return false;
    }

    static vector<File> allErrors(const string& localPath, const vector<File>& serverFiles){

        vector<File> errorFiles{};

        vector<File> localFiles = FileChecker::getChangedFiles();
        for (File localFile : localFiles){
            for (File serverFile: serverFiles){
                if (localFile.getFilename() == serverFile.getFilename()){
                    errorFiles.emplace_back(localFile);
                }
            }
        }

        return errorFiles;
    }


    /**
     * Static method that checks which File object is last modified
     * @see File
     * @param file1[File]: Takes first File object to be used in comparison
     * @param file2[File]: Takes a second File object to be used in comparsion
     * @return [int]: Returns 0 if similar, 1 if file 1 is newer, and -1 if file 1 is older.
     * */
    static int newestFile(File file1, File file2) {
        //Output from fileLastModified(filepath) is 1587233629, time in seconds since a date in 1970. UNIX EPOCH TIME.

        if(file1.getLastModified()-file2.getLastModified() == 0){
            //File has not changed
            return 0;
        }else if(file1.getLastModified()>file2.getLastModified()){
            //File1 is newer than file 2
            return 1;
        } else if(file1.getLastModified()<file2.getLastModified()){
            //File1 is older than file 2
            return -1;
        }
        //Error, should never get to this point.
        return -2;
    }

};

