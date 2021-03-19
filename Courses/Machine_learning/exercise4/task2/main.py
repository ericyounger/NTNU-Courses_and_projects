import torch
import torch.nn as nn
from torch import Tensor
import numpy as np


def generate(word):

    word_toCharEncoding = []
    for element in [char for char in word]:
        word_toCharEncoding.append(char_encodings[letter_encoding[element]])

    letters_to_add = len(word)-4
    for i in range(letters_to_add):
        word_toCharEncoding.append(char_encodings[0])

    x = torch.Tensor([word_toCharEncoding])
    x = torch.transpose(x, 0, 1)
    model.reset(1)
    y = int(model.f(torch.tensor(x)).argmax().numpy())
    print(index_to_char[y])


class LongShortTermMemoryModel(nn.Module):
    def __init__(self, encoding_size, label_length):
        super(LongShortTermMemoryModel, self).__init__()

        self.lstm = nn.LSTM(encoding_size, 128)  # 128 is the state size
        self.dense = nn.Linear(128, label_length)  # 128 is the state size  |Dense layer |

    def reset(self, batch_size):
        # Reset states prior to new input sequence
        zero_state = torch.zeros(1, batch_size, 128)  # Shape: (number of layers, batch size, state size)
        self.hidden_state = zero_state
        self.cell_state = zero_state

    def logits(self, x):
        # x shape: (sequence length, batch size, encoding size)
        out, (self.hidden_state, self.cell_state) = self.lstm(x, (self.hidden_state, self.cell_state))
        return self.dense(out[-1].reshape(-1,128))

    def f(self, x):
        # x shape: (sequence length, batch size, encoding size)
        return torch.softmax(self.logits(x), dim=1)

    def loss(self, x, y):
        # x shape: (sequence length, batch size, encoding size), y shape: (sequence length, encoding size)
        return nn.functional.cross_entropy(self.logits(x), y.argmax(1))


char_encodings = [
    [1., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.],  # ' '  0
    [0., 1., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.],  # 'h'  1
    [0., 0., 1., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.],  # 'a'  2
    [0., 0., 0., 1., 0., 0., 0., 0., 0., 0., 0., 0., 0.],  # 't'  3
    [0., 0., 0., 0., 1., 0., 0., 0., 0., 0., 0., 0., 0.],  # 'r'  4
    [0., 0., 0., 0., 0., 1., 0., 0., 0., 0., 0., 0., 0.],  # 'c'  5
    [0., 0., 0., 0., 0., 0., 1., 0., 0., 0., 0., 0., 0.],  # 'p'  6
    [0., 0., 0., 0., 0., 0., 0., 1., 0., 0., 0., 0., 0.],  # 'f'  7
    [0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0., 0., 0.],  # 'l'  8
    [0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0., 0.],  # 'm'  9
    [0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 0., 0.],   # 's'  10
    [0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1., 0.],  # 'o'  11
    [0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 1.],  # 'n'  12

]

encoding_size = len(char_encodings)
index_to_char = ['🎩', '🐀', '🐈', '🧢', '🏢', '👨', '👦']
index_to_letter = [' ', 'h', 'a', 't', 'r', 'c', 'p', 'f', 'l', 'm', 's', 'o', 'n']
letter_encoding = dict((char, i) for i, char in enumerate(index_to_letter))
print(letter_encoding)


x_train: Tensor = torch.tensor([
     [char_encodings[1], char_encodings[2], char_encodings[3], char_encodings[0]],  # 'hat '
     [char_encodings[4], char_encodings[2], char_encodings[3], char_encodings[0]],  # 'rat '
     [char_encodings[5], char_encodings[2], char_encodings[3], char_encodings[0]],  # 'cat '
     [char_encodings[5], char_encodings[2], char_encodings[6], char_encodings[0]],  # 'cap '
     [char_encodings[7], char_encodings[8], char_encodings[2], char_encodings[3]],  # 'flat'
     [char_encodings[9], char_encodings[2], char_encodings[3], char_encodings[3]],  # 'matt'
     [char_encodings[10], char_encodings[11], char_encodings[12], char_encodings[0]]  # 'son '
     ])

label_length = 7

x_train = torch.transpose(x_train, 0, 1) # transposes x-train to match dimensions of y-train
y_train = torch.Tensor(np.eye(label_length, label_length)) # creates a 7x7 diagonal matrix
model = LongShortTermMemoryModel(encoding_size, label_length)

optimizer = torch.optim.RMSprop(model.parameters(), 0.01)

for epoch in range(500):
    model.reset(x_train.size(1))
    model.loss(x_train, y_train).backward()
    optimizer.step()
    optimizer.zero_grad()

    if epoch % 10 == 9:
        # Generate characters from the initial characters ' h'
        generate("rt")
        model.reset(x_train.size(1))
        loss = (model.loss(x_train, y_train))
        print("Epoch:", epoch, " Loss:",loss)