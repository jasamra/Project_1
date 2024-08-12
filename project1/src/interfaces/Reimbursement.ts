// src/interfaces/Reimbursement.ts

export interface User {
  userId: number;
  firstName: string;
  lastName: string;
  username: string;
  role: string;
}

export interface Reimbursement {
  reimbId: number;
  description: string;
  amount: number;
  status: string;
  user: User;
}
