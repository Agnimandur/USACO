"""
ID: shivara2
LANG: PYTHON3
TASK: bcs
"""

def readInput():
    fin = open('bcs.in','r')
    data = fin.readlines()
    l = list(map(int,data[0].split()))
    N = l[0]
    K = l[1]
    cowFig = []
    for i in range(1,N+1):
        temp = list(data[i].strip())
        cowFig.append(temp)
    fragments = []
    for i in range(0,K):
        temp = []
        for j in range(0,N):
            temp.append(list(data[N*i + j + N + 1].strip()))
        fragments.append(temp)

    for i in range(0,N):
        for j in range(0,N):
            if cowFig[i][j] == '.':
                cowFig[i][j] = 0
            else:
                cowFig[i][j] = 1
    for i in range(0,K):
        for j in range(0,N):
            for k in range(0,N):
                if fragments[i][j][k] == '.':
                    fragments[i][j][k] = 0
                else:
                    fragments[i][j][k] = 1
    fin.close()
    return N,cowFig,fragments

def main(N,cowFig,fragments):
    for frag1 in fragments:
        for frag2 in fragments:
            if fragments.index(frag1) < fragments.index(frag2):
                cases1 = rearrange(N,frag1)
                cases2 = rearrange(N,frag2)
                for arrangement1 in cases1:
                    for arrangement2 in cases2:
                        if combine(N,cowFig,arrangement1,arrangement2) == True:
                            A = fragments.index(frag1) + 1
                            B = fragments.index(frag2) + 1
                            #Write Output
                            fout = open('bcs.out','w')
                            fout.write(str(A) + " " + str(B) + '\n')
                            fout.close()
                            break

def combine(N,cowFig,frag1,frag2):
    tempL = []
    for i in range(0,N):
        t = []
        for j in range(0,N):
            t.append(0)
        tempL.append(t)

    for i in range(0,N):
        for j in range(0,N):
            if frag1[i][j] == 1 and frag2[i][j] == 1:
                return False
            if frag1[i][j] == 1 or frag2[i][j] == 1:
                tempL[i][j] = 1
    return tempL == cowFig

def rearrange(N,frag):
    rowNum = []
    colNum = []
    for i in range(0,N):
        for j in range(0,N):
            if frag[i][j] == 1:
                rowNum.append(i)
                colNum.append(j)
    #Pos => moving downwards, Neg => moving up
    rowMap = []
    #zero means no movement
    #Pos => moving rightwards, Neg => moving left
    colMap = []
    for i in range(-N,N):
        n1 = list(map(lambda x: x + i, rowNum))
        n2 = list(map(lambda x: x + i, colNum))
        if min(n1) >= 0 and max(n1) < N:
            rowMap.append(i)
        if min(n2) >= 0 and max(n2) < N:
            colMap.append(i)
    
    #All possible re-arrangements
    cases = []
    for rowS in rowMap:
        for colS in colMap:
            cases.append(move(N,rowS,colS,frag))
    return cases

def move(N,rowS,colS,frag):
    tempL = []
    for i in range(0,N):
        t = []
        for j in range(0,N):
            t.append(0)
        tempL.append(t)

    for i in range(0,N):
        for j in range(0,N):
            if frag[i][j] == 1:
                tempL[i+rowS][j+colS] = 1
    return tempL

N,cowFig,fragments = readInput()
main(N,cowFig,fragments)
