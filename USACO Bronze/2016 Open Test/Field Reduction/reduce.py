"""
ID: shivara2
LANG: PYTHON3
TASK: reduce
"""

def readInput():
    fin = open('reduce.in','r')
    data = fin.readlines()
    cowX = []
    cowY = []
    for i in range(1,len(data)):
        l = data[i].split()
        cowX.append(int(l[0]))
        cowY.append(int(l[1]))
    fin.close()
    return cowX,cowY

def main(cowX,cowY):
    minX = min(cowX)
    maxX = max(cowX)
    minY = min(cowY)
    maxY = max(cowY)
    minArea = (maxX - minX) * (maxY - minY)
    interestingN = []
    #The interesting cows are the ones at the edge of the field.
    for i in range(0,len(cowX)):
        if (cowX[i] == minX or cowX[i] == maxX) or (cowY[i] == minY or cowY[i] == maxY):
            interestingN.append(i)
    for i in range(0,len(interestingN)):
        n = interestingN[i]
        newCowX = cowX[:n] + cowX[n+1:]
        newCowY = cowY[:n] + cowY[n+1:]
        newArea = (max(newCowX) - min(newCowX)) * (max(newCowY) - min(newCowY))
        minArea = min(minArea,newArea)

    #Write Output
    fout = open('reduce.out','w')
    fout.write(str(minArea) + '\n')
    fout.close()

cowX,cowY = readInput()
main(cowX,cowY)
