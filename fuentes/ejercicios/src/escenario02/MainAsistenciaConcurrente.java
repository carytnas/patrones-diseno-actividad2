package escenario02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Cliente del patron Adapter: solo conoce ISistemaAsistencia y RegistroNormalizado,
 * nunca las bibliotecas de los fabricantes.
 */
public class MainAsistenciaConcurrente {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Unico punto donde se conocen los fabricantes: se ensamblan los adaptadores
        ISistemaAsistencia adaptadorLegacy = new BiometricoLegacyAdapter(new BibliotecaBiometricaLegacy());
        ISistemaAsistencia adaptadorCloud = new BiometricoNuevoAdapter(new CloudBiometricApi());
        // Un nuevo proveedor se integra agregando su adaptador a esta lista
        List<ISistemaAsistencia> dispositivos = Arrays.asList(adaptadorLegacy, adaptadorCloud);

        String fecha = "2026-10-19";
        int opcion = 0;
        do {
            System.out.println("\n==================================================");
            System.out.println("   SISTEMA DE ASISTENCIA BIOMETRICA (ADAPTER)");
            System.out.println("==================================================");
            System.out.println("Fecha de consulta: " + fecha);
            System.out.println("1. Consultar asistencia con Dispositivo Legacy (Adaptador 1)");
            System.out.println("2. Consultar asistencia con Dispositivo Cloud (Adaptador 2)");
            System.out.println("3. Consultar todos los dispositivos en PARALELO");
            System.out.println("4. Cambiar fecha de consulta (yyyy-MM-dd)");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
            } else {
                scanner.next();
                continue;
            }

            if (opcion == 1) {
                System.out.println("\n--- CONSULTA INDIVIDUAL LEGACY ---");
                System.out.println(adaptadorLegacy.obtenerRegistroAsistencia("COL-1001", fecha));
            } else if (opcion == 2) {
                System.out.println("\n--- CONSULTA INDIVIDUAL CLOUD ---");
                System.out.println(adaptadorCloud.obtenerRegistroAsistencia("COL-1002", fecha));
            } else if (opcion == 3) {
                System.out.println("\n--- CONSULTA DE DISPOSITIVOS EN PARALELO ---");
                long inicio = System.currentTimeMillis();

                List<CompletableFuture<RegistroNormalizado>> tareas = new ArrayList<>();
                for (int i = 0; i < dispositivos.size(); i++) {
                    ISistemaAsistencia dispositivo = dispositivos.get(i);
                    String idPersona = "COL-100" + (i + 1);
                    String fechaConsulta = fecha;
                    tareas.add(CompletableFuture.supplyAsync(
                            () -> dispositivo.obtenerRegistroAsistencia(idPersona, fechaConsulta)));
                }
                CompletableFuture.allOf(tareas.toArray(new CompletableFuture<?>[0])).join();
                long duracion = System.currentTimeMillis() - inicio;

                System.out.println(" Resultados Concurrentes Obtenidos (formato normalizado):");
                for (CompletableFuture<RegistroNormalizado> tarea : tareas) {
                    System.out.println(" - " + tarea.join());
                }
                System.out.println(" Tiempo total de respuesta en paralelo: " + duracion + " ms.");
            } else if (opcion == 4) {
                System.out.print("Nueva fecha (yyyy-MM-dd): ");
                String nueva = scanner.next();
                if (nueva.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    fecha = nueva;
                } else {
                    System.out.println("Formato invalido, se mantiene " + fecha);
                }
            }

        } while (opcion != 5);

        scanner.close();
    }
}
