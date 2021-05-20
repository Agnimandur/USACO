"""
ID: shivara2
LANG: PYTHON3
TASK: hoofball
"""

def readInput():
    fin = open('hoofball.in','r')
    data = fin.readlines()
    cowPosition = list(map(lambda x: int(x),data[1].split()))
    fin.close()
    return sorted(cowPosition)

def main(cowPlay,balls):
    K = set(cowPlay.keys())
    V = set(cowPlay.values())
    return balls + len(K - V)

def cowPlaysTo(cowPosition):
    cowPlay = {}
    cowPlay[cowPosition[0]] = cowPosition[1]
    for i in range(1,len(cowPosition)-1):
        if cowPosition[i] - cowPosition[i-1] <= cowPosition[i+1] - cowPosition[i]:
            #Play to the Left
            cowPlay[cowPosition[i]] = cowPosition[i-1]
        else:
            #Play to the Right
            cowPlay[cowPosition[i]] = cowPosition[i+1]
    cowPlay[cowPosition[-1]] = cowPosition[-2]
    return cowPlay

def isolatedPlayers(cowPlay):
    pairs = []
    for key in cowPlay:
        val = cowPlay[key]
        if cowPlay[val] == key:
            pairs.append([key,val])
    notIsolated = []
    for key in cowPlay:
        for pair in pairs:
            if (not key in pair) and cowPlay[key] in pair:
                notIsolated.append(pair)
    isolatedPairs = [item for item in pairs if item not in notIsolated]
    return isolatedPairs

cowPosition = readInput()
balls = 0
if len(cowPosition) > 3:
    cowPlay = cowPlaysTo(cowPosition)
    isolatedPairs = isolatedPlayers(cowPlay)
    for pair in isolatedPairs:
        del cowPlay[pair[0]]
        balls += 0.5
    balls = main(cowPlay,int(balls))
else:
    balls = 1

#Write Output
fout = open('hoofball.out','w')
fout.write(str(balls) + '\n')
fout.close()
    

