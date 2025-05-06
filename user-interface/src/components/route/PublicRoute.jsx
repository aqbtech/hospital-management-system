import { Navigate, Outlet } from 'react-router-dom'

const PublicRoute = ({ user }) => {
  if (user) {
    switch (user.userType) {
    case 'PATIENT':
      return <Navigate to="/patient" replace />
    case 'DOCTOR':
      return <Navigate to="/doctor" replace />
    case 'ACCOUNTANT':
      return <Navigate to="/accountant" replace />
    default:
      return <Navigate to="/notFound" replace />
    }
  }

  return <Outlet />
}

export default PublicRoute
