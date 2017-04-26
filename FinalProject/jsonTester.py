#!/usr/bin/env python

import json, ast

data_file = open('JSONTEST.txt','r')
data = data_file.read()
data_file.close()

data = json.loads(data)
print(data)
print()
# datadict = {}
# for i in data:
#     ar = {}
#     for j in i:
#     	if j != 'userhash':
#     		ar[j]=i[j]
#     datadict[i['userhash']]=ar
# print(datadict)
# print()
for j in data:
	print(j)
	for i in data[j]:
		print(i+' '+str(data[j][i]))
print()
data['keviniscool']['weight'].append({'value':'100','datetime':''})
data['keviniscool']['weight'].append({'value':'111','datetime':''})
print(data)
print()


dump = json.dumps(data)

with open('JSONTEST.txt','w') as content:
	content.write(dump)

# lit_eval = ast.literal_eval(dump)
# print(lit_eval)