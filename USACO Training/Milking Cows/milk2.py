"""
ID: shivara2
LANG: PYTHON3
TASK: milk2
"""


def readInput():
    fin = open('milk2.in','r')
    data = fin.readlines()
    fin.close()
    minTime = 1000000
    maxTime = 0
    for i in range(1,len(data)):
        data[i] = data[i].strip()
        startTime = int(data[i].split()[0])
        endTime = int(data[i].split()[1])
        minTime = min(minTime,startTime)
        maxTime = max(maxTime,endTime)
    times = {}
    for i in range(minTime,maxTime):
        times[i] = False
    return data,times
        
def milkTimes(data,times):
    for i in range(1,len(data)):
        startTime = int(data[i].split()[0])
        endTime = int(data[i].split()[1])
        for j in range(startTime,endTime):
            times[j] = True
    return times
def calculateContinuousMilking(times):
    continuousMilkingTime = 0
    maxCMT = 0
    for key in times:
        continuousMilkingTime += 1
        if not times[key]:
            continuousMilkingTime = 0 
        maxCMT = max(continuousMilkingTime,maxCMT)
    return maxCMT

def calculateIdleTime(times):
    idleTime = 0
    maxIT = 0
    for key in times:
        idleTime += 1
        if times[key]:
            idleTime = 0 
        maxIT = max(idleTime,maxIT)
    return maxIT

def writeOutput(maxCMT,maxIT):
    fout = open('milk2.out','w')
    fout.write(str(maxCMT) + " " + str(maxIT) + '\n')
    fout.close()

data,times = readInput()
times = milkTimes(data,times)
maxCMT = calculateContinuousMilking(times)
maxIT = calculateIdleTime(times)
writeOutput(maxCMT,maxIT)

