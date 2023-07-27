import React, { useState } from 'react';
import { useKeycloak } from '@react-keycloak/web';

const LoginPage: React.FC = () => {
  const { keycloak } = useKeycloak();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const login = () => {
    keycloak.login();
  };

  const register = async () => {
    try {
      // Validate input (e.g., check if email and password are not empty)
      if (!email || !password) {
        alert('Please provide both email and password.');
        return;
      }

      // Implement registration logic here (e.g., API call to create a new user).
      // Make sure to handle errors appropriately.
      // For simplicity, we assume the registration API endpoint is '/api/register'.

      const response = await fetch('/api/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        alert('Registration successful! You can now log in.');
        // Optionally, you can automatically trigger the login after successful registration.
        // keycloak.login();
      } else {
        // Handle registration error (e.g., email already exists).
        const data = await response.json();
        alert('Registration failed: ' + data.message);
      }
    } catch (error) {
      console.error('Error during registration:', error);
    }
  };

  return (
    <div>
      <h2>Login Page</h2>
      <button onClick={login}>Login</button>
      <div>
        <h3>Register</h3>
        <label>
          Email:
          <input type="email" value={email} onChange={handleEmailChange} />
        </label>
        <label>
          Password:
          <input type="password" value={password} onChange={handlePasswordChange} />
        </label>
        <button onClick={register}>Register</button>
      </div>
    </div>
  );
};

export default LoginPage;
