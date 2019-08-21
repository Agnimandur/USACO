"""
ID: shivara2
LANG: PYTHON3
TASK: ride
"""

fin = open('ride.in', 'r')
data = fin.readlines()
fin.close()

follower = data[0].strip()
followerNum = 1
for x in follower:
    followerNum *= (ord(x) - 64)

comet = data[1].strip()
cometNum = 1
for x in comet:
    cometNum *= (ord(x) - 64)

fout = open('ride.out','w')
followerNum = followerNum % 47
cometNum = cometNum % 47
if followerNum == cometNum:
    fout.write('GO\n')
else:
    fout.write('STAY\n')
fout.close()
