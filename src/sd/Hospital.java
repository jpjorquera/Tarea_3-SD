package sd;

import com.google.gson.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.lang.*;

public class Hospital {
    // Ips asociados a maquinas virtuales
    public static final String ip37 = "10.6.40.177";
    public static final String ip38 = "10.6.40.178";
    public static final String ip39 = "10.6.40.179";
    public static final String ip40 = "10.6.40.180";

    static Doctor mejorDoctor(Doctor[] drs){
        //Recibe el vector de doctores, lo itera y se escoge el mejor doctor, en funcion de su experiencia solamente
        int summax = 0;
        int estudio = 0;
        Doctor max = null;
        for (Doctor doctor : drs){
            if (summax<doctor.getExp()){
                summax=doctor.getExp();
                estudio = doctor.getEsp();
                max=doctor;
            }
            else if (summax == doctor.getExp()){
                if (estudio<doctor.getEsp()){
                    max = doctor;
                }
            }
        }
        return max;
    }
    public static void main (String[] args) throws InterruptedException, FileNotFoundException {
        // Inicializacion de variables relativas por maquina
        int nMaquina = 0;
        String ipMaquina = "";
        String cliente1 = "";
        String cliente2 = "";
        String cliente3 = "";
        String nombreArchivoPersonal = "";
        // Identificar maquina actual
        for (String s: args) {
            if (s.equals("37")) {
                System.out.println(ip37);
                nMaquina = 37;
                ipMaquina = ip37;
                cliente1 = ip38;
                cliente2 = ip39;
                cliente3 = ip40;
                nombreArchivoPersonal = "Personal37.json";
            }
            if (s.equals("38")) {
                System.out.println(ip38);
                nMaquina = 38;
                ipMaquina = ip38;
                cliente1 = ip37;
                cliente2 = ip39;
                cliente3 = ip40;
                nombreArchivoPersonal = "Personal38.json";
            }
            if (s.equals("39")) {
                System.out.println(ip39);
                nMaquina = 39;
                ipMaquina = ip39;
                cliente1 = ip37;
                cliente2 = ip38;
                cliente3 = ip40;
                nombreArchivoPersonal = "Personal39.json";
            }
            if (s.equals("40")) {
                System.out.println(ip40);
                nMaquina = 40;
                ipMaquina = ip40;
                cliente1 = ip37;
                cliente2 = ip38;
                cliente3 = ip39;
                nombreArchivoPersonal = "Personal40.json";
            }
        }

        // Lectura inicial de json 
        File f = new File(nombreArchivoPersonal);
        Map javaRootMapObject = new Gson().fromJson(new FileReader(f), Map.class);
        // Formular personal hospital
        List doctores = (List) javaRootMapObject.get("Doctor");
        List paramedicos = (List) javaRootMapObject.get("Paramedico");
        List enfermeros = (List) javaRootMapObject.get("Enfermero");

        //****INICIALIZAR DOCTORES , ENFERMEROS, PARAMEDICOS*****

        Doctor doctoresV[] = new Doctor[doctores.size()];//Vector de doctores a inicializar, de tipo Doctor[].

        for (int i = 0; i < doctores.size(); i++) {
            //**En esta parte,se tomarán los datos de la lista sacada del JSON y se ingresarán al objeto.
            doctoresV[i] = new Doctor((Map) doctores.get(i)); //Se ingresa un map con los datos del dr
        }


        Paramedico paramV[] = new Paramedico[paramedicos.size()];
        for (int i = 0; i < paramedicos.size(); i++) {
            //**En esta parte,se tomarán los datos de la lista sacada del JSON y se ingresarán al objeto.
            paramV[i] = new Paramedico((Map) enfermeros.get(i)); //Se ingresa un map con losd datos del paramedico
        }

        Enfermero enfeV[] = new Enfermero[enfermeros.size()];
        for (int i = 0; i < enfermeros.size(); i++) {
            //**En esta parte,se tomarán los datos de la lista sacada del JSON y se ingresarán al objeto.
            enfeV[i] = new Enfermero((Map) enfermeros.get(i)); //Se ingresa un map con los del enfermero
        }

        // Asignar mejor doctor
        Doctor bestdr = mejorDoctor(doctoresV);
        int experiencia = bestdr.getExp();
        int estudio = bestdr.getEsp();
        
        System.out.println("El mejor doctor es: "+bestdr.getNombre()+bestdr.getApellido()+". Con "+
        experiencia+" anios de experiencia y "+estudio+" de estudio.");


        

        // Inicializacion de servidor y clientes
        CSThread servidor = new CSThread("server", 1, ipMaquina, experiencia, estudio);
        servidor.start();
        // Esperar inicializacion de otros servidores antes de ejecutar clientes
        System.out.println("Esperando 20s en Main Thread...");
        Thread.sleep(20000);

        // Iniciar clientes
        CSThread client1 = new CSThread("cliente 1", cliente1);
        CSThread client2 = new CSThread("cliente 2", cliente2);
        CSThread client3 = new CSThread("cliente 3", cliente3);

        // Mensaje primera eleccion
        String msgInicial = "1 "+Integer.toString(experiencia)+" "+Integer.toString(estudio)+"\n";
        System.out.println("Mensaje inicial: "+msgInicial);
        client1.insertMsg(msgInicial);
        client2.insertMsg(msgInicial);
        client3.insertMsg(msgInicial);

        // Empezar clientes
        client1.start();
        client2.start();
        client3.start();

        // Leer mensajes desde el servidor
        String mensaje;
        while (true) {
            mensaje = servidor.getMsg();
            // No llego nada, esperar e intentar denuevo
            if (mensaje.equals("")) {
                Thread.sleep(200);
                continue;
            }

            char tipoOrden = mensaje.charAt(0);
            switch (tipoOrden) {
                // Hay que bullear
                case '1':
                    System.out.println("Hay que bullear: "+mensaje);
                    client1.insertMsg(mensaje);
                    client2.insertMsg(mensaje);
                    client3.insertMsg(mensaje);
                    break;
            
                default:
                    break;
            }
        }
    }
}