package FrontEnd;

import models.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class Menus {
    /* Aqui se va a concentrar todos los menus de esta clase Como una clase Static
    la nomesclatura para ponerle nombre a los menus está determinado por el nombre
    menu[nombre del User Primera en mayuscula][seguido por el numero de opcion en el que se encuentra..[Si tienen submenu se pone un punto y el numeo del submenu]]
    Ejemplo 1 menuTecnico1() esta quiere decir que es ta en el menu del tecnico en la opcion 1[Consultar las incidencias asignadas]
    Ejemplo 2 menuAdministrador6.1() esta te muesta la pantalla para meter los datos para dar de alta un tecnico

     */




    /* *********************************************************************************/
    /* ******************************* Menus de inicio *********************************/
    /* *********************************************************************************/

    /**
     * Muestra el menu principal
     *
     * @author Ariel Cocherane
     * @return El menu principal
     */
    static public String menuPrincipal_1( boolean isGuestUserActive ) {
        return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░     MENU DE LOGGING      ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                  Bienvenido, Quieres reportar una incidencia
                    ***Recuerda que para reportar una incidencia tienes que estar registrado***
                                                                                                   
                          -------------------------------------------------------------
                           [1]   Ya estoy registrado
                          -------------------------------------------------------------
                           [2]   Registrarme
                          -------------------------------------------------------------
                %s                                                                         
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
                , isGuestUserActive?
                        """
                                           [3]   Ingresa como invitado
                                         --------------------------------------------------------------
                                """
                        :
                        ""
        );
    }

    /**
     * Se encarga de pedir los datos del User a logiarse
     *
     * @author Ariel Cocherane
     * @return El menu de pedir el User
     */
    static public  String menuPrincipal_1_1() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE LOGGING   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                             Introduce tu EMAIL
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /**
     * Se encarga de pedir la clave de los User
     *
     *@author Ariel Cocherane
     *@return El menu de pedir la clave del User
     */
    static public  String menuPrincipal_1_2() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE LOGGING   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                             Introduce tu PASSWORD
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /**
     * Se encarga de mandar un mensaje de advertencia al User que el password
     * y el user no coinciden
     *
     *
     *@author Ariel Cocherane
     *@return mensaje de advertencia que el proceso de login no se pudo realizar
     */
    static public  String menuLoggingError() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE LOGGING   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                       ***** Tu USUARIO o Tu PASSWORD no concuerdan intenta de nuevo *****
                                                                                                   
                              [1] Pulsa cualquier tecla para intentar de nuevo
                              [*] para volver al menu principal para registrarte
                          -------------------------------------------------------------
                                                                                                   
                ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /**
     * Menu donde se solicita el token
     *
     * @author Ariel Cocherane
     * @return Menu donde se solicita el token o una opcion para salir al menu principal.
     */

    public static String menuSolicitarToken( ){
        return """
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE LOGGING   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                             Introduce tu Token        
                                   ***** El token fue enviado a tu email *****  
                                   [1] Para salir del al menu principal.          
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """;

    }

    /**
     * El token introducido por el usuario concuerda con los datos de su token almacenado
     *
     *@author Ariel Cocherane
     *@return Mensaje de aporbacion al introducir el token
     */
     public static String mensajeTokenAccepted(){
         return ("""
                  ╔═════════════════════════════════════════════════════════════════════════════════╗
                  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE LOGGING   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                    
                               ****** Tu token ha sido exitosamente comprobado ******
                           -------------------------------------------------------------
                                                                                                    
                  ╚═════════════════════════════════════════════════════════════════════════════════╝
                 """
         );
     }

    /**
     * Mensaje donde te solicita introducir tu token de nuevo, porque la comprobación no ha sido aceptada
     *
     * @author Ariel Cocherane
     * @return Mensaje de introducir tu token de nuevo
     */
    public static String mensajeTokenNoAccepted() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE LOGGING   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                          ****** Vuelve a introducir tu token, no es el correcto ******              
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }



    /* *********************************************************************************/
    /* ******************************* Menus de USUARIO ********************************/
    /* *********************************************************************************/
    static public String menuUsuarioPrincipal(Usuario usuario, int incidenciaSinAsignar, int incidenciasAsignadas) {
        return String.format("""
                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░      MENU DE USUARIO     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                  Bienvenido %8S, Tienen usted perfil de usuario normal
                            Actualmente, tiene %d incidencias sin asignar y %d incidencias ya asignadas
                                                                                                           
                                  -------------------------------------------------------------
                                   [1]   Registrar una incidencia
                                  -------------------------------------------------------------
                                   [2]   Consultar mis incidencias abiertas
                                  -------------------------------------------------------------
                                   [3]   Consultar mis incidencias cerradas
                                  -------------------------------------------------------------
                                   [4]   Mostrar mi perfil
                                  -------------------------------------------------------------
                                   [5]   Cambiar clave de acceso
                                  -------------------------------------------------------------
                                   [6]   Cerra session
                                  -------------------------------------------------------------
                            La hora actual es: %tr                                                                               
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """
                , usuario.getNombre()
                , incidenciaSinAsignar
                ,incidenciasAsignadas
                , new Date()
        );
    }


    public static String menuUsuarioPerfil(Object object, int incidecniasCerradas ){
        return String.format(
                """
                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░          Usuario          ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                         Perfil del Usuario
                                                                                                             
                                                                                                             
                                  -------------------------------------------------------------
                                   Nombre: %15S
                                  -------------------------------------------------------------
                                   ID del Usuario: %15d
                                  -------------------------------------------------------------
                                   Incidencias que han sido resueltas para este Usuario: %d
                                  -------------------------------------------------------------
                                                                                                             
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """, ((Usuario) object).getNombre(), ((Usuario) object).getId(), incidecniasCerradas
        );
    }

    /* Este menu pregunta por el usuario y devuelve el usuario introducido */
    static public  String menuUsuarioSetIncidenciaDescription() {
        return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE USUARIO   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                    Detallándonos el motivo de tu incidencia
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    /* Este menu pregunta por el usuario y devuelve el usuario introducido */
    static public  String menuUsuarioSetIncidenciaPrioridad() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE USUARIO   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                    Ingresa la prioridad del 1 al 10
                                      El 1 es la prioridad minima
                                      El 10 es la prioridad maxima
                          -------------------------------------------------------------
                                                                                            
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    /* Este metodo muestra una pantalla si es tigger por que no hay espacio en el usuario normal para almacenar una incidencia*/
    static public String  menuUserSetIncindentMessager() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE USUARIO   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                           Tu incidencia esta siendo procesada en breve lo atenderemos
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }


    /* Este menu realiza la obtención del Nombre al registrarse*/
    static public  String menuRecordUserEmail() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░    REGISTRANDO EL EMAIL   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                           Estaremos registrando tus datos
                          -------------------------------------------------------------
                           [*]   Introduce tu EMAIL
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este menu realiza la obtención del Nombre al registrarse*/
    static public  String menuRecordUsuarioName(){
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░   REGISTRANDO EL NOMBRE   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                           Estaremos registrando tus datos
                          -------------------------------------------------------------
                           [*]   Introduce tu NOMBRE
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este menu realiza la obtención del Apellido al registrarse*/
    static public  String menuRecordUserSurname() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░  REGISTRANDO EL APELLIDO  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                           Estaremos registrando tus datos
                          -------------------------------------------------------------
                           [*]   Introduce tu APELLIDO
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este menu realiza la obtención del Nombre al registrarse*/
    static public  String menuRecordUserPass(){
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░  REGISTRANDO EL PASSWORD  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                           Estaremos registrando tus datos
                          -------------------------------------------------------------
                           [*]   Introduce tu PASSWORD
                          -------------------------------------------------------------
                        NOTA!!! debe tener una longitud de al menos 4 caracteres o digitos
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este menu realiza la obtención del Nombre al registrarse*/
    public static String  menuRecordErrorUsuarioEmail() {
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                 El email ya existe, intenta registrarte de nuevo
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este menu realiza la obtención del Nombre al registrarse*/
    static public  String menuRecordErrorUsuario() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                               Lo sentimos ha ocurrido un error al registrate
                               te recordamos algunos errores comunes
                               [*] No puedes introducir campos vacíos
                               [*] La clave debe tener una longitud de 4 
                               [*] Introduce un email valido
                           -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }







        /* *********************************************************************************/
    /* ******************************* Menus de TECNICO ********************************/
    /* *********************************************************************************/

    static public String menuTecnicoPrincipal(String nameTecnico, int inciAbiertaTecnico, int inciCerradaTecnico, float priorMedia){
        return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░      MENU DE TECNICO     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                          Bienvenido %S, Tienen usted perfil de Tecnico
                          Actualmente, Tiene %d incidenias abiertas y %d incidenias cerradas
                          La prioridad media de su incidencias es %.2f
                          
                          -------------------------------------------------------------
                           [1]   Consultar las incidencias que tengo asignadas
                          -------------------------------------------------------------
                           [2]   Marcar una incidencia como cerrada
                          -------------------------------------------------------------
                           [3]   Consultar las incidencias que he resuelto
                          -------------------------------------------------------------
                           [4]   Mostrar mi perfil
                          -------------------------------------------------------------
                           [5]   Cambiar clave de acceso
                          -------------------------------------------------------------
                           [6]   Cerra session
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """, nameTecnico, inciAbiertaTecnico, inciCerradaTecnico, priorMedia);
    }
    public static String menuTecnicoPerfil(Object object){
        return String.format(
                """
                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░          Técnico          ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                         Perfil del Técnico
                                                                                                             
                                                                                                             
                                  -------------------------------------------------------------
                                   Nombre: %15S
                                  -------------------------------------------------------------
                                   ID del Técnico: %15d
                                  -------------------------------------------------------------
                                   Email: %15S
                                  -------------------------------------------------------------
                                                                                                             
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """, ((Tecnico) object).getNombre(), ((Tecnico) object).getId(), ((Tecnico) object).getEmail()
        );
    }

    // me muestra un menu de seleccion de inidecnias dada una arraylist de inidencias
    static public String menuMarcarIncidenciaGeneral(ArrayList<IncidenciaDataClass> incidencias){
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );

        String unitInicencia = "";
        for (int i = 0; i < incidencias.size(); i++) {
            unitInicencia += String.format("""
                                      -------------------------------------------------------------
                                       [%d]  Incidencia con ID: %s   Abierta por :%s    
                                            Fecha de la creacion: %s 
                                            Comentario: %s
                                            Prioridad: %d
                                                            
                            """
                    , i+1
                    , incidencias.get(i).getId()
                    , incidencias.get(i).getNombreUsuario()
                    , dateFormat.format( incidencias.get(i).getFechaInicio())
                    , incidencias.get(i).getDescripcion()
                    , incidencias.get(i).getPrioridad()
                    );
        }


        String salidaChoice = String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░      MENU DE SELECCION DE INCIDENCIA     ░░░░░░░░░░░░░░░░░░░░░
                                          QUE INCIDENCIAS QUIERES ESCOGER
                                                                                                 
                %s
                         -------------------------------------------------------------
                                                                                                  
                                Marca la incidencias que quieres escoger
                         -------------------------------------------------------------
                                                                  
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """, unitInicencia);
        return salidaChoice;
    }

    /* Este metodo muestra un mensaje de cual es el mensaje del tecnico para esta resolucion*/
    static public String menuCerrarIncidenciaGetMensaje() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░      MENU     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                           Introduce el mensaje mensaje de resolución de la incidencia
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    // este menu indica si al momento de cerrar una incidencia por el tecnico ha sido Aceptada o procesada
    static public String menuCerrarIncidenciaAceptada() {
        return ("""
                  ╔═════════════════════════════════════════════════════════════════════════════════╗
                  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░      MENU     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                    La incidencia ha sido Marcada como Cerrada
                          -------------------------------------------------------------
                                                                                                   
                  ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );

    }

    static public String menuCerrarIncidenciaDeclinada() {
        return("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░      MENU     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                 
                                 La incidencia no ha sido Marcada como Cerrada
                                 Intenta de nuevo  selecionar el menu de cerrar
                         -------------------------------------------------------------
                                                                                                  
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );

    }






    /* *********************************************************************************/
    /* **************************** Menus de ADMINISTRADOR *****************************/
    /* *********************************************************************************/

    static public String menuAdministradorPrincipal(String nameAdmin,int inciAbiertaAsignada, int inciSinAsignar) {
        return String.format("""

                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░  MENU DE ADMINISTRACION  ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                  Bienvenido  %8S, Tienen usted perfil de administrador
                                  Actualmente, hay %d incidencias abiertas, de las cuales 
                                  %d no estan asignadas a ningun tecnico.
                        
                                  -------------------------------------------------------------
                                   [1]   Consultar todas las incidencias abiertas
                                  -------------------------------------------------------------
                                   [2]   Consultar todas las incidencias cerradas
                                  -------------------------------------------------------------
                                   [3]   Consultar incidencias por termino
                                  -------------------------------------------------------------
                                   [4]   Consultar los tecnicos
                                  -------------------------------------------------------------
                                   [5]   Asignar una incidencia a un tecnico
                                  -------------------------------------------------------------
                                   [6]   Dar alta a un tecnico
                                  -------------------------------------------------------------
                                   [7]   Borrar Un tecnico
                                  -------------------------------------------------------------
                                   [8]   Consultar los usuarios
                                  -------------------------------------------------------------
                                   [9]   Estadísticas de la aplicacion
                                  -------------------------------------------------------------
                                   [10]  Enviar resumen incidencia por email al tecnico
                                  -------------------------------------------------------------
                                   [11]  Configuraciones del sistema
                                  -------------------------------------------------------------
                                   [12]  Ultimas sesiones de los usuarios
                                  -------------------------------------------------------------
                                   [13]  Cerra session
                                  -------------------------------------------------------------
                                                                                                               
                                                                                                           
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """
                , nameAdmin.toUpperCase()
                , inciAbiertaAsignada
                , inciSinAsignar
        );
    }
    static public String menuAdministrador1MostarIncidecniasAbiertas(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                        Las incidecnias abiertas son
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador1MostarIncidecniasCerradas(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                        Las incidecnias carradas son
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador2MostarUsers(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                                Los usuarios son
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador3MostarTecnicos(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                                Los técnicos son
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador4MostarIncidecniasSinAsignar(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                        Las incidecnias sin asignar son
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador4ErrorBuscarTecnico(){
            return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE ADMIN   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                Error!! No se ha podido encontrar al tecnico
                                               Intenta de nuevo
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
            );

    }
    static public String menuAdministrador4ErrorBuscarIncidencia(){
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE ADMIN   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                Error!! No se ha podido encontrar la incidencia
                                               Intenta de nuevo
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );

    }
    static public String menuAdministrador6TecnicoConIncidenias(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                               Este tecnico tiene incidenicas asignadas sin resolver
                            ** Inserte el id del tecnico al que se les va a traspasar **
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador6TecnicoEliminado(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                     El Tecnico ha sido eliminado conrrectamente
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador6ErrorBuscaTEcnico(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                            El id del tecnico no existe
                              Por favor comprueba que lo has introducido correctamente
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    static public String menuAdministrador6NoSePuedeEliminarTecnico(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                        El Tecnico no se puede eliminar
                                  Tiene incidecnias cerradas en su historial
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    // me muestra un menu de seleccion de inidecnias dada una arraylist de inidencias
    static public String menuShowAllConfigApp( Set<Map.Entry<Object, Object>> allConfigApp ) {
        String configApp = "";
        
        for (Map.Entry<Object, Object> configSet : allConfigApp) {
            // aseguramos que los datos protegidos de las apis y id de telegran salgan ocultos
            String secureValues = "";
            if ( configSet.getKey().toString().equals( "idsendtelegram" ) || configSet.getKey().toString().equals( "bottelegram" )  ) secureValues = "****************";
            else secureValues = configSet.getValue().toString();
            configApp += String.format("""
                                      -------------------------------------------------------------
                                       [*]  %s = %s               
                            """
                    , configSet.getKey().toString()
                    , secureValues
            );
        }
        String salidaChoice = String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░       MENU DE CONFIGURACIONES DE LA APP      ░░░░░░░░░░░░░░░░░

                                             Configuraciones del sistema    
                                                                                                 
                %s
                         -------------------------------------------------------------
                                                                                                  
                            Las configuraciones se pueden modificar en el archivo de 
                                             propiedades.config 
                         -------------------------------------------------------------
                                                                  
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """, configApp);

        return salidaChoice;
    }




    /* *********************************************************************************/
    /* ******************************** Menus de ERROR *********************************/
    /* *********************************************************************************/

    /* **************************** ERROR Menu PRINCIPAL *******************************/
    /**
     * Maneja un mensaje de error si el User Introduce un menu
     * fuera de rango o una letra a las opciones
     *
     *
     * @author Ariel Cocherane
     * @return Mensaje de ERROR para cuando la opcion en el menu principal no esta o introduce un character
     */
    public static String errorMenuOpcionesNoEstan(){
        return """
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                         ***** Lo sentimos la opcion que marcarte no esta en el menu *****
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """;

    }

    /**
     * Se envia el mensaje cuando no hay dentros de los USer con el email y clave dada
     *
     * @author Ariel Cocherane
     * @return Mensaje que el loguin ha fallado
     */
    public static String errorMenuPrincipalNoCoincidenciaEmailClave( ){
        return """
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE LOGGING   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                       ***** Tu USUARIO o Tu PASSWORD no concuerdan intenta de nuevo *****
                                                                                                   
                              [*] Pulsa cualquier tecla para intentar de nuevo
                              [1] para volver al menu principal para registrarte
                          -------------------------------------------------------------
                                                                                                   
                ╚═════════════════════════════════════════════════════════════════════════════════╝
                """;

    }

    /* ***************************** ERROR Menu USUARIO ********************************/

    /* Este menu advierte al usuario las condiciones de las claves que tiene que introducir */
    static public String menuUserSetIncidentError() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░    MENU DE USUARIO   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                Error!! No se ha podido introducir tu incidencia
                                               Intenta de nuevo
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }
    /* Este menu muestra la prioridad media de las inicencias y las incidenias abiertas y cerrdas */
    public static String  menuEstadisticaApp(int numInciCerradas, int numInciAbiertas, float prioridadMedia) {
        return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░   LA ESTADISTICA DE LA APLICACION   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                              Numero de incidencias cerradas:             %d
                              Numero de incidencias abiertas:             %d
                              Prioridad media de las incidencias totales: %.2f 
                              Procentaje de incidencias resueltas:        %.2f %%      
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
                ,  numInciCerradas
                , numInciAbiertas
                , prioridadMedia
                , (float)( ( numInciCerradas*100 )/ ( numInciAbiertas + numInciCerradas ) )
        );
    }

    public static String menuGetTermFindIncidencia( ) {
        return("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                     
                             Ingresa el termino que quieres encontar en las incidencias
                          -------------------------------------------------------------
                                                                                                     
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }



    /* ***************************** ERROR Menu TECNICO ********************************/

    /* ************************** ERROR Menu ADMINISTRADOR *****************************/
    /* Este metodo muestra una pantalla si es tigger que no has realizado ninguna incidencia aun*/
    static public String  menuNoHayTecnicosRegistrados() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░     MENU DE ADMIN     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                ****** No hay ningun tecnico registrado ******
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este metodo muestra una pantalla si es tigger que no has realizado ninguna incidencia aun*/
    static public String  menuNoHayTecnicosParaAsignarOtraIncidecniasTecnico() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░     MENU DE ADMIN     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                    ****** No hay ningun tecnico registrado para asignarle las incidencias ******
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* *********************************************************************************/
    /* ************************* Menus de User conjuntos *******************************/
    /* *********************************************************************************/

    /* Este menu muestra que no hay tecnicos aun registrados */
    static public  String menuUserChangePassword() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░   MENU DE CAMBIO DE CLAVE   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                          Introduce el nuevo PASSWORD
                          -------------------------------------------------------------
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este menu advierte al usuario que la modification del password ha sido exitosa */
    public static String  menuUserChangePassMessager() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░   MENU DE CAMBIO DE CLAVE   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                               Se ha modificado tu clave de usuario exitosamente!!!!!
                                      Te enviaremos un Token a tu email 
                    Recuerda que tendras que hacer loguin de nuevo una vez salgas de la pantalla
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    static public String  menuUserChangePassMessagerError() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░   MENU DE CAMBIO DE CLAVE   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                               NO se ha modificado tu clave correctamente ERROR!!!!!
                                         Contacta con tu ADMINISTRADOR
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """);
    }

    /* Este metodo muestra una pantalla si es tigger que no has realizado ninguna incidencia aun*/
    static public String  menuUserSetIncindentNoAun() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░     INCIDENCIAS     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                ****** No hay ninguna incidencia en este menu ******
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* Este metodo muestra una pantalla si es tigger que no has realizado ninguna incidencia aun*/
    static public String  menuUserNoTecnicoAun() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░   MENU DE  ADMIN     ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                                ****** No hay ningun tecnicos en este menu ******
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    static public  String menuCreateUserCorrect(String nombre, int type) {
        return String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                       Bienvenido!!! se a creado el %s exitosamente       %10S
                       
                   %s
                     ---------------------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
                ,(type == 1)?"usuario":"tecnico"
                ,nombre
                ,(type == 1)?"**** Un token fue enviado a tu email, el cual debes ingresar por primera vez****":""
        );
    }
    /* Este menu advierte al usuario que la modification del password ha sido exitosa */
    public static String  menuTecnicoChangePassMessager() {
        return ("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░   MENU DE CAMBIO DE CLAVE   ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                               Se ha modificado tu clave de usuario exitosamente!!!!!
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    // muestra la informacion del usuario en el menu de admin
    static public String showUsuario(UsuarioDataClass usuario) {
        return String.format("""
                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░       MENU DE ADMIN        ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                         Perfil del Usuario
                                                                                                                                                                
                                  -------------------------------------------------------------
                                   Nombre: %15S
                                  -------------------------------------------------------------
                                   ID del Usuario: %15d
                                  -------------------------------------------------------------
                                   Incidencias pendientes por asignar a este Usuario: %d
                                  -------------------------------------------------------------
                                                                                                             
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """
                , usuario.getNombre() + " " + usuario.getApel()
                , usuario.getId()
                , usuario.getIncidenciasAbiertas()
        );
    }
    // muestra los tecnicos en el menu de admin
    static public String showTecnico(TecnicoDataClass tecnico ) {
        return String.format("""
                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░       MENU DE ADMIN        ░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                Tecnico a cargo de las incidencias
                                                                                                           
                                                                                                           
                                  -------------------------------------------------------------
                                   Nombre: %15S
                                  -------------------------------------------------------------
                                   ID del tecnico: %15d
                                  -------------------------------------------------------------
                                   Numero de incidencias pendientes:  %4d
                                  -------------------------------------------------------------
                                   Numero de incidencias realizadas:  %4d
                                  -------------------------------------------------------------
                                                                                                           
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """
                , tecnico.getNombre() + " " + tecnico.getApel()
                , tecnico.getId()
                , tecnico.getIncidenciasAbiertas()
                , tecnico.getIncidenciasCerradas()
        );
    }

    // me muestra un menu de seleccion de inidecnias dada una arraylist de inidencias
    static public String menuMarcarTecnicoGeneral(ArrayList<TecnicoDataClass> tecnico) {
        String unitTecnico = "";
        for (int i = 0; i < tecnico.size(); i++) {
            unitTecnico += String.format("""
                                      -------------------------------------------------------------
                                       [%d]  El ID Tecnico: %s     
                                            Nombre : %s %s
                                            Incidencias asignadas: %d
                                            Prioridad media: %.2f
                                                            
                            """
                    , i + 1
                    , tecnico.get(i).getId()
                    , tecnico.get(i).getNombre()
                    , tecnico.get(i).getApel()
                    , tecnico.get(i).getIncidenciasAbiertas()
                    , tecnico.get(i).getPrioridadMediaIncidenciaTecnico()
            );

        }
        String salidaChoice = String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░       MENU DE SELECCION DEL TECNICO      ░░░░░░░░░░░░░░░░░░░░░
                                             QUE TECNICO QUIERES ESCOGER
                                                                                                 
                %s
                         -------------------------------------------------------------
                                                                                                  
                                Marca el tecnico con el index ...... 
                         -------------------------------------------------------------
                                                                  
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """, unitTecnico);

        return salidaChoice;
    }

    // me muestra un menu de seleccion de inidecnias dada una arraylist de inidencias
    static public String menuBorrarTecnicoGeneral(ArrayList<TecnicoDataClass> tecnico) {
        String unitTecnico = "";
        for (int i = 0; i < tecnico.size(); i++) {
             unitTecnico += String.format("""
                                      -------------------------------------------------------------
                                       [%d]  El ID Tecnico: %s     
                                            Nombre : %s %s
                                            Incidencias asignadas: %d
                                            %s                          
                            """
                    , i + 1
                    , tecnico.get(i).getId()
                    , tecnico.get(i).getNombre()
                    , tecnico.get(i).getApel()
                    , tecnico.get( i ).getIncidenciasAbiertas()
                    , tecnico.get( i ).getIncidenciasAbiertas() > 0?"Advertencia: no puede borrar el tecnico hasta que traspase las incidencia": ""
            );
        }
        String salidaChoice = String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░       MENU DE SELECCION DEL TECNICO      ░░░░░░░░░░░░░░░░░░░░░
                                             QUE TECNICO QUIERES BORRAR
                        Advertencia: no pueder borrar un tecnico que tenga incidencias cerradas o 
                        abiertas
                                                                                                 
                %s
                         -------------------------------------------------------------
                                                                                                  
                                Marca el tecnico con el index ...... 
                         -------------------------------------------------------------
                                                                  
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """, unitTecnico);

        return salidaChoice;
    }

    // me muestra un menu de seleccion de inidecnias dada una arraylist de inidencias
    static public String menuMostrarTecnicoIncidecniaToOtherTecnico(ArrayList<TecnicoDataClass> tecnico) {
        String unitTecnico = "";
        for (int i = 0; i < tecnico.size(); i++) {
            unitTecnico += String.format("""
                                      -------------------------------------------------------------
                                       [%d]  El ID Tecnico: %s     
                                            Nombre : %s %s
                                            Incidencias asignadas: %d
                                            %s                          
                            """
                    , i + 1
                    , tecnico.get(i).getId()
                    , tecnico.get(i).getNombre()
                    , tecnico.get(i).getApel()
                    , tecnico.get( i ).getIncidenciasAbiertas()
                    , tecnico.get( i ).getIncidenciasAbiertas() > 0?"Advertencia: no pueder borrar el tecnico hasta que el tecnico Cierre la incidenia": ""
            );
        }
        String salidaChoice = String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░       MENU DE SELECCION DEL TECNICO      ░░░░░░░░░░░░░░░░░░░░░
               
                               Escoge el tecnico que quieres asignarles la incidecnia
                                                                                                 
                %s
                         -------------------------------------------------------------
                                                                                                  
                                Marca el tecnico con el index ...... 
                         -------------------------------------------------------------
                                                                  
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """, unitTecnico);

        return salidaChoice;
    }



    public static String messageWelcomeLastSession(Date date){
        String menBienvenida ="""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                    ¡Te damos la bienvenida al servicio de gestión de incidencias FERNTICKETS!!!
                       
                     ---------------------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """;

        // Comprobamos si existe el registro, si es cierto le mandamos el mensaje del último login
        if ( date != null ) {
            return  String.format("""
                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                    Bienvenido!!!! La última vez que iniciaste sesión fue él   %tD %tR 
                       
                     ---------------------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
                    , date
                    , date
            );
        }
        // si es nuevo el user le enviamos un mensaje de bienvenida
        return menBienvenida;
    }

    /* muestra un mensaje cuando se envia un email con resumen de sus incidencias y no hay incidencia sin asignar aun a ningun tecnico*/
    static public String menuAdminNohayNingunaIncidenciaAsignada(){
        return ("""

                 ╔═════════════════════════════════════════════════════════════════════════════════╗
                 ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                   
                          No hay ninguna incidencia sin asignar a ningun tecnico aun
                          -------------------------------------------------------------
                                                                                                   
                 ╚═════════════════════════════════════════════════════════════════════════════════╝
                """
        );
    }

    /* *********************************************************************************/
    /* *************************  PARTE DE INVITADO  *******************************/
    /* *********************************************************************************/

    static public String menuInvitadoPrincipal( int inciAbiertaAsignada ) {
        return String.format("""

                         ╔═════════════════════════════════════════════════════════════════════════════════╗
                         ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░   MENU DE INVITADO    ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                  Bienvenido Usuario, Tienen usted perfil de Invitado
                                  Actualmente, hay %d incidencias abiertas.
                        
                                  -------------------------------------------------------------
                                   [1]   Consultar todas las incidencias abiertas
                                  -------------------------------------------------------------
                                   [2]   Cerra session
                                  -------------------------------------------------------------
                                                                                                           
                         ╚═════════════════════════════════════════════════════════════════════════════════╝
                        """
                , inciAbiertaAsignada

        );
    }



    /* *********************************************************************************/
    /* *************************  PARTE DE COMUNICATION  *******************************/
    /* *********************************************************************************/

    /* pinta menu de registra y enviar el token del usuario*/
    public static String sentTokenRegister(String name, String surname, int token ) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"><head><meta charset=\"UTF-8\"><meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"><meta name=\"x-apple-disable-message-reformatting\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta content=\"telephone=no\" name=\"format-detection\"><title>Nuevo mensaje</title> <!--[if (mso 16)]><style type=\"text/css\">     a {text-decoration: none;}     </style><![endif]--> <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> <!--[if gte mso 9]><xml> <o:OfficeDocumentSettings> <o:AllowPNG></o:AllowPNG> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml><![endif]--><style type=\"text/css\">#outlook a {\tpadding:0;}.ExternalClass {\twidth:100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div {\tline-height:100%;}.es-button {\tmso-style-priority:100!important;\ttext-decoration:none!important;}a[x-apple-data-detectors] {\tcolor:inherit!important;\ttext-decoration:none!important;\tfont-size:inherit!important;\tfont-family:inherit!important;\tfont-weight:inherit!important;\tline-height:inherit!important;}.es-desk-hidden {\tdisplay:none;\tfloat:left;\toverflow:hidden;\twidth:0;\tmax-height:0;\tline-height:0;\tmso-hide:all;}.es-button-border:hover a.es-button, .es-button-border:hover button.es-button {\tbackground:#ffffff!important;\tborder-color:#ffffff!important;}.es-button-border:hover {\tbackground:#ffffff!important;\tborder-style:solid solid solid solid!important;\tborder-color:#3d5ca3 #3d5ca3 #3d5ca3 #3d5ca3!important;}[data-ogsb] .es-button {\tborder-width:0!important;\tpadding:15px 20px 15px 20px!important;}@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:20px!important; text-align:center } h2 { font-size:16px!important; text-align:left } h3 { font-size:20px!important; text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:20px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:16px!important } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:10px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:12px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button, button.es-button { font-size:14px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }</style></head>\n" +
                "<body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"><div class=\"es-wrapper-color\" style=\"background-color:#FAFAFA\"> <!--[if gte mso 9]><v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\"> <v:fill type=\"tile\" color=\"#fafafa\"></v:fill> </v:background><![endif]--><table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"><tr style=\"border-collapse:collapse\"><td valign=\"top\" style=\"padding:0;Margin:0\"><table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0;background-color:#fafafa\" bgcolor=\"#fafafa\" align=\"center\"><table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;background-color:transparent\" bgcolor=\"transparent\" align=\"left\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;font-size:0px\" align=\"center\"><img src=\"https://github.com/Cocherane/Imagenpractica3/blob/main/index_img.png?raw=true\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"560\"></td>\n" +
                "</tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px\"><span style=\"font-family:-apple-system, blinkmacsystemfont, 'segoe ui', roboto, helvetica, arial, sans-serif, 'apple color emoji', 'segoe ui emoji', 'segoe ui symbol';font-size:18px\"><strong>BIENVENIDO!!! GRACIAS POR REGISTRATE<br>Ya falta poco para usar Ferntickets</strong></span></td></tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">Hola,&nbsp;" + name.toUpperCase() + " " + surname.toUpperCase() + ",</p></td>\n" +
                "</tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-right:35px;padding-left:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">te damos las bienvenida!<br></p></td></tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-top:25px;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">Sigue ahora con la activacion de tu cuenta para utilizar y disfrutar cuanto antes de Fernticket.<br><br><strong>TU TOKEN DIGITAL</strong><br><br></p></td>\n" +
                "</tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:40px;padding-bottom:40px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#3D5CA3;background:#FFFFFF;border-width:2px;display:inline-block;border-radius:10px;width:auto\"><a href=\"https://github.com/Cocherane/practicaObligatoria3.git\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#3D5CA3;font-size:20px;border-style:solid;border-color:#FFFFFF;border-width:15px 20px 15px 20px;display:inline-block;background:#FFFFFF;border-radius:10px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:17px;width:auto;text-align:center\">" + token + "</a></span></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td>\n" +
                "</tr></table></div></body></html>";
    }

    public static String sentReseatPassword(String email, int password ) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"><head><meta charset=\"UTF-8\"><meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"><meta name=\"x-apple-disable-message-reformatting\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta content=\"telephone=no\" name=\"format-detection\"><title>Nuevo mensaje</title> <!--[if (mso 16)]><style type=\"text/css\">     a {text-decoration: none;}     </style><![endif]--> <!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--> <!--[if gte mso 9]><xml> <o:OfficeDocumentSettings> <o:AllowPNG></o:AllowPNG> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml><![endif]--><style type=\"text/css\">#outlook a {\tpadding:0;}.ExternalClass {\twidth:100%;}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div {\tline-height:100%;}.es-button {\tmso-style-priority:100!important;\ttext-decoration:none!important;}a[x-apple-data-detectors] {\tcolor:inherit!important;\ttext-decoration:none!important;\tfont-size:inherit!important;\tfont-family:inherit!important;\tfont-weight:inherit!important;\tline-height:inherit!important;}.es-desk-hidden {\tdisplay:none;\tfloat:left;\toverflow:hidden;\twidth:0;\tmax-height:0;\tline-height:0;\tmso-hide:all;}.es-button-border:hover a.es-button, .es-button-border:hover button.es-button {\tbackground:#ffffff!important;\tborder-color:#ffffff!important;}.es-button-border:hover {\tbackground:#ffffff!important;\tborder-style:solid solid solid solid!important;\tborder-color:#3d5ca3 #3d5ca3 #3d5ca3 #3d5ca3!important;}[data-ogsb] .es-button {\tborder-width:0!important;\tpadding:15px 20px 15px 20px!important;}@media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120%!important } h1 { font-size:20px!important; text-align:center } h2 { font-size:16px!important; text-align:left } h3 { font-size:20px!important; text-align:center } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:20px!important } h2 a { text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:16px!important } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:10px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:16px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:12px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class=\"gmail-fix\"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:block!important } a.es-button, button.es-button { font-size:14px!important; display:block!important; border-left-width:0px!important; border-right-width:0px!important } .es-btn-fw { border-width:10px 0px!important; text-align:center!important } .es-adaptive table, .es-btn-fw, .es-btn-fw-brdr, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } }</style></head>\n" +
                "<body style=\"width:100%;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0\"><div class=\"es-wrapper-color\" style=\"background-color:#FAFAFA\"> <!--[if gte mso 9]><v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\"> <v:fill type=\"tile\" color=\"#fafafa\"></v:fill> </v:background><![endif]--><table class=\"es-wrapper\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top\"><tr style=\"border-collapse:collapse\"><td valign=\"top\" style=\"padding:0;Margin:0\"><table class=\"es-content\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%\"><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0;background-color:#fafafa\" bgcolor=\"#fafafa\" align=\"center\"><table class=\"es-content-body\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#ffffff;width:600px\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#ffffff\" align=\"center\"><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0;padding-left:20px;padding-right:20px;padding-top:40px;background-color:transparent\" bgcolor=\"transparent\" align=\"left\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td valign=\"top\" align=\"center\" style=\"padding:0;Margin:0;width:560px\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" style=\"mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px\"><tr style=\"border-collapse:collapse\"><td style=\"padding:0;Margin:0;padding-top:5px;padding-bottom:5px;font-size:0px\" align=\"center\"><img src=\"https://github.com/Cocherane/Imagenpractica3/blob/main/index_img.png?raw=true\" alt style=\"display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic\" width=\"560\"></td>\n" +
                "</tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-top:15px;padding-bottom:15px\"><span style=\"font-family:-apple-system, blinkmacsystemfont, 'segoe ui', roboto, helvetica, arial, sans-serif, 'apple color emoji', 'segoe ui emoji', 'segoe ui symbol';font-size:18px\"><strong>Has solicitado restablecer la contraseña<br>Ya falta poco para usar Ferntickets</strong></span></td></tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">Se ha restablecido la contraseña de la cuenta </p></td>\n" +
                "</tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-right:35px;padding-left:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">"+ email.toUpperCase() +"<br></p></td></tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"padding:0;Margin:0;padding-top:25px;padding-left:40px;padding-right:40px\"><p style=\"Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:24px;color:#666666;font-size:16px\">Para ingresar a tu cuenta usa esta clave temporal al hacer login, recuerda cambiar la contraseña.<br><br><strong>TU CONTRASEÑA TEMPORAL</strong><br><br></p></td>\n" +
                "</tr><tr style=\"border-collapse:collapse\"><td align=\"center\" style=\"Margin:0;padding-left:10px;padding-right:10px;padding-top:40px;padding-bottom:40px\"><span class=\"es-button-border\" style=\"border-style:solid;border-color:#3D5CA3;background:#FFFFFF;border-width:2px;display:inline-block;border-radius:10px;width:auto\"><a href=\"https://github.com/Cocherane/practicaObligatoria3.git\" class=\"es-button\" target=\"_blank\" style=\"mso-style-priority:100 !important;text-decoration:none;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;color:#3D5CA3;font-size:20px;border-style:solid;border-color:#FFFFFF;border-width:15px 20px 15px 20px;display:inline-block;background:#FFFFFF;border-radius:10px;font-family:arial, 'helvetica neue', helvetica, sans-serif;font-weight:bold;font-style:normal;line-height:17px;width:auto;text-align:center\">" + password + "</a></span></td></tr></table></td></tr></table></td></tr></table></td></tr></table></td>\n" +
                "</tr></table></div></body></html>";
    }

    public static String messageTelegramIncidenciaAdmin(Incidencia incidencia, Usuario usuario ){
        return "<b>SE CREO UNA INCIDENCIA</b><pre>Creada por: "+usuario.getNombre().toUpperCase() +" "
                +usuario.getApel() +"</pre>"+
                "<pre>Comentarios: " + incidencia.getDescripcion() + "</pre>" +
                "<pre>Prioridad: " + incidencia.getPrioridad()  + "</pre>" ;
    }

    public static String messageTelegramIncidenciaTecnico(Incidencia incidencia, Usuario usuario ){
        return "<b>UNA INCIDENCIA ASIGNADA A USTED</b><pre>Creada por: "+usuario.getNombre().toUpperCase() +" "
                +usuario.getApel().toUpperCase() +"</pre>"+
                "<pre>Comentarios: " + incidencia.getDescripcion() + "</pre>" +
                "<pre>Fecha de creacion: " + incidencia.getFechaInicio().toString() + "</pre>" +
                "<pre>Prioridad: " + incidencia.getPrioridad() + "</pre>" ;

    }
}
