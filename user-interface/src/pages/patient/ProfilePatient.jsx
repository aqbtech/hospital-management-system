import { Avatar, Box, Card, CardContent, Divider, Grid, Typography, TextField, Button } from '@mui/material'
import useProfilePatient from '../../hooks/useProfilePatient'

const getInitials = (fullName) => {
  const names = fullName.split(' ')
  return names.length >= 2 ? `${names[0][0]}${names[names.length - 1][0]}`.toUpperCase() : names[0][0].toUpperCase()
}

const ProfilePatient = () => {
  const {
    userInfo,
    isEditing,
    tempUserInfo,
    handleEdit,
    handleCancel,
    handleSave,
    handleInputChange
  } = useProfilePatient()

  const { username, email, phone, profile } = userInfo
  const { fullName, gender, address, insuranceNumber } = profile

  return (
    <Box>
      <Typography variant="h2" gutterBottom>
        Profile
      </Typography>
      <Divider sx={{ mb: 2 }} />

      <Card elevation={3} sx={{ maxWidth: 1000, width: '100%', mx: 'auto', p: 3, borderRadius: 3 }}>
        <CardContent>
          <Grid container spacing={16} alignItems="center" justifyContent="center">
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
                {getInitials(fullName)}
              </Avatar>

              <Typography variant="h4" mt={2} fontWeight={700}>
                {isEditing ? (
                  <TextField
                    variant="outlined"
                    name="profile.fullName"
                    value={tempUserInfo.profile.fullName}
                    onChange={handleInputChange}
                    size="small"
                    fullWidth
                  />
                ) : (
                  fullName
                )}
              </Typography>

              <Typography variant="subtitle1" color="text.secondary">
                Patient
              </Typography>
            </Grid>

            <Grid item xs={12} md={8}>
              <Box display="flex" flexDirection="column" gap={3}>
                {/* Basic Info */}
                <Box>
                  <Typography variant="h6" color="primary" gutterBottom>
                    Account Information
                  </Typography>
                  <Box display="flex" flexDirection="column" gap={2}>
                    {['username', 'email', 'phone'].map((field) => (
                      <Typography key={field} variant="body1" color="text.secondary">
                        <strong>{field.charAt(0).toUpperCase() + field.slice(1)}:</strong>{' '}
                        {isEditing ? (
                          <TextField
                            variant="outlined"
                            name={field}
                            value={tempUserInfo[field]}
                            onChange={handleInputChange}
                            size="small"
                            fullWidth
                          />
                        ) : (
                          userInfo[field]
                        )}
                      </Typography>
                    ))}
                  </Box>
                </Box>
                <Divider />

                {/* Profile Info */}
                <Box>
                  <Typography variant="h6" color="primary" gutterBottom>
                    Profile
                  </Typography>
                  <Box display="flex" flexDirection="column" gap={2}>
                    {['gender', 'address', 'insuranceNumber'].map((field) => (
                      <Typography key={field} variant="body1" color="text.secondary">
                        <strong>{field.replace(/([A-Z])/g, ' $1')}:</strong>{' '}
                        {isEditing ? (
                          <TextField
                            variant="outlined"
                            name={`profile.${field}`}
                            value={tempUserInfo.profile[field]}
                            onChange={handleInputChange}
                            size="small"
                            fullWidth
                          />
                        ) : (
                          profile[field]
                        )}
                      </Typography>
                    ))}
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
