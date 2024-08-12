import React, { useState, useEffect } from 'react';
import api from '../api';
import { User } from '../interfaces/Reimbursement';
import '../styles/Users.css'; // Import the CSS file

const Users: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        setIsLoading(true);
        const response = await api.get('/users');
        setUsers(response.data);
        setIsLoading(false);
      } catch (err) {
        setError('Failed to fetch users');
        setIsLoading(false);
      }
    };

    fetchUsers();
  }, []);

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h2>All Users</h2>
      <ul className="user-list">
        {users.map(user => (
          <li key={user.userId} className="user-item">
            <h3>Name: {user.firstName} {user.lastName}</h3>
            <p><strong>Username:</strong> {user.username}</p>
            <p><strong>Role:</strong> {user.role}</p>
            <button onClick={() => handleDeleteUser(user.userId)}>Delete User</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

const handleDeleteUser = async (userId: number) => {
  try {
    await api.delete(`/users/${userId}`);
    alert('User deleted successfully');
    window.location.reload(); // Reload the page to reflect changes
  } catch (error) {
    alert('Failed to delete user');
  }
};

export default Users;
