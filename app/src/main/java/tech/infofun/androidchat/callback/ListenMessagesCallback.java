package tech.infofun.androidchat.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.infofun.androidchat.activity.MainActivity;
import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 11/06/17.
 */
public class ListenMessagesCallback implements Callback<Message> {

    private MainActivity activity;

    public ListenMessagesCallback(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<Message> call, Response<Message> response) {

        if(response.isSuccessful()) {
            Message m = response.body();
            activity.insertInList(m);
        }
    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {
        activity.listenMessage();
    }
}
