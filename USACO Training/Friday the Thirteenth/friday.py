"""
ID: shivara2
LANG: PYTHON3
TASK: friday
"""

fin = open('friday.in','r')
data = fin.readlines()
fin.close()
endYear = 1900 + int(data[0].strip())
answer = {0:0,1:0,2:0,3:0,4:0,5:0,6:0}

year = 1900
dayOfTheWeek = 2
month = 1
date = 1

while year != endYear:
    if date == 13:
        answer[dayOfTheWeek] += 1
    dayOfTheWeek += 1
    dayOfTheWeek = dayOfTheWeek % 7
    date += 1
    if month == 2:
        if (year == 2000) or ((year % 4 == 0) and (not year % 100 == 0)):
            date = ((date - 1) % 29) + 1
        else:
            date = ((date - 1) % 28) + 1
    elif month in [4,6,9,11]:
        date = ((date - 1) % 30) + 1
    else:
        date = ((date - 1) % 31) + 1
    if date == 1:
        month += 1
        month = ((month - 1) % 12) + 1
    if date == 1 and month == 1:
        year += 1

submit = ''
for key in answer:
    submit = submit + str(answer[key]) + " "
submit = submit[0:-1]
submit = submit + '\n'

fout = open('friday.out','w')
fout.write(submit)
fout.close()
