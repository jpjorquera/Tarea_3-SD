package sd;
import java.util.*;

class CSThread implements Runnable {
	// Constructor
	private Thread t;
   	private String threadName;
	private volatile boolean exit = false;
	// Tipo, cliente = 0, servidor = 1
	private int tipo = 0;
	Vector mensajes;
   	

   	CSThread(String name) {
      	threadName = name;
	}
	
	CSThread(String name, int type) {
		threadName = name;
		tipo = type;
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

				}
			}
			// Servidor
			else {

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