package escenario01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Cliente del patron Composite: trabaja solo con IElementoPresupuestario,
 * sin distinguir entre partidas individuales y grupos.
 */
public class MainPresupuestoConcurrente {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Estructura del enunciado: TI -> Licenciamiento, Infraestructura (Servidores,
        // Almacenamiento, Comunicaciones) y Servicios.
        GrupoPresupuestario dptoTI = new GrupoPresupuestario("G-TI", "Tecnologias de la Informacion");
        dptoTI.agregar(new PartidaPresupuestaria("P-01", "Licenciamiento de software", 15000.00));

        GrupoPresupuestario infraestructura = new GrupoPresupuestario("G-INF", "Infraestructura");
        infraestructura.agregar(new PartidaPresupuestaria("P-02", "Servidores", 35000.00));
        infraestructura.agregar(new PartidaPresupuestaria("P-03", "Almacenamiento", 18000.00));

        GrupoPresupuestario comunicaciones = new GrupoPresupuestario("G-COM", "Comunicaciones");
        comunicaciones.agregar(new PartidaPresupuestaria("P-04", "Switches core", 12000.00));
        comunicaciones.agregar(new PartidaPresupuestaria("P-05", "Enlaces de internet", 6000.00));
        infraestructura.agregar(comunicaciones);
        dptoTI.agregar(infraestructura);

        GrupoPresupuestario servicios = new GrupoPresupuestario("G-SER", "Servicios");
        servicios.agregar(new PartidaPresupuestaria("P-06", "Soporte tecnico", 9000.00));
        servicios.agregar(new PartidaPresupuestaria("P-07", "Servicios en la nube", 11000.00));
        dptoTI.agregar(servicios);

        IElementoPresupuestario auditoria = new PartidaPresupuestaria("P-08", "Auditoria Externa Sistemas", 8000.00);

        GrupoPresupuestario presupuestoGeneral = new GrupoPresupuestario("G-01", "Presupuesto Institucional 2026");
        presupuestoGeneral.agregar(dptoTI);
        presupuestoGeneral.agregar(auditoria);

        // Subarboles de primer nivel que se evaluaran en paralelo (grupos y partidas por igual)
        List<IElementoPresupuestario> subarboles = Arrays.asList(dptoTI, auditoria);

        int opcion = 0;
        do {
            System.out.println("\n==================================================");
            System.out.println("   SISTEMA DE GESTION PRESUPUESTARIA (COMPOSITE)");
            System.out.println("==================================================");
            System.out.println("1. Mostrar estructura completa y calculo secuencial");
            System.out.println("2. Evaluar subarboles de forma PARALELA (Concurrente)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                scanner.next();
                continue;
            }

            if (opcion == 1) {
                System.out.println("\n--- REPORTE SECUENCIAL ---");
                presupuestoGeneral.mostrarEstructura("");
                System.out.println("Total Consolidado: $" + presupuestoGeneral.calcularValorTotal());
            } else if (opcion == 2) {
                System.out.println("\n--- ANALISIS Y CALCULO PARALELO DE SUBARBOLES ---");
                long inicio = System.currentTimeMillis();

                List<CompletableFuture<Void>> tareas = new ArrayList<>();
                for (IElementoPresupuestario subarbol : subarboles) {
                    tareas.add(CompletableFuture.runAsync(() -> procesarSubarbolParalelo(subarbol)));
                }
                CompletableFuture.allOf(tareas.toArray(new CompletableFuture<?>[0])).join();

                long duracion = System.currentTimeMillis() - inicio;
                System.out.println(" Total Consolidado: $" + presupuestoGeneral.calcularValorTotal());
                System.out.println(" Tiempo de procesamiento concurrente: " + duracion + " ms.");
            }

        } while (opcion != 3);

        scanner.close();
    }

	/**
	 * Evalua un subarbol en el hilo actual. Recibe la abstraccion comun, por lo que
	 * funciona igual para una partida individual que para un grupo con niveles internos.
	 */
	public static void procesarSubarbolParalelo(IElementoPresupuestario elemento) {
		String hilo = Thread.currentThread().getName();
		double subtotal = elemento.calcularValorTotal();
		System.out.println(" [" + hilo + "] [" + elemento.obtenerCodigo() + "] "
				+ elemento.obtenerDescripcion() + " -> Subtotal: $" + subtotal);
	}
}
