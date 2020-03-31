#!/usr/bin/python

import paho.mqtt.publish as publish
from gattlib import DiscoveryService
import time

serviceBLE = DiscoveryService()
#topic = "hotel/raspberry1/restaurantes"
topic = "ihotel/CiDa5TyIKDsYWbCCheF9/bares"
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
	MAC = scanBLE()
	#data= "horario=De 7 AM a 9 PM?nombre=Restaurante IHoTel?ubicacion=Edificio 2, Piso 1, IHoTel"
	data= "horario=De 4pm a 11 PM?nombre=Bar IHoTel 24 horas?menu=Ron 3 esquinas:8000,Tequila:28000,Vodka:30000,Whisky:31000"
	#data = "horario=7am a 6pm?nombre=Piscina El Descanso?recomendaciones=No tomarse fotos en la piscina?tipo=Piscina familiar?ubicacion=Edificio 2"	
	if str(MAC) != "None":
		print("Se encontro el beacon con direccion MAC: " + str(MAC))
		mqtt_pub(data)
		print("Enviado\nFin")
		print(" ")
		time.sleep(6)
	else:
		print("Error detectando Beacon, compruebe beacon cercano")
		print("")
		time.sleep(1)
