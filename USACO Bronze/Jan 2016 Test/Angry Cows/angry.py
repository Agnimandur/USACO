"""
ID: shivara2
LANG: PYTHON3
TASK: badmilk
"""

def readInput():
    fin = open('angry.in','r')
    data = fin.readlines()
    cows = []
    for i in range(1,len(data)):
        cows.append(int(data[i]))
    fin.close()
    return sorted(cows)

def main(cows):
    explosionSizes = []
    for bale in cows:
        explosionSizes.append(explosion(bale,cows))
    fout = open('angry.out','w')
    fout.write(str(max(explosionSizes)) + '\n')
    fout.close()

def explosion(target,cows):
    #Explosion Going to the Left
    indL = cows.index(target)
    blastL = 1
    while indL > 0 and cows[indL] - blastL <= cows[indL-1]:
        explodedList = [x for x in cows if cows[indL] - blastL <= x]
        indL = cows.index(explodedList[0])
        blastL += 1

    #Explosion Going to the Right
    indR = cows.index(target)
    blastR = 1
    while indR < len(cows)-1 and cows[indR] + blastR >= cows[indR+1]:
        explodedList = [x for x in cows if cows[indR] + blastR >= x]
        indR = cows.index(explodedList[-1])
        blastR += 1

    #All the bales between indL and indR have exploded
    return indR-indL+1

    

cows = readInput()
main(cows)
