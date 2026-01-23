import { VncScreen } from "react-vnc";
import { useState } from "react";

export default function VncViewer({
  url,
  viewOnly,
  onConnect,
  onDisconnect,
  onCredentialsRequired,
  onBell,
}) {
  const [connected, setConnected] = useState(false);

  return (
    <div className="bg-white rounded-md shadow-sm overflow-hidden relative w-full h-98 p-1">
      {!connected && <div className="absolute inset-0 skeleton rounded-md" />}
      <div
        className={`w-full h-full transition-opacity duration-300 ${connected ? "opacity-100" : "opacity-0"}`}
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
    </div>
  );
}
