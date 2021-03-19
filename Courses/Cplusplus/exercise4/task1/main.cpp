#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<double> numbers{2.0,9.4,1.2,92.0,3.0};

    cout << "Front method test" << endl;
    cout << numbers.front() << endl; //Returns first element (2.0)

    cout << "Back method test" << endl;
    cout << numbers.back() << endl; //Returns last element (3.0);

    cout << "Emplace method" << endl;
    numbers.emplace(numbers.begin()+1,999.9);
    cout << numbers.front() << endl; //Still (2.0) as first number.
    
    auto search = find(numbers.begin(), numbers.end(), 1.2);

    if(search != numbers.end()){
        //*search to dereference iterator result.
        cout << "Found with find: " << *search << endl;
    } else {
        cout << "Find returned empty" << endl;
    }


    return 0;
}
