package com.ubicuos.aplicacionhotelmqtt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ubicuos.aplicacionhotelmqtt.ui.login.LoginActivity;

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
    TextView notificacionM;
    Context context;
    MqttAndroidClient mqttClient;
    NotificationCompat.Builder builder;
    String CHANNEL_ID= "idnotification";
    String TOPIC_MAIN = "hotel/raspberry1/#";
    String TOPIC_RESTAURANT = "hotel/raspberry1/restaurant";
    String TOPIC_BAR = "hotel/raspberry1/bar";
    String EXAMPLE_SERVER = "tcp://broker.hivemq.com";
    Button signOut;
    FirebaseAuth homeFirebaseAuth;
    private FirebaseAuth.AuthStateListener homeStateListenerAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mqttText = findViewById(R.id.mqtt);
        notificacionM = findViewById(R.id.textView2);
        borrarButton = findViewById(R.id.borrar);
        signOut = findViewById(R.id.signout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String dato = extras.getString("lugar");
            notificacionM.setText(dato);
        }

        borrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mqttText.setText("");
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intToLogin);
                finish();
            }
        });
        iniciarMqtt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniciarMqtt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                        mqttClient.subscribe(TOPIC_MAIN, 1);
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
                if (topic.equals(TOPIC_RESTAURANT)) {
                    String lugar = "Restaurante";
                    String addText = mqttText.getText() + "topic: ";
                    addText = addText + topic + " mensaje: ";
                    addText = addText + message.toString();
                    addText = addText + "\n";
                    mqttText.setText(addText);
                    displayNotification(lugar, addText);
                }
                else if(topic.equals(TOPIC_BAR)){
                    String lugar = "Bar";
                    String addText = mqttText.getText() + "topic: ";
                    addText = addText + topic + " mensaje: ";
                    addText = addText + message.toString();
                    addText = addText + "\n";
                    mqttText.setText(addText);
                    displayNotification(lugar, addText);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        };
        mqttClient.setCallback(mqttCallback);
    }
    private void displayNotification(String lugar, String desc){
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        createNotificationChannel();
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.putExtra("lugar", lugar);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.hotel)
                .setContentTitle(lugar)
                .setContentText(desc)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(desc))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notification.notify(1,builder.build());
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
