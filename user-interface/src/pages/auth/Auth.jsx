import { useLocation } from 'react-router-dom'
import LoginForm from './LoginForm'
import RegisterForm from './RegisterForm'
import Header from '../../components/layout/Header'

const Auth = () => {
  const location = useLocation()
  const isLogin = location.pathname === '/login'
  const isRegister = location.pathname === '/register'

  return (
    <>
      <Header />
      {isLogin && <LoginForm /> }
      {isRegister && <RegisterForm />}
    </>
  )
}

export default Auth