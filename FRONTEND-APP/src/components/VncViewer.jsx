import { useEffect, useRef, useState } from "react";
import { createRoot } from "react-dom/client";
import { VncScreen } from "react-vnc";
import { Icon } from "@iconify/react";

export default function VncViewer({ url, password, viewOnly, title }) {
  const vncRef = useRef(null);
  const intervalRef = useRef(null);
  const hideTimerRef = useRef(null);

  const [connected, setConnected] = useState(false);
  const [isViewOnly, setIsViewOnly] = useState(viewOnly);
  const [isLoading, setIsLoading] = useState(true);
  const [barsVisible, setBarsVisible] = useState(false);

  const connect = () => {
    vncRef.current?.connect();
  };

  const disconnect = () => {
    vncRef.current?.disconnect();
  };

  const reconnect = () => {
    disconnect();
    setTimeout(connect, 800);
  };

  const toggleViewOnly = () => {
    setIsViewOnly((prev) => !prev);
    reconnect();
  };

  const openInNewWindow = () => {
    const newWindow = window.open("", "_blank", "width=1200,height=800");

    if (!newWindow) return;

    newWindow.document.body.style.margin = "0";
    newWindow.document.title = "VNC Viewer";

    const container = newWindow.document.createElement("div");
    newWindow.document.body.appendChild(container);

    const root = createRoot(container);
    root.render(
      <VncPopup url={url} password={password} viewOnly={isViewOnly} />,
    );
  };

  useEffect(() => {
    connect();
    intervalRef.current = setInterval(
      () => {
        reconnect();
      },
      10 * 60 * 1000,
    );

    return () => {
      clearInterval(intervalRef.current);
      disconnect();
    };
  }, []);

  useEffect(() => {
    vncRef.current?.connect();
    return () => vncRef.current?.disconnect();
  }, []);

  useEffect(() => {
    return () => {
      if (hideTimerRef.current) {
        clearTimeout(hideTimerRef.current);
      }
    };
  }, []);

  return (
    <div className="relative overflow-hidden rounded-xl shadow-xl ">
      {/* BOTÓN FLOTANTE SIEMPRE VISIBLE */}
      <button
        onClick={() => setBarsVisible((v) => !v)}
        className={`absolute left-1/2 -translate-x-1/2 
                  transition-all duration-200
                  ${barsVisible ? "top-10" : "top-0"}
                  z-60
                  bg-black/40 text-white px-1 py-1 
                  rounded-b backdrop-blur`}
      >
        <Icon
          icon={barsVisible ? "mdi:chevron-up" : "mdi:chevron-down"}
          width={24}
        />
      </button>

      {/* VNC BASE (FONDO) */}
      <div className="relative z-0">
        <VncScreen
          ref={vncRef}
          showDotCursor
          url={url}
          credentials={{ password }}
          scaleViewport
          background="#000"
          viewOnly={isViewOnly}
          className="w-full h-100"
          onConnect={() => {
            setConnected(true);
            setIsLoading(false);
          }}
          onDisconnect={() => {
            setConnected(false);
            setIsLoading(true);
          }}
        />
      </div>

      {/* BARRA SUPERIOR */}
      <div
        className={`absolute top-0 left-0 w-full
                  bg-black/40 text-white 
                  flex justify-between items-center px-2 py-1
                  transition-transform duration-200
                  z-50
                  ${barsVisible ? "translate-y-0" : "-translate-y-full"}`}
      >
        <h1>{title}</h1>
        <div className="flex gap-1">
          <button onClick={reconnect} className="hover:bg-white/20 p-1 rounded">
            <Icon icon="mdi:refresh" width={24} />
          </button>
          <button
            onClick={openInNewWindow}
            className="hover:bg-white/20 p-1 rounded"
          >
            <Icon icon="mdi:arrow-expand-all" width={24} />
          </button>
          <button
            onClick={disconnect}
            className="hover:bg-white/20 p-1 rounded"
          >
            <Icon icon="mdi:close" width={24} />
          </button>
        </div>
      </div>

      {/* LOADER SOBRE TODO */}
      {isLoading && (
        <div className="absolute inset-0 bg-black/30 flex items-center justify-center text-white z-55">
          Conectando...
        </div>
      )}

      {/* BARRA INFERIOR */}
      <div
        className={`absolute bottom-0 left-0 w-full
                  bg-black/50 text-white 
                  flex justify-between items-center px-4 py-2
                  transition-transform duration-200
                  z-50
                  ${barsVisible ? "translate-y-0" : "translate-y-full"}`}
      >
        <span className="flex items-center gap-2">
          <span
            className={`w-2.5 h-2.5 rounded-full ${
              connected ? "bg-red-600 animate-pulse duration-500" : "bg-red-500/10"
            }`}
          />
          {connected ? "En Vivo" : "Desconectado"}
        </span>

        <div className="flex gap-2">
          <button
            onClick={toggleViewOnly}
            className="hover:bg-white/20 p-1 rounded"
          >
            {isViewOnly ? (
              <span className="flex gap-1 ">
                <Icon icon="mdi:eye-lock-outline" width={24} />
                <h6>Desbloquear escritura</h6>
              </span>
            ) : (
              <span className="flex gap-1">
                <Icon icon="mdi:eye-outline" width={24} />
                <h6>Bloquear escritura</h6>
              </span>
            )}
          </button>
        </div>
      </div>
    </div>
  );
}

function VncPopup({ url, password, viewOnly }) {
  const vncRef = useRef(null);

  useEffect(() => {
    vncRef.current?.connect();
    return () => vncRef.current?.disconnect();
  }, []);

  useEffect(() => {
    const el = document.documentElement;
    if (el.requestFullscreen) {
      el.requestFullscreen();
    }
  }, []);

  return (
    <VncScreen
      ref={vncRef}
      showDotCursor
      url={url}
      credentials={{ password }}
      scaleViewport
      background="#000"
      viewOnly={viewOnly}
      className="w-screen h-screen"
    />
  );
}
