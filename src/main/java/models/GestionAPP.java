package models;

import Comunications.Comunications;
import DAO.*;
import DataPersistence.DataRecovery;
import ExcelData.WriteExcel;
import FrontEnd.Menus;

import java.io.*;
import java.util.*;

/**
 * <h2>La clase <b>GestionApp</b> es el controlador de la aplicacion</h2>.
 * Contempla las solicitudes de la aplicacion y controla cada manejo con los dados de la APP
 * <ul>
 * <li>Inserción de registros</li>
 * <li>Borrado y Modificación de registros</li>
 * </ul>
 *
 * @author Gil Carlos
 * @author Ariel Cocherane
 * @version v5.2022
 */
public class GestionAPP {
    //Atributos
    //private ArrayList<Usuario> usuarios;
    //private ArrayList<Tecnico> tecnicos;
    private ArrayList<Admin> admins;
    private DataRecovery dataRecord;
    private Comunications comunications;

    // poner tu codigo de telegram para que te llegue las notificaciones a tu celular
    final private int TELEGRAM_ID_SEND;

    // Conexion de la BBD
    DAOManager daoManager;
    DAOIncidenciaSQL daoIncidenciaSQL;
    DAOUsuariosSql daoUsuariosSql;
    DAOTecnicosSql daoTecnicosSql;
    DAOAdminSql daoAdminSql;

    DAOMessageSql daoMessageSql;

    private String ipPublic;

    //Constructor
    /**
     * Es el controlador de init la aplicacion
     * se inicia desde el main de la aplicacion
     *
     */
    public GestionAPP( ) {
        //this.usuarios = new ArrayList<>();
        //this.tecnicos = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.dataRecord = new DataRecovery( getPathDataConfig() );
        this.TELEGRAM_ID_SEND = getIdTelegramConfig();
        this.comunications = new Comunications(
                getApiTelegramConfig(),  // toma la API del bot del telegram
                getEmailConfig(),        // toma el email en el cual se envia las notificaciones
                getEmailPasswordConfig() // toma la clave del email que envia las notificaciones
        );

        // Instancias de los DAO Objetos
        this.daoManager = DAOManager.getSinglentonInstance();
        this.daoIncidenciaSQL = new DAOIncidenciaSQL( daoManager );
        this.daoUsuariosSql = new DAOUsuariosSql( daoManager );
        this.daoTecnicosSql = new DAOTecnicosSql( daoManager );
        this.daoAdminSql = new DAOAdminSql( daoManager );
        this.daoMessageSql = new DAOMessageSql( daoManager );


        this.ipPublic = "192.168.0.11";




    }
    //Getters y Setters

    /**
     * Getter Usuarios
     */
    public ArrayList<UsuarioDataClass> getUsuarios() {
        return daoUsuariosSql.readAllUsuarios();
    }

    /**
     * Getter Tecnicos
     */
    public ArrayList<TecnicoDataClass> getTecnicos() {
        return daoTecnicosSql.readAllTecnicos();
    }

    /**
     * Getter Admin
     */
    public ArrayList<Admin> getAdmins() {
        return daoAdminSql.readAllAdmins();
    }



    /**
     * Setter Admin
     */
    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    /* busca user by email all user*/
    public Object buscaUserAllByEmail(String email){

        Usuario usuario = daoUsuariosSql.readByEmail( email );
        if ( usuario != null ) return usuario;

        Tecnico tecnico = daoTecnicosSql.readByEmail( email );
        if (tecnico != null) return tecnico;
        else return null;

    }


    /**
     * Inserta usuarios en la Array de usuarios con control de introducir un usuario
     * @see GestionAPP#buscaUsuario(String)
     * @see String#isBlank()
     * @see Comunications#enviarMail(String, String, String)
     * @throws RuntimeException
     * @author Gil Carlos & Ariel Cocherane
     * @return boolean insertaUsuario true = usuario insertado correctamente.
     */
    public boolean insertaUsuario(String nombre, String apel, String clave, String email)throws RuntimeException{
        // controla que el usuario no inserte un valor en Blank y que el usuario este registrado un usuario con este email
        // comprueba que la clave sea de más de 4 caracteres alfa numeric.
        if ( daoTecnicosSql.readByEmail(email.toLowerCase()) != null || daoUsuariosSql.readByEmail(email.toLowerCase()) != null   || daoAdminSql.readByEmail(email.toLowerCase()) != null || nombre.isBlank() || apel.isBlank() || clave.isBlank() || clave.length() < 4 || !email.contains("@"))
            return false;

        // si no lso espacios estan rellenados y el usuario no existe y envia el token al email del cliente

        //Creamos el usuario
        Usuario user = new Usuario(generaIdUser(), nombre, apel, clave, email.toLowerCase().trim() );

        /*¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN DISCO!!!!!!!!!!!!!!*/
        // TODO: 13/05/2022 Insert Usuario
        daoUsuariosSql.insert(user);

        comunications.enviarMail(
                email.toLowerCase().trim()
                , Menus.sentTokenRegister(  nombre, apel, buscaUsuario(email.toLowerCase()).getToken()  )
                , "Activacion de tu token digital"
        );
        return true;

    }

    /**
     *Inserta tecnicos en la Array de tecnicos.
     *
     *@author Gil Carlos & Ariel Cocherane
     *@return boolean insertaTecnico true = tecnico insertado correctamente.
     */
    public boolean insertaTecnico(String nombre, String apel, String clave, String email){

        // controla que el usuario no inserte un valor en Blank y que el usuario este registrado un tecnico con este email
        // comprueba que la clave sea de más de 4 caracteres alfa numeric.
        if ( daoTecnicosSql.readByEmail(email.toLowerCase()) != null || daoUsuariosSql.readByEmail(email.toLowerCase()) != null || daoAdminSql.readByEmail( email.toLowerCase()) != null || nombre.isBlank() || apel.isBlank() || clave.isBlank() || !email.contains("@"))
            return false;

        // si no lso espacios estan rellenados y el usuario no existe
        //Generamos el tecnico
        Tecnico techTemp = new Tecnico( generaIdUser(),nombre,apel,clave,email.toLowerCase() );

        /*¡¡¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!!!!!*/
        // TODO: 13/05/2022 Insert tecnico

        return daoTecnicosSql.insert(techTemp);

    }

    /**
     * Se encarga de insertar una incidencia ligada a un usuario,
     * si el email que se introduce no está en los usuarios envia un Throws NULLPointerException
     * Que debe ser controlada en el main
     * Debe contener una descripcion diferente que una cadena vacia
     * La prioridad debe estar entre 1 [MIN] y 10 [MAX].
     *
     * @author Ariel Cocherane
     * @see GestionAPP#buscaUsuario(String)
     * @see GestionAPP#generaIdIncidencia()
     * @see Usuario#insertaIncidencia(Incidencia)
     * @see Usuario#getId()
     * @throws NullPointerException si el email no concuerda con un usuario.
     * @param email  El email del usuario que quiere abrir una incidencia.
     * @param descripcion  El mensaje del problema a solucionar por parte del usuario.
     * @param prioridad  El estado de prioridad de la incidencia dada por el usuario.
     * @return Retorna un Boolean True si la incidencia se ha añadido, False si ha ocurrido un problema.
     */
    public boolean insertaIncidencia( String email, String descripcion, int prioridad )throws NullPointerException{

        // comprobar el usuario por email y mantener lo en la funcion cargado
        Usuario tempUsuario = buscaUsuario(email);
        // control de los datos introducidos por el usuario.
        if ( tempUsuario != null && !descripcion.equals("")  && prioridad > 0 && prioridad <= 10 ){

            // sacamos la generacion de la idIncidencia para enviar la notificacion a administrado
            int idIncidencia = generaIdIncidencia();

            /*¡¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!!!!*/
            // TODO: 13/05/2022 [PROBAR]Insert incidencia
            daoIncidenciaSQL.inserta(
                    new Incidencia(
                            idIncidencia,   // genera la ID de la incidencia.
                            descripcion,            // Ingresa la descripcion de la incidencia.
                            prioridad,              // Ingresa la prioridad de la incidencia de 1 - 10  [1-MIN][10-MAX].
                            tempUsuario.getId() )  // Introduce la ID del usuario llamando el metodo buscaUsuario(email).
            );

            // enviamos la notificacion al administrador mediante TELEGRAM
            sentMessageTelegramAdminIncidentCreated( idIncidencia );
            // registramos la creacion de la incidenia en el log
            newIncidenciaLog( idIncidencia );
            // Si la creacion de la incidencia por parte del usuario ha sido exitosa.
            return true;
        }else
            // ha ocurrido un Error al insertar la incidencia.
            return false;
    }

