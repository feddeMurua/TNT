package murua.fedde.sugar_orm;

import com.orm.SugarRecord;

public class Desarrollador extends SugarRecord {
    private String nombre;
    private String apellido;
    private String lenguajeFavorito;


    public Desarrollador(String apellido, String nombre, String lenguajeFavorito) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.lenguajeFavorito = lenguajeFavorito;
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

    public String getLenguajeFavorito() {
        return lenguajeFavorito;
    }

    public void setLenguajeFavorito(String lenguajeFavorito) {
        this.lenguajeFavorito = lenguajeFavorito;
    }
}
