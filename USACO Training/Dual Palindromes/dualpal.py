"""
ID: shivara2
LANG: PYTHON3
TASK: dualpal
"""
import math

def readInput():
    fin = open('dualpal.in','r')
    data = fin.readlines()
    data = data[0].strip().split()
    N = int(data[0])
    S = int(data[1])
    fin.close()
    return N,S

def findMultiplePals(N,S):
    numberList = []
    n = S
    while len(numberList) < N:
        if palindrome(n,0):
            numberList.append(n)
        n += 1
    return numberList

def palindrome(S,counter):
    for i in range(2,11):
        if translate(S,i,'') == translate(S,i,'')[::-1]:
            counter += 1
    return (counter > 1)

def translate(n,B,answer):
    if n > 0:
        answer += str(n%B)
        n = math.floor(n/B)
    else:
        return answer[::-1]
    return translate(n,B,answer)

def writeOutput(numberList):
    fout = open('dualpal.out','w')
    for i in range(0,len(numberList)):
        fout.write(str(numberList[i]) + '\n')
    fout.close()

N,S = readInput()
numberList = findMultiplePals(N,S+1)
writeOutput(numberList)
