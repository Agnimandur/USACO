"""
ID: shivara2
LANG: PYTHON3
TASK: milk
"""

def readInput():
    fin = open('milk.in','r')
    data = fin.readlines()
    milkNeeded = int(data[0].split()[0])
    milkIndex = {}
    for i in range(1,len(data)):
        price = int(data[i].split()[0])
        milkAmount = int(data[i].split()[1])
        if price in milkIndex:
            milkIndex[price] += milkAmount
        else:
            milkIndex[price] = milkAmount
    fin.close()
    return milkNeeded,milkIndex

def buyMilk(milkNeeded,milkIndex):
    milkRequirement = milkNeeded
    moneySpent = 0
    for key in sorted(milkIndex):
        if milkIndex[key] < milkRequirement:
            moneySpent += key * milkIndex[key]
            milkRequirement -= milkIndex[key]
        else:
            moneySpent += key * milkRequirement
            break
    return moneySpent

def writeOutput(moneySpent):
    fout = open('milk.out','w')
    fout.write(str(moneySpent) + '\n')
    fout.close()

milkNeeded,milkIndex = readInput()
moneySpent = buyMilk(milkNeeded,milkIndex)
writeOutput(moneySpent)
