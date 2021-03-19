import matplotlib.pyplot as plt
import torch
import pandas as pd


class LinearRegressionModel:
    def __init__(self):
        # requires_grad enables calculation of gradients.
        self.W = torch.tensor([[0.0], [0.0]], requires_grad=True)
        self.b = torch.tensor([[0.0]], requires_grad=True)

    # Predictor
    def f(self, x):
        return x @ self.W + self.b

    # Uses Mean Squared Error
    def loss(self, x, y):
        return torch.nn.functional.mse_loss(self.f(x), y)


# Read in data
data = pd.read_csv("day_length_weight.csv")


# Put x1, x2 into list
x_data = [data["length"].tolist(), data["weight"].tolist()]
y_data = data["day"].tolist()

# Put into tensor object
x_train = torch.tensor(x_data, dtype=torch.float).t()
y_train = torch.tensor(y_data, dtype=torch.float).t().reshape(-1, 1)

epochs = 65000
learning_rate = 0.0001

model = LinearRegressionModel()  # Create object of model to use with optimizer
optimizer = torch.optim.SGD([model.b, model.W], learning_rate)

for epoch in range(epochs):
    model.loss(x_train, y_train).backward()  # Compute loss gradients
    optimizer.step()  # Perform optimization by adjusting W and b,
    optimizer.zero_grad()  # Clear gradients for next step


# Print model variables and loss
print("W = %s, b = %s, loss = %s" % (model.W, model.b, model.loss(x_train, y_train)))

# VISUALIZATION
# Set title for window
fig = plt.figure().gca(projection='3d')

# Plot data points
fig.scatter(data["length"].tolist(), data["weight"].tolist(), data["day"].tolist(), c='red')

# Plot Regression line
fig.scatter(data["length"].tolist(), data["weight"].tolist(), model.f(x_train).detach(), label='$y = f(x) = xW+b$')


#Set axis labels.
fig.set_xlabel('Length cm')
fig.set_ylabel('Weight kg')
fig.set_zlabel('Age in days')

plt.show()