package com.ubicuos.aplicacionhotelmqtt;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    Context context;
    MqttAndroidClient mqttClient;
    NotificationCompat.Builder builder;
    String emailLog;
    String dataPlace;
    String dataSchedule;
    String dataLocation;
    String CHANNEL_ID= "idnotification";
    String EXAMPLE_SERVER = "tcp://broker.hivemq.com";
    Button signOut;
    TextView welcome;
    TextView sitio;
    TextView horario;
    TextView ubicacion;
    FirebaseAuth homeFirebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth.AuthStateListener homeStateListenerAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        signOut = findViewById(R.id.signout);
        welcome = findViewById(R.id.textwelcome);
        sitio = findViewById(R.id.textsitio);
        horario = findViewById(R.id.texthorario);
        ubicacion = findViewById(R.id.textubicacion);

        getDataActivity();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intToLogin);
                finish();
            }
        });
        getIdDB();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mqttClient != null) {
            mqttClient.close();
        }
    }

    private void getDataActivity(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            emailLog = extras.getString("emailLog");
            dataPlace = extras.getString("sitio");
            dataSchedule = extras.getString("horario");
            dataLocation = extras.getString("ubicacion");
            if (dataPlace != null){
                sitio.setText(dataPlace);
                horario.setText(dataSchedule);
                ubicacion.setText(dataLocation);
            }

            String concatenate = getString(R.string.welcome) + " " + emailLog;
            welcome.setText(concatenate);
        }
    }

    private void getIdDB(){
        db.collection("usuarios").whereEqualTo("email", emailLog)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if (task.getResult() != null) {
                                    startMqtt(document.getId());
                                }
                            }
                        } else {
                            Log.e("Firebase", "Error getting documents.", task.getException());
                            System.out.println("error: " + task.getException());
                        }
                    }
                });
    }

    private void startMqtt(String userID) {
        String clientId = MqttClient.generateClientId();
        MqttConnectOptions mqttOptions = new MqttConnectOptions();
        mqttOptions.setAutomaticReconnect(true);
        mqttClient = new MqttAndroidClient(context, EXAMPLE_SERVER, clientId);
        //the different topics that user can manage
        final String TOPIC_MAIN = "ihotel/"+userID+"/#";
        final String TOPIC_RESTAURANT = "ihotel/"+userID+"/restaurantes";
        final String TOPIC_BAR = "ihotel/"+userID+"/bares";
        final String TOPIC_POOL = "ihotel/"+userID+"/piscinas";
        System.out.println(TOPIC_MAIN);
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
            System.out.println("No se pudo iniciar servidor mqtt");
        }

        MqttCallback mqttCallback = new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                //TODO: Agregar a cada condicional una accion modificada en el archivo xml y ahi finaliza la aplicación
                if (topic.equals(TOPIC_RESTAURANT)) {
                    String lugar = "Restaurante";
                    ArrayList<String> campos;
                    campos = separateCharacters(message.toString());

                    String concatenatesitio = "Nombre del lugar: "+ campos.get(3);
                    String concatenatehorario = "Horario de atención: " + campos.get(1);
                    String concatenateubicacion= "Se encuentra ubicado en: "+ campos.get(5);
                    sitio.setText(concatenatesitio);
                    horario.setText(concatenatehorario);
                    ubicacion.setText(concatenateubicacion);

                    displayNotification(lugar, concatenatesitio, concatenatehorario, concatenateubicacion);
                }
                else if(topic.equals(TOPIC_BAR)){
                    String lugar = "Bar";
                    ArrayList<String> campos;
                    campos = separateCharacters(message.toString());
                    System.out.println(campos);

                    //displayNotification(lugar, campos.get(5));
                }
                else if(topic.equals(TOPIC_POOL)){
                    String lugar = "Pisicna";
                    ArrayList<String> campos;
                    campos = separateCharacters(message.toString());
                    System.out.println(campos);

                    //displayNotification(lugar, campos.get(3));
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        };
        mqttClient.setCallback(mqttCallback);
    }

    private ArrayList<String> separateCharacters(String characters){
        String separator = Pattern.quote("?");
        String[] processSplit = null;
        ArrayList<String> result = new ArrayList<>();
        processSplit = characters.split(separator);
        for (int i = 0; i < processSplit.length; i++) {
            String[] splitToArray = processSplit[i].split("=");
            result.add(splitToArray[0]);
            result.add(splitToArray[1]);
        }
        return result;
    }

    private void displayNotification(String lugar, String descSitio, String descHorario, String descUbicacion){
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        createNotificationChannel();
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.putExtra("emailLog", emailLog);
        notificationIntent.putExtra("sitio", descSitio);
        notificationIntent.putExtra("horario", descHorario);
        notificationIntent.putExtra("ubicacion", descUbicacion);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.hotel)
                .setContentTitle(lugar)
                .setContentText(descSitio)
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
