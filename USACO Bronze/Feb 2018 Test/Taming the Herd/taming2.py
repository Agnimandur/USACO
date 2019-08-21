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

def main(breakouts):
    possibleLog = [-1] * len(breakouts)
    for i in range(len(breakouts) - 1,-1,-1):
        currentVal = breakouts[i]
        if currentVal != -1:
            for j in range(i-1,-1,-1):
                currentVal -= 1
                if possibleLog[j] != currentVal:
                    return -1
                else:
                    possibleLog[j] == currentVal
                if currentVal == 0:
                    break
    return possibleLog

breakouts = readInput()
if breakouts[0] == 0 or breakouts[0] == -1:
    breakouts[0] = 0
    print(main(breakouts))
else:
    print(-1)
    
