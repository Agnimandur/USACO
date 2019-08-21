"""
ID: shivara2
LANG: PYTHON3
TASK: shuffle
"""

def readInput():
    fin = open('shuffle.in','r')
    data = fin.readlines()
    shuffle = list(map(lambda x: int(x), data[1].split()))
    IDs = data[2].split()
    fin.close()
    return shuffle,IDs

def main(shuffle,IDs):
    for i in range(0,3):
        IDs = reverseShuffle(shuffle,IDs)
    fout = open('shuffle.out','w')
    for ID in IDs:
        fout.write(ID)
        fout.write('\n')
    fout.close()


def reverseShuffle(shuffle,IDs):
    newIDs = [""] * len(shuffle)
    for i in range(0,len(shuffle)):
        newIDs[i] = IDs[shuffle[i] - 1]
    return newIDs
        
        

shuffle,IDs = readInput()
main(shuffle,IDs)
