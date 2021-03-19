cmake_minimum_required (VERSION 3.0)

project (buggy-web-server)

    add_compile_options(-std=c++11 -Wall -Wextra)
    if (CMAKE_CXX_COMPILER_ID MATCHES "Clang")
        add_compile_options(-Wthread-safety)
    endif()

add_library(simple-web-server INTERFACE)

target_include_directories(simple-web-server INTERFACE ${CMAKE_CURRENT_SOURCE_DIR})

find_package(Threads REQUIRED)
target_link_libraries(simple-web-server INTERFACE ${CMAKE_THREAD_LIBS_INIT})

find_package(Boost 1.53.0 COMPONENTS system thread REQUIRED)
target_link_libraries(simple-web-server INTERFACE ${Boost_LIBRARIES})
target_include_directories(simple-web-server INTERFACE ${Boost_INCLUDE_DIR})

add_executable(web_server main.cpp)
target_link_libraries(web_server simple-web-server)
