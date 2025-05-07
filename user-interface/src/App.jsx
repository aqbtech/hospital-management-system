import { Routes } from 'react-router-dom'
import { Route } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { selectUser } from './redux/slice/userSlice'
import { ToastContainer } from 'react-toastify'

import ProtectedRoute from '../src/components/route/ProtectedRoute'
import PublicRoute from './components/route/PublicRoute'

import LandingPage from './pages/landingPage/LandingPage'
import Auth from './pages/auth/Auth'
import MainLayout from './components/layout/MainLayout'
import NotFound from '../src/pages/notFound/NotFound'
import Forbidden from '../src/pages/403/Forbidden'

import Doctor from './pages/Doctor/Doctor'
import Patient from './pages/patient/Patient'
import Accountant from './pages/accountant/Accountant'
import PatientInfo from './pages/Doctor/Patient/PatientInfo'

function App() {
  const currentUser = useSelector(selectUser)

  return (
    <>
      <ToastContainer/>
      <Routes>
        {/* Doctor routes */}
        <Route element={<ProtectedRoute user={currentUser} allowedTypes={['DOCTOR']} />}>
          <Route path='doctor' element={<Doctor />} />
          <Route path='/doctor/patient/:patientId' element={<PatientInfo />}/>
        </Route>

        {/* Accountant routes */}
        <Route element={<ProtectedRoute user={currentUser} allowedTypes={['ACCOUNTANT']} />}>
          <Route path='accountant' element={<Accountant />} />
        </Route>

        {/* Patient routes */}
        <Route element={<ProtectedRoute user={currentUser} allowedTypes={['PATIENT']} />}>
          <Route path='patient' element={<Patient />} />
        </Route>

        {/*<Route element={<PublicRoute user={currentUser} />} >*/}
          <Route path='/register' element={<Auth />} />
          <Route path='/login' element={<Auth />} />
        {/*</Route>*/}


        {/* LandingPage */}
        <Route path="/" element={<LandingPage />} />

        {/* Test */}
        <Route path='/patientsss' element={<MainLayout />} />

        {/* Forbidden */}
        <Route path='/403' element={<Forbidden />} />

        {/* Not Found */}
        <Route path='*' element={<NotFound />} />
      </Routes>
    </>

  )
}

export default App
