package escenario02;

public class RegistroNormalizado {

	private String identificacion;
    private String fecha;
    private String horaIngreso;
    private String horaSalida;

    public RegistroNormalizado(String id, String fecha, String ingreso, String salida) {
        this.identificacion = id; this.fecha = fecha; this.horaIngreso = ingreso; this.horaSalida = salida;
    }

    @Override
    public String toString() {
        return "ID: " + identificacion + " | Fecha: " + fecha + " | Ingreso: " + horaIngreso + " | Salida: " + horaSalida;
    }
}
