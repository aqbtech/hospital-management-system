import { Box, Card, CardContent, Typography, Chip, Divider, Grid, Button, Select, MenuItem, InputLabel, FormControl } from '@mui/material'
import PatientItem from './PatientItem'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { getListPatientForDoctorAPI } from '../../apis/doctorAPI'
import { toast } from 'react-toastify'

const MyPatients = () => {
  const navigate = useNavigate()

  const [patients, setPatients] = useState([
    {
      patientId: '3fa85f64-5717-4562-b3fc-2c963f66afa6',
      name: 'Nguyễn Văn A',
      status: 'WAITING',
      waitingTime: 15
    },
    {
      patientId: '3fa85f64-5717-4562-b3fc-2c963f66afa7',
      name: 'Trần Thị B',
      status: 'IN_PROGRESS',
      waitingTime: 0
    }
  ])

  const [status, setStatus] = useState('WAITING') // Trạng thái mặc định là 'WAITING'

  const getPatients = async () => {
    toast.promise(getListPatientForDoctorAPI(status), {
      pending: 'Fetching patients...'
    }).then((res) => {
      setPatients(res)
    })
  }

  const handleStatusChange = (event) => {
    setStatus(event.target.value) // Cập nhật trạng thái khi người dùng chọn
  }

  const handleBackToDoctor = () => {
    navigate('/doctor?tab=myProfile')
  }

  return (
    <Box sx={{ p: 3 }}>
      {/* Tiêu đề */}
      <Typography variant="h4" gutterBottom>
        My Patients
      </Typography>

      <Divider sx={{ mb: 4 }} />

      {/* Bộ lọc trạng thái */}
      <FormControl fullWidth sx={{ mb: 3 }}>
        <InputLabel id="status-select-label">Status</InputLabel>
        <Select
          labelId="status-select-label"
          value={status}
          onChange={handleStatusChange}
          label="Status"
        >
          <MenuItem value="WAITING">Waiting</MenuItem>
          <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
          <MenuItem value="COMPLETED">Completed</MenuItem>
        </Select>
      </FormControl>

      {/* Nút fetch */}
      <Button
        variant="contained"
        color="primary"
        onClick={getPatients}
        fullWidth
        sx={{ mb: 4 }}
      >
        Fetch Patients
      </Button>

      {/* Danh sách bệnh nhân */}
      <Grid container spacing={3}>
        {patients.map((patient) => (
          <Grid item xs={12} sm={6} md={4} key={patient.patientId}>
            <PatientItem patient={patient} />
          </Grid>
        ))}
      </Grid>
    </Box>
  )
}

export default MyPatients
