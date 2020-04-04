
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
public class Client  
{ 
   // final static int ServerPort = 1234;
    private Socket s = null; 
    private DataInputStream dis = null;
    private DataOutputStream dos = null;




    public Client(){}

    public void createSocket(String ipadd, int port) {
        try {
            s = new Socket(ipadd, port);
        } catch (UnknownHostException u) {
            u.printStackTrace();
        } 
        catch (IOException io) {
            io.printStackTrace();
            System.out.println("!! Wrong Port Number !!");
        }
        
    }
        public Socket returnSocket(){
        return s;
    }

  
    public static void main(String args[]) throws UnknownHostException, IOException  
    { 

        Client c = new Client();
        if (args.length != 2) {
            System.err.println("To run the chat app write the PORT NUMBER and IP ADDRESS of the Server as an argument  (java Client <port_number> <ip_address>)!");
            InetAddress localhost = InetAddress.getLocalHost(); 
            System.out.println("Your IP Address : " + (localhost.getHostAddress()).trim());
            
            return;
        }
        Scanner scn = new Scanner(System.in); 
          
        // getting localhost ip
        String ip = args[1];//.toString(); 
        //InetAddress ip = InetAddress.getByName("localhost"); 

        int serverPort= Integer.parseInt(args[0]);
          
        // establish the connection 
        
        c.createSocket(ip, serverPort);

        Socket s = c.returnSocket();

         
          
        // obtaining input and out streams 
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 


        // sendMessage thread 
        Thread sendMessage = new Thread(new Runnable()  
        { 

            @Override
            public void run() { 
                while (true) { 
  
                    // read the message to deliver. 
                    String msg = scn.nextLine(); 
                    if(msg.equals("help!") || msg.equals("Help!")){
                        System.out.println("  The available commands: ");
                        System.out.println("  myip! :  see your IP address.");
                        System.out.println("  myport! :  know the port number on which this process is listening for incoming connections.");
                        System.out.println("  list! :  see all the onnections of this chat.");                
                        //System.out.println("  connect! <destination> <port number> :  connect to the choosen destination.");
                        System.out.println("  terminate! client <client_num> :  end the chat with the chosen that server.");
                        System.out.println("  send! <destination> <message> :  sent  message to the chosen server.");
                        System.out.println("  exit! :  close all connections and terminate this process..");
                    }
                    if(msg.equals("myip!")){
                        try{
                            System.out.println(InetAddress.getLocalHost()); //return the IP addres of computer
                        }catch(IOException i){
                            i.printStackTrace();
                        }
                    }
            
                    if(msg.equals("myport!")){
                        int port_no = s.getLocalPort();
                        System.out.println("Local port of accepted socket : " + s.getLocalPort()); 
                
                    }

          
                    try { 
                        // write on the output stream 
                        dos.writeUTF(msg); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 

                    

                } 
            } 
        }); 
          
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = dis.readUTF(); 
                        System.out.println(msg); 
                    } catch (IOException e) { 
  
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
  
        sendMessage.start(); 
        readMessage.start(); 
  
    } 
} 