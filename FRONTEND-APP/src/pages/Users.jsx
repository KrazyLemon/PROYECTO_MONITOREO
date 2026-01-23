import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";

export default function User() {
  const { getAllUsers } = useAuth();
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  const getUsers = async () => {
    try {
      //console.log("Obtenieno Users...");
      const response = await getAllUsers();
      //console.log(response.data);
      setUsers(response.data);
    } catch (error) {
      console.error(error.data);
    }
  };

  useEffect(() => {
    try {
      getUsers();
      console.log(users);
      setLoading(false);
    } catch (error) {
      console.error(error);
    }
  }, []);

  return (
    <>
      <nav className="bg-white flex justify-between p-4 mb-2 shadow-sm border-white/20 rounded-md">
        <div className="flex gap-2 justify-start">
          <button className="btn btn-success">Crear</button>
          <button className="btn btn-error">Borrar</button>
        </div>
        <div className="flex gap-2">
          <input className="input" />
          <button className="btn btn-secondary">Conexion</button>
          <button className="btn btn-primary">Cambiar permisos</button>
        </div>
      </nav>
      <table className="table overflow-x-auto bg-white shadow-sm border-white/20">
        <thead>
          <tr>
            <th>#</th>
            <th>Nombre</th>
            <th>Correo Electronico</th>
            <th>Empresa</th>
            <th>Rol</th>
            <th>Conexiones</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr>
              <th>{user.id}</th>
              <th>
                {user.firstName} {user.lastName}
              </th>
              <th>{user.email}</th>
              <th>{user.company.name}</th>
              <th>{user.role}</th>
              <th>{user.conexiones?.id || "No hay conexiones"}</th>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
