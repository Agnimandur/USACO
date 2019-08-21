"""
ID: shivara2
LANG: PYTHON3
TASK: measurement
"""

def readInput():
    fin = open('measurement.in','r')
    data = fin.readlines()
    logbook = []
    for i in range(1,len(data)):
        logbook.append(data[i].split())
    for entry in logbook:
        entry[0] = int(entry[0])
        sign = entry[2][0]
        number = int(entry[2][1:])
        del entry[2]
        entry.append(sign)
        entry.append(number)
    
    return sorted(logbook)

def main(logbook):
    milkAmount = {'Bessie':7, 'Elsie':7, 'Mildred':7}
    leadCow = {'Bessie','Elsie','Mildred'}
    counter = 0
    for entry in logbook:
        if entry[2] == '+':
            milkAmount[entry[1]] += entry[3]
        else:
            milkAmount[entry[1]] -= entry[3]

        maxMilkAmount = max(list(milkAmount.values()))
        newSetofLeadCow = set()
        for key in milkAmount:
            if milkAmount[key] == maxMilkAmount:
                newSetofLeadCow.add(key)
        if newSetofLeadCow != leadCow:
            leadCow = newSetofLeadCow
            counter += 1
    return counter

logbook = readInput()
milkChanges = main(logbook)
fout = open('measurement.out','w')
fout.write(str(milkChanges) + '\n')
fout.close()