    /**
     *Inserta Message.
     *
     *@author  Ariel Cocherane
     *@return boolean insertaMessage true = message insertado correctamente.
     */
    public boolean insertaMessage(User emitter, User receptor, String content){

        // controla que el usuario no inserte un valor en Blank y que el usuario este registrado un tecnico con este email
        if ( content.isBlank())
            return false;

        // create a message for a user
        return daoMessageSql.inserta(
                new Message(
                        generaIdMessage(),
                        emitter,
                        receptor,
                        content
                )
        );

    }


    /**
     * Genera una collection de todas las incidencias abiertas que se encuentran en los usuario
     * como en los técnicos
     *
     * @author Ariel Cocherane
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario)
     * @return ArrayList<IncidenciaDataClass>
     */

    public ArrayList<IncidenciaDataClass> buscarTodasIncidenciasAbiertas(){
        ArrayList<IncidenciaDataClass> arrayListIncideciaOut = new ArrayList<>();

        /*// busca las incidenias en los Usuarios
        for (Usuario usuario:
             this.usuarios) {
            if ( !usuario.getIncidencias().isEmpty() ){
                for (Incidencia incidencia: usuario.getIncidencias()) {
                    if ( !incidencia.estaResuelta() ) arrayListIncideciaOut.add( new IncidenciaDataClass(incidencia, usuario) );
                }
            }
        }
        // Busca las incidencias en los tecnicos
        for (Tecnico tecnico:
             this.tecnicos) {
            if ( !tecnico.getIncidencias().isEmpty() ){
                for (Incidencia incidencia: tecnico.getIncidencias()) {
                    if ( !incidencia.estaResuelta() ) arrayListIncideciaOut.add( new IncidenciaDataClass( incidencia, buscarUsuariobyID( incidencia.getIdUsuario() ), tecnico) );
                }
            }
        }*/
        // busca las incidencias abierta en la Base de Datos
        for (var incidencia : daoIncidenciaSQL.readTodasIncidenciasAbiertas() ) {
            arrayListIncideciaOut.add(
                    new IncidenciaDataClass(                                // Se crea la instancia del Objeto <IncidenciaDataClass>
                            incidencia,                                     // Se introduce el parametro de Objeto<Incidencia>
                            buscarUsuariobyID( incidencia.getIdUsuario() ),                   // Se introduce el Objeto<Usuario>
                            (incidencia.getIdTecnico() != 0)? buscaTecnico( incidencia.getIdTecnico() ) : null       // Se introduce el parametro el Objeto<Tecnico>
                    ));
        }

        System.out.println("hola estoy corriendo aqui");
        // se ordena la lista de más antigua a más reciente
        Collections.sort( arrayListIncideciaOut );
        return arrayListIncideciaOut;

    }

    /**
     * Se encarga de contar todas las incidencias abiertas
     *
     * @author Ariel Cocherane
     * @see Tecnico#incidecniasAbiertas()
     * @see Usuario#incidenciasAbiertas()
     * @return int numero de incidencias abiertas.
     */
    public int incidenciasAbiertas( ){
        /*int contadorIncidencias = 0;
        // contar incidencias en los usuarios
        for (Usuario usuario:
             this.usuarios) {
             contadorIncidencias += usuario.incidenciasAbiertas();

            }
        // constar incidencias
        for (Tecnico tecnico:
             this.tecnicos) {
            contadorIncidencias += tecnico.incidecniasAbiertas();
        }

        return contadorIncidencias;*/

        return daoIncidenciaSQL.readNumIncidenciasAbiertas();
    }

    /**
     * Se encarga de sacar la prioridad media de las incidencias globales
     *
     * @author Ariel Cocherane
     * @return prioridad media float [1-10]
     */
    public float prioridadMediaApp( ){
        /*float  sumPrioridad = 0f;
        float countIncidecnia = 0f;
        // busca en los usuarios
        for ( var usuario :  usuarios ){
            if ( !usuario.getIncidencias().isEmpty() ){
                sumPrioridad += usuario.prioridadMediaUsuario();
                countIncidecnia++;
            }
        }
        // busca en los tecnicos
        for (var tecnicos : tecnicos) {
            if ( !tecnicos.getIncidencias().isEmpty() ){
                sumPrioridad += tecnicos.prioridadMediaTecnica();
                countIncidecnia++;
            }
        }
        if ( sumPrioridad == 0 ) return 0.0f;
        return sumPrioridad;*/

        return daoIncidenciaSQL.readNumPrioridadmediaIncidecnias();
    }

    /**
     * Se encarga de sacar la prioridad media de las incidencias de las incidencias del tecnico
     *
     * @author Ariel Cocherane
     * @return prioridad media float [1-10]
     */
    public float prioridadMediaIncidenciaByTecnico( int idTecnico ){
        return daoIncidenciaSQL.readNumPrioridadmediaIncidecniasByTecnico( idTecnico );
    }

    /**
     * Devuelve el numero de incidencias que estan asignadas y aun no se ha solucionado
     *
     *@author Gil Carlos
     *@see Incidencia#estaResuelta()
     *@return int incidenciasAbiertasAsignadas
     */
    public int incidenciasAbiertasAsignadas(){
        /*int incidenciasAbiertasyAsignadas = 0;
        for (Tecnico t:
                tecnicos) {
            for (Incidencia i:
                    t.getIncidencias()) {
                if (!i.estaResuelta()) incidenciasAbiertasyAsignadas++;
            }

        }
        return incidenciasAbiertasyAsignadas;*/
        return daoIncidenciaSQL.readNumIncidenciasAbiertasAsignadas();
    }

    /**
     * Se encarga de contar las incidencias que han sido resueltas por los técnicos
     *
     * @author Ariel Cocherane
     * @see Tecnico#incidecniasCerradas()
     * @return int numero de incidencias cerradas (Resueltas)
     */
    public int incidenciasCerradas( ){
        /*int contadorIncidenciasCerradas = 0;
        for (Tecnico tecnico:
             this.tecnicos) {
            contadorIncidenciasCerradas += tecnico.incidecniasCerradas();
        }
        return contadorIncidenciasCerradas;*/
        return daoIncidenciaSQL.readNumIncidenciasCerradas();
    }

    /**
     * Genera una collecion de todas las incidencias asignadas al tecnico con esa id
     * @deprecated
     * @author Gil Carlos Hermoso
     * @see Incidencia#estaResuelta()
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario)
     * @return ArrayList<IncidenciaDataClass>
     */
    public ArrayList<IncidenciaDataClass> buscaIncidenciasAsignadas(int idTecnico){
        ArrayList<IncidenciaDataClass> arrayListIncideciaAsignada = new ArrayList<>();
        /*IncidenciaDataClass incidenciaDataClassTemp;
        for (Tecnico tecnico:
                this.tecnicos) {
            if (idTecnico == tecnico.getId()){
                for (Incidencia incidencia:
                        tecnico.getIncidencias()) {
                    if (!incidencia.estaResuelta()){
                        incidenciaDataClassTemp = new IncidenciaDataClass(incidencia, buscarUsuariobyID(incidencia.getIdUsuario()), tecnico);
                        arrayListIncideciaAsignada.add(incidenciaDataClassTemp);
                    }
                }
            }
        }*/
        return arrayListIncideciaAsignada;

    }

    /**
     *Genera una id que no se repite entre todas las incidencias del sistema.
     *
     *@author Gil Carlos
     *@return int generaIdIncidencia.
     */
    private int generaIdIncidencia(){

        int idNuevaIncidencia;
        do {
            idNuevaIncidencia = (int) (Math.random() * 100000);
//            for (Usuario usuario :
//                    this.usuarios) {
//                for (Incidencia incidencia :
//                        usuario.getIncidencias()) {
//                    if (idNuevaIncidencia == incidencia.getId()) {
//                        idRepetido = true;
//                        break;
//                    }
//                }
//            }
//            for (Tecnico tecnico :
//                    this.tecnicos) {
//                for (Incidencia incidencia :
//                        tecnico.getIncidencias()) {
//                    if (idNuevaIncidencia == incidencia.getId()) {
//                        idRepetido = true;
//                        break;
//                    }
//                }
//            }
        }while ( daoIncidenciaSQL.existeID( idNuevaIncidencia ) );
        return idNuevaIncidencia;
    }

    /**
     *Genera una id que no se repite entro todos los usuarios del sistema.
     *
     *@author Gil Carlos
     *@return int generaIdUser id unica para todos los usuarios que se registren.
     */
    private int generaIdUser(){
        int id;
        do {

            id = (int) (Math.random() * 10000 + 1);

        }while (daoUsuariosSql.readById(id) != null && daoTecnicosSql.readById(id) != null);
        return id;
    }

    /**
     *Genera una id para los mensajes.
     *
     *@author Ariel COcherane
     *@return int generaIdMessage.
     */
    private int generaIdMessage(){
        int id;
        do {

            id = (int) (Math.random() * 10000 + 1);

        }while ( daoMessageSql.existeID(id) );
        return id;
    }

