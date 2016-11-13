from django.shortcuts import render_to_response
import datetime
from .classes import getFromWeb, parseDateMMDDYYYY
from .constants import _constants as const

def index(request):
    daysPerYear = 365.24
    lastYearDate = datetime.date.today() - datetime.timedelta(days=(daysPerYear))
    
    lastYear = formatDate(lastYearDate)
    today = formatDate(datetime.date.today())
    
    return render_to_response('bfim/index.html', 
        {'last_year' : lastYear,
         'today' : today,
	 'const' : const})
    
def content(request):
    beginDate = parseDateMMDDYYYY(request.GET['beginDate'])
    endDate = parseDateMMDDYYYY(request.GET['endDate'])
    
    entries = getFromWeb(beginDate, endDate)
    
    return render_to_response('bfim/bfim.html',
        {'entries' : entries,
         'beginDate' : beginDate,
         'endDate' : endDate,
	 'const' : const})

def formatDate(date):
    dateStr = str(date.month) + '-' + str(date.day) + '-' + str(date.year)
    return dateStr
