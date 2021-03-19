#include <iostream>

using namespace std;

int main(){
    size_t lessThan = 0, between = 0, above =0;
    size_t period = 5;

    cout << "Du skal skrive inn 5 temperaturer." << endl;

    for(size_t i=0; i<period; i++){
        double input;
        cout << "Temperatur nr " << i+1 << ": ";
        cin >> input;

        if(input < 10){
            lessThan++;
        } else if(input >= 10 && input <= 20) {
            between++;
        } else if(input > 20){
            above++;
        }
    }

    cout << "Antall under 10 er " << lessThan << endl;
    cout << "Antall mellom 10 og 20 er " << between << endl;
    cout << "Antall over 20 er " << above << endl;

}