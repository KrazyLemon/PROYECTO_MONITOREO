# Sistema de Monitoreo - Frontend App

Aplicación web moderna construida con React y Vite para el monitoreo y gestión de plantas de tratamiento de aguas residuales con integración de VNC Viewer.

## 🎯 Características Principales

- **Autenticación JWT**: Sistema seguro de login con tokens JWT
- **VNC Viewer Integrado**: Visualización remota de sistemas en tiempo real
- **Gestión de Usuarios**: Administración completa de usuarios del sistema
- **Gestión de Plantas**: Control de plantas de tratamiento
- **Dashboard Interactivo**: Panel principal con información en tiempo real
- **Interfaz Responsive**: Diseño adaptable para dispositivos móviles y desktop
- **Rutas Protegidas**: Control de acceso basado en autenticación
- **Sistema de Reportes**: Generación de reportes del sistema
- **Notificaciones y Alarmas**: Centro de alertas del sistema

## 🛠 Tecnología

| Tecnología | Versión | Propósito |
|---|---|---|
| **React** | 19.2.0 | Framework UI |
| **Vite** | 7.2.4 | Build tool |
| **Tailwind CSS** | 4.1.18 | Estilos CSS |
| **DaisyUI** | 5.5.14 | Componentes UI predefinidos |
| **React Router** | 7.12.0 | Enrutamiento |
| **Axios** | 1.13.2 | Cliente HTTP |
| **React VNC** | 3.2.0 | Visor VNC |
| **JWT Decode** | 4.0.0 | Decodificación de tokens |
| **Iconify React** | 6.0.2 | Librería de iconos |

## 📁 Estructura del Proyecto

```
src/
├── pages/                    # Páginas principales
│   ├── Login.jsx            # Página de autenticación
│   ├── Dashboard.jsx        # Panel principal
│   ├── Plants.jsx           # Gestión de plantas
│   ├── Users.jsx            # Gestión de usuarios
│   ├── Profile.jsx          # Perfil de usuario
│   └── NotFound.jsx         # Página 404
├── components/              # Componentes reutilizables
│   ├── Navbar.jsx           # Barra de navegación
│   └── VncViewer.jsx        # Visor VNC integrado
├── context/                 # Context de React
│   └── AuthContext.jsx      # Contexto de autenticación
├── routes/                  # Configuración de rutas
│   └── ProtectedRoute.jsx   # Rutas privadas
├── api/                     # Configuración de API
│   └── api.js               # Cliente Axios
├── utils/                   # Funciones utilitarias
│   └── auth.js              # Utilitarios de autenticación
├── theme/                   # Estilos
│   └── main.css             # CSS principal
├── App.jsx                  # Componente raíz
└── main.jsx                 # Punto de entrada
```

## 📄 Componentes

### Navbar.jsx
Barra de navegación principal con:
- Toggle de sidebar en responsive
- Navegación a secciones: Inicio, Plantas, Reportes, Usuarios
- Menú de usuario con opción de logout
- Iconos de notificaciones y búsqueda

### VncViewer.jsx
Visor VNC integrado con funcionalidades:
- Conectar/desconectar a servidor VNC
- Modo solo lectura alternable
- Abrir en ventana nueva
- Reconexión automática
- Indicadores de estado de conexión

## 📑 Páginas

### Login.jsx
- Formulario de autenticación
- Validación de credenciales
- Toggle de visibilidad de contraseña
- Soporte para Enter para enviar

### Dashboard.jsx
- Panel principal con información del usuario
- Integración de VNC Viewer
- Búsqueda y filtros
- Visualización en tiempo real

### Plants.jsx
- Listado de plantas de tratamiento
- Gestión y monitoreo
- [Descripción específica a completar]

### Users.jsx
- Gestión de usuarios del sistema
- Creación, edición, eliminación
- [Descripción específica a completar]

### Profile.jsx
- Información del perfil del usuario
- Edición de datos personales
- [Descripción específica a completar]

## 🔐 Autenticación

### AuthContext.jsx
Proporciona:
- **login()**: Autenticación con username y password
- **logout()**: Cerrar sesión
- **fetchUserData()**: Obtener información del usuario
- **token**: Token JWT almacenado
- **user**: Datos del usuario actual

### ProtectedRoute.jsx
- Validación de rutas protegidas
- Redirección a login si no hay token válido

## 🔌 API

### api.js
Cliente Axios personalizado con:
- BaseURL: `https://monitor.odis.com.mx/spring`
- Interceptores para manejar:
  - Errores 401: Sesión expirada
  - Errores 403: Acceso denegado
  - Redirección automática al login

## 📦 Instalación

```bash
# Instalar dependencias
npm install

# Ejecutar en modo desarrollo
npm run dev

# Construir para producción
npm run build

# Vista previa de build
npm run preview

# Linting del código
npm run lint
```

## 🚀 Scripts Disponibles

- `npm run dev` - Inicia servidor de desarrollo (Vite)
- `npm run build` - Construye la aplicación para producción
- `npm run preview` - Previsualiza la build de producción
- `npm run lint` - Verifica código con ESLint

## ⚙️ Configuración

### Variables de Entorno
[Documentar variables de entorno necesarias]

### Configuración de API
Editar `src/api/api.js` para cambiar la URL base de la API:
```javascript
const api = axios.create({
    baseURL: 'https://monitor.odis.com.mx/spring'
})
```

### Temas y Estilos
- Tailwind CSS: `src/theme/main.css`
- DaisyUI: Componentes predefinidos
- Iconify: Sistema de iconos

## 📝 Notas de Desarrollo

- El token JWT se almacena en `localStorage`
- Las rutas protegidas requieren un token válido
- El contexto de autenticación proporciona estado global
- Los iconos usan la librería Iconify

## 🤝 Notas para Completar

- [ ] Agregar información específica de plantas
- [ ] Detallar funcionalidades de usuarios
- [ ] Documentar variables de entorno
- [ ] Agregar instrucciones de deploy
- [ ] Incluir ejemplos de uso de API
- [ ] Documentar procedimientos de debugging