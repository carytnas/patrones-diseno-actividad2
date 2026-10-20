package escenario02;

/**
 * Adapter 2 (de objetos): implementa el contrato institucional y traduce hacia el
 * SDK cloud, que trabaja con consultas JSON y fechas/horas en otros formatos.
 */
public class BiometricoNuevoAdapter implements ISistemaAsistencia {

    private CloudBiometricApi cloudApi;

    public BiometricoNuevoAdapter(CloudBiometricApi cloudApi) {
        this.cloudApi = cloudApi;
    }

    @Override
    public RegistroNormalizado obtenerRegistroAsistencia(String idPersona, String fecha) {
        // Parametros: (idPersona, "2026-10-19") -> {"userId":"...","day":"20261019"}
        String consulta = "{\"userId\":\"" + idPersona + "\",\"day\":\"" + fecha.replace("-", "") + "\"}";
        JsonObject respuesta = cloudApi.fetchUserData(consulta);

        // Resultado: JsonObject con horas ISO-8601 -> RegistroNormalizado con "HH:mm"
        if (!respuesta.has("firstPunch")) {
            return RegistroNormalizado.sinMarcacion(idPersona, fecha);
        }
        return new RegistroNormalizado(respuesta.getString("employeeCode"), fecha,
                hora(respuesta.getString("firstPunch")), hora(respuesta.getString("lastPunch")));
    }

    // "2026-10-19T07:55:12" -> "07:55"
    private String hora(String fechaHoraIso) {
        return fechaHoraIso.substring(11, 16);
    }
}
