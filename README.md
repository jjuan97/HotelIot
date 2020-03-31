# HotelIot
## Proyect for IOT

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

### POSIBLES ERRORES: 
1. instalar gattlib podria significar un problema para el archivo dphys-swapfile:
    - 1.1. Editar el archivo, `sudo nano /etc/dphys-swapfile`
    - 1.2. Editar la variable de `CONF_SWAPSIZE=100` a `CONF_SWAPSIZE=500`
    - 1.3. Parar y recargar el archivo, `sudo /etc/init.d/dphys-swapfile stop` y `sudo /etc/init.d/dphys-swapfile start`
    - 1.4. Ejecutar una vez mas `sudo pip install gattlib` y esperar maximo se demorara 15 minutos.

###Autores:
- JUAN JOSE PAREDES ROSERO. (jparedesr@unicauca.edu.co)
- ASTRID DANIELA NARVAEZ LOPEZ. (astriddnarvaez@unicauca.edu.co)
- JORGE ARMANDO MUNOZ ORDONEZ. (jorgemo@unicauca.edu.co)
- EDINSON DAVID LEON CHILITO. (leoc@unicauca.edu.co)
- SANTIAGO FELIPE YEPES CHAMORRO. (ysantiago@unicauca.edu.co)
