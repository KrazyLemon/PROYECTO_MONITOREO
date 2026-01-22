import { VncScreen } from "react-vnc";
import { useEffect, useRef, useState } from "react";

export default function VncViewer({
  url,
  viewOnly,

  onConnect,
  onDisconnect,
  onCredentialsRequired,
  onBell,
}) {
  const [error, setError] = useState(null);
  const vncRef = useRef(null);

  useEffect(() => {
    if (!url) {
      setError("URL is required");
    } else {
      setError(null);
    }
  }, [url]);

  return (
    <VncScreen      
      url={url}
      scaleViewport
      style={{
        width: "100%",
        height: "100%",
      }}
      viewOnly={viewOnly}
      backgroundColor="#fff"
      onConnect={onConnect}
      onDisconnect={onDisconnect}
      onCredentialsRequired={onCredentialsRequired}
      onBell={onBell}
    />
  );
}
