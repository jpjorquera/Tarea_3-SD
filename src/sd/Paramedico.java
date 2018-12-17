import java.util.Map;

public class Paramedico {
    private String nombre;
    private String apellido;
    private int id;
    private int exp;
    private int esp;


    Paramedico(Map hash) {
        this.setId(((Double) hash.get("id")).intValue());
        this.setNombre((String) hash.get("nombre"));
        this.setApellido((String) hash.get("apellido"));
        this.setExp(((Double) hash.get("experiencia")).intValue());
        this.setEsp(((Double) hash.get("estudios")).intValue());

    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getEsp() {
        return esp;
    }

    public void setEsp(int esp) {
        this.esp = esp;
    }
}
