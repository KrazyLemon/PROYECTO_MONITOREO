Pasos para configurar las maquinas nodos
    1.- Extrae el .zip en una nueva carpeta en C:\Websockify
        a) crea el subdirectorio \token
        b) crea el archivo token.txt usando la siguiente estructura
                TOKEN_SUPER_LARGO_Y_SUPER_SECRETO: IP_DESTINO:PUERTO_DE_DESTINO
    2.- Mete el archivo nssm en una carpeta en C:\nssm
    3.- Ejecuta el instalador de Wireguard
    4.- Importa un tunel usando el archivo .conf 
    5.- Revisa la conexion del VPN al servidor
        a) Ejecuta un cmd
        b) Realiza ping al servidor con IP : 10.13.13.1
            i) si no procede el ping revisa el .conf
    6.- Ejecuta el instalador de python
        a) Selecciona Add PATH y ejecutar como administrador ( Instalacion para todos los usuarios )
    7.- Revisa la instalacion de python 
        a) Abre un cmd 
        b) Ejecuta el comando "python -V" && "pip -V"
            i) si no los reconoce realiza de nuevo la instalacion
    8.- En una consola ejecuta el comando "where python" guarda la ruta de instalacion para los siguientes pasos
    9.- Instala el websockify usando el sig comando.
        "<<RUTA_DE_INSTALACION_DE_PYTHON>>" -m pip install websockify --target "<<RUTA_DE_INSTALACION_DE_PYTHON>>\Lib\site-packages" --upgrade
    10. Ejecuta los sig comandos
        # 1. Ruta al ejecutable de Python
            nssm set WebsockifyService Application "<<RUTA_DE_INSTALACION_DE_PYTHON>>"
        # 2. Directorio de inicio (MUY IMPORTANTE)
            nssm set WebsockifyService AppDirectory "<<RUTA_DE_INSTALACION_DE_PYTHON>>"
        # 3. Argumentos (Usando barras / y sin espacios al inicio)
            nssm set WebsockifyService AppParameters "-m websockify 6080 --token-plugin=TokenFile --token-source=C:/Websockify/token/token.txt"
    11. Ejecuta el servicio nssm start WebsockifyService
        a) ejecuta el sig comando: netstat -ano | findstr :6080
        b) si no se ejecuta el servicio abre visor de eventos > Registro de windows > aplicacion
    12. Cambia el adaptador de red a privada
    13. Permite el wireguar.exe atraves del firewall