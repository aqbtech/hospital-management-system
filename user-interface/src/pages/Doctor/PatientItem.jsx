import { Grid, Card, CardContent, Chip, Typography, Box } from '@mui/material'
import { useNavigate } from 'react-router-dom'

const getStatusColor = (status) => {
  switch (status) {
  case 'WAITING':
    return 'warning'
  case 'IN_PROGRESS':
    return 'info'
  case 'DONE':
    return 'success'
  default:
    return 'default'
  }
}

const PatientItem = ({ patient }) => {
  const navigate = useNavigate()
  const handleClick = () => {
    navigate(`/doctor/patient/${patient.patientId}`)
  }

  return (
    <Grid item xs={12} sm={6} md={4}>
      <Card
        onClick={handleClick}
        sx={{
          cursor: 'pointer',
          '&:hover': {
            boxShadow: 6,
            bgcolor: 'grey.100'
          },
          transition: '0.3s'
        }}
      >
        <CardContent>
          <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <Typography variant="h6">{patient.name}</Typography>
            <Chip
              label={patient.status}
              color={getStatusColor(patient.status)}
              variant="outlined"
              size="small"
            />
          </Box>
          <Typography variant="body2" color="text.secondary" sx={{ mt: 1 }}>
            Waiting time: {patient.waitingTime} minutes
          </Typography>
        </CardContent>
      </Card>
    </Grid>
  )
}

export default PatientItem