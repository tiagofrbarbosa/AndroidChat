package tech.infofun.androidchat.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.infofun.androidchat.R;
import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 24/05/17.
 */
public class MessageAdapter extends BaseAdapter{

    private List<Message> messages;
    private Activity activity;
    private String clientId;

    @BindView(R.id.tv_text)
    TextView text;

    @BindView(R.id.iv_avatar_message)
    ImageView avatar;

    @Inject
    Picasso picasso;

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

        ButterKnife.bind(this, row);

        Message message = getItem(position);

        String message_id = message.getId();

        picasso.with(activity)
                .load("https://api.adorable.io/avatars/285/" + message_id + ".png")
                .into(avatar);

        if(!clientId.equals(message.getId())){
            row.setBackgroundColor(Color.CYAN);
        }

        text.setText(message.getText());

        return row;
    }
}
