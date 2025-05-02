import { Routes } from 'react-router-dom'
import { Route } from 'react-router-dom'
import LandingPage from './pages/landingPage/LandingPage'
import Auth from './pages/auth/Auth'
import MainLayout from './components/layout/MainLayout'
import NotFound from '../src/pages/notFound/NotFound'
import Forbidden from '../src/pages/403/Forbidden'

function App() {

  return (
    <Routes>
      <Route path='/register' element={<Auth />} />
      <Route path='/login' element={<Auth />} />
      <Route path="/" element={<LandingPage />} />
      <Route path='/patient' element={<MainLayout />}/>
      <Route path='/403' element={<Forbidden />}/>
      <Route path='*' element={<NotFound />} />
    </Routes>
  )
}

export default App
