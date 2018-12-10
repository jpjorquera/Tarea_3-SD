package sd;

// Librerias para manejo cliente - servidor
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
// Solo servidor
import java.net.ServerSocket;
// Solo cliente
import java.net.InetAddress;

public class Hospital {
    // Ips asociados a maquinas virtuales
    public static final String ip37 = "10.6.40.177";
    public static final String ip38 = "10.6.40.178";
    public static final String ip39 = "10.6.40.179";
    public static final String ip40 = "10.6.40.180";

    // Socket para recibir consultas en el servidor
    private static Socket socket;
    public static void main (String[] args) {
        int nMaquina = 0;
        for (String s: args) {
            if (s.equals("37")) {
                System.out.println(ip37);
                nMaquina = 37;
            }
            if (s.equals("38")) {
                System.out.println(ip38);
                nMaquina = 38;
            }
            if (s.equals("39")) {
                System.out.println(ip39);
                nMaquina = 39;
            }
            if (s.equals("40")) {
                System.out.println(ip40);
                nMaquina = 40;
            }
        }
        System.out.println("Run exitoso");

        // Si es maquina 37 => servidor
        if (nMaquina == 37) {
        // Intentar escuchar en puerto
        try {
            int port = 9090;
            //ServerSocket serverSocket = new ServerSocket(port);
            String host_ip = ip37;
            InetAddress address = InetAddress.getByName(host_ip);
            ServerSocket serverSocket = new ServerSocket(port, 10, address);
            System.out.println("Servidor empezado y escuchando en puerto "+port+" del ip "+host_ip);
 
            // Servidor escucha siempre
            while(true) {
                // Esperar mensaje de cliente
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String number = br.readLine();
                System.out.println("Mensaje recibido del cliente es: "+number);
 
                // Tratar de multiplicar por 2 y responder de vuelta
                String returnMessage;
                try {
                    int numberInIntFormat = Integer.parseInt(number);
                    int returnValue = numberInIntFormat*2;
                    returnMessage = String.valueOf(returnValue) + "\n";
                }
                catch(NumberFormatException e) {
                    // Entrada no era numero
                    returnMessage = "Por favor, envie un numero adecuadamente\n";
                }
 
                // Enviando respuesta devuelta
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Mensaje enviado al cliente es: "+returnMessage);
                bw.flush();
            }
        }
        // Error al inicializar server
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al iniciar servidor");
            System.exit(1);
        }

        finally {
            // Intentar cerrar socket
            try {
                socket.close();
            }
            catch(Exception e){
                System.out.println("Error al cerrar el socket");
                System.exit(1);
            }
        }

        }
        
        // Si maquina no era numero 37 => cliente
        else {
            try {
            // Asignar ip del host
            String host = ip37;
            System.out.println("Intentando conectarse a host: "+host);
            int port = 9090;
            System.out.println("En puerto: "+port);
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            System.out.println("Creacion exitosa del socket");
            // Enviar mensaje al servidor
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
 
            String number = "2";
 
            String sendMessage = number + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Mensaje enviado al servidor: "+sendMessage);
 
            // Recibir mensaje devuelta
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("Mensaje recibido del servidor: " +message);
        }

        // Error al inicializar server
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al iniciar servidor");
            System.exit(1);
        }
        finally {
            // Intentar cerrar socket
            try {
                socket.close();
            }
            catch(Exception e) {
                e.printStackTrace();
                System.out.println("Error al cerrar el socket");
                System.exit(1);
            }
        }

        }
    }
}