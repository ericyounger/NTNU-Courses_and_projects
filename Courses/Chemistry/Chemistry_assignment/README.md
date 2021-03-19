# Chemistry_assignment
Chemistry assignment with python

**Main file: atomic_values.py**

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

#### NOTES:

* We had to update element 16 (Sulfur) as the stated calculated radius was "00".
We assume this was a spelling mistake.

* We had to update element 43 (Technetium) as it had no stated density.
We replaced the "-" in the file with "no data" in order to make the CSV cooperate with our program.