    /**
     * Se encarga de encontrar el objeto de un usuario dado el id del usuario
     * retorna null si no hay coincidencia.
     *
     * @author Ariel Cocherane
     * @see Usuario#getId()
     * @param idUsuario Integer la id del tecnico a buscar
     * @return Usuario Devuelve el objeto Usuario que coincide con la id
     */

    public Usuario buscarUsuariobyID(int idUsuario) {
        return daoUsuariosSql.readById(idUsuario);
    }

    /**
     *Se encarga de encontrar el objeto de un usuario dado el email del usuario
     * retorna null si no hay coincidencia.
     *
     *@author Gil Carlos
     *@param emailUsuario String del email del usaurio
     *@see Usuario#getEmail()
     *@return Usuario Devuelve el objeto Usuario que coincide con el email
     */
    public Usuario buscaUsuario (String emailUsuario){
        return daoUsuariosSql.readByEmail(emailUsuario);
    }
    

    /**
     * Busca una incidencia por la ID, en los usuarios y tecnicos
     *
     * @author Ariel Cocherane
     * @see Usuario#buscaIncidenciabyId(int)
     * @see Incidencia#Incidencia(Incidencia)
     * @param idIncidencia número que identifica la incidencia.
     * @return Incidencia NOTA: El Objeto no está referenciada, es una copia.
     */
    public Incidencia buscarIncidencia( int idIncidencia ){
        /*// Buscar la incidencia por los Usuarios
        for (Usuario usuario:
             this.usuarios) {
            if ( !usuario.getIncidencias().isEmpty() && usuario.buscaIncidenciabyId(idIncidencia) != null ){
                return usuario.buscaIncidenciabyId(idIncidencia);
            }
        }
        // Buscar la incidencia por los Tecnicos
        for (Tecnico tecnico:
             this.tecnicos) {
            if ( !tecnico.getIncidencias().isEmpty() && tecnico.buscaIncidenciabyId( idIncidencia ) != null){
                return  tecnico.buscaIncidenciabyId(idIncidencia);
            }
        }

         return null;*/
        return daoIncidenciaSQL.readIncidenciaById( idIncidencia );
    }

    /**
     *Se encarga de encontrar el objeto de un tecnico dado el id del tecnico
     * retorna null si no hay coincidencia.
     *
     *@author Gil Carlos Hermoso
     *@param idTecnico Integer la id del tecnico a buscar
     *@see Tecnico
     *@return Tecnico Devuelve el objeto Tecnico que coincide con la id
     */
    public   Tecnico buscaTecnico(int idTecnico){
        return daoTecnicosSql.readById(idTecnico);
    }

    /**
     * Se encarga de encontrar el objeto de un admin dado el id del admin
     * retorna null si no hay coincidencia.
     *
     * @author Gil Carlos Hermoso
     * @param idAdmin Integer la id del admin a buscar
     * @see Admin
     * @return Admin Devuelve el objeto Admin que coincide con la id
     */
    public Admin buscarAdminbyId(int idAdmin){
        return daoAdminSql.readById( idAdmin );
    }

    /**
     * Se encarga de encontrar el usuario que creo la incidencia dando el ID de la incidencia
     *
     * @author Ariel Cocherane
     * @see GestionAPP#buscarUsuariobyID(int)
     * @see Incidencia#getIdUsuario()
     * @param idIncidencia Es el Id de la incidencia
     * @return Usuario, null retorna el objeto Usuario al que pertenece la incidencia
     */
    public Usuario buscarUsuariobyIncidencia( int idIncidencia ){
        // buscando en los usuario
        /*for (Usuario usuario:
             this.usuarios) {
            if ( !usuario.getIncidencias().isEmpty() ){
                for (Incidencia incidencia:
                        usuario.getIncidencias()) {
                    if ( incidencia.getId() == idIncidencia ) return usuario;
                }
            }
        }
        // buscando en los tecnicos
        for (Tecnico tecnico:
             this.tecnicos) {
            if ( !tecnico.getIncidencias().isEmpty() ){
                for (Incidencia incidencia:
                        tecnico.getIncidencias()) {
                    if ( incidencia.getId() == idIncidencia ) return buscarUsuariobyID( incidencia.getIdUsuario() );
                }
            }
        }
        return null;*/
        return daoUsuariosSql.readUsuarioByIdIncidencia( idIncidencia );
    }


    /**
     * Se encarga de encontrar los mensajes de los usur
     *
     * @author Ariel Cocherane
     * @see DAOMessageSql#readMessagebyUser(int)
     * @see GestionAPP#buscaUserById(int)
     * @param idUser Es el Id del user
     * @return una lista de mensaje del user, null retorna si no existe el usuario o no tiene
     */

    public ArrayList<Message> buscaMessageByUserId( int idUser ){
        ArrayList<Message> mensajeUser = new ArrayList<>();

        // busca el mensaje del usuario
        mensajeUser = daoMessageSql.readMessagebyUser( idUser );
        if ( mensajeUser == null)
            return mensajeUser;

        // sirve para rellenar el usuario desde el id
        for (Message message : mensajeUser ) {
            message.setEmitter(
                    (User) buscaUserById( message.getEmitter().getId() )
            );

            message.setReceptor(
                    (User) buscaUserById( message.getReceptor().getId() )
            );
        }

        return mensajeUser;

    }

    /**
     * Se encarga de borrar un mensage de un usuario
     *
     * @author Ariel Cocherane
     * @see DAOMessageSql#deleteMessage(int, int)
     * @param idMessage Es el Id del message
     * @param idUser Es el Id del user
     * @return retorna a true si ha sido borrado o un false si no existe el mensaje
     */

    public boolean deleteMessageByIdMessage( int idMessage, int idUser ){

        return daoMessageSql.deleteMessage( idMessage, idUser );

    }

    /**
     * Se encarga de marcar que un message ha sido leido por el usuario
     *
     * @author Ariel Cocherane
     * @param idMessage El id del message
     * @return retorna un booleano si la operacion ha sido satisfactoria.
     */

    public boolean markMessageOpenByidMessage( int idMessage ){
        return daoMessageSql.updateSetReadMessage( idMessage );
    }



    /**
     * Se encarga de cerrar la incidencia por el tecnico, el mismo necesita describir el evento para la
     * resolucion de la misma.
     *
     * @author Ariel Cocherane.
     * @see GestionAPP#buscaTecnico(int).
     * @throws  NullPointerException Al usar buscarTecnico(int) si no existe el tecnico retorna un Null.
     * @param idTecnico la id del tecnico.
     * @param idIncidencia la id de la incidencia.
     * @param mensajeCierre El mensaje del tecnico describiendo la forma que fue resuelta la incidencia.
     * @return boolean true si la operacion fue realizada, falso si hay un problema con el cierre.
     */
    public boolean cierraIncidencia(int idTecnico, int idIncidencia, String mensajeCierre ) throws NullPointerException{
        /*// adjunto un throw porque puede return el busca tecnico un null
        var tecnico = buscaTecnico(idTecnico);
        if ( tecnico != null && tecnico.cierraIncidencia( idIncidencia, mensajeCierre ) ){
            // registramos en el log la incidencia cerrada
            cierraIncidenciaLog( idTecnico, idIncidencia );
        *//*¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!*//*
            // TODO: 13/05/2022 Tecnico aqui tienes que modificar la incluision si quieres trabajar con un objeto de tipo tecnico
            return true;
        } else return false;*/

        return !mensajeCierre.isBlank() && daoIncidenciaSQL.updateCierreIncidencia(idIncidencia, mensajeCierre);
    }


    /**
     * Se encarga de encontrar todas las incidencias por la expresion de un termino
     *
     * @author Ariel Cocherane
     * @param termino - El término clave para encontrar coincidencias en la description.
     * @return Todas las incidencias que coincidan con el término buscado [NOTA: no referenciadas].
     */
    public ArrayList<IncidenciaDataClass> buscarIncidenciasbyTerm( String termino ){
        ArrayList<IncidenciaDataClass> incidenciasbyTerm = new ArrayList<>();
        /*// Incidencias by termino dentro de los usuarios sin asignar aun
        for (Usuario usuario: this.usuarios) {
            for (Incidencia incidencia: usuario.buscaIncidecniasbyTerm( termino )) {
                incidenciasbyTerm.add(
                        new IncidenciaDataClass(
                                incidencia,
                                usuario
                        ) );
            }
        }
        // busco las incidecnias en el tecnico
        for (Tecnico tecnico : tecnicos) {
            for (Incidencia incidencia : tecnico.buscaIncidecniasbyTerm( termino )) {
                incidenciasbyTerm.add(
                        new IncidenciaDataClass(
                                incidencia,
                                buscarUsuariobyIncidencia( incidencia.getId() ),
                                tecnico
                        ) );
            }
        }*/

        // se encarga de buscar la incidecnias por un termino ne concreto
        for (var incidencia : daoIncidenciaSQL.readIncidenciasByTerm( termino ) ) {
            incidenciasbyTerm.add(
                    new IncidenciaDataClass(                                // Se crea la instancia del Objeto <IncidenciaDataClass>
                            incidencia,                                     // Se introduce el parametro de Objeto<Incidencia>
                            buscarUsuariobyID( incidencia.getIdUsuario() ),                   // Se introduce el Objeto<Usuario>
                            (incidencia.getIdTecnico() != 0)? buscaTecnico( incidencia.getIdTecnico() ) : null       // Se introduce el parametro el Objeto<Tecnico>
                    ));
        }

        // se ordena la lista de más antigua a más reciente
        Collections.sort(incidenciasbyTerm);
        return incidenciasbyTerm;

    }

