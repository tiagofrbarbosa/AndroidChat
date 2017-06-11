package tech.infofun.androidchat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import tech.infofun.androidchat.callback.ListenMessagesCallback;
import tech.infofun.androidchat.callback.SendMessageCallback;
import tech.infofun.androidchat.component.ChatComponent;
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

    private List<Message> messages;

    @Inject
    ChatService chatService;

    private ChatComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        messages = new ArrayList<>();
        MessageAdapter adapter = new MessageAdapter(String.valueOf(clientId), messages, this);
        messageList.setAdapter(adapter);

        Call<Message> call =  chatService.listen();
        call.enqueue(new ListenMessagesCallback(this));
    }

    @OnClick(R.id.bt_send)
    public void sendMessage(){
        chatService.send(new Message(String.valueOf(clientId), editText.getText().toString())).enqueue(new SendMessageCallback());
    }

    public void insertInList(Message m){

        messages.add(m);

        MessageAdapter adapter = new MessageAdapter(String.valueOf(clientId), messages, this);

        messageList.setAdapter(adapter);

        listenMessage();
    }

    public void listenMessage(){
        Call<Message> call = chatService.listen();
        call.enqueue(new ListenMessagesCallback(this));
    }


}
