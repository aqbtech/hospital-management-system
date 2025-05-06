import MainLayout from '../../components/layout/MainLayout'
import { SIDEBAR_PATIENT } from '../../utils/constant.jsx'
import { useSearchParams } from 'react-router-dom'
import ProfilePatient from './ProfilePatient.jsx'
import Appointment from './Appointment.jsx'
import MedicalRecord from './MedicalRecord.jsx'


const Patient = () => {
  const [searchParams] = useSearchParams()
  const tabFromUrl = searchParams.get('tab') || 'profile'

  const TABS_COMPONENT = {
    profile: <ProfilePatient/>,
    appointment: <Appointment />,
    medical: <MedicalRecord />
  }
  const CurrentTabContent = TABS_COMPONENT[tabFromUrl] || <div>Tab not found</div>


  return (
    <MainLayout SIDEBAR={SIDEBAR_PATIENT}>
      {CurrentTabContent}
    </MainLayout>

  )
}

export default Patient