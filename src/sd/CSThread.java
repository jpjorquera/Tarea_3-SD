package sd;
import java.util.*;

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

class CSThread implements Runnable {
	// Constructor
	private Thread t;
   	private String threadName;
	private volatile boolean exit = false;
	// Tipo, cliente = 0, servidor = 1
	private int tipo = 0;
	private int port = 9090;
	private String ipMaquina = "";
	Vector mensajes;
   	
	// Constructor para clientes con ip del servidor esperado
   	CSThread(String name, String ip) {
		  threadName = name;
		  ipMaquina = ip;
	}
	// Constructor para servidores con ip en el cual se inicializa
	CSThread(String name, int type, String ip) {
		threadName = name;
		tipo = type;
		ipMaquina = ip;
	}

   	// Acciones mientras corre el thread
	public void run(){
		// Salir cuando se indique
		while (!exit) {
			// Revisar cada 200 milisegundos
			try {
				Thread.sleep(200);
			}
			catch (Exception e) {};
			// Realizar acciones dependiendo de si es cliente o servidor
			// Cliente
			if (tipo == 0) {
				// Si hay mensajes, actuar
				if (!mensajes.isEmpty()) {
					try {
						// Asignar ip del host
						String host = ipMaquina;
						System.out.println("Intentando conectarse a host: "+host);
						System.out.println("En puerto: "+port);
						InetAddress address = InetAddress.getByName(host);
						socket = new Socket(address, port);
						System.out.println("Creacion exitosa del socket");
			
						// Try debuggear socket
						String ip = socket.getInetAddress().getHostAddress();
						int puerto = socket.getLocalPort();
						System.out.println("En ip: "+ip+" y puerto: "+Integer.toString(puerto));
			
						// Enviar mensaje al servidor
						OutputStream os = socket.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os);
						BufferedWriter bw = new BufferedWriter(osw);
			 
						String number = "2";
			 
						String sendMessage = number + "\n";
						bw.write(sendMessage);
						bw.flush();
						System.out.println("Msg sent to server: "+sendMessage);
			 
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
			// Servidor
			else {
				 // Intentar escuchar en puerto (servidor)
				 try {
					int port = 9090;
					String host_ip = ipMaquina;
					InetAddress address = InetAddress.getByName(host_ip);
					ServerSocket serverSocket = new ServerSocket(port, 10, address);
					System.out.println("Servidor empezado y escuchando en puerto "+port+" del ip "+host_ip);
		 
					// Servidor escucha siempre
					while(!exit) {
						// Checkear ip y puerto
						String ip = serverSocket.getInetAddress().getHostAddress();
						int puerto = serverSocket.getLocalPort();
						System.out.println("En ip: "+ip+" y puerto: "+Integer.toString(puerto));
		
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
		}
	}

	// Correr el thread
	public void start() {
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   	}

   	// Detener el thread
   	public void stop() {
   		exit = true;
   	}
}