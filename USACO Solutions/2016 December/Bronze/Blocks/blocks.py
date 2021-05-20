"""
ID: shivara2
LANG: PYTHON3
TASK: blocks
"""

def readInput():
    fin = open('blocks.in','r')
    data = fin.readlines()
    tiles = []
    for i in range(1,len(data)):
        tiles.append(data[i].strip())
    fin.close()
    return tiles

def main(tiles):
    letters = {}
    for i in range(97,123):
        letters[chr(i)] = 0
    for board in tiles:
        splitBoard = board.split()
        print(letters)
        for i in range(97,123):
            letters[chr(i)] += max(splitBoard[0].count(chr(i)),splitBoard[1].count(chr(i)))
    return letters

def writeOutput(letters):
    fout = open('blocks.out','w')
    for key in sorted(letters):
        fout.write(str(letters[key]) + '\n')
    fout.close()

tiles = readInput()
letters = main(tiles)
writeOutput(letters)
