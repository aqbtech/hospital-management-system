import { authorizedAxios } from '../utils/authorizedAxios'

export const getDoctorProfileAPI = async () => {
  const response = await authorizedAxios.get('/doctors/profile')
  return response.data
}

//bonus
export const updateDoctorProfileAPI = async (data) => {
  const response = await authorizedAxios.put('/doctors/profile', data)
  return response.data
}

export const getListScheduleAPI = async (doctorId, startDate, endDate) => {
  const response = await authorizedAxios.get(`/doctors/schedule?doctorId=${doctorId}&startDate=${startDate}&endDate=${endDate}`)
  return response.data
}

export const getListPatientForDoctorAPI = async (status) => {
  const response = await authorizedAxios.get(`/doctors/patients?status=${status}`)
  return response.data
}

export const createPrescriptionsAPI = async (data) => {
  const response = await authorizedAxios.post('/doctors/prescriptions', data)
  return response.data
}

//bonus
export const updatePrescriptionsAPI = async (data, prescriptionId) => {
  const response = await authorizedAxios.put(`/doctors/prescriptions?prescriptionId=${prescriptionId}`, data)
  return response.data
}

//bonus
export const getPrescriptionsAPI = async (patientId) => {
  const response = await authorizedAxios.get(`/doctors/prescriptions?patientId=${patientId}`)
  return response.data
}

//get info patient
export const getInfoPatientAPI = async (patientId) => {
  const response = await authorizedAxios.get(`/doctor/patient?patientId=${patientId}`)
  return response.data
}

//get medical record
export const getMedicalRecordAPI = async (patientId) => {
  const response = await authorizedAxios.get(`/doctor/patient/mediacal?patientId=${patientId}`)
  return response.data
}

//put medical record
export const updateMedicalRecord = async ( mediacalId, data) => {
  const response = await authorizedAxios.put(`/doctor/patient/medical?medicalRecordId=${mediacalId}`, data)
  return response.data
}

//create mediacal record
export const createMedicalRecord = async (data) => {
  const response = await authorizedAxios.post('/doctor/patient/mediacal', data)
  return response.data
}

