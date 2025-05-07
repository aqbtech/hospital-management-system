import { publicAxios } from '../utils/publicAxios'


export const registerUserAPI = async (data) => {
  const response = await publicAxios.post('/api/v1/auth/signup', data)
  return response.data
}

export const refreshTokenAPI = async (token) => {
  const data = { refreshToken: token }
  const response = await axiosPublic.post('/api/v1/auth/refresh', data)
  return response.data
}