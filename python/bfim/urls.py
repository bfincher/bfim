from django.conf.urls import url
from bfim.views import index, content

urlpatterns = [
    url(r'^$', index),
    url(r'content/.*', content)]
