package tech.infofun.androidchat.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 09/06/17.
 */
public interface ChatService {

    @POST("polling")
    Call<Void> send(@Body Message m);

    @GET("polling")
    Call<Message> listen();
}
