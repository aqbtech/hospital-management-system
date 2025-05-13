import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'
import { registerUserAPI } from '../apis/userAPI'
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom'
import {
  PHONENUMBER_RULE,
  PHONENUMBER_RULE_MESSAGE,
  EMAIL_RULE,
  EMAIL_RULE_MESSAGE,
  PASSWORD_RULE,
  PASSWORD_RULE_MESSAGE,
  FIELD_REQUIRED_MESSAGE
} from '../utils/validator'
import { USER_TYPES } from '../utils/constant'

const schema = yup.object().shape({
  username: yup.string().required(FIELD_REQUIRED_MESSAGE),
  email: yup.string().matches(EMAIL_RULE, EMAIL_RULE_MESSAGE).required(FIELD_REQUIRED_MESSAGE),
  password: yup.string().matches(PASSWORD_RULE, PASSWORD_RULE_MESSAGE).required(FIELD_REQUIRED_MESSAGE),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref('password'), null], 'Passwords do not match')
    .required('Please confirm your password'),
  phone: yup.string().matches(PHONENUMBER_RULE, PHONENUMBER_RULE_MESSAGE).required(FIELD_REQUIRED_MESSAGE),
  fullName: yup.string().required(FIELD_REQUIRED_MESSAGE),
  role: yup.string().oneOf(USER_TYPES, 'Invalid role').required('Role is required'),
  gender: yup.string().oneOf(['MALE', 'FEMALE'], 'Invalid gender').required('Gender is required'),
  dob: yup.date().required('Date of birth is required'),
  address: yup.string().required(FIELD_REQUIRED_MESSAGE),
  insuranceNumber: yup.string().required(FIELD_REQUIRED_MESSAGE),
  emergencyContact: yup.string().matches(PHONENUMBER_RULE, 'Invalid emergency contact').required(FIELD_REQUIRED_MESSAGE)
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

    await toast.promise(
      registerUserAPI(formData),
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