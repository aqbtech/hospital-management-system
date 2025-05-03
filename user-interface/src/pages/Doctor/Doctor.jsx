import MainLayout from '../../components/layout/MainLayout'
import { SIDEBAR_DOCTOR } from '../../utils/constant'
import { useSearchParams } from 'react-router-dom'
import ProfileStaff from '../../components/layout/ProfileStaff'
import MyPatients from './MyPatients'
import Dashboard from './Dashboard'

const TABS_COMPONENT = {
  profile: <ProfileStaff role={'Doctor'}/>,
  dashboard: <Dashboard />,
  myPatients: <MyPatients />,
  prescriptions: <>prescriptions</>
}


const Doctor = () => {
  const [searchParams] = useSearchParams()
  const tabFromUrl = searchParams.get('tab') || 'profile'

  const CurrentTabContent = TABS_COMPONENT[tabFromUrl] || TABS_COMPONENT['profile']

  return (
    <>
      <MainLayout SIDEBAR={SIDEBAR_DOCTOR} role='doctor'>
        {CurrentTabContent}
      </MainLayout>
    </>
  )
}

export default Doctor