package tech.infofun.androidchat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.infofun.androidchat.R;
import tech.infofun.androidchat.adapter.MessageAdapter;
import tech.infofun.androidchat.callback.ListenMessagesCallback;
import tech.infofun.androidchat.callback.SendMessageCallback;
import tech.infofun.androidchat.model.Message;
import tech.infofun.androidchat.service.ChatService;

public class MainActivity extends AppCompatActivity {

    private int clientId = 1;
    private EditText editText;
    private Button button;

    private ListView messageList;
    private List<Message> messages;

    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageList = (ListView) findViewById(R.id.lv_message);
        messages = new ArrayList<>();
        MessageAdapter adapter = new MessageAdapter(String.valueOf(clientId), messages, this);
        messageList.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.26:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatService = retrofit.create(ChatService.class);
        Call<Message> call =  chatService.listen();
        call.enqueue(new ListenMessagesCallback(this));

        editText = (EditText) findViewById(R.id.ed_text);

        button = (Button) findViewById(R.id.bt_send);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatService.send(new Message(String.valueOf(clientId), editText.getText().toString())).enqueue(new SendMessageCallback());
            }
        });
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
