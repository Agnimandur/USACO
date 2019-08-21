"""
ID: shivara2
LANG: PYTHON3
TASK: notlast
"""

def readInput():
    fin = open('notlast.in','r')
    data = fin.readlines()
    milkValues = {'Bessie':0,'Elsie':0,'Daisy':0,'Gertie':0,'Annabelle':0,'Maggie':0,'Henrietta':0}
    for i in range(1,len(data)):
        milkValues[data[i].split()[0]] += int(data[i].split()[1])
    fin.close()
    return milkValues

def deleteMinimum(milkValues):
    minimum = milkValues['Bessie']
    for key in milkValues:
        minimum = min(minimum,milkValues[key])
    goodMilkValues = {}
    for key in milkValues:
        if milkValues[key] > minimum:
            goodMilkValues[key] = milkValues[key]
    return goodMilkValues

def findMinimum(goodMilkValues):
    minimum = 100000000000000000000000000000
    for key in goodMilkValues:
        minimum = min(minimum,goodMilkValues[key])
    bestMilkValues = {}
    for key in goodMilkValues:
        if goodMilkValues[key] == minimum:
            bestMilkValues[key] = goodMilkValues[key]
    return bestMilkValues

def writeOutput(answer):
    fout = open('notlast.out','w')
    fout.write(answer + '\n')
    fout.close()

milkValues = readInput()
goodMilkValues = deleteMinimum(milkValues)
if len(goodMilkValues) > 0:
    bestMilkValues = findMinimum(goodMilkValues)
    if len(bestMilkValues) == 1:
        writeOutput(list(bestMilkValues.keys())[0])
    else:
        writeOutput('Tie')
else:
    writeOutput('Tie')
