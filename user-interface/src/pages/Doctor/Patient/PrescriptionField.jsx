import { Box, Typography, Divider, Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField, IconButton } from '@mui/material'
import { useState } from 'react'
import EditIcon from '@mui/icons-material/Edit'
import AddIcon from '@mui/icons-material/Add'
import { toast } from 'react-toastify'
import { createPrescriptionsAPI, updatePrescriptionsAPI } from '../../../apis/doctorAPI'

const PrescriptionField = ({ prescriptions: initialPrescriptions, patientId }) => {
  const [prescriptions, setPrescriptions] = useState(initialPrescriptions)
  const [open, setOpen] = useState(false)
  const [isEditMode, setIsEditMode] = useState(false)
  const [selectedPrescription, setSelectedPrescription] = useState(null)

  const handleOpenView = (prescription) => {
    setSelectedPrescription(prescription)
    setIsEditMode(false)
    setOpen(true)
  }

  const handleOpenEdit = (prescription) => {
    setSelectedPrescription(prescription)
    setIsEditMode(true)
    setOpen(true)
  }

  const handleOpenAdd = () => {
    setSelectedPrescription({
      prescriptionId: '',
      createDate: '',
      doctorCreate: '',
      medications: []
    })
    setIsEditMode(true)
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
    setSelectedPrescription(null)
  }

  const handleSave = () => {
    if (!selectedPrescription) return

    if (selectedPrescription.prescriptionId === '' || selectedPrescription.createDate === '' || selectedPrescription.doctorCreate === '') {
      alert('Please fill in all required fields!')
      return
    }

    if (prescriptions.find(p => p.prescriptionId === selectedPrescription.prescriptionId)) {
      // Edit mode: update existing
      toast.promise(updatePrescriptionsAPI(selectedPrescription, selectedPrescription.prescriptionId), {
        pending: 'Updating prescription...',
        success: 'Update prescription successfully!'
      }).then(() => {
        setPrescriptions(prev =>
          prev.map(p => p.prescriptionId === selectedPrescription.prescriptionId ? selectedPrescription : p)
        )
        handleClose()
      })
    } else {
      // Add mode: create new
      toast.promise(createPrescriptionsAPI({ ...selectedPrescription, patientId: patientId }), {
        pending: 'Creating prescription for patient...',
        success: 'Create prescription successfully!'
      }).then((res) => {
        const newPrescription = { ...selectedPrescription, prescriptionId: res.prescriptionId }
        setPrescriptions(prev => [...prev, newPrescription])
        handleClose()
      })
    }
  }

  const handleMedicationChange = (idx, field, value) => {
    const updatedMedications = [...selectedPrescription.medications]
    updatedMedications[idx][field] = value
    setSelectedPrescription({ ...selectedPrescription, medications: updatedMedications })
  }

  const handleAddMedication = () => {
    setSelectedPrescription(prev => ({
      ...prev,
      medications: [...prev.medications, { medicineId: '', dosage: '', frequency: '', duration: '', notes: '' }]
    }))
  }

  return (
    <Box sx={{ mb: 4 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
        <Typography variant="h5">Prescriptions</Typography>
        <Button
          startIcon={<AddIcon />}
          variant="contained"
          size="small"
          onClick={handleOpenAdd}
        >
          Add Prescription
        </Button>
      </Box>

      <Divider sx={{ mb: 2 }} />

      {prescriptions.length === 0 ? (
        <Typography>No prescriptions available.</Typography>
      ) : (
        prescriptions.map((prescription) => (
          <Box
            key={prescription.prescriptionId}
            sx={{
              mb: 2,
              p: 2,
              bgcolor: 'grey.100',
              borderRadius: 2,
              position: 'relative',
              cursor: 'pointer',
              '&:hover': { bgcolor: 'grey.200' }
            }}
          >
            <IconButton
              size="small"
              sx={{ position: 'absolute', top: 8, right: 8 }}
              onClick={() => handleOpenEdit(prescription)}
            >
              <EditIcon fontSize="small" />
            </IconButton>

            <Box onClick={() => handleOpenView(prescription)}>
              <Typography><strong>Prescription ID:</strong> {prescription.prescriptionId}</Typography>
              <Typography><strong>Create Date:</strong> {new Date(prescription.createDate).toLocaleDateString('en-GB')}</Typography>
              <Typography><strong>Doctor Create:</strong> Dr {prescription.doctorCreate}</Typography>
            </Box>
          </Box>
        ))
      )}

      {/* Modal */}
      <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
        <DialogTitle>{isEditMode ? 'Add / Edit Prescription' : 'Prescription Details'}</DialogTitle>
        <DialogContent dividers>

          {selectedPrescription && (
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
              {isEditMode ? (
                <>
                  <TextField
                    label="Prescription ID"
                    value={selectedPrescription.prescriptionId}
                    onChange={(e) => setSelectedPrescription({ ...selectedPrescription, prescriptionId: e.target.value })}
                    fullWidth
                  />
                  <TextField
                    type='date'
                    value={selectedPrescription.createDate}
                    onChange={(e) => setSelectedPrescription({ ...selectedPrescription, createDate: e.target.value })}
                    fullWidth
                  />
                  <TextField
                    label="Doctor Create"
                    value={selectedPrescription.doctorCreate}
                    onChange={(e) => setSelectedPrescription({ ...selectedPrescription, doctorCreate: e.target.value })}
                    fullWidth
                  />

                  <Divider sx={{ my: 2 }} />

                  <Typography variant="subtitle1" fontWeight="bold">Medications</Typography>

                  {selectedPrescription.medications.map((med, idx) => (
                    <Box key={idx} sx={{ bgcolor: 'grey.100', p: 2, borderRadius: 2 }}>
                      <TextField
                        label="Medicine ID"
                        value={med.medicineId}
                        onChange={(e) => handleMedicationChange(idx, 'medicineId', e.target.value)}
                        fullWidth
                        sx={{ mb: 1 }}
                      />
                      <TextField
                        label="Dosage"
                        value={med.dosage}
                        onChange={(e) => handleMedicationChange(idx, 'dosage', e.target.value)}
                        fullWidth
                        sx={{ mb: 1 }}
                      />
                      <TextField
                        label="Frequency"
                        value={med.frequency}
                        onChange={(e) => handleMedicationChange(idx, 'frequency', e.target.value)}
                        fullWidth
                        sx={{ mb: 1 }}
                      />
                      <TextField
                        label="Duration"
                        value={med.duration}
                        onChange={(e) => handleMedicationChange(idx, 'duration', e.target.value)}
                        fullWidth
                        sx={{ mb: 1 }}
                      />
                      <TextField
                        label="Notes"
                        value={med.notes}
                        onChange={(e) => handleMedicationChange(idx, 'notes', e.target.value)}
                        fullWidth
                      />
                    </Box>
                  ))}

                  <Button onClick={handleAddMedication} sx={{ mt: 2 }} variant="outlined">
                    Add Medication
                  </Button>
                </>
              ) : (
                <>
                  <Typography variant="subtitle1"><strong>Prescription ID:</strong> {selectedPrescription.prescriptionId}</Typography>
                  <Typography variant="subtitle1"><strong>Create Date:</strong> {new Date(selectedPrescription.createDate).toLocaleDateString('en-GB')}</Typography>
                  <Typography variant="subtitle1"><strong>Doctor create:</strong> Dr {selectedPrescription.doctorCreate}</Typography>

                  <Divider sx={{ my: 2 }} />

                  {selectedPrescription.medications.map((med, idx) => (
                    <Box key={idx} sx={{ mb: 2 }}>
                      <Typography><strong>Medicine ID:</strong> {med.medicineId}</Typography>
                      <Typography><strong>Dosage:</strong> {med.dosage}</Typography>
                      <Typography><strong>Frequency:</strong> {med.frequency}</Typography>
                      <Typography><strong>Duration:</strong> {med.duration}</Typography>
                      <Typography><strong>Notes:</strong> {med.notes}</Typography>
                      <Divider sx={{ my: 1 }} />
                    </Box>
                  ))}
                </>
              )}
            </Box>
          )}
        </DialogContent>

        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          {isEditMode && (
            <Button variant="contained" onClick={handleSave}>Save</Button>
          )}
        </DialogActions>
      </Dialog>
    </Box>
  )
}

export default PrescriptionField
