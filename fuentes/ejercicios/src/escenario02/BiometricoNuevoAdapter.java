package escenario02;

public class BiometricoNuevoAdapter implements ISistemaAsistencia {
	
    private CloudBiometricApi cloudApi;

    public BiometricoNuevoAdapter(CloudBiometricApi cloudApi) {
        this.cloudApi = cloudApi;
    }

    @Override
    public RegistroNormalizado obtenerRegistroAsistencia(String idPersona, String fecha) {
        String jsonSimulado = cloudApi.consultarCloudJson(idPersona);
        // Adaptación de datos JSON simulados al contrato estándar de la universidad
        return new RegistroNormalizado(idPersona, fecha, "07:55", "17:15");
    }
}
