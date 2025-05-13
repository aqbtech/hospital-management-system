import { Box, Typography, Divider, Button, IconButton } from '@mui/material'
import { ArrowBack } from '@mui/icons-material'
import InfoField from './InfoField'
import MedicalRecordField from './MedicalRecordField'
import PrescriptionField from './PrescriptionField'
import MainLayout from '../../../components/layout/MainLayout'
import { SIDEBAR_DOCTOR } from '../../../utils/constant'
import { getInfoPatientAPI, getPrescriptionsAPI, getMedicalRecordAPI } from '../../../apis/doctorAPI'
import { toast } from 'react-toastify'
import { useNavigate, useParams } from 'react-router-dom'
import { useEffect, useState } from 'react'

const PatientInfo = () => {
  const { patientId } = useParams()
  const [patientData, setPatientData] = useState({
    identity: { citizenId: '123456789', insuranceId: 'BH123456', patientId: '12' },
    personalInfo: {
      firstName: 'Văn A',
      lastName: 'Nguyễn',
      dob: '1990-05-20',
      gender: 'MALE',
      phone: '0901234567',
      address: '123 Đường ABC, Quận 1, TP.HCM'
    }
  })
  const [medicalRecordsData, setMedicalRecords] = useState([
    {
      recordId: 1,
      patientId: 1234,
      doctor: { doctorId: 1, name: 'Dr. Trần Văn B', specialty: 'Nội tiết' },
      date: '2024-04-27T08:00:00Z',
      symptoms: 'Đau đầu, sốt',
      diagnosis: 'Cảm cúm'
    },
    {
      recordId: 2,
      patientId: 1234,
      doctor: { doctorId: 2, name: 'Dr. Lê Văn C', specialty: 'Da liễu' },
      date: '2024-03-20T08:00:00Z',
      symptoms: 'Nổi mẩn đỏ',
      diagnosis: 'Viêm da'
    }
  ])
  const [prescriptionsData, setPrescriptionData] = useState([
    {
      prescriptionId: 234,
      createDate: '2025-04-28',
      doctorCreate: 'Luong Cuong',
      medications: [
        {
          medicineId: 'MED001',
          dosage: '2 viên',
          frequency: '3 lần/ngày',
          duration: '7 ngày',
          notes: 'Uống sau ăn'
        },
        {
          medicineId: 'MED002',
          dosage: '1 viên',
          frequency: '2 lần/ngày',
          duration: '5 ngày',
          notes: 'Uống trước ăn'
        }
      ]
    }
  ])
  const navigate = useNavigate()

  const getInfoPatien = async () => {
    await toast.promise(getInfoPatientAPI(patientId), {})
      .then((res) => {
        console.log(res)
        setPatientData(res)
      })
  }

  const getMedicalRecord = async () => {
    await toast.promise(getMedicalRecordAPI(patientId), {})
      .then((res) => {
        console.log(res)
        setMedicalRecords(res)
      } )
  }

  const getPrescriptions = async () => {
    await toast.promise(getPrescriptionsAPI(patientId), {})
      .then((res) => {
        console.log(res)
        setPrescriptionData(res)
      })
  }

  const handleBackToDoctor = () => {
    // Điều hướng về trang doctor với tab=myProfile
    navigate('/doctor?tab=myPatients')
  }

  useEffect(() => {
    getInfoPatien()
    getMedicalRecord()
    getPrescriptions()
  }, [patientId])

  return (
    <>
      <MainLayout SIDEBAR={SIDEBAR_DOCTOR}>
        <Box sx={{ p: 2 }}>
          <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
            <IconButton onClick={handleBackToDoctor} sx={{ color: 'primary.main' }}>
              <ArrowBack />
            </IconButton>
            <Typography variant="h4" sx={{ fontWeight: 'bold', ml: 1 }}>
          My Patients
            </Typography>
          </Box>
          <Divider sx={{ mb: 3 }} />
          <InfoField identity={patientData.identity} personalInfo={patientData.personalInfo} />
          <MedicalRecordField medicalRecords={medicalRecordsData} patientId={patientData.identity.patientId} />
          <PrescriptionField prescriptions={prescriptionsData} patientId={patientData.identity.patientId}/>
        </Box>
      </MainLayout>

    </>
  )
}

export default PatientInfo