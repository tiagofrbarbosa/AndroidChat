package tech.infofun.androidchat.component;

import dagger.Component;
import tech.infofun.androidchat.activity.MainActivity;
import tech.infofun.androidchat.adapter.MessageAdapter;
import tech.infofun.androidchat.module.ChatModule;

/**
 * Created by tfbarbosa on 11/06/17.
 */

@Component(modules = ChatModule.class)
public interface ChatComponent {

    void inject(MainActivity activity);
    void inject(MessageAdapter adapter);
}
