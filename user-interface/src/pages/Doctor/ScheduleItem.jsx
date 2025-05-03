import { Box, Typography, Chip } from '@mui/material'

const ScheduleItem = ({ slot, date }) => {
  const formattedDate = date ? new Date(date).toLocaleDateString('vi-VN') : ''

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        mb: 1.5,
        p: 2,
        bgcolor: 'grey.100',
        borderRadius: 2
      }}
    >
      <Box>
        {date && (
          <Typography variant="body2" color="text.secondary" sx={{ mb: 0.5 }}>
            {formattedDate}
          </Typography>
        )}
        <Typography variant="h6">
          {slot.startTime} - {slot.endTime}
        </Typography>
      </Box>

      <Chip
        label={slot.status}
        color={slot.status === 'AVAILABLE' ? 'success' : 'error'}
        variant="outlined"
        sx={{ fontSize: '0.9rem', height: '28px' }}
      />
    </Box>
  )
}

export default ScheduleItem
