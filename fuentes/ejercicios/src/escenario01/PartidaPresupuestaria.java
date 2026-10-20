package escenario01;

public class PartidaPresupuestaria implements IElementoPresupuestario {
	private String codigo;
	private String descripcion;
	private double valorAsignado;

	public PartidaPresupuestaria(String codigo, String descripcion, double valorAsignado) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.valorAsignado = valorAsignado;
	}

	@Override
	public String obtenerCodigo() {
		return codigo;
	}

	@Override
	public String obtenerDescripcion() {
		return descripcion;
	}

	@Override
	public double calcularValorTotal() {
		return valorAsignado;
	}

	@Override
	public void mostrarEstructura(String nivel) {
		System.out.println(nivel + "[-] Partida: [" + codigo + "] " + descripcion + " - Valor: $" + valorAsignado);
	}
}
