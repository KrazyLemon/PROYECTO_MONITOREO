# 🌊 Sistema de Monitoreo de Plantas de Tratamiento

Sistema integral de monitoreo y gestión para plantas de tratamiento de aguas residuales, con arquitectura distribuida que combina gateway en la nube, API REST, y aplicación web moderna.

## 📋 Descripción General

Este proyecto proporciona una solución completa de monitoreo remoto para plantas de tratamiento, permitiendo:

- **Visualización remota en tiempo real** mediante VNC integrado
- **Gestión centralizada** de múltiples plantas y usuarios
- **Acceso seguro** a través de VPN WireGuard
- **API RESTful** para integración de sistemas externos
- **Panel de control web** intuitivo y responsive
- **Autenticación JWT** segura
- **Control de accesos** basado en roles y permisos

## 🏗️ Arquitectura del Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                    INTERNET / USUARIOS                       │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌──────────────────────────────────────────────────────────────┐
│              AWS CLOUD - Cloud Gateway (EC2)                 │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  VPN Server (WireGuard)                                │  │
│  │  - Puerto: 51820/udp                                   │  │
│  │  - Red interna: 10.0.0.0/24                            │  │
│  │  - Acceso seguro a API y servicios                     │  │
│  └────────────────────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  Nginx Proxy Reverso                                   │  │
│  │  - Puertos: 80/443                                     │  │
│  │  - Enrutamiento hacia :                                │  │
│  │    • API Backend                                       │  │
│  │    • Frontend                                          │  │
│  │    • Servicios internos                                │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
         │                              │
         │                              ▼
         │               ┌────────────────────────────┐
         │               │  EC2 - Backend API         │
         │               │  ┌──────────────────────┐  │
         │               │  │  Spring Boot API     │  │
         │               │  │  Puerto: 8080        │  │
         │               │  └──────────────────────┘  │
         │               └────────────┬───────────────┘
         │                            │
         │                            ▼
         │               ┌────────────────────────────┐
         │               │  RDS PostgreSQL            │
         │               │  - Base de datos principal │
         │               │  - Usuarios, plantas, etc  │
         │               └────────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│  Front End (Navegador)           │
│  - React 19 + Vite               │
│  - Tailwind CSS + DaisyUI        │
│  - VNC Viewer integrado          │
└──────────────────────────────────┘

