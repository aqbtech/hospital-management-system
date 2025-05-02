import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import LocalHospitalIcon from '@mui/icons-material/LocalHospital'
import MenuIcon from '@mui/icons-material/Menu'
import {
  AppBar,
  Toolbar,
  Typography,
  Box,
  IconButton,
  Menu,
  MenuItem,
  Button,
  Container,
  useMediaQuery
} from '@mui/material'
import { useTheme } from '@mui/material/styles'

const menuItems = [
  { label: 'Home', path: '/' },
  { label: 'About Us', path: '/about' },
  { label: 'Service', path: '/services' },
  { label: 'Contact', path: '/contact' }
]

const Header = () => {
  const theme = useTheme()
  const isMobile = useMediaQuery(theme.breakpoints.down('sm'))
  const [anchorEl, setAnchorEl] = useState(null)
  const navigate = useNavigate()

  const handleMenuOpen = (event) => {
    setAnchorEl(event.currentTarget)
  }

  const handleMenuClose = () => {
    setAnchorEl(null)
  }

  const handleNavigate = (path) => {
    navigate(path)
    handleMenuClose()
  }

  return (
    <AppBar position="static" sx={{ bgcolor: theme.palette.background.paper, boxShadow: 3 }}>
      <Container maxWidth="lg">
        <Toolbar sx={{ justifyContent: 'space-between' }}>
          {/* Logo */}
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, cursor: 'pointer' }} onClick={() => navigate('/')}>
            <LocalHospitalIcon color="primary" />
            <Typography variant="h6" color="primary" sx={{ fontWeight: 700 }}>
              ATOM
            </Typography>
          </Box>

          {/* Menu Items */}
          {isMobile ? (
            <>
              <IconButton onClick={handleMenuOpen}>
                <MenuIcon />
              </IconButton>
              <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                {menuItems.map((item) => (
                  <MenuItem key={item.label} onClick={() => handleNavigate(item.path)}>
                    {item.label}
                  </MenuItem>
                ))}
              </Menu>
            </>
          ) : (
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 3 }}>
              {menuItems.map((item) => (
                <Typography
                  key={item.label}
                  variant="button"
                  onClick={() => handleNavigate(item.path)}
                  sx={{
                    cursor: 'pointer',
                    color: theme.palette.text.primary,
                    '&:hover': { color: theme.palette.primary.main }
                  }}
                >
                  {item.label}
                </Typography>
              ))}
              <Button variant="contained" color="primary" onClick={() => navigate('/register')}>
                Get Started
              </Button>
            </Box>
          )}
        </Toolbar>
      </Container>
    </AppBar>
  )
}

export default Header
