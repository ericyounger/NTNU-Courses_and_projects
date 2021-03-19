import csv
import matplotlib.pyplot as plt
import torch
import math
import numpy as np

class NonLinearRegressionModel:
    def __init__(self):
        # requires_grad enables calculation of gradients.
        self.W = torch.tensor([[0.0]], requires_grad=True)
        self.b = torch.tensor([[0.0]], requires_grad=True)

    # Predictor
    def f(self, x):
        return 20 * torch.sigmoid(x @ self.W + self.b) + 31

    # Uses Mean Squared Error
    def loss(self, x, y):
        return torch.mean(torch.square(self.f(x) - y))


def read_csv():
    # READ IN DATA FROM CSV FILE.
    x_data = []
    y_data = []
    with open('day_head_circumference.csv') as csvfile:
        data = csv.reader(csvfile, delimiter=',')
        line_count = 0
        for row in data:
            if line_count == 0:
                line_count += 1
                continue
            else:
                x_data.append(float(row[0]))
                y_data.append(float(row[1]))

    # returns tuple with both x and y data sets.
    return (x_data, y_data)


(x_data,y_data) = read_csv()

# Fit x and y data to proper dimensions.
x_train = torch.reshape(torch.tensor(x_data), (-1, 1))
y_train = torch.reshape(torch.tensor(y_data), (-1, 1))

epochs = 50000
learning_rate = 0.0000001

model = NonLinearRegressionModel()  # Create object of model to use with optimizer
optimizer = torch.optim.SGD([model.b, model.W], learning_rate)

for epoch in range(epochs):
    model.loss(x_train, y_train).backward()  # Compute loss gradients
    optimizer.step()  # Perform optimization by adjusting W and b,
    optimizer.zero_grad()  # Clear gradients for next step


# VISUALIZATION OF DATA

# Print model variables and loss
print("W = %s, b = %s, loss = %s" % (model.W, model.b, model.loss(x_train, y_train)))

# Plot data points
plt.plot(x_train, y_train, 'o', label='$(\\hat x^{(i)},\\hat y^{(i)})$')

# set x-label
plt.xlabel('x')
# set y-label
plt.ylabel('y')

x, indices = torch.sort(x_train, 0)
plt.plot(x, model.f(x).detach(), label='$y = f(x) = 20*σ(xW+b)+31$', c="red")

plt.legend()
plt.show()
