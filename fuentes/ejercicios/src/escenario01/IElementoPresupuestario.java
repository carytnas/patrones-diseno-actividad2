package escenario01;

public interface IElementoPresupuestario {
	
	String obtenerCodigo();
    String obtenerDescripcion();
    double calcularValorTotal();
    void mostrarEstructura(String nivel);
}