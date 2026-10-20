# Demostracion del patron Adapter

Ejemplo educativo compatible con Java 8 o superior, sin dependencias externas.
Los bancos son simulaciones locales: no se consulta una API ni se procesan
pagos reales. Los ejemplos existentes del paquete ejercicios no se modifican.

## Correspondencia con el diagrama

| Participante | Clase | Responsabilidad |
| --- | --- | --- |
| Cliente | SistemaUniversidad | Consultar una cuota usando exclusivamente ITargetBanco. |
| Target | ITargetBanco | Definir verificarPagoCuota(String): boolean. |
| Adapter A | AdaptadorBancoA | Convertir el ID a int y el estado del servicio a boolean. |
| Adapter B | AdaptadorBancoB | Convertir el ID a una referencia externa CUOTA-ID. |
| Adaptee A | ServicioBancoA | Ofrecer consultarEstadoTransaccion(int): String. |
| Adaptee B | ServicioBancoB | Ofrecer validarFacturaExterna(String): boolean. |
| Demostracion | Ejecutable | Crear los objetos y permitir consultas desde un menu. |

Los constructores permiten inyectar las dependencias. Son complementos del
diagrama, cuyas firmas de consulta se conservan. Los adaptadores contienen sus
servicios por composicion: este es un Adapter de objetos, no de clases.

## Ejecutar desde la terminal de VS Code

Abre una terminal en la carpeta ejercicios, que contiene src y bin:

```powershell
javac -encoding UTF-8 -Xlint:all -d bin src/patronadapter/*.java
java -cp bin patronadapter.Ejecutable
```

La compilacion crea las clases en bin/patronadapter. El comando java ejecuta
el main del nuevo paquete, no el ejecutable del paquete ejercicios.

Con soporte Java instalado en VS Code tambien puedes abrir
[Ejecutable.java](src/patronadapter/Ejecutable.java) y pulsar Run Java sobre main.
Introduce las respuestas en la terminal de ejecucion, no en Debug Console.

## Guion interactivo para la exposicion

1. Ejecuta el programa. El banco activo inicialmente es A.
2. Selecciona 3 e introduce 1001. Observa el ID String convertido a int,
   la respuesta COMPLETADA y su conversion a true. La universidad muestra PAGADA.
3. Selecciona 3 e introduce 1002. El servicio responde PENDIENTE y el
   adaptador devuelve false: PAGO NO CONFIRMADO.
4. Selecciona 2 para cambiar al banco B, luego 3 e introduce 1001.
   Observa la referencia CUOTA-1001 y el resultado true.
5. Selecciona 4 para ejecutar ocho consultas: cuatro IDs en cada banco.
   La comparacion no cambia el banco activo del menu.
6. Selecciona 1, luego 3 e introduce abc para mostrar el manejo del ID
   incompatible con el contrato numerico del banco A.
7. Selecciona 0 para salir.

### Datos y resultados esperados

| ID | Respuesta banco A | Respuesta banco B | Resultado de la universidad |
| --- | --- | --- | --- |
| 1001 | COMPLETADA | true | PAGADA |
| 1002 | PENDIENTE | false | PAGO NO CONFIRMADO |
| 1003 | RECHAZADA | false | PAGO NO CONFIRMADO |
| 9999 | NO_ENCONTRADA | false | PAGO NO CONFIRMADO |

El banco B solo confirma CUOTA-1001; cualquier otra referencia devuelve false.
Por el contrato boolean del diagrama, false no distingue entre pendiente,
rechazada o inexistente. Por eso no se presenta una cuota inexistente como una
deuda confirmada. Una aplicacion real podria necesitar un resultado mas rico,
pero eso cambiaria el contrato del ejercicio.

El banco A acepta IDs positivos dentro del rango int. El banco B acepta IDs
no vacios porque su servicio usa referencias de texto. Esta diferencia es
deliberada: cada adaptador realiza la conversion necesaria para su proveedor.

## Revisar con el depurador de VS Code

Requisito: soporte de lenguaje Java y Debugger for Java disponibles en el editor.

1. Coloca un breakpoint haciendo clic en el margen izquierdo de
   [SistemaUniversidad.java](src/patronadapter/SistemaUniversidad.java), en
   la instruccion boolean pagada = banco.verificarPagoCuota(idCuota).
2. Coloca otro en cada implementacion de verificarPagoCuota de
   [AdaptadorBancoA.java](src/patronadapter/AdaptadorBancoA.java) y
   [AdaptadorBancoB.java](src/patronadapter/AdaptadorBancoB.java).
3. Abre [Ejecutable.java](src/patronadapter/Ejecutable.java) y pulsa Debug Java
   sobre main. Consulta 1001 con la opcion 3 en la terminal.
4. Inspecciona idCuota y banco en Variables. Aunque banco esta declarado
   como ITargetBanco, el objeto concreto es AdaptadorBancoA.
5. Usa F11 (Step Into) para entrar al adaptador y al servicio, F10 (Step Over)
   para avanzar, Shift+F11 (Step Out) para volver y F5 para continuar.
   Inspecciona codigoTransaccion, estado y pagada conforme se asignan.
6. Cambia al banco B con la opcion 2 y repite la consulta. Inspecciona
   referencia: ahora vale CUOTA-1001, y banco es un AdaptadorBancoB.
7. Observa Call Stack: Ejecutable -> SistemaUniversidad -> Adaptador -> Servicio.

Si el soporte Java del editor no esta disponible, el menu por terminal y las
trazas permiten observar el mismo flujo sin depurador.

## Ideas clave para explicar al profesor

- Problema: los proveedores ofrecen contratos incompatibles con lo que necesita
  la universidad, tanto en nombres como en parametros y resultados.
- Solucion: cada adaptador implementa la interfaz esperada y delega al servicio
  existente despues de traducir los datos.
- Evidencia: se cambia de proveedor sin modificar SistemaUniversidad ni su
  llamada verificarPagoCuota(idCuota). El ensamblaje de objetos esta en Ejecutable.
- Polimorfismo: la misma llamada al Target ejecuta un adaptador distinto segun
  el objeto inyectado. El proposito aqui es compatibilizar contratos, no solo
  escoger algoritmos como en Strategy.
- Extension: para integrar otro banco se crea su servicio y su adaptador, y se
  conecta en el punto de ensamblaje; el cliente permanece igual.
- Limite: la traduccion de estados implica decisiones de negocio. Este ejemplo
  considera pagado solo COMPLETADA y usa datos fijos para ser reproducible.

## Estado de verificacion

El paquete compilo con javac 1.8.0_202 y -Xlint:all sin diagnosticos.
El editor no reporto errores en el paquete. La comprobacion automatizada del
menu no quedo confirmada; el guion anterior permite verificarlo manualmente.