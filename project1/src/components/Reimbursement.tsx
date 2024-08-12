import React, { useState, useEffect } from 'react';
import api from '../api';
import { Reimbursement } from '../interfaces/Reimbursement';
import '../styles/Reimbursement.css';


interface Props {
  status?: string; // Optional prop to filter by status
}

const Reimbursements: React.FC<Props> = ({ status }) => {
  const [reimbursements, setReimbursements] = useState<Reimbursement[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchReimbursements = async () => {
      try {
        setIsLoading(true);
        const endpoint = status ? `/reimbursements/status/${status}` : '/reimbursements';
        const response = await api.get(endpoint);
        setReimbursements(response.data);
        setIsLoading(false);
      } catch (err) {
        setError('Failed to fetch data');
        setIsLoading(false);
      }
    };

    fetchReimbursements();
  }, [status]);

  const handleResolve = async (reimbId: number, newStatus: string) => {
    try {
      const response = await api.put(`/reimbursements/${reimbId}/resolve`, { status: newStatus });
      if (response.status === 200) {
        alert(`Reimbursement ${newStatus}`);
        setReimbursements(prevReimbursements =>
          prevReimbursements.map(r =>
            r.reimbId === reimbId ? { ...r, status: newStatus } : r
          )
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
      <h2>{status ? `Reimbursements - ${status}` : 'All Reimbursements'}</h2>
      <ul className="reimbursement-list">
        {reimbursements.map(r => (
          <li key={r.reimbId} className="reimbursement-item">
            <h3>Amount: ${r.amount}</h3>
            <p><strong>Description:</strong> {r.description}</p>
            <p>
              <strong>Status:</strong> 
              <span className={`reimbursement-status status-${r.status.toLowerCase()}`}>
                {r.status}
              </span>
            </p>
            <p><strong>Submitted by:</strong> {r.user.firstName} {r.user.lastName} ({r.user.username})</p>
            <p><strong>Role:</strong> {r.user.role}</p>
            {r.status === 'PENDING' && (
              <div>
                <button onClick={() => handleResolve(r.reimbId, 'APPROVED')}>Approve</button>
                <button onClick={() => handleResolve(r.reimbId, 'DENIED')}>Deny</button>
              </div>
            )}
          </li>
        ))}
      </ul>
</div>
  );
};

export default Reimbursements;