import { Modal, Typography, Box, Button, TextField, MenuItem, Select, InputLabel, FormControl, Grid } from '@mui/material'
import { useState, useEffect } from 'react'
import { getDoctorForTimeAPI } from '../../apis/appointmentAPI'
import { toast } from 'react-toastify'
import { bookAppointmentAPI } from '../../apis/appointmentAPI'

const AppointmentModal = ({ open, onClose }) => {
  const [selectedDate, setSelectedDate] = useState(null)
  const [selectedTime, setSelectedTime] = useState('')
  const [doctors, setDoctors] = useState([
    { doctorId: 1, doctorName: 'Dang Tran Cong Quy' },
    { doctorId: 2, doctorName: 'Nguyen Van B' },
    { doctorId: 3, doctorName: 'Tran Thi C' }
  ])
  const [selectedDoctor, setSelectedDoctor] = useState('')
  const [error, setError] = useState('')

  useEffect(() => {
    if (selectedDate && selectedTime) {
      fetchAvailableDoctors()
    }
  }, [selectedDate, selectedTime])

  const fetchAvailableDoctors = async () => {
    toast.promise(getDoctorForTimeAPI(selectedDate, selectedTime), {
      pending: 'loading doctor...'
    }).then((res) => {
      console.log(res)
      setDoctors(res)
    })
  }

  const handleDateChange = (e) => {
    setSelectedDate(e.target.value)
    setSelectedTime('') // Reset time when the date changes
  }

  const handleTimeChange = (e) => {
    setSelectedTime(e.target.value)
  }

  const handleDoctorChange = (e) => {
    setSelectedDoctor(e.target.value)
  }

  const handleSubmit = () => {
    if (!selectedDate || !selectedTime || !selectedDoctor) {
      setError('Please fill all the fields.')
      return
    }
    const [hour, minute] = selectedTime.split(':').map(Number)
    const endHour = (hour + 1) % 24
    const endTime = `${endHour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`

    const appointmentData = {
      patientId: 1234,
      doctorId: selectedDoctor,
      slot: {
        date: selectedDate,
        startTime: selectedTime,
        endTime: endTime
      }
    }
    console.log('Booking appointment:', appointmentData)
    toast.promise(bookAppointmentAPI(appointmentData), {
      pending: 'booking appointment...',
      success: 'book appointment success'
    }).then(() => {
      handleClose()
    })
  }

  const handleClose = () => {
    setSelectedDate(null)
    setSelectedTime('')
    setSelectedDoctor('')
    onClose()
  }


  const today = new Date().toISOString().split('T')[0] // lấy ngày hôm nay dưới dạng YYYY-MM-DD

  return (
    <Modal open={open} onClose={onClose} aria-labelledby="modal-title" aria-describedby="modal-description">
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 600, // Thay đổi chiều rộng của modal
          bgcolor: 'background.paper',
          p: 4,
          borderRadius: 2,
          boxShadow: 24
        }}
      >
        <Typography id="modal-title" variant="h2" sx={{ mb: 2 }}>
          Book Appointment
        </Typography>

        <Typography id="modal-description" variant="body1" color="text.secondary" sx={{ mb: 2 }}>
          Please fill out the form below to book an appointment.
        </Typography>

        {/* Chọn ngày */}
        <TextField
          label="Select Date"
          type="date"
          fullWidth
          value={selectedDate}
          onChange={handleDateChange}
          InputLabelProps={{
            shrink: true
          }}
          inputProps={{
            min: today // Chỉ cho phép chọn ngày hôm nay trở đi
          }}
          sx={{ mb: 2 }}
        />

        {/* Chọn khoảng thời gian */}
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel>Time Slot</InputLabel>
          <Select
            value={selectedTime}
            onChange={handleTimeChange}
            label="Time Slot"
            disabled={!selectedDate} // Vô hiệu hóa khi chưa chọn ngày
          >
            {/* Các khoảng thời gian cố định (1 giờ) */}
            <MenuItem value="09:00">09:00 - 10:00</MenuItem>
            <MenuItem value="10:00">10:00 - 11:00</MenuItem>
            <MenuItem value="11:00">11:00 - 12:00</MenuItem>
            <MenuItem value="14:00">14:00 - 15:00</MenuItem>
            <MenuItem value="15:00">15:00 - 16:00</MenuItem>
            <MenuItem value="16:00">16:00 - 17:00</MenuItem>
          </Select>
        </FormControl>


        {/* Chọn bác sĩ */}
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel>Doctor</InputLabel>
          <Select
            value={selectedDoctor}
            onChange={handleDoctorChange}
            label="Doctor"
            disabled={!selectedTime} // Vô hiệu hóa khi chưa chọn thời gian
          >
            {doctors.map((doctor) => (
              <MenuItem key={doctor.doctorId} value={doctor.doctorId}>
                  Dr. {doctor.doctorName}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        {/* Error message */}
        {error && <Typography color="error" sx={{ mb: 2 }}>{error}</Typography>}

        <Grid container spacing={2}>
          <Grid item xs={6}>
            <Button
              variant="outlined"
              color="secondary"
              onClick={handleClose}
              fullWidth
            >
              Close
            </Button>
          </Grid>
          <Grid item xs={6}>
            <Button
              variant="contained"
              color="primary"
              onClick={handleSubmit}
              fullWidth
              disabled={!selectedDate || !selectedTime || !selectedDoctor}
            >
              Book Appointment
            </Button>
          </Grid>
        </Grid>
      </Box>
    </Modal>
  )
}

export default AppointmentModal
