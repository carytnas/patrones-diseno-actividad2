package escenario01;

import java.util.ArrayList;
import java.util.List;

public class GrupoPresupuestario implements IElementoPresupuestario {
    private String codigo;
    private String descripcion;
    private List<IElementoPresupuestario> elementos = new ArrayList<>();

    public GrupoPresupuestario(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public void agregar(IElementoPresupuestario elemento) { elementos.add(elemento); }

    @Override public String obtenerCodigo() { return codigo; }
    @Override public String obtenerDescripcion() { return descripcion; }

    @Override
    public double calcularValorTotal() {
        double total = 0;
        for (IElementoPresupuestario e : elementos) {
            total += e.calcularValorTotal();
        }
        return total;
    }

    @Override
    public void mostrarEstructura(String nivel) {
        System.out.println(nivel + "[+] Grupo: [" + codigo + "] " + descripcion + " (Total: $" + calcularValorTotal() + ")");
        for (IElementoPresupuestario e : elementos) {
            e.mostrarEstructura(nivel + "    ");
        }
    }
}