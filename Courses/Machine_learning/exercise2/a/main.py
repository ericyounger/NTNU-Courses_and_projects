import torch
import matplotlib.pyplot as plt
import numpy as np


class SigmoidModel:
    def __init__(self):
        # Model variables
        self.W = torch.tensor([[0.]], requires_grad=True)
        self.b = torch.tensor([[0.]], requires_grad=True)

    def logits(self, x):
        return x @ self.W + self.b

    # predictor
    def f(self, x):
        return torch.sigmoid(self.logits(x))

    # Cross Entropy loss
    def loss(self, x, y):
        return torch.nn.functional.binary_cross_entropy_with_logits(self.logits(x), y)


if __name__ == "__main__":
    model = SigmoidModel()

    x_train = torch.tensor([[0.], [1.]])
    y_train = torch.tensor([[1.], [0.]])

    learning_rate = 0.1
    optimizer = torch.optim.SGD([model.W, model.b], learning_rate)

    for epoch in range(10000):
        model.loss(x_train, y_train).backward()  # Compute loss gradients
        optimizer.step()  # perform optimization by adjusting W and b.
        optimizer.zero_grad()  # Clear gradients for the next step.

    # Print model variables and loss
    print("W = %s, b = %s, loss = %s" % (model.W, model.b, model.loss(x_train, y_train)))

    # Test NOT function
    print("Not of 1 is = " + str(torch.round(model.f(torch.tensor([1.])).detach()).item()))  # prints out 0.0

    fig = plt.figure("NOT operator")

    plot1 = fig.add_subplot()

    # Plot data points
    plt.plot(x_train.detach(), y_train.detach(), 'o', label='$(\\hat x^{(i)},\\hat y^{(i)})$')

    # set x and y labels
    plt.xlabel('x')
    plt.ylabel('y')

    out = torch.reshape(torch.tensor(np.linspace(0, 1, 100).tolist()), (-1, 1))

    plot1.set_xticks([0, 1])  # x range from 0 to 1
    plot1.set_yticks([0, 1])  # y range from 0 to 1

    x, indices = torch.sort(out, 0)

    # Plot sigmoid regression curve.
    plt.plot(x, model.f(x).detach())

    plt.show()
