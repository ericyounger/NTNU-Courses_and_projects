import csv
import re
import math
from decimal import Decimal

"""
A program for calculating the empirical and calculated single-atom density, distance between atoms, and atoms per volume
of all the elements based on input .CSV-files.

The program prints out a table to the terminal containing all the calculated and comparable data
(be sure to have a wide terminal when running this program for proper print out).

Each element is handled as a separate Element object containing methods for calculating volume,
density, distance between atoms and atoms per. volume.

The required input CSV-files are:
    
    atomic_weight.csv
    atomicRadii.csv
    numericDensities.csv
    
These files were, in our case, fetched from Wikipedia during the development of this program.

NOTES:

    We had to update element 16 (Sulfur) as the stated calculated radius was "00".
    We assume this was a spelling mistake.
    
    We had to update element 43 (Technetium) as it had no stated density.
    We replaced the "-" in the file with "no data" in order to make the CSV cooperate with our program.
    
"""

# Utilities
regex_decimal_only = r"\d+\.?\d*"       # Used when parsing data (String) to separate decimal numbers from text
U = 1.66053906660 * math.pow(10, -27)   # Atomic weight in kg
pico_to_centi = pow(10, 10)             # Convert from pico magnitude to centi magnitude


class Element:
    def __init__(self, atomic_no, symbol, name, empirical_atomic_radius, calculated_atomic_radius):
        self.atomicNo = atomic_no
        self.symbol = symbol
        self.name = name
        self.empirical_atomic_radius = empirical_atomic_radius      # cm
        self.calculated_atomic_radius = calculated_atomic_radius    # cm
        self.stated_density = None                                  # g/cm^3
        self.empirical_single_atom_density = None                   # g/cm^3                Calculated in program
        self.calculated_single_atom_density = None                  # g/cm^3                Calculated in program
        self.mass = None                                            # g
        self.empirical_single_atom_volume = None                    # g/cm^3                Calculated in program
        self.calculated_single_atom_volume = None                   # g/cm^3                Calculated in program
        self.empirical_distance_between_atoms = None                # cm                    Calculated in program
        self.calculated_distance_between_atoms = None               # cm                    Calculated in program
        self.stated_atoms_per_volume = None                         # Zetta-atoms/ cm^3
        self.empirical_atoms_per_volume = None                      # Zetta-atoms/ cm^3     Calculated in program
        self.calculated_atoms_per_volume = None                     # Zetta-atoms/ cm^3     Calculated in program


    def setDistanceBetweenAtomsAndCalculateAtomsPerVolume(self):

        # Empirical
        if self.mass != "no data" and self.stated_density != "no data" and self.empirical_atomic_radius != "no data":

            '''
            r is the distance from the center of an atom to a theoretical mid-point between it and a nearby atom.
            d is the distance from the boundary of an atom to a theoretical mid-point between it and a nearby atom.
            
            the distance between atoms is 2*d
            '''

            r = ((3 * self.mass) / (4 * math.pi * self.stated_density)) ** (1/3)

            d = float(r) - float(self.empirical_atomic_radius)  # Distance between atoms EMPIRICAL
            self.empirical_distance_between_atoms = d * 2

            # Volume
            v = (4 / 3) * math.pi * math.pow(r, 3)  # 🔴

            atoms_per_volume = (1/v) * math.pow(10,-21)
            self.empirical_atoms_per_volume = atoms_per_volume

        else:
            self.empirical_single_atom_density = "no data"
            self.empirical_atoms_per_volume = "no data"
            self.empirical_distance_between_atoms = "no data"

        # Calculated
        if self.mass != "no data" and self.stated_density != "no data" and self.calculated_atomic_radius != "no data":

            r = ((3 * self.mass) / (4 * math.pi * self.stated_density)) ** (1/3)
            d = float(r) - float(self.calculated_atomic_radius)
            self.calculated_distance_between_atoms = d*2

            # Volume
            v = (4 / 3) * math.pi * math.pow(r, 3)  # 🔴

            atoms_per_volume = (1 / v) * math.pow(10, -21)
            self.calculated_atoms_per_volume = atoms_per_volume

        else:
            self.calculated_single_atom_density = "no data"
            self.calculated_atoms_per_volume = "no data"
            self.calculated_distance_between_atoms = "no data"


    def calculateSingleAtomDensity(self):

        if self.empirical_single_atom_volume != "no data" and self.mass != "no data":
            self.empirical_single_atom_density = (self.mass / self.empirical_single_atom_volume)
        else:
            self.empirical_single_atom_density = "no data"

        if self.calculated_single_atom_volume != "no data" and self.mass != "no data":
            self.calculated_single_atom_density = (self.mass / self.calculated_single_atom_volume)
        else:
            self.calculated_single_atom_density = "no data"


    def calculateSingleAtomVolume(self):
        if self.empirical_atomic_radius != "no data":
            self.empirical_single_atom_volume = (4 / 3) * math.pi * math.pow(self.empirical_atomic_radius, 3)
        else:
            self.empirical_single_atom_volume = "no data"

        if self.calculated_atomic_radius != "no data":
            self.calculated_single_atom_volume = (4 / 3) * math.pi * math.pow(self.calculated_atomic_radius, 3)
        else:
            self.calculated_single_atom_volume = "no data"

    def printOut(self):

        # Handling for elements with no stated density
        if self.stated_density is None:
            self.stated_density = "no data"

        # Handling for elements with no stated density
        if self.stated_atoms_per_volume is None:
            self.stated_atoms_per_volume = "no data"

        # Formatting into columns followed by the variables to be printed
        print('{:<20} {:<20} {:<20} {:>20} {:>35} {:>40} {:>30} {:>30} {:>30} {:>30}'.format(self.atomicNo,
                                                                                             self.name,
                                                                                             self.stated_density,
                                                                                             '%.4e' % Decimal(self.empirical_single_atom_density) if self.empirical_single_atom_density != "no data" else "no data",
                                                                                             '%.4e' % Decimal(self.calculated_single_atom_density) if self.calculated_single_atom_density != "no data" else "no data",
                                                                                             '%.4e' % Decimal(self.empirical_distance_between_atoms) if self.empirical_distance_between_atoms != "no data" else "no data",
                                                                                             '%.4e' % Decimal(self.calculated_distance_between_atoms) if self.calculated_distance_between_atoms != "no data" else "no data",
                                                                                             '%.4e' % Decimal(self.stated_atoms_per_volume) if self.stated_atoms_per_volume != "no data" else "no data",
                                                                                             '%.4e' % Decimal(self.empirical_atoms_per_volume) if self.empirical_atoms_per_volume != "no data" else "no data",
                                                                                             '%.4e' % Decimal(self.calculated_atoms_per_volume) if self.calculated_atoms_per_volume != "no data" else "no data"))


