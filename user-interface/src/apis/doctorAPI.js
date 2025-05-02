import { authorizedAxios } from '../utils/authorizedAxios'

export const getDoctorProfileAPI = async () => {
  const response = authorizedAxios.get('/doctors/profile')
  return response.data
}

//bonus
export const updateDoctorProfileAPI = async () => {
  const response = authorizedAxios.put('/doctors/profile')
  return response.data
}

export const getListScheduleAPI = async (doctorId, startDate, endDate) => {
  const response = authorizedAxios.get(`/doctors/schedule?doctorId=${doctorId}&startDate=${startDate}&endDate=${endDate}`)
  return response.data
}

export const getListPatientForDoctorAPI = async (doctorId, status) => {
  const response = authorizedAxios.get(`/doctors/patients?doctorId=${doctorId}&status=${status}`)
  return response.data
}

export const createPrescriptionsAPI = async (data) => {
  const response = authorizedAxios.post('/doctors/prescriptions', data)
  return response.data
}

//bonus
export const updatePrescriptionsAPI = async (data, prescriptionId) => {
  const response = authorizedAxios.put(`/doctors/prescriptions?prescriptionId=${prescriptionId}`, data)
  return response.data
}

//bonus
export const getPrescriptionsAPI = async (patientId) => {
  const response = authorizedAxios.put(`/doctors/prescriptions?patientId=${patientId}`)
  return response.data
}