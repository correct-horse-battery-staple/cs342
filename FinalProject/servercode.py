#!/usr/bin/env python

from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import json, ast, random

class handler(BaseHTTPRequestHandler):
    def set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        self.set_headers()
        request = self.raw_requestline.replace('GET ', '').split(' HTTP')[0][1:] #remove first slash
        try:
            #Retrieves first parameter, i.e., url:port/login
            operation = request.split('/')[0]
            
        except IndexError:
            self.wfile.write('<html><body><h1>Query in this format "ip/[operation]/" </h1></body></html>\n')
            return
        
        if operation.lower() == 'login' or operation.lower() == 'register':
            no_file_userpass = False
            try: 
                userpass_file = open('userpass_data', 'r')
                data = userpass_file.read()
                userpass_file.close()
            
                dictionary_userpass = "{\n\t'Info': {\n"
                dictionary_userpass += '\t\t\n'.join(data.lower().splitlines())
                dictionary_userpass += '\n\t}\n}'
                dictionary_userpass = ast.literal_eval(dictionary_userpass)
                
            except IOError:
                #if no file exists
                self.wfile.write('<html><body><h1>No users exist.</h1></body></html>\n')
                no_file_userpass = True
            
            if not no_file_userpass:   
                #Retrieves username and passhash, i.e., url:port/login/user:passhash
                username = request.split('/')[1].split(':')[0]
                passhash = request.split('/')[1].split(':')[1]
                if operation.lower() == 'login':
                    #If login, check if user and password is valid. 
                    try:
                        stored_passhash = dictionary_userpass['Info'][username]
                        if stored_passhash == passhash:
                            self.wfile.write('<html><body><h1>Successful login for %s</h1></body></html>\n' % (username))
                            
                            ###    ISSUE TOKEN
                            rand = random.getrandbits(6);
                            temp = rand
                            while temp>0:
                                lastChar = temp%26
                                temp/=26
                                token+=(char)(temp+65)
                            send_response(token)
                    except KeyError:
                        self.wfile.write('<html><body><h1>The requested user [%s] was not found in the system.</h1></body></html>\n' % (username))
                    
                elif operation.lower() == 'register':
                    with open('userpass_data', 'ab') as content:
                        content.write(
                        '''"%s": "%s"\n,''' % (username,passhash)
                        )
                    #Also send a response giving a confirmation.
                    self.wfile.write('<html><body><h1>Registered new user %s </h1></body></html>\n' % username)
                else:
                    #Query format error.
                    self.wfile.write('<html><body><h1>Query in this format "register/USER:PASSHASH" </h1></body></html>\n')
                    return
        
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
                self.wfile.write('<html><body><h1>No tokens exist.</h1></body></html>\n')
                no_file_tokens = True
                
            #token/[token]?operation
            token = request.split('/')[1].split('?')[0]
            operation = request.split('/')[1].split('?')[1]
            
            try:
                token_user = dictionary_tokens['Tokens'][token]
                get_operation(token_user,operation)
            except KeyError:
                self.wfile.write('<html><body><h1>Token invalid</h1></body></html>')
            
        return
        
    def get_operation(user,op):
        data_file = open('saved_data','r')
        data = data_file.read()
        data_file.close()

        data = json.load(data)
        
def run():
    http_serv = HTTPServer(('', 47159), handler)
    print 'Starting server'
    http_serv.serve_forever()

if __name__ == "__main__":
    run()