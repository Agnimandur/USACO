"""
ID: shivara2
LANG: PYTHON3
TASK: palsquare
"""
import math

def readInput():
    fin = open('palsquare.in','r')
    data = fin.readlines()
    B = int(data[0])
    fin.close()
    return B

def translate(n,B,answer):
    translate_list = ['0','1','2','3','4','5','6','7','8','9','A',
                      'B','C','D','E','F','G','H','I','J']
    if n > 0:
        answer += translate_list[n%B]
        n = math.floor(n/B)
    else:
        return answer[::-1]
    return translate(n,B,answer)

def writeOutput(B):     
    fout = open('palsquare.out','w')
    for i in range(1,301):
        palindrome = translate(i**2,B,'')
        if palindrome == palindrome[::-1]:
            fout.write(translate(i,B,'') + " " + palindrome + '\n')
    fout.close()

B = readInput()
writeOutput(B)

    
    
