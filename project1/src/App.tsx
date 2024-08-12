import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import PrivateRoute from './components/PrivateRoute';
import { AuthProvider } from './context/AuthContext';
import Register from './components/Register';
import Reimbursements from './components/Reimbursement'
import Users from './components/Users';
import ManagerDashboard from './components/ManagerDashboard';
import ResolveReimbursements from './components/ResolveReimbursements';
import NewReimbursement from './components/NewReimbursement';

const App: React.FC = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Navigate to="/login" replace />} />
		  <Route path="/register" element={<Register />} />
		  <Route path="/manager" element={<ManagerDashboard />} />
		  <Route path="/dashboard" element={<Dashboard />} />
		  <Route path="/resolve-reimbursements" element={<ResolveReimbursements />} />
          <Route path="/users" element={<Users />} />
		  <Route path="/reimbursements" element={<Reimbursements />} />
		 <Route path="/new-reimbursement" element={<NewReimbursement />} />
		  <Route path="/reimbursements/status/PENDING" element={<Reimbursements status="PENDING" />} />
          
	
          {/* You can similarly protect other routes */}
        </Routes>
      </Router>
    </AuthProvider>
  );
};

export default App;
