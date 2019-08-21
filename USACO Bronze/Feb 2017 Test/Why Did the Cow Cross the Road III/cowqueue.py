"""
ID: shivara2
LANG: PYTHON3
TASK: cowqueue
"""

def readInput():
    fin = open('cowqueue.in','r')
    data = fin.readlines()
    cowSchedule = []
    for i in range(1,len(data)):
        cowSchedule.append(data[i].split())
    for i in range(0,len(cowSchedule)):
        cowSchedule[i][0] = int(cowSchedule[i][0])
        cowSchedule[i][1] = int(cowSchedule[i][1])
    fin.close()
    return sorted(cowSchedule)

def main(cowSchedule):
    time = 0
    for i in range(0,len(cowSchedule)):
        time = max(time,cowSchedule[i][0])
        time += cowSchedule[i][1]
    return time

def writeOutput(time):
    fout = open('cowqueue.out','w')
    fout.write(str(time) + '\n')
    fout.close()

cowSchedule = readInput()
print(cowSchedule)
time = main(cowSchedule)
writeOutput(time)







#End at 2:14pm
