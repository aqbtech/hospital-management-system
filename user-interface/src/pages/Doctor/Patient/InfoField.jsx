import { Box, Typography, Divider, Grid, Paper, Avatar } from '@mui/material'

const InfoField = ({ identity, personalInfo }) => {
  const fullName = `${personalInfo.lastName} ${personalInfo.firstName}`
  const firstChar = personalInfo.firstName?.charAt(0)?.toUpperCase() || ''

  return (
    <Paper elevation={3} sx={{ p: 4, mb: 4, borderRadius: 3 }}>
      <Grid container spacing={3} alignItems="center">
        {/* Avatar */}
        <Grid item xs={12} md="auto">
          <Avatar sx={{ width: 80, height: 80, fontSize: 32 }}>
            {firstChar}
          </Avatar>
        </Grid>

        {/* Info */}
        <Grid item xs={12} md>
          <Typography variant="h5" fontWeight="bold">
            {fullName}
          </Typography>
          <Typography color="text.secondary" sx={{ mb: 2 }}>
            {personalInfo.gender === 'MALE' ? 'Male' : 'Female'} | {new Date(personalInfo.dob).toLocaleDateString('en-GB')}
          </Typography>

          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <InfoItem label="Phone" value={personalInfo.phone} />
            </Grid>
            <Grid item xs={12} sm={6}>
              <InfoItem label="Address" value={personalInfo.address} />
            </Grid>
            <Grid item xs={12} sm={6}>
              <InfoItem label="Citizen ID" value={identity.citizenId} />
            </Grid>
            <Grid item xs={12} sm={6}>
              <InfoItem label="Insurance ID" value={identity.insuranceId} />
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Paper>
  )
}

const InfoItem = ({ label, value }) => {
  return (
    <Box sx={{ mb: 1 }}>
      <Typography variant="body2" color="text.secondary">
        {label}
      </Typography>
      <Typography variant="subtitle1" fontWeight="500">
        {value || '-'}
      </Typography>
    </Box>
  )
}

export default InfoField
