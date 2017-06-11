package tech.infofun.androidchat.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tech.infofun.androidchat.R;
import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 24/05/17.
 */
public class MessageAdapter extends BaseAdapter{

    private List<Message> messages;
    private Activity activity;
    private String clientId;

    public MessageAdapter(String clientId, List<Message> messages, Activity activity){
        this.clientId = clientId;
        this.messages = messages;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = activity.getLayoutInflater().inflate(R.layout.messages, parent, false);

        TextView text = (TextView) row.findViewById(R.id.tv_text);

        Message message = getItem(position);

        if(!clientId.equals(message.getId())){
            row.setBackgroundColor(Color.CYAN);
        }

        text.setText(message.getText());

        return row;
    }
}
