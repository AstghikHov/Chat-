# Chat-
One host multiple clients chat app with socket programming. 
A Chat Application for Remote Message Exchange  

Prerequisites 
In order to run this program, you need to install on any java compiler platform 
How to run 
Run the Server:        
javac Server.java       
java Server 

After running Server.java run Client 
Run the Client:        
javac Client.java      
java Client <Server_port_number>  <IP_address> 

Server port number is 9311 for this server. If you don’t know your IP just run the Client without arguments, or type ipconfig in command prompt.  

Available Commands 
Help!  : See all the available commands myip! :  see your IP address.  
myport! :  know the port number on which this process is listening for incoming connections. 
list! :  see all the connections of this chat. 
terminate! client <client_num> :  end the chat with the chosen that server. (Ex: terminate! client 0)
send! Client <client_num> <message> :  sent  message to the chosen server. (Ex: send! client 0 Hello)
  exit! :  Close all connections and terminate this process. 
 
 
Please insert the commands with the exact same format, otherwise the program wouldn’t work as it is expected 
