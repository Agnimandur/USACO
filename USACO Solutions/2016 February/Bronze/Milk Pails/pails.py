"""
ID: shivara2
LANG: PYTHON3
TASK: pails
"""

def readInput():
    fin = open('pails.in','r')
    data = fin.readlines()
    pails = data[0].split()
    X = int(pails[0])
    Y = int(pails[1])
    M = int(pails[2])
    fin.close()
    return X,Y,M

def main(X,Y,M):
    maxMilk = 0
    maxSize = int(M/X) + 1
    for i in range(0,maxSize):
        for j in range(0,maxSize):
            n = X * i + Y * j
            if n <= M and n > maxMilk:
                maxMilk = n
    fout = open('pails.out','w')
    fout.write(str(maxMilk) + '\n')
    fout.close()
    

X,Y,M = readInput()
main(X,Y,M)