┌──────────────────────────────────┐
│  Plantas de Tratamiento (Remote) │
│  - PC/Servidor local en planta   │
│  - Conectadas vía VPN            │
│  - Accesibles por VNC            │
└──────────────────────────────────┘
```

## 📦 Componentes del Proyecto

### 1. 🌐 Cloud Gateway (`CLOUD-GATEWAY/`)

**Descripción**: Puerta de enlace principal que proporciona acceso seguro a todos los servicios.

**Componentes**:
- **VPN Server (WireGuard)**: Crea una red privada virtual segura
- **Nginx Proxy**: Enruta el tráfico hacia los servicios internos

**Configuración**:
```bash
cd CLOUD-GATEWAY/
# Ver compose.yml para configuración
# Variables en .env:
SERVER_PUBLIC_IP=tu.ip.publica
SERVER_PORT=51820
PEER_1=nombre_cliente
INTERNAL_SUBNET=10.0.0.0/24
```

**Instalación**:
```bash
docker-compose up -d
```

[Ver documentación completa](./CLOUD-GATEWAY/readme.md)

### 2. 🔗 Backend API (`BACKEND-API/demo/`)

**Descripción**: API RESTful que gestiona usuarios, plantas, conexiones y autenticación.

**Stack**:
- **Framework**: Spring Boot
- **Base de datos**: PostgreSQL
- **Autenticación**: JWT (JSON Web Tokens)
- **Build**: Maven

**Endpoints principales**:

#### Autenticación
- `POST /auth/login` - Iniciar sesión
- `POST /auth/register` - Registrar usuario

#### Empresas
- `GET /api/companies` - Listar empresas
- `POST /api/companies` - Crear empresa
- `PUT /api/companies/{id}` - Actualizar empresa
- `DELETE /api/companies/{id}` - Eliminar empresa

#### Plantas
- `GET /api/plants` - Listar plantas
- `GET /api/plants/{id}` - Obtener planta
- `GET /api/plants/company/{companyId}` - Plantas por empresa
- `POST /api/plants` - Crear planta
- `PUT /api/plants/{id}` - Actualizar planta
- `DELETE /api/plants/{id}` - Eliminar planta

#### Conexiones
- `GET /api/connections?token=...` - Obtener conexión
- `POST /api/connections` - Crear conexión
- `PUT /api/connections/update?token=...` - Actualizar conexión
- `DELETE /api/connections/delete?token=...` - Eliminar conexión
- `POST /api/connections/conect` - Conectar
- `POST /api/connections/disconnect` - Desconectar

**Instalación**:
```bash
cd BACKEND-API/demo/
mvn clean install
mvn spring-boot:run
```

[Ver documentación completa](./BACKEND-API/demo/readme.md)

### 3. 💻 Frontend App (`FRONTEND-APP/`)

**Descripción**: Aplicación web moderna para visualización y gestión del sistema.

**Stack**:
- **Framework**: React 19.2.0
- **Build Tool**: Vite 7.2.4
- **Estilos**: Tailwind CSS 4.1.18 + DaisyUI 5.5.14
- **Enrutamiento**: React Router 7.12.0
- **Cliente HTTP**: Axios 1.13.2
- **VNC Viewer**: React VNC 3.2.0

**Páginas**:
- **Login**: Autenticación de usuarios
- **Dashboard**: Panel principal con visualización VNC
- **Plants**: Gestión de plantas de tratamiento
- **Users**: Administración de usuarios
- **Profile**: Perfil del usuario actual
- **Reports**: Reportes del sistema
- **Alarms**: Centro de notificaciones

**Instalación**:
```bash
cd FRONTEND-APP/
npm install
npm run dev        # Desarrollo
npm run build      # Producción
npm run lint       # Verificar código
```

[Ver documentación completa](./FRONTEND-APP/README.md)

## 🚀 Instalación Completa

### Prerrequisitos
- Docker y Docker Compose
- Node.js 18+
- Java 11+
- Maven
- PostgreSQL (o usar RDS)
- Puerto 51820 UDP disponible
- Puertos 80 y 443 TCP disponibles

### Paso 1: Configurar Cloud Gateway
```bash
cd CLOUD-GATEWAY/
# Crear archivo .env
cat > .env << EOF
SERVER_PUBLIC_IP=your.public.ip
SERVER_PORT=51820
PEER_1=cliente1
INTERNAL_SUBNET=10.0.0.0/24
SERVER_ALLOWED_IPS=0.0.0.0/0
EOF

# Iniciar servicios
docker-compose up -d
```

### Paso 2: Configurar Backend API
```bash
cd BACKEND-API/demo/

# Configurar base de datos en application.properties
# Actualizar conexión PostgreSQL

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

### Paso 3: Configurar Frontend App
```bash
cd FRONTEND-APP/

# Instalar dependencias
npm install

# Configurar URL de API en src/api/api.js
# Actualizar baseURL según tu ambiente

# Ejecutar en desarrollo
npm run dev

# O compilar para producción
npm run build
npm run preview
```

## 🔐 Flujo de Autenticación

```
1. Usuario accede al Frontend
   ↓
2. Submit credenciales en Login
   ↓
3. Frontend envía POST /auth/login → Backend
   ↓
4. Backend valida credenciales
   ↓
5. Backend retorna JWT Token
   ↓
6. Frontend almacena token en localStorage
   ↓
7. Todas las requests incluyen: Authorization: Bearer {token}
   ↓
8. Backend valida token en cada request
```

## 📊 Variables de Entorno

### Cloud Gateway (.env)
```env
SERVER_PUBLIC_IP=tu.ip.publica
SERVER_PORT=51820
PEER_1=nombre_cliente
INTERNAL_SUBNET=10.0.0.0/24
SERVER_ALLOWED_IPS=0.0.0.0/0
PUID=1000
PGID=1000
TZ=America/Mexico_City
```

