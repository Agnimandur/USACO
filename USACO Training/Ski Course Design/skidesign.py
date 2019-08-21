"""
ID: shivara2
LANG: PYTHON3
TASK: skidesign
"""

def readInput():
    fin = open('skidesign.in','r')
    data = fin.readlines()
    data = [int(x) for x in data[1:]]
    fin.close()
    return sorted(data)

def checkMins(originalHills,diff):
    caseMin = min(originalHills) + diff
    caseMax = caseMin + 17
    newHills = [0 for x in range(0,len(originalHills))]
    for i in range(0,len(originalHills)):
        if originalHills[i] < caseMin:
            newHills[i] = caseMin
        elif originalHills[i] > caseMax:
            newHills[i] = caseMax
        else:
            newHills[i] = originalHills[i]
    return newHills

def findCost(originalHills,newHills):
    costList = [(originalHills[x] - newHills[x])**2 for x in range(0,len(originalHills))]
    return sum(costList)

def writeOutput(answer):
    fout = open('skidesign.out','w')
    fout.write(str(answer) + '\n')
    fout.close()
    
originalHills = readInput()
diff = max(originalHills) - min(originalHills) - 17
if diff < 1:
    answer = 0
    writeOutput(answer)
else:
    answer = 10000000
    for i in range(0,diff+1):
        newHills = checkMins(originalHills,i)
        cost = findCost(originalHills,newHills)
        if cost < answer:
            answer = cost
    writeOutput(answer)
