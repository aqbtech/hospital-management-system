import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import { publicAxios } from '../../utils/publicAxios'

const initialState = {
  user: null
}

//login
export const loginUserAPI = createAsyncThunk(
  'user/loginUserAPI',
  async (data) => {
    const response = await publicAxios.post('/api/v1/auth/login', data)
    return response.data
  }
)

//logout
export const logoutUserAPI = createAsyncThunk(
  'user/logoutUserAPI',
  async (token) => {
    const data = { refreshToken : token }
    const response = await publicAxios.post('/api/v1/auth/logout', data)
    return response.data
  }
)

export const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser: (state, action) => {
      state.user = action.payload
    },
    clearUser: (state) => {
      state.user = null
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginUserAPI.fulfilled, (state, action) => {
        state.user = action.payload
      })
      .addCase(logoutUserAPI.fulfilled, (state) => {
        state.user = null
      })
  }
})

export const { setUser, clearUser } = userSlice.actions

export const selectUser = (state) => state.user.user

export const userReducer = userSlice.reducer