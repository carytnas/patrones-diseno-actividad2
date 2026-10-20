package escenario02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Adaptee 2: SDK del nuevo fabricante (no modificable).
 * Recibe una consulta en formato JSON con fecha "yyyyMMdd" y responde un JsonObject
 * con marcaciones en formato ISO-8601 ("yyyy-MM-ddTHH:mm:ss").
 */
public class CloudBiometricApi {

	public JsonObject fetchUserData(String jsonQuery) {
        System.out.println("[Cloud API]: Consultando API web del nuevo proveedor... " + jsonQuery);
        String userId = leerCampo(jsonQuery, "userId");
        String day = leerCampo(jsonQuery, "day");

        JsonObject respuesta = new JsonObject();
        respuesta.put("employeeCode", userId);
        if ("20261019".equals(day)) {
            respuesta.put("firstPunch", "2026-10-19T07:55:12");
            respuesta.put("lastPunch", "2026-10-19T17:15:40");
        }
        return respuesta;
    }

	private String leerCampo(String json, String campo) {
		Matcher m = Pattern.compile("\"" + campo + "\"\\s*:\\s*\"([^\"]*)\"").matcher(json);
		return m.find() ? m.group(1) : "";
	}
}
