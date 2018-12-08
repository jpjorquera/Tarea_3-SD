public class Doctor {
    private String nombre;
    private String apellido;
    private int exp;
    private int esp;


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
