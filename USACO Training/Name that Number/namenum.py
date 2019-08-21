"""
ID: shivara2
LANG: PYTHON3
TASK: namenum
"""
import math

def readInput():
    fin = open('namenum.in','r')
    data = fin.readlines()
    for i in range(0,len(data)):
        data[i] = data[i].strip()
    data = data[0]
    fin.close()
    fin2 = open('dict.txt','r')
    nameList = fin2.readlines()
    for i in range(0,len(nameList)):
        nameList[i] = nameList[i].strip()
    fin2.close()
    return data,nameList

def completePurge(serialDigit,data,inputList,n):
    allowedLetters = letters(serialDigit)
    for i in range(0,len(inputList)):
        if inputList[i][n] not in allowedLetters or len(inputList[i]) != len(data):
            inputList[i] = ''
    inputList = [x for x in inputList if x != '']
    #Recursion to make the code run fast
    n += 1
    if n < len(data):
        return completePurge(data[n],data,inputList,n)
    else:
        return inputList
            
def letters(serialDigit):
    if int(serialDigit) == 7:
        return ['P','R','S']
    elif int(serialDigit) == 8:
        return ['T','U','V']
    elif int(serialDigit) == 9:
        return ['W','X','Y']
    else:
        return [chr(59+int(serialDigit)*3),chr(60+int(serialDigit)*3),chr(61+int(serialDigit)*3)]

def writeOutput(answer):
    fout = open('namenum.out','w')
    if len(answer) == 0:
        fout.write('NONE\n')
    else:
        for i in range(0,len(answer)):
            fout.write(answer[i] + '\n')
    fout.close()

data,nameList = readInput()
answer = completePurge(data[0],data,nameList,0)
writeOutput(answer)
        
