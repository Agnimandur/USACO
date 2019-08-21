"""
ID: shivara2
LANG: PYTHON3
TASK: guess
"""

def readInput():
    fin = open('guess.in','r')
    data = fin.readlines()
    traits = []
    N = int(data[0])
    for i in range(1,len(data)):
        traits.append(data[i].split()[2:])
    fin.close()
    return traits,N

def main(traits,N):
    M = 0
    for i in range(0,N-1):
        for j in range(i+1,N):
            a = set(traits[i])
            b = set(traits[j])
            c = a.intersection(b)
            M = max(M,len(c))
    
    fout = open('guess.out','w')
    fout.write(str(M+1) + '\n')
    fout.close()

traits,N = readInput()
main(traits,N)
