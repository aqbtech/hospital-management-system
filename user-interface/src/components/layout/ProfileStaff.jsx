// ProfileStaff.js
import { Avatar, Box, Card, CardContent, Divider, Grid, Typography, Chip, TextField, Button } from '@mui/material'
import useProfileStaff from '../../hooks/useProfileStaff'

const getInitials = (name) => {
  return name
    .split(' ')
    .map((word) => word[0])
    .join('')
    .toUpperCase()
}

const ProfileStaff = ({ role }) => {
  const {
    staffInfo,
    isEditing,
    tempStaffInfo,
    handleEdit,
    handleCancel,
    handleSave,
    handleInputChange
  } = useProfileStaff(role)

  return (
    <Box>
      <Typography variant="h2" gutterBottom>
        {`Profile ${role}`}
      </Typography>

      <Divider sx={{ mb: 2 }} />

      <Card elevation={3} sx={{ maxWidth: 1000, mx: 'auto', p: 3 }}>
        <CardContent>
          <Grid container spacing={4} alignItems="center" justifyContent="center">
            {/* Avatar + Tên */}
            <Grid item xs={12} md={4} textAlign="center">
              <Avatar
                sx={{
                  bgcolor: 'primary.main',
                  width: 150,
                  height: 150,
                  fontSize: 50,
                  margin: '0 auto'
                }}
              >
                {getInitials(staffInfo.name)}
              </Avatar>

              <Typography variant="h4" mt={2} fontWeight={700}>
                {isEditing ? (
                  <TextField
                    fullWidth
                    variant="outlined"
                    name="name"
                    value={tempStaffInfo.name}
                    onChange={handleInputChange}
                  />
                ) : (
                  staffInfo.name
                )}
              </Typography>

              <Typography variant="subtitle1" color="text.secondary">
                {isEditing ? (
                  <TextField
                    fullWidth
                    variant="outlined"
                    name="department"
                    value={tempStaffInfo.department}
                    onChange={handleInputChange}
                  />
                ) : (
                  staffInfo.department
                )}
              </Typography>
            </Grid>

            {/* Thông tin chi tiết */}
            <Grid item xs={12} md={8}>
              <Box display="flex" flexDirection="column" gap={3}>
                <Box>
                  <Typography variant="h6" color="primary" gutterBottom>
                    {role === 'Doctor' ? 'Specialty' : 'Department' }
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    {isEditing ? (
                      <TextField
                        fullWidth
                        variant="outlined"
                        name="department"
                        value={role === 'Doctor' ? tempStaffInfo.specialty : tempStaffInfo.department}
                        onChange={handleInputChange}
                      />
                    ) : (
                      role === 'Doctor' ? staffInfo.specialty : staffInfo.department
                    )}

                  </Typography>
                </Box>
                <Divider />

                <Box>
                  <Typography variant="h6" color="primary" gutterBottom>
                    Experience
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    {isEditing ? (
                      <TextField
                        fullWidth
                        variant="outlined"
                        name="experience"
                        type="number"
                        value={tempStaffInfo.experience}
                        disabled
                        onChange={handleInputChange}
                      />
                    ) : (
                      staffInfo.experience + ' Year'
                    )}
                  </Typography>
                </Box>
                <Divider />

                <Box>
                  <Typography variant="h6" color="primary" gutterBottom>
                    Qualifications
                  </Typography>
                  <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 1, mt: 1 }}>
                    {isEditing ? (
                      <TextField
                        fullWidth
                        variant="outlined"
                        name="qualifications"
                        value={tempStaffInfo.qualifications.join(', ')}
                        onChange={handleInputChange}
                      />
                    ) : (
                      staffInfo.qualifications.map((qual, index) => (
                        <Chip key={index} label={qual} color="secondary" variant="outlined" />
                      ))
                    )}
                  </Box>
                </Box>
              </Box>
            </Grid>
          </Grid>

          {/* Buttons */}
          <Box display="flex" justifyContent="flex-end" gap={2} mt={2}>
            {isEditing ? (
              <>
                <Button variant="outlined" color="secondary" onClick={handleCancel}>
                  Cancel
                </Button>
                <Button variant="contained" color="primary" onClick={handleSave}>
                  Save
                </Button>
              </>
            ) : (
              <Button variant="contained" color="primary" onClick={handleEdit}>
                Edit
              </Button>
            )}
          </Box>
        </CardContent>
      </Card>
    </Box>
  )
}

export default ProfileStaff
