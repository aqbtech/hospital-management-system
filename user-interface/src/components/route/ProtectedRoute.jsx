import { Navigate, Outlet } from 'react-router-dom'

const ProtectedRoute = ({ user, allowedType }) => {

  if (!user) return <Navigate to='/login' replace={true}/>

  // if (allowedType !== user?.user.role) {
  //   return <Navigate to="/403" replace />
  // }

  return (
    <Outlet />
  )
}

export default ProtectedRoute