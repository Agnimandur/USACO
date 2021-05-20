"""
ID: shivara2
LANG: PYTHON3
TASK: diamond
"""

def readInput():
    fin = open('diamond.in','r')
    data = fin.readlines()
    l = list(map(int,data[0].split()))
    K = l[1]
    diamonds = []
    for i in range(1,len(data)):
        diamonds.append(int(data[i]))
    return sorted(diamonds),K

def main(diamonds,K):
    mostDiamonds = 0
    diamondDict = {}
    for item in diamonds:
        if item not in diamondDict:
            diamondDict[item] = 0
        else:
            diamondDict[item] += 1
    for i in range(0,len(diamonds)-1):
        mostDiamonds = max(mostDiamonds,binSch(i,diamonds,diamondDict,K))

    fout = open('diamond.out','w')
    fout.write(str(mostDiamonds) + '\n')
    fout.close()

def binSch(i,diamonds,diamondDict,K):
    #i is the index of the smallest item in the collection.
    #chunk is the truncated list on which the binary search is performed on
    #target is the maximum size of a diamond in this collection
    chunk = diamonds[i:]
    target = diamonds[i] + K
    while 1 == 1:
        ind = int(len(chunk)/2)
        if len(chunk) <= 1 or chunk[ind] == target:
            m = chunk[ind]
            break
        elif chunk[ind] > target:
            chunk = chunk[:ind]
        else:
            chunk = chunk[ind:]

    #m = the largest diamond in the list <= target
    return diamonds.index(m) + diamondDict[m] - i + 1

diamonds,K = readInput()
main(diamonds,K)
