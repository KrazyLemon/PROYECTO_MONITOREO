import { useEffect, useRef } from "react";
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
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-2">
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

      <VncViewer
        url="wss://monitor.odis.com.mx/websockify/?token=contenedor_dev_v1"
        viewOnly={true}
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
  );
}
