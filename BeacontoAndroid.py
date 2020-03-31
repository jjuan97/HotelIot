#!/usr/bin/python
'''
from gattlib import DiscoveryService'''
import time
import paho.mqtt.publish as publish

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

#	TODO
#	Configure the credentials of Firebase Admin and create clients.
#	Change the path of JSON file
cred = credentials.Certificate("/home/santiago/ubicuos/ihotel-538ba-firebase-adminsdk-esnt1-614649f757.json")
firebase_admin.initialize_app(cred)
db = firestore.client()
print("Cliente creado")

#	TODO
#	Configure the place's type:
#		in Firebase only: {restaurantes, bares, piscinas}
#	Configure the place's name: 
#		Requires de exact place's name
placeType = "restaurantes"	
placeName = "Restaurante IHoTel"  
#placeName = "Bar IHotel 24 horas"
#placeName = "Piscina El Descanso"


placeInformation = ""
mainTopicLevel = "ihotel/"
broker = "broker.hivemq.com"
placeID = ""
collection = ""
#	Array of {'id': 'id', 'email': 'correo'}
users = []

#	TODO: change menu by menú
#	What subcollection is required ?
if placeType == "piscinas":
	collection = "promocion"
else:
	collection = "menu"


#	Get place's information
places = db.collection(placeType).where(u'nombre', u'==', placeName).stream()
for doc in places:
    #print(u'{} => {}'.format(doc.id, doc.to_dict()))
    placeID = str(doc.id)
    placeInformation = 'horario=' + doc.to_dict()['horario']
    placeInformation +=  '?nombre=' + doc.to_dict()['nombre']
    #placeInformation +=  '?ubicacion=' + doc.to_dict()['ubicacion']
    #placeInformation +=  '?menu=producto1:valor,producto2:valor'
    placeInformation += '?'+collection+'='


#	Get Menu or Promotions
#print(placeID)
menu = db.collection(placeType+'/'+placeID+'/'+collection).stream()
for doc in menu:
    #print(u'{} => {}'.format(doc.id, doc.to_dict()))
    if collection == "promocion":
    	placeInformation += str(doc.to_dict()['precio']) + ','
    else:
    	placeInformation += str(doc.to_dict()['producto']) + ':' + str(doc.to_dict()['precio']) + ','    	



#	Show the MQTT message
print(placeInformation)



# 	Get Initial Users 
docs = db.collection(u'usuarios').stream()

for doc in docs:
    #print(u'{} => {}'.format(doc.id, doc.to_dict()))
    user = {'id': doc.id, 'beaconUID': doc.to_dict()['beaconUID']}
    users.append(user)


#
#print(users)
#print(" ")


#	Create a callback on_snapshot function to capture changes
def on_snapshot(col_snapshot, changes, read_time):
    print(u'Callback received query snapshot.')
    print(u'Current users:')
    users = []
    for doc in col_snapshot:
        #print(u'{} => {}'.format(doc.id, doc.to_dict()['email']) )
        user = {'id': str(doc.id), 'beaconUID': str(doc.to_dict()['beaconUID'])}
        users.append(user)
    print(users)
    print(" ")

#	Watch the collection query
col_query = db.collection(u'usuarios')
query_watch = col_query.on_snapshot(on_snapshot)


#	Scanning Function
def scanBLE():
	'''devices = serviceBLE.discover(2)
	for address, name in devices.items():
		print("name:{}, address:{}".format(name,address))
		return(address)'''
	# Simulation of scan
	return "AC:23:3F:A1:0C:47"


#	Search user id of the beacon
def searchUser(uid):
	idUser = "None"
	for user in users:
		if str(user['beaconUID']) == str(uid):
			idUser = str(user['id'])
	return idUser


#	Pushing MQTT
def mqtt_pub(user):
	topic = str(mainTopicLevel)+str(user)+'/'+str(placeType)
	print(topic)
	try:
		print("Enviando ["+str(placeInformation)+"] ...")
		publish.single(topic, placeInformation, hostname=broker)
		print("Enviado\nFin")
	except:
		print ("[ERROR]")


#	Main loop
while True:
	print("Scanning ...")
	data = scanBLE()

	if str(data) != "None":
		print("Se encontro el Beacon con UID: " + str(data))
		user = searchUser(str(data))
		if user != "None":
			print("EL beacon corresponde a id:"+ str(user))
		mqtt_pub(user)
		print(" ")
		time.sleep(6)
	else:
		print("No hay usuarios cerca")
		print("")
		time.sleep(1)
	time.sleep(15)
