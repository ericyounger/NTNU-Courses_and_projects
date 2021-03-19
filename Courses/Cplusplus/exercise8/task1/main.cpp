#include <iostream>
#include <math.h>
#include <iomanip>
#include <cmath>

template <typename Type>

bool equal(Type a, Type b) {
	std::cout << "Template metoden kalles" << std::endl;
	return a == b;
}
bool equal(double a, double b) {
	std::cout << "Spesial metoden kalles" << std::endl;
	return fabs(a-b)<0.00001;
}


int main() {
	std::cout << "Is 1 and 1 equal? = " << equal(1,1) << std::endl;
	std::cout << "is 1.000001 and 1.000002 = " << equal(1.000001,1.000002) << std::endl;
	return 0;
}
