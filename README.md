# HotelIot
## Video de Demostración IHoTel
https://youtu.be/144RZzcFyQ0
## Pasos para la ejecución del proyecto IHoTel

### Instalación para raspberry:
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

### POSIBLES ERRORES: 
1. Instalar gattlib podria significar un problema para el archivo dphys-swapfile:
    - 1.1. Editar el archivo, `sudo nano /etc/dphys-swapfile`
    - 1.2. Editar la variable de `CONF_SWAPSIZE=100` a `CONF_SWAPSIZE=500`
    - 1.3. Parar y recargar el archivo, `sudo /etc/init.d/dphys-swapfile stop` y `sudo /etc/init.d/dphys-swapfile start`
    - 1.4. Ejecutar una vez mas `sudo pip install gattlib` y esperar maximo se demorara 15 minutos.
    
2. Al instalar la libreria firebase-admin, pueden presentarse errores durante la ejecución de Setup.py, asociados posiblemente a conflictos con setuptools.py o incompatibilidades con la version de python que se este usando. Por lo anterior se realizan las siguientes recomendaciones:
    - 2.1. Si se presenta el siguiente error `TypeError: unsupported operand type(s) for -=: 'Retry' and 'int'` durante             la instalación de la librería se recomienda usar estos pasos antes realizar un nuevo intento de instalación:

    `apt-get remove python-pip python3-pip`
    `wget https://bootstrap.pypa.io/get-pip.py`
    `python get-pip.py`
    `python3 get-pip.py`
    Tomado de https://stackoverflow.com/questions/37495375/python-pip-install-throws-typeerror-unsupported-operand-types-for-retry
    
    - 2.2. Utilizar una version de python > 3.5 para ejecutar el script donde se emplee Firebase.

### Autores:
- JUAN JOSE PAREDES ROSERO. (jparedesr@unicauca.edu.co)
- ASTRID DANIELA NARVAEZ LOPEZ. (astriddnarvaez@unicauca.edu.co)
- JORGE ARMANDO MUNOZ ORDONEZ. (jorgemo@unicauca.edu.co)
- EDINSON DAVID LEON CHILITO. (leoc@unicauca.edu.co)
- SANTIAGO FELIPE YEPES CHAMORRO. (ysantiago@unicauca.edu.co)
