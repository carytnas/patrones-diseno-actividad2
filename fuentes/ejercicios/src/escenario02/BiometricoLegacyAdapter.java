package escenario02;

/**
 * Adapter 1 (de objetos): implementa el contrato institucional y traduce hacia la
 * biblioteca legacy, que usa ids numericos y devuelve texto con todas las marcaciones.
 */
public class BiometricoLegacyAdapter implements ISistemaAsistencia {
    private BibliotecaBiometricaLegacy legacyApi;

    public BiometricoLegacyAdapter(BibliotecaBiometricaLegacy legacyApi) {
        this.legacyApi = legacyApi;
    }

    @Override
    public RegistroNormalizado obtenerRegistroAsistencia(String idPersona, String fecha) {
        // Parametro: "COL-1001" (String) -> 1001 (int)
        int idNumerico = Integer.parseInt(idPersona.replaceAll("\\D+", ""));
        String crudo = legacyApi.getLogsOld(idNumerico);

        // Resultado: texto "ID:..|DATE:..|IN:..|OUT:..;..." -> registro de la fecha pedida
        for (String marcacion : crudo.split(";")) {
            String[] campos = marcacion.split("\\|");
            if (valor(campos[1]).equals(fecha)) {
                return new RegistroNormalizado(idPersona, fecha, valor(campos[2]), valor(campos[3]));
            }
        }
        return RegistroNormalizado.sinMarcacion(idPersona, fecha);
    }

    // "IN:08:10" -> "08:10"
    private String valor(String campo) {
        return campo.substring(campo.indexOf(':') + 1);
    }
}
