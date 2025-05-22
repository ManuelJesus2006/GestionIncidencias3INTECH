**WEB GESTIÓN INCIDENCIAS**

**RESUMEN USO DE LA APLICACIÓN: La instalación del programa, el enlace de la instalación y sus complementos estan en el manual de usuario en este repositorio**

**Índice de contenidos:**

- Apartado de login

- Dashboard Admin

  - Alta/baja/modificacion de usuarios (clientes/tecnicos)

  - Asignación manual de incidencias a técnicos

  - Visualización de todos los tickets

- Dashboard Cliente

  - Crear nueva incidencia

  - Consultar historial de incidencias propias

  - Consultar el estado de una incidencia concreta

- Dashboard Trabajador

  - Ver incidencias asignadas

  - Marcar incidencia como resuelta (añadir descripción resolución)

  - Cambiar estado


**Apartado de login**

Ya estamos en la parte principal de este manual y comenzaremos por el
apartado más simple el apartado de login. Antes de nada debemos de tener
MariaDB o tu server elegido de MySQL Server funcionando en el puerto
3306 y el JDK 24 ya instalado en el sistema. Con estos requisitos hechos
iniciamos el ejecutable .bat llamado "iniciarGestionIncidencias".

Una vez hecho doble click nos aparecerá una ventana de cmd de Windows
que NO deberemos cerrar hasta que queramos cerrar el programa web:

![](media/image11.png){width="5.905555555555556in"
height="3.3006944444444444in"}  
Para acceder a la aplicación bastará con ir a cualquier navegador de
nuestra preferencia, en nuestro caso usaremos Google Chrome y pondremos
en el buscador de arriba localhost y el puerto deseado desde el archivo
"application.properties":

![](media/image12.png){width="1.6666666666666667in"
height="0.6145833333333334in"}![](media/image13.png){width="5.905555555555556in"
height="1.5645833333333334in"}

Una vez accedido a localhost:8082 se nos abrirá una página de inicio de
sesión:

![](media/image14.png){width="4.750694444444444in" height="3.84375in"}

En este inicio de sesión es donde comienza el programa. En este inicio
de sesión se pueden loguear tanto admins, clientes y técnicos.

Si se introducen los datos incorrectos sale el siguiente error:

![](media/image15.png){width="3.972916666666667in"
height="3.8777777777777778in"}  
  
  
Si se ponen los datos correctos pasa al dashboard de dicho usuario:

![](media/image16.png){width="5.905555555555556in"
height="2.772222222222222in"}

Lo primero que debemos hacer es acceder al dashboard del admin para que
cree los clientes y los técnicos.

**Dashboard Admin**

Para acceder al dashboard de admin hemos puesto un email y una
contraseña especificas:

**Usuario: <root@root>**

**Contraseña: 1234root**

![](media/image16.png){width="5.5125in" height="2.5875in"}Una vez en el
dashboard de Admin tras iniciar sesión tenemos tres acciones diferentes

Las explicaremos en diferentes apartados

**Alta/baja/modificación de usuarios (clientes/técnicos)**

En este primer apartado del programa crearemos, borraremos y
modificaremos clientes y técnicos, cuando hacemos click en la opción nos
sale un submenú:

![](media/image17.png){width="2.808333333333333in"
height="2.654861111111111in"}

**En el apartado de Registrar Cliente,** nos pedirá todos los datos del
cliente a insertar, su id se creará automáticamente:

![](media/image18.png){width="4.45625in"
height="2.3222222222222224in"}  
Cada vez que finalice una acción similar a esta saldrá está ventana,
pidiendo que ![](media/image19.png){width="5.905555555555556in"
height="1.6027777777777779in"}esperemos unos 3 segundos antes de
continuar usando el programa:

Si revisamos la base de datos en la tabla de los clientes, vemos como se
ha creado con los datos introducidos:

![](media/image20.png){width="4.656944444444444in" height="1.5in"}

**En el apartado de Registrar Técnico,** ocurrirá lo mismo que con el
anterior, solo que se creará un técnico:

![](media/image21.png){width="5.905555555555556in"
height="2.7423611111111112in"}  
![](media/image22.png){width="4.615277777777778in" height="1.53125in"}

**En el apartado Dar de Baja Usuario,** nos saldrá una selección de
usuario con el tipo de usuario, el nombre y el correo. Si seleccionamos
dicho usuario y pulsamos "Dar de Baja Usuario" se eliminará de la base
de datos:

![](media/image23.png){width="5.905555555555556in"
height="2.0034722222222223in"}  
  
