# Cloud Gateway

Este proyecto configura una puerta de enlace en la nube que combina un servidor VPN WireGuard con un proxy Nginx para proporcionar acceso seguro y proxy reverso a servicios internos. esos servicios siendo API, PC,s en local para las plantas, Front end, 

## Servicios

### VPN Server (WireGuard)
- **Imagen**: `linuxserver/wireguard:latest`
- **Descripción**: Servidor VPN WireGuard que proporciona conectividad segura a la red interna.
- **Puertos**:
  - `51820/udp`: Puerto WireGuard
  - `443/tcp`: HTTPS
  - `80/tcp`: HTTP
- **Volúmenes**:
  - `./config:/config`: Configuraciones WireGuard
  - `/lib/modules:/lib/modules`: Módulos del kernel

### Nginx Proxy
- **Imagen**: `nginx:latest`
- **Descripción**: Proxy reverso Nginx que maneja el tráfico HTTP/HTTPS. La configuracion depende de los servicios dentro del servidor de aws y los servicios que se necesiten, un ejemplo de arquitectura es:
EC2(nginx,VPN) -> EC2(API db) -> RDS (postgres) -> S3(Frontend). 
- **Modo de red**: Comparte la red con el servicio `vpn-server`
- **Volúmenes**:
  - `./nginx/conf.d:/etc/nginx/conf.d`: Configuraciones Nginx
  - `./html:/usr/share/nginx/html:ro`: Archivos estáticos
  - `/etc/letsencrypt:/etc/letsencrypt:ro`: Certificados SSL

## Prerrequisitos

- Docker y Docker Compose instalados
- Puerto 51820/udp disponible
- Puertos 80/tcp y 443/tcp disponibles
- Archivo `.env` con las variables de entorno necesarias

## Configuración

### Variables de Entorno (.env)

Crea un archivo `.env` en el directorio raíz con las siguientes variables:

```env
SERVER_PUBLIC_IP=tu.ip.publica.aqui
SERVER_PORT=51820
PEER_1=nombre_del_cliente
INTERNAL_SUBNET=10.0.0.0/24
SERVER_ALLOWED_IPS=0.0.0.0/0
```

### Configuraciones Adicionales

1. **WireGuard**: Las configuraciones se generan automáticamente en `./config/wg_confs/`
2. **Nginx**: Configura los archivos en `./nginx/conf.d/` para definir los proxies reversos
3. **Certificados SSL**: Monta certificados Let's Encrypt en `/etc/letsencrypt/`

## Instalación y Uso

1. Clona este repositorio
2. Crea el archivo `.env` con tus configuraciones
3. Ejecuta `docker-compose up -d`
4. Verifica que los servicios estén corriendo: `docker-compose ps`

## Conexión VPN

Después de iniciar los servicios, encontrarás las configuraciones de cliente en `./config/wg_confs/`. Usa estos archivos para conectar clientes WireGuard.

## Notas de Seguridad

- Cambia las variables de entorno por valores seguros
- Configura firewalls apropiados
- Mantén los certificados SSL actualizados
- Monitorea los logs de los contenedores

## Troubleshooting

- Verifica que los puertos no estén en uso: `netstat -tlnp | grep :51820`
- Revisa logs: `docker-compose logs`
- Asegúrate de que el módulo WireGuard esté disponible en el host
