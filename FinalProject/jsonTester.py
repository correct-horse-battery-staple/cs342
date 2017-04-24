#!/usr/bin/env python

import json, ast

data_file = open('JSONTEST.txt','r')
data = data_file.read()
data_file.close()

data = json.loads(data)
print(data)
print()
datadict = {}
# for i in data:
#     ar = {}
#     for j in i:
#     	if j != 'userhash':
#     		ar[j]=i[j]
#     datadict[i['userhash']]=ar
print(datadict)
print()
for i in datadict['keviniscool']:
	print(datadict['keviniscool'][i])
print()
datadict['keviniscool']['weight'].append({'value':'100','datetime':''})
datadict['keviniscool']['weight'].append({'value':'111','datetime':''})
print(datadict)
print()


dump = json.dumps(datadict)

with open('JSONTEST.txt','w') as content:
	content.write(dump)

# lit_eval = ast.literal_eval(dump)
# print(lit_eval)