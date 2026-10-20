package escenario02;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
public class MainAsistenciaConcurrente {


	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        ISistemaAsistencia adaptadorLegacy = new BiometricoLegacyAdapter(new BibliotecaBiometricaLegacy());
        ISistemaAsistencia adaptadorCloud = new BiometricoNuevoAdapter(new CloudBiometricApi());

        int opcion = 0;
        do {
            System.out.println("\n==================================================");
            System.out.println("   SISTEMA DE ASISTENCIA BIOMÉTRICA (ADAPTER)");
            System.out.println("==================================================");
            System.out.println("1. Consultar asistencia con Dispositivo Legacy (Adaptador 1)");
            System.out.println("2. Consultar asistencia con Dispositivo Cloud (Adaptador 2)");
            System.out.println("3. Comparar registros de ambos dispositivos en PARALELO");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                scanner.next();
                continue;
            }

            if (opcion == 1) {
                System.out.println("\n--- CONSULTA INDIVIDUAL LEGACY ---");
                RegistroNormalizado reg = adaptadorLegacy.obtenerRegistroAsistencia("COL-1001", "2026-10-19");
                System.out.println(reg);
            } else if (opcion == 2) {
                System.out.println("\n--- CONSULTA INDIVIDUAL CLOUD ---");
                RegistroNormalizado reg = adaptadorCloud.obtenerRegistroAsistencia("COL-1002", "2026-10-19");
                System.out.println(reg);
            } else if (opcion == 3) {
                System.out.println("\n--- CONSULTA Y COMPARACIÓN EN PARALELO ---");
                long inicio = System.currentTimeMillis();

                CompletableFuture<RegistroNormalizado> tarea1 = CompletableFuture.supplyAsync(() -> {
                    return adaptadorLegacy.obtenerRegistroAsistencia("COL-1001", "2026-10-19");
                });

                CompletableFuture<RegistroNormalizado> tarea2 = CompletableFuture.supplyAsync(() -> {
                    return adaptadorCloud.obtenerRegistroAsistencia("COL-1002", "2026-10-19");
                });

                CompletableFuture.allOf(tarea1, tarea2).join();
                long duracion = System.currentTimeMillis() - inicio;

                System.out.println(" Resultados Concurrentes Obtenidos:");
                System.out.println(" - Dispositivo Legacy: " + tarea1.join());
                System.out.println(" - Dispositivo Cloud:  " + tarea2.join());
                System.out.println(" Tiempo total de respuesta en paralelo: " + duracion + " ms.");
            }

        } while (opcion != 4);

        scanner.close();
    }
}