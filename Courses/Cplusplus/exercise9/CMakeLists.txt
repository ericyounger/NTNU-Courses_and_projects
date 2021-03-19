cmake_minimum_required(VERSION 3.17)
project(exercise9)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread -Wall -Wextra")

add_executable(exercise9 main.cpp)