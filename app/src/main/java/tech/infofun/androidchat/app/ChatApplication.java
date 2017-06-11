package tech.infofun.androidchat.app;

import android.app.Application;

import tech.infofun.androidchat.component.ChatComponent;
import tech.infofun.androidchat.component.DaggerChatComponent;

/**
 * Created by tfbarbosa on 11/06/17.
 */
public class ChatApplication extends Application{
    private ChatComponent component;

    @Override
    public void onCreate(){
        super.onCreate();

        component = DaggerChatComponent.builder().build();
    }

    public ChatComponent getComponent(){
        return component;
    }
}