### Backend API (application.properties)
```properties
spring.datasource.url=jdbc:postgresql://host:5432/monitoreo
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

### Frontend App (src/api/api.js)
```javascript
const api = axios.create({
    baseURL: 'https://monitor.odis.com.mx/spring'
})
```

## 📝 Funcionalidades Principales

### Gestión de Acceso
- Autenticación con JWT
- Roles: ADMIN, SUPERVISOR, VIEWER
- Permisos basados en empresas y plantas
- Control de acceso con tokens de conexión

### Monitoreo en Tiempo Real
- Visualización VNC integrada
- Múltiples conexiones simultáneas
- Modo lectura y control total
- Abrir en ventana flotante

### Gestión de Plantas
- Crear/editar/eliminar plantas
- Asignar a empresas
- Configurar IPs VPN y VNC
- Generar tokens de acceso

### Gestión de Usuarios
- Crear usuarios
- Asignar roles y permisos
- Asociar a empresas
- Auditoría de acciones

### Reportes y Auditoría
- Registros de conexiones
- Historial de acciones de usuarios
- Reportes de disponibilidad
- Logs del sistema

## 🛠️ Desarrollo

### Estructura de Directorios
```
PROYECTO_MONITOREO/
├── CLOUD-GATEWAY/          # VPN + Nginx
├── BACKEND-API/            # API Spring Boot
│   └── demo/
│       ├── src/
│       └── pom.xml
├── FRONTEND-APP/           # React + Vite
│   ├── src/
│   ├── public/
│   └── package.json
└── EDGE-NODE/              # Nodo perimetral (opcional)
    └── DEV_DEPLOY/
```

### Scripts Útiles

**Frontend**:
```bash
npm run dev              # Servidor de desarrollo
npm run build           # Build producción
npm run preview         # Preview del build
npm run lint            # ESLint
```

**Backend**:
```bash
mvn clean install       # Compilar
mvn spring-boot:run     # Ejecutar
mvn test                # Pruebas
```

**Cloud Gateway**:
```bash
docker-compose up -d    # Iniciar
docker-compose down     # Detener
docker-compose logs     # Ver logs
```

## 📱 Requisitos del Navegador

- Chrome/Firefox/Safari/Edge (últimas 2 versiones)
- JavaScript habilitado
- WebSocket habilitado (para VNC)
- LocalStorage habilitado (para tokens)

## 🔒 Seguridad

### Implementado
- ✅ Autenticación JWT
- ✅ HTTPS
- ✅ VPN WireGuard
- ✅ Control de acceso basado en roles
- ✅ Validación de tokens con expiración
- ✅ Interceptores de errores 401/403

### Recomendaciones
- 🔐 Cambiar credenciales por defecto
- 🔐 Usar certificados SSL válidos
- 🔐 Configurar firewall adecuadamente
- 🔐 Rotar tokens regularmente
- 🔐 Monitorear logs de acceso
- 🔐 Mantener dependencias actualizadas

## 🐛 Troubleshooting

### Frontend no se conecta a API
```bash
# Verificar baseURL en src/api/api.js
# Asegurar que CORS está habilitado en Backend
# Verificar que el backend está corriendo en el puerto correcto
```

### VPN WireGuard no conecta
```bash
netstat -tlnp | grep 51820      # Verificar puerto
docker-compose logs vpn-server   # Ver logs del contenedor
```

### Base de datos no conecta
```bash
# Verificar credenciales en application.properties
# Asegurar que PostgreSQL está corriendo
# Verificar firewall permite puerto 5432
```

### Token JWT expirado
```bash
# Frontend automáticamente redirige a login
# Token se almacena en localStorage
# Verificar fecha/hora del servidor
```

## 📚 Documentación Adicional

- [Cloud Gateway README](./CLOUD-GATEWAY/readme.md)
- [Backend API README](./BACKEND-API/demo/readme.md)
- [Frontend App README](./FRONTEND-APP/README.md)

## 👥 Roles y Permisos

| Rol | Plantas | Usuarios | Reportes | Configuración |
|-----|---------|----------|----------|---------------|
| **ADMIN** | ✅ Full | ✅ Full | ✅ Full | ✅ Full |
| **SUPERVISOR** | ✅ Read/Write | ✅ Lectura | ✅ Full | ❌ |
| **VIEWER** | ✅ Lectura | ❌ | ✅ Lectura | ❌ |

## 📊 Base de Datos

### Entidades principales
- **Users**: Usuarios del sistema
- **Companies**: Empresas que poseen plantas
- **Plants**: Plantas de tratamiento
- **Connections**: Conexiones activas con tokens
- **LogActivity**: Auditoría de acciones

## 🌐 URLs de Referencia

- **Frontend**: `http://localhost:5173` (desarrollo) o dominio configurado
- **Backend**: `http://localhost:8080/spring`
- **VPN**: `51820/udp`

## 📞 Soporte

Para reportar problemas o sugerencias:
1. Revisar los logs de cada componente
2. Verificar el troubleshooting arriba
3. Contactar al equipo de desarrollo

---

**Estado del Proyecto**: ✅ En desarrollo activo | Última actualización: Marzo 2026