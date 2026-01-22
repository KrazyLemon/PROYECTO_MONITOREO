import { useEffect,useRef } from "react";
import { useAuth } from "../context/AuthContext";
import VncViewer from "../components/VncViewer";
//import { VncScreen } from "react-vnc";

export default function Dashboard() {
  const { user, fetchUserData } = useAuth();
  //const ref = useRef(null);

  useEffect(() => {
    if (!user) {
      fetchUserData();
    }
  }, [user, fetchUserData]);

  return (
    <>
      <div className="bg-white mt-2 p-2 rounded-md shadow-sm">
        <h1 className="text-xl font-semibold">Bienvenido al Panel de Monitoreo</h1>
        <p className="text-xs text-gray-600">
          Aquí puedes supervisar y gestionar tus plantas de manera eficiente.
        </p>
      </div>
      <div className="bg-white mt-2 p-2 rounded-md shadow-sm overflow-hidden h-100 w-1/3">
        <VncViewer
          url="wss://monitor.odis.com.mx/websockify/?token=contenedor_dev_v1"
          viewOnly={false}
          onConnect={() => {
            console.log("Conexion correcta");
          }}
          onDisconnect={() => {
            console.log("Desconectandose del Hmi");
          }}
          onCredentialsRequired={() => {
            console.log("Contraseña necesaria");
          }}
          onBell={() => {
            console.log("Campana jijiji");
          }}
        />
        
      </div>
    </>
  );
}