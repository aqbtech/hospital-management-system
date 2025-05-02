import React from 'react'
import { useNavigate } from 'react-router-dom'
import { Box, Typography, Button, Container } from '@mui/material'
import { LockOutlined } from '@mui/icons-material'

const Forbidden = () => {
  const navigate = useNavigate()

  return (
    <Container maxWidth="md" sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center' }}>
      <Box
        sx={{
          width: '100%',
          textAlign: 'center',
          py: 8
        }}
      >
        <LockOutlined
          sx={{
            fontSize: 100,
            color: 'error.main',
            mb: 3
          }}
        />
        <Typography
          variant="h1"
          sx={{
            fontSize: { xs: '4rem', sm: '6rem' },
            fontWeight: 800,
            background: (theme) => `linear-gradient(45deg, ${theme.palette.error.main}, ${theme.palette.error.light})`,
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent',
            mb: 2
          }}
        >
          403
        </Typography>
        <Typography
          variant="h5"
          sx={{
            fontWeight: 600,
            color: 'text.primary',
            mb: 2
          }}
        >
          Access Denied
        </Typography>
        <Typography
          variant="body1"
          sx={{
            color: 'text.secondary',
            mb: 4,
            maxWidth: 500,
            mx: 'auto'
          }}
        >
          You do not have permission to view this page. Please contact the administrator
          if you believe this is a mistake.
        </Typography>
        <Button
          variant="contained"
          size="large"
          onClick={() => navigate('/')}
          sx={{
            px: 4,
            py: 1.5,
            borderRadius: 2,
            fontSize: '1rem',
            textTransform: 'none'
          }}
        >
          Go Back Home
        </Button>
      </Box>
    </Container>
  )
}

export default Forbidden
