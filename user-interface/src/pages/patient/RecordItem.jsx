import { Card, CardContent, Typography, Box, Divider } from '@mui/material'

const LabelValue = ({ label, value }) => (
  <Box sx={{ mb: 1 }}>
    <Typography variant="subtitle2" sx={{ fontWeight: 'bold', color: 'primary.main' }}>
      {label}
    </Typography>
    <Typography variant="body2" color="text.secondary" sx={{ fontSize: '0.875rem' }}>
      {value}
    </Typography>
  </Box>
)

const RecordItem = ({ record }) => (
  <Card
    sx={{
      width: 350,
      mb: 2,
      borderRadius: 3,
      boxShadow: 4,
      transition: '0.3s ease-in-out',
      '&:hover': {
        boxShadow: 8,
        transform: 'scale(1.03)'
      },
      backgroundColor: '#fff',
      overflow: 'hidden' // Cải thiện khi hover
    }}
  >
    <CardContent sx={{ p: 2 }}>
      <Typography variant="h6" sx={{ mb: 1, fontWeight: 'bold', color: 'primary.dark' }}>
        Record #{record.recordId}
      </Typography>

      <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
        Patient ID: <span style={{ fontWeight: 'bold' }}>{record.patientId}</span>
      </Typography>

      <Divider sx={{ mb: 2 }} />

      <LabelValue label="Doctor" value={`${record.doctor.name} (${record.doctor.specialty})`} />
      <LabelValue label="Appointment Date" value={new Date(record.date).toLocaleString()} />
      <LabelValue label="Symptoms" value={record.symptoms} />
      <LabelValue label="Diagnosis" value={record.diagnosis} />
    </CardContent>
  </Card>
)

export default RecordItem
