"""
ID: shivara2
LANG: PYTHON3
TASK: cowsignal
"""

def readInput():
    fin = open('cowsignal.in','r')
    data = fin.readlines()
    K = int(data[0].split()[2])
    signalList = []
    for i in range(1,len(data)):
        signalList.append(data[i].strip())
    fin.close()
    return K,signalList

def main(K,signalList):
    listOfStrings = []
    for string in signalList:
        emptyString = ''
        for i in range(0,len(string)):
            emptyString += K * string[i]
        for i in range(0,K):
            listOfStrings.append(emptyString)
    return listOfStrings

def writeOutput(listOfStrings):
    fout = open('cowsignal.out','w')
    for i in range(0,len(listOfStrings)):
        fout.write(listOfStrings[i] + '\n')
    fout.close()

K,signalList = readInput()
listOfStrings = main(K,signalList)
writeOutput(listOfStrings)
