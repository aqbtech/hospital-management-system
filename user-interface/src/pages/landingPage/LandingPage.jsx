import { useNavigate } from 'react-router-dom'
import { Box, Typography, Button } from '@mui/material'
import Header from '../../components/layout/Header'
import Footer from '../../components/layout/Footer'
import medicalTeamImg from '../../assets/medical-team.jpg'

const LandingPage = () => {
  const navigate = useNavigate()

  return (
    <>
      <Box sx={{ minHeight: '100vh', bgcolor: '#f8fafc', display: 'flex', flexDirection: 'column' }}>
        <Header />

        <Box
          sx={{
            flexGrow: 1,
            display: 'flex',
            flexDirection: { xs: 'column', md: 'row' }
          }}
        >
          {/* Left: Image */}
          <Box
            sx={{
              width: { xs: '100%', md: '50%' },
              height: { xs: 300, md: 'auto' },
              maxHeight: { md: 'calc(100vh - 64px)' },
              overflow: 'hidden',
              position: 'relative'
            }}
          >
            <img
              src={medicalTeamImg}
              alt="Medical Team"
              style={{
                width: '100%',
                height: '100%',
                objectFit: 'cover'
              }}
            />
          </Box>

          {/* Right: Content */}
          <Box
            sx={{
              width: { xs: '100%', md: '50%' },
              p: { xs: 3, md: 6 },
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'center',
              bgcolor: '#fff'
            }}
          >
            <Typography
              variant="h2"
              sx={{
                fontWeight: 800,
                mb: 2,
                background: 'linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
                fontSize: { xs: '2.5rem', md: '3.5rem' }
              }}
            >
              Private Hospital
            </Typography>

            <Typography
              variant="h5"
              sx={{
                opacity: 0.9,
                mb: 3,
                color: '#666',
                fontSize: { xs: '1.2rem', md: '1.5rem' }
              }}
            >
              Your health is our priority
            </Typography>

            <Typography
              sx={{
                mb: 4,
                color: '#666',
                fontSize: { xs: '1rem', md: '1.1rem' },
                lineHeight: 1.8,
                textAlign: 'justify'
              }}
            >
              Experience world-class healthcare services with our team of expert doctors and modern facilities.
              We are committed to providing you with the best medical care in a comfortable and welcoming environment.
            </Typography>

            <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
              <Button
                variant="contained"
                size="large"
                sx={{
                  bgcolor: '#2196F3',
                  color: '#fff',
                  px: 4,
                  py: 1.5,
                  borderRadius: '30px',
                  '&:hover': {
                    bgcolor: '#1976D2',
                    transform: 'translateY(-2px)',
                    boxShadow: '0 4px 20px rgba(33, 150, 243, 0.3)'
                  },
                  transition: 'all 0.3s ease'
                }}
                onClick={() => navigate('/register')}
              >
                Get Started
              </Button>

              <Button
                variant="outlined"
                size="large"
                sx={{
                  borderColor: '#2196F3',
                  color: '#2196F3',
                  px: 4,
                  py: 1.5,
                  borderRadius: '30px',
                  '&:hover': {
                    borderColor: '#1976D2',
                    bgcolor: 'rgba(33, 150, 243, 0.04)',
                    transform: 'translateY(-2px)'
                  },
                  transition: 'all 0.3s ease'
                }}
                onClick={() => navigate('/login')}
              >
                Log In
              </Button>
            </Box>
          </Box>
        </Box>

        <Footer />
      </Box>
    </>
  )
}

export default LandingPage
