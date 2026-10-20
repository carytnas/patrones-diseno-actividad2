package escenario02;

public class CloudBiometricApi {

	public String consultarCloudJson(String uuid) {
        System.out.println("[Cloud API]: Consultando API web del nuevo proveedor...");
        return "{\"code\":\"" + uuid + "\", \"date\":\"2026-10-19\", \"checkIn\":\"07:55\", \"checkOut\":\"17:15\"}";
    }
}
