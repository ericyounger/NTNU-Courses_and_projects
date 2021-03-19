#include <iostream>

using namespace std;

int main() {
    int a = 5;
    int &b = a; //B er referanse til a

    int *c; // allokerer plass til en peker

    c = &b; // c blir en peker til b som igjen peker til a.

    a = b + *c; // b(a) + *c(a) = a = a+a 
    b = 2;

    return 0;
}
