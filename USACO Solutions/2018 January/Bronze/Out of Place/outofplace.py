"""
ID: shivara2
LANG: PYTHON3
TASK: outofplace
"""

def readInput():
    fin = open('outofplace.in','r')
    data = fin.readlines()
    lineup = [0]
    for i in range(1,len(data)):
        lineup.append(int(data[i]))
    fin.close()
    return lineup

def findBessie(lineup):
    for i in range(1,len(lineup)):
        Alineup = lineup[:i] + lineup[i+1:]
        if ordered(Alineup) == True:
            return i
    return -1

def ordered(lineup):
    #True means that lineup is correctly ordered
    for i in range(1,len(lineup)):
        if lineup[i] < lineup[i-1]:
            return False
    return True

def main(lineup,Bessie):
    target = sorted(lineup).index(lineup[Bessie])
    swap = 0
    while ordered(lineup) == False:
        if target < Bessie:
            if lineup[Bessie - 1] != lineup[Bessie - 2]:
                swap += 1
            temp = lineup[Bessie-1]
            lineup[Bessie-1] = lineup[Bessie]
            lineup[Bessie] = temp
            Bessie -= 1
        elif target > Bessie:
            if Bessie == len(lineup) - 2 or lineup[Bessie + 1] != lineup[Bessie + 2]:
                swap += 1
            temp = lineup[Bessie+1]
            lineup[Bessie+1] = lineup[Bessie]
            lineup[Bessie] = temp
            Bessie += 1
        else:
            return 0
    return swap



lineup = readInput()
Bessie = findBessie(lineup)
swap = main(lineup,Bessie)
fout = open('outofplace.out','w')
fout.write(str(swap) + '\n')
fout.close()

