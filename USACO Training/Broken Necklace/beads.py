"""
ID: shivara2
LANG: PYTHON3
TASK: beads
"""


def readInput():
    fin = open('beads.in','r')
    data = fin.readlines()
    fin.close()
    necklace = data[1].strip()
    return necklace

def sequenceLength(splitPoint,necklace):
    startingColor = necklace[splitPoint]
    stringLength = 0
    i = splitPoint
    #Counts beads towards the right
    while startingColor == necklace[i] or necklace[i] == 'w':
        stringLength += 1
        i += 1
        i = i % len(necklace)
        if startingColor == 'w' and not necklace[i] == 'w':
            startingColor = necklace[i]
        if i == splitPoint:
            break
    #Refresh variables
    splitPoint -= 1
    splitPoint = splitPoint % len(necklace)
    startingColor = necklace[splitPoint]
    i = splitPoint
    #Counts beads towards the left
    while startingColor == necklace[i] or necklace[i] == 'w':
        stringLength += 1
        i -= 1
        i = i % len(necklace)
        if startingColor == 'w' and not necklace[i] == 'w':
            startingColor = necklace[i]
        if i == splitPoint:
            break
    return stringLength

def maxSequenceLength(necklace):
    maxSL = 0
    for i in range(0,len(necklace)):
        maxSL = max(maxSL,sequenceLength(i,necklace))
    maxSL = min(maxSL,len(necklace))
    return str(maxSL)

def writeOutput(maxSL):
    fout = open('beads.out','w')
    fout.write(maxSL + '\n')
    fout.close()
    
necklace = readInput()
maxSL = maxSequenceLength(necklace)
writeOutput(maxSL)
