#include <iostream>
#include <list>
#include <thread>
#include <vector>
#include <condition_variable>
#include <mutex>
#include <functional>
using namespace std;


static int staticID = 0;

/**
 * @class Worker
 * Worker class is for creating either an event loop or for Worker loop (Worker Threads).
 * Initializes a set of threads to work when there is work pushed.
 */
class Worker{

private:
    vector<thread> threads;
    list<function<void()>> tasks;
    mutex wait_mutex;
    condition_variable cv;
    int noOfThreads;
    bool stopThread = false;
    int currentID;


public:

    /**
     * Constructor for Worker class.
     * Initializes variables with amount of how many threads there should be in pool.
     */
    explicit Worker(int threads) {
        noOfThreads = threads;
        currentID = staticID;
        staticID++;
    }


    /**
     * Posts work to be done for threads waiting.
     * Notifies a thread when there is work to be done.
     */
    void post(const function<void()> &task){
        {
            lock_guard<mutex> lock(wait_mutex);
            tasks.emplace_back(task);
        }
        cv.notify_one();
    }

    /**
     * Creates the set number of threads given in constructor and give them tasks,
     * or put them to sleep while waiting for work.
     */
    void start(){
        stopThread = false;
        for(int i=0; i<noOfThreads; i++){
            threads.emplace_back([this, &i]{
                while (true)
                {
                    function<void()> task;
                    {
                        unique_lock<mutex> lock(wait_mutex);

                        while(!stopThread && tasks.empty()){
                            cv.wait(lock);
                        }

                        if (tasks.empty())
                        {
                            return;
                        }

                        task = *tasks.begin(); // Copy task for later use
                        tasks.pop_front(); // Remove task from list
                    }
                    task(); // Run task outside of mutex lock
                }

            });
        }
    }

    /**
     * Method that tells the threads to close down and join main thread.
     */
    void join(){
        stop();
        for (auto &thread : threads)
            thread.join();
    }

    /**
     * Stops the worker thread loop and wakes up threads to let them finish remaining work and then join main thread.
     */
    void stop(){
        stopThread = true;
        cv.notify_all();
    }

    /**
     * Adds a repeating task to the worker
     * @param delayMs [int]: The amount of time between each
     * @param task [function<void()>]: The repeating task.
     */
    void post_repeating(int delayMs, const function<void()> &task){
        thread t([&delayMs, &task, this]{
            post_timeout(delayMs, task);
            if (!stopThread){
                post_repeating(delayMs, task);
            }
        });
    }

    /**
     * Method to put thread to sleep for given duration.
     * @param timeoutMs time for thread to sleep, given in miliseconds
     */
    void post_timeout(int timeoutMs, const function<void()> &task){
        this_thread::sleep_for(std::chrono::milliseconds(timeoutMs));
        post(task);
    }
};