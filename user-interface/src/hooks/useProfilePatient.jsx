import { useEffect, useState } from 'react'
import { getProfilePatientAPI, updateProfilePatientAPI } from '../apis/patientAPI'
import { toast } from 'react-toastify'

const useProfilePatient = () => {
  const [userInfo, setUserInfo] = useState({
    id: 2,
    username: 'chinh5504',
    email: 'chinhtran5504@gmail.com',
    phone: '0909080809',
    role: 'PATIENT',
    createdAt: '2025-05-07T12:15:33.371+00:00',
    lastLogin: '2025-05-07T13:07:25.660+00:00',
    active: true,
    profile: {
      id: 2,
      fullName: 'Đặng Trần Công Chính',
      gender: 'MALE',
      address: 'Biên hoà, Đồng Nai',
      insuranceNumber: '123123123123'
    }
  })

  const [isEditing, setIsEditing] = useState(false)
  const [tempUserInfo, setTempUserInfo] = useState(userInfo)

  const fetchProfile = async () => {
    await toast.promise(getProfilePatientAPI(), {
      pending: 'Loading profile...'
    }).then((res) => {
      setUserInfo(res)
      setTempUserInfo(res)
    })
  }

  const handleEdit = () => setIsEditing(true)

  const handleCancel = () => {
    setTempUserInfo(userInfo)
    setIsEditing(false)
  }

  const handleSave = async () => {
    await toast.promise(updateProfilePatientAPI(tempUserInfo), {
      pending: 'Saving...'
    }).then(() => {
      setUserInfo(tempUserInfo)
      setIsEditing(false)
    })
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    if (name.includes('.')) {
      const [section, field] = name.split('.')
      setTempUserInfo((prev) => ({
        ...prev,
        [section]: {
          ...prev[section],
          [field]: value
        }
      }))
    } else {
      setTempUserInfo((prev) => ({
        ...prev,
        [name]: value
      }))
    }
  }

  useEffect(() => {
    fetchProfile()
  }, [])

  return {
    userInfo,
    isEditing,
    tempUserInfo,
    handleEdit,
    handleCancel,
    handleSave,
    handleInputChange
  }
}

export default useProfilePatient
