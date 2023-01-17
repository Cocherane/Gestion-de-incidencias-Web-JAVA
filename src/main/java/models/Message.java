package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Comparable{

    private int id;

    private User emitter;

    private User receptor;

    private String content;

    private Date dateSent;

    private Date dateRead;

    private boolean isDeleteEmitter;

    private boolean isDeleteReceptor;


    public Message(int id, User emitter, User receptor, String content ) {
        this.id = id;
        this.emitter = emitter;
        this.receptor = receptor;
        this.content = content;
        this.dateSent = new Date();
        this.dateRead = null;
        this.isDeleteEmitter = false;
        this.isDeleteReceptor = false;
    }

    public Message( Message message ) {
        this.id = message.getId();
        this.emitter = message.getEmitter();
        this.receptor = message.getReceptor();
        this.content = message.getContent();
        this.dateSent = message.getDateSent();
        this.dateRead = message.getDateRead();
        this.isDeleteEmitter = message.isDeleteEmitter();
        this.isDeleteReceptor = message.isDeleteReceptor();
    }

    public Message(int id, User emitter, User receptor, String content, Date dateSent, Date dateRead, boolean isDeleteEmitter, boolean isDeleteReceptor) {
        this.id = id;
        this.emitter = emitter;
        this.receptor = receptor;
        this.content = content;
        this.dateSent = dateSent;
        this.dateRead = dateRead;
        this.isDeleteEmitter = isDeleteEmitter;
        this.isDeleteReceptor = isDeleteReceptor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getEmitter() {
        return emitter;
    }

    public void setEmitter(User emitter) {
        this.emitter = emitter;
    }

    public User getReceptor() {
        return receptor;
    }

    public void setReceptor(User receptor) {
        this.receptor = receptor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Date getDateRead() {
        return dateRead;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public boolean isDeleteEmitter() {
        return isDeleteEmitter;
    }

    public void setDeleteEmitter(boolean deleteEmitter) {
        isDeleteEmitter = deleteEmitter;
    }

    public boolean isDeleteReceptor() {
        return isDeleteReceptor;
    }

    public void setDeleteReceptor(boolean deleteReceptor) {
        isDeleteReceptor = deleteReceptor;
    }

    /* ver si esta leido el mensaje*/
    public boolean isReadMessage(){
        return this.dateRead != null;
    }

    /* set un mensaje leido*/
    public void setMessageRead(){
        this.dateRead = new Date();
    }

    /* set delete emitter*/
    public void setDeleteEmitter(){
        this.isDeleteEmitter = true;
    }

    /* set delete receptor*/
    public void setDeleteReceptor(){
        this.isDeleteReceptor = true;
    }

    public String dateFormatterSent( ){
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        return dateFormat.format( dateSent );
    }

    public String dateFormatterRead( ){
        SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        return dateFormat.format( dateRead );
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
