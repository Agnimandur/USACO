"""
ID: shivara2
LANG: PYTHON3
TASK: circlecross
"""

def readInput():
    fin = open('circlecross.in','r')
    data = fin.readlines()
    data = data[0].strip()
    cowCrossing = {}
    for i in range(65,91):
        cowCrossing[chr(i)] = [-1,-1]
    for i in range(0,len(data)):
        if cowCrossing[data[i]][0] == -1:
            cowCrossing[data[i]][0] = i
        else:
            cowCrossing[data[i]][1] = i
    fin.close()
    return cowCrossing

def main(cowCrossing):
    totalCrossingPairs = 0
    for i in range(65,91):
        for j in range(65,91):
            if i != j:
                totalCrossingPairs += checkCrossingPair(cowCrossing,chr(i),chr(j))
    return totalCrossingPairs

def checkCrossingPair(cowCrossing,cow1,cow2):
    crossingList = sorted(cowCrossing[cow1] + cowCrossing[cow2])
    if (crossingList[0] in cowCrossing[cow1] and crossingList[2] in cowCrossing[cow1]) and (crossingList[1] in cowCrossing[cow2] and crossingList[3] in cowCrossing[cow2]):
        return 1
    else:
        return 0

def writeOutput(totalCrossingPairs):
    fout = open('circlecross.out','w')
    fout.write(str(totalCrossingPairs) + '\n')
    fout.close()

cowCrossing = readInput()
totalCrossingPairs = main(cowCrossing)
writeOutput(totalCrossingPairs)
