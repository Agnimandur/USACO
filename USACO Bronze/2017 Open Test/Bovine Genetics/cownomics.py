"""
ID: shivara2
LANG: PYTHON3
TASK: cownomics
"""

def readInput():
    fin = open('cownomics.in','r')
    data = fin.readlines()
    N = int((len(data) - 1)/2)
    spottedCows = []
    for i in range(1,N+1):
        spottedCows.append(data[i].strip())
    plainCows = []
    for i in range(N+1,2*N+1):
        plainCows.append(data[i].strip())
    fin.close()
    return spottedCows,plainCows

def main(spottedCows,plainCows):
    possibleGenes = 0
    for i in range(0,len(spottedCows[0])):
        scgene = []
        for j in range(0,len(spottedCows)):
            scgene.append(spottedCows[j][i])
        pcgene = []
        for j in range(0,len(plainCows)):
            pcgene.append(plainCows[j][i])
        possibleGenes += possibleGene(scgene,pcgene)
    return possibleGenes

def possibleGene(scgene,pcgene):
    if len(set(scgene).intersection(set(pcgene))) == 0:
        return 1
    else:
        return 0

def writeOutput(possibleGenes):
    fout = open('cownomics.out','w')
    fout.write(str(possibleGenes) + '\n')
    fout.close()

spottedCows,plainCows = readInput()
possibleGenes = main(spottedCows,plainCows)
writeOutput(possibleGenes)
