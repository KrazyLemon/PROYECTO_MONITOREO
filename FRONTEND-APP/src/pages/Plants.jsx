import { useAuth } from "../context/AuthContext";
import { Icon } from "@iconify/react";
import { useState, useEffect } from "react";

export default function Plants() {
  const [plants, setPlants] = useState();
  const [loading, setLoading] = useState(true);
  const {
    getPlants,
    getPlantById,
    getPlantsByCompany,
    deletePlant,
    updatePlant,
    createPlant,
  } = useAuth();

  const getAllPlants = async () => {
    try {
      const response = await getPlants();
      //console.log(response);
      setPlants(response);
      //console.log(plants);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      if (!id) {
        window.alert("Selecciona una planta para eliminar");
        return;
      }
      const confirm = window.confirm("¿Estas seguro de eliminar la planta?");
      if (!confirm) return;
      await deletePlant(id);
      getAllPlants(); // Refresh the list
    } catch (error) {
      window.alert("Error al eliminar la planta");
      console.error(error);
    }
  };

  const handleUpdate = async (id) => {
    // TODO: Implement update logic/modal
    console.log("Update plant", id);
  };

  const handleCreate = async () => {
    // TODO: Implement create logic/modal
    console.log("Create plant");
  };

  useEffect(() => {
    getAllPlants();
  }, []);

  return (
    <>
      <nav className="bg-white flex justify-between p-4 mb-2 shadow-sm border-white/20 rounded-md">
        <div className="flex gap-2 justify-start">
          <button
            className="btn tooltip tooltip-top"
            data-tip="Agregar Planta"
            onClick={handleCreate}
          >
            <Icon icon="mdi:plus" width="24" height="24" />
          </button>
        </div>
        <div className="flex gap-2">
          <div className="flex border rounded-md px-2 items-center">
            <Icon icon="mdi:magnify" width="24px" height="24px" />
            <input
              type="text"
              placeholder="Buscar planta"
              className="focus:outline-none"
            />
          </div>
        </div>
      </nav>

      {loading ? (
        <div className="flex justify-center p-10">
          <span className="loading loading-spinner loading-lg"></span>
        </div>
      ) : (
        <div className="overflow-x-auto bg-white shadow-sm border-white/20 rounded-md w-full">
          <table className="table table-zebra w-full">
            <thead>
              <tr className="bg-gray-300">
                <th className="w-[2%]">#</th>
                <th className="w-[15%]">Nombre</th>
                <th className="w-[15%]">Empresa / Cliente</th>
                <th className="w-[15%]">Tipo de Planta</th>
                <th className="w-[33%]">Token / Conexión</th>
                <th className="w-[20%]">Acciones</th>
              </tr>
            </thead>
            <tbody className="hover:bg-black/5">
              {!plants || plants.length === 0 ? (
                <tr>
                  <td colSpan="6" className="text-center py-4">
                    No se encontraron plantas.
                  </td>
                </tr>
              ) : (
                plants.map((plant) => (
                  <tr key={plant.id}>
                    <td>{plant.id}</td>
                    <td>{plant.name}</td>
                    <td>{plant.company?.name || "N/A"}</td>
                    <td>{plant.type || "Estándar"}</td>
                    <td>
                      <span className="font-mono text-xs opacity-60">
                        {plant.connectionToken || "Sin token"}
                      </span>
                    </td>
                    <td>
                      <div className="flex justify-start gap-2">
                        <button
                          className="btn btn-xs btn-info tooltip tooltip-top"
                          data-tip="Editar planta"
                          onClick={() => handleUpdate(plant.id)}
                        >
                          <Icon icon="mdi:pencil" width="20" height="20" />
                        </button>
                        <button
                          className="btn btn-xs btn-error tooltip tooltip-top tooltip-error"
                          data-tip="Borrar planta"
                          onClick={() => handleDelete(plant.id)}
                        >
                          <Icon icon="mdi:trash-can" width="20" height="20" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
    </>
  );
}
