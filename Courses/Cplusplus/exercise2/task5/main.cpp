#include <iostream>

using namespace std;

int main(){
  double number;

  double *pointer = &number;
  double &ref = number;


  ref = 1.0; // number is set to value 1.0
  cout << number << endl;
  number = 2.0; // number is set to value 2.0
  cout << number << endl;

  *pointer = 3.0; // number is set to value 3.0
  cout << number << endl;

}
