#!/usr/bin/env python
# coding: utf-8

# # Exercise 14

# ### Task 1

# In[1]:


sumMod = (232 + 22 * 77 - 18*18) % 8
print(sumMod)


# 232 + 22 * 77 - 18^2 (mod 8)  
# 0 + 6 * 5 - 4 = 26 mod 8  
# 26 mod 8 = 2  
# 

# ### Task 2

# In[2]:


import numpy as np
import math


# In[3]:


def multiplication_table_mod(MIN_MULTIPLIER, MAX_MULTIPLIER):
    # 2a
    matrix = np.zeros((MAX_MULTIPLIER-1, MAX_MULTIPLIER-1))

    for x in range(MIN_MULTIPLIER,MAX_MULTIPLIER):
        for y in range(MIN_MULTIPLIER,MAX_MULTIPLIER):
            matrix[x-1][y-1] = x*y%MAX_MULTIPLIER
    return matrix

def multiplicative_inverse(matrix):
    array = []
    for x in range(len(matrix)):
        for y in range(len(matrix)):
            if(matrix[x][y] == 1):
                array.append([x+1, y+1])
    return array


# #### a)

# In[4]:


multi_table_mod = multiplication_table_mod(1,12)
print(multi_table_mod)


# ##### b)

# In[5]:


multi_inverse = multiplicative_inverse(multi_table_mod)

for x in multi_inverse:
    print("{} * {} congruent 1 mod 12".format(x[0], x[1]))


# #### c)

# Grunnen til at man ikke kan få 1 og 0 på samme rad og kolonne, er fordi for å få 1 så må det settet være relativ primisk for å få 1.
# Det vil si at man enten får 0 eller 1.

# ### Task 3

# In[6]:


def inverse_matrix(A):
    if np.linalg.det(A) != 0:
        return np.linalg.inv(A)*np.linalg.det(A)
    else:
        return None 

def inverse_matrix_over_a(a, z):
    a_det = int(np.linalg.det(a))
    a_det_mod = a_det % len(z)
    z_multipl_inverse = multiplicative_inverse(z)
    det_in_multipl_inverse = False
    
    for x in z_multipl_inverse:
        if a_det_mod in x:
            det_in_multipl_inverse = True
    
    if(det_in_multipl_inverse):
        a_inv = inverse_matrix(a)
        return a_inv
    else:
        return None


# #### a)

# In[7]:


a = np.array([[2, -1], [5,8]])

z_10 = multiplication_table_mod(1,11)
inverse_over_z10 = inverse_matrix_over_a(a, z_10)
print("Invers over z10:")
print(inverse_over_z10)


# #### b)

# In[8]:


print("Invers over z9:")
z_9 = multiplication_table_mod(1,10)
inverse_over_z9 = inverse_matrix_over_a(a, z_9)
print(inverse_over_z9)


# ### Task 4

# a)  
# Et substitusjoncipher med alfabet med lengde 29 og med fast skift kan ha 29 forskjellige nøkler
# 
# b)  
# Ved å endre på cipheret med fast lengde til et permutasjonscipher, så vil det eksistere 29! = 8,841761993739701e30 ulike nøkler.
# Man kan også sette det opp i et blokkchifre. Det hjelper også på å bruke dialekt, og å unngå padding, og kjente hilsener og fraser.
# 
# c)  
# Hvis vi lager et substitusjonschiffer for blokker med N-tegn, så har vi alfabetet = (29^n)!

# ### Task 5

# In[9]:


def alphabet_list():
    letters = []
    for x in range(26):
        letters.append(chr(65+x))
        
    letters.append('Æ')
    letters.append('Ø')
    letters.append('Å')
    return letters

def k_shift_cipher_decode(cipher, shift):
    trimmedCipher = cipher.replace(" ", "").upper()
    
    letters = alphabet_list()

    decoded_string = ""
    for letter in trimmedCipher:
        index = (letters.index(letter) + shift) % len(letters)
        decoded_string += letters[index]
    return decoded_string

print("Dekodet cipher er:")
decoded_string = k_shift_cipher_decode("YÆVFB VBVFR ÅVBV", 12)
print(decoded_string)


# ### Task 6

# ##### Formell definisjon:  
# E_K(x1,x2...x_b) --> (x1+ k1, x2+k2, .... x_b+k_b)(mod N)  
# D_K(y1,y2...y_b) --> (y1-k1, y2-k2, .... y_b-k_b)(mod N)  
# 
# ##### Det finnes N^B forskjellige nøkler for chiffret, hvor B er blokk lengden og N er alfabet med lengde N.

# ### Task 7   - Vigenere chiffer

# #### a)

# In[10]:


def create_vigenere_table():
    table = np.zeros((29,29), 'U1')

    letters = [chr(65+x) for x in range(26)]
    letters.append('Æ')
    letters.append('Ø')
    letters.append('Å')

    for row in range(len(letters)):
        for col in range(len(letters)):
            charToAdd = (col+row) % len(letters)
            table[row][col] = letters[charToAdd]
            
    return (table, letters)

