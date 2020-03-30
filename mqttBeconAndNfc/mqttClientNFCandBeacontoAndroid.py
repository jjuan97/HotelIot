#!/usr/bin/python

import paho.mqtt.publish as publish
from gattlib import DiscoveryService
import time
import rfidiot

serviceBLE = DiscoveryService()
#topic = "hotel/raspberry1/restaurantes"
topic = "ihotel/CiDa5TyIKDsYWbCCheF9/restaurantes"
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
def scanNFC():
	print("Inicio Deteccion TAG NFC")
	try:
		card = rfidiot.card
		args = rfidiot.args
		if len(args) == 1:
			card.settagtype(args[0])
		else:
			card.settagtype(card.ALL)
		if card.select():
			print("TAG ID: "+ card.uid)
	except:
		pass
	finally:
		print("Fin Deteccion TAG NFC")

while True:
	scanNFC()
	print("Iniciando Busqueda de Beacons ...")
	#data = scanBLE()
	#data= "horario=De 7 AM a 9 PM?nombre=Restaurante IHoTel?ubicacion=Edificio 2, Piso 1, IHoTel"
	data = "horario=7am a 6pm?nombre=Piscina El Descanso?recomendaciones=No tomarse fotos en la piscina?tipo=Piscina familiar?ubicacion=Edificio 2"
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
