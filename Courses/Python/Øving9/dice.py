import random
import turtle

class Dice():
    """Dice class can roll a dice and draw the dice with it's value
    uses turtle to draw the die, and random for giving a random value.
    """

    def __init__(self, xPos, yPos, size):
        """Dice constructor, sets up drawing enviroment and user set variables to
        get a custom dice.
        Input: takes X-position, Y-position and size of die
        """
        self.xPos = xPos
        self.yPos = yPos
        self.size = size
        self.rolledValue = None
        self.dieColor = "white"
        self.dotColor = "black"
        self.penSize = 4

        # Setup drawing environment
        turtle.setup(500, 500)
        self.bob = turtle.Turtle()
        self.bob.speed(0)
        self.bob.pensize(1)
        self.bob.hideturtle()

    def getPosition(self):
        """getPosition() gets the position of the corner of a dice
        Returns: a list of x and y position, ie [x,y]
        """
        return [self.xPos, self.yPos]

    def getSize(self):
        """getSize() gets the size of the dice
        returns an integer of the size of the dice"""
        return self.size

    def rollDice(self):
        """ rollDice() rolls a dice, and sets its own dice value to a random integer
        between 1-6
        """
        self.rolledValue = random.randrange(1, 7)

    def getRolledValue(self):
        """getRolledValue() gives the value of the top of the die if the die has been rolled
        returns: if dice has been rolled, returns the value of the die (integer)
        """
        #If returns true than dice has been rolled
        if self.rolledValue:
            return self.rolledValue

    def setDieColor(self, dieColor, dotColor):
        """setDieColor() sets the color of the die and the dots on it, by default
        the die has the standard color of white and black, this method sets new colors

        Input:
        dieColor in the form of a string, ie "orange"
        dotColor in the form of a string, ie "black"
        """
        self.dieColor = dieColor
        self.dotColor = dotColor

    def drawDice(self):
        """drawDice() is a public method that draws the dice with its value as long as the dice
        has been rolled, if not it raises a Exception
        """
        rolled = self.getRolledValue()
        if rolled:
            self.__drawDiceEdges()

            # Draw value of rolled dice depending on variable
            if rolled == 1:
                self.__drawOne()

            elif rolled == 2:
                self.__drawTwo()

            elif rolled == 3:
                self.__drawThree()

            elif rolled == 4:
                self.__drawFour()

            elif rolled == 5:
                self.__drawFive()

            elif rolled == 6:
                self.__drawSix()
        else:
            raise Exception("Dice has not been rolled")


    def __drawDiceEdges(self):
        """__drawDiceEdges() is a private method used to draw the corners of the dice"""
        self.bob.fillcolor(self.dieColor)
        self.bob.begin_fill()
        self.bob.penup()
        self.bob.goto(self.getPosition())
        self.bob.pendown()

        for x in range(0, 4):
            self.bob.forward(self.getSize())
            self.bob.right(90)

        self.bob.end_fill()

    def __drawOne(self):
        """__drawOne() is a private method used to draw the value one on the dice"""
        self.bob.pensize(self.penSize)
        self.bob.color(self.dotColor)
        self.bob.penup()
        self.bob.goto(self.getPosition()[0]+self.getSize()/2, self.getPosition()[1]-self.getSize()/2)
        self.bob.pendown()
        self.bob.dot()



    def __drawTwo(self):
        """__drawTwo() is a private method used to draw the value two on the dice"""
        self.bob.pensize(self.penSize)
        self.bob.color(self.dotColor)
        spacing = self.getSize() / 5
        self.bob.penup()
        self.bob.goto(self.getPosition()[0]+self.getSize()/2-spacing, self.getPosition()[1]-self.getSize()/2)
        self.bob.pendown()
        self.bob.dot()
        self.bob.penup()
        self.bob.forward(spacing*2)
        self.bob.pendown()
        self.bob.dot()


    def __drawThree(self):
        """__drawThree() is a private method used to draw the value three on the dice"""
        self.bob.pensize(self.penSize)
        self.bob.color(self.dotColor)
        spacing = self.getSize()/5
        self.bob.penup()
        self.bob.goto(self.getPosition()[0]+self.getSize()/2,self.getPosition()[1]-self.getSize()/2)
        self.bob.pendown()
        self.bob.dot()
        self.bob.penup()
        self.bob.left(90)
        self.bob.forward(spacing)
        self.bob.pendown()
        self.bob.dot()
        self.bob.penup()
        self.bob.right(180)
        self.bob.forward(spacing*2)
        self.bob.pendown()
        self.bob.dot()

    def __drawFour(self):
        """__drawFour() is a private method used to draw the value four on the dice"""
        self.bob.pensize(self.penSize)
        self.bob.color(self.dotColor)
        spacing = self.getSize()/5
        self.bob.penup()
        self.bob.goto(self.getPosition()[0]+self.getSize()/2-spacing, self.getPosition()[1]-self.getSize()/2+spacing)
        self.bob.dot()
        self.bob.penup()
        self.bob.forward(spacing*2)
        self.bob.pendown()
        self.bob.dot()

        for x in range(0,2):
            self.bob.penup()
            self.bob.right(90)
            self.bob.forward(spacing*2)
            self.bob.pendown()
            self.bob.dot()

    def __drawFive(self):
        """__drawFive() is a private method used to draw the value five on the dice"""
        self.bob.pensize(self.penSize)
        self.bob.color(self.dotColor)
        spacing = self.getSize()/5
        self.bob.penup()
        self.bob.goto(self.getPosition()[0]+self.getSize()/2, self.getPosition()[1]-self.getSize()/2)
        self.bob.pendown()
        self.bob.dot()

        self.bob.penup()
        self.bob.forward(spacing)
        self.bob.left(90)
        self.bob.forward(spacing)
        self.bob.pendown()
        self.bob.dot()

        for x in range(0,3):
            self.bob.penup()
            self.bob.left(90)
            self.bob.forward(spacing*2)
            self.bob.pendown()
            self.bob.dot()


    def __drawSix(self):
        """__drawSix() is a private method used to draw the value six on the dice"""
        self.bob.pensize(self.penSize)
        self.bob.color(self.dotColor)
        spacing = self.getSize()/5
        self.bob.penup()
        self.bob.goto(self.getPosition()[0] + self.getSize() / 2, self.getPosition()[1] - self.getSize() / 2)
        self.bob.forward(spacing)
        self.bob.pendown()
        self.bob.dot()

        self.bob.penup()
        self.bob.left(90)
        self.bob.forward(spacing)
        self.bob.pendown()
        self.bob.dot()

        self.bob.penup()
        self.bob.right(180)
        self.bob.forward(spacing*2)
        self.bob.pendown()
        self.bob.dot()

        self.bob.penup()
        self.bob.right(90)
        self.bob.forward(spacing*2)
        self.bob.right(90)
        self.bob.pendown()
        self.bob.dot()

        for x in range(0,2):
            self.bob.penup()
            self.bob.forward(spacing)
            self.bob.pendown()
            self.bob.dot()





if __name__ == "__main__":
    xPos = int(input("X-position of corner of die\n"))
    yPos = int(input("Y-position of corner of die\n"))
    size = int(input("size of die, preferably 50 or above\n"))

    #create instance of dice
    dice = Dice(xPos,yPos, size)

    #sett dice colors
    dice.setDieColor("red", "white")

    #Roll the dice
    dice.rollDice()

    #draw dice
    dice.drawDice()

    # holds the image after finished drawing
    turtle.done()
