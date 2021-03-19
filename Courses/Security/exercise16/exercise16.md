# Exercise 16

### Task 1a)

#### 1.


```python
import math
import numpy as np

def LSFR(K, modN):
    strK = str(K)
    firstBin = strK
    period = 0
    counter = 0;
    repeatingPattern = ""
    
 
    while(True):
        strK += str((int(strK[counter]) + int(strK[counter+1])+ int(strK[counter+2]) + int(strK[counter+3]))%modN)
        try:
            index = strK.index(firstBin, len(K))
            return (strK, index)
        except:
            pass     
        counter += 1
 
    
    
K= str(1000)
modN = 2

bits, period = LSFR(K, modN)
print("Pattern is:", bits)
print("Period of K is:", period)


```

    Pattern is: 100011000
    Period of K is: 5


#### 2.


```python
K = "0011"
modN = 2
bits, period = LSFR(K, modN)
print("Pattern is:", bits)
print("Period of K is:", period)
```

    Pattern is: 001100011
    Period of K is: 5


#### 3.


```python
K = "1111"
modN = 2
bits, period = LSFR(K, modN)

print("Pattern is:", bits)
print("Period of K is:", period)
```

    Pattern is: 111101111
    Period of K is: 5


### 1b)


```python
def LSFR_altered(K, modN):
    strK = str(K)
    firstBin = strK
    period = 0
    counter = 0;
    repeatingPattern = ""
    
    while(True):
        strK += str((int(strK[counter]) + int(strK[counter+3]))%modN)
        try:
            index = strK.index(firstBin, len(K))
            return (strK, index)
        except:
            pass

        counter += 1

```

#### 1.


```python
K= str(1000)
modN = 2

bits, period = LSFR_altered(K, modN)
print("Pattern is:", bits)
print("Period of K is:", period)

```

    Pattern is: 1000111101011001000
    Period of K is: 15


#### 2.


```python
K = "0011"
modN = 2
bits, period = LSFR_altered(K, modN)
print("Pattern is:", bits)
print("Period of K is:", period)
```

    Pattern is: 0011110101100100011
    Period of K is: 15


#### 3.


```python
K = "1111"
modN = 2
bits, period = LSFR_altered(K, modN)

print("Pattern is:", bits)
print("Period of K is:", period)
```

    Pattern is: 1111010110010001111
    Period of K is: 15


### Task 2a)


```python
def alphabet_list():
    letters = []
    for x in range(26):
        letters.append(chr(65+x))
        
    letters.append('Æ')
    letters.append('Ø')
    letters.append('Å')
    return letters

def autokey_encrypt(plaintext, K):
    letters = alphabet_list()
    letter_to_numbers = []
    plaintext_upper = plaintext.upper()
    
    for letter in plaintext_upper:
        index = letters.index(letter)
        letter_to_numbers.append(index)
        
    cipher_numbers = []
    for i, val in enumerate(letter_to_numbers):
        if(i==0):
            cipher_numbers.append((val+K)%len(letters))
        else:
            cipher_numbers.append((val+letter_to_numbers[i-1])%len(letters))
            
    cipher = ""
    
    for number in cipher_numbers:
        cipher += letters[number]

    return cipher

def autokey_decrypt(cipher, K):
    letters = alphabet_list()
    letter_to_numbers = cipher
        
    plaintext_numbers = []
    previous_value = 0
    for i, val in enumerate(letter_to_numbers):
        if(i==0):
            plaintext_numbers.append((val-K)%len(letters))
            previous_value=(val-K)%len(letters)
        else:
            plaintext_numbers.append((val-previous_value)%len(letters))
            previous_value=(val-previous_value)%len(letters)
    
    plaintext = ""
    
    for number in plaintext_numbers:
        plaintext += letters[number]

    return plaintext
    
        
```


```python
plaintext="goddag"
K = 17
cipher = autokey_encrypt(plaintext, K)
print("Encrypted cipher is:", cipher)
```

    Encrypted cipher is: XURGDG


### Task 2b)


```python
cipher = [23, 8, 23, 12, 21, 2, 4, 3, 17, 13, 19]
K = 5

plaintext = autokey_decrypt(cipher, K)
print(plaintext)
```

    STEINSPRANG


### Task 3a)


