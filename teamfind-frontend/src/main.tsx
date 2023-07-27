import ReactDOM from 'react-dom';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from './config/keycloak';
import App from './App.tsx';
import './index.css';
import { BrowserRouter } from 'react-router-dom';

ReactDOM.render(
  <ReactKeycloakProvider authClient={keycloak}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </ReactKeycloakProvider>,
  document.getElementById('root')
);
