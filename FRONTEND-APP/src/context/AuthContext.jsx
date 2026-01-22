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
        fetchUserData, 
        isAuthenticated: !!token, 
        loading }}>
      {children}
    </AuthContext.Provider>
  );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => useContext(AuthContext);
