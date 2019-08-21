"""
ID: shivara2
LANG: PYTHON3
TASK: balancing
"""

def readInput():
    fin = open('balancing.in','r')
    data = fin.readlines()
    l = list(map(int,data[0].split()))
    N = l[0]
    B = l[1]
    cows = []
    for i in range(1,len(data)):
        cows.append(list(map(int,data[i].split())))
    fin.close()
    return N,B,cows

def main(N,B,cows):
    #Horizontal and vertical fence
    hor = 0
    ver = 0

    #Simply consider the gaps between each cows coordinate, since any fence in between won't change the quadrant value
    xCoords = []
    yCoords = []
    for i in range(0,N):
        xCoords.append(cows[i][0])
        yCoords.append(cows[i][1])
    xCoords = sorted(list(set(xCoords)))
    yCoords = sorted(list(set(yCoords)))
    cowCount = []
    
    for x in range(0,len(xCoords)-1):
        for y in range(0,len(yCoords)-1):
            ver = int((xCoords[x] + xCoords[x+1])/2)
            hor = int((yCoords[y] + yCoords[y+1])/2)
            cowCount.append(balance(ver,hor,cows))
    
            

    #Write Output
    fout = open('balancing.out','w')
    fout.write(str(min(cowCount)) + '\n')
    fout.close()

def balance(ver,hor,cows):
    quadrant = [0,0,0,0] #Quadrants of the plane with hor,ver as the x/y axis
    for cow in cows:
        if cow[0] > ver and cow[1] > hor:
            quadrant[0] += 1
        elif cow[0] < ver and cow[1] > hor:
            quadrant[1] += 1
        elif cow[0] < ver and cow[1] < hor:
            quadrant[2] += 1
        else:
            quadrant[3] += 1
    return max(quadrant)

N,B,cows = readInput()
main(N,B,cows)
