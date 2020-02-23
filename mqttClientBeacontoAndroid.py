#!/usr/bin/python

import paho.mqtt.publish as publish
from random import randint
import time

topic = "example1990/raspberrypi1/luz123"
broker = "broker.hivemq.com"

def get_data(pin):
	reading = 0
	GPIO.setup(pin, GPIO.OUT)
	GPIO.output(pin, GPIO.LOW)
	time.sleep(0.1)
	
	GPIO.setup(pin, GPIO.IN)
	while (GPIO.input(pin) == GPIO.LOW):
		reading += 1
	return reading

def mqtt_pub(message):
	try:
		print("Enviando ["+str(message)+"]")
		publish.single(topic, str(message), hostname=broker)
	except:
		print "[ERROR]"

while True:
	print("Inicio")
	data = get_data(11)
	mqtt_pub(data)
	print("Fin")
	print("")
	time.sleep(3)
