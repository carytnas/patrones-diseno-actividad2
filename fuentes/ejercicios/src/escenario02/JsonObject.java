package escenario02;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tipo de dato propio del fabricante cloud (simula el JsonObject de su SDK).
 * Solo se usa entre CloudBiometricApi y su adaptador; el sistema institucional no lo conoce.
 */
public class JsonObject {

	private final Map<String, String> campos = new LinkedHashMap<>();

	public void put(String clave, String valor) {
		campos.put(clave, valor);
	}

	public String getString(String clave) {
		return campos.get(clave);
	}

	public boolean has(String clave) {
		return campos.containsKey(clave);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		for (Map.Entry<String, String> e : campos.entrySet()) {
			if (sb.length() > 1) sb.append(", ");
			sb.append('"').append(e.getKey()).append("\":\"").append(e.getValue()).append('"');
		}
		return sb.append('}').toString();
	}
}
