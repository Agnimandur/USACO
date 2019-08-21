"""
ID: shivara2
LANG: PYTHON3
TASK: combo
"""

def readInput():
    fin = open('combo.in','r')
    data = fin.readlines()
    N = int(data[0])
    JohnLock = data[1].strip().split()
    MasterLock = data[2].strip().split()
    JohnLock = [int(x) for x in JohnLock]
    MasterLock = [int(x) for x in MasterLock]
    fin.close()
    return N,JohnLock,MasterLock

def createSets(combination,N):
    sets = set()
    for i in range(-2,3):
        for j in range(-2,3):
            for k in range(-2,3):
                sets.add(str(adjustedModulus(N,combination[0]+i)) +','+ str(adjustedModulus(N,combination[1]+j)) + ',' + str(adjustedModulus(N,combination[2]+k)))
    return sets

def adjustedModulus(N,num):
    if num <= 0:
        return num + N
    elif num > N:
        return num - N
    else:
        return num

def writeOutput(allSets):
    fout = open('combo.out','w')
    answer = len(allSets)
    fout.write(str(answer) + '\n')
    fout.close()


N,JohnLock,MasterLock = readInput()
allSets = set()
if N != 1:
    allSets = allSets.union(createSets(JohnLock,N))
    allSets = allSets.union(createSets(MasterLock,N))
else:
    allSets.add("1,1,1")
writeOutput(allSets)
