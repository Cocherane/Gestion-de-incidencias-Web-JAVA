# Sistema de gestión de Incidencias (FERNTICKETS)

![](RackMultipart20230117-1-mhl7bs_html_efbde9b8f106e390.png)

_El sistema de gestión de tickets es un software para el manejo de incidencias de una institución o empresas, en el cual lo componen usuarios los cuales e pueden registrar incidencias que son llevadas a cabo por técnicos que son dados de alta y baja por el administrador y un administrador lo que conlleva este software es masificar, el uso coordinado para resolver las incidencias coordinadas por un administrador en el cual le asigna las incidencias del técnico, juzgándolas si son prioritarias y dándole capacidad al técnico a realizar la incidencias más eficaz, recordándole la importancia de resolverla y darle la capacidad de organizar las tareas a llevar a cabo al técnico. Con lo que facilita una interface de usuario agradable con alertas tanto al técnico como al administrador el cual llevara a organizar las tareas de las incidencias para ambos._

## Table de contenido

1. [**Comenzando**](#id1)
2. [**Construido con**](#id2)
3. [**Menú Principal**](#id3)

- 3.1[Menú de navegación de las opciones del menú principal](#id3.1)

1. [**Menú Usuario**](#id4)

- 4.1[Menú de navegación de las opciones de los usuarios normales](#id4.1)

1. [**Menú Técnico**](#id5)

- 5.1[Menú de navegación de las opciones de los Técnicos](#id5.1)

1. [**Menú Administración**](#id6)

- 6.1[Menú de navegación de las opciones de los Técnicos](#id6.1)

1. [**Autores**](#id7)
2. [**Licencia**](#id8)

# Comenzando 🚀

_Antes de comenzar tenemos registrarnos en la WebApp de Ferntickets se lleve a cabo sin dándole a registrarse en la página principal. La prueba de la WebApp se ha llevado a cabo con múltiples pruebas para facilitar a los usuarios una experiencia sin fallos._

# Construido con 🛠️

La herramientas que se utilizo para crear FERNTICKETS

- [JAVA](http://www.java.com/es/) - El principal lenguaje de codigo usado
- [COM.SUN.MAIL](https://mvnrepository.com/artifact/com.sun.mail/javax.mail) - La libreria javax.mail 1.5.0-b01
- [JAVAX.ACTIVATION](https://mvnrepository.com/artifact/javax.activation/activation) - La libreria javax.activation 1.1
- [APACHE POI](https://mvnrepository.com/artifact/org.apache.poi/poi/5.2.0) - La libreria para el manejo de Excel 5.2.0
- [APACHE POI OOXML](https://mvnrepository.com/artifact/org.apache.poi/poi/5.2.0) - La libreria es un adependecia de apache poi para el manejo de Excel 5.2.0
- [JAVAX.SERVLET.JSP](https://mvnrepository.com/artifact/javax.servlet.jsp/jsp-api) - La libreria es un adependecia para el manejo de los jsp
-

## Ultimas actualizaciones📋

En esta última versión del programa hemos añadido

- Realizar la versión de WebApp
- Mejoras de antiguos fallos.
- Patrón DAO para la conexión con base de datos.
- Cambios en el código para traer datos de la base de datos.

# Menú Principal 📖

_El menú principal consta del menú para registra usuario y para hacer login de todos los usuarios tanto como usuarios normales como usuarios técnicos y administrador, este programa la forma de hacer sesión, es por medio del Usuario que es único para cada usuario, técnico y administrador. La forma de usar el ecosistema de la WepApp es en la página principal llevar al usuario a registrarse o hacer sesión._

![](RackMultipart20230117-1-mhl7bs_html_7948158f8e510a27.png) Pantalla principal

![Shape1](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

## Menú de navegación de las opciones del menú principal

![Shape2](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

- **[1]**Menú Principal

El menú es para hacer login con un usuario registrado

  - EL menú de ingresar o hacer login comprende una secuencia de que ingreses tu email y la clave correspondiente que escogiste al regístrate recuerda que la clave si es case sensitive, te mostrare una imagen para guiarte por este menú. **observación** : Todos los usuarios, técnicos y administrador tienen un usuario único para hacer login.

![](RackMultipart20230117-1-mhl7bs_html_5c5947cb5354660d.png)

  1. Primero se te solicita el email que escogiste como usuario .
  2. Luego ingresas la clave correspondiente.
  3. Ya estarás en tu en tu perfil ya seas usuario, técnico o administrador.

  1. SOLO USUARIOS NORMALES Introducir tu token enviado a tu email. Nota ejemplo del email de token enviado abajo.

- Ejemplo del email del token

  - **Problemas al hacer login** _Si estás aquí es por q tienes algún inconveniente al ingresar a tu perfil, no te preocupes te daré algunas soluciones esperadas que solucionara tu ingreso a tu perfil._

    - El único inconveniente es que el ingreso de tu usuario o clave no fueran las correcta, te recomiendo ingresarla nuevamente recuerda que el ingreso del usuario no es case sensitive y la clave si es case sensitive, te podría haber salido una pantalla advirtiéndote problema como de la siguiente que te muestro.

  - ![](RackMultipart20230117-1-mhl7bs_html_c0fc574d2b7a808.png)

- **[2]**Menú Principal

Es para registrarse un usuario normal para reportar las incidencias

  - El menú es el cual te ayudare a regístrate, procedemos mediante pantallazos para facilitar el registro **observación** : Solo puedo registrarte como usuario normales.

  - ``` Registraras tu Email aquí debe ser un email valido

![](RackMultipart20230117-1-mhl7bs_html_bf3930611bf49c03.png)

-

- Si has llegado hasta aquí quieres decir que tu registro ha sido exitoso!!!

### Posibles problemas al registrarte

_Posibles problemas que te mencionamos son dos_

- Que el email introducido ya existe y por lo tanto tienes que escoger otro te saldría una mensaje de alerta en el formulario de registro.

![](RackMultipart20230117-1-mhl7bs_html_9ad40b01ef13bb55.png)

Lo que te recomendamos es ingresar otro email diferente

- Que algún dato introducido por el usuario no cumple con las políticas de registrar un usuario.

Las recomendaciones se
\* verifica que el email sea válido contenga @
\* verifica que los campos sean rellenados

# Menú Usuario 📖

_El menú de usuario normal son los usuario, los cuales pueden ingresar incidencia en el sistema, el perfil del usuario normal tiene una serie de opciones que pueden navegar te los mostrare en la siguiente imagen, las opciones que pueden acceder. Una de las principales enfoque es darle al usuario una fluides en el manejo y simplicidad de._

![](RackMultipart20230117-1-mhl7bs_html_240aa4018d498875.png)

**Pantalla del menú de usuarios normales**

![Shape3](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

**Notificaciones Para el Usuario**

![Shape4](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

- **La notificación del token para comprobar el email del usuario, solo se enviara una sola vez al registrase por primera vez**

![](RackMultipart20230117-1-mhl7bs_html_4a723c82239e7820.png)

- Ejemplo de Una notificación al usuario del Token
- Contiene El token el cual será pedido al ingresar por primera vez.

​

![Shape5](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

## Menú de navegación de las opciones de los usuarios normales

![Shape6](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

![](RackMultipart20230117-1-mhl7bs_html_98a82cb0bd485e97.png)

**[1]**

**[2]**

**[3]**

**[4]**

- **[1]**Menú Usuario **Reportar una incidencia** El menú comprende el ingreso de las incidencias que vas a reportar como usuario normal **Observación** : Recuerda ser lo mas preciso al reportar el detalle de la incidencia

  - Para ingresar una incidencia solo tienes que darle click al botón de **Reportar un incidencia**. ![](RackMultipart20230117-1-mhl7bs_html_a905a2700994ea74.png)

Ejemplo del menú de reportar una incidencia

  - Selecciona el nivel de prioridad para llevar a cabo las incidencia, y el resumen de la incidencia

  -

​ \*\* Una notificación será enviada con el resumen de tu incidencia \*\*

- Si tu incidencia ha sido registrada en nuestro sistema te enviara un mensaje de que tu incidencia ha sido reportara exitosamente al Telegram.

- **Posibles problemas al reportar una incidencia**

  - Que introduzcas una incidencia sin ningún sin la descripción del problema (vacía sin caracteres)

- **[2]**Menú Usuario **Incidencias abiertas** El menú te mostrara las incidencias reportadas por ti, que pueden estar asignada a un técnico o no, se mostrara en el panel de abajo, que contempla dos botones de Mas información.. contempla más detalle de la incidencia, botón de enviar un mensaje, si la incidencia esta asignada se enviara al técnico de lo contrario será reportada al administrador de la WepApp. **Observación** : Si no tienes ningún a incidencia reportada te saldrá un menú que te advierte, que no hay ninguna incidencia reportada aun.

![](RackMultipart20230117-1-mhl7bs_html_be1874b0e44d48b5.png)

- **[3]**Menú Usuario **Incidencias cerradas** El menú te mostrar las incidencias ya realizadas por el técnico asignado a tu incidencia, la cual puedes ver la observación del técnico, también contempla botones para saber más detalle de la incidencia ya resuelta y un mensaje al técnico que la llevo a cabo. **Observación** : Si no tienes ningún a incidencia reportada te saldrá un menú que te advierte, que no hay ninguna incidencia reportada aun.

![](RackMultipart20230117-1-mhl7bs_html_949034b5f505f271.png)

- **[4]**Menú Usuario **Mensajeria** El menú te mostrara los mensajes enviados y recibidos por tu usuario, como también redactar un mensaje a cualquier miembro de FernTickets

![](RackMultipart20230117-1-mhl7bs_html_6f7b2bd07e4d0043.png)

- **[5]**Menú Usuario **Perfil de Usuario** El menú ver toda la información del usuario como también cambiar tu clave le sesión, y hacer log out de FernTickets **Observación** : para cambiar la clave tienes que tener en cuenta que te pedirá una clave de 4 caracteres y que contenga una letra, esto lo hemos ingresado para facilitarle un nivel de seguridad para proteger sus datos.

- ![](RackMultipart20230117-1-mhl7bs_html_2d37a7f2512df9f8.png)

  - **Posible problema al cambiar la clave** _-Recuerda que tiene que tener una longitud de 4 caracteres y al menos una letra__Si no ingresas estas condiciones te mostrare un advertencia para que introduzcas de nuevo la clave seleccionada como en la imagen siguiente_

  - ![](RackMultipart20230117-1-mhl7bs_html_66baaf6979e88326.png)

- **[6]**Menú Usuario El menú te permitirá hacer logout de tu cuenta, una vez sucedido a seleccionar esta opción ya estas completamente fuera de tu cuenta.

# Menú Técnico 📖

_El perfil de técnico comprende el manejo de las incidencias asignadas a el técnico comprende una serie de opciones una vez loguiado para el manejo, optimización de las tareas llevadas a cabo, una de las ventajas del sistema de gestión de incidencias por parte del perfil de usuario es darle al técnico una herramienta para manejar y organizar las tareas de las incidencia sin enfocarse en aprender el manejo de la misma._ _ **Una de las novedades es en la pantalla principal del menú del perfil del técnico un mensaje si tienes incidencias asignadas** _

![](RackMultipart20230117-1-mhl7bs_html_7ea7eba07f279a96.png)

**Pantalla del menú de Técnico**

![Shape7](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

**Notificaciones Para el Técnico**

![Shape8](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

- **Todas las incidencias Asignadas a los técnicos serán notificadas previamente a su móvil**

- Ejemplo de Una notificación al técnico a través de Telegram
- Contiene El nombre del cliente
- Comentario del cliente
- Fecha de la creación
- La prioridad de la incidencia por el cliente

![Shape9](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

## Menú de navegación de las opciones de los Técnicos

![Shape10](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

![](RackMultipart20230117-1-mhl7bs_html_51f634b368e95479.png)

**[1]**

**[2]**

**[3]**

**[4]**

- **[1]**Menú Técnico Resolver una incidencia El menú muestra una pantalla para marcar las incidencias asignadas para cerrar y marcar la descripción de resolución del problema **Observación** : Si no tienes ninguna incidencia asignadas aun te saldrá un menú que te advierte, que no hay ninguna incidencia asignadas a ti.

![](RackMultipart20230117-1-mhl7bs_html_76e0d794e1ba6cd1.png)

Una vez dándole click a Cerrar incidencia se abrirá una pantalla para introducir los detalles de cerrado de incidencia

![](RackMultipart20230117-1-mhl7bs_html_94281d02009a4818.png)

Observación. Recuerda ser breve declarando la resolución de la incidencia

- **[2]**Menú Técnico **Incidencias asignadas** El menú comprende mostrar las incidencias ya asignadas al técnico, en el cual cada incidencia contiene botones para mostrar cerrar la incidencia, como enviar un mensaje al usuario que la reporto. **Observación** : Si no tienes ninguna incidencia asignadas aun te saldrá un menú que te advierte, que no hay ninguna incidencia asignadas a ti.

  - Unos de los primeros pasos es escoger la incidencia, que te ha sido asignada y quieres dejar constancia como realizada, te mostrare un ejemplo de como se puede presentar el menú, mirar la siguiente imagen.

  - ![](RackMultipart20230117-1-mhl7bs_html_52295ad04768976c.png) ![Shape11](RackMultipart20230117-1-mhl7bs_html_5dfa8671f3bf3b71.gif)Muestra la imagen las incidencias disponibles.

  - Una vez seleccionado la incidencia que ha sido asignada y que quieres marcar como hecha, es proceder a dejar un mensaje de la incidencia como fue resuelta. Te mostrare un ejemplo en la imagen siguiente.

  - ![](RackMultipart20230117-1-mhl7bs_html_27cdc4ffa55eb0cb.png)
- Ejemplo de cómo debes dejar un mensaje de la incidencia que has resuelto.
  -

- **[3]**Menú Técnico **Incidencias cerradas** Mostrara las incidencias que has resueltas, te mostrare un ejemplo en la imagen siguiente, puedes con los botones de cada incidencia ya cerrada ver más información de la incidencia como enviarle un mensaje al usuario que la reporto **Observación** : Si no tienes ningún a incidencia aun resuelta te advertirá que no hay ninguna incidencia resuelta en este menú

- ![](RackMultipart20230117-1-mhl7bs_html_c1c1312ccfd4ff5e.png)

Ejemplo de incidencias resueltas

- **[4]**Menú Técnico **Mensajeria** El menú te mostrara los mensajes enviados y recibidos por tu usuario, como también redactar un mensaje a cualquier miembro de FernTickets

![](RackMultipart20230117-1-mhl7bs_html_9f125a8db1d14ca8.png)

**[5]**

**[6]**

**[7]**

- **[5]**Menú Técnico **Ver perfil** Te muestra los detalles de perfil como usuario, nombre, apellido, y clave, te mostrare un ejemplo en la siguiente imagen.

- ![](RackMultipart20230117-1-mhl7bs_html_5bdfc5ff3eacaa19.png)

Ejemplo de perfil del técnico

- **[5]**Menú Técnico **Cambiar contraseña** El menú te permite cambiar la clave del técnico , pero tienes que tener en cuenta, mirara observación. **Observación** : para cambiar la clave tienes que tener en cuenta que te pedirá una clave de 4 caracteres Alfa-Numéricos

  - Una vez iniciado el proceso te mostrare imágenes para facilitar el cambio de tu clave

  - ![](RackMultipart20230117-1-mhl7bs_html_1c56854dd9597d4.png)
- Pantalla en el cual te solicita tu nueva clave
- _Si todo ha sido correcto al cambiar la clave te saldrá un mensaje de que la clave fue cambiada exitosamente!!!!_

  - **Posible problema al cambiar la clave** _-Recuerda que tiene que tener una longitud de 4 caracteres_ _Si no ingresas estas condiciones te mostrare un advertencia para que introduzcas de nuevo la clave seleccionada como en la imagen siguiente_
  -

- **[6]**Menú Técnico **LogOut** El menú te permitirá hacer logout de tu cuenta, una vez has seleccionar esta opción ya estas completamente fuera de tu cuenta, Para realizar cualquiera gestión con tu usuario tendrías que logiarte de nuevo.

# Menú Administración 📖

_El perfil de administrador se encarga de organizar las incidencias, dar de alta los técnicos que trabajaran a resolver las incidencias, tiene toda la información relevante para el manejo fluido de llevar a cabo a la brevedad posible cada una de las tareas, gracias al sistema sencillo de la interface de usuario amigable, así realizando a cabo las partes del administrativo, sin tener que estudiar un software de manejo de incidencias complejo y con información irrelevantes al manejo de las de las mismas._

**Una de las novedades en la pantalla principal del menú del administrativo, es una notificación de la llegadas de las incidencias por asignar liberando al administrativo a navegar en los menús.**

![](RackMultipart20230117-1-mhl7bs_html_694aba4effc0c958.png)

**Pantalla del menú de Administración**

![Shape12](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

**Notificaciones Para el Administrator**

![Shape13](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

- **Todas las incidencias creadas por el usuario se notifican al administrador para proceder a su asignación**

- Ejemplo de Una notificación al técnico a través de Telegram
- Contiene El nombre del cliente
- Comentario del cliente
- La prioridad de la incidencia por el cliente

![Shape14](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

## Menú de navegación de las opciones del Administrador

![Shape15](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

**[1]**

**[2]**

**[3]**

![](RackMultipart20230117-1-mhl7bs_html_7f68a1a8c2fabaaf.png)

- **[1]**Menú Administración **Asignar una incidencia** El menú comprende la asignación de las incidencias abiertas a un técnico, te demostrare a través de pasos por imagen como proceder y asignar una incidencia con éxito.

- **Observación** : Hay que tener en cuenta posibles mensajes de advertencias si algún mensaje de advertencia se presenta al asignar la incidencia dirigente al apartado debajo de este menú para ver las posibles soluciones de los problemas al asignar una incidencia.

  - Paso 1 una vez seleccionado el menú de asignar una incidencia al técnico, es escoger las incidencias que están sin asignar un técnico. **NOTA** Selecciona bien los numero de las incidencias, no van siempre en orden.

![](RackMultipart20230117-1-mhl7bs_html_de80bccf697edc45.png)

  - Imagen demostrativa de las incidencias por asignar.

  - Paso 2 Selecciona el técnico que quiere que lleve a cabo la incidencia, también aparece cuantas incidencias han sido asignadas a cada técnico **NOTA** Selecciona bien el número del técnico , no van siempre en orden.

  - ![](RackMultipart20230117-1-mhl7bs_html_3ba26a6ebf629406.png)
- Imagen demostrativa de los técnicos disponibles.

  - Paso 3 si la incidencia ha sido asignada pasara a verse con un cambio en la barra de estado

![](RackMultipart20230117-1-mhl7bs_html_c4c3ee68c7ade29.png) ![Shape16](RackMultipart20230117-1-mhl7bs_html_1e9779f37dbfee90.gif)

- Imagen demostrativa del mensaje

  - **Problemas al hacer asignar una incidencia** _Los problemas al asignar una incidencia puede ser mensajes de advertencias que pueden ser dos_
    - La advertencia que no hay incidencias creadas para asignar a un técnico

    - Mensaje de advertencia si no se ha creado una incidencia aun.

    - La advertencia de que no hay técnicos dados de alta

    - Mensaje de advertencia que no hay un técnico dado de alta

- **[2]**Menú Administración **Incidencias abiertas** El menú muestra todas las incidencias que han sido abiertas por los usuarios y aún no han sido reparadas por los técnicos.

Contempla botones de asignar a un técnico o a modificar un técnico, como también pedir más información de la incidencia como enviarle un mensaje a el usuario que la reporto

**Observación** : Si no tienes ninguna incidencia en el sistema aun, mostrara un mensaje que no hay incidencias aun creadas.

![](RackMultipart20230117-1-mhl7bs_html_2198f2a6ad0111d5.png)

Incidencias abiertas asignadas y por asignar un técnico

![](RackMultipart20230117-1-mhl7bs_html_a15cd20ae67360f0.png) ![Shape17](RackMultipart20230117-1-mhl7bs_html_1e9779f37dbfee90.gif)

Pantalla muestra los técnicos para asignar una incidencia o modificar el técnico, simplemente solo tienes que seleccionar el técnico con el botón señalado con la flecha en rojo

- **[3]**Menú Administración **Incidencias cerradas** El menú muestra todas las incidencias que han sido cerradas por los técnicos **Observación** : Si no tienes ninguna incidencia en el sistema aun, mostrara un mensaje que no hay incidencias aun creadas.

![](RackMultipart20230117-1-mhl7bs_html_673127c618e94fc.png)

Muestra de una incidencia ya cerrada por el técnico.

- **[4]**Menú Administración **Buscar por termino** El menú muestra todas las incidencias las cuales tengan en su descripción el termino introducido **Observación** : Si no tienes ninguna incidencia en el sistema aun con ese término, mostrara un mensaje que no hay incidencias aun creadas.

![](RackMultipart20230117-1-mhl7bs_html_c9f0105986df065d.png) ![Shape18](RackMultipart20230117-1-mhl7bs_html_1e9779f37dbfee90.gif)

Esta en el panel superior del dashboard.

![](RackMultipart20230117-1-mhl7bs_html_dcd32d02389bdc09.png)

**[4]**

**[5]**

**[6]**

Menús de consultas de información y gestión de técnicos

- **[4]**Menú Administración **Ver todo técnico** El menú muestra todos los Técnicos que has registrado, mediante este software; muestra el nombre, el código de id del técnico, el números de incidencias asignadas abiertas y el número de incidencias cerradas. Te mostrare un ejemplo en la imagen siguiente.

![](RackMultipart20230117-1-mhl7bs_html_259318ca6f69a4b3.png)

- Vemos como se mostraría todos los técnicos desde el perfil de administrador.

**Observación** : Si no has registrado ningún técnico aun, te mostraría un mensaje de advertencia de que no hay ningún técnico registrado aun.

- **[5]**Menú Administración **Registrar un técnico** Este menú comprende el dar de alta un técnico, te demostrare como añadirlo es fácil y sencillo como la aplicación por si mismo. A través de pasos.

  - El menú es el cual te ayudare a regístrate, procedemos mediante pantallazos para facilitar el registro **observación** : Solo puedo registrarte como usuario normales.

  - ![](RackMultipart20230117-1-mhl7bs_html_b1f3f77967c249ae.png)
  - Pantalla para registrar un tecnico
-
- Si has llegado hasta aquí quieres decir que tu registro ha sido exitoso!!!
-

  - **Posibles problemas al registrarte** _Posibles problemas que te mencionamos son dos_

    - Que el usuario introducido ya existe y por lo tanto tienes que escoger otro te saldría una pantalla así.

    - Lo que te recomendamos es ingresar otro nombre de usuario diferente
  -

- **[6]**Menú Administración **Borrar un técnico** El menú comprende la opción de borrar un técnico que este registrado, este menú escoges el técnico que quieres borrar. Te iré demostrando como proceder y las advertencias que hay que tener en cuenta al borrar un técnico.

- **NOTA** Antes de borrar un técnico hay que tener en consideración algunos puntos.

  - No se pueden borrar técnicos si han resuelto alguna incidencia
  - Si el técnico tiene incidencias asignadas sin resolver tendrás que pasárselas a otro técnico en el proceso de eliminación
  - No podrás borrar a un técnico si únicamente existe el como perfil de técnico

  - Paso 1 Que tienes que llevar a cabo una ves seleccionado el menú de borrar un técnico, es seleccionarlo el técnico a borrar, habrá pantalla de advertencia si el técnico tiene unas incidencias por realizar, como advertencia. Te mostrare un ejemplo en la siguiente imagen.

​ ![](RackMultipart20230117-1-mhl7bs_html_6e0f05f7dd52a642.png)

Pantalla demostrativa del menú de selección del técnico a borrar

- Paso 2 Una vez seleccionado el técnico se borrara del sistema de registro de incidencias, inmediatamente, no hay manera de recuperación una vez procedas a eliminarlo. **Observación** : Si no has registrado ningún técnico aun, te mostraría un mensaje de advertencia de que no hay ningún técnico registrado aun.

![](RackMultipart20230117-1-mhl7bs_html_7e264e31865f8fa0.png)

**[7]**

**[7]**

Menús de consulta de datos de los usuarios

- **[7]**Menú Administración **Ver todo usuario** El menú muestra todos los usuarios registrados, mediante este software; muestra el nombre, el código de id del usuario, y el números de incidencias abiertas por este usuario. Te mostrare un ejemplo en la imagen siguiente

- ![](RackMultipart20230117-1-mhl7bs_html_22132323756b8dee.png)

![](RackMultipart20230117-1-mhl7bs_html_4fa8fa38e50c0b5d.png)

**[8]**

**[9]**

**[10]**

**[11]**

Menús de consulta de otras opciones

- **[8]**Menú Administración **mensajería** El menú te mostrara los mensajes enviados y recibidos por tu usuario, como también redactar un mensaje a cualquier miembro de FernTickets

- **[9]**Menú Administración El menú te permitirá ver las estadísticas medias de la aplicación.

- ![](RackMultipart20230117-1-mhl7bs_html_4f0f900d3f9461f8.png)

- **[10]**Menú Administración El menú te permitirá enviar por email un resumen de una incidencia a un técnico asignado.

![](RackMultipart20230117-1-mhl7bs_html_89c4b8022b32ca83.png)

-
- Nos dará a elegir entre los técnicos que tienen alguna incidencia asignada.
-
- ![](RackMultipart20230117-1-mhl7bs_html_9c51b52c79027471.png)
- Dará un mensaje de confirmación de que el email se ha enviado correctamente y le llegara el email mostrado al técnico seleccionado.
-
- **[11]**Menú Administración El menú te permitirá ver las últimas sesiones que han tenido los usuarios del sistema.
- ![](RackMultipart20230117-1-mhl7bs_html_581599a36dc2f6de.png)

- **[13]**Menú Administración El menú te permitirá hacer logout de tu cuenta, una vez has seleccionar esta opción ya estas completamente fuera de tu cuenta, Para realizar cualquiera gestión con tu usuario tendrías que logiarte de nuevo.

![Shape19](RackMultipart20230117-1-mhl7bs_html_6ebf213c0ab41707.gif)

# Autores ✒️

_Menciona a todos aquellos que ayudaron a levantar el proyecto desde sus inicios_

- **Ariel Cocherane** - [@cocherane - GitHub](http://github.com/Cocherane "@cocherane - GitHub")

# Licencia 📄

[_ **GNU GENERAL PUBLIC LICENSE ©** _](http://www.gnu.org/licenses/gpl-3.0.html)

⌨️ con 😊
