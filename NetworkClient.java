import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkClient {

public static void main(String args[]) throws IOException, InterruptedException {

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
    try{
        line=br.readLine(); 
        while(true) {
                os.println(line);
                os.flush();
                response=is.readLine();//read Line from Server output

                //Decide when to show options
                if(response.substring(response.length()-3).equals("opt")) {
                    response=response.substring(0,response.length()-3);
                    System.out.println("Server : "+response);
                    new Thread().sleep(2000);
                    System.out.println (options);
                }
                else
                    System.out.println("Server : "+response);

                //For opt 4.. Statement is written here so as to print server response for "4"
                if (line.compareTo("4")==0) {
                    break;
                }
                line=br.readLine();//read clientside input
            }
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
