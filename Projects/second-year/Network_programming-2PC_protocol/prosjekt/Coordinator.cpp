#include <sys/socket.h>
#include <string>
#include "ServerSocket.cpp"
#include <vector>


class Coordinator
{
private :


public:
    Coordinator(){
        ServerSocket ss = ServerSocket(4000, "127.0.0.1"); // This is blocking, because of event loop inside. Change event loop if need to run more in coordinator
    }

};