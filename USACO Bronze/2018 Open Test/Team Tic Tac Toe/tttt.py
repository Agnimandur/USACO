"""
ID: shivara2
LANG: PYTHON3
TASK: tttt
"""

def readInput():
    fin = open('tttt.in','r')
    data = fin.readlines()
    gameboard = []
    solo = []
    for i in range(0,3):
        for j in range(0,3):
            gameboard.append(data[i][j])
            if data[i][j] not in solo:
                solo.append(data[i][j])
    #Determine Two person teams
    twoPersonTeams = []
    for i in range(0,len(solo)):
        for j in range(i+1,len(solo)):
            twoPersonTeams.append([solo[i],solo[j]])
    fin.close()
    return gameboard,solo,twoPersonTeams

def createRCD(gameboard):
    RCD = []
    #3 Rows
    RCD.append(gameboard[0] + gameboard[1] + gameboard[2])
    RCD.append(gameboard[3] + gameboard[4] + gameboard[5])
    RCD.append(gameboard[6] + gameboard[7] + gameboard[8])

    #3 Cols
    RCD.append(gameboard[0] + gameboard[3] + gameboard[6])
    RCD.append(gameboard[1] + gameboard[4] + gameboard[7])
    RCD.append(gameboard[2] + gameboard[5] + gameboard[8])
    
    #2 Diags
    RCD.append(gameboard[0] + gameboard[4] + gameboard[8])
    RCD.append(gameboard[2] + gameboard[4] + gameboard[6])

    return RCD

def soloWins(solo,RCD):
    soloWins = 0
    for cow in solo:
        for i in range(0,len(RCD)):
            if RCD[i] == cow * 3:
                soloWins += 1
                break
    return soloWins

def teamWins(twoPersonTeams,RCD):
    teamWins = 0
    for team in twoPersonTeams:
        for i in range(0,len(RCD)):
            if RCD[i][0] in team and RCD[i][1] in team and RCD[i][2] in team:
                if not (RCD[i] == team[0] * 3 or RCD[i] == team[1] * 3):
                    teamWins += 1
                    break
    return teamWins

gameboard,solo,twoPersonTeams = readInput()
RCD = createRCD(gameboard)
soloWins = soloWins(solo,RCD)
teamWins = teamWins(twoPersonTeams,RCD)
fout = open('tttt.out','w')
fout.write(str(soloWins) + '\n')
fout.write(str(teamWins) + '\n')
fout.close()
