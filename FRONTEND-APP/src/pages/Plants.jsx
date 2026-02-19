import { useAuth } from "../context/AuthContext";
import { Icon } from "@iconify/react";
import { useState, useEffect } from "react";

export default function Plants() {
  const [plants, setPlants] = useState([]);
  const [loading, setLoading] = useState(true);
  const { getAllPlants } = useAuth();

  const getPlants = async () => {
    try {
      const response = await getPlants();
      console.log(response);
      //setPlants(response.data)
      console.log(plants);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    try {
      getPlants();
      setLoading(false);
    } catch (error) {
      console.error(error);
    }
  }, []);

  return (
    <>
      <nav className="bg-white flex justify-between p-4 mb-2 shadow-sm border-white/20 rounded-md">
        <div className="flex gap-2 justify-start">
          <button className="btn tooltip tooltip-top" data-tip="Agregar Planta">
            <Icon icon="mdi:plus" width="24" height="24" />
          </button>
          {/* <button className="btn tooltip tooltip-top " data-tip="Eliminar Planta">
            <Icon icon="mdi:delete" width="24" height="24" />
          </button> */}
        </div>
        <div className="flex gap-2">
          <button className="btn btn-neutral tooltip" data-tip="Crear conexion">
            Conexion
          </button>
          <div className="flex border rounded-md px-2 items-center">
            <Icon icon="mdi:magnify" width="24px" height="24px" />
            <input
              type="text"
              placeholder="Buscar usuario"
              className="focus:outline-none"
            />
          </div>
        </div>
      </nav>
      <div className="overflow-x-auto bg-white shadow-sm border-white/20 rounded-md w-full">
        <table className="table table-zebra w-full">
          <thead>
            <tr className=" bg-gray-300">
              <th className="w-[2%] ">#</th>
              <th className="w-[8%] ">Nombre</th>
              <th className="w-[8%] ">Cliente</th>
              <th className="w-[8%] ">Tipo de planta</th>
              <th className="w-[26%] ">Conexiones</th>
              <th className="w-[20%] ">Acciones</th>
            </tr>
          </thead>
          
        </table>
      </div>
    </>
  );
}
