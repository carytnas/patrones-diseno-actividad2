package escenario02;

public class BibliotecaBiometricaLegacy {

	public String recuperarLogAntiguo(int codigoNumerico) {
		System.out.println("[Legacy API]: Consultando dispositivo antiguo...");
		return "ID:" + codigoNumerico + "|DATE:2026-10-19|IN:08:10|OUT:17:00";
	}
}