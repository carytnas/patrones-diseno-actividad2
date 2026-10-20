package escenario02;

/**
 * Target: contrato estable que usa el sistema institucional de asistencia.
 */
public interface ISistemaAsistencia {
	RegistroNormalizado obtenerRegistroAsistencia(String idPersona, String fecha);
}
