import java.io.*; 
import java.util.*; 
import java.net.*; 
  
// Server class 
public class Server  
{ 
  
    // Vector to store active clients 
    static Vector<ClientHandler> ar = new Vector<>(); 
      
    // counter for clients 
    static int i = 0; 
  
    public static void main(String[] args) throws IOException  
    { 
        // server is listening on port 1234 
        ServerSocket ss = new ServerSocket(9311); 
          
        Socket s; 
          
        // running infinite loop for getting 
        // client request 
        while (true)  
        { 
            // Accept the incoming request 
            s = ss.accept(); 
  
            System.out.println("New client request received from: " + s); 
              
            // obtain input and output streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
              
            //System.out.println("Creating a new handler for this client..."); 
  
            // Create a new handler object for handling this request. 
            ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos); 
  
            // Create a new Thread with this object. 
            Thread t = new Thread(mtch); 
              
            System.out.println("Adding this client to active client list"); 
  
            // add this client to active clients list 
            ar.add(mtch); 
  
            // start the thread. 
            t.start(); 
  
            // increment i for new client. 
            i++; 
  
        } 
    } 
} 
  
// ClientHandler class 
class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    Socket s; 
    boolean connected; 
      
    // constructor 
    public ClientHandler(Socket s, String name, 
                            DataInputStream dis, DataOutputStream dos) { 
        this.dis = dis; 
        this.dos = dos; 
        this.name = name; 
        this.s = s; 
        this.connected=true; 

    } 
  
    @Override
    public void run() { 
  
        String received; 
        while (true)  
        { 
            try
            { 
                // receive the string 
                received = dis.readUTF(); 
                  
                System.out.println(received); 
                  
                if(received.equals("exit!")){ 
                    this.connected=false; 
                    this.dos.writeUTF("You are leaving the caht!");
                    //this.s.close();
                    
           
                    break; 
                } 

                if(received.equals("list!")){
                    for (ClientHandler mc : Server.ar ){
                        if(mc.name != this.name && mc.connected==true){
                            this.dos.writeUTF(mc.name+":"+ mc.s);
                        }
                        
                    }
                     
                }
                 
                 if(received.startsWith("terminate!")){ 
                        //s = Socket(ip, ServerPort) ;
                        String msg1 = received.replaceFirst("terminate! ", "");
                        for (ClientHandler mc : Server.ar ){
                            if(mc.name == msg1 && mc.connected==true){
                                this.dos.writeUTF(mc.name+"is disconnected");
                                mc.dos.writeUTF("You are removed from the chat");
                                mc.connected = false;
                        }
                        
                    }
                        //msg1 = msg1.replaceAll(" ", "");      
                        StringTokenizer st = new StringTokenizer(msg1, ":");
                        String recipient = st.nextToken();  
                        String MsgToSend = st.nextToken(); 
                } 
                // break the string into message and recipient part 
                if(received.startsWith("send!")){ 
                        //s = Socket(ip, ServerPort) ;
                        String msg1 = received.replaceFirst("send! ", "");
                        //msg1 = msg1.replaceAll(" ", "");
                        if(msg1.contains(":")){
                            StringTokenizer st = new StringTokenizer(msg1, ":");
                            String recipient = st.nextToken();  
                            String MsgToSend = st.nextToken(); 
                                            // search for the recipient in the connected devices list. 
                            // ar is the vector storing client of active users 
                            for (ClientHandler mc : Server.ar)  
                            { 
                                // if the recipient is found, write on its 
                                // output stream 
                                if (mc.name.equals(recipient) )  
                                { 
                                    if( mc.connected==true){
                                    mc.dos.writeUTF(this.name+" : "+MsgToSend); 
                                    break; 
                                }
                                    else{
                                        this.dos.writeUTF("Not connected to this client or wrong format");
                                    }
                       
                                } 
                            } 
                        }
                        else{
                             this.dos.writeUTF("wrong format");
                        }
                   
  

                }
            } catch (IOException e) { 
                  
                e.printStackTrace(); 
            } 
              
        } 
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 