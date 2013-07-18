package com.lifeshield.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class WebSocketTestActivity extends Activity {

    private static final String TAG = "WebSocketTestActivity";

    private final WebSocketConnection mConnection = new WebSocketConnection();

    private EditText inputView;
    private TextView outputView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputView = (EditText) findViewById(R.id.input);
        outputView = (TextView) findViewById(R.id.output_window);

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnection.sendTextMessage(inputView.getText().toString());
            }
        });
        start();
    }

    private void start() {

        final String wsuri = "ws://echo.websocket.org";

        try {
            mConnection.connect(wsuri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + wsuri);
                    outputView.setText(outputView.getText().toString() + "\nconnected to " + wsuri);
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, "Got echo: " + payload);
                    outputView.setText(outputView.getText().toString() + "\nGot echo: " + payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connection lost.");
                    outputView.setText(outputView.getText().toString() + "\nConnection lost: " + reason);
                }
            });
        } catch (WebSocketException e) {

            Log.d(TAG, e.toString());
        }
    }
}

