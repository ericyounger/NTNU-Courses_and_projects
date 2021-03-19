cmake_minimum_required(VERSION 3.17)
project(task1)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++1y -pthread -Wall -Wextra")
find_package(Boost 1.54.0 COMPONENTS system REQUIRED)
include_directories(${Boost_INCLUDE_DIR})

add_executable(task1 main.cpp ChessBoardPrint.cpp ChessBoardPrint.hpp ChessBoard.cpp)