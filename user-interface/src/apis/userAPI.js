import { publicAxios } from '../utils/publicAxios'


export const registerUserAPI = async (data) => {
  const response = await publicAxios.post('/auth/register', data)
  return response.data
}

export const refreshTokenAPI = async (token) => {
  const data = { refreshToken: token }
  const response = await axiosPublic.post('/auth/refresh', data)
  return response.data
}