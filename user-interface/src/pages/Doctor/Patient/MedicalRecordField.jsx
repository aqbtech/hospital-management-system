import { Box, Typography, Divider, Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField, IconButton } from '@mui/material'
import { Edit as EditIcon } from '@mui/icons-material'
import { useState } from 'react'
import { updateMedicalRecord, createMedicalRecord } from '../../../apis/doctorAPI'
import { toast } from 'react-toastify'

const MedicalRecordField = ({ medicalRecords, patientId }) => {
  const [open, setOpen] = useState(false)
  const [selectedRecord, setSelectedRecord] = useState(null)
  const [records, setRecords] = useState(medicalRecords || [])

  const handleOpen = (record = null) => {
    if (record) {
      setSelectedRecord(record)
    } else {
      setSelectedRecord({
        recordId: '',
        patientId,
        doctor: { doctorId: '', name: '', specialty: '' },
        date: new Date().toISOString(),
        symptoms: '',
        diagnosis: ''
      })
    }
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
    setSelectedRecord(null)
  }

  const handleChange = (field, value) => {
    setSelectedRecord(prev => ({
      ...prev,
      [field]: value
    }))
  }

  const handleDoctorChange = (field, value) => {
    setSelectedRecord(prev => ({
      ...prev,
      doctor: {
        ...prev.doctor,
        [field]: value
      }
    }))
  }

  const handleSave = () => {
    if (!selectedRecord) return

    if (!selectedRecord.date || !selectedRecord.doctor.name || !selectedRecord.symptoms || !selectedRecord.diagnosis) {
      alert('Please fill in all required fields!')
      return
    }

    if (selectedRecord.recordId && records.find(r => r.recordId === selectedRecord.recordId)) {
      toast.promise(updateMedicalRecord(selectedRecord.recordId, selectedRecord ), {
        pending: 'Updating medical record...',
        success: 'Medical record updated successfully!'
      }).then(() => {
        setRecords(prev => prev.map(r => r.recordId === selectedRecord.recordId ? selectedRecord : r))
        handleClose()
      })
    } else {
      toast.promise(createMedicalRecord(selectedRecord, patientId), {
        pending: 'Creating medical record...',
        success: 'Medical record created successfully!'
      }).then((res) => {
        const newRecord = { ...selectedRecord, recordId: res.recordId }
        setRecords(prev => [...prev, newRecord])
        handleClose()
      })
    }
  }

  return (
    <Box sx={{ mb: 4 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
        <Typography variant="h5">Medical Records</Typography>
        <Button variant="contained" onClick={() => handleOpen()}>Add Record</Button>
      </Box>
      <Divider sx={{ mb: 2 }} />

      {records.length === 0 ? (
        <Typography>No medical records available.</Typography>
      ) : (
        records.map((record) => (
          <Box
            key={record.recordId}
            sx={{
              mb: 2,
              p: 2,
              bgcolor: 'grey.100',
              borderRadius: 2,
              position: 'relative',
              '&:hover': { bgcolor: 'grey.200' }
            }}
          >
            <IconButton
              size="small"
              sx={{ position: 'absolute', top: 8, right: 8 }}
              onClick={() => handleOpen(record)}
            >
              <EditIcon fontSize="small" />
            </IconButton>
            <Typography><strong>Examination Date:</strong> {new Date(record.date).toLocaleDateString('en-GB')}</Typography>
            <Typography><strong>Doctor:</strong> {record.doctor.name} ({record.doctor.specialty})</Typography>
            <Typography><strong>Symptoms:</strong> {record.symptoms}</Typography>
            <Typography><strong>Diagnosis:</strong> {record.diagnosis}</Typography>
          </Box>
        ))
      )}

      {/* Dialog for Add/Edit */}
      <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
        <DialogTitle>{selectedRecord?.recordId ? 'Edit Medical Record' : 'Add Medical Record'}</DialogTitle>
        <DialogContent dividers sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 1 }}>
          <TextField
            label="Examination Date"
            type="date"
            value={selectedRecord ? selectedRecord.date.slice(0, 10) : ''}
            onChange={(e) => handleChange('date', e.target.value)}
            InputLabelProps={{ shrink: true }}
          />
          <TextField
            label="Doctor's Name"
            value={selectedRecord?.doctor.name || ''}
            onChange={(e) => handleDoctorChange('name', e.target.value)}
          />
          <TextField
            label="Specialty"
            value={selectedRecord?.doctor.specialty || ''}
            onChange={(e) => handleDoctorChange('specialty', e.target.value)}
          />
          <TextField
            label="Symptoms"
            value={selectedRecord?.symptoms || ''}
            onChange={(e) => handleChange('symptoms', e.target.value)}
            multiline
            rows={2}
          />
          <TextField
            label="Diagnosis"
            value={selectedRecord?.diagnosis || ''}
            onChange={(e) => handleChange('diagnosis', e.target.value)}
            multiline
            rows={2}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} variant="outlined">Cancel</Button>
          <Button onClick={handleSave} variant="contained">Save</Button>
        </DialogActions>
      </Dialog>
    </Box>
  )
}

export default MedicalRecordField
