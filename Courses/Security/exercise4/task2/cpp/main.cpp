#include <iostream>
#include <string>

using namespace std;

string replaceString(const string &input);

int main() {
	string input;
	std::getline (std::cin,input);

	string example = "1 < 2 && 2 > 1";
	string output = replaceString(input);
	cout << output << endl;

	cout << "Example: " << replaceString(example) << endl;
	// prints out Example: 1 &lt; 2 &amp;&amp; 2 &gt; 1
	return 0;
}

string replaceString(const string &input){
	string newString = input;
	for(size_t i=0; i<newString.length(); ++i){
		if(newString[i] == '<'){
			newString.replace(i,1, "&lt;");
		} else if(newString[i] == '>'){
			newString.replace(i, 1, "&gt;");
		} else if(newString[i] == '&'){
			newString.replace(i, 1, "&amp;");
		}
	}
	return newString;
}

