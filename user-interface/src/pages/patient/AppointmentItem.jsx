import { useState } from 'react'
import { Paper, Typography, Button, Stack, Box } from '@mui/material'
import { cancelAppointmentAPI } from '../../apis/appointmentAPI'
import { toast } from 'react-toastify'

const AppointmentItem = ({ appointment }) => {
  const [status, setStatus] = useState(appointment.status)

  const handleCancel = async () => {
    if (window.confirm('Are you sure you want to cancel this appointment?')) {
      toast.promise(cancelAppointmentAPI(appointment.appointmentId), {
        pending: 'Cancelling appointment...'
      }).then(() => {
        setStatus('CANCELLED')
      })
    }
  }

  const getStatusColor = () => {
    switch (status) {
    case 'CANCELLED':
      return 'error.main'
    case 'PENDING':
      return 'warning.main'
    case 'CONFIRMED':
      return 'success.main'
    default:
      return 'text.primary'
    }
  }

  return (
    <Paper
      elevation={3}
      sx={{
        p: 2,
        width: '380px',
        borderRadius: 3,
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-between',
        height: '100%'
      }}
    >
      <Stack spacing={1.5}>
        {/* Appointment ID */}
        <Typography variant="caption" color="text.secondary">
          ID: {appointment.appointmentId}
        </Typography>

        {/* Doctor Name */}
        <Typography variant="h6" color="text.primary" fontWeight={600}>
          Dr. {appointment.doctorName}
        </Typography>

        {/* Date & Time */}
        <Stack spacing={0.5}>
          <Typography variant="body2" color="text.secondary">
            Date: {appointment.slot.date}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Time: {appointment.slot.startTime} - {appointment.slot.endTime}
          </Typography>
        </Stack>

        {/* Status */}
        <Typography variant="body2" fontWeight={600}>
          Status:{' '}
          <Box component="span" sx={{ color: getStatusColor() }}>
            {status}
          </Box>
        </Typography>
      </Stack>

      {/* Cancel Button */}
      <Button
        variant="contained"
        disabled={status!=='PENDING'}
        color="error"
        size="small"
        onClick={handleCancel}
        sx={{ alignSelf: 'flex-start', mt: 2 }}
      >
          Cancel
      </Button>

    </Paper>
  )
}

export default AppointmentItem
