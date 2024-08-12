import React from 'react';
import { useNavigate } from 'react-router-dom';

const ManagerDashboard: React.FC = () => {
  const navigate = useNavigate();
  const role = localStorage.getItem('role'); // Retrieve the role from localStorage

  console.log('Current role:', role); // Debugging output

  if (role !== 'manager') {
    return <p>You do not have access to this page.</p>;
  }

   const handleLogout = () => {
    localStorage.removeItem('role'); // Clear the role from localStorage
    navigate('/login'); // Redirect to the login page
  };

  return (
    <div className="dashboard">
      <h1>Manager Dashboard</h1>
      <p>Manage users and reimbursements from here.</p>
      <div>
		<button onClick={() => navigate('/reimbursements')}>View All Reimbursements</button>
        <button onClick={() => navigate('/reimbursements/status/PENDING')}>View Pending Reimbursements</button>
        <button onClick={() => navigate('/users')}>View All Users</button>
		<button onClick={() => navigate('/resolve-reimbursements')}>Resolve Reimbursements</button>
		<button onClick={() => navigate('/new-reimbursement')}>Create Reimbursement</button>
 		<button onClick={handleLogout}>Logout</button> {/* Logout button */}
      </div>
    </div>
  );
};

export default ManagerDashboard;
