#!/usr/bin/env python

import json, ast

# data_file = open('JSONTEST.txt','r')
# data = data_file.read()
# data_file.close()

# data = json.loads(data)
# print(data)
# print()
# # datadict = {}
# # for i in data:
# #     ar = {}
# #     for j in i:
# #     	if j != 'userhash':
# #     		ar[j]=i[j]
# #     datadict[i['userhash']]=ar
# # print(datadict)
# # print()
# for j in data:
# 	print(j)
# 	for i in data[j]:
# 		print(i+' '+str(data[j][i]))
# print()
# data['keviniscool']['weight'].append({'value':'100','datetime':''})
# data['keviniscool']['weight'].append({'value':'111','datetime':''})
# print(data)
# print()


# dump = json.dumps(data)

# with open('JSONTEST.txt','w') as content:
# 	content.write(dump)

# data_file = open('JSONTEST2.txt','r')
# data = data_file.read()
# data_file.close()

# data = ast.literal_eval(data)
# print(data)
# #data = json.loads(data)

# #print(data)
# user = '0'
# rand = '132'
# if user in data['userpass']:
# 	prev_token = data['userpass'][user]
# 	data['tokens'].pop(prev_token)
# data['tokens'][rand] = user
# data['userpass'][user] = rand
# print(data)
# print (rand in data['tokens'])
# print (user in data['userpass'])

print ('170'==170)
print ('170'=='170')
print (170 == 170)
print (str(170)=='170')