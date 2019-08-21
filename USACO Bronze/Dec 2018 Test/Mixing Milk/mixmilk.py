"""
ID: shivara2
LANG: PYTHON3
TASK: mixmilk
"""

def readInput():
    fin = open('mixmilk.in','r')
    data = fin.readlines()
    capacity = []
    startingMilk = []
    for i in range(0,3):
        l = list(map(int,data[i].split()))
        capacity.append(l[0])
        startingMilk.append(l[1])
    fin.close()
    return capacity,startingMilk

def main(capacity,startingMilk):
    bucket1 = startingMilk[0]
    bucket2 = startingMilk[1]
    bucket3 = startingMilk[2]
    for i in range(0,33):
        #pour 1 -> 2
        if bucket1 + bucket2 > capacity[1]:
            bucket1 -= capacity[1] - bucket2
            bucket2 = capacity[1]
        else:
            bucket2 += bucket1
            bucket1 = 0

        #pour 2 -> 3
        if bucket2 + bucket3 > capacity[2]:
            bucket2 -= capacity[2] - bucket3
            bucket3 = capacity[2]
        else:
            bucket3 += bucket2
            bucket2 = 0

        #pour 3 -> 1
        if bucket3 + bucket1 > capacity[0]:
            bucket3 -= capacity[0] - bucket1
            bucket1 = capacity[0]
        else:
            bucket1 += bucket3
            bucket3 = 0

    #pour 1 -> 2 (the 100th pour)
    if bucket1 + bucket2 > capacity[1]:
        bucket1 -= capacity[1] - bucket2
        bucket2 = capacity[1]
    else:
        bucket2 += bucket1
        bucket1 = 0

    #Write Output
    fout = open('mixmilk.out','w')
    fout.write(str(bucket1) + '\n')
    fout.write(str(bucket2) + '\n')
    fout.write(str(bucket3) + '\n')
    fout.close()

capacity,startingMilk = readInput()
main(capacity,startingMilk)
