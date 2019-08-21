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

def main(lineup):
    sLineup = sorted(lineup)
    swap = 0
    for i in range(0,len(lineup)):
        if lineup[i] != sLineup[i]:
            swap += 1
    if swap > 0:
        swap -= 1
    fout = open('outofplace.out','w')
    fout.write(str(swap) + '\n')
    fout.close()
    


lineup = readInput()
main(lineup)