```python
def H(x):
    h = bin(int(math.pow(x, 2) % math.pow(2,8))).replace("0b", "")
    h_inner = h[2:len(h)-2]
    return h_inner

def HMAC_HASH(message, K):
 
    ipad_dec = int("0011", 2)

    opad_dec = int("0101", 2)
    k_dec = int(K, 2) #convert binary to decimal
    
    #k_dec ^ipad_dec is XOR on the bits, just in integer form.
    #bin gives out binary representation of integers.
    inner_key = bin(k_dec ^ ipad_dec).replace("0b", "")
    outer_key = bin(k_dec ^ opad_dec).replace("0b", "")
    
    #Inner HMAC
    inner_hash = inner_key+message
    inner_hash_dec = int(inner_hash, 2)
    h_inner = H(inner_hash_dec)
    
    #Outer HMAC
    outer_hash = outer_key+h_inner
    outer_hash_dec = int(outer_hash,2)
    h_outer = H(outer_hash_dec)
    return h_outer




K = "1001"
message= "0110"

hmac_hash = HMAC_HASH(message, K)
print("Generert HMAC-HASH er:", hmac_hash)


```

    Generert HMAC-HASH er: 0100


### 3b)


```python
message_received = "0111"
hmac_received = "0100"
gen_received_hmac = HMAC_HASH(message_received, K)
print("Mottat HMAC:", hmac_received, " Generert HMAC:", gen_received_hmac)
```

    Mottat HMAC: 0100  Generert HMAC: 0100


Siden mottatt HMAC og generert HMAC er det samme så er det ingen grunn til å tro at meldingen ikke er autentisk.

### 4)


```python
def Cesar_CBC_MAC(x):
    letters = alphabet_list()
    IV = int("0000",2)
    y_blocks = []

    y = IV
    for block in x:
        block_int = int(block,2)
        y = int((int(y ^ block_int)+3) % math.pow(2,4))
        y_blocks.append(y)
        
    return y_blocks
        
x = ["1101", "1111", "1010", "0001"]
x_merket = ["0010", "1100", "0001", "1111"]

x_res = Cesar_CBC_MAC(x)
x_merket_res = Cesar_CBC_MAC(x_merket)

print("CBC-MAC-CÆSAR:",x_res)
print("CBC-MAC-CÆSAR:",x_merket_res)
```

    CBC-MAC-CÆSAR: [0, 2, 11, 13]
    CBC-MAC-CÆSAR: [5, 12, 0, 2]


### 5a)


```python
s_box = [
    [0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76],
    [0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0],
    [0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15],
    [0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75],
    [0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84],
    [0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF],
    [0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8],
    [0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2],
    [0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73],
    [0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB],
    [0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79],
    [0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08],
    [0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A],
    [0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E],
    [0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF],
    [0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16],
]

inv_s_box = [
    [0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB],
    [0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB],
    [0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E],
    [0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25],
    [0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92],
    [0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84],
    [0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06],
    [0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B],
    [0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73],
    [0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E],
    [0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B],
    [0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4],
    [0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F],
    [0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF],
    [0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61],
    [0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D],
]

s_np = np.array(s_box)
s_inv_np = np.array(inv_s_box)


def addRoundKey(x, privateKey):
    arr = []
    
    for i, (row1, row2) in enumerate(zip(x, privateKey)):
        for j, (col1, col2) in enumerate(zip(row1, row2)):
            msgCol = int(col1, 16)
            keyCol = int(col2, 16)
            
            xor = hex(msgCol^keyCol)[2:]
            if(len(xor) == 1):
                arr.append("0x0"+xor)
            else:
                arr.append("0x"+ xor)
            
    matrix = np.array(arr).reshape((4,4))
    return matrix

def subBytes(x):
    arr = []
    
    for row in x:
        for col in row:
            if(len(col[2:]) == 1):
                colIndex = int(col[2:], 16)
                temp = hex(s_np[0][colIndex])[2:]
                if(len(temp) == 1):
                    arr.append("0x0"+temp)
                else:
                    arr.append("0x"+temp)
            else:
                rowIndex = int(col[2:][0], 16)
                colIndex = int(col[2:][1], 16)
                temp = hex(s_np[rowIndex][colIndex])[2:]
                if(len(temp) == 1):
                    arr.append("0x0"+temp)
                else:
                    arr.append(hex(s_np[rowIndex][colIndex]))
          
           
    matrix = np.array(arr).reshape((4,4))
    return matrix

def reverseSubBytes(x):
    arr = []
    
    for row in x:
        for col in row:
            if(len(col[2:]) == 1):
                colIndex = int(col[2:], 16)
                temp = hex(s_inv_np[0][colIndex])[2:]
                if(len(temp) == 1):
                    arr.append("0x0"+temp)
                else:
                    arr.append("0x"+temp)
            else:
                rowIndex = int(col[2:][0], 16)
                colIndex = int(col[2:][1], 16)
                temp = hex(s_inv_np[rowIndex][colIndex])[2:]
                if(len(temp) == 1):
                    arr.append("0x0"+temp)
                else:
                    arr.append(hex(s_inv_np[rowIndex][colIndex]))
          
           
    matrix = np.array(arr).reshape((4,4))
    return matrix
            
     
def shiftRows(x):
    array = ["        " for i in range(16)]
    matrix = np.array(array).reshape((4,4))

    rowArr = []
    for row in range(len(x)):
        for col in range(len(x)):
            if(row == 0):
                if(len(x[row][col][2:]) ==1):
                    matrix[row][col] = "0x0"+x[row][col][2:]
                else: 
                    matrix[row][col] = x[row][col]
            else:
                shiftIndex = (col+row)%len(x)
                temp = x[row][shiftIndex]
                if(len(temp[2:]) == 1):
                    matrix[row][col] = "0x0"+temp[2:]
                else:
                    matrix[row][col] = x[row][shiftIndex]
    return matrix
                
    
def reverseShift(x):
    array = ["        " for i in range(16)]
    matrix = np.array(array).reshape((4,4))

    rowArr = []
    for row in range(len(x)):
        for col in range(len(x)):
            if(row == 0):
                if(len(x[row][col][2:]) ==1):
                    matrix[row][col] = "0x0"+x[row][col][2:]
                else: 
                    matrix[row][col] = x[row][col]
            else:
                shiftIndex = (col-row)%len(x)
                temp = x[row][shiftIndex]
                if(len(temp[2:]) == 1):
                    matrix[row][col] = "0x0"+temp[2:]
                else:
                    matrix[row][col] = x[row][shiftIndex]
    return matrix 

def AES_Encrypt(plaintext, privateKey):
    np_plain = np.array(plaintext).reshape((4,4)).T
    np_privateKey = np.array(privateKey).reshape((4,4)).T
        
    round_ = addRoundKey(np_plain, np_privateKey)
    sub_ = subBytes(round_)
    shift_ = shiftRows(sub_)
    return shift_.reshape((1,-1)).squeeze()
    
    
def AES_Decrypt(cipher, privateKey):
    np_cipher = np.array(cipher).reshape((4,4)).T
    np_privateKey = np.array(privateKey).reshape((4,4)).T
    
    rev_shift = reverseShift(np_cipher)
    rev_sub = reverseSubBytes(rev_shift)
    round_ = addRoundKey(rev_sub, np_privateKey)
    return round_.reshape((1,-1)).squeeze()
        
startKeyArr = "67 71 35 c4 ff da e5 ff 1c 54 e1 fd 7f 2e 88 b7".split(" ")
messageArr = "24 59 66 0c 99 da 9b 00 d6 55 fd 20 e9 ff 46 95".split(" ")


encrypted = AES_Encrypt(messageArr, startKeyArr)
print("Encrypted:")
print(encrypted)
#listToASCII(encrypted)



```

    Encrypted:
    ['0x1a' '0x33' '0x74' '0x90' '0x63' '0x7c' '0x3e' '0x34' '0x9c' '0x8b'
     '0xed' '0xf3' '0x93' '0xe8' '0x16' '0xc1']


