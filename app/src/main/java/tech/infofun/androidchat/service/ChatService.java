package tech.infofun.androidchat.service;

import org.json.JSONStringer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 09/06/17.
 */
public class ChatService {

    public void send(final Message m){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String text = m.getText();

                try {

                    HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://192.168.0.26:8080/polling").openConnection();
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("content-type","application/json");

                    JSONStringer json = new JSONStringer()
                            .object()
                            .key("text")
                            .value(text)
                            .key("id")
                            .value(m.getId()).endObject();

                    OutputStream out = httpConnection.getOutputStream();
                    PrintStream ps = new PrintStream(out);

                    ps.println(json.toString());
                    httpConnection.connect();
                    httpConnection.getInputStream();

                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
