//
// Created by Eric Younger on 12/09/2020.
//


#include <iostream>
#include "Set.hpp"


using namespace std;

Set::Set() {}

Set::Set(const vector<int> &numbers) {
	this->numbers = numbers;
}


Set Set::operator+(const Set &other) const {
	Set newSet(this->numbers);
	newSet += other;
	return newSet;
}

Set &Set::operator+=(const Set &other) {
	auto beginOther = other.numbers.begin();
	while(beginOther != other.numbers.end()){
		if(!(find(this->numbers.begin(), this->numbers.end(), *beginOther) != this->numbers.end())){
			this->numbers.emplace_back(*beginOther);
		}
		++beginOther;
	}
	return *this;
}

Set &Set::operator=(const Set &other) {
	this->numbers = other.numbers;
	return *this;
}

ostream &operator<<(ostream &ut, const Set &otherSet) {
	if (otherSet.numbers.empty()) return ut << "{}";

	ut << "{";
	for(auto &number : otherSet.numbers){
		if(number !=  (int) otherSet.numbers.size()) ut << number << ", ";
		else ut << number;
	}
	ut << "}";
	return ut;
}

Set operator+(int wholenumber, const Set &object) {
	Set newSet(object.numbers);
	Set oneNumber({wholenumber});
	newSet += oneNumber;
	return newSet;
}

Set &Set::operator+=(int wholeNumber) {
	Set newNumber({wholeNumber});
	*this += newNumber;
	return *this;
}

Set Set::operator+(int wholeNumber) const {
	Set returnSet(this->numbers);
	Set oneNumber({wholeNumber});
	returnSet += oneNumber;
	return returnSet;
}





