
import './App.css'
import { Route, Routes } from 'react-router-dom';
import { Layout } from './component/layout/Layout';
import { HomePage } from './pages/HomePage';
import { PlayerPage } from './pages/PlayerPage';
import { TeamPage } from './pages/TeamPage';
import LoginPage from './pages/LoginPage';

function App() {
  return (
    <div>
      <Layout>
        <Routes>
          <Route path='/' element={<HomePage/>}></Route>
          <Route path='/players' element={<PlayerPage/>}></Route>
          <Route path='/teams' element={<TeamPage/>}></Route>
          <Route path='/login' element={<LoginPage/>}></Route>
        </Routes>
      </Layout>
    </div>
  )
}

export default App
