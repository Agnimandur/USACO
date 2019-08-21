"""
ID: shivara2
LANG: PYTHON3
TASK: transform
"""

def readInput():
    fin = open('transform.in','r')
    data = fin.readlines()
    for i in range(0,len(data)):
        data[i] = data[i].strip()
        temp = []
        for char in data[i]:
            temp.append(char)
        data[i] = temp
    data = data[1:]
    originalGrid = data[:int(len(data)/2)]
    finalGrid = data[int(len(data)/2):]
    fin.close()
    return originalGrid,finalGrid

def rotate(originalGrid):
    #setup output
    outputGrid = []
    N = len(originalGrid)
    for i in range(0,N):
        outputGrid.append(N * ['x'])
    #run through the grid
    for row in range(0,N):
        for column in range(0,N):
            outputGrid[column][(N-1)-row] = originalGrid[row][column]
    return outputGrid

def reflect(originalGrid):
    #setupoutput
    outputGrid = []
    N = len(originalGrid)
    for i in range(0,N):
        outputGrid.append(N * ['x'])
    #run through the grid
    for row in range(0,N):
        outputGrid[row] = originalGrid[row][::-1]
    return outputGrid

def runThroughOutcomes(originalGrid,finalGrid):
    caseNum = 0
    if rotate(originalGrid) == finalGrid:
        caseNum = 1
    elif rotate(rotate(originalGrid)) == finalGrid:
        caseNum = 2
    elif rotate(rotate(rotate(originalGrid))) == finalGrid:
        caseNum = 3
    elif reflect(originalGrid) == finalGrid:
        caseNum = 4
    elif (rotate(reflect(originalGrid)) == finalGrid or rotate(rotate(reflect(originalGrid))) == finalGrid) or rotate(rotate(rotate(reflect(originalGrid)))) == finalGrid:
        caseNum = 5
    elif originalGrid == finalGrid:
        caseNum = 6
    else:
        caseNum = 7
    return caseNum

def writeOutput(caseNum):
    fout = open('transform.out','w')
    fout.write(str(caseNum) + '\n')
    fout.close()
    
originalGrid,finalGrid = readInput()
caseNum = runThroughOutcomes(originalGrid,finalGrid)
writeOutput(caseNum)
