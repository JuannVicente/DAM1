package Clases;

public class Tarea {
        private int id_tarea;
        private String nombre_tarea;
        private String descripcion_tarea;
        private String fecha_tarea;
        private Estado estado;
        private int creador_tarea;

    public Tarea(int id_tarea, String nombre_tarea, String descripcion_tarea, String fecha_tarea, String estado, int creador_tarea) {
        this.id_tarea = id_tarea;
        this.nombre_tarea = nombre_tarea;
        this.descripcion_tarea = descripcion_tarea;
        this.fecha_tarea = fecha_tarea;
        this.estado = Estado.fromString(estado);
        this.creador_tarea = creador_tarea;
    }

    public Tarea() {
    }

    public Tarea(String nombreTarea, String descripcionTarea, String fechaTarea, String estado) {
    }

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public String getDescripcion_tarea() {
        return descripcion_tarea;
    }

    public void setDescripcion_tarea(String descripcion_tarea) {
        this.descripcion_tarea = descripcion_tarea;
    }

    public String getFecha_tarea() {
        return fecha_tarea;
    }

    public void setFecha_tarea(String fecha_tarea) {
        this.fecha_tarea = fecha_tarea;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCreador_tarea() {
        return creador_tarea;
    }

    public void setCreador_tarea(int creador_tarea) {
        this.creador_tarea = creador_tarea;
    }

    // Definición del enum dentro de la misma clase
        public enum Estado {
            Completado("Completado"),
            Pendiente("Pendiente"),
            No_completado("No_completado");

            private final String estado;

            Estado(String estado) {
                this.estado = estado;
            }

            public String getEstado() {
                return estado;
            }

            public static Estado fromString(String text) {
                for (Estado e : Estado.values()) {
                    if (e.estado.equalsIgnoreCase(text)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException("No se encontró el estado: " + text);
            }
        }

    @Override
    public String toString() {
        return  "Nombre de la tarea : " + nombre_tarea +
                ", Descripción : " + descripcion_tarea +
                ", Fecha : " + fecha_tarea +
                ", Estado en el que se encuentra : " + estado
                ;
    }
}



