package escenario02;

/**
 * Adaptee 1: biblioteca del dispositivo antiguo (no modificable).
 * Recibe un codigo numerico y devuelve TODAS las marcaciones en texto plano,
 * con registros separados por ';' y campos separados por '|'.
 */
public class BibliotecaBiometricaLegacy {

	public String getLogsOld(int idCode) {
		System.out.println("[Legacy API]: Consultando dispositivo antiguo...");
		return "ID:" + idCode + "|DATE:2026-10-18|IN:08:05|OUT:16:58;"
			 + "ID:" + idCode + "|DATE:2026-10-19|IN:08:10|OUT:17:00;"
			 + "ID:" + idCode + "|DATE:2026-10-20|IN:07:59|OUT:17:03";
	}
}
