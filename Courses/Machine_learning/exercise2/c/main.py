import torch
import matplotlib.pyplot as plt
import numpy as np


class SigmoidModel:
    def __init__(self):
        # Model variables
        '''
        Example of XOR that does not converge
        self.W1 = torch.tensor([[0.0, 0.0], [0.0, 0.0]], dtype=torch.float, requires_grad=True)
        self.b1 = torch.tensor([[0.0, 0.0]], dtype=torch.float, requires_grad=True)

        self.W2 = torch.tensor([[0.0], [0.0]], dtype=torch.float, requires_grad=True)
        self.b2 = torch.tensor([[0.0]], dtype=torch.float, requires_grad=True)

        '''


        # XOR that converges
        self.W1 = torch.tensor([[1.0, 0.5], [0.5, 0.6]], dtype=torch.float, requires_grad=True)
        self.b1 = torch.tensor([[1.0, 1.0]], dtype=torch.float, requires_grad=True)

        self.W2 = torch.tensor([[1.0], [-1.0]], dtype=torch.float, requires_grad=True)
        self.b2 = torch.tensor([[1.0]], dtype=torch.float, requires_grad=True)

    # Logits
    def logits2(self, h):
        return h @ self.W2 + self.b2

    # First layer function
    def f1(self, x):
        return torch.sigmoid(x @ self.W1 + self.b1)

    # Second layer function
    def f2(self, h):
        return torch.sigmoid(h @ self.W2 + self.b2)

    # Predictor
    def f(self, x):
        return self.f2(self.f1(x))

    # Cross Entropy loss
    def loss(self, x, y):
        return torch.nn.functional.binary_cross_entropy_with_logits(self.logits2(self.f1(x)), y)


if __name__ == "__main__":
    model = SigmoidModel()

    x_train = torch.tensor([[1., 0.], [0., 1.], [1., 1.], [0., 0.]])
    y_train = torch.tensor([[1.], [1.], [0.], [0.]])

    learning_rate = 5
    optimizer = torch.optim.SGD([model.W1, model.b1, model.W2, model.b2], learning_rate)

    for epoch in range(5000):
        model.loss(x_train, y_train).backward()  # Compute loss gradients
        optimizer.step()  # perform optimization by adjusting W and b.
        optimizer.zero_grad()  # Clear gradients for the next step.

    # Print model variables and loss
    print("W = %s, b = %s, loss = %s" % (model.W1, model.b1, model.loss(x_train, y_train)))
    print("W = %s, b = %s, loss = %s" % (model.W2, model.b2, model.loss(x_train, y_train)))
    print("[0,1] is 1 = ", round(model.f(torch.tensor([0., 1.])).item()))

    fig = plt.figure("Logistic regression: the logical XOR operator")

    plot1 = fig.add_subplot(111, projection='3d')

    plot1_f = plot1.plot_wireframe(np.array([[]]), np.array([[]]), np.array([[]]), color="green",
                                   label="$y=f(x)=\\sigma(xW+b)$")
    plot1.plot(x_train[:, 0].squeeze().detach(),
               x_train[:, 1].squeeze().detach(),
               y_train[:, 0].squeeze().detach(),
               'o',
               label="$(\\hat x_1^{(i)}, \\hat x_2^{(i)},\\hat y^{(i)})$",
               color="blue")

    plot1_info = fig.text(0.01, 0.02, "$W1=\\genfrac{[}{]}{0}{}{%.2f}{%.2f}$\n"
                                      "$b1=[%.2f]$\n"
                                      "$W2=\\genfrac{[}{]}{0}{}{%.2f}{%.2f}$\n"
                                      "$b2=[%.2f]$\n"
                                      "$\n$loss = %.2f$"
                          % (model.W1[0, 0], model.W1[1, 0], model.b1[0, 0], model.W2[0, 0], model.W2[1, 0],
                             model.b2[0, 0],
                             model.loss(x_train, y_train)))

    plot1.set_xlabel("$x_1$")
    plot1.set_ylabel("$x_2$")
    plot1.set_zlabel("$y$")
    plot1.legend(loc="upper left")
    plot1.set_xticks([0, 1])
    plot1.set_yticks([0, 1])
    plot1.set_zticks([0, 1])
    plot1.set_xlim(-0.25, 1.25)
    plot1.set_ylim(-0.25, 1.25)
    plot1.set_zlim(-0.25, 1.25)

    table = plt.table(cellText=[[0, 0, 0], [0, 1, 0], [1, 0, 0], [1, 1, 0]],
                      colWidths=[0.1] * 3,
                      colLabels=["$x_1$", "$x_2$", "$f(x)$"],
                      cellLoc="center",
                      loc="lower right")
    table._cells[(1, 2)]._text.set_text("${%.1f}$" % model.f(torch.tensor([[0., 0.]], dtype=torch.float)).detach())
    table._cells[(2, 2)]._text.set_text("${%.1f}$" % model.f(torch.tensor([[0., 1.]], dtype=torch.float)).detach())
    table._cells[(3, 2)]._text.set_text("${%.1f}$" % model.f(torch.tensor([[1., 0.]], dtype=torch.float)).detach())
    table._cells[(4, 2)]._text.set_text("${%.1f}$" % model.f(torch.tensor([[1., 1.]], dtype=torch.float)).detach())

    plot1_f.remove()
    x1_grid, x2_grid = torch.tensor(np.meshgrid(np.linspace(-0.25, 1.25, 10),
                                                np.linspace(-0.25, 1.25, 10)), dtype=torch.float)
    y_grid = torch.tensor(np.empty([10, 10]), dtype=torch.float)
    for i in range(0, x1_grid.shape[0]):
        for j in range(0, x1_grid.shape[1]):
            y_grid[i, j] = model.f(torch.tensor([[x1_grid[i, j], x2_grid[i, j]]])).detach()
    plot1_f = plot1.plot_wireframe(x1_grid, x2_grid, y_grid, color="green")

    plot1.set_proj_type('ortho')

    plt.savefig('c')
    plt.show()