### 5b)



```python
#TODO: FIX TRANPOSE SO THAT IT WORKS FOR EVERYTHING, ASSIGNMENT STILL FINISHED THOUGH.

cipher = "0x26 0xFA 0x83 0xE7 0x2D 0xCD 0x5D 0xB8 0xC4 0xDC 0xEB 0x12 0x70 0xCF 0xD6 0x1E".split(" ")
decrypted = AES_Decrypt(cipher, startKeyArr)
print("Decrypted:")
print(decrypted)
```

    Decrypted:
    ['0x44' '0x05' '0x94' '0xaf' '0x2e' '0xce' '0xd4' '0xbd' '0x09' '0xaf'
     '0xa0' '0x05' '0x5e' '0xc6' '0x14' '0x07']


### 6)


```python
Rcon = [
"0x8d", "0x01", "0x02", "0x04", "0x08", "0x10", "0x20", "0x40", "0x80", "0x1b", "0x36", "0x6c", "0xd8", "0xab", "0x4d", "0x9a", 
"0x2f", "0x5e", "0xbc", "0x63", "0xc6", "0x97", "0x35", "0x6a", "0xd4", "0xb3", "0x7d", "0xfa", "0xef", "0xc5", "0x91", "0x39", 
"0x72", "0xe4", "0xd3", "0xbd", "0x61", "0xc2", "0x9f", "0x25", "0x4a", "0x94", "0x33", "0x66", "0xcc", "0x83", "0x1d", "0x3a", 
"0x74", "0xe8", "0xcb", "0x8d", "0x01", "0x02", "0x04", "0x08", "0x10", "0x20", "0x40", "0x80", "0x1b", "0x36", "0x6c", "0xd8", 
"0xab", "0x4d", "0x9a", "0x2f", "0x5e", "0xbc", "0x63", "0xc6", "0x97", "0x35", "0x6a", "0xd4", "0xb3", "0x7d", "0xfa", "0xef", 
"0xc5", "0x91", "0x39", "0x72", "0xe4", "0xd3", "0xbd", "0x61", "0xc2", "0x9f", "0x25", "0x4a", "0x94", "0x33", "0x66", "0xcc", 
"0x83", "0x1d", "0x3a", "0x74", "0xe8", "0xcb", "0x8d", "0x01", "0x02", "0x04", "0x08", "0x10", "0x20", "0x40", "0x80", "0x1b", 
"0x36", "0x6c", "0xd8", "0xab", "0x4d", "0x9a", "0x2f", "0x5e", "0xbc", "0x63", "0xc6", "0x97", "0x35", "0x6a", "0xd4", "0xb3", 
"0x7d", "0xfa", "0xef", "0xc5", "0x91", "0x39", "0x72", "0xe4", "0xd3", "0xbd", "0x61", "0xc2", "0x9f", "0x25", "0x4a", "0x94", 
"0x33", "0x66", "0xcc", "0x83", "0x1d", "0x3a", "0x74", "0xe8", "0xcb", "0x8d", "0x01", "0x02", "0x04", "0x08", "0x10", "0x20", 
"0x40", "0x80", "0x1b", "0x36", "0x6c", "0xd8", "0xab", "0x4d", "0x9a", "0x2f", "0x5e", "0xbc", "0x63", "0xc6", "0x97", "0x35", 
"0x6a", "0xd4", "0xb3", "0x7d", "0xfa", "0xef", "0xc5", "0x91", "0x39", "0x72", "0xe4", "0xd3", "0xbd", "0x61", "0xc2", "0x9f", 
"0x25", "0x4a", "0x94", "0x33", "0x66", "0xcc", "0x83", "0x1d", "0x3a", "0x74", "0xe8", "0xcb", "0x8d", "0x01", "0x02", "0x04", 
"0x08", "0x10", "0x20", "0x40", "0x80", "0x1b", "0x36", "0x6c", "0xd8", "0xab", "0x4d", "0x9a", "0x2f", "0x5e", "0xbc", "0x63", 
"0xc6", "0x97", "0x35", "0x6a", "0xd4", "0xb3", "0x7d", "0xfa", "0xef", "0xc5", "0x91", "0x39", "0x72", "0xe4", "0xd3", "0xbd", 
"0x61", "0xc2", "0x9f", "0x25", "0x4a", "0x94", "0x33", "0x66", "0xcc", "0x83", "0x1d", "0x3a", "0x74", "0xe8", "0xcb"
]


def rotWord(x): 
    new_arr = np.roll(x,-1)
    return new_arr



def genRoundKey(orig, rotword, roundNr, xorRcon=False):
    out = []
    for x in range(len(orig)):
        if(x == 0):
            valOrig = int(orig[x], 16)
            valRot = int(rotword[x],16)
            valRcon = int(Rcon[roundNr], 16)
            if(xorRcon):
                roundKey = hex((valOrig^valRot)^valRcon)
            else:
                roundKey = hex(valOrig^valRot)
            out.append(roundKey)
        else:
            valOrig = int(orig[x], 16)
            valRot = int(rotword[x],16)
            valRcon = int("0x00", 16)
            if(xorRcon):
                roundKey = hex((valOrig^valRot)^valRcon)
            else:
                roundKey = hex(valOrig^valRot)
            out.append(roundKey)
    
    return out
    

def stackBeginningKey(array):
    new_arr = []
    
    for x in range(0,len(array), 4):
        new_arr.append([array[x], array[x+1], array[x+2], array[x+3]])
    
    return np.array(new_arr)
    

def keyScheduler(startKey):
    keyColumns = stackBeginningKey(startKey)
    
    genRound = []
    
    for x in range(4):
        if(x == 0):          
            res_rotWord = rotWord(keyColumns[-1])
            res_genRound = genRoundKey(keyColumns[x], res_rotWord, 1, xorRcon=True)
            genRound.append(res_genRound)
        else:
            res_genRound = genRoundKey(keyColumns[x], genRound[-1], 1, xorRcon=False)
            genRound.append(res_genRound)
            
    np_GEN = np.array(genRound).reshape((4,4)).T
    return np_GEN

    
        

key = "2B 7E 15 16 28 AE D2 A6 AB F7 15 88 09 CFhttp://localhost:8888/notebooks/exercise16.ipynb# 4F 3C".split(" ")

keys = keyScheduler(key)
print("Generert rundenøkler, kolonner er ord:")
print(keys)
```

    Generert rundenøkler, kolonner er ord:
    [['0xe5' '0xcd' '0x66' '0x6f']
     ['0x31' '0x9f' '0x68' '0xa7']
     ['0x29' '0xfb' '0xee' '0xa1']
     ['0x1f' '0xb9' '0x31' '0xd']]



```python

```
