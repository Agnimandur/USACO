"""
ID: shivara2
LANG: PYTHON3
TASK: cbarn
"""

def readInput():
    fin = open('cbarn.in','r')
    data = fin.readlines()
    roomWalk = []
    N = int(data[0])
    for i in range(1,len(data)):
        roomWalk.append(int(data[i]))
    roomWalk = roomWalk * 2
    del roomWalk[-1]
    fin.close()
    return N,roomWalk

def main(N,roomWalk):
    distances = []
    for start in range(0,N):
        room = roomWalk[start:start+N]
        distances.append(travel(N,room))

    fout = open('cbarn.out','w')
    fout.write(str(min(distances)) + '\n')
    fout.close()

def travel(N,room):
    distance = 0
    for i in range(0,N):
        distance += i * room[i]
    return distance

N,roomWalk = readInput()
main(N,roomWalk)
