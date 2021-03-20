cmake_minimum_required(VERSION 2.8)
project(prosjekt)


find_package (Threads)

set(CMAKE_CXX_STANDARD 17)
SET(CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS} "-std=c++17 -pthread")


add_executable(server main.cpp Coordinator.cpp ServerSocket.cpp)
add_executable(client TUI.cpp FileComparator.cpp FileChecker.cpp ClientSocket.cpp Worker.cpp)
add_executable(filetester FileTester.cpp)
add_executable(tester test/tester.cpp Worker.cpp)

