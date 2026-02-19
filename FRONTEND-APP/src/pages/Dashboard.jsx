import { useState, useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import VncViewer from "../components/VncViewer";
import { Icon } from "@iconify/react";

export default function Dashboard() {
  const { user, fetchUserData } = useAuth();
  const [viewOnly, setViewOnly] = useState(true);

  useEffect(() => {
    if (!user) {
      fetchUserData();
    }
    console.log(user);
  }, [user, fetchUserData]);

  return (
    <>
      <div className="w-full flex justify-end items-center p-2 bg-white mb-2">
        <div className="flex gap-1">
          <div className="input">
            <input type="text" className="" />
            <button className="cursor-pointer">
              <Icon icon={"mdi:magnify"} width={24} height={24} />
            </button>
          </div>
          <button className="btn">
            <Icon icon={"mdi:filter"} width={24} height={24} />
          </button>
          <button className="btn">
            <Icon icon={"mdi:view-grid"} width={24} height={24} />
          </button>
        </div>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-1 lg:grid-cols-2 gap-2 p-4 bg-white ">
        <VncViewer
          title="PLANTA DE PRUEBA 1"
          url="wss://monitor.odis.com.mx/websockify/DEBUG_PC?token=OBEGCnYa6TFZRt9r3yylqmd1az6ebMaR1e1V4u2oCdc="
          viewOnly={viewOnly}
        />
      </div>
    </>
  );
}
