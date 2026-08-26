
import java.io.*;
import java.net.*;


public class ClientSocket {

        private Socket socket = null;
        private BufferedReader dataInput = null;
        private DataOutputStream dataOutput = null;



        public ClientSocket(String addr, int port){

            try{
                socket = new Socket(addr, port);
                System.out.println("Connected");

                dataInput = new BufferedReader(new InputStreamReader(System.in));
                dataOutput = new DataOutputStream(socket.getOutputStream());
            }catch(UnknownHostException hostError){
                System.out.println(hostError);
                return;
            }catch(IOException ioError){
                System.out.println(ioError);
                return;
            }

            String message = "";

            while(!message.equals("bumbip")){
                try{
                    message = dataInput.readLine();
                    dataOutput.writeUTF(message);
                    dataOutput.flush();
                }catch(IOException ioError){
                    System.out.println(ioError);
                }
            }

            try {
                System.out.println("Closing connection...");
                if (dataInput != null) dataInput.close();
                if (dataOutput != null) dataOutput.close();
                if (socket != null) socket.close();
            } catch (IOException ioError) {
                System.out.println("Error" + ioError);
            }	}

        public static void main(String[] args){
            ClientSocket client = new ClientSocket("0.0.0.0", 5000);
        }
    }

