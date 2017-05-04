#!/usr/bin/env python

from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
#from http.server import BaseHTTPRequestHandler, HTTPServer
import json, ast, random, urlparse

class handler(BaseHTTPRequestHandler):
    def set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        self.set_headers()
        requested_name = self.raw_requestline.replace('GET ', '').split(' HTTP')[0][1:] #remove first slash
        self.wfile.write('<html><body><h1>%s</h1></body></html>'%requested_name)
        
    def do_POST(self):
        
        #List of operations:
        #login/user:passhash
        #register/user:passhash
        #token/[token]?operation:[field]/data
        #ping
        
        length = int(self.headers.getheader('content-length'))
        request = self.rfile.read(length)
        self.set_headers()

        try:
            operation = request.split('/')[0]
        except IndexError:
            self.wfile.write('error/input:invalid_operation') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            return
        
        if operation.lower() == 'login':
            self.login(request)
                
        elif operation.lower() == 'register':
            self.register(request)

        elif operation.lower() == 'token':
            self.token(request)
                    
        elif operation.lower() == 'ping':
            self.wfile.write('ping/success') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
        return

    def login(self,request):
        try: 
            userpass_file = open('userpass_data', 'r')
            data = userpass_file.read()
            userpass_file.close()
            # {'userpass': {'0':'0',...} }
            dictionary_userpass = ast.literal_eval(data)
            #if no file exists
        except IOError:
            open('userpass_data', 'w').write("{'userpass':{}}")
            self.wfile.write('error/login:no_users') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            return

        # login/user:passhash
        userhash = request.split('/')[1].split(':')[0]
        passhash = request.split('/')[1].split(':')[1]

        try:
            tokens_file = open('tokens_data', 'r')
            data = tokens_file.read()
            tokens_file.close()

            dictionary_tokens = ast.literal_eval(data)
        except IOError:
            open('tokens_data', 'w').write('{"tokens":{},"users":{}}')
            return

        #If login, check if user and password is valid. 
        stored_passhash = ''
        try:
            stored_passhash = dictionary_userpass['userpass'][userhash]
        except KeyError:
            self.wfile.write('error/login:no_user')

        if stored_passhash == passhash:
            rand = random.getrandbits(15);
            if userhash in dictionary_tokens['users']:
                prev_token = dictionary_tokens['users'][userhash]
                dictionary_tokens['users'][userhash] = str(rand)
                try:
                    dictionary_tokens['tokens'].pop(prev_token)
                    dictionary_tokens['tokens'][str(rand)] = userhash
                except KeyError:
                    self.wfile.write('error/login:token_error_1')
                    return
                self.wfile.write('login/success:'+str(rand)) #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            else:
                dictionary_tokens['users'][userhash]=str(rand)
                dictionary_tokens['tokens'][str(rand)] = userhash
                self.wfile.write('login/success:'+str(rand)) #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            dump = json.dumps(dictionary_tokens)
            with open('tokens_data','w') as content:
                content.write(dump)
        else:
            self.wfile.write('login/failed') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            return

    def register(self,request):
    	userhash = request.split('/')[1].split(':')[0]
        passhash = request.split('/')[1].split(':')[1]
        
        no_file_userpass = False
        try: 
            userpass_file = open('userpass_data', 'r')
            data = userpass_file.read()
            userpass_file.close()

            # following code maintained here for documentation purposes

            # dictionary_userpass = "{\n\t'Info': {\n"
            # dictionary_userpass += '\t\t\n'.join(data.lower().splitlines())
            # dictionary_userpass += '\n\t}\n}'
            dictionary_userpass = ast.literal_eval(data)
            # dictionary_userpass = dictionary_userpass['Info']

            if userhash not in dictionary_userpass['userpass']:
                dictionary_userpass['userpass'][userhash]=passhash
                dump = json.dumps(dictionary_userpass)
                with open('userpass_data', 'w') as content:
                    content.write(dump)
                    self.wfile.write('register/success') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            # else:
            #     self.wfile.write('error/register:userhash_in_use') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            
        except IOError:
            with open('userpass_data', 'w') as content:
                content.write(
                '''{"userpass":{"%s","%s"}}''' % (userhash,passhash)
                )
            # Also send a response giving a confirmation.
            # self.wfile.write('register/success') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
        self.login(request)

    def token(self,request):
    	try: 
            tokens_file = open('tokens_data', 'r')
            data = tokens_file.read()
            tokens_file.close()

            dictionary_tokens = ast.literal_eval(data)

        except IOError:
            #if no file exists
            with open('tokens_data','w') as content:
            	content.write('''{"tokens":{},"users":{}}''')
            
        #token/[token]?operation:data
        post_slash = '-'.join(request.split('/')[1:])
        token = post_slash.split('?')[0][:-1]
        try:
            post_q = post_slash.split('?')[1]
            operation = post_q.split(':')[0]
            input_data = ':'.join(post_q.split(':')[1:])
            try:
                token_user = dictionary_tokens['tokens'][token]
                self.do_operation(token_user,operation,input_data)
            except KeyError:
                self.wfile.write('error/token:%d %s'% (' '.join(list(token)), dictionary_tokens['tokens'])) #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
        except IndexError:
            self.wfile.write('error/token:bad_input') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#

    def do_operation(self,user,op,inp):
        #operations:
        #load
        #store

        try:
            data_file = open('master_data','r')
            data = data_file.read()
            data_file.close()
        except IOError:
            open('master_data', 'ab').write('{"data":{}}')
            data=json.loads('{}')
        #data = {
        #userdata [
        #{userhash, weight: [value, datetime], ...}]}

        #data fields:
        #weight
        #heartrate
        #activities (more complicated)
        #steps

        data = ast.literal_eval(data)
        #data = {
        #userhash:{weight:[{value,datetime},{}...],heartrate:[]...}...}

        if op == 'load':
        	#[field]
            try:
                data_dump = json.dumps(data[user][inp])
                self.wfile.write('token/load:'+data_dump) #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
            except KeyError:
                self.wfile.write('error/token:no_data') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
        elif op == 'store':
            #[field]-[data]
            input_field = inp.split('-')[0]
            input_data = inp.split('-')[1]
            if user not in data:
                data[user]={'weight': [], 'heartrate': [], 'activities': [], 'steps': []}
            data[user][input_field].append(ast.literal_eval(input_data))
            dump = json.dumps(data)
            with open('master_data','w') as content:
                content.write(dump)
            self.wfile.write('token/store/success') #~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~#
        return
        
def run():
    http_serv = HTTPServer(('', 47158), handler)
    print 'Starting server'
    http_serv.serve_forever()

if __name__ == "__main__":
    run()