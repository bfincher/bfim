'''
Created on Sep 11, 2012

@author: S149450
'''

import classes
from classes import SandPEntry
import datetime
from bfim.classes import formatPercentStr

def main():
#    entry = SandPEntry('date', 'price')
#    print(entry.price + entry.date)

    begin = datetime.date(2011, 1, 1)    
    print begin
    entries = classes.getFromWeb(begin, datetime.date.today())
    print entries
    

if __name__ == '__main__':
    main()