    /**
     * Se encarga de extraer todas las incidencias hechas por el usuario.
     *
     * @author Ariel Cocherane
     * @see GestionAPP#buscaTecnico(int)
     * @see Tecnico#getIncidencias()
     * @see Incidencia#estaResuelta()
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario, Tecnico)
     * @see GestionAPP#buscaTecnico(int)
     * @see GestionAPP#buscarUsuariobyID(int)
     * @throws NullPointerException Si la Id del usuario no se encuentra o el Tecnico no tiene incidencias cerradas.
     * @param idTecnico La ID del tecnico, para buscar la(s) incidencia(s), si hay incidencias cerradas por este tecnico.
     * @return Todas las incidencias que han sido resueltos por el tecnico [NOTA: no referenciadas].
     */
    public ArrayList<IncidenciaDataClass> buscarIncidenciaCerradabyTecnico( int idTecnico ) throws NullPointerException{
        ArrayList<IncidenciaDataClass> incidenciasCerradasByTecnico = new ArrayList<>();
        /*for (Incidencia incidencia:
             buscaTecnico( idTecnico ).getIncidencias()) {
            if ( incidencia.estaResuelta() ){
                incidenciasCerradasByTecnico.add(                   // se anade al Arraylist<IncidenciaDataClass> todas la incidencias cerradas by tecnico.
                        new IncidenciaDataClass(                    // Se crea el objeto IncidenciaDataClass todas las incidencias cerradas by tecnico.
                                new Incidencia( incidencia ),       // Se hace una copia de las incidencias que esten resueltas.
                                buscarUsuariobyID( incidencia.getIdUsuario()), // se introduce el objeto Usuario.
                                buscaTecnico( idTecnico)   // se introduce el objeto Tecnico.
                        ));
            }
        }*/
        // incidencias cerradas por el por un tecnico PERSISTENCIA EN BASE DE DATO
        for (Incidencia incidencia : daoIncidenciaSQL.readIncidenciasCerradasbyTecnico( idTecnico ) ) {
            incidenciasCerradasByTecnico.add(                               // se anade al Arraylist<IncidenciaDataClass> todas la incidencias cerradas by tecnico.
                    // TODO: 24/05/2022 Comprobar si los nombres de metodos del dao de gil son inguales
                    new IncidenciaDataClass(                                // Se crea el objeto IncidenciaDataClass todas las incidencias cerradas by tecnico.
                              incidencia                                    // Se hace una copia de las incidencias que esten resueltas.
                            , buscarUsuariobyID( incidencia.getIdUsuario()) // se introduce el objeto Usuario.
                            , buscaTecnico( idTecnico)                      // se introduce el objeto Tecnico.
                    )
            );
        }

        // se ordena la lista de más antigua a más reciente
        Collections.sort(incidenciasCerradasByTecnico);
        return incidenciasCerradasByTecnico;
    }

    /**
     * Se encarga de buscar las incidencias que estar sin asignar a un tecnico, recuerda que solo pueden estar sin asignar
     * en el usuario, Las Incidencias colectadas no están referenciadas.
     *
     * @author Ariel Cocherane
     * @see Usuario#incidenciasAbiertas()
     * @see Usuario#getIncidencias()
     * @see Incidencia#Incidencia(Incidencia)
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario)
     * @return ArrayList<IncidenciaDataClass> de todas las incidencias que están sin asignar en usuario.
     */
    public ArrayList<IncidenciaDataClass> buscarIncidenciasSinAsignar( ){
        ArrayList<IncidenciaDataClass> incidenciasSinAsignar = new ArrayList<>();
        /*// buscando incidencias abiertas en los Usuarios
        for (Usuario usuario: this.usuarios) {
            if ( usuario.incidenciasAbiertas() > 0 ){
                for (Incidencia incidencia: usuario.getIncidencias()) {
                    incidenciasSinAsignar.add( new IncidenciaDataClass( new Incidencia( incidencia ), usuario ) );
                }
            }
        }*/

        /* base de datos connection*/
        for (var incidencia : daoIncidenciaSQL.readIncidenciaSinAsignar() ) {
            incidenciasSinAsignar.add(
                    new IncidenciaDataClass(                                // Se crea la instancia del Objeto <IncidenciaDataClass>
                            incidencia,                                     // Se introduce el parametro de Objeto<Incidencia>
                            buscarUsuariobyID( incidencia.getIdUsuario() ),                   // Se introduce el Objeto<Usuario>
                            (incidencia.getIdTecnico() != 0)? buscaTecnico( incidencia.getIdTecnico() ) : null       // Se introduce el parametro el Objeto<Tecnico>
                    ));
        }
        ;

        //Ordena El ArrayList<IncidenciaDataClass> de la más antigua a la nueva
        Collections.sort( incidenciasSinAsignar );
        return incidenciasSinAsignar;
    }

    /**
     *DESCRIPTION
     *
     * @author Ariel Cocherane
     * @see Tecnico#getIncidencias()
     * @see Incidencia#getIdUsuario()
     * @see Incidencia#estaResuelta()
     * @throws NullPointerException Se produce un ERROR cuando se ingresa una idUsuario Que no este dentro del programa
     * @param idUsuario La ID del usuario a buscar
     * @return El número de incidencias cerradas creadas por un tecnico en particular.
     */
    public int incidenciasCerradasbyUsuario( int idUsuario )throws NullPointerException{
        /*int countIncideniciasCerradasbyUsuario = 0;
        // busca las incidencias ya resueltas creadas por un usuario en todos los tecnicos
        for (Tecnico tecnico: this.tecnicos) {
            for (Incidencia incidencia:
                 tecnico.getIncidencias()) {
                // compurba cada incidencia de los tecnico que sea creada por UN USUARIO en concreto y este ya resuelta
                if (  incidencia.getIdUsuario() == idUsuario && incidencia.estaResuelta() ) countIncideniciasCerradasbyUsuario++;
            }
        }
        return countIncideniciasCerradasbyUsuario;*/

        return daoIncidenciaSQL.readNumIncidenciasCerradasByUser( idUsuario );
    }

    /**
     * Busca todas las incidencia cerredas en los Objetos<Tecnico> creadas por un usuario, y retorna una collection ordenada
     * de más vieja a más reciente
     *
     * @author Ariel Cocherane
     * @see Tecnico#getIncidencias()
     * @see Incidencia#estaResuelta()
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario, Tecnico)
     * @see GestionAPP#buscarUsuariobyID(int)
     * @param idUsuario El ID del Usuario
     * @return ArrayList<IncidenciaDataClass> Con todas las incidencias creadas por un usuario y que esten cerradas
     */
    public ArrayList<IncidenciaDataClass> buscarIncidenciasCerradasbyUsuario( int idUsuario ){
        // Inicializar ArrayList<IncidenciaDataClass>
        ArrayList<IncidenciaDataClass> incidenciasCerradasByUsuario = new ArrayList<>();
        /*// Busca los las incidencias cerradas en los tecnicos que es donde pueden estar cerradas.
        for (Tecnico tecnico:
             this.tecnicos) {
            // busca las incidencias de cada tecnico.
            for (Incidencia incidencia:
                    tecnico.getIncidencias()) {
                // Comprueba que la incidencia sea del Usuario y que esté cerrada
                if ( incidencia.getIdUsuario() == idUsuario && incidencia.estaResuelta() ){
                    incidenciasCerradasByUsuario.add(
                            new IncidenciaDataClass(  // Se crea la istancia del Objeto <IncidenciaDataClass>
                                    incidencia,       // Se introduce el parametro de Objeto<Incidencia>
                                    buscarUsuariobyID(idUsuario), // Se introduce el Objeto<Usuario>
                                    tecnico           // Se introduce el parametro el Objeto<Tecnico>
                            ));
                }
            }
        }*/

        // buscamos las incidencia cerradas por un usuario
        for (var incidencia : daoIncidenciaSQL.readIncidenciasCerradasbyUsuario( idUsuario )) {
            incidenciasCerradasByUsuario.add(
                    new IncidenciaDataClass(                                // Se crea la instancia del Objeto <IncidenciaDataClass>
                            incidencia,                                     // Se introduce el parametro de Objeto<Incidencia>
                            buscarUsuariobyID(idUsuario),                   // Se introduce el Objeto<Usuario>
                            (incidencia.getIdTecnico() != 0)? buscaTecnico( incidencia.getIdTecnico() ) : null       // Se introduce el parametro el Objeto<Tecnico>
                    ));
        }

        // Se ordena de más vieja a más nueva.
        Collections.sort(incidenciasCerradasByUsuario);
        return incidenciasCerradasByUsuario;
    }

