package tech.infofun.androidchat.callback;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tfbarbosa on 11/06/17.
 */
public class SendMessageCallback implements retrofit2.Callback<Void> {
    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {

    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {

    }
}
