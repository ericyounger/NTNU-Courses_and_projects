nameList = input("Input your name\n").split(" ")

initials = ""
for x in range(0, len(nameList)):
    initials += nameList[x][0:1]

print(initials)
print("Welcome to Python", " ".join(nameList))

