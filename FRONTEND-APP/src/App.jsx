import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import ProtectedRoute from "./routes/ProtectedRoute";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import NotFound from "./pages/NotFound";
import Users from "./pages/Users";
import Plants from "./pages/Plants";


export default function App() {
  return (
    <AuthProvider>
     
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<Login />} />
            {/* Rutas protegidas */}
            <Route element={<ProtectedRoute />}>
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/users" element={<Users />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/plants" element = {<Plants />} />
              <Route path="/reports" element={<div> A1 </div>} />
              <Route path="/alarms" element={<div>A1</div>} />
              <Route path="/settings" element={<div>A1</div>} />
              <Route path="*" element={<NotFound />} />
            </Route>
          </Routes>
        </BrowserRouter>
      
    </AuthProvider>
  );
}
