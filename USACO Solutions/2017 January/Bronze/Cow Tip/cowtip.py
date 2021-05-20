"""
ID: shivara2
LANG: PYTHON3
TASK: cowtip
"""

def readInput():
    fin = open('cowtip.in','r')
    data = fin.readlines()
    cowGrid = []
    for i in range(1,len(data)):
        row = list(data[i].strip())
        row = [int(x) for x in row]
        cowGrid.append(row)
    fin.close()
    return cowGrid

def main(cowGrid,iterations):
    indexOf1s = []
    for i in range(0,len(cowGrid)):
        for j in range(0,len(cowGrid)):
            if cowGrid[i][j] == 1:
                indexOf1s.append(str(i) + str(j))
    if len(indexOf1s) > 0:
        return main(flip(cowGrid,indexOf1s[-1]),iterations + 1)
    else:
        return iterations

def flip(cowGrid,flipIndex):
    row = int(flipIndex[0])
    column = int(flipIndex[1])
    for i in range(0,len(cowGrid)):
        for j in range(0,len(cowGrid)):
            if i <= row and j <= column:
                cowGrid[i][j] = (cowGrid[i][j] + 1) % 2
    return cowGrid

def writeOutput(answer):
    fout = open('cowtip.out','w')
    fout.write(str(answer) + '\n')
    fout.close()
    
cowGrid = readInput()
answer = main(cowGrid,0)
writeOutput(answer)

