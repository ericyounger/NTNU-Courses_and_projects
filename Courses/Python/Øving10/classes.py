''' This code is copied from the video https://youtu.be/HTLu2DFOdTg?list=FLJpj9JO97RXNeiS1yV47URg
 The code is copied and commented as a exercise for a Python Development class
 '''

import math

class Circle(object):
    '''Class Circle inherits from object and serves as the parent for many subclasses,
    but is also able to stand on its own.
    '''

    __slots__ = ['diameter'] # defining variables that objects can have.
    version = '0.1' # class variable

    def __init__(self, radius):
        """init method for the class, sets objects variables on creation"""
        self.radius = radius

    def area(self):
        '''Method that returns the area of the circle (areal)
        Returns: the area of the circle
        '''
        p = self.__perimeter() #class local function reference
        r = p / math.pi / 2.0
        return math.pi * r ** 2.0

    @property # property decorator, serves as a getter method
    def radius(self):
        return self.diameter / 2.0

    @radius.setter #setter decorator for the function below to set radius.
    def radius(self, radius):
        '''Setter method/function for setting the diameter
        Takes: radius = number
        '''
        self.diameter = radius * 2.0

    def perimeter(self):
        '''Metod that returns the perimeter of the circle'''
        return 2.0 * math.pi * self.radius

    @classmethod # alternative constructor
    def from_bbd(cls, bbd):
        '''alternative constructor that returns a instance of a circle.'''
        radius = bbd / 2.0 / math.sqrt(2.0)
        return cls(radius)

    @staticmethod
    def angle_to_grade(angle):
        '''Convert angle in degree to a percentage grade, this is a static
        method that does not need to have a instance of a object to use'''
        return math.tan(math.radians(angle)) * 100.0

    __perimeter = perimeter


class Tire(Circle):
    '''Class Tire inherits from Circle, uses Circle's init method.
    Tire is a subclass of Circle.'''

    def perimeter(self):
        """Overruns the parents perimeter function, and runs its own"""
        return Circle.perimeter(self) * 1.25




if __name__ == "__main__":
    #test methods
    print("Circle tests")
    circle = Circle(30)
    print("Perimeter", circle.perimeter())
    print("Area", circle.area())
    print("Angle to grade, static:",Circle.angle_to_grade(90))
    print("")

    print("Tire tests")
    tire = Tire(30)
    print("Area", tire.area())
    print("Perimeter", tire.perimeter())
    print("Angle to grade, static:", tire.angle_to_grade(90))