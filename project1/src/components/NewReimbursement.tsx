import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/NewReimbursement.css';

const NewReimbursement: React.FC = () => {
  const [description, setDescription] = useState('');
  const [amount, setAmount] = useState<number | ''>('');
  const [status, setStatus] = useState('PENDING');
  const navigate = useNavigate(); // Initialize navigate here

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

	// Retrieve the userId from localStorage
    const userIdString = localStorage.getItem('userId');
    
    if (!userIdString) {
      alert("User ID not found. Please log in again.");
      navigate('/login'); // Redirect to login if userId is not found
      return;
    }

    const userId = parseInt(userIdString);

    try {
      const response = await axios.post('http://localhost:8080/reimbursements', {
        description,
        amount,
        status,
		userId // This is now guaranteed to be a number
      });

      if (response.status === 201) {
        alert('Reimbursement created successfully');
        setDescription('');
        setAmount('');
        setStatus('PENDING');
      } else {
        alert('Failed to create reimbursement');
      }
    } catch (error) {
      console.error(error);
      alert('An error occurred while creating the reimbursement');
    }
  };

  return (
    <div className="new-reimbursement-container">
      <h2>Create New Reimbursement</h2>
      <form onSubmit={handleSubmit} className="new-reimbursement-form">
        <div className="input-group">
          <label htmlFor="description">Description:</label>
          <input
            type="text"
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="amount">Amount:</label>
          <input
            type="number"
            id="amount"
            value={amount}
            onChange={(e) => setAmount(Number(e.target.value))}
            required
          />
        </div>
        <button type="submit" className="create-button">Create</button>
      </form>
    </div>
  );
};

export default NewReimbursement;