ANTES:  
![](media/image22.png){width="4.615277777777778in" height="1.53125in"}  
DESPUÉS:  
  
![](media/image24.png){width="4.542361111111111in"
height="0.5729166666666666in"}  
  
![](media/image25.png){width="5.905555555555556in"
height="2.339583333333333in"}  
  
"Empty set" significa que no hay ningún dato en la tabla

**En el apartado de Modificar Usuario,** nos volverá a salir una
selección de usuario con el tipo de usuario, el nombre y el correo:

![](media/image26.png){width="3.652083333333333in"
height="2.127083333333333in"}

Si seleccionamos dicho usuario y pulsamos "Mostrar Formulario de
Modificacion" nos saldrá un formulario para modificar los datos de dicho
usuario, y podemos dejar en blanco la clave si no quisiéramos cambiarla:

![](media/image27.png){width="4.808333333333334in" height="3.55in"}

Si ponemos los cambios y pulsamos "Guardar Cambios" se cambiarán los
datos de dicho usuario en la base de datos:

ANTES:

![](media/image20.png){width="4.656944444444444in" height="1.5in"}  
DESPUÉS:

![](media/image28.png){width="4.822916666666667in" height="1.46875in"}  
**Asignación manual de incidencias a técnicos**

Para probar está tendremos que tener creados un técnico y un cliente. En
el cliente debe de haber una incidencia ya creada:

![](media/image29.png){width="4.459722222222222in"
height="1.8652777777777778in"}![](media/image30.png){width="5.905555555555556in"
height="0.7833333333333333in"}**  
  
**Cuando abrimos la opción con incidencias creadas saldrá una lista de
todas las que están sin asignar y un botón para asignarlo:

Si no hay ninguna incidencia registrada o ya está asignada saldrá este
mensaje:

![](media/image31.png){width="5.905555555555556in"
height="1.8076388888888888in"}

**[ADVERTENCIA: SI UNA INCIDENCIA YA HA SIDO RESUELTA, EL TECNICO LA
SEGUIRÁ TENIENDO AUNQUE NO PODRÁ VOLVER A CAMBIAR EL ESTADO NI PODRÁ
VOLVER A SER ASIGNADA POR OTRO TRABAJADOR]{.underline}**

![](media/image32.png){width="5.905555555555556in"
height="2.832638888888889in"}Si le damos click a "Elegir para Asignar" a
cualquier incidencia, saldrá una ventana como está para elegir con un
desplegable el técnico que queremos:

![](media/image33.png){width="4.260504155730533in"
height="2.102195975503062in"}Vamos a elegir el técnico que sale en la
captura de antes y le damos a Confirmar Asignación:

Saldrá una página con el mensaje "La incidencia ha sido asignada con
éxito" y redigirá al inicio del administrador en 3 segundos.

![](media/image34.png){width="5.905555555555556in"
height="0.8194444444444444in"}Si miramos ahora la base de datos de
Incidencias vemos que en el id del técnico tiene el mismo id del técnico
de la captura:

Si vamos al técnico y vemos las incidencias asignadas ya aparece:

![](media/image35.png){width="5.905555555555556in"
height="0.9736111111111111in"}  
  
**Visualización de todos los tickets**

![](media/image36.png){width="4.907562335958005in"
height="1.9245909886264216in"}Esta opción solamente mostrará la
información de todos los tickets de incidencias:

**Dashboard Cliente**

El cliente, como hemos dicho anteriormente, debe haber sido creado por
el administrador, mientras que el administrador no cree el usuario no
podrá acceder a las funciones.

![](media/image37.png){width="4.394444444444445in"
height="1.9027777777777777in"}Cuando iniciamos sesión en el login con
los datos del cliente, nos saldrá el dashboard de cliente:

**Crear nueva incidencia**

![](media/image38.png){width="3.747916666666667in"
height="2.6465277777777776in"}Cuando hacemos click en esta funcionalidad
nos aparecerá un área texto donde introducir detalladamente la
incidencia.

![](media/image39.png){width="4.327083333333333in"
height="1.5291666666666666in"}![](media/image40.png){width="4.075in"
height="1.6076388888888888in"}  
  
Cuando introducimos los datos en el área de texto y pulsamos "Enviar
Incidencia" nos volverá a salir una página con este mensaje y
redirección al inicio del usuario en 3 segundos:

**Consultar historial incidencias propias**

![](media/image41.png){width="4.520833333333333in"
height="1.9722222222222223in"}La siguiente funcionalidad del cliente es
ver las incidencias que ya ha realizado, esto nos sirve para ver si el
ejemplo del apartado de crear nueva incidencia ha sido registrado.
Hacemos click en la función y vemos que se ha registrado correctamente:

