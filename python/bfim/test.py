'''
Created on Sep 11, 2012

@author: S149450
'''

from django.test import TestCase

from .classes import getFromWeb
import datetime
from bfim.classes import formatPercentStr

class ClassesTest(TestCase):


    def testGetFromWeb(self):
        begin = datetime.date(2011, 1, 1)    
        print(begin)
        entries = getFromWeb(begin, datetime.date.today())
        print(entries)
    

#if __name__ == '__main__':
#    main()