    /**
     * Busca a un User a traves de su email
     *
     * @deprecated
     * @param email Un email de algún User
     * @return Object Si encuentra el objeto lo retorna.
     * @author Gil Carlos Hermoso
     */
    private Object buscaUser(String email) {
        /*for (Usuario u :
                usuarios) {
            if (u.getEmail().equals(email.toLowerCase())) return u;
        }
        for (Tecnico t :
                tecnicos) {
            if (t.getEmail().equals(email.toLowerCase())) return t;
        }
        for (Admin a :
                admins) {
            if (a.getEmail().equals(email.toLowerCase())) return a;
        }*/
        return null;
    }

    /**
     * Busca una incidencia por su id y return true si la encientra asignads a algun tecnico
     *
     * @return boolean estaIncidenciaAsignada Si esta asignada ya esa incidencia
     * @author Gil Carlos Hermoso
     * @see Tecnico#getIncidencias()
     * @see Incidencia#Incidencia(Incidencia)
     */
    public boolean estaIncidenciaAsignada(int idIncidencia) {
        return daoIncidenciaSQL.readIsIncidenciasAsignadas( idIncidencia );
    }

    /**
     * Asigna la incidencia a traves de la id del tecnico que se la queremos asignar y de la id de la incidencia
     * 
     * @author Gil Carlos Hermoso & Ariel Cocherane
     * @param idTecnico    La id del tecnico
     * @param idIncidencia La id de la incidencia
     * @see GestionAPP#buscarIncidencia(int) 
     * @see GestionAPP#buscaTecnico(int) 
     * @see Tecnico#asignaIncidencia(Incidencia) 
     * @see GestionAPP#buscarUsuariobyIncidencia(int) 
     * @see Usuario#deleteIncidencia(int) 
     * @return boolean asignaIncidencia si ha encontrado el tecnico y la incidencia y la ha conseguido asignar con exito
     */
    public boolean asignaIncidencia(int idTecnico, int idIncidencia) {
        // bolean para conprobar la asignacion fue exitosa
        boolean okAsignacion = false;
        /*// procedemos a anadir la incidencia al tecnico
        Tecnico tecnico = buscaTecnico( idTecnico );
        Incidencia incidecnia = buscarIncidencia( idIncidencia );

        // se comprueba que las la inidecnia y el tecnico existan
        if (incidecnia != null && tecnico != null) {
            // asigna la incidencia al tecnico
            tecnico.asignaIncidencia( incidecnia );
            // borra la incidencia del usuario
            buscarUsuariobyIncidencia( idIncidencia).deleteIncidencia( idIncidencia);

            Incidencia tempTes = buscarIncidencia( idIncidencia);

            *//*¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!!!!!*//*
            okAsignacion = daoIncidenciaSQL.updateAsignaIncidencia( idTecnico, idIncidencia );
            //Sobreescribimos los dartos del usuario
            // TODO: 13/05/2022 usuario 
            //Sobreescribimos los datos del tecnico
            // TODO: 13/05/2022 Tecnico 

            // procedemos a enviar al tecnico una notificacion de que se a asignado una incidecnia
            sentMessageTelegramTecnicoIncidenciaAsignada( idIncidencia );

            // procedemos a registrar la incidencia asignada al log
            asignaIncidenciaLog( idTecnico, idIncidencia);


            return okAsignacion;
        }*/


        /*¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!!!!!*/
        okAsignacion = daoIncidenciaSQL.updateAsignaIncidencia( idTecnico, idIncidencia );
        //Sobreescribimos los dartos del usuario
        // TODO: 13/05/2022 usuario
        //Sobreescribimos los datos del tecnico
        // TODO: 13/05/2022 Tecnico

        // procedemos a enviar al tecnico una notificacion de que se a asignado una incidecnia
        sentMessageTelegramTecnicoIncidenciaAsignada( idIncidencia );

        // procedemos a registrar la incidencia asignada al log
        asignaIncidenciaLog( idTecnico, idIncidencia);

        return okAsignacion;
    }

    /**
     * Dice cuantas incidencias tiene asignadas un tecnico por su id
     *
     * @param idTecnico La id del tecnico
     * @return int incidenciasAsignadas numero de incidencias asignadas a ese tecnico
     * @author Gil Carlos Hermoso
     * @see Tecnico
     * @see Incidencia
     */
    public int incidenciasAsignadasByTecnico(int idTecnico) {
        return daoIncidenciaSQL.readNumIncidenciasAbiertasByTecnico( idTecnico );
    }

    /**
     * Busca las Incidecnias abiertas dentro de uN Tecnico
     *
     * @param idTecnico El ID del Tecnico
     * @return ArrayList<IncidenciaDataClass> Con todas las incidecnias abiertas por el tecnico
     * @author Gil Carlos Hermoso
     * @see Tecnico#getIncidencias()
     * @see Incidencia#estaResuelta()
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario, Tecnico)
     * @see GestionAPP#buscarUsuariobyID(int)
     */
    public ArrayList<IncidenciaDataClass> buscaIncidenciasAbiertasbyTecnico(int idTecnico) {
        ArrayList<IncidenciaDataClass> incidenciasAbiertasTecnico = new ArrayList<>();
        /*for (Tecnico t : tecnicos) {
            if (t.getId() == idTecnico) {
                for (Incidencia i : t.getIncidencias()) {
                    if (!i.estaResuelta()) {
                        incidenciasAbiertasTecnico.add(new IncidenciaDataClass(i, buscarUsuariobyID(i.getIdUsuario()), t));
                    }
                }
            }
        }*/

        // busca en la base de datos daoIncidenciaSQl las incidecnia Abiertas asignadas al tecnico
        for (Incidencia incidencia : daoIncidenciaSQL.readIncidenciasAbiertasbyTecnico( idTecnico )) {
            incidenciasAbiertasTecnico.add(                                 // se anade al Arraylist<IncidenciaDataClass> todas la incidencias cerradas by tecnico.
                    // TODO: 24/05/2022 Comprobar si los nombres de metodos del dao de gil son inguales
                    new IncidenciaDataClass(                                // Se crea el objeto IncidenciaDataClass todas las incidencias cerradas by tecnico.
                            incidencia                                      // Se hace una copia de las incidencias que esten resueltas.
                            , buscarUsuariobyID( incidencia.getIdUsuario()) // se introduce el objeto Usuario.
                            , buscaTecnico( idTecnico )                     // se introduce el objeto Tecnico.
                    )
            );
        }


        if ( !incidenciasAbiertasTecnico.isEmpty() )
            WriteExcel.writeExcelIncidencias( incidenciasAbiertasTecnico, getPathDataConfig() );

        return incidenciasAbiertasTecnico;
    }


    /**
     * Busca las Incidecnias cerradas en todos los tecnicos
     *
     * @return ArrayList<IncidenciaDataClass> Con todas las incidecnias cerradas
     * @author Gil Carlos Hermoso
     * @see Tecnico#getIncidencias()
     * @see Incidencia#estaResuelta()
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario, Tecnico)
     * @see GestionAPP#buscarUsuariobyID(int)
     */
    public ArrayList<IncidenciaDataClass> buscaIncidenciasCerradas() {
        ArrayList<IncidenciaDataClass> incidenciasAbiertasTecnico = new ArrayList<>();
        /*for (Tecnico t : tecnicos) {
            for (Incidencia i : t.getIncidencias()) {
                if ( i.estaResuelta() ) incidenciasAbiertasTecnico.add(new IncidenciaDataClass(i, buscarUsuariobyID(i.getIdUsuario()), t));
            }
        }*/

        // buscamos las incidencia cerradas por un usuario
        for (var incidencia : daoIncidenciaSQL.readIncidenciasAllCerradas( )) {
            incidenciasAbiertasTecnico.add(
                    new IncidenciaDataClass(                                // Se crea la instancia del Objeto <IncidenciaDataClass>
                            incidencia,                                     // Se introduce el parametro de Objeto<Incidencia>
                            buscarUsuariobyID( incidencia.getIdUsuario() ),                   // Se introduce el Objeto<Usuario>
                            (incidencia.getIdTecnico() != 0)? buscaTecnico( incidencia.getIdTecnico() ) : null       // Se introduce el parametro el Objeto<Tecnico>
                    ));
        }

        // Se ordena de más vieja a más nueva.
        Collections.sort(incidenciasAbiertasTecnico);
        return incidenciasAbiertasTecnico;

    }


