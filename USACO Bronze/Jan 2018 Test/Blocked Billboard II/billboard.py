"""
ID: shivara2
LANG: PYTHON3
TASK: billboard
"""

def readInput():
    fin = open('billboard.in','r')
    data = fin.readlines()
    lawnmower = list(map(lambda x: int(x),data[0].split()))
    cowfeed = list(map(lambda x: int(x),data[1].split()))
    fin.close()
    return lawnmower,cowfeed

def main(xVisible,yVisible):
    fout = open('billboard.out','w')
    area = xVisible * yVisible
    fout.write(str(area) + '\n')
    fout.close()

def fullyCoverASide(lawnmower,cowfeed):
    #Complete X overlap
    yVisible = lawnmower[3] - lawnmower[1] 
    if (cowfeed[2] >= lawnmower[2] and cowfeed[0] <= lawnmower[0]):
        #intersects the bottom of the lawnmower
        if (cowfeed[1] <= lawnmower[1] and lawnmower[1] < cowfeed[3]) and (cowfeed[3] <= lawnmower[3]):
            yVisible = lawnmower[3] - cowfeed[3]
        #intersects the top of the lawnmower
        elif (cowfeed[3] >= lawnmower[3] and lawnmower[3] > cowfeed[1]) and (cowfeed[1] >= lawnmower[1]):
            yVisible = cowfeed[1] - lawnmower[1]
        #Fully covers the lawnmower
        elif cowfeed[1] <= lawnmower[1] and lawnmower[3] <= cowfeed[3]:
            yVisible = 0
        else:
            yVisible = lawnmower[3] - lawnmower[1]

    #Complete Y overlap
    xVisible = lawnmower[2] - lawnmower[0]
    if (cowfeed[3] >= lawnmower[3] and cowfeed[1] <= lawnmower[1]):
        #intersects the left of the lawnmower
        if (cowfeed[0] <= lawnmower[0] and lawnmower[0] < cowfeed[2]) and (cowfeed[2] <= lawnmower[2]):
            xVisible = lawnmower[2] - cowfeed[2]
        #intersects the right of the lawnmower
        elif (cowfeed[2] >= lawnmower[2] and lawnmower[2] > cowfeed[0]) and (cowfeed[0] >= lawnmower[0]):
            xVisible = cowfeed[0] - lawnmower[0]
        #fully covers the lawnmower
        elif cowfeed[0] <= lawnmower[0] and lawnmower[2] <= cowfeed[2]:
            xVisible = 0
        else:
            xVisible = lawnmower[2] - lawnmower[0]
    return xVisible,yVisible
        

lawnmower,cowfeed = readInput()
xVisible,yVisible = fullyCoverASide(lawnmower,cowfeed)
main(xVisible,yVisible)


