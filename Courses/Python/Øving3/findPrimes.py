
def findPrimes(n):
    for x in range(2, n):
        if(checkIfPrime(x)):
            print(x)  # prints the number if it's a prime number


def checkIfPrime(number):
    prime = True  # true until proven otherwise
    # no need to start on 1, since all numbers are divisble by 1, only need to check up to the number divived by 2.
    for x in range(2, int(number/2+1)):
        if(number % x == 0):  # number will never reach number so it will never divide by itself
            prime = False
            break  # break the loop, not a prime number
    if prime:  # if checked true, then it was never able to divide by any other number
        return True
    else:
        return False


if __name__ == "__main__":
    findPrimes(1000)
