import os

'''Get the unique words in the file and their frequencies'''
def getwordfreqs(filename):
    filepath = getFilePath(filename) #searches for filename in subfolders and returns filepath.

    SPACE = 32
    A = 97
    Z = 122
    dict = {}

    with open(filepath, "r") as file: # with auto closes.
        line = file.readline().lower()

        while line: # returns false if there aren't any more lines.

            '''Removes characters other than A-Z and SPACE'''
            for x in line:
                if (ord(x)<A or ord(x)>Z) and not (ord(x) == SPACE):
                    #should probably add some logic for words with seperator like re-use f.ex
                        line = line.replace(x, "").replace("  ", " ")


            '''Splits into words seperated by spaces'''
            listLine = line.split(" ")

            '''Go through every word and add it to dictionary or increments it'''
            for x in listLine:
                if x not in dict:
                    dict[x] = 1
                else: dict[x] += 1

            '''Reads in a new line and increment line number'''
            line = file.readline().lower()


    return dict

'''Get which line numbers the word is used'''
def getwordsline(filename, word):
    filepath = getFilePath(filename)  #searches for filename in subfolders and returns filepath.

    word_in_line_list = []
    with open(filepath, "r") as file: # with auto closes.
        line = file.readline().lower() #lower case for comparison
        cnt = 1 #line counter

        while line: #returns false if there are no more lines to read.
            if word in line:
                word_in_line_list.append(cnt)
            line = file.readline().lower()
            cnt += 1

    return word_in_line_list

'''Searches for the filename in subdirectories and returns filepath for that file'''
def getFilePath(filename):
    generatorDirectories = os.walk(os.curdir) # traverses the subdirectories and returns a generator object
    for root, dir, files in generatorDirectories:
        for file in files:
            if filename in file:
                return root+"/"+filename




if __name__ == "__main__":

        try:
            words = getwordfreqs("pg5200.txt")
            print("Number of unqiue words in file: " + str(len(words.items())))
            print("Dictionary returned: ")
            print(words)


            word_in_line = getwordsline("pg5200.txt", "the")
            print("\nFound word on line numbers:")
            print(word_in_line)

        except TypeError:
            print("filename not found, remember to add extension, F.ex .txt ")
        except Exception:
            print("Something went wrong")










