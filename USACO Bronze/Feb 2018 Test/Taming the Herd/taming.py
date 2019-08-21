"""
ID: shivara2
LANG: PYTHON3
TASK: taming
"""

def readInput():
    fin = open('taming.in','r')
    data = fin.readlines()
    breakouts = list(map(lambda x: int(x),data[1].split()))
    fin.close()
    return breakouts

def main(breakouts,possibleLog):
    #breakouts[len(possibleLog)] is the next term in breakouts, which is going to become the target of the recursion
    if len(possibleLog) == len(breakouts):
        return breakoutCount(possibleLog)
    elif breakouts[len(possibleLog)] == -1:
        return [main(breakouts,possibleLog + [possibleLog[-1] + 1]) + main(breakouts,possibleLog + [0])]
    elif breakouts[len(possibleLog)] == possibleLog[-1] + 1 or breakouts[len(possibleLog)] == 0:
        return main(breakouts,possibleLog + [breakouts[len(possibleLog)]])
    else:
        return []

def breakoutCount(log):
    counter = 0
    for num in log:
        if num == 0:
            counter += 1
    return [counter]

#Flatten Function taken from http://code.activestate.com/recipes/578948-flattening-an-arbitrarily-nested-list-in-python/
def flatten(lis):
    new_lis = []
    for item in lis:
        if type(item) == type([]):
            new_lis.extend(flatten(item))
        else:
            new_lis.append(item)
    return new_lis

breakouts = readInput()
#A contradiction occurs if breakouts[0] is NOT originally 0 or -1, OR if no possible combination arises.
N = breakouts[0]
breakouts[0] = 0
complexList = main(breakouts,[0])
flattenedList = flatten(complexList)
fout = open('taming.out','w')
if N <= 0 and len(flattenedList) > 0:
    fout.write(str(min(flattenedList)) + " " + str(max(flattenedList)) + '\n')
else:
    fout.write('-1\n')
fout.close()
