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
    <div className="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-1 lg:grid-cols-2 gap-2">
      <VncViewer
        title="PLANTA DE PRUEBA 2"
        url="wss://monitor.odis.com.mx/websockify/localhost-dev-pc?token=n4OqKgn98xYRACGOScjRuflwYGS9V2nwlgMRlTh0YWY="
        viewOnly={false}
      />
      <VncViewer
        title="PLANTA DE PRUEBA 1"
        url="wss://monitor.odis.com.mx/websockify/DEBUG_PC?token=OBEGCnYa6TFZRt9r3yylqmd1az6ebMaR1e1V4u2oCdc="
        viewOnly={true}
      />
    </div>
  );
}
