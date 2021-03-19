#pragma once//

#include <vector>

using namespace std;

class Set{
public:
	Set();
	explicit Set(const vector<int> &numbers);
	Set operator+(const Set &other) const;
	Set operator+(int wholeNumber) const;
	Set &operator+=(const Set &other);
	Set &operator+=(int wholeNumber);
	Set &operator=(const Set &other);
	friend ostream &operator<<(ostream &ut, const Set &otherSet);
	friend Set operator+(int wholenumber, const Set &object);





private:
	vector<int> numbers;
};
