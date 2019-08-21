"""
ID: shivara2
LANG: PYTHON3
TASK: barn1
"""

def readInput():
    fin = open('barn1.in','r')
    data = fin.readlines()
    boardNumber = int(data[0].split()[0])
    cowStalls = []
    for i in range(1,len(data)):
        cowStalls.append(int(data[i]))
    cowStalls = sorted(cowStalls)
    fin.close()
    return boardNumber,cowStalls

def differences(cowStalls):
    listOfDifferences = []
    for i in range(1,len(cowStalls)):
        listOfDifferences.append(cowStalls[i] - cowStalls[i-1])
    return listOfDifferences

def findBiggestDifferences(boardNumber,listOfDifferences):
    temp = listOfDifferences
    biggestDifferences = []
    for i in range(1,boardNumber):
        biggestDifferences.append(max(temp) - 1)
        temp.remove(max(temp))
        if len(temp) == 0:
            break
    return biggestDifferences

def calculateBlockedStalls(cowStalls,biggestDifferences):
    n = cowStalls[-1] - cowStalls[0] + 1
    n -= sum(biggestDifferences)
    return n

def writeOutput(n):
    fout = open('barn1.out','w')
    fout.write(str(n) + '\n')
    fout.close()

boardNumber,cowStalls = readInput()
if len(cowStalls) != 1:    
    listOfDifferences = differences(cowStalls)
    biggestDifferences = findBiggestDifferences(boardNumber,listOfDifferences)
    n = calculateBlockedStalls(cowStalls,biggestDifferences)
    writeOutput(n)
else:
    writeOutput(1)
