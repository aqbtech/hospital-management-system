import { authorizedAxios } from '../utils/authorizedAxios'

export const bookAppointmentAPI = async (data) => {
  const response = authorizedAxios.post('/book-appointment', data)
  return response.data
}

export const updateAppointmentAPI = async (data) => {
  const response = authorizedAxios.put('/update-appointment', data)
  return response.data
}


//unknown
export const getListAppointmentForPatientsAPI = async (id) => {
  const response = authorizedAxios.get('/')
  return response.data
}


export const getDoctorForTimeAPI = async (time) => {
  const response = authorizedAxios.get('/', time)
  return response.data
}

export const cancelAppointmentAPI = async (id) => {
  const response = authorizedAxios.delete('/', id)
  return response.data
}