if __name__ == '__main__':

    periodic_table = []     # Storage for all our elements

    '''
    Create elements based on parsed csv-data and
    adds the atomic number, symbol, name, empirical radius, calculated radius
    '''
    with open('atomicRadii.csv', newline='') as csvfile:
        lines = csv.reader(csvfile, delimiter=',', quotechar='|')
        for row in lines:
            if(row[0].startswith("atomic number")):     # Ignore header line in CSV file
                continue
            else:
                if row[3] == "no data" and row[4] == "no data":
                    element = Element(row[0], row[1], row[2], row[3], row[4])
                    periodic_table.append(element)

                elif row[3] == "no data" and row[4] != "no data":
                    element = Element(row[0], row[1], row[2], row[3], float(row[4]) / pico_to_centi)
                    periodic_table.append(element)

                elif row[4] == "no data" and row[3] != "no data":
                    element = Element(row[0], row[1], row[2], float(row[3]) / pico_to_centi, row[4])
                    periodic_table.append(element)

                else:
                    element = Element(row[0], row[1], row[2], (float(row[3]) / pico_to_centi), (float(row[4]) / pico_to_centi))
                    periodic_table.append(element)

    '''
    Add empirical data to elements based on csv-data
    '''
    with open('numericDensities.csv', newline='') as csvfile:
        lines = csv.reader(csvfile, delimiter=',', quotechar='|')
        for row in lines:

            if(row[0].startswith("Name")):      # Ignore header line in CSV file
                continue

            else:
                for element in periodic_table:
                    if(element.atomicNo == row[4]):

                        '''
                        Read in number of atoms per volume unit
                        '''

                        regex_atoms_per_volume = re.search(regex_decimal_only, row[3])
                        if(regex_atoms_per_volume):     
                            element.stated_atoms_per_volume = float(regex_atoms_per_volume.group())
                        else:
                            element.stated_atoms_per_volume = "no data"

                        '''
                        Reads in stated density
                        '''

                        regex_stated_density = re.search(regex_decimal_only, row[2])
                        if(regex_stated_density):

                            element.stated_density = float(regex_stated_density.group())

                        else:
                            element.stated_density = "no data"

    '''
    Reads in mass
    '''

    with open('atomic_weight.csv', newline='') as csvfile:
        lines = csv.reader(csvfile, delimiter=',', quotechar='|')
        for row in lines:
            for element in periodic_table:
                if(element.atomicNo == row[0]):
                    regex_mass = re.search(regex_decimal_only, row[4])
                    if(regex_mass):
                        element.mass = (float(regex_mass.group()) * U) * 1000  # convert from atomic mass gram
                    else:
                        element.mass = "no data"

    #print table header
    print('{:<20} {:<20} {:<20} {:>35} {:>35} {:>30} {:>30} {:>35} {:>30} {:>30}'.format("ATOMIC_NR",
                                                                                         "NAME",
                                                                                         "STATED_DENSITY (g/cm^3)",
                                                                                         "EMP.SINGLE A. DENSITY (g/cm^3)",
                                                                                         "CALC. SINGLE A. DENSITY (g/cm^3)",
                                                                                         "EMP.DIST BETWEEN A. (cm)",
                                                                                         "CALC.DIST BETWEEN A. (cm)",
                                                                                         "STATED A. PR. VOL. (z-a/cm^3)",
                                                                                         "EMP. A. PR. VOL. (z-a/cm^3)",
                                                                                         "CALC. A. PR. VOL. (z-a/cm^3)"))

    '''
    Calculates the distance between atoms, atoms per. volume, single atom volume, and single atom density for every
    element in out periodic table.
    
    After calculation, the element runs printOut()
    '''
    for element in periodic_table:

        element.setDistanceBetweenAtomsAndCalculateAtomsPerVolume()
        element.calculateSingleAtomVolume()
        element.calculateSingleAtomDensity()

        print(element.printOut(), end="\r")

