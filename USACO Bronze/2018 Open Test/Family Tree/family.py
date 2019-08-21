"""
ID: shivara2
LANG: PYTHON3
TASK: family
"""

def readInput():
    fin = open('family.in','r')
    data = fin.readlines()
    Bessie = data[0].split()[1]
    Elsie = data[0].split()[2]
    familyTree = {}
    for i in range(1,len(data)):
        rel = data[i].split()
        familyTree[rel[1]] = rel[0]
    fin.close()
    return Bessie,Elsie,familyTree

def main(Bessie,Elsie,ancestor,ageDiff):
    #Not Related
    if ancestor == 0 and ageDiff == 0:
        return "NOT RELATED"
    #Sisters (same age bracket,common ancestor = Mother)
    elif ancestor == 1 and ageDiff == 0:
        return "SIBLINGS"
    #Direct descendants
    elif ancestor == ageDiff:
        if ancestor == 1:
            title = "mother"
        elif ancestor == 2:
            title = "grand-mother"
        else:
            title = "great-" * (ancestor-2) + "grand-mother"
        return Elsie + " is the " + title + " of " + Bessie
    #Some level of aunts. Common ancestor goes back one level further than the age difference.
    elif ancestor == ageDiff + 1:
        if ancestor == 2:
            title = "aunt"
        else:
            title = "great-" * (ancestor-2) + "aunt"
        return Elsie + " is the " + title + " of " + Bessie
    else:
        return "COUSINS"

def commonAncestor(Bessie,Elsie,familyTree):
    BessieAncestors = []
    i = Bessie
    while i in familyTree.keys():
        BessieAncestors.append(familyTree[i])
        i = familyTree[i]

    ElsieAncestors = []
    i = Elsie
    while i in familyTree.keys():
        ElsieAncestors.append(familyTree[i])
        i = familyTree[i]

    ancestor = 0
    ageDiff = 0
    if Elsie not in BessieAncestors:
        for i in range(0,
                       len(BessieAncestors)):
            if BessieAncestors[i] in ElsieAncestors:
                ancestor = i + 1
                ageDiff = i - ElsieAncestors.index(BessieAncestors[i])
                break
    else:
        ancestor = BessieAncestors.index(Elsie) + 1
        ageDiff = ancestor
    return ancestor,ageDiff


Bessie,Elsie,familyTree = readInput()
ancestor,ageDiff = commonAncestor(Bessie,Elsie,familyTree)
#determine whether Bessie or Elsie is older
if ageDiff <= 0:
    temp = Elsie
    Elsie = Bessie
    Bessie = temp
    ancestor,ageDiff = commonAncestor(Bessie,Elsie,familyTree)
relation = main(Bessie,Elsie,ancestor,ageDiff)
fout = open('family.out','w')
fout.write(relation + '\n')
fout.close()
