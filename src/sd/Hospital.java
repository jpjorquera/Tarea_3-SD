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

    public static void main (String[] args) throws InterruptedException {
        int nMaquina = 0;
        String ipMaquina = "";
        String cliente1 = "";
        String cliente2 = "";
        String cliente3 = "";
        // Identificar maquina actual
        for (String s: args) {
            if (s.equals("37")) {
                System.out.println(ip37);
                nMaquina = 37;
                ipMaquina = ip37;
                cliente1 = ip38;
                cliente2 = ip39;
                cliente3 = ip40;
            }
            if (s.equals("38")) {
                System.out.println(ip38);
                nMaquina = 38;
                ipMaquina = ip38;
                cliente1 = ip37;
                cliente2 = ip39;
                cliente3 = ip40;
            }
            if (s.equals("39")) {
                System.out.println(ip39);
                nMaquina = 39;
                ipMaquina = ip39;
                cliente1 = ip37;
                cliente2 = ip38;
                cliente3 = ip40;
            }
            if (s.equals("40")) {
                System.out.println(ip40);
                nMaquina = 40;
                ipMaquina = ip40;
                cliente1 = ip37;
                cliente2 = ip38;
                cliente3 = ip39;
            }
        }
        System.out.println("Run exitoso");

        // Si es maquina 38 => servidor
        ///if (nMaquina == 38) {

        //}
        
        // Si maquina no era numero 38 => cliente
        //else {

        //}
        CSThread servidor = new CSThread("server", 1, ipMaquina);
        servidor.start();
        // Esperar inicializacion de otros servidores antes de ejecutar clientes
        System.out.println("Esperando 20s en Main Thread...");
        Thread.sleep(20000);
        // Imprimir clientes hasta ahora
        System.out.println("Cliente 1: "+cliente1);
        System.out.println("Cliente 2: "+cliente2);
        System.out.println("Cliente 3: "+cliente3);
        // Intentar iniciar clientes
        CSThread client1 = new CSThread("cliente 1", cliente1);
        CSThread client2 = new CSThread("cliente 2", cliente2);
        CSThread client3 = new CSThread("cliente 3", cliente3);

        client1.start();
        client2.start();
        client3.start();
    }
}