    /**
     * Se encarga de comprobar de que los User se loguen en la aplicacion
     * comprueba todos los usuarios y si hay coincidencia regresa el objeto
     * copia
     *
     * @author Ariel Cocherane
     * @see Usuario#login(String, String)
     * @see Tecnico#login(String, String)
     * @see Admin#login(String, String)
     * @param email compone el email del user
     * @param clave compone de la clave para aceder del user
     * @return El usuario OBJECT con el que se esta loguiando la aplicacion
     */
    public Object login( String email, String clave ){
        // TODO: 29/06/2022  
        Object user;
        // User
        if ( daoUsuariosSql.readByEmailClave(email, clave) != null) {
            user = daoUsuariosSql.readByEmailClave(email, clave);
            // save session log
            logInSessionLog( user );
            // set las session record
            recordLastSession( ((Usuario) user).getId() );
            return user;
            
        // Tecnico    
        } else if (daoTecnicosSql.readByEmailClave(email, clave) != null){

            user = daoTecnicosSql.readByEmailClave(email, clave);
            // save session log
            logInSessionLog( user );
            // set las session record
            recordLastSession( ((Tecnico) user).getId() );
            return user;

            
        // Admins    
        } else if ( daoAdminSql.readByEmailClave( email, clave) != null) {

            user = daoAdminSql.readByEmailClave( email, clave );
            // save session log
            logInSessionLog( user );
            // set las session record
            recordLastSession( ((Admin) user).getId() );
            return user;

        // si no hay coincidencia en los User se envia un objeto NULL
        } else{
            return null;}
    }

    /**
     * Comprueba si el Usuario esta ya ha validado el Token
     *
     * @author Ariel Cocherane
     * @see Usuario#isTokenValidated()
     * @param usuarioObjet es el Usuario que queremos comprobar para comprobar el token
     * @return retorna un boolean con el acierto o falsedad de que ha validado el Token
     */
    public boolean isValidadoToken(Object usuarioObjet ){
        return ((Usuario)usuarioObjet).isTokenValidated();
    }

    /**
     * Comprueba si el token introducido por el usuario que ya ha validado las credenciales de email y login
     * concuerda con el que fue enviado a su email
     * @author Ariel Cocherane
     * @see Usuario#tokenCheckUsuario(int)
     * @see Usuario#tokenacept()
     * @param usuarioObjet es el Usuario que queremos comprobar para comprobar el token
     * @return retorna un boolean con el acierto o falsedad de que ha validado el Token
     */
    public boolean validandoTokentoObject(Object usuarioObjet, int token ){
        if ( ((Usuario)usuarioObjet).tokenCheckUsuario(token) ){
            // colocamos en el la instancia del usuario que ya ha sido comprobado
            ((Usuario)usuarioObjet).tokenacept();

            /*¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!!!!!*/
            // TODO: 13/05/2022 user PROBAR
            daoUsuariosSql.updateTokenValidado( ((Usuario)usuarioObjet) );

            return true;
        }
        return false;
    }


    /**
     * Se encarga de contar todas las incidencias en el usuario abiertas pero no asignadas
     *
     * @author Ariel Cocherane
     * @see GestionAPP#buscarUsuariobyID(int)
     * @see Usuario#getIncidencias()
     * @see ArrayList#size()
     * @param idUsuario
     * @return int numero de incidencias en el usuario no asignadas
     */
    public int incidenciasNoAsignadasbyUsuario(int idUsuario ){
        return daoIncidenciaSQL.readNumIncidenciasSinAbiertasbyUsuario( idUsuario );
    }

    /**
     * Se encarga de extraer todas las incidencias aun abiertas de creadas por un usuario [Sin o asignadas]
     *
     * @author Ariel Cocherane
     * @see IncidenciaDataClass#IncidenciaDataClass(Incidencia, Usuario)
     * @see Incidencia#Incidencia(Incidencia)
     * @see GestionAPP#buscarUsuariobyID(int)
     * @param idUsuario el identificacion unica del Usuario
     * @return ArrayList<IncidenciaDataClass> con todas las incidencias abiertas por el usuario tanto en el usuario y los tecnicos no cerrada
     */
    public ArrayList<IncidenciaDataClass> buscaIncidenciasAbiertasbyUsuario(int idUsuario){
        // init el array
        ArrayList<IncidenciaDataClass>  incidenciasAbiertasbyUsuario = new ArrayList<>();

        /*// busca las incidencias abiertas en el usuario
        for (var usuario : usuarios) {
            if ( idUsuario == usuario.getId()){
                for (Incidencia incidencia : usuario.getIncidencias()) {
                    incidenciasAbiertasbyUsuario.add(                   // se anade al Arraylist<IncidenciaDataClass> todas incidencias abiertas by Usuario.
                            new IncidenciaDataClass(                    // Se crea el objeto IncidenciaDataClass todas las incidencias abiertas by Usuario.
                                    new Incidencia( incidencia ),       // Se hace una copia de las incidencias que eaten en el Usuario.
                                    buscarUsuariobyID( incidencia.getIdUsuario()) // se introduce el objeto Usuario.
                            ));
                }
            }
        }

        // buscar en los tecnicos las inidecncias abiertas pero no cerradas
        for (Tecnico tecnico: this.tecnicos) {
            // busca las incidencias de cada tecnico.
            for (Incidencia incidencia: tecnico.getIncidencias()) {
                // Comprueba que la incidencia sea del Usuario y que esté cerrada
                if ( incidencia.getIdUsuario() == idUsuario && !incidencia.estaResuelta() ){
                    incidenciasAbiertasbyUsuario.add(
                            new IncidenciaDataClass(  // Se crea la instancia del Objeto <IncidenciaDataClass>
                                    incidencia,       // Se introduce el parametro de Objeto<Incidencia>
                                    buscarUsuariobyID(idUsuario), // Se introduce el Objeto<Usuario>
                                    tecnico           // Se introduce el parametro el Objeto<Tecnico>
                            ));
                }
            }
        }*/
        // buscamos las incidencia abirtas por un usuario
        for (var incidencia : daoIncidenciaSQL.readIncidenciasAbiertasbyUsuario( idUsuario )) {
            incidenciasAbiertasbyUsuario.add(
                    new IncidenciaDataClass(  // Se crea la instancia del Objeto <IncidenciaDataClass>
                            incidencia,       // Se introduce el parametro de Objeto<Incidencia>
                            buscarUsuariobyID(idUsuario), // Se introduce el Objeto<Usuario>
                            (incidencia.getIdTecnico()!= 0)? buscaTecnico( incidencia.getIdTecnico() ) : null           // Se introduce el parametro el Objeto<Tecnico>
                    ));
        }


        Collections.sort( incidenciasAbiertasbyUsuario);
        return incidenciasAbiertasbyUsuario;
    }


    /**
     * Cuenta las <Incidencia>s de un <Usuarios> que han sido asignadas a los <Tecnicos>
     *
     * @author Ariel Cocherane
     * @see Tecnico#getIncidencias()
     * @see Incidencia#getIdUsuario()
     * @param idUsuario
     * @return int Numero de incidencias de un usuario asignadas
     */
    public int incidenciasAsignadasbyUsuario( int idUsuario ){
        return daoIncidenciaSQL.readNumIncidenciasAbiertasAsignadasByUsuario( idUsuario );
    }


    /**
     * Se encarga de extraer la ID del Objeto loguiado <objectUser>
     *
     * @author Ariel Cocherane
     * @see instanceof
     * @param object es el objeto que se usa una vez es loguiado el User con exito
     * @return Id[Usuario][Tecnico][Admin]
     */
    public int objectGetId(Object object ){
        if ( object instanceof Usuario ) return ((Usuario) object).getId();
        if ( object instanceof Tecnico) return ((Tecnico) object).getId();
        if ( object instanceof Admin) return ((Admin) object).getId();
        return -1;
    }

    /**
     * Se encarga de cambiar la clave pasando el <objectUser> y el nuevo password
     *
     * @author Ariel Cocherane
     * @see Usuario#cambiaClave(String)
     * @see Tecnico#cambiaClave(String)
     * @see Comunications#enviarMail(String, String, String)
     * @param object
     * @param newPassword
     * @return boolean si la operacion a=ha sido exitosa
     */
    public boolean setNewPasswordObject( Object object, String newPassword )throws RuntimeException{
        if ( object instanceof Usuario ) {
            ((Usuario) object).cambiaClave( newPassword );
            /*¡¡¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!!*/
            // TODO: 13/05/2022 user


            // enviar el email con el nuevo token
            comunications.enviarMail(
                    ((Usuario) object).getEmail().toLowerCase().trim()
                    , Menus.sentTokenRegister(
                            ((Usuario) object).getNombre()
                            , ((Usuario) object).getApel()
                            , ((Usuario) object).getToken() )
                    , "Re-activacion de tu token digital"
            );

            return daoUsuariosSql.updateClave((Usuario) object, newPassword);
        }
        if ( object instanceof Tecnico) {
            //((Tecnico) object).cambiaClave(newPassword);
            /*¡¡¡¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!!!!!!*/
            // TODO: 13/05/2022 tecnicos
            return daoTecnicosSql.updateClave((Tecnico) object, newPassword);
        }
        if ( object instanceof Admin ){
            return daoAdminSql.updateClave( ((Admin) object).getId() , newPassword);
        }
        return false;

    }


