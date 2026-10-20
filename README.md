# patrones-diseno-actividad2

Actividad 02 – Patrones estructurales (Patrones de Diseño de Software, Maestría en Software, UPS). Grupo 02.

| Escenario | Patrón | Paquete |
| --- | --- | --- |
| 01 – Estructura jerárquica del presupuesto | Composite | `fuentes/ejercicios/src/escenario01` |
| 02 – Integración de dispositivos biométricos | Adapter (de objetos) | `fuentes/ejercicios/src/escenario02` |

## Estructura del repositorio

- `Documentacion/` – informe de la actividad (`PDS_Actividad_02_Grupo02.docx`).
- `Diagramas/Actividad02.drawio` – diagramas UML (Composite inicial/final, Adapter inicial/final).
- `fuentes/ejercicios/` – proyecto Java (Eclipse), sin dependencias externas, Java 8 o superior.

## Correspondencia UML – código

### Escenario 01: Composite

| Participante | Clase |
| --- | --- |
| Component | `IElementoPresupuestario` |
| Leaf | `PartidaPresupuestaria` |
| Composite | `GrupoPresupuestario` |
| Client | `MainPresupuestoConcurrente` (incluye `procesarSubarbolParalelo`) |

### Escenario 02: Adapter

| Participante | Clase |
| --- | --- |
| Target | `ISistemaAsistencia` |
| Adapter | `BiometricoLegacyAdapter`, `BiometricoNuevoAdapter` |
| Adaptee | `BibliotecaBiometricaLegacy` (`getLogsOld`), `CloudBiometricApi` (`fetchUserData`) |
| Client | `MainAsistenciaConcurrente` |
| Dato normalizado | `RegistroNormalizado` |

`JsonObject` simula el tipo de dato propio del SDK del fabricante cloud.

Transformaciones que realiza cada adaptador:

- **Legacy:** `"COL-1001"` → `1001` (int); filtra, entre todas las marcaciones en texto `ID:..|DATE:..|IN:..|OUT:..`, la de la fecha pedida.
- **Cloud:** arma la consulta JSON con la fecha `yyyyMMdd`; convierte `firstPunch`/`lastPunch` ISO‑8601 a `HH:mm`.
- Si no hay marcación para la fecha, ambos devuelven `--:--`.

## Compilar y ejecutar

Desde `fuentes/ejercicios`:

```bash
javac -d bin src/escenario01/*.java src/escenario02/*.java
java -cp bin escenario01.MainPresupuestoConcurrente
java -cp bin escenario02.MainAsistenciaConcurrente
```

En Eclipse: importar `fuentes/ejercicios` como proyecto existente y ejecutar cualquiera de las dos clases `Main*` (Run As → Java Application).

Datos de prueba del escenario 02: el dispositivo legacy tiene marcaciones del 2026-10-18 al 2026-10-20; el cloud solo del 2026-10-19 (opción 4 para cambiar la fecha).
