"""
ID: shivara2
LANG: PYTHON3
TASK: wormhole
"""

def readInput():
    fin = open('wormhole.in','r')
    data = fin.readlines()
    Xpoints = []
    Ypoints = []
    for i in range(1,len(data)):
        Xpoints.append(data[i].split()[0])
        Ypoints.append(data[i].split()[1])
    for i in range(0,len(Xpoints)):
        Xpoints[i] = int(Xpoints[i])
        Ypoints[i] = int(Ypoints[i])
    fin.close()
    return Xpoints,Ypoints

def moveBessie(Xpoints,Ypoints,wormholePairs,wormholeID,nextOnRight):
    coordinates = [[Xpoints[i],Ypoints[i]] for i in range(0,len(Xpoints))]
    wormholesVisited = []
    while len(wormholesVisited) == len(set(wormholesVisited)):
        #Move Bessie to the Next Wormhole.
        wormholeID = nextOnRight[wormholeID]
        if wormholeID == 0:
            break
        wormholesVisited.append(wormholeID)
        #Teleport to New Wormhole
        wormholeID = wormholePairs[wormholeID - 1]
    if len(wormholesVisited) == len(set(wormholesVisited)):
        return 0
    else:
        return 1
        

#Xpoints and Ypoints are set to a base pairing of [1,2,3,4,5,...]. rearrange changes Xpoints and Ypoints when the wormholePairs are changed.

def main(Xpoints,Ypoints,wormholePairs,nextOnRight):
    totalCycles = 0
    if 0 in wormholePairs:
        unpairedIndex = wormholePairs.index(0)
    else:
        badWormholes = 0
        for i in range(0,len(Xpoints)):
            badWormholes += moveBessie(Xpoints,Ypoints,wormholePairs,i+1,nextOnRight)
        if badWormholes > 0:
            return 1
        else:
            return 0
    for i in range(unpairedIndex+1,len(Xpoints)):
        if wormholePairs[i] == 0:
            wormholePairs[i] = unpairedIndex+1
            wormholePairs[unpairedIndex] = i+1
            totalCycles += main(Xpoints,Ypoints,wormholePairs,nextOnRight)
            wormholePairs[i] = 0
            wormholePairs[unpairedIndex] = 0
    return totalCycles

def generateRight(Xpoints,Ypoints):
    nextOnRight = {}
    for i in range(0,len(Xpoints)):
        nextOnRight[i+1] = 0
    for i in range(0,len(Xpoints)):
        for j in range(0,len(Xpoints)):
            if Ypoints[i] == Ypoints[j] and Xpoints[i] < Xpoints[j]:
                if nextOnRight[i+1] != 0:
                    XofNOR = Xpoints[nextOnRight[i+1] - 1]
                    if XofNOR > Xpoints[j]:
                        nextOnRight[i+1] = j+1
                else:
                    nextOnRight[i+1] = j+1
    return nextOnRight

def writeOutput(answer):
    fout = open('wormhole.out','w')
    fout.write(str(answer) + '\n')
    fout.close()

Xpoints,Ypoints = readInput()
wormholePairs = [0] * len(Xpoints)
nextOnRight = generateRight(Xpoints,Ypoints)
answer = main(Xpoints,Ypoints,wormholePairs,nextOnRight)
writeOutput(answer)
