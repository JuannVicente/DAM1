package Clases;

public class Notas {
    private int id_nota;
    private String nota;
    private String fecha_creacion_de_tarea;
    private int creador_de_la_nota;

    public Notas(int id_nota, String nota, String fecha_creacion_de_tarea, int creador_de_la_nota) {
        this.id_nota = id_nota;
        this.nota = nota;
        this.fecha_creacion_de_tarea = fecha_creacion_de_tarea;
        this.creador_de_la_nota = creador_de_la_nota;
    }

    public int getId_nota() {
        return id_nota;
    }

    public void setId_nota(int id_nota) {
        this.id_nota = id_nota;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFecha_creacion_de_tarea() {
        return fecha_creacion_de_tarea;
    }

    public void setFecha_creacion_de_tarea(String fecha_creacion_de_tarea) {
        this.fecha_creacion_de_tarea = fecha_creacion_de_tarea;
    }

    public int getCreador_de_la_nota() {
        return creador_de_la_nota;
    }

    public void setCreador_de_la_nota(int creador_de_la_nota) {
        this.creador_de_la_nota = creador_de_la_nota;
    }

    @Override
    public String toString() {
        return  "Nota : " + nota +
                ", Fecha de la Tarea : " + fecha_creacion_de_tarea
               ;
    }
}
