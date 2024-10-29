package Clases;

public class Evento {
    private int id_evento;
    private String nombre_evento;
    private String descripcion_evento;
    private String fecha_evento;
    private Estado estado;
    private int creador_evento;

    public Evento(int id_evento, String nombre_evento, String descripcion_evento, String fecha_evento, String estado, int creador_evento) {
        this.id_evento = id_evento;
        this.nombre_evento = nombre_evento;
        this.descripcion_evento = descripcion_evento;
        this.fecha_evento = fecha_evento;
        this.estado = Estado.fromString(estado);
        this.creador_evento = creador_evento;
    }

    public Evento() {
    }

    public Evento(String nombreEvento, String descripcionEvento, String fechaEvento ,String estado) {
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre_evento() {
        return nombre_evento;
    }

    public void setNombre_evento(String nombre_evento) {
        this.nombre_evento = nombre_evento;
    }

    public String getDescripcion_evento() {
        return descripcion_evento;
    }

    public void setDescripcion_evento(String descripcion_evento) {
        this.descripcion_evento = descripcion_evento;
    }

    public String getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(String fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCreador_evento() {
        return creador_evento;
    }

    public void setCreador_evento(int creador_evento) {
        this.creador_evento = creador_evento;
    }

    public enum Estado {
        Completado("Completado"),
        Pendiente("Pendiente"),
        No_asistido("No_asistido");

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
        return "Nombre del evento : " + nombre_evento +
                ", Descripción : " + descripcion_evento +
                ", Fecha de evento : " + fecha_evento +
                ", Estado en el que se encuentra : " + estado;
    }
}
