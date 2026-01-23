import { createContext, useContext, useEffect, useState } from "react";
import { isTokenValid } from "../utils/auth";
import api from "../api/api";
const AuthContext = createContext();


export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [username, setUsername] = useState(null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);


  // Función para iniciar sesión y almacenar el token
   const login = async (username, password) => {
    try {
      //console.log("Intentando iniciar sesión con:", {username, password });
      const response = await api.post('/auth/login', {username, password});  
      setToken(response.data.token);
      setUsername(username);
      //console.log("Token almacenado en el contexto:", response.data.token);
      localStorage.setItem('token', response.data.token);
    } catch (error) {
      //console.error("Error al iniciar sesión:", error); 
      alert('Error al iniciar sesión. Por favor, verifica tus credenciales.');
    }
  };

  // Función para cerrar sesión
  const logout = () => {
    localStorage.removeItem('token');
    setUser(null);
    setToken(null);
    setUsername(null);
  }; 

  // Función para obtener los datos del usuario requiere primero un TOKEN válido y username
  const fetchUserData = async () => {
    try {
      //console.log("Obteniendo datos del usuario para:", username);
      const response = await api.get(`api/users/me/${username}`, { headers: { Authorization: `Bearer ${token}`}});
      setUser(response.data);
      //console.log("Datos del usuario obtenidos:", response.data);
    } catch (error) {
      //console.error("Error al obtener los datos del usuario:", error);
      alert('Error al obtener los datos del usuario.');
      logout();
    }   
  };

  // Función para registrar un nuevo usuario
  const register = async (userData) => {
    try {
      const response = await api.post('/auth/register', userData);
      setToken(response.data.token);
      setUsername(userData.username);
      localStorage.setItem('token', response.data.token);
      return response.data;
    } catch (error) {
      console.error("Error al registrar usuario:", error);
      throw error;
    }
  };

  // Funciones para Empresas
  const getCompanies = async () => {
    try {
      const response = await api.get('/api/companies', { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al obtener empresas:", error);
      throw error;
    }
  };

  const getCompanyById = async (id) => {
    try {
      const response = await api.get(`/api/companies/${id}`, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al obtener empresa:", error);
      throw error;
    }
  };

  const createCompany = async (companyData) => {
    try {
      const response = await api.post('/api/companies', companyData, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al crear empresa:", error);
      throw error;
    }
  };

  const updateCompany = async (id, companyData) => {
    try {
      const response = await api.put(`/api/companies/${id}`, companyData, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al actualizar empresa:", error);
      throw error;
    }
  };

  const deleteCompany = async (id) => {
    try {
      const response = await api.delete(`/api/companies/${id}`, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al eliminar empresa:", error);
      throw error;
    }
  };

  // Funciones para Plantas
  const getPlants = async () => {
    try {
      const response = await api.get('/api/plants', { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al obtener plantas:", error);
      throw error;
    }
  };

  const getPlantById = async (id) => {
    try {
      const response = await api.get(`/api/plants/${id}`, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al obtener planta:", error);
      throw error;
    }
  };

  const getPlantsByCompany = async (companyId) => {
    try {
      const response = await api.get(`/api/plants/company/${companyId}`, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al obtener plantas por empresa:", error);
      throw error;
    }
  };

  const createPlant = async (plantData) => {
    try {
      const response = await api.post('/api/plants', plantData, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al crear planta:", error);
      throw error;
    }
  };

  const updatePlant = async (id, plantData) => {
    try {
      const response = await api.put(`/api/plants/${id}`, plantData, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al actualizar planta:", error);
      throw error;
    }
  };

  const deletePlant = async (id) => {
    try {
      await api.delete(`/api/plants/${id}`, { headers: { Authorization: `Bearer ${token}` } });
    } catch (error) {
      console.error("Error al eliminar planta:", error);
      throw error;
    }
  };

  // Funciones para Conexiones
  const getConnectionByToken = async (tokenParam) => {
    try {
      const response = await api.get('/api/connections', { 
        headers: { Authorization: `Bearer ${token}` },
        params: { token: tokenParam }
      });
      return response.data;
    } catch (error) {
      console.error("Error al obtener conexión:", error);
      throw error;
    }
  };

  const createConnection = async (connectionData) => {
    try {
      const response = await api.post('/api/connections', connectionData, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al crear conexión:", error);
      throw error;
    }
  };

  const updateConnection = async (tokenParam, connectionData) => {
    try {
      const response = await api.put('/api/connections/update', connectionData, { 
        headers: { Authorization: `Bearer ${token}` },
        params: { token: tokenParam }
      });
      return response.data;
    } catch (error) {
      console.error("Error al actualizar conexión:", error);
      throw error;
    }
  };

  const deleteConnection = async (tokenParam) => {
    try {
      const response = await api.delete('/api/connections/delete', { 
        headers: { Authorization: `Bearer ${token}` },
        params: { token: tokenParam }
      });
      return response.data;
    } catch (error) {
      console.error("Error al eliminar conexión:", error);
      throw error;
    }
  };

  const connect = async (plantId) => {
    try {
      const response = await api.post('/api/connections/conect', { plantId }, { headers: { Authorization: `Bearer ${token}` } });
      return response.data;
    } catch (error) {
      console.error("Error al conectar:", error);
      throw error;
    }
  };

  const disconnect = async (plantId) => {
    try {
      await api.post('/api/connections/disconnect', { plantId }, { headers: { Authorization: `Bearer ${token}` } });
    } catch (error) {
      console.error("Error al desconectar:", error);
      throw error;
    }
  };

  const getAllUsers = async () =>{
    try{
      const response =  await api.get("/api/users",{headers : { Authorization: `Bearer ${token}` }})
      return response;
    }catch (error) {
      console.error("Error al obtener usuarios:", error);
    }
  };

  const getUserById = async (id) => {
     try{
      const response = await api.get("/api/users", { id }, { headers : { Authorization: `Bearer ${token}` }})
      return response;
    }catch (error) {
      console.error("Error al obtener usuario:", error);
    }
  };

  // const updateUSer = async (id, usuario) => {
  //   try {
  //     await api.put("/api/users", { id }, { headers : { Authorization: `Bearer ${token}` }})
  //   } catch (error) {
      
  //   }
  // }

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    // Validamos si existe y no ha expirado (usando la función que vimos antes)
    if (storedToken && isTokenValid(storedToken)) {
      setToken(storedToken);
    } else {
      logout();
    }
    setLoading(false); // <--- Esto le dice a la app que ya terminamos de revisar
  }, [token]);

  return (
    <AuthContext.Provider 
      value={{ 
        token, 
        user, 
        login, 
        logout, 
        register,
        fetchUserData, 
        getCompanies,
        getCompanyById,
        createCompany,
        updateCompany,
        deleteCompany,
        getPlants,
        getPlantById,
        getPlantsByCompany,
        createPlant,
        updatePlant,
        deletePlant,
        getConnectionByToken,
        createConnection,
        updateConnection,
        deleteConnection,
        connect,
        disconnect,
        getAllUsers,
        getUserById,
        isAuthenticated: !!token, 
        loading }} >
          {children}
    </ AuthContext.Provider>
  );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => useContext(AuthContext);
