"""
ID: shivara2
LANG: PYTHON3
TASK: badmilk
"""

def readInput():
    fin = open('badmilk.in','r')
    data = fin.readlines()
    milkLog = []
    sickLog = {}
    M = list(map(int,data[0].split()))[1]
    D = list(map(int,data[0].split()))[2]
    for i in range(1,len(data)):
        if i <= D:
            milkLog.append(list(map(int,data[i].split())))
        else:
            n = list(map(int,data[i].split()))
            sickLog[n[0]] = n[1]
    fin.close()
    return M,milkLog,sickLog

def main(M,milkLog,sickLog):
    sick = list(sickLog.keys())
    badMilk = set()
    for i in range(0,M):
        badMilk.add(i+1)

    #Determine the real Bad Milk
    for person in sick:
        badMilk = badMilk.intersection(set(contamination(milkLog,sickLog,person)))

    #Determine who might get sick
    couldFallSick = []
    for entry in milkLog:
        if entry[1] in badMilk:
            couldFallSick.append(entry[0])
    numIll = len(set(couldFallSick))
    fout = open('badmilk.out','w')
    fout.write(str(numIll) + '\n')
    fout.close()

def contamination(milkLog,sickLog,person):
    contaminated = []
    for entry in milkLog:
        #The entry matches the sick person at the right time
        if entry[0] == person and entry[2] < sickLog[person]:
            contaminated.append(entry[1])
    return contaminated


M,milkLog,sickLog = readInput()
main(M,milkLog,sickLog)
