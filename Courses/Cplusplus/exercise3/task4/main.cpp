#include <iostream>
#include <string>

using namespace std;

int main(){
    //a
    string word1, word2, word3;
    cin >> word1 >> word2 >> word3;

    //b
    string sentence = word1 + " " + word2 + " " + word3 + ".";
    cout << sentence << endl;

    //c
    cout << word1.length() << endl;
    cout << word2.length() << endl;
    cout << word3.length() << endl;
    cout << sentence.length() << endl;

    //d
    string sentence2 = sentence;

    //e
    if(sentence2.length() > 12){
        sentence2.replace(10, 3, "xxx");
        cout << "Sentence er: " << sentence << endl;
        cout << "Sentence2 er: " << sentence2 << endl;
    }

    //f
    if(sentence2.length() > 4){
        string sentence_start = sentence.substr(0,5);
        cout << "Sentence_start er: " << sentence_start << endl;
        cout << "Sentence er: " << sentence << endl;
    }

    //g
    size_t result = sentence.find("hallo",0);
    if(result == string::npos){
        cout << "Hallo er ikke i strengen" << endl;
    } else {
        cout << "Resultatet er: " << result << endl;
    }

    //h
    size_t result2 = sentence.find("er", 0);
    if(result2 == string::npos) cout << "\"er\" ble ikke funnet i strengen" << endl;

    while(result2 != string::npos){
        cout << "\"er\" funnet ved posisjon " << result2 << endl;
        result2 = sentence.find("er", result2+2);
    }

}
