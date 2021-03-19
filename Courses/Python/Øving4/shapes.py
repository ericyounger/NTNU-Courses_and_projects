import turtle
import math


def getPoints(intervals, radius):
    points = []
    for x in range(intervals):
        points.append(
            [math.sin(x * (2 * math.pi) / intervals) * radius, math.cos(x * (2 * math.pi) / intervals) * radius])
    return points


def multiplication(points, multiplier):
    sum = []
    for x in range(0, len(points)):
        sum.append(x * multiplier)
    return sum


def multiplicationGen(points, multiplier):
    for x in range(0, len(points)):
        yield x * multiplier


def drawVisualization(bob, points, sumArr):
    bob.circle(radius)
    for x in range(0, len(points)):
        bob.penup()
        bob.goto(points[x])
        bob.pendown()
        bob.goto(points[sumArr[x] % intervals])


def drawVisualizationWithGen(bob, points, sumGen):
    bob.circle(radius)
    for x, y in zip(points, sumGen):
        bob.penup()
        bob.goto(x)
        bob.pendown()
        bob.goto(points[y % intervals])


if __name__ == "__main__":
    '''Setup environment for turtle'''
    turtle.setup(500, 500)  # window screen
    bob = turtle.Turtle()  # bob the turtle
    bob.speed(0)  # max speed
    bob.penup()
    bob.goto(0, -200)  # Offset to have origo in the middle of the screen
    bob.pendown()

    '''Init values'''
    intervals = 128
    radius = 200
    multiplier = 2
    positions = getPoints(intervals, radius)

    '''Draw visualization with a ordinary list, uncomment to use'''
    #sum = multiplication(positions, multiplier)
    #drawVisualization(bob, positions, sum)

    '''Draw visualization with generator object, uncomment to use'''
    #sumGen = multiplicationGen(positions, multiplier)
    #drawVisualizationWithGen(bob, positions, sumGen)

    turtle.done()  # holds the display open after finished drawing
