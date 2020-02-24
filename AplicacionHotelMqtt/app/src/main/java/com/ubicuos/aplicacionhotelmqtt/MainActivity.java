package com.ubicuos.aplicacionhotelmqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    TextView mqttText;
    Button borrarButton;
    Context context;
    MqttAndroidClient mqttClient;
    String EXAMPLE_TOPIC = "hotel/raspberryRoom/entrance";
    String EXAMPLE_SERVER = "tcp://broker.hivemq.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mqttText = findViewById(R.id.mqtt);
        borrarButton = findViewById(R.id.borrar);
        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttText.setText("");
            }
        });
        iniciarMqtt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy()
        ;
        if (mqttClient != null) {
            mqttClient.close();
        }
    }

    private void iniciarMqtt() {
        String clientId = MqttClient.generateClientId();
        MqttConnectOptions mqttOptions = new MqttConnectOptions();
        mqttOptions.setAutomaticReconnect(true);
        mqttClient = new MqttAndroidClient(context, EXAMPLE_SERVER, clientId);
        try {
            IMqttToken token = mqttClient.connect(mqttOptions);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    try {
                        mqttClient.subscribe(EXAMPLE_TOPIC, 1);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });
        } catch (MqttException e) {
        }

        MqttCallback mqttCallback = new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                if (topic.equals(EXAMPLE_TOPIC)) {
                    String addText = mqttText.getText() + "topic: ";
                    addText = addText + topic + " mensaje: ";
                    addText = addText + message.toString();
                    addText = addText + "\n";
                    mqttText.setText(addText);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        };
        mqttClient.setCallback(mqttCallback);
    }
}
