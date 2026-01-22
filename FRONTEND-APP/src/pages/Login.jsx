import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      //console.log("Intentando iniciar sesión con:", {username, password });
      await login(username, password);
      navigate('/dashboard');
    } catch (error) {
      //console.error("Error al iniciar sesión:", error);
      alert('Error al iniciar sesión. Por favor, verifica tus credenciales.');
    }
  };

  return (
    <div className="w-full h-screen flex flex-col justify-around items-center">
      <div className="w-1/4 mx-auto">
        <img src="/logo.png" alt="Logo" className="mb-2 mx-auto" />
        <fieldset className="fieldset bg-base-200 border-base-300 rounded-box w-sx border p-4">
          <legend className="fieldset-legend">Iniciar Sesión</legend>
          <label htmlFor="username" className="label font-semibold">
            Nombre de Usuario o Email:
          </label>
          <input
            type="text"
            id="username"
            className="input w-full"
            onChange={(e) => setUsername(e.target.value)}
          />
          <label htmlFor="password" className="label font-semibold">
            Contraseña:
          </label>
          <input
            type="password"
            id="password"
            className="input w-full"
            onChange={(e) => setPassword(e.target.value)}
          />
          <a href="" className="link-hover"> ¿Olvidaste tu contraseña?</a>
            <button className="btn btn-primary mt-4" onClick={handleSubmit}>Ingresar</button>
        </fieldset>
      </div>
    </div>
  );
}
