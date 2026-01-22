import axios from 'axios';

const api = axios.create ({
    baseURL: 'http://localhost:8080'
})

api.interceptors.response.use(
  (response) => response, // Si la respuesta es OK, déjala pasar
  (error) => {
    // Si el servidor responde 401 (No autorizado) o 403 (Token inválido/vencido)
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      console.warn("Sesión expirada o inválida. Cerrando sesión...");
      
      // Limpiamos todo
      localStorage.removeItem('token');
      
      // Forzamos la redirección al login
      window.location.href = '/login'; 
    }
    return Promise.reject(error);
  }
);
export default api;