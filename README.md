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

![image](https://github.com/user-attachments/assets/7117901d-11d4-4c68-8384-3fc929869b71)
 
Para acceder a la aplicación bastará con ir a cualquier navegador de
nuestra preferencia, en nuestro caso usaremos Google Chrome y pondremos
en el buscador de arriba localhost y el puerto deseado desde el archivo
"application.properties":

![image](https://github.com/user-attachments/assets/5d1695cd-1b1c-499c-bfb0-1d96e409367b)

![image](https://github.com/user-attachments/assets/ae9fb2aa-1778-4627-a4f4-77cab8653a86)


Una vez accedido a localhost:8082 o la que deseemos se nos abrirá una página de inicio de
sesión:

![image](https://github.com/user-attachments/assets/8766ba4d-b6b7-4957-97f2-f1de8f337100)


En este inicio de sesión es donde comienza el programa. En este inicio
de sesión se pueden loguear tanto admins, clientes y técnicos.

Si se introducen los datos incorrectos sale el siguiente error:

![image](https://github.com/user-attachments/assets/dccb591d-868d-4f6b-bbf8-253e5c17c60d)

Si se ponen los datos correctos pasa al dashboard de dicho usuario

Lo primero que debemos hacer es acceder al dashboard del admin para que
cree los clientes y los técnicos.

**Dashboard Admin**

Para acceder al dashboard de admin hemos puesto un email y una
contraseña especificas:

**Usuario: <root@root>**

**Contraseña: 1234root**

![image](https://github.com/user-attachments/assets/0bae9233-4574-4fa3-83b9-6f3e5b79feda)

Una vez en el dashboard de Admin tras iniciar sesión tenemos tres acciones diferentes

Las explicaremos en diferentes apartados

***Alta/baja/modificación de usuarios (clientes/técnicos)***

En este primer apartado del programa crearemos, borraremos y
modificaremos clientes y técnicos, cuando hacemos click en la opción nos
sale un submenú:

![image](https://github.com/user-attachments/assets/6cdd5af3-dbd2-4171-9d39-98dfd794dd33)

**En el apartado de Registrar Cliente,** nos pedirá todos los datos del
cliente a insertar, su id se creará automáticamente:

![image](https://github.com/user-attachments/assets/81fdb719-d7fc-49fd-bce2-b4f92eebb876)

Cada vez que finalice una acción similar a esta saldrá está ventana,
pidiendo que esperemos unos 3 segundos antes de
continuar usando el programa:

![image](https://github.com/user-attachments/assets/d491f46a-af45-45a1-8224-903470a79db0)

Si revisamos la base de datos en la tabla de los clientes, vemos como se
ha creado con los datos introducidos:

![image](https://github.com/user-attachments/assets/84172ac7-ccc7-45ef-ac74-754c676ed323)

**En el apartado de Registrar Técnico,** ocurrirá lo mismo que con el
anterior, solo que se creará un técnico:

![image](https://github.com/user-attachments/assets/7cb51ae6-0968-4f79-aaad-c298f367267e)

![image](https://github.com/user-attachments/assets/380c9473-6ba3-4e4a-bd6e-85e3e3d0d018)

**En el apartado Dar de Baja Usuario,** nos saldrá una selección de
usuario con el tipo de usuario, el nombre y el correo. Si seleccionamos
dicho usuario y pulsamos "Dar de Baja Usuario" se eliminará de la base
de datos:

![image](https://github.com/user-attachments/assets/cb2fa5d9-8dd5-46dd-8070-53aece12a9fd)

![image](https://github.com/user-attachments/assets/e548031b-4b87-4a39-97c9-1f8a10064369)

ANTES:  
![image](https://github.com/user-attachments/assets/da292ada-0e7d-4c74-a2b6-654406596f6c)

DESPUÉS:

![image](https://github.com/user-attachments/assets/ade8c6d3-d4a8-4351-8a1d-5a4aa4726955)
  
"Empty set" significa que no hay ningún dato en la tabla

**En el apartado de Modificar Usuario,** nos volverá a salir una
selección de usuario con el tipo de usuario, el nombre y el correo:

![image](https://github.com/user-attachments/assets/87c5309f-a7a1-490a-9540-2b9940086bb0)

Si seleccionamos dicho usuario y pulsamos "Mostrar Formulario de
Modificacion" nos saldrá un formulario para modificar los datos de dicho
usuario, y podemos dejar en blanco la clave si no quisiéramos cambiarla:

![image](https://github.com/user-attachments/assets/88d75bdd-d699-4275-8dfd-e8b1eae187ec)

Si ponemos los cambios y pulsamos "Guardar Cambios" se cambiarán los
datos de dicho usuario en la base de datos:

ANTES:

![image](https://github.com/user-attachments/assets/3688ee37-e80f-4ad9-9b00-5d4f3d2f8c26)

DESPUÉS:

![image](https://github.com/user-attachments/assets/5050e24e-dccb-4118-a59c-7e6b4cbac8eb)

***Asignación manual de incidencias a técnicos***

Para probar está tendremos que tener creados un técnico y un cliente. En
el cliente debe de haber una incidencia ya creada:

![image](https://github.com/user-attachments/assets/5cd98c7f-7b45-4a76-a99f-5ae3e295d833)

**Cuando abrimos la opción con incidencias creadas saldrá una lista de
todas las que están sin asignar y un botón para asignarlo:

![image](https://github.com/user-attachments/assets/1524816f-2c75-4fd5-aed9-66a9bca78db6)

Si no hay ninguna incidencia registrada o ya está asignada saldrá este
mensaje:

![image](https://github.com/user-attachments/assets/8480e10a-ffbc-4cf6-aec4-09bf59fa74b2)


***[ADVERTENCIA: SI UNA INCIDENCIA YA HA SIDO RESUELTA, EL TECNICO LA
SEGUIRÁ TENIENDO AUNQUE NO PODRÁ VOLVER A CAMBIAR EL ESTADO NI PODRÁ
VOLVER A SER ASIGNADA POR OTRO TRABAJADOR]{.underline}***

Si le damos click a "Elegir para Asignar" a
cualquier incidencia, saldrá una ventana como está para elegir con un
desplegable el técnico que queremos:

![image](https://github.com/user-attachments/assets/68974b61-40e7-46cb-9c49-8954042bbf7c)

Vamos a elegir el técnico que sale en la
captura de antes y le damos a Confirmar Asignación, saldrá nua página con el mensaje "La incidencia ha sido asignada con
éxito" y redigirá al inicio del administrador en 3 segundos:

![image](https://github.com/user-attachments/assets/6898284c-3dcc-4c0e-b057-8ed8e66d93ba)

Si miramos ahora la base de datos de
Incidencias vemos que en el id del técnico tiene el mismo id del técnico
de la captura:

![image](https://github.com/user-attachments/assets/0c53c5b1-13d0-458f-ad9b-2f6301f1a7ae)


Si vamos al técnico y vemos las incidencias asignadas ya aparece:

![image](https://github.com/user-attachments/assets/d695e8aa-cbe8-4d37-a186-c890f2d30ad4)

  
***Visualización de todos los tickets***

Esta opción solamente mostrará la información de todos los tickets de incidencias:

![image](https://github.com/user-attachments/assets/455ea532-5b34-4001-a212-3e492146d25c)


**Dashboard Cliente**

El cliente, como hemos dicho anteriormente, debe haber sido creado por
el administrador, mientras que el administrador no cree el usuario no
podrá acceder a las funciones.

Cuando iniciamos sesión en el login con
los datos del cliente, nos saldrá el dashboard de cliente:

![image](https://github.com/user-attachments/assets/ee9df1c0-131f-4d2a-bc63-079daa3ef45c)


***Crear nueva incidencia***

Cuando hacemos click en esta funcionalidadnos aparecerá un área texto donde introducir detalladamente la incidencia.

![image](https://github.com/user-attachments/assets/2ec24640-aad2-4af0-a3a9-a6fed3f34024) 
  
Cuando introducimos los datos en el área de texto y pulsamos "Enviar
Incidencia" nos volverá a salir una página con este mensaje y
redirección al inicio del usuario en 3 segundos:

![image](https://github.com/user-attachments/assets/0f39a80a-d6b6-4c7b-86de-55f20a0c2616)

![image](https://github.com/user-attachments/assets/17954c3e-9779-4954-88f7-b44fa6f0d851)

***Consultar historial incidencias propias***

La siguiente funcionalidad del cliente es
ver las incidencias que ya ha realizado, esto nos sirve para ver si el
ejemplo del apartado de crear nueva incidencia ha sido registrado.
Hacemos click en la función y vemos que se ha registrado correctamente:

![image](https://github.com/user-attachments/assets/eb759ab1-db85-4f56-911c-0068db25d24e)


***Consultar el estado de una incidencia concreta***

Muy similar a la anterior función, solo que
tendremos que introducir la id de la incidencia para ver su estado.
Hacemos click en esta función y nos pedirá la ID de una incidencia que
el cliente haya realizado:

![image](https://github.com/user-attachments/assets/2289b277-e6e6-4089-b708-adb54f8d69bb)

Introducimos una de las IDs que aparecen en
la función anterior y le damos en "Consultar" y nos aparecerá esto:

![image](https://github.com/user-attachments/assets/d75d25ba-6682-42e9-9897-7d75240798e7)

Sale el estado de la incidencia arriba del todo en grande y los datos de
la incidencia en lista y de forma ordenada.

**Dashboard Técnico**

El técnico, también debe ser creado por el
administrador y se encargará de la gestión de las incidencias. Cuando
iniciamos sesión con los datos del técnico en el login, nos saldrá su
dashboard:

![image](https://github.com/user-attachments/assets/6d98a923-3e69-43f7-8c71-7143ded19d50)


**Ver incidencias asignadas**

La primera funcionalidad del técnico será la de ver sus incidencias
asignadas por el administrador. Cuando hacemos click en dicha opción,
aparecerán de forma ordenada:

![image](https://github.com/user-attachments/assets/75b87361-be6a-487f-98b0-b2a6c40f5b98)


**Marcar la incidencia como resuelta (añadir descripción resolución)**

Esta funcionalidad es de las más importantes, el técnico en está opción
pondrá la incidencia como resuelta y añadirá una descripción de cual erá
el problema o como se arregló.

Cuando hacemos click nos saldrá una lista con las incidencias
disponibles y un botón para elegir cual queremos marcar como resuelta:

![image](https://github.com/user-attachments/assets/d7d0963c-18e9-4718-9ee6-68ed07b8ba9f)

Damos click en la primera por ejemplo, nos saldrá una página con los
datos de la incidencia y un área de texto donde poner la información de
la resolución de la incidencia:

![image](https://github.com/user-attachments/assets/d68792cf-f692-434e-806e-e1bb0e64aa09)

Si introducimos la información en el área
de texto y pulsamos "Marcar como Resuelta", nos volverá a salir otra
página con este mensaje y un redirección de 3 segundos al inicio de
nuestro usuario:

![image](https://github.com/user-attachments/assets/9925bd07-dbf1-4f9e-a43c-ff5c00193ecb)


Si volvemos a la primera función de ver incidencias asignadas podemos
ver que sigue estando asignada pero como resuelta. De hecho si volvemos
a la opción de marcar incidencia como resuelta, no nos volverá a salir
la incidencia ya que está resuelta:

![image](https://github.com/user-attachments/assets/1e478b14-ec9a-4e36-b1e5-68d525a4f86b)

![image](https://github.com/user-attachments/assets/4b323593-1852-4c80-bb50-f7ef0f8e127d)


Si volvemos a la administración, vemos que no hay incidencias que
asignar ya que una ya está asignada y la otra ya está resuelta:

![image](https://github.com/user-attachments/assets/24a83fcd-258f-4645-aae9-2fa72ef5630c)

Si vamos ahora al cliente que realizó la
incidencia podemos ver que sale como resuelta y la descripción
resolución introducida anteriormente:

![image](https://github.com/user-attachments/assets/dcd3a4b3-7cce-4997-b91d-033836a1d86b)


**Cambiar estado**

Hace la misma función que el anterior, solo que se pueden cambiar entre
estados diferentes (pendiente / en curso y resuelta)

Hacemos click en la última función del
técnico y nos volverá a salir una lista con las incidencias asignadas y
un botón que pone "Cambiar estado".

![image](https://github.com/user-attachments/assets/911b8558-e0a9-4ac1-9e9c-e854c1fc73c1)

Si hacemos click en cambiar estado a la
incidencia concreta nos saldrá un formulario donde poner el estado nuevo
de dicha incidencia:

![image](https://github.com/user-attachments/assets/9d743202-7e60-43ed-af0c-4afb57dfda38)

Si ponemos el nuevo estado como resuelto en la selección del formulario,
automáticamente saldrá un área de texto donde poner la descripción de la
resolución de la incidencia:

![image](https://github.com/user-attachments/assets/2ceaa8d4-fabd-4147-abef-b6d4d4000417)

Vamos a probar a poner el estado "en
curso" y le damos a actualizar, nos volverá a salir la misma página con
este mensaje y un redirección de 3 segundos al inicio de nuestro
usuario:

![image](https://github.com/user-attachments/assets/17ff6161-c1c5-486d-af29-5ac07f1a555e)


Vemos que efectivamente se ha cambiado:

![image](https://github.com/user-attachments/assets/43174fb4-be68-4c37-8510-8fd9d47460e3)

  
Un técnico podra seguir cambiando el estado de la incidencia hasta que
se ponga como resuelta.
