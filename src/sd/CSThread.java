package sd;
import java.util.*;

// Librerias para manejo cliente - servidor
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
	Vector<String> mensajes;
	// Socket para recibir consultas en el servidor
	private Socket socket;
	// Datos del mejor doctor en el servidor
	private int experiencia = 0;
	private int estudio = 0;
	// Mantener estado de coordinacion
	private boolean estadoCoordinacion = false;
   	
	// Constructor para clientes con ip del servidor esperado
   	CSThread(String name, String ip) {
		  threadName = name;
		  ipMaquina = ip;
		  mensajes = new Vector();
	}
	// Constructor para servidores con ip en el cual se inicializa
	CSThread(String name, int type, String ip, int Exp, int Est) {
		threadName = name;
		tipo = type;
		ipMaquina = ip;
		experiencia = Exp;
		estudio = Est;
		mensajes = new Vector();
	}

	public void insertMsg(String msg) {
		mensajes.add(msg);
	}

	public String getMsg() {
		if (mensajes.isEmpty()) {
			return "";
		}
		String msg = (String) mensajes.firstElement();
		mensajes.remove(msg);
		return msg;
	}

	public boolean getEstadoCoordinacion() {
		return estadoCoordinacion;
	}

	public void toogleCoordinacion() {
		if (estadoCoordinacion) {
			estadoCoordinacion = false;
		}
		else {
			estadoCoordinacion = true;
		}
	}

   	// Acciones mientras corre el thread
	public void run(){
		// Si es cliente, inicializar info de la conexion
		if (tipo == 0) {
			String host = this.ipMaquina;
			//System.out.println("ipMaquina: "+host);
			try {
				InetAddress address = InetAddress.getByName(host);
				socket = new Socket(address, port);
			}
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error al iniciar cliente");
				System.exit(1);
			}
			/*finally {
				// Intentar cerrar socket
				try {
					if (exit) {
						socket.close();
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("Error al cerrar el socket");
					System.exit(1);
				}
			}*/
		}
		// Salir cuando se indique
		while (!exit) {
			// Revisar cada 200 milisegundos
			try {
				Thread.sleep(2000);
			}
			catch (Exception e) {};
			// Realizar acciones dependiendo de si es cliente o servidor
			// Cliente
			if (tipo == 0 && !mensajes.isEmpty()) {
				System.out.println("Empezando cliente");
				// Si hay mensajes, actuar
				//if (!mensajes.isEmpty()) {
				// Asignar ip del host
				/*String host = ipMaquina;
				//System.out.println("Intentando conectarse a host: "+host);
				//System.out.println("En puerto: "+port);
				InetAddress address = InetAddress.getByName(host);
				socket = new Socket(address, port);
				System.out.println("Creacion exitosa del socket");
				*/
				try {
					// Extraer mensaje
					String msg = (String) mensajes.firstElement();
					mensajes.remove(msg);
					/* Debuggear destino
					String ip = socket.getInetAddress().getHostAddress();
					int puerto = socket.getPort();
					System.out.println(threadName+" En ip: "+ip+" y puerto: "+Integer.toString(puerto));
					*/

					// Enviar mensaje al servidor
					OutputStream os = socket.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(osw);
					bw.write(msg);
					bw.flush();
					System.out.println("Msg sent to server: "+msg+" from client: "+threadName+".");
			
					// Recibir mensaje devuelta
					InputStream is = socket.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String message = br.readLine();
					System.out.println("Mensaje recibido del servidor: " +message);
				}
				catch (IOException e) {
					System.out.println("IOException en cliente");
					System.exit(1);
				}
				//}
			}
			// Servidor
			else {
				 // Intentar escuchar en puerto (servidor)
				 try {
					int port = 9090;
					String host_ip = ipMaquina;
					InetAddress address = InetAddress.getByName(host_ip);
					ServerSocket serverSocket = new ServerSocket(port, 15, address);
					System.out.println("Servidor empezado y escuchando en puerto "+port+" del ip "+host_ip);
		 
					// Servidor escucha siempre
					while(!exit) {
						// Checkear ip y puerto
						String ip = serverSocket.getInetAddress().getHostAddress();
						int puerto = serverSocket.getLocalPort();
						System.out.println("Servidor - En ip: "+ip+" y puerto: "+Integer.toString(puerto));
		
						// Esperar mensaje de cliente
						socket = serverSocket.accept();
						System.out.println("Socket aceptando");
						InputStream is = socket.getInputStream();
						System.out.println("Inputstream");
						InputStreamReader isr = new InputStreamReader(is);
						System.out.println("InputstreamReader");
						BufferedReader br = new BufferedReader(isr);
						System.out.println("BufferedReader");
						String entrada = br.readLine();
						while (entrada != null) {
							System.out.println("Lei null en server");
							Thread.sleep(100);
							System.out.println("Dormi");
							entrada = br.readLine();
						};
						System.out.println("readline");
						System.out.println("Recibi en servidor: "+entrada);
						char tipoOrden = entrada.charAt(0);

						String returnMessage = "0";
						// Realizar accion dependiendo de orden recibida
						switch (tipoOrden) {
							// Recibi eleccion del bully
							case '1':
								String[] recibido = entrada.split(" ");
								// Comparar
								try { 
									if (Integer.parseInt(recibido[1]) < experiencia) {
										if (Integer.parseInt(recibido[2]) < estudio) {
											// Soy el  bully
											insertMsg("1 "+Integer.toString(experiencia)+" "+Integer.toString(estudio)+"\n");
											returnMessage = "2";
										}
									}
								}
								catch (Exception e) {
									System.out.println("No correspondia a un numero");
									System.exit(11);
								}
								// Descartar
								toogleCoordinacion();
								break;
						
							default:
								break;
						}
		 
						// Enviando respuesta devuelta
						OutputStream os = socket.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os);
						BufferedWriter bw = new BufferedWriter(osw);
						bw.write(returnMessage);
						System.out.println("Mensaje enviado al cliente es: "+returnMessage+"\n");
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