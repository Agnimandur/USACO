"""
ID: shivara2
LANG: PYTHON3
TASK: milkorder
"""

def readInput():
    fin = open('milkorder.in','r')
    data = fin.readlines()
    N = int(data[0].split()[0])
    order = [0] * N
    hierarchy = list(map(lambda x: int(x),data[1].split()))
    #The results of K are inputted in the prelimary order
    for i in range(2,len(data)):
        Klist = list(map(lambda x: int(x),data[i].split()))
        order[Klist[1]-1] = Klist[0]
    fin.close()
    return order,hierarchy

def main(order,hierarchy):
    lastIndex = len(order) - 1
    while len(hierarchy) > 0:
        if hierarchy[-1] not in order:
            for i in range(lastIndex,-1,-1):
                if order[i] == 0:
                    order[i] = hierarchy[-1]
                    lastIndex = i
                    del hierarchy[-1]
                    break
        else:
            lastIndex = order.index(hierarchy[-1])
            del hierarchy[-1]

    #Find where the ill cow goes
    if 1 not in order:
        for i in range(0,len(order)):
            if order[i] == 0:
                illCow = i+1
                break
    else:
        illCow = order.index(1) + 1
    return illCow

def illHierarchy(order,hierarchy):
    i = 0
    while i < len(order):
        if hierarchy[0] in order:
            i = order.index(hierarchy[0]) + 1
            del hierarchy[0]
        elif order[i] == 0:
            order[i] = hierarchy[0]
            if hierarchy[0] == 1:
                return i+1
            del hierarchy[0]
            i += 1
        else:
            i += 1
            
order,hierarchy = readInput()
if 1 in order:
    illCow = order.index(1) + 1
elif 1 in hierarchy:
    illCow = illHierarchy(order,hierarchy)
else:
    illCow = main(order,hierarchy)

fout = open('milkorder.out','w')
fout.write(str(illCow) + '\n')
fout.close()
