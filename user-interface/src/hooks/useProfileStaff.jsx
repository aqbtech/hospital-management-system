import { useState, useEffect } from 'react'
import { toast } from 'react-toastify'
import { getAccountantProfileAPI, updateAccountantProfileAPI } from '../apis/accountantAPI'
import { getDoctorProfileAPI, updateDoctorProfileAPI } from '../apis/doctorAPI'

const FETCH_PROFILE_FUNCTIONS = {
  Accountant: getAccountantProfileAPI,
  Doctor: getDoctorProfileAPI
}

const EDIT_PROFILE_FUNCTIONS = {
  Accountant: updateAccountantProfileAPI,
  Doctor: updateDoctorProfileAPI
}

const useProfileStaff = (role) => {
  const [staffInfo, setStaffInfo] = useState({
    name: 'Nguyễn Văn A',
    specialty:'CARDIOLOGY',
    department: 'Kế toán',
    qualifications: ['Cử nhân Kế toán', 'Chứng chỉ ACCA'],
    experience: 5
  })

  const [isEditing, setIsEditing] = useState(false)
  const [tempStaffInfo, setTempStaffInfo] = useState(staffInfo)

  const fetchProfile = async () => {
    const fetchFunction = FETCH_PROFILE_FUNCTIONS[role]
    await toast.promise(fetchFunction(), {
      pending: 'Getting data...'
    }).then((res) => {
      setStaffInfo(res)
      setTempStaffInfo(res)
    })
  }

  const handleEdit = () => setIsEditing(true)

  const handleCancel = () => {
    setIsEditing(false)
    setTempStaffInfo(staffInfo)
  }
  const handleSave = () => {
    const updateFunction = EDIT_PROFILE_FUNCTIONS[role]
    if (role === 'Doctor') {
      delete tempStaffInfo.department
    } else {
      delete tempStaffInfo.specialty
    }
    toast.promise(updateFunction(tempStaffInfo), {
      pending: 'editing profile...',
      success: 'update profile success'
    }).then(() => {
      setStaffInfo(tempStaffInfo)
      setIsEditing(false)
    })
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setTempStaffInfo((prevInfo) => ({
      ...prevInfo,
      [name]: value
    }))
  }

  useEffect(() => {
    fetchProfile()
  }, [role])

  return {
    staffInfo,
    isEditing,
    tempStaffInfo,
    handleEdit,
    handleCancel,
    handleSave,
    handleInputChange
  }
}

export default useProfileStaff
