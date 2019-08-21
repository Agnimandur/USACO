"""
ID: shivara2
LANG: PYTHON3
TASK: blist
"""

def readInput():
    fin = open('blist.in','r')
    data = fin.readlines()
    milkInfo = []
    for i in range(1,len(data)):
        milkInfo.append(list(map(int,data[i].split())))
    fin.close()
    return sorted(milkInfo)

def main(milkInfo):
    startTimes = [x[0] for x in milkInfo]
    endTimes = [x[1] for x in milkInfo]
    buckets = [x[2] for x in milkInfo]
    inUse = 0
    maxBuckets = 0
    for time in range(1,max(endTimes)+1):
        if time in startTimes:
            b = buckets[startTimes.index(time)]
            inUse += b
            maxBuckets = max(maxBuckets,inUse)
        if time in endTimes:
            b = buckets[endTimes.index(time)]
            inUse -= b

    #Write Output
    fout = open('blist.out','w')
    fout.write(str(maxBuckets) + '\n')
    fout.close()

milkInfo = readInput()
main(milkInfo)
