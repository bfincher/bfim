import urllib.request, urllib.error, urllib.parse
import datetime
import time

class SandPEntry:
    
    fourDays = 0.0
    eighteenDays = 0.0

    def __init__(self, price, date):
        self.date = date
        self.price = float(price)
        
    def __repr__(self):
        return str(self.date) + " " + str(self.price) + " " + str(self.fourDays) + " " + str(self.eighteenDays)            
        
        
__BUY_DAYS = 4
__SELL_DAYS = 18
__BUY_PERCENT = 0.035
__SELL_PERCENT = -0.075
__BEGIN_MONTH_TAG = '!!BEGIN_MONTH!!'
__BEGIN_DAY_TAG = '!!BEGIN_DAY!!'
__BEGIN_YEAR_TAG = '!!BEGIN_YEAR!!'
__END_MONTH_TAG = '!!END_MONTH!!'
__END_DAY_TAG = '!!END_DAY!!'
__END_YEAR_TAG = '!!END_YEAR!!'

__HISTORICAL_URL_STRING = "http://ichart.finance.yahoo.com/table.csv?s=%5EGSPC&a="
__HISTORICAL_URL_STRING +=  __BEGIN_MONTH_TAG + "&b=" 
__HISTORICAL_URL_STRING += __BEGIN_DAY_TAG + "&c=" 
__HISTORICAL_URL_STRING += __BEGIN_YEAR_TAG + "&d=" 
__HISTORICAL_URL_STRING += __END_MONTH_TAG + "&e=" 
__HISTORICAL_URL_STRING += __END_DAY_TAG + "&f=" 
__HISTORICAL_URL_STRING += __END_YEAR_TAG 
__HISTORICAL_URL_STRING += "&g=d&ignore=.csv";

def getFromWeb(beginDate, endDate):
    now = datetime.datetime.now().date()
    entries = []
    if (endDate >= now):
        url = "http://quote.yahoo.com/d/quotes.csv?s=^GSPC&f=sl1d1t1c1ohgv&e=.csv"
        fileHandle = urllib.request.urlopen(url)
        content = str(fileHandle.read())
        split = content.split(',')
        price = split[1]
        dateStr = split[2]
        dateStr = dateStr.replace('"', '')

        entry = SandPEntry(price, parseDateMMDDYYYYSlash(dateStr))
        entries.append(entry)

    url = __HISTORICAL_URL_STRING.replace(__BEGIN_MONTH_TAG, str(beginDate.month))
    url = url.replace(__BEGIN_DAY_TAG, str(beginDate.day))
    url = url.replace(__BEGIN_YEAR_TAG, str(beginDate.year))
    url = url.replace(__END_MONTH_TAG, str(endDate.month))
    url = url.replace(__END_DAY_TAG, str(endDate.day))
    url = url.replace(__END_YEAR_TAG, str(endDate.year))
    print(url)
        
    fileHandle = urllib.request.urlopen(url)
    content = str(fileHandle.read())
    lines = content.split('\n')
    fileHandle.close()
    
    lines = lines[1:] # skip the header line
    
    for line in lines:
        if (len(line) > 0) :
            split = line.split(',')
            try :
                dateStr = split[0]
                price = split[6]
            except IndexError :
                print(('line = ' + line + '.'))
        
            entry = SandPEntry(price, parseDateYYYYMMDD(dateStr))

            if (len(entries) == 0 or entry.date != entries[0].date):
                entries.append(entry)
            
    for i in range(len(entries)) :
        entry = entries[i]
        buyPercent = getCompValue(__BUY_DAYS, i, entries)
        sellPercent = getCompValue(__SELL_DAYS, i, entries)
        
        entry.fourDays = formatPercentStr(buyPercent)
        entry.eighteenDays = formatPercentStr(sellPercent)
        if sellPercent != None and sellPercent <= __SELL_PERCENT:
            entry.buySell = 'Sell'
        elif buyPercent != None and buyPercent >= __BUY_PERCENT:
            entry.buySell = 'Buy'
        else:
            entry.buySell = ''
        print((entries[i]))
        
    return entries

def getCompValue(numDays, index, entries):
    compIdx = index + numDays
    if compIdx < len(entries):
        thisPrice = entries[index].price
        compPrice = entries[compIdx].price
        delta = (thisPrice - compPrice) / compPrice
        return delta
    else:
        return None
    
def formatPercentStr(percent):
    if percent == None:
        return "N/A"
    else:
        s = "{0:2.1%}".format(percent)
        return s
        
def parseDateYYYYMMDD(dateStr):
    split = dateStr.split('-')
    date = datetime.date(int(split[0]), int(split[1]), int(split[2]))
    return date

def parseDateMMDDYYYY(dateStr):
    split = dateStr.split('-')
    date = datetime.date(int(split[2]), int(split[0]), int(split[1]))
    return date

def parseDateMMDDYYYYSlash(dateStr):
    split = dateStr.split('/')
    date = datetime.date(int(split[2]), int(split[0]), int(split[1]))
    return date
