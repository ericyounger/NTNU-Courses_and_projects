#include <iostream>
#include <string>
#include <vector>
#include <algorithm>


using namespace std;


ostream &operator<<(ostream &out, const vector<int> &table) {
	for (auto &e : table)
		out << e << " ";
	return out;
}

int main(){
	vector<int> v1 = {3, 3, 12, 14, 17, 25, 30};
	vector<int> v2 = {2, 3, 12, 14, 24};

	//Oppgave a:
	/* Finn første element i v1 som er større enn 15 ved hjelp av find_if.
	 * Tips: Den logiske funksjonen skal ta ett argument
	 * og returnere true dersom dette er større enn 15.
	 * */

	auto it = find_if(v1.begin(), v1.end(), [](int i){return i>15;});
	if(it != v1.end()) cout << *it << " er det første tallet større enn 15" << endl;
	else cout << "Ingen tall er større enn 15" << endl;

	//Oppgave b:
	/*Vi definerer "omtrent lik" til å bety at forskjellen mellom to verdier ikke er mer enn 2.
	 * Finn ut om intervallet [v1.begin(), v1.begin() + 5> og v2 er like i denne betydningen av likhet.
	 * Hva med intervallet [v1.begin(), v1.begin() + 4>? Bruk algoritmen equal.
	 * */
	bool equals = equal(v1.begin(), v1.begin()+ 5, v2.begin(),  [](int a, int b){return abs(a-b)<=2;});
	if(equals) cout << equals << endl;
	else cout << "Does not equal anything" << endl;

	bool equals2 = equal(v1.begin(), v1.begin()+4, v2.begin(), [](int a, int b){return abs(a-b)<=2;});
	if(equals2) cout << "They are equal" << endl;
	else cout << "Does not equal anything" << endl;

	//Oppgave c:
	/* Erstatt alle oddetall i v1 med 100 ved hjelp av replace_copy_if. */
	//TODO: Allocate room for input
	vector<int> out;

	cout << "Before replace_if ->" << v1 << endl;
	replace_copy_if(v1.begin(), v1.end(), v1.begin(), [](int i){ return ((i%2)==1);}, 100);
	cout << "After replace_if ->" << v1 << endl;

}