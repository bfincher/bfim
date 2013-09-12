from django.conf.urls import patterns, url

urlpatterns = patterns('bfim.views',
    url(r'^bfim/$', 'index'),
    url(r'content/.*', 'content'),
    url(r'^$', 'content')
    )
