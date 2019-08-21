"""
ID: shivara2
LANG: PYTHON3
TASK: paint
"""

def readInput():
    fin = open('paint.in','r')
    data = fin.readlines()
    FJPaint = list(map(int,data[0].split()))
    BessiePaint = list(map(int,data[1].split()))
    fin.close()
    return FJPaint,BessiePaint

def main(FJPaint,BessiePaint):
    #No Overlap
    if max(BessiePaint) < min(FJPaint) or max(FJPaint) < min(BessiePaint):
        paint = (FJPaint[1] - FJPaint[0]) + (BessiePaint[1] - BessiePaint[0])
    #Overlap
    else:
        paint = max(FJPaint[1],BessiePaint[1]) - min(FJPaint[0],BessiePaint[0])
    fout = open('paint.out','w')
    fout.write(str(paint) + "\n")
    fout.close()

FJPaint,BessiePaint = readInput()
main(FJPaint,BessiePaint)
