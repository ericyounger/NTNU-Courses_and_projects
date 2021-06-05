import math

def archimedesPI(n, s):
    for x in range(0, 30):
        s2 = s / 2 # midpoint of each triangles side
        a = math.sqrt(1 - math.pow(s2, 2)) # length from origo to midpoint
        b = 1 - a                       # sidelength from midpoint of triangle to circle bound
        newS = math.sqrt(math.pow(b, 2) + math.pow(s2, 2)) # pythagoras to calculate side length
        p = n * s
        pi = p / 2
        print(x, "   :", pi) # prints pi approximation for X no of times.
        n *= 2 # doubles the amount of triangles
        s = newS  # changing sidelength as n grows



if __name__ == '__main__':
    n = 6 # no of triangles to start with
    s = 1 # sidelength of triangle
    archimedesPI(n, s) # Method for approximating PI using the archimedes method. N is the starting no of triangles, and s i the sidelength of the triangle