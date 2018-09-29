package murua.fedde.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Dia implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(DateConverter.class)
    private Date titulo;
    private Integer vasos;

    public Dia(Integer vasos) {
        this.titulo =  new Date(System.currentTimeMillis());
        this.vasos = vasos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTitulo() {
        return titulo;
    }

    public void setTitulo(Date titulo) {
        this.titulo = titulo;
    }

    public Integer getVasos() {
        return vasos;
    }

    public void setVasos(Integer vasos) {
        this.vasos = vasos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dia)) return false;

        Dia dia = (Dia) o;

        if (id != dia.id ) return false;
        return titulo != null ? titulo.equals(dia.titulo) : dia.titulo== null;
    }

    @Override
    public int hashCode() {
        int result = (int)id;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dia{" +
                "id=" + id +
                ", titulo='" + titulo+ '\'' +
                ", vasos=" + vasos +
                '}';
    }
}
