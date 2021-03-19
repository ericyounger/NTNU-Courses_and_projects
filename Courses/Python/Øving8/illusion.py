import turtle
import math
def drawSquareWithDots(bob,xPos, yPos,squareSize,squareColor,gridColor,circleColor):
    """drawSquareWithDots is a helper method used by drawIllusion(), that draws squares filled with a color,
    and then adds dots in every corner of the square.

    Takes:
    bob: takes an instance of turtle class.
    xPos: Starting x-position to draw from
    yPos: Starting y-position to draw from
    squareSize: the size of each rectangle
    squareColor: the fill color for the rectangle
    gridColor: the stroke color for grid/edges of rectangles.
    circleColor: color of the dots in every corner of rectangle.

    Returns:
    This method does not return anything, but is a helper metod that draws on the canvas
    created in drawIllusion().
    """
    bob.penup()
    bob.color(gridColor)
    bob.goto(xPos,yPos)
    bob.fillcolor(squareColor)

    # draws rectangle
    bob.begin_fill()
    bob.pendown()
    for x in range(0,4):
        bob.right(90)
        bob.forward(squareSize)
    bob.end_fill()

    # draws dots
    for x in range(0,4):
        bob.color(circleColor)
        bob.fillcolor(circleColor)
        bob.begin_fill()
        bob.circle(1)
        bob.end_fill()
        bob.penup()
        bob.right(90)
        bob.forward(squareSize)
        bob.pendown()


def drawIllusion(gridColor, squareColor, circleColor, imgSize, squareSize, gridWidth):
    """drawIllusion is a method that draws a grid with colored rectangles that have dots in every corner
    which creates the Hermann grid illusion.

    Input:
    gridColor: color for the gird
    squareColor: color thats filled inside the each rectangle
    circleColor: the dots in every rectangle corner
    imgSize: the size of the window
    squareSize: the size of each rectangle
    gridWidth: the width of the stroke for the rectangles

    Output:
    Draws The Hermann grid illusion using turtle library.
    """

    #Setup drawing environment
    turtle.setup(imgSize, imgSize)
    bob = turtle.Turtle()  # bob the turtle
    bob.speed(0)  # max speed
    bob.pensize(width=gridWidth)

    #draw the illusion using helper method drawSquareWithDots
    for x in range(0,int(math.ceil(imgSize/squareSize))):
        for y in range(0,int(math.ceil(imgSize/squareSize))):
            drawSquareWithDots(bob,(-imgSize/2)+squareSize+(y*squareSize),imgSize/2-(x*squareSize),squareSize,squareColor, gridColor, circleColor)

    #holds the image after it is done drawing
    turtle.done()






if __name__ == "__main__":
    drawIllusion("grey","yellow","blue",500,75,4)

