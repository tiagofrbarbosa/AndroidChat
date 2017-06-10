package tech.infofun.androidchat.service;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import tech.infofun.androidchat.activity.MainActivity;
import tech.infofun.androidchat.model.Message;

/**
 * Created by tfbarbosa on 09/06/17.
 */
public class ChatService {

    private MainActivity activity;

    public ChatService(MainActivity activity){
        this.activity = activity;
    }

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

    public void listen(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://192.168.0.26:8080/polling").openConnection();
                    httpConnection.setRequestMethod("GET");
                    httpConnection.setRequestProperty("Accept","application/json");

                    httpConnection.connect();
                    Scanner scanner = new Scanner(httpConnection.getInputStream());

                    StringBuilder builder = new StringBuilder();

                    while(scanner.hasNextLine()){
                        builder.append(scanner.nextLine());
                    }

                    String json = builder.toString();

                    JSONObject jsonObject = new JSONObject(json);

                    final Message m = new Message(jsonObject.getInt("id"), jsonObject.getString("text"));

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.insertInList(m);
                        }
                    });

                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
