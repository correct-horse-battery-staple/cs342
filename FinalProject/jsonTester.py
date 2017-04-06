#!/usr/bin/env python

import json

data_file = open('JSONTEST.txt','r')
data = data_file.read()
data_file.close()

data = json.loads(data)
# print(data)
data = data['userdata']
# print(data)
datadict = {}
for i in data:
    #print(i)
    datadict[i['username']]=i['weight']
print (datadict)
print (json.dumps(datadict['keviniscool']))