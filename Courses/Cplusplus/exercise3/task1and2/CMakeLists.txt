cmake_minimum_required(VERSION 3.17)
project(task1and2)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread -Wall -Wextra")


add_executable(client client.cpp circle.cpp circle.hpp)
