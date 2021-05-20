"""
ID: shivara2
LANG: PYTHON3
TASK: shell
"""

def readInput():
    fin = open('shell.in','r')
    data = fin.readlines()
    guess = []
    swaps = []
    for i in range(1,len(
        data)):
        l = data[i].split()
        guess.append(int(l[2]))
        del l[2]
        swaps.append([int(l[0]),int(l[1])])
    fin.close()
    return guess,swaps

def main(guess,swaps):
    maxRight = 0
    maxRight = max(correct(guess,swaps,1),correct(guess,swaps,2),correct(guess,swaps,3))
    fout = open('shell.out','w')
    fout.write(str(maxRight) + '\n')
    fout.close()

def correct(guess,swaps,pebble):
    counter = 0
    for i in range(0,len(guess)):
        if pebble == swaps[i][0]:
            pebble = swaps[i][1]
        elif pebble == swaps[i][1]:
            pebble = swaps[i][0]
        else:
            pebble += 0

        if pebble == guess[i]:
            counter += 1

    return counter

guess,swaps = readInput()
main(guess,swaps)
