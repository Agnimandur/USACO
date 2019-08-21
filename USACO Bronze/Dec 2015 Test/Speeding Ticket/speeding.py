"""
ID: shivara2
LANG: PYTHON3
TASK: speeding
"""

def readInput():
    fin = open('speeding.in','r')
    data = fin.readlines()
    speedLimit = []
    N = list(map(int,data[0].split()))[0]
    M = list(map(int,data[0].split()))[1]
    for i in range(1,N+1):
        roadBlock = list(map(int,data[i].split()))
        speedLimit += [roadBlock[1]] * roadBlock[0]

    cowDrive = []
    for i in range(N+1,len(data)):
        roadBlock = list(map(int,data[i].split()))
        cowDrive += [roadBlock[1]] * roadBlock[0]
    fin.close()
    return speedLimit,cowDrive

def main(speedLimit,cowDrive):
    breakingLaw = []
    for i in range(0,100):
        breakingLaw.append(max(cowDrive[i] - speedLimit[i],0))
    fout = open('speeding.out','w')
    fout.write(str(max(breakingLaw)) + '\n')
    fout.close()

speedLimit,cowDrive = readInput()
main(speedLimit,cowDrive)
