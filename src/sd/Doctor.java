import com.google.gson.Gson;


import java.util.Map;

public class Doctor {
    private String nombre;
    private String apellido;
    private int id;
    private int exp;
    private int esp;

    Doctor(Map hash) {
        this.setId(((Double) hash.get("id")).intValue());
        this.setNombre((String) hash.get("nombre"));
        this.setApellido((String) hash.get("apellido"));
        this.setExp(((Double) hash.get("experiencia")).intValue());
        this.setEsp(((Double) hash.get("estudios")).intValue());

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getExp() {
        return exp;
    }

    public int getEsp() {
        return esp;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEsp(int esp) {
        this.esp = esp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

