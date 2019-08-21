"""
ID: shivara2
LANG: PYTHON3
TASK: hps
"""

def readInput():
    fin = open('hps.in','r')
    data = fin.readlines()
    games = []
    for i in range(1,len(data)):
        cow1 = int(data[i].split()[0])
        cow2 = int(data[i].split()[1])
        if cow1 != cow2:
            games.append([cow1,cow2])
    fin.close()
    return games

def main(games):
    winList = []
    for i in range(1,4):
        for j in range(1,4):
            for k in range(1,4):
                if i != j and j != k:
                    winList.append(winNumber(games,[i,j,k]))
    return max(winList)

def winNumber(games,pairingList):
    wins = 0
    for match in games:
        if (match[0] == pairingList[0] and match[1] == pairingList[1]) or (match[0] == pairingList[1] and match[1] == pairingList[2]):
            wins += 1
        elif match[0] == pairingList[2] and match[1] == pairingList[0]:
            wins += 1
        else:
            wins += 0
    return wins

def writeOutput(maxWins):
    fout = open('hps.out','w')
    fout.write(str(maxWins) + '\n')
    fout.close()

games = readInput()
maxWins = main(games)
writeOutput(maxWins)
















