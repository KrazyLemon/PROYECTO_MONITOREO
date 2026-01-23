import { Outlet, Link } from "react-router-dom"; // Importamos navegación
import { Icon } from "@iconify/react"; // Importamos iconos
import { useState } from "react";
import { useAuth } from "../context/AuthContext";

const pages = [
  { name: "Inicio", path: "/dashboard",icon: "mdi:home-variant" },
  { name: "Usuarios", path: "/user", icon: "mdi:users" },
  { name: "Reportes", path: "/reports", icon: "mdi:file-report" },
];

const getActualPage = () => {
  const path = window.location.pathname;
  const page = pages.find((p) => p.path === path);
  return page ? page.name : "Dashboard";
};


export default function Navbar() {
  //const currentPage = getActualPage();
  const [isOpen, setIsOpen] = useState(false);
  const { logout } = useAuth();

  const handleLogout = (e) =>{
    e.preventDefault();
    logout();
  }

  return (
    <div className="drawer lg:drawer-open">
      <input id="my-drawer-4" type="checkbox" className="drawer-toggle" />
      <div className="drawer-content">
        {/* Navbar */}
        <nav className="navbar bg-white shadow-sm mx-auto">
          <div className="navbar-start">
            <label
              htmlFor="my-drawer-4"
              aria-label="open sidebar"
              className="btn btn-square btn-ghost"
            >
              {/* Sidebar toggle icon */}
              <input
                id="my-drawer-4"
                type="checkbox"
                className="drawer-toggle"
                hidden
                checked={isOpen}
                onChange={(e) => setIsOpen(e.target.checked)}
              />
              <Icon icon="mdi:menu" width="24" height="24" />
            </label>
          </div>
          <div className="navbar-center">
            <img src="/logo.png" alt="Logo" className="h-10 " />
          </div>
          <div className="navbar-end dropdown dropdown-end relative ">
            <Icon icon="line-md:bell" width="24" height="24" className="me-4" />
            <div
              tabIndex={0}
              role="button"
              className="btn btn-ghost btn-circle avatar avatar-placeholder"
            >
              <div className="w-10 rounded-full border border-base-300 bg-base-200 flex items-center justify-center">
                <span className="text-xl">A</span>
              </div>
            </div>
            <ul
              tabIndex="-1"
              className="menu menu-sm dropdown-content bg-base-100 rounded-box z-50 mt-3 w-52 p-2 shadow absolute right-0 top-full"
            >
              <li>
                <a href="#" className="justify-between">
                  Perfil <span className="badge bg-accent-content">New</span>
                </a>
              </li>
              <li>
                <button onClick={handleLogout} >Cerrar Sesión</button>
              </li>
            </ul>
          </div>
        </nav>
        {/* Page content here */}
        <div className="p-2">
          <Outlet />
        </div>
      </div>

      <div className="drawer-side is-drawer-close:overflow-visible">
        <label
          htmlFor="my-drawer-4"
          aria-label="close sidebar"
          className="drawer-overlay"
        ></label>
        <div className="flex min-h-full flex-col justify-between bg-base-200 is-drawer-close:w-14 is-drawer-open:w-40">
          {/* Sidebar content here */}
          <ul className="menu">
            {pages.map((page) => (
              <li key={page.path}>
                <Link
                  to={page.path}
                  className="is-drawer-close:tooltip is-drawer-close:tooltip-right tooltip-secondary"
                  data-tip={page.name}
                >
                  <Icon icon={page.icon} width="24" height="24" />
                  <span className="is-drawer-close:hidden">{page.name}</span>
                </Link>
              </li>
            ))}
          </ul>
          <ul className="menu">
            <li>
              <Link
                to = "/settings"
                className="is-drawer-close:tooltip is-drawer-close:tooltip-right tooltip-secondary"
                data-tip="Ajustes"
              >
                <Icon icon="mdi:settings" width={24} height={24} />
                <span className="is-drawer-close:hidden">Ajustes</span>
              </Link>
            </li>
          </ul>
          
        </div>
      </div>
    </div>
  );
}
