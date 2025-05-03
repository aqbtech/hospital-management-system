import { Box, Typography, Divider, Grid } from '@mui/material'
import { useState } from 'react'
import RecordItem from './RecordItem'

const MedicalRecord = () => {
  const [records, setRecords] = useState([
    {
      'recordId': 5678,
      'patientId': 1234,
      'doctor': {
        'doctorId': 987,
        'name': 'Dr. Nguyễn Văn A',
        'specialty': 'CARDIOLOGY'
      },
      'date': '2023-10-01T10:00:00Z',
      'symptoms': 'Fever, cough',
      'diagnosis': 'Flu'
    },
    {
      'recordId': 5679,
      'patientId': 1235,
      'doctor': {
        'doctorId': 988,
        'name': 'Dr. Trần Văn B',
        'specialty': 'NEUROLOGY'
      },
      'date': '2023-11-01T14:00:00Z',
      'symptoms': 'Headache, dizziness',
      'diagnosis': 'Migraine'
    },
    {
      'recordId': 5680,
      'patientId': 1235,
      'doctor': {
        'doctorId': 988,
        'name': 'Dr. Trần Văn B',
        'specialty': 'NEUROLOGY'
      },
      'date': '2023-11-01T14:00:00Z',
      'symptoms': 'Headache, dizziness',
      'diagnosis': 'Migraine'
    },
    {
      'recordId': 5681,
      'patientId': 1235,
      'doctor': {
        'doctorId': 988,
        'name': 'Dr. Trần Văn B',
        'specialty': 'NEUROLOGY'
      },
      'date': '2023-11-01T14:00:00Z',
      'symptoms': 'Headache, dizziness',
      'diagnosis': 'Migraine'
    }
  ])

  return (
    <Box>
      <Typography variant="h2" gutterBottom>
        Medical Record
      </Typography>

      <Divider sx={{ mb: 2 }} />

      {records.length > 0 ? (
        <Grid container spacing={3}>
          {records.map((record) => (
            <Grid
              item
              xs={12} sm={6} md={4} lg={3} // responsive grid: full width on xs, 2 items on sm, 3 items on md, 4 items on lg
              key={record.recordId}
            >
              <RecordItem record={record} />
            </Grid>
          ))}
        </Grid>
      ) : (
        <Typography variant="body1" color="text.secondary">
          No medical records found.
        </Typography>
      )}
    </Box>
  )
}

export default MedicalRecord
