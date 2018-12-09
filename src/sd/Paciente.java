package sd;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private List<Examen> Examenes;//lista de examenes, realizados o no
    private  List<Procedimiento> Procedimientos;
    public Paciente(){this.Examenes = new ArrayList<Examen>();}



    //setters y getters:


    public List<Procedimiento> getProcedimientos() {return Procedimientos;}
    public void setProcedimientos(List<Procedimiento> procedimientos) {Procedimientos = procedimientos;}
    public List<Examen> getExamenes() {return Examenes;}
    public void setExamenes(List<Examen> examenes) {Examenes = examenes;}
}

class Examen{
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    private String nombre; //Ejemplo: "Examen de prostata"
    private boolean realizado; //Si es TRUE: se ha realizado, FALSE: debe realizarse.
}

class Procedimiento{
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    private String nombre; //Ejemplo: "Quimioterapia"??
    private boolean completado; //TRUE: completado, FALSE: asignado
}