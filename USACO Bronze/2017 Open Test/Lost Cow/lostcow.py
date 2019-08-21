"""
ID: shivara2
LANG: PYTHON3
TASK: lostcow
"""

def readInput():
    fin = open('lostcow.in','r')
    data = fin.readlines()
    x = data[0].split()[0]
    x = int(x)
    y = data[0].split()[1]
    y = int(y)
    fin.close()
    return x,y

def main(x,y):
    if x == y:
        return 0
    else:
        return moveBessie(x,y)

def moveBessie(x,y):
    jump = 1
    position = x
    distanceTravelled = 0
    while position != y:
        distanceTravelled += abs(position - x)
        position = x
        if x + jump > y and x < y:
            position = y
            distanceTravelled += abs(y-x)
        elif x + jump < y and x > y:
            position = y
            distanceTravelled += abs(y-x)
        else:
            position += jump
            distanceTravelled += abs(jump)
        jump *= -2
    return distanceTravelled

def writeOutput(distanceTravelled):
    fout = open('lostcow.out','w')
    fout.write(str(distanceTravelled)+'\n')
    fout.close()

x,y = readInput()
distanceTravelled = main(x,y)
writeOutput(distanceTravelled)
