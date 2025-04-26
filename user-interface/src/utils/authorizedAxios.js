import { BASE_URL } from './constant.jsx'
import axios from 'axios'
import { refreshTokenAPI } from '../apis/userAPI.js'
import { setUser, clearUser } from '../redux/slice/userSlice.js'
import { toast } from 'react-toastify'

const authorizedAxios = axios.create({
  baseURL: BASE_URL,
  timeout: 1000 * 60 * 10, // 10 minutes
  withCredentials: false
})

let axiosReduxStore
export const injectStore = (mainStore) => {
  axiosReduxStore = mainStore
}


authorizedAxios.interceptors.request.use(
  (config) => {
    // You can add any request interceptors here if needed
    const token = axiosReduxStore.getState().user.user?.token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    // Handle request error
    return Promise.reject(error)
  }
)

authorizedAxios.interceptors.response.use(
  (response) => {
    // You can add any response interceptors here if needed
    return response
  },
  async (error) => {
    // Handle response error
    const token = axiosReduxStore.getState().user.user?.token
    if (error.response?.status === 401 && token) {
      try {
        if (error.config._retry) {
          window.location.href('/login')
          return Promise.reject(error)
        }
        error._retry = true

        const response = await refreshTokenAPI(token)
        const newToken = response.result.token
        if (newToken) {
          const currentUser = axiosReduxStore.getState().user.user
          axiosReduxStore.dispatch(setUser({ ...currentUser, token: newToken }))

          error.config.headers.Authorization = `Bearer ${newToken}`
          return authorizedAxios(error.config)
        } else {
          axiosReduxStore.dispatch(clearUser())
          window.location.href('/login')
          throw new Error('Token không hợp lệ vui lòng đăng nhập lại')
        }
      } catch (error) {
        return Promise.reject(error)
      }
    }

    // Xử lý lỗi chung
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