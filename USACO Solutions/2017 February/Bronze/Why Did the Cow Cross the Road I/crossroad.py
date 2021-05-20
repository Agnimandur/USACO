"""
ID: shivara2
LANG: PYTHON3
TASK: crossroad
"""

def readInput():
    fin = open('crossroad.in','r')
    data = fin.readlines()
    cowCrossings = []
    for i in range(1,len(data)):
        cowCrossings.append(data[i].split())
    for i in range(0,len(cowCrossings)):
        cowCrossings[i] = [int(x) for x in cowCrossings[i]]
    fin.close()
    return cowCrossings

def main(cowCrossings):
    travelIndex = {}
    for observation in cowCrossings:
        if observation[0] in travelIndex:
            travelIndex[observation[0]].append(observation[1])
        else:
            travelIndex[observation[0]] = [observation[1]]
    totalCrossings = 0
    for key in travelIndex:
        totalCrossings += countCrossings(travelIndex[key])
    return totalCrossings

def countCrossings(locationList):
    currentLocation = locationList[0]
    crossings = 0
    for i in range(0,len(locationList)):
        if locationList[i] != currentLocation:
            crossings += 1
            currentLocation = locationList[i]
    return crossings

def writeOutput(totalCrossings):
    fout = open('crossroad.out','w')
    fout.write(str(totalCrossings) + '\n')
    fout.close()

cowCrossings = readInput()
totalCrossings = main(cowCrossings)
writeOutput(totalCrossings)
