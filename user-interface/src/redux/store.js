import { configureStore } from '@reduxjs/toolkit'
import { combineReducers } from 'redux'
import { persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import { userReducer } from './slice/userSlice'

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['user']
}

const reducers = combineReducers({
  user: userReducer
  // Add other reducers here
})
const persistedReducer = persistReducer(persistConfig, reducers)

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false
    })
})

export default store