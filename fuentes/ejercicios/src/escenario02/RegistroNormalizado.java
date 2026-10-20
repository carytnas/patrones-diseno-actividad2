package escenario02;

/**
 * Estructura normalizada que espera el sistema institucional,
 * independiente del formato de cada fabricante.
 */
public class RegistroNormalizado {

	private static final String SIN_MARCACION = "--:--";

	private String identificacion;
    private String fecha;
    private String horaIngreso;
    private String horaSalida;

    public RegistroNormalizado(String id, String fecha, String ingreso, String salida) {
        this.identificacion = id; this.fecha = fecha; this.horaIngreso = ingreso; this.horaSalida = salida;
    }

    public static RegistroNormalizado sinMarcacion(String id, String fecha) {
        return new RegistroNormalizado(id, fecha, SIN_MARCACION, SIN_MARCACION);
    }

    public String getIdentificacion() { return identificacion; }
    public String getFecha() { return fecha; }
    public String getHoraIngreso() { return horaIngreso; }
    public String getHoraSalida() { return horaSalida; }

    @Override
    public String toString() {
        return "ID: " + identificacion + " | Fecha: " + fecha + " | Ingreso: " + horaIngreso + " | Salida: " + horaSalida;
    }
}
