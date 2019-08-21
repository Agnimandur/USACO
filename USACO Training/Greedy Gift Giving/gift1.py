"""
ID: shivara2
LANG: PYTHON3
TASK: gift1
"""
import math

def readInput():
    fin = open('gift1.in','r')
    data = fin.readlines()
    fin.close()
    NP = int(data[0].strip())
    balances = {}
    for i in range(1,NP+1):
        balances[data[i].strip()] = 0
    transactions = ''.join(data[NP+1:])
    transactions = transactions.split()
    return balances,transactions

def changeBalances(balances,gift):
    balances[gift[0]] -= int(gift[1])
    balances[gift[0]] += int(gift[1]) % int(gift[2])
    #Use Import Math
    payout = math.floor(int(gift[1])/int(gift[2]))
    for j in range(3,len(gift)):
        balances[gift[j]] += payout

def allTransactions(transactions):
    while len(transactions) > 0:
        #Edge Case for Stingy Friends
        if not transactions[2] == '0':
            changeBalances(balances,transactions[:(3+int(transactions[2]))])
        transactions = transactions[(3+int(transactions[2])):]

def writeOutput(balances):
    fout = open('gift1.out','w')
    for key in balances:
        fout.write(key + " " + str(balances[key]) + '\n')
    fout.close()

#Caculate Balances
balances = readInput()[0]
transactions = readInput()[1]
allTransactions(transactions)
writeOutput(balances)
