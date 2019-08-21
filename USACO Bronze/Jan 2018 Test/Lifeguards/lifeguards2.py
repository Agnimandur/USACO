"""
ID: shivara2
LANG: PYTHON3
TASK: lifeguards
"""

def readInput():
    fin = open('lifeguards.in','r')
    data = fin.readlines()
    timeCovered = {}
    guardShifts = []
    for i in range(0,1000):
        timeCovered[i] = 0
    for i in range(1,len(data)):
        l = list(map(lambda x: int(x),data[i].split()))
        guardShifts.append(l)
        start = l[0]
        end = l[1]
        for j in range(start,end):
            timeCovered[j] += 1
    fin.close()
    return guardShifts,timeCovered

def main(guardShifts,timeCovered):
    maxTime = 0
    for duty in guardShifts:
        for i in range(duty[0],duty[1]):
            timeCovered[i] -= 1
        timeOn = 0
        for i in range(0,1000):
            if timeCovered[i] > 0:
                timeOn += 1
        maxTime = max(timeOn,maxTime)
        for i in range(duty[0],duty[1]):
            timeCovered[i] += 1
    return maxTime

guardShifts,timeCovered = readInput()
maxTime = main(guardShifts,timeCovered)
fout = open('lifeguards.out','w')
fout.write(str(maxTime) + '\n')
fout.close()
