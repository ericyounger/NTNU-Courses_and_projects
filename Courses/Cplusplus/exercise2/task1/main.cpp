#include <iostream>

using namespace std;

int main() {


    //1a
    int i = 3;
    int j = 5;
    int *p = &i;
    int *q = &j;


    /*
     * p peker til variabel i, og q peker til j.
     * både p og q har egen adresse siden det settes av plass i minne til pointer/referansen
     */
    cout << "Variabel i = " << i << " har som adresse " << &i << endl;
    cout << "Variabel j = " << j << " har som adresse " << &j << endl;
    cout << "Variabel p = " << p << " har som adresse " << &p << endl;
    cout << "Variabel q = " << q << " har som adresse " << &q << endl;


    //1b

    /*
     * Siden p er en peker til i, så vil i få verdien 7, og ved utskrift cout << *p << i << endl
     * Man ser at begge vil ha lik verdi.
     */
    *p = 7;

    /*
     * siden q er en peker til j så vil verdien 4 legges til 5 som allerede ligger der og man får
     * at j er nå 9.
    */
    *q += 4;

    /*
     * q som peker til j vil få verdien til det som p peker til og legge til 1.
     *  *q = *p +1 = 7 +1 = 8
     */
    *q = *p + 1;

    /*
     * P får nå referansen til q som er til j
     * tar man *p så vil man få verdien til j ut.
     */
    p = q;

    /*
     * Man vil få verdien 8 8 ut, siden p = q;
     * De peker begge til samme element.
     */
    cout << *p << " " << *q << endl;
    return 0;
}
