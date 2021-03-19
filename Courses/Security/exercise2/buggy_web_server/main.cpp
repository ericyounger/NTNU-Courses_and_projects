#include "server_http.hpp"

typedef SimpleWeb::Server<SimpleWeb::HTTP> HttpServer;

int main() {
  // Construct HTTP-server at port 8080 using 1 thread, and without request timeout
  HttpServer server;
  server.config.port = 8080;
  server.config.timeout_request = 0;

  // Response to all GET requests
  server.resource["^.*$"]["GET"] = [](std::shared_ptr<HttpServer::Response> response, std::shared_ptr<HttpServer::Request> /*request*/) {
    response->write("<h1>The web server is working!</h1>");
  };

  server.start();
}
