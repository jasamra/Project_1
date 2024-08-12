import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

interface PrivateRouteProps {
  element: JSX.Element;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ element }) => {
  const { isAuthenticated } = useAuth(); // Use the Auth context to check if the user is authenticated

  // Render the element if authenticated, otherwise redirect to the login page
  return isAuthenticated ? element : <Navigate to="/login" replace />;
};

export default PrivateRoute;
