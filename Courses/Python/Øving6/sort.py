import os

"""
Sorts the list using bubble sort, with an additional check, 
to sort lexicographically if the word has the same length

Takes: Array as input
Returns: sorted array.
"""
def sort(array):
    alist = array
    for element in range(len(alist)-1, 0, -1):
        for i in range(element):
            if len(alist[i]) > len(alist[i + 1]):
                temp = alist[i]
                alist[i] = alist[i + 1]
                alist[i + 1] = temp
            elif(len(alist[i]) == len(alist[i + 1])):
                if (alist[i] > alist[i + 1]):
                    temp = alist[i]
                    alist[i] = alist[i + 1]
                    alist[i + 1] = temp

    return alist

"""
Reads in the text from a file into a list.

Takes: filename as input, i.e. 'list.txt'
Returns: array created from textfile
"""
def readToList(filename):
    filepath = get_filepath(filename)
    array = []
    with open(filepath, "r") as file:
        line = file.readline().strip("\n")
        array.append(line)

        while(line):
            line = file.readline().strip("\n")
            if(line != ""): #check to avoid blank lines in the array.
                array.append(line)

    return array

"""
Helper method to get the filepath to the filename.
This method traverses the subdirectories to find a matching filename.

Takes: filename as input, i.e. 'list.txt'
Returns: a string with the filepath+filename 
"""
def get_filepath(filename):
        for root, dir, files in os.walk(os.curdir):
            for file in files:
                if filename in file:
                    return root + "/" + filename


if __name__ == "__main__":
    array = readToList("list.txt") #read text to array
    print("Before sort\n {}".format(array))
    array = sort(array) #sorts the array
    print("After sort\n {}".format(array))
