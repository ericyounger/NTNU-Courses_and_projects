#include <filesystem>
#include "../Worker.cpp"
#include "../FileComparator.cpp"
#include "../File.cpp"
#include "../ChangelogParser.cpp"

using namespace std;

/**
 * Contains static methods to test the Worker class.
 */
class WorkerTest {

public:
    /**
     * Checks if the Worker can run a task
     * @return true if the Worker runs the task successfully
     */
    static bool runsTask(){
        Worker worker(1);
        worker.start();
        int a = 0;
        worker.post([&a]{
            a++;
        });
        worker.join();
        return a == 1;
    }

    /**
     * Checks if the Worker runs the tasks sequentially if we only have one thread in it.
     * @return true if it works.
     */
    static bool runsTwoTasksInEventLoop(){
        Worker worker(1);
        worker.start();
        int a = 1;
        worker.post([&a]{
            a *= 5;
        });
        worker.post([&a]{
            a /= 2;
        });
        worker.join();
        return a == 2;
    }

    /**
     * Checks if the timout function actually waits.
     * @return true if it does.
     */
    static bool runTimeoutWork(){
        Worker worker(1);
        worker.start();
        int a = 1;
        worker.post_timeout(10, [&a]{
            a++;
        });
        bool firstValue = a == 1;
        worker.join();
        return firstValue && a == 2;
    }
};

/**
 * Prints a message on test success
 * @param message [string]: The messaghe given to the user.
 */
void printSuccess(const string& message){
    cout << "SUCCESS: " << message << endl;
}

void printFailure(const string& message){
    cout << "FAIL: " << message << endl;
}

bool checkIfContentInFile(){
    string str1 = FileChecker::getFileContent(std::filesystem::current_path() / "ChangedFiles.txt");
    return str1 ==
           "1587407194;ServerSocket.cpp:1587417267;testClientSocket.cpp:1587417267;FileComparator.cpp:1587417267";
}

bool checkNameOfModifiedFiles(){
    vector<string> v = FileChecker::getAllChangedFiles();
    int counter = 0;
    for (auto i : v) {
        if(i == "ServerSocket.cpp"){
            counter++;
        } else if(i == "testClientSocket.cpp"){
            counter++;
        } else if(i == "FileComparator.cpp"){
            counter++;
        }
    }
    return(counter == v.size());
}

 bool checkProjectUpdate(){
     long time = FileChecker::getTimeOfProjectUpdate();
     return ((time == 1587407194));
}


int main(){
    DIRECTORY_PATH = std::filesystem::current_path() / "";

    int failures = 0;
    if (WorkerTest::runsTask()){
        printSuccess("Worker can run one task");
    }
    else{
        printFailure("Worker can NOT run one task");
        failures++;
    }

    if (WorkerTest::runsTwoTasksInEventLoop()){
        printSuccess("Eventloop with worker works");
    }
    else{
        printFailure("Eventloop with worker does not work");
        failures++;
    }

    if (WorkerTest::runTimeoutWork()){
        printSuccess("Timeout works as expected");
    }
    else{
        printFailure("Timeout does not work as expected");
        failures++;
    }

    if(FileComparator::fileDiff(File("test.txt", 1234), File("test.txt", 124))){
        printSuccess("FileComparator::fileDiff works, should be different files");
    } else {
        printFailure("FileComparator::fileDiff does not work as expected");
        failures++;
    }

    if(!FileComparator::fileDiff(File("test.txt",1234), File("test.txt",1234))){
        printSuccess("FileComparator:fileDiff works as expected, returns false, because files are identical.");
    } else {
        printFailure("FileComparator::fileDiff does not work as expected");
        failures++;
    }

    if(FileComparator::newestFile(File("test.txt", 9999), File("test.txt",8888)) == 1){
        printSuccess("FileComparator:newestFile works as expected, file 1 is newer than file2");
    } else {
        printFailure("FileComparator:newestFile does not work as expected");
        failures++;
    }

    if(FileComparator::newestFile(File("test.txt", 8888), File("test.txt",9999)) == -1){
        printSuccess("FileComparator::fileDiff works as expected, file 1 is older than file 2");
    } else {
        printFailure("FileComparator:newestFile does not work as expected");
        failures++;
    }

    if(FileComparator::newestFile(File("test.txt", 9999), File("test.txt",9999)) == 0){
        printSuccess("FileComparator:newestFile works as expected, files have not been modified");
    } else {
        printFailure("FileComparator:newestFile does not work as expected");
        failures++;
    }

    if(ChangelogParser::parseToFiles("../test/", ';',':').size() == 4){
        printSuccess("ChangelogParser::parseToFiles works as expected, specified files only contains 4 File objects");
    } else {
        printFailure("ChangelogParser::parseToFiles does not work as expected");
        failures++;
    }


    if(checkIfContentInFile()){
        printSuccess("FileChecker::getFileContent works as expected, file content matching");
    } else {
        printFailure("FileChecker::getFileContent does not work as expected, file content not matching");
        failures++;
    }

    if(FileChecker::getFilesChanged() == 3){
        printSuccess("FileChecker::getFilesChanged works as expected, amount of modified files matching");
    } else {
        printFailure("FileChecker::getFilesChanged does not work as expected, amount of modified files not matching");
        failures++;
    }

    if(checkNameOfModifiedFiles()){
        printSuccess("FileChecker::getAllChangedFiles works as expected, names of modified files matching");
    } else {
        printFailure("FileChecker::getAllChangedFiles does not work as expected, names of modified files not matching");
        failures++;
    }

    if(checkProjectUpdate()){
        printSuccess("FileChecker::getTimeOfProjectUpdate works as expected, time of project update matches");
    } else {
        printFailure("FileChecker::getTimeOfProjectUpdate does not work as expected, time of project update not matching");
        failures++;
    }
    return -failures;
}
