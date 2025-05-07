import { authorizedAxios } from '../utils/authorizedAxios'

export const bookAppointmentAPI = async (data) => {
  const response = await authorizedAxios.post('/book-appointment', data)
  return response.data
}

export const updateAppointmentAPI = async (data) => {
  const response = await authorizedAxios.put('/update-appointment', data)
  return response.data
}


//unknown
export const getListAppointmentForPatientsAPI = async () => {
  const response = await authorizedAxios.get('/appointment/appointment-status')
  return response.data
}


export const getDoctorForTimeAPI = async (date, time) => {
  const dateTimeString = `${date}T${time}:00`

  // startTime: timestamp (seconds)
  const startTime = Math.floor(new Date(dateTimeString).getTime() / 1000)

  // endTime: startTime + 1 hour
  const endTime = startTime + 3600
  const response = await authorizedAxios.get(`/available-doctor?startTs=${startTime}&endTs=${endTime}`)
  return response.data
}

export const cancelAppointmentAPI = async (id) => {
  const response = await authorizedAxios.delete(`/appointment?appointmentId=${id}`)
  return response.data
}