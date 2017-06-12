package tech.infofun.androidchat.callback;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.infofun.androidchat.activity.MainActivity;
import tech.infofun.androidchat.event.MessageEvent;
import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 11/06/17.
 */
public class ListenMessagesCallback implements Callback<Message> {

    private Context context;
    private EventBus eventBus;

    public ListenMessagesCallback(EventBus eventBus, Context context){
        this.context = context;
        this.eventBus = eventBus;
    }

    @Override
    public void onResponse(Call<Message> call, Response<Message> response) {

        if(response.isSuccessful()) {
            Message m = response.body();

            eventBus.post(new MessageEvent(m));
        }
    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {
        eventBus.post(new FailureEvent());
    }
}