def encrypt(cleartext, key):
    table, letters = create_vigenere_table()
    cipher = ""

    for i, letter in enumerate(cleartext):
        indexCol = letters.index(letter)
        indexRow = letters.index(key[i % len(key)])
        cipher += table[indexRow][indexCol]
    return cipher

def decrypt(cipher, key):
    table, letters = create_vigenere_table()
    cleartext = ""

    for i, letter in enumerate(cipher):
        indexCol = letters.index(letter)
        indexRow = letters.index(key[i % len(key)])
        cleartext += letters[(indexCol - indexRow) % len(letters)]
    return cleartext
    
cleartext = "Nå er det snart helg".replace(" ", "").upper()
key = "torsk".upper()


cipher = encrypt(cleartext, key)
print("Encrypted cipher: ", cipher)

decrypted = decrypt(cipher, key)
print("Decrypted cleartext:", decrypted)
    


# #### b)

# In[11]:


cipher_b = "QZQOBVCAFFKSDC"
key_b = "BRUS"
decrypted_b = decrypt(cipher_b, key_b)
print("Dekrypter tekst er=",decrypted_b)


# #### c)

# 29^5 = 20511149 antall mulig nøkler med en lengde 5 på nøkkelen

# ### Task 8

# #### a)

# In[12]:


K = np.array([[11, 8], [3, 7]])
z_29 = multiplication_table_mod(1,29)
K_inv = inverse_matrix_over_a(K, z_29)
print("K invers over z_29:")
print(K_inv)


# #### b)

# In[63]:


def vectorize_string(text):
    vector = []
    for letter in text:
        indexLetter = letters.index(letter)
        vector.append(indexLetter)
    vector = np.array(vector)
    return vector

def gcdExtended(a, b):  
    # Base Case  
    if a == 0 :   
        return b,0,1
             
    gcd,x1,y1 = gcdExtended(b%a, a)  
     
    # Update x and y using results of recursive  
    # call  
    x = y1 - (b//a) * x1  
    y = x1  
     
    return gcd,x,y 
    
def encrypt_hill(cleartext, k_matrix, key_size=2):
    if(len(cleartext)%2 == 0):
        cleartext_vector = vectorize_string(cleartext).reshape((-1, key_size))
    else:
        cleartext_vector = vectorize_28string(cleartext+"A").reshape((-1, key_size))
    cipher = ""
    
    e_list = []
    for vector in cleartext_vector:
        encryption = (vector@k_matrix)%len(letters)
        e_list.append(encryption)
    
    for row in e_list:
        for column in row:
            cipher += letters[column%len(letters)]
            
    return cipher

def decrypt_hill(cipher, K, key_size=2):
    K_inv = inverse_matrix_over_a(K, multiplication_table_mod(1,29))
    if(K_inv is not None):
        K_det = round(np.linalg.det(K))%len(letters)
        
        if(len(cipher)%2 == 0):
            cipher_vector = vectorize_string(cipher).reshape((-1, key_size))
        else:
            cipher_vector = vectorize_string(cipher+"A").reshape((-1, key_size))
            
        x = gcdExtended(K_det, len(letters))[1]
        d_list = []
        
        for vector in cipher_vector:
            decryption = (vector@(K_inv*x))% len(letters)
            d_list.append(decryption)

        cleartext = ""
        for row in d_list:
            for column in row:
                cleartext += letters[int(round(column))%len(letters)]
        return cleartext
    else:
        return ""


# In[64]:


letters = alphabet_list()

cleartext_hill= "prim".upper()
cipher_hill_text = encrypt_hill(cleartext_hill, K)
print("Encrypted cipher is:", cipher_hill_text)

#Testing with decrypt
decryptHillText = decrypt_hill(cipher_hill_text, K)
print("Decrypted text is:", decryptHillText)


# #### c)

# In[65]:


cipher = "TOYYSN"
cleartext_c = decrypt_hill(cipher, K)
print(cleartext_c)


# #### d)

# In[70]:


CRACKED_KEY = np.array([[2,14], [19,5]])
plain = "EASY"
found_cipher = "IØÅY"

encrypted_cipher = encrypt_hill(plain, CRACKED_KEY)
decrypt = decrypt_hill(found_cipher, CRACKED_KEY)

print("Decrypted with cracked key:", decrypt, ", Found plaintext:", plain)

print("Found cipher:", found_cipher, ", Encrypted_cipher:", encrypted_cipher)


# SE NOTATBOK FOR FREMGANGSMÅTE FOR OPPGAVE D

# In[73]:


#brute force find key
found_key = False
brute_key = np.array([])
for a in range(21):
    if found_key: break
    for b in range(21):
        if found_key: break
        for c in range(21):
            if found_key: break
            for d in range(21):
                testKey = np.array([[a, b], [c, d]])
                if(decrypt_hill(found_cipher, testKey) == plain):
                    print("Found key")
                    print(testKey)
                    found_key = True
                    brute_key = found_key
                    break


# In[ ]:




