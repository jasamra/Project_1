import React from 'react';
import { useNavigate } from 'react-router-dom';


const Dashboard: React.FC = () => {
  const navigate = useNavigate(); // Declare navigate using useNavigate hook

const handleLogout = () => {
    localStorage.removeItem('role'); // Clear the role from localStorage
    navigate('/login'); // Redirect to the login page
  };


  return (
    <div className="dashboard">
      <h1>Employee Dashboard</h1>
      <p>This is your dashboard where you can see all your reimbursements and perform actions.</p>
      <div>
        {/* Employees should only see their own reimbursements */}
        <button onClick={() => navigate('/reimbursements')}>View Your Reimbursements</button>
        <button onClick={() => navigate('/reimbursements/status/PENDING')}>View Your Pending Reimbursements</button>
        {/* Manager-specific buttons are hidden */}
        <button onClick={handleLogout}>Logout</button>
      </div>
    </div>
  );
};

export default Dashboard;