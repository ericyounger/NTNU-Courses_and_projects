import numpy as np
import math
def multiplication_table_mod(MIN_MULTIPLIER, MAX_MULTIPLIER):
    # 2a
    MAX_MULTIPLIER = 12
    MIN_MULTIPLIER = 1

    matrix = np.zeros((MAX_MULTIPLIER-1, MAX_MULTIPLIER-1))

    for x in range(MIN_MULTIPLIER,MAX_MULTIPLIER):
        for y in range(MIN_MULTIPLIER,MAX_MULTIPLIER):
            matrix[x-1][y-1] = x*y%MAX_MULTIPLIER
    return matrix

def multiplication_table(MIN_MULTIPLIER, MAX_MULTIPLIER):
    # 2 b.
    matrix = np.zeros((MAX_MULTIPLIER-1, MAX_MULTIPLIER-1))
    for x in range(MIN_MULTIPLIER,MAX_MULTIPLIER):
        for y in range(MIN_MULTIPLIER,MAX_MULTIPLIER):
            matrix[x-1][y-1] = x*y
    return matrix

def multiplicative_inverse(matrix):
    array = []
    for x in range(len(matrix)):
        for y in range(len(matrix)):
            if(matrix[x][y] == 1):
                array.append([x+1, y+1])
    return array

def k_shift_cipher_decode(cipher, shift):
    trimmedCipher = cipher.replace(" ", "").upper()
    letters = []
    for x in range(26):
        letters.append(chr(65+x))
        
    letters.append('Æ')
    letters.append('Ø')
    letters.append('Å')

    decoded_string = ""
    for letter in trimmedCipher:
        index = (letters.index(letter) + shift) % len(letters)
        decoded_string += letters[index]
    return decoded_string

print("task 2a")
multi_table_mod = multiplication_table_mod(1,10)
print(multi_table_mod)

print("\ntask 2b")
multi_inverse = multiplicative_inverse(multi_table_mod)
for x in multi_inverse:
    print("{} * {} = 1 mod 12".format(x[0], x[1]))

print("\ntask 2c")


print("\nOppgave 3a")


print("\n task 4a")
print("29 fakultet forskjellige nøkler kan man ha med 29 tegn")
print("29 fakultet = {}".format(math.factorial(29)))

print("\nOppgave 5")
print("Dekoded cipher er:")
decoded_string = k_shift_cipher_decode("YÆVFB VBVFR ÅVBV", 12)
print(decoded_string)