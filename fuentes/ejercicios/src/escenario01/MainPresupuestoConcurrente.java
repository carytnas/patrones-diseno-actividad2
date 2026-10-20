package escenario01;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
public class MainPresupuestoConcurrente {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GrupoPresupuestario presupuestoGeneral = new GrupoPresupuestario("G-01", "Presupuesto Institucional 2026");
        
        GrupoPresupuestario dptoTI = new GrupoPresupuestario("G-TI", "Tecnologías de la Información");
        dptoTI.agregar(new PartidaPresupuestaria("P-01", "Licenciamientos Cloud", 15000.00));
        
        GrupoPresupuestario infraestructura = new GrupoPresupuestario("G-INF", "Infraestructura de Red");
        infraestructura.agregar(new PartidaPresupuestaria("P-02", "Servidores Blade", 35000.00));
        infraestructura.agregar(new PartidaPresupuestaria("P-03", "Switches Core", 12000.00));
        
        dptoTI.agregar(infraestructura);
        presupuestoGeneral.agregar(dptoTI);
        presupuestoGeneral.agregar(new PartidaPresupuestaria("P-04", "Auditoría Externa Sistemas", 8000.00));

        int opcion = 0;
        do {
            System.out.println("\n==================================================");
            System.out.println("   SISTEMA DE GESTIÓN PRESUPUESTARIA (COMPOSITE)");
            System.out.println("==================================================");
            System.out.println("1. Mostrar estructura completa y cálculo secuencial");
            System.out.println("2. Evaluar subgrupos de forma PARALELA (Concurrente)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

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
                System.out.println("\n--- ANÁLISIS Y CÁLCULO PARALELO DE SUBÁRBOLES ---");
                long inicio = System.currentTimeMillis();

                // Procesamiento concurrente de los dos grandes bloques del presupuesto general
                CompletableFuture<Double> tareaTI = CompletableFuture.supplyAsync(() -> {
                    System.out.println(" [Hilo TI] Calculando presupuesto de Tecnologías de la Información...");
                    return dptoTI.calcularValorTotal();
                });

                CompletableFuture<Double> tareaAuditoria = CompletableFuture.supplyAsync(() -> {
                    System.out.println(" [Hilo Auditoría] Consultando partida de Auditoría Externa...");
                    return 8000.00;
                });

                CompletableFuture.allOf(tareaTI, tareaAuditoria).join();

                long duracion = System.currentTimeMillis() - inicio;
                System.out.println(" Resultados Paralelos:");
                System.out.println(" - Subtotal TI: $" + tareaTI.join());
                System.out.println(" - Subtotal Auditoría: $" + tareaAuditoria.join());
                System.out.println(" Tiempo de procesamiento concurrente: " + duracion + " ms.");
            }

        } while (opcion != 3);

        scanner.close();
    }
}