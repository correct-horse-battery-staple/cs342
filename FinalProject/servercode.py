#!/usr/bin/env python

from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
#from http.server import BaseHTTPRequestHandler, HTTPServer
import json, ast, random, urlparse

class handler(BaseHTTPRequestHandler):
    def set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()
        
    def do_POST(self):
        
        # List of operations:
        # login/user:passhash
        # register/user:passhash
        # token/[token]?operation:data
        # ping
        
        length = int(self.headers.getheader('content-length'))
        field_data = self.rfile.read(length)
        
        self.set_headers()

        request = field_data
        try:
            #Retrieves first parameter, i.e., url:port/login
            operation = request.split('/')[0]
            
        except IndexError:
            self.wfile.write('error/input:invalid_operation')
            return
        
        if operation.lower() == 'login':
            no_file_userpass = False
            try: 
                userpass_file = open('userpass_data', 'r')
                data = userpass_file.read()
                userpass_file.close()
            
                dictionary_userpass = "{\n\t'Info': {\n"
                dictionary_userpass += '\t\t\n'.join(data.lower().splitlines())
                dictionary_userpass += '\n\t}\n}'
                dictionary_userpass = ast.literal_eval(dictionary_userpass)      
                dictionary_userpass = dictionary_userpass['Info']

                #Retrieves username and passhash, i.e., url:port/login/user:passhash
                username = request.split('/')[1].split(':')[0]
                passhash = request.split('/')[1].split(':')[1]
                
                #If login, check if user and password is valid. 
                try:
                    stored_passhash = dictionary_userpass[username]
                    if stored_passhash == passhash:
                        ###    ISSUE TOKEN
                        rand = random.getrandbits(15);                
                        self.wfile.write('login/success:'+str(rand))
                    else:
                        #Query format error.
                        self.wfile.write('error/login:format')
                        return
                        
                except KeyError:
                    self.wfile.write('error/login:no_user')
                
            except IOError:
                #if no file exists
                self.wfile.write('error/login:no_users')
                
        elif operation.lower() == 'register':
            username = request.split('/')[1].split(':')[0]
            passhash = request.split('/')[1].split(':')[1]
            
            no_file_userpass = False
            try: 
                userpass_file = open('userpass_data', 'r')
                data = userpass_file.read()
                userpass_file.close()
            
                dictionary_userpass = "{\n\t'Info': {\n"
                dictionary_userpass += '\t\t\n'.join(data.lower().splitlines())
                dictionary_userpass += '\n\t}\n}'
                dictionary_userpass = ast.literal_eval(dictionary_userpass)

                dictionary_userpass = dictionary_userpass['Info']

                if username not in dictionary_userpass:
                    with open('userpass_data', 'ab') as content:
                        content.write(
                        '''"%s": "%s"\n''' % (username,passhash)
                        )
                        self.wfile.write('register/success')
                else:
                    self.wfile.write('error/register:username_in_use')
                
            except IOError:
                with open('userpass_data', 'ab') as content:
                    content.write(
                    '''"%s": "%s"\n''' % (username,passhash)
                    )
                #Also send a response giving a confirmation.
                self.wfile.write('register/success')

        elif operation.lower() == 'token':
            no_file_tokens = False
            try: 
                tokens_file = open('tokens_data', 'r')
                data = tokens_file.read()
                tokens_file.close()
    
                dictionary_tokens = "{\n\t'Tokens': {\n"
                dictionary_tokens += '\t\t\n'.join(data.lower().splitlines())
                dictionary_tokens += '\n\t}\n}'
                dictionary_tokens = ast.literal_eval(dictionary_userpass)
            except IOError:
                #if no file exists
                self.wfile.write('error/token:no_file')
                no_file_tokens = True
                
            #token/[token]?operation:data
            use_token = true
            token = request.split('/')[1].split('?')[0]
            try:
                operation = request.split('/')[1].split('?')[1].split(':')[0]
                input_data = request.split('/')[1].split('?')[1].split(':')[1]
            except IndexError:
                self.wfile.write('error/token:bad_input')
                use_token = false
            
            if use_token:
                try:
                    token_user = dictionary_tokens['Tokens'][token]
                    do_operation(token_user,operation,input_data)
                except KeyError:
                    self.wfile.write('error/token:no_token')
                    
        elif operation.lower() == 'ping':
            self.wfile.write('ping/success');
        return
        
    def do_operation(user,op,input):
        # potential operations: load previous stats, update stats
        data_file = open('','r')
        data = data_file.read()
        data_file.close()
 		
 		# data = {
 		# userdata [
 		#  {username, weight: [value, datetime], ...}]}
        data = json.loads(data)
        data = data['userdata']
        datadict = {}
        for i in data:
        	datadict[i['username']]=i['weight']
        # datadict = {
        # username:[weight:[value,datetime],...], ...}

        if op == 'load':
            data_dump = json.dumps(datadict[user])
            self.wfile.write('token/load:'+data_dump)
        elif op == 'update':
            if user in datadict:
                self.wfile.write('token/write')
        return
        
def run():
    http_serv = HTTPServer(('', 47158), handler)
    print 'Starting server'
    http_serv.serve_forever()

if __name__ == "__main__":
    run()