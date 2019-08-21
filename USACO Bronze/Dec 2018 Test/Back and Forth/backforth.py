"""
ID: shivara2
LANG: PYTHON3
TASK: backforth
"""

def readInput():
    fin = open('backforth.in','r')
    data = fin.readlines()
    tank1 = list(map(int,data[0].split()))
    tank2 = list(map(int,data[1].split()))
    fin.close()
    return sorted(tank1),sorted(tank2)

def main(tank1,tank2):
    milkAmounts = []
    for a in range(0,len(tank1)):
        for b in range(0,len(tank2)+1):
            for c in range(0,len(tank1)):
                for d in range(0,len(tank2)+1):
                    milkAmounts.append(pour(tank1,tank2,a,b,c,d))
    milkAmounts = list(set(milkAmounts))

    #Write Output
    fout = open('backforth.out','w')
    fout.write(str(len(milkAmounts)) + '\n')
    fout.close()

def pour(tank1,tank2,a,b,c,d):
    ntank1 = []
    ntank2 = []
    for i in range(0,len(tank1)):
        ntank1.append(tank1[i])
    for i in range(0,len(tank2)):
        ntank2.append(tank2[i])
    ntank2.append(0)
    #Monday
    milk = 1000
    
    #Tuesday
    i = ntank2.index(0)
    ntank2[i] = ntank1[a]
    milk -= ntank1[a]
    ntank1[a] = 0

    #Wednesday
    i = ntank1.index(0)
    ntank1[i] = ntank2[b]
    milk += ntank2[b]
    ntank2[b] = 0

    #Thursday
    i = ntank2.index(0)
    ntank2[i] = ntank1[c]
    milk -= ntank1[c]
    ntank1[c] = 0

    #Friday
    i = ntank1.index(0)
    ntank1[i] = ntank2[d]
    milk += ntank2[d]
    ntank2[d] = 0

    return milk
    
    
tank1,tank2 = readInput()
main(tank1,tank2)