**Consultar el estado de una incidencia concreta  
**

![](media/image42.png){width="3.134027777777778in"
height="2.579861111111111in"}Muy similar a la anterior función, solo que
tendremos que introducir la id de la incidencia para ver su estado.
Hacemos click en esta función y nos pedirá la ID de una incidencia que
el cliente haya realizado:

![](media/image43.png){width="4.070833333333334in"
height="2.688888888888889in"}Introducimos una de las IDs que aparecen en
la función anterior y le damos en "Consultar" y nos aparecerá esto:

Sale el estado de la incidencia arriba del todo en grande y los datos de
la incidencia en lista y de forma ordenada.

**Dashboard Técnico**

![](media/image44.png){width="5.769444444444445in"
height="2.713888888888889in"}El técnico, también debe ser creado por el
administrador y se encargará de la gestión de las incidencias. Cuando
iniciamos sesión con los datos del técnico en el login, nos saldrá su
dashboard:

**Ver incidencias asignadas**

La primera funcionalidad del técnico será la de ver sus incidencias
asignadas por el administrador. Cuando hacemos click en dicha opción,
aparecerán de forma ordenada:

![](media/image45.png){width="5.905555555555556in"
height="2.785416666666667in"}

**Marcar la incidencia como resuelta (añadir descripción resolución)**

Esta funcionalidad es de las más importantes, el técnico en está opción
pondrá la incidencia como resuelta y añadirá una descripción de cual erá
el problema o como se arregló.

Cuando hacemos click nos saldrá una lista con las incidencias
disponibles y un botón para elegir cual queremos marcar como resuelta:

![](media/image46.png){width="5.905555555555556in"
height="2.5097222222222224in"}

Damos click en la primera por ejemplo, nos saldrá una página con los
datos de la incidencia y un área de texto donde poner la información de
la resolución de la incidencia:

![](media/image47.png){width="5.905555555555556in"
height="5.334027777777778in"}

![](media/image48.png){width="4.104166666666667in"
height="1.7979166666666666in"}Si introducimos la información en el área
de texto y pulsamos "Marcar como Resuelta", nos volverá a salir otra
página con este mensaje y un redirección de 3 segundos al inicio de
nuestro usuario:

Si volvemos a la primera función de ver incidencias asignadas podemos
ver que sigue estando asignada pero como resuelta. De hecho si volvemos
a la opción de marcar incidencia como resuelta, no nos volverá a salir
la incidencia ya que está resuelta:

![](media/image49.png){width="5.905555555555556in"
height="2.6805555555555554in"}

![](media/image50.png){width="5.905555555555556in"
height="2.1381944444444443in"}

Si volvemos a la administración, vemos que no hay incidencias que
asignar ya que una ya está asignada y la otra ya está resuelta:

![](media/image51.png){width="5.905555555555556in" height="1.5875in"}

![](media/image52.png){width="4.863194444444445in"
height="2.0840277777777776in"}Si vamos ahora al cliente que realizó la
incidencia podemos ver que sale como resuelta y la descripción
resolución introducida anteriormente:

**Cambiar estado**

Hace la misma función que el anterior, solo que se pueden cambiar entre
estados diferentes (pendiente / en curso y resuelta)

![](media/image53.png){width="4.377777777777778in"
height="1.3388888888888888in"}Hacemos click en la última función del
técnico y nos volverá a salir una lista con las incidencias asignadas y
un botón que pone "Cambiar estado".

![](media/image54.png){width="3.7895833333333333in"
height="4.041666666666667in"}Si hacemos click en cambiar estado a la
incidencia concreta nos saldrá un formulario donde poner el estado nuevo
de dicha incidencia:

Si ponemos el nuevo estado como resuelto en la selección del formulario,
automáticamente saldrá un área de texto donde poner la descripción de la
resolución de la incidencia:

![](media/image55.png){width="4.176388888888889in" height="2.5125in"}

![](media/image56.png){width="4.5125in"
height="1.6847222222222222in"}Vamos a probar a poner el estado "en
curso" y le damos a actualizar, nos volverá a salir la misma página con
este mensaje y un redirección de 3 segundos al inicio de nuestro
usuario:

Vemos que efectivamente se ha cambiado:

![](media/image57.png){width="5.905555555555556in"
height="0.4576388888888889in"}  
  
Un técnico podra seguir cambiando el estado de la incidencia hasta que
se ponga como resuelta.
