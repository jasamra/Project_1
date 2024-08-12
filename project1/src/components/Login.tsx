import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api'; // Axios instance
import "../styles/Login.css"

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  // This is the handleLogin function
  const handleLogin = async (event: React.FormEvent) => {
    event.preventDefault();

    try {
      const response = await api.post('/auth', { username, password });
      const user = response.data;

      if (user && user.role) {
        localStorage.setItem('role', user.role); // Store the role in localStorage
		localStorage.setItem('userId', user.userId); // Store the userId in localStorage
        if (user.role === 'manager') {
          navigate('/manager'); // Redirect to manager dashboard
        } else {
          navigate('/dashboard'); // Redirect to regular dashboard
        }
      } else {
        alert('Login failed: User data not received');
      }
    } catch (error) {
      console.error('Login error:', error);
      alert('Login failed');
    }
  };

return (
    <div className="login-container">
      <h1 className="title">Employee Reimbursement System</h1>
      <form onSubmit={handleLogin} className="login-form">
        <div className="input-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="login-button">Login</button>
      </form>
      <div className="register-container">
        <p>Don't have an account?</p>
        <button onClick={() => navigate('/register')} className="register-button">
          Register
        </button>
      </div>
    </div>
  );
};

export default Login;