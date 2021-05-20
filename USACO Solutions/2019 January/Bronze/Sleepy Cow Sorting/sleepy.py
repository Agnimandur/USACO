"""
ID: shivara2
LANG: PYTHON3
TASK: sleepy
"""

def readInput():
    fin = open('sleepy.in','r')
    data = fin.readlines()
    cowLine = list(map(int,data[1].split()))
    fin.close()
    return cowLine,len(cowLine)

def main(cowLine,N):
    num = cowLine[N-1]
    counter = N-1
    for i in range(N-2,-1,-1):
        if cowLine[i] > num:
            break
        num = cowLine[i]
        counter -= 1

    fout = open('sleepy.out','w')
    fout.write(str(counter) + '\n')
    fout.close()

cowLine,N = readInput()
main(cowLine,N)
