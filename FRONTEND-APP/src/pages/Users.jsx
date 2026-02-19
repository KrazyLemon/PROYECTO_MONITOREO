import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { Icon } from "@iconify/react";

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
      //console.error(error.data);
    }
  };

  useEffect(() => {
    try {
      getUsers();
      //console.log(users);
      setLoading(false);
    } catch (error) {
      //console.error(error);
    }
  }, []);

  return (
    <>
      <nav className="bg-white flex justify-between p-4 mb-2 shadow-sm border-white/20 rounded-md">
        <div className="flex gap-2 justify-start">
          <button
            className="btn tooltip tooltip-top "
            data-tip="Agregar usuario"
          >
            <Icon icon="mdi:user-add" width="24" height="24" />
          </button>
        </div>
        <div className="flex gap-2">
          <button className="btn btn-neutral">Conexion</button>
          <div className="flex border rounded-md px-2 items-center">
            <input  type="text" placeholder="Buscar usuario" className="focus:outline-none" />
            <Icon icon="mdi:magnify" width="24px" height="24px" />
          </div>
        </div>
      </nav>
      <div className="overflow-x-auto bg-white shadow-sm border-white/20 rounded-md w-full">
        <table className="table table-zebra w-full">
          <thead>
            <tr className=" bg-gray-300">
              <th className="w-[2%] ">#</th>
              <th className="w-[8%] ">Nombre</th>
              <th className="w-[8%] ">Correo Electronico</th>
              <th className="w-[8%] ">Empresa</th>
              <th className="w-[8%] ">Rol</th>
              <th className="w-[26%] ">Conexiones</th>
              <th className="w-[20%] ">Acciones</th>
            </tr>
          </thead>
          <tbody className="hover:bg-black/5">
            {users.map((user) => (
              <tr key={user.id}>
                <th className="w-[2%]">{user.id}</th>
                <th className="w-[8%]">
                  {user.firstName} {user.lastName}
                </th>
                <th className="w-[8%]">{user.email}</th>
                <th className="w-[8%]">{user.company.name}</th>
                <th className="w-[8%]">
                  {user.role === "ROLE_ADMIN" ? (
                    <span className="badge badge-warning badge-outline badge-sm">
                      <Icon icon="mdi:administrator" className="size-4" />
                      Administrador
                    </span>
                  ) : (
                    <span className="badge badge-success badge-outline badge-sm">
                      <Icon icon="mdi:account-group" className="size-4" />
                      Usuario
                    </span>
                  )}
                </th>
                <th className="w-[26%]">
                  <div className="flex flex-wrap gap-1">
                    {user.conections?.map((conn) => (
                      <span
                        key={conn.id}
                        className="badge badge-info badge-outline badge-sm"
                      >
                        {conn.plant?.name}
                      </span>
                    ))}
                  </div>
                </th>
                <th className="w-[20%]">
                  <div className="flex justify-start gap-2">
                    <button
                      className="btn btn-xs btn-info tooltip tooltip-top"
                      data-tip="Editar usuario"
                    >
                      <Icon icon="mdi:user-edit" width="20" height="20" />
                    </button>
                    <button
                      className="btn btn-xs btn-error tooltip tooltip-top tooltip-error"
                      data-tip="Borrar usuario"
                    >
                      <Icon icon="mdi:user-remove" width="20" height="20" />
                    </button>
                    <button
                      className="btn btn-xs bg-warning tooltip tooltip-top tooltip-warning text-white"
                      data-tip="Conectar a monitor"
                    >
                      <Icon icon="mdi:drop-plus" width="20" height="20" />
                    </button>
                  </div>
                </th>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
