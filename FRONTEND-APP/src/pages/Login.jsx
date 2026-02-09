import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { Icon } from "@iconify/react";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      //console.log("Intentando iniciar sesión con:", {username, password });
      await login(username, password);
      navigate("/dashboard");
    } catch (error) {
      //console.error("Error al iniciar sesión:", error);
      alert("Error al iniciar sesión. Por favor, verifica tus credenciales.");
    }
  };

  const handleEnter = (e) => {
    if (e.key === "Enter") {
      console.log("Enter presionado");
      handleSubmit(e);
    }
  };

  return (
    <div className="w-full h-screen flex flex-col justify-around items-center">
      <div className="mx-auto w-3/5 lg:w-1/5 bg-white border-base-200 rounded-box shadow-xl p-4">
        <img src="/logo.png" alt="Logo" className="mb-2 mx-auto" />
        <h1 className="text-cyan-600 font-semibold w-full flex justify-center">
          SISTEMA DE MONITOREO
        </h1>
        <fieldset className="fieldset w-sx mt-2 border p-2 rounded-xl border-base-300">
          <legend className="fieldset-legend">Iniciar Sesión</legend>
          <label htmlFor="username" className="label font-semibold">
            Correo electronico:
          </label>
          <input
            type="email"
            id="username"
            required
            className="input validator input-bordered w-full"
            onChange={(e) => setUsername(e.target.value)}
          />
          <label htmlFor="password" className="label font-semibold">
            Contraseña:
          </label>
          <div className="relative">
            <input
              type={showPassword ? "text" : "password"}
              id="password"
              required
              className="input input-bordered w-full pr-10"
              onChange={(e) => setPassword(e.target.value)}
              onKeyDown={handleEnter}
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="absolute right-3 top-1/2 -translate-y-1/2 text-base-content opacity-60 hover:opacity-100"
            >
              <Icon
                icon={showPassword ? "mdi:eye-off" : "mdi:eye"}
                width="20"
              />
            </button>
          </div>
          
          <a href="" className="link-hover flex w-full justify-end">
            ¿Olvidaste tu contraseña?
          </a>
          <label className="label">
            <input
              type="checkbox"
              defaultChecked
              className="checkbox checkbox-sm checkbox-primary"
            />
            Recuerdame
          </label>
          <button className="btn btn-primary mt-4" onClick={handleSubmit}>
            Ingresar
          </button>
        </fieldset>
      </div>
    </div>
  );
}
