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
        url="wss://monitor.odis.com.mx/websockify/localhost-dev-pc?token=n4OqKgn98xYRACGOScjRuflwYGS9V2nwlgMRlTh0YWY="
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

      <VncViewer
        url="wss://monitor.odis.com.mx/websockify/localhost-dev-pc?token=n4OqKgn98xYRACGOScjRuflwYGS9V2nwlgMRlTh0YWY="
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
  );
}
