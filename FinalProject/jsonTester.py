#!/usr/bin/env python

import json, ast

data_file = open('JSONTEST.txt','r')
data = data_file.read()
data_file.close()

data = json.loads(data)
print(data)
data = data['userdata']
print(data)
datadict = {}
for i in data:
    #print(i)
    datadict[i['username']]=i['weight']
print (datadict)
print (json.dumps(datadict['keviniscool']))

lit_eval = "{\n\t'Info':{\n\t\t'the':'the','is':'cat'\n\t}\n}"
lit_eval = ast.literal_eval(lit_eval)
print(lit_eval)
lit_eval = "{'the':'the','is':'cat'}"
lit_eval = ast.literal_eval(lit_eval)
print(lit_eval)
print(lit_eval['is'])