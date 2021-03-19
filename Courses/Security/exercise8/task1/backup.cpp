#include <iomanip>
#include <iostream>
#include <openssl/evp.h>
#include <openssl/sha.h>
#include <sstream>
#include <string>
#include <vector>

/// Return hex string from bytes in input string.
static std::string hex(const std::string &input) {
  std::stringstream hex_stream;
  hex_stream << std::hex << std::internal << std::setfill('0');
  for (auto &byte : input)
    hex_stream << std::setw(2) << (int)(unsigned char)byte;
  return hex_stream.str();
}

int main() {

  //Hash
  std::string foundHash = "ab29d7b5c589e18b52261ecba1d3a7e7cbf212c6";
  int hashLength = foundHash.length();


  //SALT
  const unsigned char *salt = (unsigned char *) "Saltet til Ola";
  int saltLength = strlen((char*) salt);

  //Other settings.
  int iterations = 2048;


  //Output string(unsigned char)
  unsigned char *generatedHash = (unsigned char*) malloc(hashLength);
  std::string generatedHashHexed = hex((char*)generatedHash);

  const char* actualPassword;

  bool stop =false;
  while(foundHash != generatedHashHexed && !stop){

    const char* testingPassword;
    int num_ascii=123; // number of possible ascii characters
    for (int char1=65; char1<num_ascii; char1++) {
      for (int char2=65; char2<num_ascii; char2++) {
        for (int char3=65; char3<num_ascii; char3++) {
          // convert each digit to char and concatenate into string
          std::string attempt = std::string()+(char)char1+(char)char2+(char)char3;
          PKCS5_PBKDF2_HMAC_SHA1(attempt.c_str(), attempt.length(), salt, saltLength,iterations, hashLength/2, generatedHash);
          generatedHashHexed = hex((char*)generatedHash);
          actualPassword = attempt.c_str();
          if(foundHash == generatedHashHexed) {
            stop = true;
            std::cout << "Found password" << std::endl;
            std::cout << generatedHashHexed << std::endl;
            std::cout << "The password is : " << actualPassword << std::endl;
          }
        }
      }
    }



  }




  free(generatedHash);

};
