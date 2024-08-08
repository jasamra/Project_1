# Project_1
The goal of project 1 is to create a Java Full Stack Employee Reimbursement System (ERS). The main use case of the ERS centers around Employees submitting Reimbursements that can either be accepted or denied by Managers. The tech stack will consist of a React-Based Front end, communicating via HTTP to a Spring-Based Back end. The database will be either a local or cloud-based Postgres database. The entire application will have error handling in place to prevent the user from invoking unauthorized functionalities or entering invalid inputs. 

Employee User Stories  

Users using the application can: 

Create an account (create new User – default role should be employee) 

Create a new Reimbursement 

See all reimbursement tickets (only their own) 

See only their pending reimbursement tickets 

[Some other functionality of your choice] 

OPTIONAL: Update the description of a pending reimbursement 

 

Manager User Stories 

Managers using the application can:  

See all reimbursements 

See all pending reimbursements 

Resolve a reimbursement  

(update status from PENDING to APPROVED or DENIED) 

See all Users 

Delete a User 

(should also delete any related reimbursements) 

OPTIONAL: Update an employee’s role to manager 

 

Validation User Stories (Do Login Last!!) 

Users who are not logged in to the application can ONLY:  

Attempt to log in. 

Users should not be able to access the other user stories before logging in. 
