//
// Created by Eric Younger on 16/09/2020.
//

template <typename Type1, typename Type2>
class Pair{
public:
	Type1 first;
	Type2 second;

	Pair(Type1 a, Type2 b): first(a), second(b){}

	Pair operator+(const Pair &other) const {
		auto firstAdd = this->first + other.first;
		auto secondAdd = this->second + other.second;
		return Pair(firstAdd, secondAdd);
	}
	
	bool operator>(const Pair &other) const{
		auto firstSum = this->first + this->second;
		auto secondSum = other.first + other.second;
		return firstSum>secondSum;
	}





};