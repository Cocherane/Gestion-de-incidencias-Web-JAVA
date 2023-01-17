import DataPersistence.DataRecovery;
import ExcelData.WriteExcel;
import FrontEnd.Menus;
import models.*;

import java.awt.*;
import java.io.IOException;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *                                        <h1>ADVERTENCIA</h1>
 * Para que corra las funciones de comunicacion necesitas poner tu api de lelegran en <Comunications#BOT_TELEGRAM>
 * Y en para recibir las notificaciones de Telegran necesitas poner tu id de chat con el bot esta en <GestionAPP#TELEGRAM_ID_SEND>
 *
 * @author Gil Carlos
 * @author Ariel Cocherane
 * @version v1.16
 */
public class main {
    static Scanner s = new Scanner(System.in);
    static GestionAPP app = new GestionAPP( );
    public static void main(String[] args) {

        // A Inicializacion de variables
        boolean appRunning = true;  // variable que hace que la aplicacion siempre esté corriendo
        // A Variables [MENU PRINCIPAL]
        int menuOpcionPrincipal = 0;
        String userLoguinEmail = null;
        boolean isMenuPrincipal = true; // para estar en el menu principal hasta que se logueo un Usuario
        Object objectUser = null; // el objeto del usuario loguiado
        //// A Variables [MENU USUARIO]
        int opMenuUsuario = 0;
        //// A Variables [MENU TECNICO]
        int opMenuTecnico = 0;
        //// A Variables [MENU ADMIN]
        int opMenuAdmin = 0;
        //// A Variables [MENU INVITADO]
        int opMenuInvitado = 0;
        //// variable de acceso a el usuario invitado
        boolean guestUserMenu = false;

        // B Inicializacion de la vista controlador (MVC)


        // C inicializacion del mock donde cargaremos dos Usuarios y dos tecnicos


        // D mock para anadir unas incidecnias al usuario pablogonzales@gmail.com
//        app.mockIncidencia();



        while (appRunning){
            /* *********************************** MENU PRINCIPAL **********************************/
            /* El menu principal comprende las áreas de logueo de usuarios y registro del mismo, como
            * menu de comprobación de la misma al insertar a los usuarios
            * */
            /* *************************************************************************************/
            // 1 La parte 0 se encarga de pedir al usuario las credenciales apara logiarse o registrarse
            // 1 Muestra al usuario la pantalla principal
            do {
                // mostramos un menu segun si se activa la parte de usuario invitado
                System.out.println(Menus.menuPrincipal_1( app.isGuestUserActiveConfig() ));
                try {
                    menuOpcionPrincipal = Integer.parseInt(s.nextLine());
                }catch ( NumberFormatException | InputMismatchException e){
                    // para mostra un error de opciones en el switch
                    menuOpcionPrincipal = -1;
                }

                // comprueba si el menu de user invitado esta habilitada para poder manejar el rango del menuOpcionPrincipal
                if ( !app.isGuestUserActiveConfig() && menuOpcionPrincipal == 3 ){
                    // set menuOpcionPrincipal a -1 para mostrar un mensaje que esta opcion no existe
                    menuOpcionPrincipal = -1;
                }

                // 1 Hacer el Swipch Para manejar las dos opciones loguiarse o registrase
                switch (menuOpcionPrincipal){

                    // Case 1 Para Loguiar a un USER
                    case 1 -> {
                        // bandera para tomar una opcion de volver a introducir el loguin o salir al menu principal
                        boolean reIntroducirCredencialIsFailLoguin;

                        do {
                            reIntroducirCredencialIsFailLoguin = false;
                            // pide los datos del USER a loguiarse.
                            System.out.println(Menus.menuPrincipal_1_1()); // muestra un menu donde le solicita al User el User.
                            userLoguinEmail = s.nextLine();
                            System.out.println(Menus.menuPrincipal_1_2()); // muestra un menu donde le solicita al User el password.
                            String userLoguinClave = s.nextLine();

                            // 1 -1 se comprueba si hay un User que coincide con el email y la clave sino hay coincidencia se envia un Objet NULL.
                            objectUser = app.login( userLoguinEmail, userLoguinClave );
                            if ( objectUser == null){
                                // 1 - 1 - 1 muestra una opcion de re introducir los datos si el logueo fallo o salir al menu principal
                                System.out.println( Menus.menuLoggingError() ); // mensaje de opcion una vez llegado a un loguin fallido
                                // Si se desea re introducir los datos denuevo
                                if ( s.nextLine().equals("1")  ){
                                    reIntroducirCredencialIsFailLoguin = true;
                                    // salir al menu principal
                                }
                            }

                            // se comprueba si ya el Usuario ha validado las credenciales y sino la validamos el token
                            // mantiene al usuario hasta que compruebe la credenciales o salga al menu principa
                            boolean keepOnCheckToken = true;
                            int tokenOsalidaMenuPrincipal = 0;
                            while ( objectUser instanceof  Usuario && !app.isValidadoToken(objectUser) && keepOnCheckToken ){
                                //se muestra un menu donde se solicita el TOKEN
                                // TODO: 19/03/2022 BORRAR PRUEBA DEL MAIN: te muestra el token
                                System.out.println(((Usuario)objectUser).getToken());
                                // TODO: 19/03/2022 BORRAR PRUEBA DEL MAIN: te muestra el token
                                System.out.println(Menus.menuSolicitarToken());
                                try {
                                    // pedimos al usuario su token numero de longitud de 6 digitos o la salida del introducir token con el digito 1
                                    // usamos esta varible como introduce el token O marca el menu de salida
                                    tokenOsalidaMenuPrincipal = Integer.parseInt(s.nextLine());
                                }catch ( NumberFormatException | InputMismatchException e){
                                    // ponemos a set a opcionMenuToken para atrapar el error y usar la comprobación y mandar un error que el loguin no hay coincidencia
                                    tokenOsalidaMenuPrincipal = -1;
                                }finally {
                                    // Este if te lleva al menu principal
                                    if ( tokenOsalidaMenuPrincipal == 1 ) {
                                        // LLevamos al usuario al menu principal
                                        // para salir del menu ponemos a keepOnCheckToken A false para salir del menu de comprobación del token
                                        keepOnCheckToken = false;
                                        // TODO: 12/04/2022 prueba de sacar el mensaje de bienvenido 
                                    }
                                    // este elif comprueba que sea un token válido y que no ha introducido el usuario un caracter.
                                    else if ( tokenOsalidaMenuPrincipal != -1 && app.validandoTokentoObject(objectUser, tokenOsalidaMenuPrincipal) ){
                                        // mensaje de que el la comprobación del token ha sido satisfactory
                                        System.out.println(Menus.mensajeTokenAccepted());
                                        // TODO: 18/03/2022 salto de espera
                                    }
                                    // Mensaje de que la comprobación del token, no ha sido satisfactoria y que lo vuelvas a introducir.
                                    else {
                                        System.out.println(Menus.mensajeTokenNoAccepted());
                                        // TODO: 18/03/2022 salto de espera
                                    }
                                }
                            }

                        }while (reIntroducirCredencialIsFailLoguin);

                        // mostrar el mensaje de la ultima session que sse ha loguiado el ususario
                        if( objectUser != null  ){
                            System.out.println(
                                    // llamamos al mensaje de bienvenida para user nuevos o para usuarios ya registrado
                                    // el último dia que hicieron sesion.
                                    Menus.messageWelcomeLastSession(
                                            app.readLastSession( 12 )
                                    ));
                            pulsaContinuar();
                        }

                    }

                    // Case 2 se encarga de registrar a los usuarios
                    case 2 -> {
                        // pide al usuario un email válido y que no este ya en el registro de los USER
                        System.out.println( Menus.menuRecordUserEmail() );
                        String emailRegister = s.nextLine();

                        // pide el nombre del usuario
                        System.out.println( Menus.menuRecordUsuarioName() );
                        String nombreRegister = s.nextLine();

                        // pide el apellido
                        System.out.println( Menus.menuRecordUserSurname() );
                        String apelsRegister = s.nextLine();

                        // pide La clave
                        System.out.println( Menus.menuRecordUserPass() );
                        String passRegister = s.nextLine();

                        // se procede a registrar el usuario si es fallido tendras que volver a proceder a registrarte
                        // y manejar un erro al mandar el email con el token al usuario
                        try {
                            if ( app.insertaUsuario( nombreRegister, apelsRegister, passRegister, emailRegister ) )
                                System.out.println( Menus.menuCreateUserCorrect( nombreRegister, 1 ) );

                                // manejo de mensaje al usario de error de registrar al usuario
                            else{
                                // maneja los errores si ya existe el email envia un mensaje que ya existe, o un error que los campos estan vacios
                                if( app.isValidoEmailRegistro( emailRegister ) )System.out.println( Menus.menuRecordErrorUsuario() );
                                else System.out.println( Menus.menuRecordErrorUsuarioEmail());
                            }
                        }catch ( RuntimeException e){
                            System.out.println("Advertencia!!! El email con el token no fue enviado!!!");
                        }


                        // TODO: 18/03/2022 salto de espera
                        System.out.println("Pulse para continuar ....");
                        s.nextLine();
                    }
                    // menu de invitado, le da acceso a el usuario a ingresar
                    case 3 ->{
                        // comprobamos que el usuario no active este menu si el parametro esta false en la configuracion
                        if ( app.isGuestUserActiveConfig() ) guestUserMenu = true;
                    }


                    // default se encarga de controlar un mensaje de error al introducir una opcion fuera del menu o un caracter
                    default -> {
                        System.out.println( Menus.errorMenuOpcionesNoEstan() ); // envia un error que el menu no se encuentra en las opciones
                        // TODO: 18/03/2022 salto de espera
                        System.out.println("Pulse para continuar ....");
                        s.nextLine();

                    }
                }

                /* **************************** ACCIONES DE USER LOGUEADOS ****************************/
                /* Las acciones correspondientes a cada USER se llevará a cabo dentro de su respectivo WHILE
                * La forma de cerrar es poner al usuario objetoUser a null
                * */
                /* ************************************************************************************/
                /* guardamos el registro de la aplicacion por parte de los usuarios */
                if ( objectUser != null ) app.recordLastSession( 12 );


                /* *********************************** MENU USUARIO **********************************/
                /* Explicacion
                * */
                /* ************************************************************************************/
                while (objectUser instanceof Usuario && app.isValidadoToken(objectUser)){
                    // TODO: 19/03/2022 Aqui va todo de los menus de USUARIO
                    // trasformar el <objectUser> a la idUsuario
                    int idUsuarioTemp = app.objectGetId(objectUser);
                    do {
                        try {
                            // inject las variables de cabeza del menu de Usuario
                            System.out.println( Menus.menuUsuarioPrincipal(
                                    ((Usuario) objectUser),
                                    app.incidenciasNoAsignadasbyUsuario( idUsuarioTemp ),
                                    app.incidenciasAsignadasbyUsuario( idUsuarioTemp ))
                            );
                            opMenuUsuario = Integer.parseInt(s.nextLine());
                        }catch (NumberFormatException e){
                            // set opMenuUsuario para que el error lo maneje el Switch
                            opMenuUsuario = -1;
                        }

                        switch (opMenuUsuario){
                            // se encarga de registra una incidencia
                                    case 1 -> {
                                        // proceso de set la description
                                        System.out.println(Menus.menuUsuarioSetIncidenciaDescription());
                                        String descriptionIncidecniaInit = s.nextLine();

                                        // proceso de set the priority con validation de que sea un número
                                        int priorityIncidenciaInit = 0;
                                        boolean checkOnInput;
                                        do {
                                        	checkOnInput = false;
                                        	try {
                                                System.out.println(Menus.menuUsuarioSetIncidenciaPrioridad());
                                        		priorityIncidenciaInit = Integer.parseInt(s.nextLine());
                                        	}catch ( NumberFormatException e){
                                        		System.out.println("Advertencia!!! Ingresa un numero no una letra!!!");
                                        		s.nextLine();
                                        		checkOnInput = true;
                                        	}
                                        }while (checkOnInput);

                                        // anadir un nuevo incidencia por parte del usuario y comprobar si se anade correctamente
                                        if( app.insertaIncidencia( userLoguinEmail, descriptionIncidecniaInit, priorityIncidenciaInit ) )
                                            System.out.println( Menus.menuUserSetIncindentMessager() );
                                        else System.out.println( Menus.menuUserSetIncidentError() );

                                        // TODO: 18/03/2022 salto de espera
                                        System.out.println("Pulse para continuar ....");
                                        s.nextLine();

                                    }

                            // se encarga de presentar mis incidencias abiertas y asignadas
                                    case 2 -> {
                                        printIncidenciaFromIncidenciaDataClass( app.buscaIncidenciasAbiertasbyUsuario( idUsuarioTemp ) );
                                    }
                            // se encarga de presentar las incidecnias cerradas por este usuario
                                    case 3 -> {
                                        printIncidenciaFromIncidenciaDataClass( app.buscarIncidenciasCerradasbyUsuario( idUsuarioTemp ) );
                                    }
                            // se encarga de mostrar el perfil del usuario
                                    case 4 -> {
                                        System.out.println( Menus.menuUsuarioPerfil( objectUser, app.incidenciasCerradasbyUsuario( idUsuarioTemp ) ) );
                                        // TODO: 18/03/2022 salto de espera
                                        System.out.println("Pulse para continuar ....");
                                        s.nextLine();
                                    }
                            // se encarga de modificar la clave de acceso del usuario
                                    case 5 -> {
                                        // pedir la nueva la nueva clave
                                        System.out.println( Menus.menuUserChangePassword() );
                                        String newUsuarioPassword = s.nextLine();
                                        // verificamos si el usuario puede cambiar la clave

                                        try {
                                            if ( app.setNewPasswordObject(objectUser, newUsuarioPassword) ){
                                                // mensaje que el cambio ha sido exitoso y que necessitate comprobar su token justo después del proceso
                                                System.out.println( Menus.menuUserChangePassMessager() );
                                                // para forzar al usuario que cambio su clave a salir y loguirse con su nuevo token
                                                idUsuarioTemp = 0;
                                                objectUser = null;
                                                opMenuUsuario = 6;
                                            }
                                            else { System.out.println( Menus.menuUserChangePassMessagerError() ); }
                                        }catch ( RuntimeException e){
                                            System.out.println("Advertencia!!! El email con tu token no fue enviado!!!");
                                        }

                                        // para parar y mostrar el mensaje
                                        // TODO: 18/03/2022 salto de espera
                                        System.out.println("Pulse para continuar ....");
                                        s.nextLine();

                                    }
                            // salir del Login del usuario
                                    case 6 -> {
                                        // ponemos el registro de el logOut
                                        app.logOutSessionLog(objectUser);
                                        // reset el Usuario temporal
                                        objectUser = null;
                                        userLoguinEmail = null;

                                    }
                                    // maneja los errores al
                                    default -> {
                                        System.out.println( Menus.errorMenuOpcionesNoEstan() ); // envia un error que el menu no se encuentra en las opciones
                                        // TODO: 18/03/2022 salto de espera
                                        System.out.println("Pulse para continuar ....");
                                        s.nextLine();
                                    }
                                }

                    }while (opMenuUsuario != 6 );



                    // IMPORTANTE!!! Poner siempre el objetoUser y userLoguinEmail a NULL cuando salga de cada logueo
                    // que se debe poner cuando cierre session en el menu[6] USUARIOS

                }

                /* *********************************** MENU TECNICO **********************************/
                /* Explicacion
                 * */
                /* ************************************************************************************/
                while ( objectUser instanceof Tecnico){
                    // TODO: 19/03/2022 Aqui va todo de los menus de TECNICO
                    // IMPORTANTE!!! Poner siempre el objetoUser a NULL cuando salga de cada logueo
                    // que se debe poner cuando cierre session en el menu[6] TECNICOS
                     /*[REFACTOR by Ariel ] Carlos te he modificado esto para no llamar a los metodos de las clases
                     *  desde el main,
                     *      1. te anadi el metodo del controlador que te envia la id del tecnico y modifique los metodos
                     *      2. te he modificado los case que le faltaba las llaves
                     *      3. modifique el apartado 5 no se ejecutaba el mensaje de error
                     *
                     * */

                    int idTecnicoTemp = app.objectGetId(objectUser);
                    do {
                        try {
                            System.out.println( Menus.menuTecnicoPrincipal(
                                    ((Tecnico) objectUser).getNombre()
                                    , app.buscaIncidenciasAbiertasbyTecnico(idTecnicoTemp).size()
                                    , app.buscarIncidenciaCerradabyTecnico(idTecnicoTemp).size()
                                    , prioridadMediabyTecnico(idTecnicoTemp)
                            ));
                            opMenuTecnico = Integer.parseInt(s.nextLine());
                        }catch (NumberFormatException e){
                            System.out.println("Advertencia!!! Ingresa un numero no una letra!!!");
                        }
                        switch (opMenuTecnico){

                            case 1 ->
                                muestraIncidenciasDataClass(app.buscaIncidenciasAbiertasbyTecnico( idTecnicoTemp ));

                    // muestras las incidencias para marcarlas como cerrada
                            case 2 -> {
                                // para almacenar la la id para cerrar la incidenia
                                int idIncideniaCerrar = 0;
                                // controla si las si tiene asignada el tecnico una incidencia
                                if ( app.buscaIncidenciasAbiertasbyTecnico( idTecnicoTemp ).isEmpty() ){
                                    System.out.println( Menus.menuUserSetIncindentNoAun() );
                                }else {
                                    // te lleva a una seleccion de escoger la incidenia y devuelve la id de la misma
                                    idIncideniaCerrar = showSeleccionIncidenciaRetornaPorId( app.buscaIncidenciasAbiertasbyTecnico( idTecnicoTemp ) );

                                    // pedimos la descripcion para cerrar la incidenia y controla la salida de
                                    System.out.println(Menus.menuCerrarIncidenciaGetMensaje());
                                    String descripcionCerrarIncidencia = s.nextLine();
                                    if ( app.cierraIncidencia( idTecnicoTemp, idIncideniaCerrar, descripcionCerrarIncidencia ) )
                                        System.out.println( Menus.menuCerrarIncidenciaAceptada() );
                                    else System.out.println( Menus.menuCerrarIncidenciaDeclinada() );
                                }
                                pulsaContinuar();
                            }

                            case 3 -> {
                                muestraIncidenciasDataClass(app.buscarIncidenciaCerradabyTecnico( idTecnicoTemp ));
                            }

                            case 4 -> {
                                System.out.println(Menus.menuTecnicoPerfil(objectUser));
                                pulsaContinuar();
                            }

                            case 5 -> {

                                System.out.print( Menus.menuUserChangePassword() );
                                if ( app.setNewPasswordObject(objectUser, s.nextLine()) ){
                                    // mensaje que el cambio ha sido exitoso
                                    System.out.println( Menus.menuTecnicoChangePassMessager() );
                                    // para forzar al usuario que cambio su clave a salir
                                    idTecnicoTemp = 0;
                                    objectUser = null;
                                    opMenuTecnico = 6;
                                }
                                else  System.out.println( Menus.menuUserChangePassMessagerError() );
                                pulsaContinuar();

                            }

                            case 6 -> {
                                // ponemos el registro de el logOut
                                app.logOutSessionLog(objectUser);
                                // set tecnico a null
                                idTecnicoTemp = 0;
                                objectUser = null;
                                userLoguinEmail = null;
                            }

                            default -> {
                                System.out.println( Menus.errorMenuOpcionesNoEstan() );
                                pulsaContinuar();
                            }

                        }

                    }while (opMenuTecnico != 6);

                }


                /* ******************************** MENU ADMINISTRADOR *******************************/
                /* Explicacion
                 * */
                /* ************************************************************************************/
                while ( objectUser instanceof Admin){
                    // TODO: 19/03/2022 Aqui va todo de los menus de ADMINISTRADOR
                    // IMPORTANTE!!! Poner siempre el objetoUser y userLoguinEmail a NULL cuando salga de cada logueo
                    // que se debe poner cuando cierre session en el menu[10] ADMINISTRADOR
                    do {
                        System.out.println(Menus.menuAdministradorPrincipal(((Admin) objectUser).getNombre(), app.incidenciasAbiertas(), (app.incidenciasAbiertas() - app.incidenciasAbiertasAsignadas())));
                        try {
                            opMenuAdmin = Integer.parseInt(s.nextLine());
                        } catch (NumberFormatException e) {
                            opMenuAdmin = -1;
                        }

                        switch (opMenuAdmin){
                            case 1 ->{
                                muestraIncidenciasDataClass( app.buscarTodasIncidenciasAbiertas() );
                            }
                            // muestra las incidencias cerradas globales
                            case 2 -> {
                                printIncidenciaFromIncidenciaDataClass( app.buscaIncidenciasCerradas());
                            }
                            // muestra las incidencias dado un termino
                            case 3 ->{
                                System.out.println(Menus.menuGetTermFindIncidencia());
                                String termino = s.nextLine();
                                printIncidenciaFromIncidenciaDataClass( app.buscarIncidenciasbyTerm( termino ) );

                            }
                            // muestra la informacion de los tecnicos
                            case 4 ->{
                                // comprueba que los tecnico esten
                                if ( app.getTecnicos().isEmpty() ) {
                                    System.out.println(Menus.menuUserNoTecnicoAun());
                                    pulsaContinuar();
                                }
                                else{
                                    // muestra los tecnicos
                                    // header de que se mostraran los tecnicos
                                    System.out.println(Menus.menuAdministrador3MostarTecnicos());
                                    // se presenta los tecnicos paso por paso
                                    for (int i = 0; i < app.getTecnicos().size(); i++) {
                                        System.out.println(Menus.showTecnico(app.getTecnicos().get(i)));
                                        if ((app.getTecnicos().size() - 1) == i)
                                            System.out.println("La lista de los tecnicos ha terminado ");
                                        pulsaContinuar();
                                    }
                                }
                            }
                            // asigna una incidencia a un tecnico
                            case 5->{

                                // compruebo que esten creadas las incidencias y hay tecnicos ya registrados
                                if ( app.buscarIncidenciasSinAsignar().isEmpty()  ){
                                    System.out.println(Menus.menuUserSetIncindentNoAun());
                                    pulsaContinuar();
                                }else if (app.getTecnicos().isEmpty() ){ // muestra un mensaje de que no hay tecnicos aun
                                    System.out.println(Menus.menuUserNoTecnicoAun());
                                    pulsaContinuar();
                                }else { // si hay usuarios registrado y una incidnncia por asignar procedo a la seleccion
                                    // este metodo pasa por un menu de selection en el cual retorna la idIncidencia
                                    int idIncidencia = showSeleccionIncidenciaRetornaPorId( app.buscarIncidenciasSinAsignar() );

                                    // este metodo implementa la seleccion del tecnico y retorna la id del tecnico
                                    int idTecnico = showSeleccionTecnicoRetornaPorId( app.getTecnicos(), 1 );

                                    // procede a asignar la incidecnias si se anade envia un mensaje de exito sino un mensaje de error
                                    if (app.asignaIncidencia( idTecnico, idIncidencia )){
                                        System.out.println(" Se ha añadido la incidencia aal tecnico con exito....");

                                        // procedemos a registar la incidencia asignada al log

                                    }
                                    else System.out.println(" Ha ocurrido un error al anadir tu incidencia intenta de nuevo ");

                                    pulsaContinuar();
                                }
                            }

                            case 6 ->{
                                creaTecnico();
                                pulsaContinuar();
                            }
                            // muestra los tecnicos que quieres borrar
                            case 7 ->{
                                // controlamos si hay un tecnico registrado , mandamos un mensaje de advertencia
                                if ( app.getTecnicos().isEmpty() ) {
                                    System.out.println( Menus.menuNoHayTecnicosRegistrados()  );
                                }
                                // si hay un tecnico controlamos la operacion de selecionar el tecnico
                                else {
                                    int idTecnicoBorra = showSeleccionTecnicoRetornaPorId( app.getTecnicos(), 2 );

                                    // procedemos a borrar el tecnico si no tiene una incidencia asignada
                                    if (  app.borraTecnico( idTecnicoBorra ) ) System.out.println(" Se ha borrado el tecnico con exito.....");
                                    else {
                                        // comprueba que hay tecnicos a quien asignarles las incidecnias
                                        if ( app.getTecnicos().size() <= 1 ){
                                            System.out.println(app.getTecnicos().size());
                                            System.out.println(Menus.menuNoHayTecnicosParaAsignarOtraIncidecniasTecnico());
                                        }
                                        // comprobar si tiene incidencias cerradas
                                        else if ( app.buscarIncidenciaCerradabyTecnico( idTecnicoBorra ).isEmpty()  ){
                                            // TODO: 30/03/2022 procedemos a asignar las incidencias  al nuevo tecnico

                                            // se comprueba has ta que el tecnico a borrar no tenga ninguna incidecnias abierta
                                            do {
                                                // muestra las incidecnias abiertas del tecnico a borrar para hacer asignadas
                                                int idIncidenciNewAsignacion = showSeleccionIncidenciaRetornaPorId( app.buscaIncidenciasAbiertasbyTecnico( idTecnicoBorra ));
                                                // muestra los tecnicos que se pueden asignar si hay almenos uno
                                                int idTecnicoAsignar = showSeleccionTecnicoRetornaPorId( muestraTecnicoAsignarIncidenciaOtroTecnico( idTecnicoBorra ), 3 );

                                                // pasa la incidecnia a el nuevo tecnico
                                                if (app.pasaIncidenciasEntreTecnicos( idTecnicoBorra, idTecnicoAsignar, idIncidenciNewAsignacion )) {
                                                    System.out.println( "La incidecnia ha sido asignada a el nuevo tecnico...");
                                                }else
                                                    System.out.println("Ha ocurrido un problema no se ha podico asignar la nueva incidencia...");

                                            }while ( !app.buscaIncidenciasAbiertasbyTecnico( idTecnicoBorra ).isEmpty() );

                                            // una ves ya las incidecnias ha sido asiganadas al nuevo tecnico se borra el tecnico
                                            app.borraTecnico( idTecnicoBorra );
                                            System.out.println(" Se ha borrado el tecnico con exito.....");


                                        } else
                                            System.out.println(" No se puede borrar el tecnico por que tiene incidencias cerradas .....");
                                    } //System.out.println("No se puede borrar el tecnico por que tiene una incidencia abierta");
                                }

                                pulsaContinuar();
                            }

                            // muestra los usuarios y sus perfil
                            case 8 ->{
                                for ( var usuario : app.getUsuarios() ) {
                                    System.out.println(Menus.showUsuario(usuario));
                                    pulsaContinuar();
                                }
                                System.out.println("La lista de los Usuarios ha terminado ");
                                pulsaContinuar();
                            }

                            // muestra las estadistica de la aplicacion
                            case 9 ->{
                                System.out.println(Menus.menuEstadisticaApp(app.incidenciasCerradas(), app.incidenciasAbiertas(), app.prioridadMediaApp()));
                                pulsaContinuar();
                            }
                            // envia un email con un ecxel al tecnico, con un resumen de sus incidencias asignadas abiertas
                            case 10 -> {

                                // compruebo que esten creadas las incidencias y hay tecnicos ya registrados
                                if ( app.incidenciasAbiertasAsignadas() == 0  ){
                                    System.out.println(Menus.menuAdminNohayNingunaIncidenciaAsignada());
                                    pulsaContinuar();
                                }else if (app.getTecnicos().isEmpty() ){ // muestra un mensaje de que no hay tecnicos aun
                                    System.out.println(Menus.menuUserNoTecnicoAun());
                                    pulsaContinuar();
                                }else { // si hay un tecnico registrado y una incidencia asignada

                                    // este metodo implementa la seleccion del tecnico y retorna la id del tecnico
                                    int idTecnico = showSeleccionTecnicoRetornaPorId(app.getTecnicoIncidenciaAsignada( ), 1);
                                    
                                    // se envia el email con el excel
                                    //app.enviarResumenIncidenciaTecnico( idTecnico );
                                    System.out.println("Se ha enviado el email con el resumen al tecnico ....." );
                                    pulsaContinuar();
                                }
                                
                                
                            }
                            // muestra las configuraciones de toda la app
                            case 11 -> {
                                try {
                                    System.out.println( showAllConfigApp( app.getAllConfigApp() ) );
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                pulsaContinuar();
                            }
                            // muestra las ultimas sesiones de los usuarios
                            case 12 ->{
                                try {
                                    System.out.println( showAllLastSessions(app.getLastSession()) );
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                pulsaContinuar();
                            }

                            // salir del menu de administrador
                            case 13 ->{
                                // ponemos el registro de el logOut
                                app.logOutSessionLog(objectUser);
                                // set admin null
                                objectUser = null;
                                userLoguinEmail = null;
                            }

                            default ->{
                                System.out.println(Menus.errorMenuOpcionesNoEstan());
                                pulsaContinuar();
                            }

                        }

                    }while (opMenuAdmin != 13);


                }

                /* ******************************** MENU INVITADO *******************************/
                /* Explicacion
                 * */
                /* ************************************************************************************/
                while ( guestUserMenu ){
                    do {
                        // muestra el menu del usuario invitado
                        System.out.println( Menus.menuInvitadoPrincipal( app.incidenciasAbiertas() ) );
                        // escoge el menu del usuario
                        try {
                             opMenuInvitado = Integer.parseInt(s.nextLine());
                        }catch ( NumberFormatException e){
                            System.out.println("Advertencia!!! Ingresa un numero no una letra!!!");
                            pulsaContinuar();
                            opMenuInvitado = -1;
                        }
                        // opciones del menu
                        switch (opMenuInvitado){
                                    // muestra las incidecnias abiertas
                                    case 1 -> {
                                        muestraIncidenciasDataClass( app.buscarTodasIncidenciasAbiertas() );
                                    }
                                    // seleccion de salida del menu set todos los valores para salir
                                    case 2 -> {
                                        guestUserMenu = false;
                                    }
                                    default -> {
                                        System.out.println(Menus.errorMenuOpcionesNoEstan());
                                        pulsaContinuar();
                                    }
                                }
                    }while (opMenuInvitado != 2);
                }





            }while (isMenuPrincipal); // NO TOCAR !!!!!

            /* ***************************************** END *****************************************/
            //END appRunning
        }
    }



    /**
     * Se encarga de seleccionar los tecnicos dado una ArrayList<TecnicoDataClass>
     *
     * @author Ariel Cocherane
     * @see GestionAPP#getTecnicos()
     * @see TecnicoDataClass
     * @see Menus#menuMarcarTecnicoGeneral(ArrayList)
     * @param tecnicos
     * @param typeAction el tipo de accion si marca 1 es para el mostar tecnicos para escoger si es 2 es para el menu de borrar tecnico
     * @return idTecnico retorna la id de l tecnico selecionado
     */
    private static int showSeleccionTecnicoRetornaPorId(ArrayList<TecnicoDataClass> tecnicos, int typeAction) {

        //para contralar si no hay tecnicos por asignar
        if ( tecnicos.isEmpty() ) return -1;

        int seleccionTecnico = 0;

        // te muestra las ilos tecnicos en el menu con su id y index para seleccionarlo
        boolean checkOnInput;
        do {
            checkOnInput = false;
            // menu muestras los tecnicos para escogerlos esto seleciona segun el tipo del menu si quieres borrar un tecnico o selecionarlo
            // [1] typeAction para selecionar un tecnico en las incidednias
            // [2] typeAction para escoger el menu de borrado
            if ( typeAction == 1 )System.out.println(Menus.menuMarcarTecnicoGeneral(tecnicos));
            else if ( typeAction == 2 ) System.out.println(Menus.menuBorrarTecnicoGeneral(tecnicos));
            else if ( typeAction == 3 ) System.out.println( Menus.menuMostrarTecnicoIncidecniaToOtherTecnico(tecnicos) );
            else System.out.println("Error: al mostrar los tecnicos para seleccionar");

            try {
                seleccionTecnico = Integer.parseInt(s.nextLine());
            }catch ( NumberFormatException e){
                seleccionTecnico = -1;
            }finally {
                // controlamos que el rango del index de los tecnicos este dentro de la seleccion
                if ( seleccionTecnico < 1 || seleccionTecnico > tecnicos.size() ){
                    System.out.println("Ha ocurrido un problema!!! Se te volvera a presentar los tecnicos e ingresa tu seleccion de nuevo!!!");
                    pulsaContinuar();
                    checkOnInput = true;
                }
            }

        }while (checkOnInput );

        // retornamo la id de la incidencia que hemos seleccionado
        return tecnicos.get( seleccionTecnico - 1 ).getId();


    }

    /**
     * Muestra las incidencias que haya en el Array ordenadas ArrayList<IncidenciasDataClass> y te pide que indiques la posicion
     * de la que quieres seleccionar y devuelve su id es un metodo general para escoger la incidencia y retorna la idIncidencia
     *
     * @author Ariel Cocherane
     * @see )
     * @see TecnicoDataClass
     * @see Menus#menuMarcarIncidenciaGeneral(ArrayList)
     * @param listaIncidencias
     * @return idTecnico retorna la id de la idIncidencia
     */
    public static int showSeleccionIncidenciaRetornaPorId(ArrayList<IncidenciaDataClass> listaIncidencias){
        //para contralar si no hay inidencias por asignar
        if ( listaIncidencias.isEmpty() ) return -1;

        int seleccion = 0;

        // te muestra las incidencias en un menu y controlamos que las escogencia de las incidenias sea la correcta
        boolean checkOnInput;
        do {
        	checkOnInput = false;
            // menu muestras las inidendencias para escogerlas se
            System.out.println(Menus.menuMarcarIncidenciaGeneral(listaIncidencias));

            try {
        		seleccion = Integer.parseInt(s.nextLine());
        	}catch ( NumberFormatException e){
                seleccion = -1;
        	}finally {
                // controlamos que el rango de numero de las incidenias este dentro de la seleccion
                if ( seleccion < 1 || seleccion > listaIncidencias.size() ){
                    System.out.println("Ha ocurrido un problema!!! Se te volvera a presentar las invidencias e ingresa tu seleccion de nuevo!!!");
                    pulsaContinuar();
                    checkOnInput = true;
                }
            }
        }while (checkOnInput );


       // retornamo la id de la incidencia que hemos seleccionado
        return listaIncidencias.get( seleccion - 1 ).getId();


    }

    /**
     * Muestra las incidencias de una Array de IncidenciasDataClass secuencial y con pausa entre cada una
     *
     * @author Ariel Cocherane
     * @see Menus#menuUserSetIncindentNoAun()
     * @see IncidenciaDataClass#toString()
     */
    public static void muestraIncidenciasDataClass(ArrayList<IncidenciaDataClass> listaIncidencias){
        if ( listaIncidencias.isEmpty() ) System.out.println( Menus.menuUserSetIncindentNoAun() );
        else for (IncidenciaDataClass i: listaIncidencias) System.out.println(i);
        pulsaContinuar();
    }

    /**
     * Se encarga de presentar las incidencias pasandole un <IncidenciaDataClass>, se encarga de presentarla con salto de lineas
     *
     * @author Ariel Cocherane
     * @see IncidenciaDataClass#toString()
     * @param incidenciasDataClasses las incidencia ya deven venir ordenadas
     */
    public static void printIncidenciaFromIncidenciaDataClass(ArrayList<IncidenciaDataClass> incidenciasDataClasses ){
        // comprueba si la lista esta vacia muestra una pantalla de que no hay incidencias aun
        if ( incidenciasDataClasses.isEmpty() ) {
            System.out.println( Menus.menuUserSetIncindentNoAun() );
            System.out.println("Pulse para ver la siguiente incidencia .....");
            // para el salto de linea
            s.nextLine();
        }
        else {
            for (var  incidenciaDC: incidenciasDataClasses) {
                System.out.println( incidenciaDC.toString() );
                System.out.println("Pulse para ver la siguiente incidencia .....");
                // para el salto de linea
                s.nextLine();
            }
        }
    }

    /**
     * Remueve el tecnico que se quiere eliminar para mostrarlo en el menu
     *
     * @author Ariel Cocherane
     * @see GestionAPP#getTecnicos()
     * @param idTecnicoDesAsignar la id del tecnico que se quiere eliminar de la lista
     * @return ArrayList<TecnicoDataClass>
     */
    public static ArrayList<TecnicoDataClass> muestraTecnicoAsignarIncidenciaOtroTecnico(int idTecnicoDesAsignar){
        ArrayList<TecnicoDataClass> tecnicosDisponibles = app.getTecnicos();
        tecnicosDisponibles.removeIf(tecnico -> tecnico.getId() == idTecnicoDesAsignar);
        return tecnicosDisponibles;
    }

    /**
     * Se encarga de mostrar la configuraciones del sistema al Admin
     *
     * @author Ariel Cocherane
     * @see Menus#menuShowAllConfigApp(Set)
     * @see GestionAPP#getAllConfigApp()
     * @see DataRecovery#getPropertiesConfig() ()
     * @param allConfigApp
     * @return El menu donde lo muestra con todas las configuraciones
     */
    private static String showAllConfigApp(Set<Map.Entry<Object, Object>> allConfigApp) {
        return Menus.menuShowAllConfigApp( allConfigApp );
    }

    /* muestra las ultimas connections del users*/
    public static String showAllLastSessions( HashMap<Object, Date> lastSessions ){
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

        String lastSession = "";
        int index = 1;
        // interamos sobre el  hashmap de objetos usuario y date
        for ( Map.Entry<Object, Date> configSet : lastSessions.entrySet()) {

            // pasamos el valor del objeto para obtener la informacion del usuario
            String[] dataUser = getResumeUser( configSet.getKey() );
            // Obtenemos el objeto Date
            Date dateLastSession =  configSet.getValue();

            lastSession += String.format("""
                                               -------------------------------------------------------------
                                               [%d]   ID: %s   Tipo de User : %s    
                                                     Nombre: %s
                                                     Ultima conexion: %s 
                                      """
                    , index++
                    , dataUser[0] // muestra la id del usuario
                    , dataUser[1] // muestra el tipo de usuario
                    , dataUser[2] // muestra el nombre del usuario
                    , dateFormat.format( dateLastSession )
            );
        }
        String salidaChoice = String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░     MENU DE REGISTRO DE LAS ULTIMAS       ░░░░░░░░░░░░░░░░░░░
                                             CONEXIONES POR LOS USUARIOS
                          
                                 Las ultimas sesiones se muestran sin ningun orden   
                                                                                                 
                %s
                         -------------------------------------------------------------
                                                                  
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """, lastSession);

        return salidaChoice;

    }

    /* Muestra un resumen del usuario ejemplo Tipo de usuario, la id, nombre, apellido, ultima dia de session  */
    private static String[] getResumeUser(Object object){
        String[] dataUser = new String[3];
        if ( object instanceof Usuario ){
            dataUser[0] = String.valueOf(((Usuario) object).getId());
            dataUser[1] = "Usuario";
            dataUser[2] = ((Usuario) object).getNombre()+" "+ ((Usuario) object).getApel();
        }else if ( object instanceof Tecnico ){
            dataUser[0] = String.valueOf(((Tecnico) object).getId());
            dataUser[1] = "Tecnico";
            dataUser[2] = ((Tecnico) object).getNombre()+" "+ ((Tecnico) object).getApel();
        }else if ( object instanceof Admin ){
            dataUser[0] = String.valueOf(((Admin) object).getId());
            dataUser[1] = "Admin";
            dataUser[2] = ((Admin) object).getNombre()+" "+ ((Admin) object).getApel();
        }

        return dataUser;
    }




    public static float prioridadMediabyTecnico( int idTecnico ){
        int countNum = 0, sumPrioridad = 0;
        // las incidencias son IncidenciaDataclass
        for (var incidencia : app.buscarIncidenciaCerradabyTecnico(idTecnico)) {
            sumPrioridad += incidencia.getPrioridad();
            countNum++;
        }

        for (var incidencia : app.buscaIncidenciasAbiertasbyTecnico(idTecnico)) {
            sumPrioridad += incidencia.getPrioridad();
            countNum++;
        }
        if ( countNum == 0) return 0f;

        return (float) (sumPrioridad/countNum);
    }

    public static void creaTecnico(){
        String nombreTecTEmp, apelsTecTemp, claveTecTemp, emailTecTemp;
        System.out.println(Menus.menuRecordUserEmail());
        emailTecTemp = s.nextLine();
        System.out.println(Menus.menuRecordUsuarioName());
        nombreTecTEmp = s.nextLine();
        System.out.println(Menus.menuRecordUserSurname());
        apelsTecTemp = s.nextLine();
        System.out.println(Menus.menuRecordUserPass());
        claveTecTemp = s.nextLine();

        if (app.insertaTecnico(nombreTecTEmp,apelsTecTemp,claveTecTemp,emailTecTemp))
            System.out.println("Tecnico creado con exito");
        else System.out.println(Menus.menuRecordErrorUsuario());
    }

    public static void pulsaContinuar(){
        System.out.println("Pulse para continuar ....");
        s.nextLine();
    }


}
