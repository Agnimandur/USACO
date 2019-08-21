"""
ID: shivara2
LANG: PYTHON3
TASK: crypt1
"""

def readInput():
    fin = open('crypt1.in','r')
    data = fin.readlines()
    digits = data[1].split()
    digits = [int(x) for x in digits]
    fin.close()
    return sorted(digits)

def possibleNumber(num,digits):
    stringNum = str(num)
    counter = 0
    for i in stringNum:
        if int(i) in digits:
            counter += 1
    return counter == len(stringNum)

def multiply(digits):
    x = len(digits)
    goodNumbers = 0
    for a in range(0,x):
        for b in range(0,x):
            for c in range(0,x):
                for d in range(0,x):
                    for e in range(0,x):
                        abc = 100*digits[a] + 10*digits[b] + digits[c]
                        if possibleNumber(abc * (10*digits[d] + digits[e]),digits):
                            if possibleNumber(abc * digits[d],digits) and len(str(abc * digits[d])) == 3:
                                if possibleNumber(abc * digits[e],digits) and len(str(abc * digits[e])) == 3:
                                    goodNumbers += 1
    return goodNumbers

def writeOutput(goodNumbers):
    fout = open('crypt1.out','w')
    fout.write(str(goodNumbers) + '\n')
    fout.close()

digits = readInput()
goodNumbers = multiply(digits)
writeOutput(goodNumbers)
