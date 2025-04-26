import { BASE_URL } from './constant.jsx'
import axios from 'axios'
import { toast } from 'react-toastify'

export const publicAxios = axios.create({
  baseURL: BASE_URL,
  timeout: 1000 * 60 * 10, // 10 minutes
  withCredentials: false
})

let axiosReduxStore
export const injectStore = (mainStore) => {
  axiosReduxStore = mainStore
}

publicAxios.interceptors.request.use(
  (config) => {
    // You can add any request interceptors here if needed
    return config
  },
  (error) => {
    // Handle request error
    return Promise.reject(error)
  }
)

publicAxios.interceptors.response.use(
  (response) => {
    // You can add any response interceptors here if needed
    return response
  },
  async (error) => {
    // Handle response error
    let errorMessage = error?.message
    if (error.response?.data?.message) {
      errorMessage = error.response.data.message
    }
    if (error.response?.status !== 410) {
      toast.error(errorMessage)
    }

    return Promise.reject(error)
  }
)