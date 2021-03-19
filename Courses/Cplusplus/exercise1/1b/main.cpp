#include <iostream>
#include <cstdlib>
#include <fstream>

using namespace std;


void read_temperatures(double temperatures[], int length);

int main(){
    int length = 5;
    double temperatures[length];
    read_temperatures(temperatures, length);

    int lowerThan = 0, between = 0, above = 0;

    for(int i=0; i<length; i++){
        cout << "Temperatur nr " << i << ":  " << temperatures[i] << endl;

        if(temperatures[i] < 10){
            lowerThan++;
        } else if(temperatures[i] >= 10 && temperatures[i] <= 20){
            between++;
        } else if(temperatures[i] > 20){
            above++;
        }
    }

    cout << "Antall under 10 er " << lowerThan << endl;
    cout << "Antall mellom 10 og 20 er " << between << endl;
    cout << "Antall over 20 er " << above << endl;
}


void read_temperatures(double temperatures[], int length) {
    cout << "Åpner fil.." << endl;
    const char filename[] = "../tallfil.dat";
    ifstream file;
    file.open(filename);

    double number;

    if (!file) {
        cout << "Feil ved åpning av innfil." << endl;
        exit(EXIT_FAILURE);
    }

    int counter = 0;
    cout << "Leser inn temperaturer" << endl;
    while (file >> number || counter < length) { // leser fram til filslutt
        temperatures[counter] = number;
        counter++;
    }

    cout << "Lukker fil" << endl;
    file.close();
}