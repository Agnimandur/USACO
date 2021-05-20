"""
ID: shivara2
LANG: PYTHON3
TASK: promote
"""

def readInput():
    fin = open('promote.in','r')
    data = fin.readlines()
    before = []
    after = []
    for i in range(0,len(data)):
        n = list(map(int,data[i].split()))
        before.append(n[0])
        after.append(n[1])
    newGuys = sum(after) - sum(before)
    #All the newGuys start in the Bronze Division
    before[0] += newGuys
    fin.close()
    return before,after

def main(before,after):
    promotedList = [0,0,0]
    for i in range(0,3):
        promoted = before[i] - after[i]
        before[i] -= promoted
        before[i+1] += promoted
        promotedList[i] = promoted

    #writeOutput
    fout = open('promote.out','w')
    for i in range(0,3):
        fout.write(str(promotedList[i]) + '\n')
    fout.close()


before,after = readInput()
main(before,after)
