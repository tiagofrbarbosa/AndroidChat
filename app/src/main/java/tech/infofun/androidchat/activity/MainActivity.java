package tech.infofun.androidchat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView messageList = (ListView) findViewById(R.id.lv_message);
        List<Message> messages = Arrays.asList(new Message(1, "Hello World!"), new Message(2, "Hi"));
        MessageAdapter adapter = new MessageAdapter(clientId, messages, this);
        messageList.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.ed_text);

        button = (Button) findViewById(R.id.bt_send);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChatService().send(new Message(clientId, editText.getText().toString()));
            }
        });
    }
}
