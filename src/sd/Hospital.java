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
        String ipMaquina = "";
        for (String s: args) {
            if (s.equals("37")) {
                System.out.println(ip37);
                nMaquina = 37;
                ipMaquina = ip37;
            }
            if (s.equals("38")) {
                System.out.println(ip38);
                nMaquina = 38;
                ipMaquina = ip38;
            }
            if (s.equals("39")) {
                System.out.println(ip39);
                nMaquina = 39;
                ipMaquina = ip39;
            }
            if (s.equals("40")) {
                System.out.println(ip40);
                nMaquina = 40;
                ipMaquina = ip40;
            }
        }
        System.out.println("Run exitoso");

        // Si es maquina 38 => servidor
        ///if (nMaquina == 38) {

        //}
        
        // Si maquina no era numero 38 => cliente
        //else {

        //}
    }
}