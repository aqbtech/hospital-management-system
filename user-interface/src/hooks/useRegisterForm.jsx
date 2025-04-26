import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'
import { registerUserAPI } from '../apis/userAPI'
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom'
import { PHONENUMBER_RULE,
  PHONENUMBER_RULE_MESSAGE,
  EMAIL_RULE, EMAIL_RULE_MESSAGE,
  PASSWORD_RULE,
  PASSWORD_RULE_MESSAGE,
  FIELD_REQUIRED_MESSAGE
} from '../utils/validator'
import { USER_TYPES } from '../utils/constant'

const schema = yup.object().shape({
  userType: yup.string()
    .oneOf(USER_TYPES, 'Invalid user type')
    .required('User type is required'),
  firstName: yup.string().required(FIELD_REQUIRED_MESSAGE),
  lastName: yup.string().required(FIELD_REQUIRED_MESSAGE),
  email: yup.string().matches(EMAIL_RULE, EMAIL_RULE_MESSAGE).required(FIELD_REQUIRED_MESSAGE),
  phone: yup.string().matches(PHONENUMBER_RULE, PHONENUMBER_RULE_MESSAGE).required(FIELD_REQUIRED_MESSAGE),
  citizenId: yup.string().required(FIELD_REQUIRED_MESSAGE),
  password: yup.string().matches(PASSWORD_RULE, PASSWORD_RULE_MESSAGE).required(FIELD_REQUIRED_MESSAGE),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref('password'), null], 'Passwords do not match')
    .required('Please confirm your password')
})

export const useRegisterForm = () => {
  const navigate = useNavigate()

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting }
  } = useForm({
    resolver: yupResolver(schema),
    mode: 'onBlur'
  })

  const onSubmit = async (data) => {
    const { confirmPassword, ...formData } = data

    const payload = {
      userType: formData.userType,
      personalInfo: { ...formData }
    }
    delete payload.personalInfo.userType

    await toast.promise(
      registerUserAPI(payload),
      {
        pending: 'Creating account...',
        success: 'Account created successfully!'
      }
    )

    navigate('/login')
  }

  return {
    register,
    handleSubmit,
    onSubmit,
    errors,
    isSubmitting
  }
}
