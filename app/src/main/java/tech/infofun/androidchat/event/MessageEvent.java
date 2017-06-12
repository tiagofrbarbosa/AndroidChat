package tech.infofun.androidchat.event;

import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 11/06/17.
 */
public class MessageEvent {

    public Message message;

    public MessageEvent(Message message){
        this.message = message;
    }
}
