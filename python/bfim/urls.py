from django.conf.urls import patterns, url

urlpatterns = patterns('bfim.views',
    url(r'^$', 'index'),
    url(r'content/.*', 'content')
    )
