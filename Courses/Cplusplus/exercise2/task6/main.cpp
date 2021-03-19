#include <iostream>

using namespace std;


int find_sum(const int *table, int length){
    int sum = 0;
    for(int i=0; i<length; i++){
        cout << "number " << table[i] << endl;
        sum += table[i];
        cout << "sum " << sum << endl;
    }
    return sum;
}

int main() {
    int arraySize = 20;
    int numbers[arraySize];

    for(int i=0; i< arraySize; i++){
        numbers[i] = i+1;
    }

    int sum10 = find_sum(numbers, 10);
    cout << "Sum of 10 first numbers is " << sum10 << endl;

    int sum5Next = find_sum(&numbers[10], 5);
    cout << "Sum of 5 next numbers is " << sum5Next << endl;

    int sum5Last = find_sum(&numbers[arraySize-5], 5);
    cout << "Sum of 5 last numbers are " << sum5Last << endl;

    return 0;
}
