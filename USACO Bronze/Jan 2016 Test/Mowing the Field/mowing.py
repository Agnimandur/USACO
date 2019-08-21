"""
ID: shivara2
LANG: PYTHON3
TASK: mowing
"""

def readInput():
    fin = open('mowing.in','r')
    data = fin.readlines()
    FJlog = []
    for i in range(1,len(data)):
        n = data[i].split()
        n[1] = int(n[1])
        FJlog.append(n)
    fin.close()
    return FJlog

def main(FJlog):
    steps = sum([x[1] for x in FJlog])
    position = [0,0]
    grassMowed = []
    grassMowed.append(position)
    for move in FJlog:
        for i in range(0,move[1]):
            position = direction(move[0],position)
            grassMowed.append(position)

    appearsOnce = []
    duplicates = []
    for i in range(0,len(grassMowed)):
        if grassMowed[i] not in appearsOnce:
            appearsOnce.append(grassMowed[i])
        else:
            duplicates.append(grassMowed[i])
    #Break if no duplicates
    if len(duplicates) == 0:
        return -1

    #Indexes at which at the duplicate elements appear
    dupIndexes = []
    for dup in duplicates:
        dupIndexes.append([x for x,y in enumerate(grassMowed) if y == dup])

    #Iterate through all spots visited multiple times by the lawnmower
    times = []
    for indList in dupIndexes:
        for i in range(0,len(indList)-1):
            times.append(indList[i+1]-indList[i])
    return min(times)

def direction(dir,position):
    if dir == "N":
        return [position[0],position[1]+1]
    elif dir == "E":
        return [position[0]+1,position[1]]
    elif dir == "S":
        return [position[0],position[1]-1]
    else:
        return [position[0]-1,position[1]]

def writeOutput(strGrassSpeed):
    fout = open('mowing.out','w')
    fout.write(strGrassSpeed + '\n')
    fout.close()

FJlog = readInput()
grassSpeed = main(FJlog)
writeOutput(str(grassSpeed))
