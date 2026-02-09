import { VncScreen } from "react-vnc";
import { useState, useRef } from "react";
import { Icon } from "@iconify/react";

export default function VncViewer({
  url,
  viewOnly,
  onConnect,
  onDisconnect,
  onCredentialsRequired,
  onBell,
}) {
  const [connected, setConnected] = useState(false);
  const [showToolbar, setShowToolbar] = useState(false);
  const screenRef = useRef(null);

  const handleScreenshot = () => {
    if (screenRef.current) {
      const canvas = screenRef.current.querySelector("canvas");
      if (canvas) {
        const link = document.createElement("a");
        link.download = `screenshot-${new Date().toISOString().replace(/[:.]/g, "-")}.png`;
        link.href = canvas.toDataURL();
        link.click();
      }
    }
  };

  const handleNewScreen = () => {
    const targetUrl = new URL(window.location.href);
    targetUrl.searchParams.set("vncUrl", url);
    window.open(
      targetUrl.toString(),
      "_blank",
      "toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=1024,height=768"
    );
  };

  return (
    <div className="bg-white/0 rounded-md shadow-sm overflow-hidden relative w-full h-95">
      {!connected && (
        <div className="absolute inset-0 skeleton rounded-md z-10" />
      )}
      <div
        ref={screenRef}
        className={`w-full h-full relative transition-opacity duration-300 z-10 ${connected ? "opacity-100" : "opacity-0"}`}
      >
        <VncScreen
          url={url}
          scaleViewport
          style={{
            width: "100%",
            height: "100%",
          }}
          viewOnly={viewOnly}
          backgroundColor="#fff"
          onConnect={() => setConnected(true)}
          onDisconnect={() => setConnected(false)}
          onCredentialsRequired={onCredentialsRequired}
          onBell={onBell}
        />
      </div>
      {/* Tool bar for the VNCScreen */}
      {viewOnly ? (
        <div className="absolute top-0 left-0 z-20 rounded-md p-2 flex flex-col items-center justify-center text-white/0 hover:bg-black/40 hover:text-white  w-full h-full">
            <Icon icon="mdi:lock-outline" width={50} height={50} />
            <h1> No tiene permisos para operar este dispositivo</h1>
        </div>
      ) : (
        <div
          className={`absolute top-0 left-0 z-20 w-full transition-transform duration-300 ${showToolbar ? "translate-y-0" : "-translate-y-full"}`}
        >
          <div className="bg-black/60 backdrop-blur-sm p-2 w-full flex justify-end items-center text-white gap-1 relative shadow-md">
            <div>
              <button onClick={handleScreenshot} className="hover:bg-black/80 rounded-full p-1" title="Captura de pantalla">
                <Icon icon="mdi:camera" width={20} height={20} />
              </button>
              <button className="hover:bg-black/80 rounded-full p-1" onClick={handleNewScreen}>
                <Icon icon="mdi:open-in-new" width={20} height={20} />
              </button>
            </div>

            {/* Pestaña para desplegar/ocultar */}
            <button
              onClick={() => setShowToolbar(!showToolbar)}
              className="absolute -bottom-6 left-1/2 -translate-x-1/2 bg-black/60 backdrop-blur-sm text-white rounded-b-md px-6 h-6 flex items-center justify-center hover:bg-black/80 transition-colors shadow-sm"
            >
              <Icon
                icon={showToolbar ? "mdi:chevron-up" : "mdi:chevron-down"}
                width={20}
                height={20}
              />
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
