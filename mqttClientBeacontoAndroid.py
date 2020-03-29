#!/usr/bin/python

import paho.mqtt.publish as publish
from gattlib import DiscoveryService
import time

serviceBLE = DiscoveryService()
topic = "hotel/raspberry1/restaurant"
broker = "broker.hivemq.com"

def scanBLE():
	devices = serviceBLE.discover(2)
	for address, name in devices.items():
		print("name:{}, address:{}".format(name,address))
		return(address)

def mqtt_pub(message):
	try:
		print("Enviando ["+str(message)+"] ...")
		publish.single(topic, str(message), hostname=broker)
	except:
		print ("[ERROR]")

while True:
	print("Iniciando Busqueda de Beacons ...")
	data = scanBLE()
	if str(data) != "None":
		print("Se encontro el beacon con direccion MAC: " + str(data))
		mqtt_pub(data)
		print("Enviado\nFin")
		print(" ")
		time.sleep(6)
	else:
		print("Error detectando Beacon, compruebe beacon cercano")
		print("")
		time.sleep(1)
