package tech.infofun.androidchat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import tech.infofun.androidchat.app.ChatApplication;
import tech.infofun.androidchat.R;
import tech.infofun.androidchat.adapter.MessageAdapter;
import tech.infofun.androidchat.callback.FailureEvent;
import tech.infofun.androidchat.callback.ListenMessagesCallback;
import tech.infofun.androidchat.callback.SendMessageCallback;
import tech.infofun.androidchat.component.ChatComponent;
import tech.infofun.androidchat.event.MessageEvent;
import tech.infofun.androidchat.model.Message;
import tech.infofun.androidchat.service.ChatService;

public class MainActivity extends AppCompatActivity {

    private int clientId = 1;

    @BindView(R.id.ed_text)
    EditText editText;

    @BindView(R.id.bt_send)
    Button button;

    @BindView(R.id.lv_message)
    ListView messageList;

    @BindView(R.id.iv_user_avatar)
    ImageView avatar;

    private List<Message> messages;

    @Inject
    ChatService chatService;

    @Inject
    Picasso picasso;

    @Inject
    EventBus eventBus;

    private ChatComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        picasso.with(this)
                .load("https://api.adorable.io/avatars/285/" + clientId + ".png")
                .into(avatar);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        if(savedInstanceState != null){
            messages = (List<Message>) savedInstanceState.getSerializable("messages");
        }else{
            messages = new ArrayList<>();
        }

        MessageAdapter adapter = new MessageAdapter(String.valueOf(clientId), messages, this);
        messageList.setAdapter(adapter);

        Call<Message> call =  chatService.listen();
        call.enqueue(new ListenMessagesCallback(eventBus, this));

        eventBus.register(this);
    }

    @OnClick(R.id.bt_send)
    public void sendMessage(){
        chatService.send(new Message(String.valueOf(clientId), editText.getText().toString())).enqueue(new SendMessageCallback());

        editText.getText().clear();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Subscribe
    public void insertInList(MessageEvent messageEvent){

        messages.add(messageEvent.message);

        MessageAdapter adapter = new MessageAdapter(String.valueOf(clientId), messages, this);

        messageList.setAdapter(adapter);

    }

    @Subscribe
    public void listenMessage(MessageEvent messageEvent){
        Call<Message> call = chatService.listen();
        call.enqueue(new ListenMessagesCallback(eventBus, this));
    }

    @Subscribe
    public void MessageFailure(FailureEvent event){
        listenMessage(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("messages", (ArrayList<Message>) messages);
    }

    @Override
    protected void onStop(){
        super.onStop();

        eventBus.unregister(this);
    }
}
