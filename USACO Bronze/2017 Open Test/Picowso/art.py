"""
ID: shivara2
LANG: PYTHON3
TASK: art
"""

def readInput():
    fin = open('art.in','r')
    data = fin.readlines()
    canvas = []
    for i in range(1,len(data)):
        row = []
        for j in range(0,len(data)-1):
            row.append(data[i][j])
        canvas.append(row)
    fin.close()
    return canvas

def main(canvas):
    underneathDict = {}
    for color in range(1,10):
        if inList(canvas,color):
            underneathDict[color] = list(set(gapsInRectangle(canvas,color)))
    colorsUsed = []
    colorsOnTopOfAnotherColor = []
    for key in underneathDict:
        colorsUsed.append(str(key))
        colorsOnTopOfAnotherColor += underneathDict[key]
    colorsOnTopOfAnotherColor = list(set(colorsOnTopOfAnotherColor))
    numBottomColors = len(colorsUsed) - len(colorsOnTopOfAnotherColor)
    return numBottomColors

def gapsInRectangle(canvas,n):
    nLocations = []
    smallestRow = 10
    biggestRow = -1
    smallestColumn = 10
    biggestColumn = -1
    for i in range(0,len(canvas)):
        for j in range(0,len(canvas)):
            if canvas[i][j] == str(n):
                nLocations.append(str(i) + str(j))
                smallestRow = min(smallestRow,i)
                biggestRow = max(biggestRow,i)
                smallestColumn = min(smallestColumn,j)
                biggestColumn = max(biggestColumn,j)
    nUnderneath = []
    for i in range(smallestRow,biggestRow+1):
        for j in range(smallestColumn,biggestColumn+1):
            if (str(i) + str(j)) not in nLocations:
                nUnderneath.append(canvas[i][j])
    return nUnderneath

def inList(canvas,color):
    for row in canvas:
        if str(color) in row:
            return True
    return False

def writeOutput(numBottomColors):
    fout = open('art.out','w')
    fout.write(str(numBottomColors) + '\n')
    fout.close()

canvas = readInput()
numBottomColors = main(canvas)
writeOutput(numBottomColors)

