#include <iostream>
#include "Set.hpp"


int main() {
	Set tom;
	Set medVerdi({1,2,3});
	Set annetSet({2,4,6});

	//Skriver ut en tom mengde
	std::cout << "Skriver ut en tom mengde: " << tom << std::endl;

	//Skriver ut en mengde med innhold
	std::cout << "Skriver ut en mengde med tall i : " << medVerdi << std::endl;

	//Finner unionen av to mengder
	std::cout << "Finner unionen av to mengder: " << medVerdi + annetSet << std::endl;

	//Sammensetning av mengder (Ikke en del av oppgaven)
	std::cout << "5 + mengde gir mengden: " << 5 + medVerdi << std::endl;

	//Sammensetning av mengder andre veien
	std::cout << "Mengde + 12 gir : " << annetSet + 12 << std::endl;

	//Legge inn tall i mengden.
	medVerdi += 10;
	std::cout << "Legger til 10 i mengden: " << medVerdi << std::endl;

	//Skal ikke legge inn tall hvis det finnes
	medVerdi += 10;
	std::cout << "Skal være lik forrige utskrift: " <<medVerdi << std::endl;


	//Setter en mengde lik en annen
	std::cout << "Skal sette en en mengde lik en annen:" << std::endl;
	std::cout << "Før endring:" << annetSet << std::endl;
	annetSet = medVerdi;
	std::cout << "Etter endring:" << annetSet << std::endl;


	return 0;
}
