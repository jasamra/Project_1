import React, { useState, useEffect } from 'react';
import api from '../api';
import { Reimbursement } from '../interfaces/Reimbursement';
import '../styles/ResolveReimbursements.css'; // You can create and style this as needed

const ResolveReimbursements: React.FC = () => {
  const [reimbursements, setReimbursements] = useState<Reimbursement[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPendingReimbursements = async () => {
      try {
        setIsLoading(true);
        const response = await api.get('/reimbursements/status/PENDING');
        setReimbursements(response.data);
        setIsLoading(false);
      } catch (err) {
        setError('Failed to fetch data');
        setIsLoading(false);
      }
    };

    fetchPendingReimbursements();
  }, []);

  const handleResolve = async (reimbId: number, newStatus: string) => {
    try {
      const response = await api.put(`/reimbursements/${reimbId}/resolve`, { status: newStatus });
      if (response.status === 200) {
        alert(`Reimbursement ${newStatus}`);
        setReimbursements(prevReimbursements =>
          prevReimbursements.filter(r => r.reimbId !== reimbId)
        );
      } else {
        alert('Failed to resolve reimbursement');
      }
    } catch (error) {
      alert('Failed to resolve reimbursement');
    }
  };

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h2>Resolve Pending Reimbursements</h2>
      <ul className="reimbursement-list">
        {reimbursements.map(r => (
          <li key={r.reimbId} className="reimbursement-item">
            <h3>Amount: ${r.amount}</h3>
            <p><strong>Description:</strong> {r.description}</p>
            <p><strong>Submitted by:</strong> {r.user.firstName} {r.user.lastName} ({r.user.username})</p>
            <div>
              <button onClick={() => handleResolve(r.reimbId, 'APPROVED')}>Approve</button>
              <button onClick={() => handleResolve(r.reimbId, 'DENIED')}>Deny</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ResolveReimbursements;
