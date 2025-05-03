import { useState, useEffect } from 'react'
import { Box, Typography, Divider, Button, Stack, Modal, Grid } from '@mui/material'
import AppointmentItem from './AppointmentItem'
import AppointmentModal from './AppointmentModal'
import { toast } from 'react-toastify'
import { getListAppointmentForPatientsAPI } from '../../apis/appointmentAPI'
import { useSelector } from 'react-redux'
import { selectUser } from '../../redux/slice/userSlice'

const Appointment = () => {
  const [appointments, setAppointments] = useState([
    {
      appointmentId: 1,
      doctorId: 101,
      doctorName: 'Dang Tran Cong Quy',
      status: 'PENDING',
      slot: {
        date: '2025-04-27',
        startTime: '21:05',
        endTime: '23:24'
      }
    },
    {
      appointmentId: 2,
      doctorId: 102,
      doctorName: 'Nguyen Van B',
      status: 'CONFIRMED',
      slot: {
        date: '2025-04-28',
        startTime: '14:00',
        endTime: '15:00'
      }
    },
    {
      appointmentId: 3,
      doctorId: 103,
      doctorName: 'Tran Thi C',
      status: 'CONFIRMED',
      slot: {
        date: '2025-04-29',
        startTime: '09:00',
        endTime: '10:00'
      }
    }
  ])
  const [openModal, setOpenModal] = useState(false)
  const currenUser = useSelector(selectUser)

  const fetchAppointments = async () => {
    toast.promise(getListAppointmentForPatientsAPI(currenUser?.userId), {
      pending: 'loading appointment...'
    }).then((res) => {
      console.log(res)
      setAppointments(res)
    })
  }
  useEffect(() => {
    fetchAppointments()
  }, [])

  const handleOpenModal = () => {
    setOpenModal(true)
  }

  const handleCloseModal = () => {
    setOpenModal(false)
  }

  return (
    <Box>
      <Typography variant="h2" gutterBottom>
        Appointments
      </Typography>

      <Divider sx={{ mb: 2 }} />

      <Stack direction="row" justifyContent="flex-end" sx={{ mb: 3 }}>
        <Button variant="contained" color="primary" onClick={handleOpenModal}>
          Create Appointment
        </Button>
      </Stack>

      {appointments.length > 0 ? (
        <Grid container spacing={3}>
          {appointments.map((appointment) => (
            <Grid item xs={12} sm={6} md={4} key={appointment.appointmentId}>
              <AppointmentItem appointment={appointment} />
            </Grid>
          ))}
        </Grid>
      ) : (
        <Typography variant="body1" color="text.secondary">
          No appointments found.
        </Typography>
      )}

      {/* Modal create appointment */}
      <AppointmentModal
        open={openModal}
        onClose={handleCloseModal}
      />
    </Box>
  )
}

export default Appointment
