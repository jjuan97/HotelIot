#!/usr/bin/python
import paho.mqtt.client as mqtt
topic = "hotel/raspberryRoom/entrance"
broker = "broker.hivemq.com"
def mqtt_on_connect(client, userdata, flags, rc):
	if rc == 0:
		print("Conectado")
		client.subscribe(topic)
		print("Suscrito")
	else:
		print "[ERROR]"
def mqtt_on_message(client, userdata, msg):
	if msg.topic == topic:
		message = msg.payload
		print("Recibiendo ["+ str(message) +"]")
client = mqtt.Client()
client.on_connect = mqtt_on_connect
client.on_message = mqtt_on_message
client.connect(broker)
client.loop_forever()