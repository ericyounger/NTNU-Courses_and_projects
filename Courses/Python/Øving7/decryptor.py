'''Decryptor.py is written by Eric Younger to recover the private key given a public key,
and the first letter of the decrypted message.
decryptor.py, uses Donn Morrison's adapted RSA implementation methods from the file rsa.py
'''

import rsa

'''This methods decrypts the private key, given the public key 
and the first letter of the decrypted message.

Input: Nothing, uses global variables in main.
Returns: Nothing, but a print-out whenever it found a possible decrypted message.
'''
def decryptPrivateKey():
    primes = rsa.PrimeGen(100) # this needs to be adjusted if higher encryption
    possibleN = []
    for x in primes:
        for y in primes:
            if(x*y == public_key[1]): # if x*y equals N, add to list.
                possibleN.append([x,y])

    try:
        for x in range(0, len(possibleN)):
            phi = (possibleN[x][0]-1) * (possibleN[x][1]-1)
            invers = rsa.multiplicative_inverse(public_key[0], phi)
            message = rsa.decrypt((invers, public_key[1]) ,cipher_text)
            if(message.startswith("h")):
                d = invers #possible 'd' out of (d, n) private key.
                print("Decrypted message:\n" + message)
                print("Private key is: ({},{})\n ".format(d,public_key[1]))

    except ValueError:
        pass


if __name__ == "__main__":
    public_key = (29815, 100127)
    cipher_text = [84620, 66174, 66174, 5926, 9175, 87925, 54744, 54744, 65916, 79243, 39613, 9932, 70186, 85020, 70186, 5926, 65916, 72060, 70186, 21706, 39613, 11245, 34694, 13934, 54744, 9932, 70186, 85020, 70186, 54744, 81444, 32170, 53121, 81327, 82327, 92023,
               34694, 54896, 5926, 66174, 11245, 9175, 54896, 9175, 66174, 65916, 43579, 64029, 34496, 53121, 66174, 66174, 21706, 92023, 85020, 9175, 81327, 21706, 13934, 21706, 70186, 79243, 9175, 66174, 81327, 5926, 74450, 21706, 70186, 79243, 81327, 81444, 32170, 53121]

    decryptPrivateKey()
    