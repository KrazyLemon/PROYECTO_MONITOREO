import {Navigate} from 'react-router-dom';
import {useAuth} from '../context/AuthContext'
import Navbar from '../components/Navbar';

const ProtectedRoute = () => {
    const {isAuthenticated, loading} = useAuth();
    if(loading) {
        return <h1>Cargando...</h1>;
    }
    return isAuthenticated ? <Navbar /> : <Navigate to="/login" replace />;
};
export default ProtectedRoute;