    /**
     * Comprueba si el email introducido por el usuario es valido
     *
     * @author Ariel Cocherane
     * @see GestionAPP#buscaUsuario(String)
     * @param email
     * @return boolean si ya existe un email
     */
    public boolean isValidoEmailRegistro(String email ){
        return daoTecnicosSql.readByEmail(email.toLowerCase()) != null || daoUsuariosSql.readByEmail(email.toLowerCase()) != null;
    }

    /**
     * Pasa las Incidecnias del Tecnico t1 al Tecnico t2
     *
     * @author Gil Carlos Hermoso && Ariel Cocherane
     * @see Tecnico#asignaIncidencia(Incidencia)
     * @see Tecnico#desAsignarIncidencia(int)
     * @param idIncidencia
     * @param idTecnicoAsignar
     * @param idTecnicoDesAsignar
     * @return boolean si la operacion ha sido realizada o rechazada
     */
    public boolean pasaIncidenciasEntreTecnicos(int idTecnicoDesAsignar, int idTecnicoAsignar, int idIncidencia){
        // busca los objetos a manipular para comprobar q no son null como control
        // incidenia a combiar
        Incidencia tempincidencia = buscarIncidencia( idIncidencia );
        // tecnico a sacar la incidenia
        Tecnico tecnicoDesAsignar = buscaTecnico( idTecnicoDesAsignar );
        // tecnico a asignar la incidenia
        Tecnico tecnicoAsignar = buscaTecnico( idTecnicoAsignar );

        if ( tempincidencia != null && tecnicoAsignar != null && tecnicoDesAsignar != null ){

            // borro la incidenia del tecnico a borrar mediante el metodo de Tecnico#desAsignarIncidencia( idIncidencia )
            // y la signi mediante Tecnico#asignaIncidencia

            /*¡¡¡¡¡¡¡¡¡¡GUARDAMOS EN BD!!!!!!!!!*/
            // TODO: 13/05/2022 Dos tecnicos
            return daoIncidenciaSQL.updateAsignaIncidencia( idTecnicoAsignar, idIncidencia );

        }else
            return false;
    }
    /**
     * Borra el tecnico por su id
     *
     * @author Gil Carlos Hermoso & Ariel Cocherane
     * @param idTecnico
     * @return true si se borra el tecnico correctamente
     */
    public boolean borraTecnico(int idTecnico){
        if ( daoIncidenciaSQL.readNumIncidenciasAbiertasByTecnico( idTecnico ) == 0 && daoIncidenciaSQL.readIncidenciasCerradasbyTecnico( idTecnico ).size() == 0 ){
            // si no tiene ninguna incidecnia abierta ni ninguna cerrada procedemos a borrarlo
            daoTecnicosSql.delete(idTecnico);
            return true;
        }
        return false;
    }


    /**
     * Se encarga de enviar al administrador una notificacion de que an avierto una incidecnia
     *
     * @author Ariel Cocherane
     * @see Comunications#enviarMensajeTelegram(String, int)
     * @see Menus#messageTelegramIncidenciaAdmin(Incidencia, Usuario)
     * @see GestionAPP#buscarIncidencia(int)
     * @see GestionAPP#buscarUsuariobyIncidencia(int)
     * @param idIncident
     */
    public void sentMessageTelegramAdminIncidentCreated( int idIncident ){
        try {
            comunications.enviarMensajeTelegram( Menus.messageTelegramIncidenciaAdmin( buscarIncidencia(idIncident) ,buscarUsuariobyIncidencia( idIncident)),  TELEGRAM_ID_SEND );
        }catch ( IOException e){
            System.out.println("Advertencia!!! Contacta con el administrador!!! Code 1020 envio de notificacion");
        }
    }

    /**
     * Envia una notificacion al usuario con su clave temporal
     *
     * @author Ariel Cocherane
     * @see
     * @param email
     * @param passTemp
     * @return RETURN
     */
    public boolean sentEmailReseatPass( String email, int passTemp ){

        // enviar el email
        return comunications.enviarMail(
                email.toLowerCase(),
                Menus.sentReseatPassword( email, passTemp),
                "Restablecer la contraseña"
        );
    }


    /**
     * Envia una notificacion al tecnico que fue asignada una incidencia a el
     *
     *@author Ariel Cocherane
     * @see Comunications#enviarMensajeTelegram(String, int)
     * @see Menus#messageTelegramIncidenciaTecnico(Incidencia, Usuario)
     * @see GestionAPP#buscarIncidencia(int)
     * @see GestionAPP#buscarUsuariobyIncidencia(int)
     *@param idIncident
     *@return RETURN
     */
    public void sentMessageTelegramTecnicoIncidenciaAsignada( int idIncident ){
        try {
            Incidencia temp = buscarIncidencia( idIncident );
            comunications.enviarMensajeTelegram( Menus.messageTelegramIncidenciaTecnico( buscarIncidencia(idIncident) ,buscarUsuariobyIncidencia( idIncident)),  TELEGRAM_ID_SEND );
        }catch ( Exception e){
            System.out.println("Advertencia!!! Contacta con el administrador!!! Code 1030 envio de notificacion");
        }
    }


    public boolean sentEmailAllIncidenciaTecnico( int idTecnico ){

        // se obtiene todas las incidencias abiertas en este usuario
        ArrayList<IncidenciaDataClass> incTecAbiertas = buscaIncidenciasAbiertasbyTecnico( idTecnico );

        // comprobamos que el tecnico tenga incidencias asignadas
        if ( incTecAbiertas.isEmpty() ) return false;

        // inicializamos el array bi-dimensional
        Object[][] datatype = new Object[6][ incTecAbiertas.size() ];


        // trasformamos la incidencia a un array bi-dimensional pra la insertion de tuples de incidencias
        int tuple = 0;
        for (var incidencia : incTecAbiertas ) {
            tuple++;
            // rellena el array
            for (int i = 0; i < 6; i++) {

            }
        }


        return true;

    }

    /* se encarga de buscar los usuarios con una id si se encuentra retorna un objeto*/
    public Object buscaUserById(int idUser ){

        var usuario = buscarUsuariobyID( idUser );
        var tecnico = buscaTecnico( idUser );
        var admin = buscarAdminbyId( idUser );

        if ( usuario != null ) return ((Object) usuario);
        if ( tecnico != null ) return ((Object) tecnico);
        if ( admin != null ) return ((Object) admin);

        return null;
    }




    public void logOutSessionLog(Object user){
        // el mensaje del loguin q se va ha registrar
        String logRecordUser = null;
        // comprobamos de que tipo de user es
        if ( user instanceof Usuario ){
            logRecordUser = String.format("%s,%s %s,%s,%s,%s,%s"
                    , "Cierre de Sesion"
                    , ((Usuario) user).getNombre()
                    , ((Usuario) user).getApel()
                    , ((Usuario) user).getEmail()
                    , "Usuario"
                    , new Date()
                    , "NaN"
            );

        }else if ( user instanceof Tecnico ){
            logRecordUser = String.format("%s,%s %s,%s,%s,%s,%s"
                    , "Cierre de Sesion"
                    , ((Tecnico) user).getNombre()
                    , ((Tecnico) user).getApel()
                    , ((Tecnico) user).getEmail()
                    , "Tecnico"
                    , new Date()
                    , "NaN"
            );

        }else if ( user instanceof Admin ){
            logRecordUser = String.format("%s,%s %s,%s,%s,%s,%s"
                    , "Cierre de Sesion"
                    , ((Admin) user).getNombre()
                    , ((Admin) user).getApel()
                    , ((Admin) user).getEmail()
                    , "Admin"
                    , new Date()
                    , "NaN"
            );
        }
        // registramos el moguin del user
        try {
            dataRecord.writeLogs( logRecordUser );
        }catch ( IOException e){
            // TODO: 11/04/2022 manejar el error
            System.out.print(e);
        }
    }

