package tech.infofun.androidchat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tfbarbosa on 24/05/17.
 */
public class Message {

    @SerializedName("text")
    private String text;

    private String id;

    public Message(String id, String text){
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}
