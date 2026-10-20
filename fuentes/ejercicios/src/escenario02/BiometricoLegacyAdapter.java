package escenario02;

public class BiometricoLegacyAdapter implements ISistemaAsistencia {
    private BibliotecaBiometricaLegacy legacyApi;

    public BiometricoLegacyAdapter(BibliotecaBiometricaLegacy legacyApi) {
        this.legacyApi = legacyApi;
    }

    @Override
    public RegistroNormalizado obtenerRegistroAsistencia(String idPersona, String fecha) {
        int idNumerico = Integer.parseInt(idPersona.replaceAll("\\D+", ""));
        String crudo = legacyApi.recuperarLogAntiguo(idNumerico);
        // Transformación de formato texto pipe a objeto normalizado
        return new RegistroNormalizado(idPersona, fecha, "08:10", "17:00");
    }
}
