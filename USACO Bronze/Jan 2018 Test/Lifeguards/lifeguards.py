"""
ID: shivara2
LANG: PYTHON3
TASK: lifeguards
"""

def readInput():
    fin = open('lifeguards.in','r')
    data = fin.readlines()
    guardShifts = []
    for i in range(1,len(data)):
        guardShifts.append(list(map(lambda x: int(x),data[i].split())))
    guardCombinations = []
    for i in range(0,len(guardShifts)):
        guardCombinations.append(guardShifts[:i] + guardShifts[i+1:])
    fin.close()
    return guardCombinations

def main(guardCombinations):
    maxTime = 0
    for i in range(0,len(guardCombinations)):
        maxTime = max(maxTime,calculateDutyTime(sorted(guardCombinations[i])))
    fout = open('lifeguards.out','w')
    fout.write(str(maxTime) + '\n')
    fout.close()

def calculateDutyTime(guardShifts):
    totalOffTime = 0
    while len(guardShifts) > 1:
        if guardShifts[0][1] >= guardShifts[1][0]:
            temp = [min(guardShifts[0][0],guardShifts[1][0]),max(guardShifts[0][1],guardShifts[1][1])]
            del guardShifts[0]
            guardShifts[0] = temp
        else:
            offTime = guardShifts[1][0] - guardShifts[0][1]
            totalOffTime += offTime
            guardShifts[0][1] += offTime
    return guardShifts[0][1] - guardShifts[0][0] - totalOffTime

guardCombinations = readInput()
main(guardCombinations)
