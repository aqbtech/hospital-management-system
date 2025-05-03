import { Avatar, Box, Card, CardContent, Divider, Grid, Typography, TextField, Button } from '@mui/material'
import useProfilePatient from '../../hooks/useProfilePatient'

const getInitials = (firstName, lastName) => {
  return `${firstName[0]}${lastName[0]}`.toUpperCase()
}

const ProfilePatient = () => {
  const {
    staffInfo,
    isEditing,
    tempStaffInfo,
    handleEdit,
    handleCancel,
    handleSave,
    handleInputChange
  } = useProfilePatient()

  const { identity, personalInfo } = staffInfo
  const { firstName, lastName } = personalInfo

  return (
    <Box>
      <Typography variant="h2" gutterBottom>
        Profile
      </Typography>
      <Divider sx={{ mb: 2 }} />

      <Card elevation={3} sx={{ maxWidth: 1000, width: '100%', mx: 'auto', p: 3, borderRadius: 3 }}>
        <CardContent>
          <Grid container spacing={16} alignItems="center" justifyContent="center">
            {/* Avatar + Tên */}
            <Grid item xs={12}	md={4} textAlign="center">
              <Avatar
                sx={{
                  bgcolor: 'primary.main',
                  width: 150,
                  height: 150,
                  fontSize: 50,
                  margin: '0 auto'
                }}
              >
                {getInitials(firstName, lastName)}
              </Avatar>

              <Typography variant="h4" mt={2} fontWeight={700}>
                {isEditing ? (
                  <Box sx={{ display: 'flex', gap: 1 }}>
                    <TextField
                      variant="outlined"
                      name="personalInfo.firstName"
                      value={tempStaffInfo.personalInfo.firstName}
                      onChange={handleInputChange}
                      size="small"
                    />
                    <TextField
                      variant="outlined"
                      name="personalInfo.lastName"
                      value={tempStaffInfo.personalInfo.lastName}
                      onChange={handleInputChange}
                      size="small"
                    />
                  </Box>
                ) : (
                  `${firstName} ${lastName}`
                )}
              </Typography>

              <Typography variant="subtitle1" color="text.secondary">
                Patient
              </Typography>
            </Grid>

            {/* Thông tin chi tiết */}
            <Grid item xs={12} md={8}>
              <Box display="flex" flexDirection="column" gap={3}>
                {/* Personal Information */}
                <Box>
                  <Typography variant="h6" color="primary" gutterBottom>
                    Personal Information
                  </Typography>
                  <Box display="flex" flexDirection="column" gap={2}>
                    <Typography variant="body1" color="text.secondary">
                      <strong>Date of Birth:</strong>{' '}
                      {isEditing ? (
                        <TextField
                          variant="outlined"
                          name="personalInfo.dob"
                          type="date"
                          value={tempStaffInfo.personalInfo.dob}
                          onChange={handleInputChange}
                          size="small"
                        />
                      ) : (
                        personalInfo.dob
                      )}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <strong>Gender:</strong>{' '}
                      {isEditing ? (
                        <TextField
                          variant="outlined"
                          name="personalInfo.gender"
                          value={tempStaffInfo.personalInfo.gender}
                          onChange={handleInputChange}
                          size="small"
                        />
                      ) : (
                        personalInfo.gender
                      )}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <strong>Phone:</strong>{' '}
                      {isEditing ? (
                        <TextField
                          variant="outlined"
                          name="personalInfo.phone"
                          value={tempStaffInfo.personalInfo.phone}
                          onChange={handleInputChange}
                          size="small"
                        />
                      ) : (
                        personalInfo.phone
                      )}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <strong>Address:</strong>{' '}
                      {isEditing ? (
                        <TextField
                          variant="outlined"
                          name="personalInfo.address"
                          value={tempStaffInfo.personalInfo.address}
                          onChange={handleInputChange}
                          size="small"
                          fullWidth
                        />
                      ) : (
                        personalInfo.address
                      )}
                    </Typography>
                  </Box>
                </Box>
                <Divider />

                {/* Identity Information */}
                <Box>
                  <Typography variant="h6" color="primary" gutterBottom>
                    Identity Information
                  </Typography>
                  <Box display="flex" flexDirection="column" gap={2}>
                    <Typography variant="body1" color="text.secondary">
                      <strong>Citizen ID:</strong>{' '}
                      {isEditing ? (
                        <TextField
                          variant="outlined"
                          name="identity.citizenId"
                          value={tempStaffInfo.identity.citizenId}
                          onChange={handleInputChange}
                          size="small"
                        />
                      ) : (
                        identity.citizenId
                      )}
                    </Typography>
                    <Typography variant="body1" color="text.secondary">
                      <strong>Insurance ID:</strong>{' '}
                      {isEditing ? (
                        <TextField
                          variant="outlined"
                          name="identity.insuranceId"
                          value={tempStaffInfo.identity.insuranceId}
                          onChange={handleInputChange}
                          size="small"
                        />
                      ) : (
                        identity.insuranceId
                      )}
                    </Typography>
                  </Box>
                </Box>
              </Box>
            </Grid>
          </Grid>

          {/* Buttons */}
          <Box display="flex" justifyContent="flex-end" gap={2} mt={3}>
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

export default ProfilePatient