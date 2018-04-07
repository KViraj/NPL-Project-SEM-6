import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;
import java.net.SocketException;

public class NetworkClient {

public static void main(String args[]) throws IOException, InterruptedException{


    InetAddress address=InetAddress.getLocalHost();
    Socket s1=null;
    String line=null;
    BufferedReader br=null;
    BufferedReader is=null;
    PrintWriter os=null;
    String options = "Enter a choice:\n1. Login\n2. Send Money\n3. View Balance\n4.LOGOUT and EXIT";

    try {
        s1=new Socket(address, 4445); // You can use static final constant PORT_NUM
        br= new BufferedReader(new InputStreamReader(System.in));
        is=new BufferedReader(new InputStreamReader(s1.getInputStream()));
        os= new PrintWriter(s1.getOutputStream());
    }
    catch (IOException e){
        e.printStackTrace();
        System.err.print("IO Exception");
    }

    //System.out.println("Client Address : "+address);
    System.out.println(options);

    String response=null;
    boolean passflag;
    try{
        //Read Line from user
        String lineNoEnc=br.readLine(); 
        //Encode into Base64
        line = Base64.getEncoder().encodeToString(lineNoEnc.getBytes("utf-8"));
        while(true) {
        		passflag=false;
                os.println(line);
                os.flush();
                response=is.readLine();	//read Line from Server output

                //Decide when to show options
                if(response.substring(response.length()-3).equals("opt")) {
                    response=response.substring(0,response.length()-3);
                    System.out.println("Server : "+response);
                    new Thread().sleep(1500);
                    System.out.println (options);
                }
                else if (response.equals("Enter password:")) {	//check if asked for password
                	System.out.println("Server : "+response);
                	passflag= true;
                }
                else{
                    System.out.println("Server : "+response);
                }

                //For opt 4.. Statement is written here so as to print server response for "4"
                if (lineNoEnc.compareTo("4")==0) {
                    break;
                }

                //Read client-side input and encode to Base64
                if (passflag) {	//read password without showing characters on console
                	lineNoEnc = new String(System.console().readPassword());
                }
                else {	//read normally
                	lineNoEnc=br.readLine();
                }
                line = Base64.getEncoder().encodeToString(lineNoEnc.getBytes("utf-8"));
            }
    }
    catch (SocketException e) {
    	System.out.println("Terminated from Server side!!");
    }
    catch(IOException e){
        e.printStackTrace();
    System.out.println("Socket read Error");
    }
    finally{

        is.close();os.close();br.close();s1.close();
                System.out.println("Connection Closed");

    }

}
}