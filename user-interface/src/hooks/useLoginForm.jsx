import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { useDispatch } from 'react-redux'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'
import { loginUserAPI } from '../redux/slice/userSlice'
import { FIELD_REQUIRED_MESSAGE, PASSWORD_RULE, PASSWORD_RULE_MESSAGE } from '../utils/validator'

const schema = yup.object().shape({
  username: yup
    .string()
    .required(FIELD_REQUIRED_MESSAGE),
  password: yup
    .string()
    .required(FIELD_REQUIRED_MESSAGE)
    .matches(
      PASSWORD_RULE,
      PASSWORD_RULE_MESSAGE
    )
})

export const useLoginForm = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting }
  } = useForm({
    resolver: yupResolver(schema)
  })

  const onSubmit = async (formData) => {
    toast
      .promise(dispatch(loginUserAPI(formData)), {
        pending: 'Logging in...'
      })
      .then((res) => {
        if (!res.error) {
          console.log(res)
          if (res.payload.user.role === 'PATIENT') {
            navigate('/patient')
          } else if (res.payload.user.role === 'DOCTOR') {
            navigate('/doctor')
          } else if (res.payload.user.role === 'ACCOUNTANT') {
            navigate('/accountant')
          }
        }
      })
  }

  return {
    register,
    handleSubmit,
    onSubmit,
    errors,
    isSubmitting
  }
}
