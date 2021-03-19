# An example, not entirely stable, web server

## Prerequisites

- Linux or MacOS
- Optionally: the C++ IDE [juCi++](https://gitlab.com/cppit/jucipp)
  - If you do not install juCi++, you will need to install additional dependencies such as g++, cmake and make

## Installing dependencies

### Debian based distributions

`sudo apt-get install libboost-thread-dev libboost-regex-dev`

### Arch Linux based distributions

`sudo pacman -S boost`

### MacOS

`brew install boost`

## Download

```sh
git clone https://gitlab.stud.iie.ntnu.no/eidheim/buggy-web-server
```

## Compile and run

### Alternative 1

In a terminal:

```sh
juci buggy-web-server
```

In juCi++, choose Compile and Run in the Project menu.

Use Kill Last Process in the Project menu to stop the server.

### Alternative 2

In a terminal:

```sh
cd buggy-web-server
mkdir build
cd build
cmake ..
make
./web_server  # Press control-c to stop the server
```

## See web page

### Alternative 1

Open `http://localhost:8080` in a web browser

### Alternative 2

In a terminal:

```sh
telnet localhost 8080
GET / HTTP/1.1        # Press enter twice here

```

## Debugging

First, stop the web server (if it is running).

### Alternative 1

In juCi++, choose Start in the Debug menu.

#### Backtrace of a stopped process

Select Run Command in the Debug menu, and run `bt`.

### Alternative 2

In a terminal:

```sh
cd build                             # In case you are not in this folder already
mkdir debug
cd debug
cmake -DCMAKE_BUILD_TYPE=Debug ../..
make
gdb ./web_server                     # In MacOS, instead use: lldb ./web_server
run
```

#### Backtrace of a stopped process

```sh
bt
```
