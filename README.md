# Hotel Iot (IHoTel)
## Video de Demostración IHoTel
https://youtu.be/144RZzcFyQ0
## Pasos para la ejecución del proyecto IHoTel

### Instalación para raspberry (solo detecta beacon):
1. Clonar repositorio comando: git clone https://github.com/jjuan97/HotelIot
2. Ejecutar `sudo apt-get update`
3. Ejecutar `sudo apt-get upgrade`
4. Descargar paho, `sudo pip install paho-mqtt`
5. Instalar librerias BLE:
    - 5.1. `sudo apt install bluetooth libbluetooth-dev`
    - 5.2. `sudo apt install pkg-config libboost-python-dev libboost-thread-dev`
    - 5.3. `sudo apt install libglib2.0-dev python-dev`
    - 5.4. `sudo pip install pybluez`
    - 5.5. `sudo pip install gattlib`
6. Reiniciar raspberry, `sudo reboot`
7. Instalar librerias Firebase:    
    `pip install --upgrade firebase-admin`
    Tomado de: https://firebase.google.com/docs/firestore/quickstart

### Instalación para raspberry (detecta beacon y NFC)
1. Descargar imagen raspbian (jessie) que permite ejecutar las librerias que trabajan con el lector NFC touchatag: https://drive.google.com/drive/u/2/folders/1T3qSvR-Hg-hm_u__w2YWRVnkBnsRxgxd
2. Instalar imagen en la memoria SD (seguir documentacion oficial: https://www.raspberrypi.org/documentation/installation/installing-images/README.md)
3. Seguir los pasos de la anterior instalación
4. Ejecutar archivo mqttClientNFCandBeacontoAndroid.py en la carpeta mqttBeconAndNfc

### Instalar aplicación web
El archivo web no necesita de una instalación, basta solo con ejecutar el archivo index.html

### Instalar aplicación android
La instalación de la aplicación se puede dar de dos formas:
1. Abrir desde android studio el proyecto AplicacionHotelMqtt y compilar la aplicación.
2. Ir a la ruta AplicacionHotelMqtt/app/release/ agregar el archivo app-release.apk a su dispositivo Smartphone. Abrir archivo desde el celular y aceptar la instalación de fuentes no oficiales.

### POSIBLES ERRORES: 
1. Instalar gattlib podria significar un problema para el archivo dphys-swapfile:
    - 1.1. Editar el archivo, `sudo nano /etc/dphys-swapfile`
    - 1.2. Editar la variable de `CONF_SWAPSIZE=100` a `CONF_SWAPSIZE=500`
    - 1.3. Parar y recargar el archivo, `sudo /etc/init.d/dphys-swapfile stop` y `sudo /etc/init.d/dphys-swapfile start`
    - 1.4. Ejecutar una vez mas `sudo pip install gattlib` y esperar maximo se demorara 15 minutos.
    
2. Al instalar la libreria firebase-admin, pueden presentarse errores durante la ejecución de Setup.py, asociados posiblemente a conflictos con setuptools.py o incompatibilidades con la version de python que se este usando. Por lo anterior se realizan las siguientes recomendaciones:
    - 2.1. Si se presenta el siguiente error `TypeError: unsupported operand type(s) for -=: 'Retry' and 'int'` durante la instalación de la librería se recomienda usar estos pasos antes realizar un nuevo intento de instalación:

        - `apt-get remove python-pip python3-pip`
        - `wget https://bootstrap.pypa.io/get-pip.py`
        - `python get-pip.py`
        - `python3 get-pip.py`
        
    Tomado de https://stackoverflow.com/questions/37495375/python-pip-install-throws-typeerror-unsupported-operand-types-for-retry
    
    - 2.2. Utilizar una version de python > 3.5 para ejecutar el script donde se emplee Firebase.
3. Aplicación no abre, si la aplicación no abre comprobar si su Smartphone tiene version de android por encima de la API 23 (Android 6.0 Marshmallow), de lo contrario la aplicación no se podra ejecutar con exito.

### Autores:
- JUAN JOSE PAREDES ROSERO. (jparedesr@unicauca.edu.co)
- ASTRID DANIELA NARVAEZ LOPEZ. (astriddnarvaez@unicauca.edu.co)
- JORGE ARMANDO MUNOZ ORDONEZ. (jorgemo@unicauca.edu.co)
- EDINSON DAVID LEON CHILITO. (leoc@unicauca.edu.co)
- SANTIAGO FELIPE YEPES CHAMORRO. (ysantiago@unicauca.edu.co)
