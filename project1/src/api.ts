// Setup Axios instance
import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true,  // Important for sessions to work
    headers: {
        'Content-Type': 'application/json'
    }
});

export default api;
