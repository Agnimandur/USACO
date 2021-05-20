"""
ID: shivara2
LANG: PYTHON3
TASK: billboard
"""

def readInput():
    fin = open('billboard.in','r')
    data = fin.readlines()
    billboard1 = list(map(lambda x: int(x),data[0].split()))
    billboard2 = list(map(lambda x: int(x),data[1].split()))
    truck = list(map(lambda x: int(x),data[2].split()))
    fin.close()
    return billboard1,billboard2,truck

def main(billboard1,billboard2,truck):
    billboardArea = (billboard1[2] - billboard1[0])*(billboard1[3] - billboard1[1]) + (billboard2[2] - billboard2[0])*(billboard2[3] - billboard2[1])
    coveredBillboard = billboardTruckOverlap(billboard1,truck) + billboardTruckOverlap(billboard2,truck)
    uncoveredBillboard = billboardArea - coveredBillboard
    fout = open('billboard.out','w')
    fout.write(str(uncoveredBillboard) + '\n')
    fout.close()
    
    
def billboardTruckOverlap(billboard,truck):
    #X-coordinates
    if (billboard[2] > truck[0]) and (billboard[2] < truck[2]):
        x = min(billboard[2] - truck[0],billboard[2] - billboard[0])
    elif (billboard[2] > truck[2]) and (billboard[0] < truck[2]):
        x = min(truck[2] - billboard[0],truck[2] - truck[0])
    else:
        x = 0
    #Y-coordinates
    if (billboard[3] > truck[1]) and (billboard[3] < truck[3]):
        y = min(billboard[3] - truck[1],billboard[3] - billboard[1])
    elif (billboard[3] > truck[3]) and (billboard[1] < truck[3]):
        y = min(truck[3] - billboard[1],truck[3] - truck[1])
    else:
        y = 0
    return x*y

billboard1,billboard2,truck = readInput()
main(billboard1,billboard2,truck)
