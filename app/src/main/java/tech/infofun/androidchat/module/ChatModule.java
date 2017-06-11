package tech.infofun.androidchat.module;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.infofun.androidchat.service.ChatService;

/**
 * Created by tfbarbosa on 11/06/17.
 */

@Module
public class ChatModule{

    @Provides
    public ChatService getChatService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.26:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatService chatService = retrofit.create(ChatService.class);
        return chatService;
    }
}
