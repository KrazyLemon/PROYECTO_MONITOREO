# API de Monitoreo

Este documento describe los endpoints disponibles en la API de Monitoreo.

## Autenticación

La API utiliza autenticación basada en tokens Bearer. Asegúrese de incluir el token en el encabezado `Authorization` de sus solicitudes.

## Endpoints

### Autenticación (Auth)

#### Iniciar sesión
Autentica a un usuario con sus credenciales y devuelve un token JWT.

*   **URL:** `/auth/login`
*   **Método:** `POST`
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "username": "usuario@ejemplo.com",
      "password": "password123"
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

#### Registrar usuario
Registra un nuevo usuario en el sistema y devuelve un token JWT.

*   **URL:** `/auth/register`
*   **Método:** `POST`
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "username": "nuevo@ejemplo.com",
      "password": "password123",
      "firstname": "Nombre",
      "lastname": "Apellido",
      "country": "Pais"
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

### Empresas (Companies)

#### Listar empresas
Obtiene todas las empresas registradas. Requiere rol ADMIN y ser superusuario.

*   **URL:** `/api/companies`
*   **Método:** `GET`
*   **Respuesta Exitosa (200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "Empresa A",
        "superEmpresa": false
      },
      {
        "id": 2,
        "name": "ODIS",
        "superEmpresa": true
      }
    ]
    ```

#### Obtener empresa por ID
Busca una empresa específica por su identificador.

*   **URL:** `/api/companies/{id}`
*   **Método:** `GET`
*   **Parámetros de URL:** `id` (Integer)
*   **Respuesta Exitosa (200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "Empresa A",
        "superEmpresa": false
      }
    ]
    ```

#### Crear empresa
Registra una nueva empresa en el sistema. Requiere rol ADMIN y ser superusuario.

*   **URL:** `/api/companies`
*   **Método:** `POST`
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "name": "Nueva Empresa",
      "superEmpresa": false
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "id": 3,
      "name": "Nueva Empresa",
      "superEmpresa": false
    }
    ```

#### Actualizar empresa
Modifica los datos de una empresa existente. Requiere rol ADMIN y ser superusuario.

*   **URL:** `/api/companies/{id}`
*   **Método:** `PUT`
*   **Parámetros de URL:** `id` (Integer)
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "name": "Empresa Actualizada"
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "id": 3,
      "name": "Empresa Actualizada",
      "superEmpresa": false
    }
    ```

#### Eliminar empresa
Elimina una empresa del sistema por su ID. Requiere rol ADMIN y ser superusuario.

*   **URL:** `/api/companies/{id}`
*   **Método:** `DELETE`
*   **Parámetros de URL:** `id` (Integer)
*   **Respuesta Exitosa (200 OK):** `true`

### Plantas (Plants)

#### Listar plantas
Obtiene todas las plantas visibles para el usuario actual.

*   **URL:** `/api/plants`
*   **Método:** `GET`
*   **Respuesta Exitosa (200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "Planta 1",
        "token": "...",
        "ubication": "Ubicacion 1",
        "vpnIp": "10.0.0.1",
        "ipVnc": "192.168.1.10",
        "conections": [ ... ],
        "company": { ... }
      }
    ]
    ```

#### Obtener planta por ID
Busca una planta por su ID. Requiere permisos de acceso a la planta.

*   **URL:** `/api/plants/{id}`
*   **Método:** `GET`
*   **Parámetros de URL:** `id` (Integer)
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "id": 1,
      "name": "Planta 1",
      "token": "...",
      "ubication": "Ubicacion 1",
      "vpnIp": "10.0.0.1",
      "ipVnc": "192.168.1.10",
      "conections": [ ... ],
      "company": { ... }
    }
    ```

#### Listar plantas por empresa
Obtiene todas las plantas asociadas a un ID de empresa específico.

*   **URL:** `/api/plants/company/{companyId}`
*   **Método:** `GET`
*   **Parámetros de URL:** `companyId` (Integer)
*   **Respuesta Exitosa (200 OK):**
    ```json
    [
      {
        "id": 1,
        "name": "Planta 1",
        ...
      }
    ]
    ```

#### Crear planta
Registra una nueva planta. Retorna el código de acceso para la planta. Requiere rol ADMIN y ser superusuario.

*   **URL:** `/api/plants`
*   **Método:** `POST`
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "name": "Nueva Planta",
      "ubication": "Ubicacion X",
      "vpnIp": "10.0.0.5",
      "ipVnc": "192.168.1.50",
      "company": "NombreEmpresa"
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "id": 5,
      "name": "Nueva Planta",
      ...
    }
    ```

#### Actualizar planta
Modifica los datos de una planta existente. Requiere rol ADMIN y ser superusuario.

*   **URL:** `/api/plants/{id}`
*   **Método:** `PUT`
*   **Parámetros de URL:** `id` (Integer)
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "name": "Planta Actualizada",
      "ubication": "Nueva Ubicacion",
      "vpnIp": "10.0.0.6",
      "ipVnc": "192.168.1.60",
      "company": "NombreEmpresa"
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "id": 5,
      "name": "Planta Actualizada",
      ...
    }
    ```

#### Eliminar planta
Elimina una planta del sistema. Requiere rol ADMIN y ser superusuario.

*   **URL:** `/api/plants/{id}`
*   **Método:** `DELETE`
*   **Parámetros de URL:** `id` (Integer)
*   **Respuesta Exitosa (204 No Content)**

### Conexiones (Connections)

#### Obtener conexión por token
Busca y retorna una conexión válida utilizando su token de acceso.

*   **URL:** `/api/connections`
*   **Método:** `GET`
*   **Parámetros de Query:** `token` (String)
*   **Respuesta Exitosa (200 OK):**
    ```json
    {
      "id": 1,
      "token": "...",
      "access": "ADMIN_ACCESS",
      "lastConexion": "...",
      "isActive": true,
      "expirationTime": "..."
    }
    ```

#### Crear conexión
Genera una nueva conexión basada en la solicitud proporcionada.

*   **URL:** `/api/connections`
*   **Método:** `POST`
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "plantId": 1,
      "level": "ADMIN_ACCESS",
      "expirationTime": "2024-12-31T23:59:59"
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    "token_generado_..."
    ```

#### Actualizar conexión
Actualiza los parámetros de una conexión existente identificada por su token.

*   **URL:** `/api/connections/update`
*   **Método:** `PUT`
*   **Parámetros de Query:** `token` (String)
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "level": "VIEWER_ACCESS",
      "expirationTime": "2025-01-01T00:00:00"
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    "Conexion Actualizada"
    ```

#### Eliminar conexión
Invalida o elimina una conexión activa utilizando su token.

*   **URL:** `/api/connections/delete`
*   **Método:** `DELETE`
*   **Parámetros de Query:** `token` (String)
*   **Respuesta Exitosa (200 OK):**
    ```json
    "Conexion Eliminada"
    ```

#### Conectar
Establece una conexión activa y retorna el token de la planta.

*   **URL:** `/api/connections/conect`
*   **Método:** `POST`
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "plantId": 1
    }
    ```
*   **Respuesta Exitosa (200 OK):**
    ```json
    "token_de_planta_..."
    ```

#### Desconectar
Finaliza una conexión activa.

*   **URL:** `/api/connections/disconnect`
*   **Método:** `POST`
*   **Cuerpo de la Solicitud (JSON):**
    ```json
    {
      "plantId": 1
    }
    ```
*   **Respuesta Exitosa (200 OK):** (Sin contenido)
