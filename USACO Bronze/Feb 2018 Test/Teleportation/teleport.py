"""
ID: shivara2
LANG: PYTHON3
TASK: teleport
"""

def readInput():
    fin = open('teleport.in','r')
    data = fin.readlines()
    l = list(map(lambda x: int(x),data[0].split()))
    location = [l[0],l[1]]
    teleporter = [l[2],l[3]]
    fin.close()
    return location,teleporter

def main(location,teleporter):
    #Distance without the teleporter
    d1 = abs(location[1] - location[0])

    #Enter the teleporter at t1
    d2 = abs(teleporter[0] - location[0]) + abs(teleporter[1] - location[1])

    #Enter the teleporter at t2
    d3 = abs(teleporter[1] - location[0]) + abs(teleporter[0] - location[1])

    #Write Output
    minDistance = min(d1,d2,d3)
    fout = open('teleport.out','w')
    fout.write(str(minDistance) + '\n')
    fout.close()


location,teleporter = readInput()
main(location,teleporter)
