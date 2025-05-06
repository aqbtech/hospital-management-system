import { useSearchParams } from 'react-router-dom'
import MainLayout from '../../components/layout/MainLayout'
import { SIDEBAR_ACCOUNTANT } from '../../utils/constant.jsx'
import ProfileStaff from '../../components/layout/ProfileStaff.jsx'
import Invoice from './Invoice.jsx'
import Insurance from './Insurance.jsx'

const TABS_COMPONENT = {
  profile: <ProfileStaff role={'Accountant'}/>,
  invoice: <Invoice />,
  insurance: <Insurance />
}

const Accountant = () => {
  const [searchParams] = useSearchParams()
  const tabFromUrl = searchParams.get('tab') || 'profile'

  const CurrentTabContent = TABS_COMPONENT[tabFromUrl] || <div>Tab not found</div>

  return (
    <MainLayout SIDEBAR={SIDEBAR_ACCOUNTANT}>
      {CurrentTabContent}
    </MainLayout>
  )
}

export default Accountant
