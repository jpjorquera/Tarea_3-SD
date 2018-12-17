
import com.google.gson.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        URL path = Main.class.getResource("Doctores1.JSON");
        File f = new File(path.getFile());
        Map javaRootMapObject = new Gson().fromJson(new FileReader(f), Map.class);
////////////////////////////////////////
        List doctores = (List) javaRootMapObject.get("Doctor");
        List paramedicos = (List) javaRootMapObject.get("Paramedico");
        List enfermeros = (List) javaRootMapObject.get("enfermero");

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

        //SE CREARON 3 VECTORES, CON LAS INSTANCIAS DE DOCTORES, PARAMEDICOS Y ENFERMEROS

        Doctor bestdr = mejorDoctor(doctoresV);
        System.out.println("Fin!"); //punto para debug


    }
    static Doctor mejorDoctor(Doctor[] drs){//Recibe el vector de doctores, lo itera y se escoge el mejor doctor, en funcion de su experiencia solamente
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


}



/*
*/