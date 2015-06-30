import os
import sys
 
path = '/srv/www'
if path not in sys.path:
    sys.path.insert(0, '/srv/www/bfim')
 
os.environ['DJANGO_SETTINGS_MODULE'] = 'bfim_site.settings'
 
from django.core.wsgi import get_wsgi_application
application = get_wsgi_application()
