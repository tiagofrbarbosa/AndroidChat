package tech.infofun.androidchat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import tech.infofun.androidchat.R;
import tech.infofun.androidchat.adapter.MessageAdapter;
import tech.infofun.androidchat.model.Message;

public class MainActivity extends AppCompatActivity {

    private int clientId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView messageList = (ListView) findViewById(R.id.lv_message);
        List<Message> messages = Arrays.asList(new Message(1, "Hello World!"), new Message(2, "Hi"));
        MessageAdapter adapter = new MessageAdapter(clientId, messages, this);
        messageList.setAdapter(adapter);
    }
}
