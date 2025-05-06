import { authorizedAxios } from '../utils/authorizedAxios'


export const getProfilePatientAPI = async () => {
  const response = await authorizedAxios.get('/patient/profile')
  return response.data
}

export const updateMedicalRecordAPI = async (data) => {
  const response = await authorizedAxios.post('/update-medical-record', data)
  return response.data
}

export const registerPatientsAPI = async (data) => {
  const response = await authorizedAxios.post('/register-patients', data)
  return response.data
}

export const viewMedicalHistoryAPI = async (patientId) => {
  const response = await authorizedAxios.get(`/view-medicine-history?patientId=${patientId}`)
  return response.data
}


//bonuss
export const updateProfilePatientAPI = async (data) => {
  const response = await authorizedAxios.post('/profile-patient', data)
  return response.data
}