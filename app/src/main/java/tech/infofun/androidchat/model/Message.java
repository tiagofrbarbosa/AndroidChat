package tech.infofun.androidchat.model;

/**
 * Created by tfbarbosa on 24/05/17.
 */
public class Message {

    private String text;
    private int id;

    public Message(int id, String text){
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