    public void logInSessionLog(Object user){
        // el mensaje del loguin q se va ha registrar
        String logRecordUser = null;
        // comprobamos de que tipo de user es
        if ( user instanceof Usuario ){
            logRecordUser = String.format("%s,%s %s,%s,%s,%s,%s"
                    , "Inicio de Sesion"
                    , ((Usuario) user).getNombre()
                    , ((Usuario) user).getApel()
                    , ((Usuario) user).getEmail()
                    , "Usuario"
                    , new Date()
                    , "NaN"
                    );

        }else if ( user instanceof Tecnico ){
            logRecordUser = String.format("%s,%s %s,%s,%s,%s,%s"
                    , "Inicio de Sesion"
                    , ((Tecnico) user).getNombre()
                    , ((Tecnico) user).getApel()
                    , ((Tecnico) user).getEmail()
                    , "Tecnico"
                    , new Date()
                    , "NaN"
            );

        }else if ( user instanceof Admin ){
            logRecordUser = String.format("%s,%s %s,%s,%s,%s,%s"
                    , "Inicio de Sesion"
                    , ((Admin) user).getNombre()
                    , ((Admin) user).getApel()
                    , ((Admin) user).getEmail()
                    , "Admin"
                    , new Date()
                    , "NaN"
            );

        }
        // registramos el moguin del user
        try {
            dataRecord.writeLogs( logRecordUser );
        }catch ( IOException e){
            // TODO: 11/04/2022 manejar el error
            System.out.print(e);
        }
    }

    public void newIncidenciaLog( int idincidenica ){
        String logRecordUser = "";
        Usuario user = buscarUsuariobyIncidencia(idincidenica);
        try {
            dataRecord.writeLogs(
                    logRecordUser = String.format("%s,%s %s,%s,%s,%s,%s"
                            , "Nueva incidencia"
                            , user.getNombre()
                            , user.getApel()
                            , user.getEmail()
                            , "Usuario"
                            , new Date()
                            , idincidenica
                    )
            );
        }catch (IOException e){
            System.out.print("");
        }
    }

    public void asignaIncidenciaLog( int idTecnico, int idIncidenica ){

        Tecnico tecnico = buscaTecnico( idTecnico );
        try {
            dataRecord.writeLogs(
                     String.format("%s,%s %s,%s,%s,%s,%s"
                            , "Asignacion incidencia"
                            , tecnico.getNombre()
                            , tecnico.getApel()
                            , tecnico.getEmail()
                            , "Tecnico"
                            , new Date()
                            , idIncidenica
                    )
            );
        }catch (IOException e){
            System.out.print("");
        }
    }

    public void cierraIncidenciaLog(int idTecnico, int idIncidencia ){

        Tecnico tecnico = buscaTecnico( idTecnico );
        try {
            dataRecord.writeLogs(
                    String.format("%s,%s %s,%s,%s,%s,%s"
                            , "Incidencia cerrada"
                            , tecnico.getNombre()
                            , tecnico.getApel()
                            , tecnico.getEmail()
                            , "Tecnico"
                            , new Date()
                            , idIncidencia
                    )
            );
        }catch (IOException e){
            System.out.print("");
        }

    }

    public void recordLastSession( int idUser ){
        try {
            dataRecord.writeLastLogin( idUser );
        }catch (IOException e) {
            System.out.print("");;
        }

    }

    public Date readLastSession( int idUser ){
        Date dateLastSession = null;
        try {
            dateLastSession = dataRecord.readLastLogin(  idUser );
        }catch (IOException e){
            System.out.print("");
        }
        return dateLastSession;
    }

    public int getIdTelegramConfig( ){
        // se llama al metodo para leer los parametros de las configuraciones
        int value = 0;
        try {
            value = Integer.parseInt( dataRecord.getValuesConfig( "idsendtelegram" ));
        } catch (IOException | NumberFormatException e) {
            value = 00000000;
            e.printStackTrace();
        }
        return value;
    }
    /* retorna el email con el que se envia los notificaciones */
    public String getEmailConfig( ){
        // se llama al metodo para leer los parametros de las configuraciones
        String  value = "";
        try {
            value =  dataRecord.getValuesConfig( "emailsender" );
        } catch (IOException e) {
            value = "arielefrain.cocherane@fernando3martos.com";
            e.printStackTrace();
        }
        return value.trim();
    }

    /* retorna el email con el que se envia los notificaciones */
    public String getEmailPasswordConfig( ){
        // se llama al metodo para leer los parametros de las configuraciones
        String  value = "";
        try {
            value =  dataRecord.getValuesConfig( "emailpassword" );
        } catch (IOException e) {
            value = "egkhubxkrystujpr";
            e.printStackTrace();
        }
        return value.trim();
    }

    /* retorna el email con el que se envia los notificaciones */
    public String getApiTelegramConfig( ){
        // se llama al metodo para leer los parametros de las configuraciones
        String  value = "";
        try {
            value =  dataRecord.getValuesConfig( "bottelegram" );
        } catch (IOException e) {
            value = "#### API TELEGRAM #####";
            e.printStackTrace();
        }
        return value.trim();
    }



    public boolean isGuestUserActiveConfig( ){
        // se llama al metodo para leer los parametros de las configuraciones
        boolean value = false;
        try {
            value = Boolean.parseBoolean( dataRecord.getValuesConfig( "userguest" ));
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return value;
    }

    public String getPathDataConfig( )  {
        // path a retornar
        String pathOut = "";
        // abrimos el archivo y comprobamos si existe el archivo de configuracion
        File pathRoot = new File( "C:\\Users\\ariel\\Desktop\\FernanTicket_New_Set\\FernanTickets\\properties.config" );
        try {

            // si esta el archivo leemos el parametro del path si existe se toma como parametro donde se gusdaran los datos de la app
            // o se cargala la ruta de ejecucion de la aplicacion
            FileReader reader = new FileReader( pathRoot );
            // cargamos la instancia de properties
            Properties in = new Properties();
            in.load( reader );

            // se extrae el path root de los datos donde se guardaran
            pathOut = in.getProperty( "pathdata", "C:\\Users\\ariel\\Desktop\\FernanTicket_New_Set\\FernanTickets" );

            // se cierra el flujo
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // se comprueba que el path root es valido si no se asignara el de la ruta donde se ejecuta la aplicacion
        return pathOut;
    }

    /* obtienes todas las configuraciones de la application */
    public Set<Map.Entry<Object, Object>> getAllConfigApp( ) throws IOException {
        return dataRecord.getPropertiesConfig();
    }

    /* obtienes un set.entry con todas las sessiones de las app extrayendo las id pasandolas por objeto user*/
    public HashMap<Object, Date> getLastSession( ) throws IOException {
        // creamos un hashMap para pasar de una idUser al objeto user
        HashMap<Object, Date> userDate = new HashMap<>();

        // interamos para pasar a el hashmap los objetos
        for( Map.Entry<Object, Object>  set : dataRecord.getLastSession() ) {
            var idUser = Integer.parseInt( set.getKey().toString() );
            var numDate = Long.parseLong( set.getValue().toString() );

            // comprobamos que solo añadimos los user que no han sido borrado asi que comprobamos si son null, si lo son
            // marcamos un continue
            Object user = buscaUserById( idUser );
            if ( user != null ) {
                userDate.put(
                        user,
                        new Date( numDate )
                );
            }
        }

        return userDate;
    }

    /* retorna todos lo susuario que tienen almenos una incidencia asignada */
    public ArrayList<TecnicoDataClass>  getTecnicoIncidenciaAsignada( ){

        return new ArrayList<TecnicoDataClass>(
                getTecnicos().stream().filter( st -> st.getIncidenciasAbiertas() > 0 ).toList()
        );
    }


    public void enviarResumenIncidenciaTecnico( int idTecnico, String pathMain ){
        TecnicoDataClass tecnico = getTecnicos().stream().filter( st -> st.getId() == idTecnico ).findFirst().orElse(null);

        String mensaje =String.format( """
                Estimado Sr.(a). %s:
                                
                Tengo el agrado de comunicarme con usted,  tiene %s incidencias asignadas de las cuales son reflejadas en detalle en el fichero de Excel adjuntó.
                Espero que siga manteniendo ese desempeño por su prestigio y también para beneficio de la empresa.
                
                Atentamente
                
                Equipo de Ferntickets
                """, tecnico.getApel(), tecnico.getIncidenciasAbiertas());
        // se crea el excel con el resumen de usus incidencias abiertas
        WriteExcel.writeExcelIncidencias(
                buscaIncidenciasAbiertasbyTecnico( idTecnico ), getPathDataConfig()
        );

        // enviamos el email del tecnico
         comunications.enviarMailAdjunto(
                 tecnico.getEmail().trim(),
                 mensaje,
                 String.format("Resumen de las incidecnias asignadas %s", tecnico.getNombre()+" "+tecnico.getApel() ),
                 pathMain + "\\incidencias.xlsx"
         );


    }

    /* Muestra un resumen del usuario ejemplo Tipo de usuario, la id, nombre, apellido, ultima dia de session  */
    public String[] getResumeUser(Object object){
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

    public String getIpv4red(){
        return ipPublic;
    }



}
