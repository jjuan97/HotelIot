#!/usr/bin/python

import paho.mqtt.publish as publish
from bluetooth.ble import DiscoveryService
import time

serviceBLE = DiscoveryService()
topic = "hotel/raspberryRoom/entrance"
broker = "broker.hivemq.com"

def scanBLE():
	devices = serviceBLE.discover(2)
	for address, name in devices.items():
		print("name:{}, address:{}".format(name,address))
		return(address)

def mqtt_pub(message):
	try:
		print("Enviando ["+str(message)+"]")
		publish.single(topic, str(message), hostname=broker)
	except:
		print "[ERROR]"

while True:
	print("Iniciando Busqueda de Beacons ...")
	data = scanBLE()
	if str(data) != "None":
		print("Se encontro el beacon con direccion MAC: " + str(data))
		mqtt_pub(data)
		print("Fin")
		print("")
		time.sleep(10)
	else:
		print("Error detectando Beacon, compruebe beacon cercano")
		print("")
		time.sleep(2)
