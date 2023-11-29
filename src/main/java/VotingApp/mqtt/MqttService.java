package VotingApp.mqtt;

import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService implements MqttCallback {

    private final MqttClient mqttClient;
    @Autowired
    private VotingApp.db.MongoDBService mongoDBService; // MongoDB Service

    public MqttService() throws MqttException {
        String brokerUrl = "ssl://27f7f176f6cf4168a91e2826d5207e77.s1.eu.hivemq.cloud:8883";
        mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        options.setUserName("echoP");
        options.setPassword("Dat250hvl".toCharArray());

        mqttClient.setCallback(this);
        mqttClient.connect(options);
    }


    public void publish(String topic, String payload) throws MqttException {
        if (!mqttClient.isConnected()) {
            reconnect();
        }
        MqttMessage message = new MqttMessage(payload.getBytes());
        mqttClient.publish(topic, message);
    }

    public void subscribeToPollResults() throws MqttException {
        if (!mqttClient.isConnected()) {
            reconnect();
        }
        mqttClient.subscribe("polls/results", this::processPollResult);
    }

    private void processPollResult(String topic, MqttMessage message) {
        String pollResult = new String(message.getPayload());
        mongoDBService.insertPollResult(pollResult); // Store in MongoDB
    }


    private void reconnect() {
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            // Log or handle reconnection failure
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // This method is called when the connection to the broker is lost.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // This method is called when a message arrives from the broker.
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // This method is called when a message delivery to the broker is complete.
    }

    @PreDestroy
    public void cleanUp() throws MqttException {
        mqttClient.disconnect();
    }
}

