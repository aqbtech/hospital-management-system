import { useEffect, useState } from 'react'
import { getProfilePatientAPI, registerPatientsAPI, updateProfilePatientAPI } from '../apis/patientAPI'
import { toast } from 'react-toastify'

const useProfilePatient = () => {
  const [staffInfo, setStaffInfo] = useState({
    identity: {
      citizenId: '123456789',
      insuranceId: 'INS987654'
    },
    personalInfo: {
      firstName: 'John',
      lastName: 'Doe',
      dob: '1990-04-27',
      gender: 'MALE',
      phone: '+1-555-123-4567',
      address: '123 Main St, Springfield, USA'
    }
  })
  const [isEditing, setIsEditing] = useState(false)
  const [tempStaffInfo, setTempStaffInfo] = useState(staffInfo)

  const fetchProfile = async () => {
    await toast.promise(getProfilePatientAPI(), {
      pending: 'Getting data...'
    }).then((res) => {
      setStaffInfo(res)
      setTempStaffInfo(res)
    })
  }

  const handleEdit = () => setIsEditing(true)

  const handleCancel = () => {
    setTempStaffInfo(staffInfo)
    setIsEditing(false)
  }

  const handleSave = async () => {
    await toast.promise(updateProfilePatientAPI(tempStaffInfo), {
      pending: 'updating...'
    }).then(() => {
      setStaffInfo(tempStaffInfo)
      setIsEditing(false)
    })
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    const [section, field] = name.split('.')
    setTempStaffInfo((prev) => ({
      ...prev,
      [section]: {
        ...prev[section],
        [field]: value
      }
    }))
  }

  useEffect(() => {
    fetchProfile()
  }, [])

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
export default useProfilePatient