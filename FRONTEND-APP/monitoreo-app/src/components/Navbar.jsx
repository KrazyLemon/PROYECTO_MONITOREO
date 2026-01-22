import { Outlet } from "react-router-dom"; // Importamos navegación
import { Icon } from "@iconify/react"; // Importamos iconos

const pages = [
  { name: "Plantas", path: "/dashboard" },
  { name: "Usuarios", path: "/user" },
  { name: "Reportes", path: "/reports" },
];

const getActualPage = () => {
  const path = window.location.pathname;
  const page = pages.find((p) => p.path === path);
  return page ? page.name : "Dashboard";
}

export default function Navbar() {
  const currentPage = getActualPage();

  return (
    <div className="p-4 mx-auto">
      <div className="navbar bg-white shadow-sm  mx-auto rounded-md">
        <div className="navbar-start">
          <div className="dropdown">
            <div tabIndex={0} role="button" className="btn btn-ghost lg:hidden">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-5 w-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M4 6h16M4 12h8m-8 6h16"
                />
              </svg>
            </div>
            <ul
              tabIndex="-1"
              className="menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow"
            >
              {pages.map((page) => (
                <li key={page.path}>
                  <a href={page.path} className={currentPage === page.name ? "active text-primary font-bold" : ""}>{page.name}</a>
                </li>
              ))}
            </ul>
          </div>
          <img src="/logo.png" alt="Logo" className="h-10 hidden lg:block ml-2" />
        </div>

        <div className="navbar-center lg:hidden">
          <img src="/logo.png" alt="Logo" className="h-10" />
        </div>
        <div className="navbar-center hidden lg:flex">
          <ul className="menu menu-horizontal px-1">
            {pages.map((page) => (
              <li key={page.path}>
                <a href={page.path} className={currentPage === page.name ? "active border-b-2 border-primary text-primary font-bold" : ""}>{page.name}</a>
              </li>
            ))}
          </ul>
        </div>

        <div className="navbar-end dropdown dropdown-end relative ">
          <Icon icon="line-md:bell" width="24" height="24" className="me-4"/>
          <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar avatar-placeholder">
            <div className="w-10 rounded-full border border-base-300 bg-base-200 flex items-center justify-center">
              <span className="text-xl">A</span>
            </div>
          </div>
          <ul tabIndex="-1" className="menu menu-sm dropdown-content bg-base-100 rounded-box z-50 mt-3 w-52 p-2 shadow absolute right-0 top-full">
            <li>
              <a href="#" className="justify-between">Perfil <span className="badge bg-accent-content">New</span></a>
            </li>
            <li>
              <a href="#">Ajustes</a>
            </li>
            <li>
              <a href="#">Cerrar Sesión</a>
            </li>
          </ul>
        </div>
      </div>
      <Outlet />
    </div>
  );
}
