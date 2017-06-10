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

import tech.infofun.androidchat.R;
import tech.infofun.androidchat.adapter.MessageAdapter;
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
        MessageAdapter adapter = new MessageAdapter(clientId, messages, this);
        messageList.setAdapter(adapter);

        chatService = new ChatService(this);
        chatService.listen();

        editText = (EditText) findViewById(R.id.ed_text);

        button = (Button) findViewById(R.id.bt_send);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChatService(MainActivity.this).send(new Message(clientId, editText.getText().toString()));
            }
        });
    }

    public void insertInList(Message m){

        messages.add(m);

        MessageAdapter adapter = new MessageAdapter(clientId, messages, this);

        messageList.setAdapter(adapter);

        chatService.listen();
    }
}
