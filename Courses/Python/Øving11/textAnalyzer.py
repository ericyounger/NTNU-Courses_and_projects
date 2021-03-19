import os

class TextAnalyzer(object):
    '''A TextAnalyzer class, simply create a instance of this class
    to use the methods individually, or calling the method print_analyzis() to
    get an analyzis.'''

    def get_word_frequency(self, filename):
        '''Gets the frequency of words in your file and returns a dictionary with it
        Takes: filename as input
        Return: Word_dictionary (with frequency of words)
        '''
        replace = {44: None, 46: None, 33: None, 63: None, 58: None, 59: None, 10: " ", 40: None, 41: None, 34: None,
                   123: None, 125: None}

        filepath = self.__getFilepath(filename)
        word_dictionary = {}
        with open(filepath, "r", encoding="utf-8-sig") as reader:
            line = reader.readline()

            while (line != ""):
                check_line = line.translate(replace).split(" ")
                for item in check_line:
                    if item in word_dictionary:
                        word_dictionary[item] += 1
                    else:
                        word_dictionary[item] = 1
                line = reader.readline()

        return word_dictionary

    def get_unique_words(self, filename):
        '''Gets the no of unique words in a file
        Takes: filename as input
        Returns: Integer, the number of unique words in a file.
        '''
        word_dictionary  = self.get_word_frequency(filename)
        counter = 0
        for item in word_dictionary.values():
            if item == 1:
                counter += 1
        return counter

    def __percentage(self, numerator, denominator):
        '''Local helper method that calculates the percentage
        Takes:
        1 argument  = numerator
        2 argument = denominator

        Returns the percentage
        '''
        return numerator / denominator


    def __getFilepath(self, filename):
        """
        Helper method to get the filepath to the filename.
        This method traverses the subdirectories to find a matching filename.
        This is a local help method.

        Takes: filename as input, i.e. 'list.txt'
        Returns: a string with the filepath+filename
        """
        for root, dir, files in os.walk(os.curdir):
            for file in files:
                if filename in file:
                    return root + "/" + filename


    def get_words_per_sentence(self, filename):
        '''Returns a dictionary with the number of words per sentences
        Takes: filename as input
        Return: Dictionary with words per sentences.

        '''
        filepath = self.__getFilepath(filename)
        words = []

        with open(filepath, "r", encoding="utf-8-sig") as reader:
            line = reader.readline().lower()
            word = ""
            sentence_no = 1
            dict = {}
            _period_ = 46
            _a_ = 97
            _z_ = 122
            _space_ = 32
            _newLine_ = 10
            while (line != ""):
                #Need to count words before reading a new line
                for char in line:
                    if ord(char)>=_a_ and ord(char)<=_z_:
                        word += char
                    elif ord(char) == _period_:
                        if(word != ''):
                            words.append(word)
                        dict["Sentence no: {}".format(sentence_no)] = len(words)
                        word = ""
                        words = []
                        sentence_no +=1
                    elif ord(char) == _space_ or ord(char) == _newLine_:
                        if (word != ""):
                            words.append(word)
                            word = ""

                line = reader.readline().lower()

            return dict

    def get_sentences_per_paragraph(self, filename):
        '''Is a method that counts sentences per paragraph
        Takes: filename as input
        Returns: a list of sentences per paragraph
        '''
        filepath = self.__getFilepath(filename)

        with open(filepath, "r", encoding="utf-8-sig") as reader:
            line = reader.readline().lower()
            sentences = 0
            _period_ = 46 #unicode value
            _newLine_ = 10 #unicode value
            sentenceList = []

            while(line != ""):
                for letter in line:
                    if(ord(letter) == _period_):
                        sentences += 1
                    if(ord(letter) == _newLine_ and sentences > 0):
                        sentenceList.append(sentences)
                        sentences = 0
                line = reader.readline().lower()
            if(sentences>0):
                sentenceList.append(sentences)

        return sentenceList

    def get_percentage_easy_words(self, filename):
        '''Methods that returns the percentage of easy words in file,
           calculates a threshold based on frequency of words

           Takes: filename as input
           Returns: the percentage of easy words
           '''
        words_dictionary = self.get_word_frequency(filename)
        no_of_words = sum(words_dictionary.values())
        threshold = (no_of_words / 1000) * 1.3 + (no_of_words / (no_of_words*0.8))+3
        print("threshold", threshold)
        easy_words = 0
        for item_value in words_dictionary.values():
            if item_value > threshold:
                easy_words += item_value

        return self.__percentage(easy_words, no_of_words)

    def get_percentage_hard_words(self, filename):
        '''Methods that returns the percentage of hard words in file,
        calculates a threshold based on frequency of words

        Takes: filename as input
        Returns: the percentage of hard words
        '''
        words_dictionary = self.get_word_frequency(filename)
        no_of_words = sum(words_dictionary.values())
        threshold = (no_of_words / 1000) * 1.3 + (no_of_words / (no_of_words*0.8))+3
        hard_words = 0

        for item_value in words_dictionary.values():
            if item_value < threshold:
                hard_words += item_value

        return self.__percentage(hard_words,no_of_words)


    def get_no_of_words(self, filename):
        '''Method that returns the total number of words in file
        Takes: filename as input, i.e. "test.txt"
        Returns: Integer, number of total words in file.

        '''
        return sum(self.get_word_frequency(filename).values())



    def print_analyzis(self, filename):
        '''A print method for getting a full analyzis of a file, similar to toString in Java
        Takes: filename as input
        Returns: void
        '''
        print("Analyzis for", filename)
        sentence_length = self.get_words_per_sentence(filename)
        print("Sentence length:")
        print(sentence_length,"\n")

        percentage_easy_words = self.get_percentage_easy_words(filename)
        print("Percentage of easy words: ", percentage_easy_words)

        percentage_hard_words = self.get_percentage_hard_words(filename)
        print("Percentage of hard words: ", percentage_hard_words,"\n")

        no_of_words = self.get_no_of_words(filename)
        print("Dictionary has", no_of_words, "number of words in it")
        no_of_unique_words = self.get_unique_words(filename)
        print("Number of Unique Words:", no_of_unique_words)

        print("Percentage of Unqiue Words: ", self.__percentage(no_of_unique_words, no_of_words) * 100, "%", "\n")

        print("Sentences per paragraph")
        print(self.get_sentences_per_paragraph(filename), "\n")
        print("----------------------------------------------------------------------")



if __name__ == "__main__":
    file = "ipsum.txt"
    file2 = "test.txt"

    analyze = TextAnalyzer()

    analyze.print_analyzis(file)
    analyze.print_analyzis(file2)


