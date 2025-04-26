import { useState, useEffect } from 'react'
import { Box, AppBar, Toolbar, IconButton, Typography } from '@mui/material'
import { Menu as MenuIcon } from '@mui/icons-material'
import LocalHospitalIcon from '@mui/icons-material/LocalHospital'
import Sidebar from './Sidebar'
import { SIDEBAR_DOCTOR, SIDEBAR_PATIENT, SIDEBAR_ACCOUNTANT } from '../../utils/constant.jsx'
import { useSearchParams } from 'react-router-dom'

export default function MainLayout({ children }) {
  const [open, setOpen] = useState(true)
  const [searchParams, setSearchParams] = useSearchParams()
  const tabFromUrl = searchParams.get('tab') || 'profile'
  const [currentTab, setCurrentTab] = useState(tabFromUrl)

  const toggleDrawer = () => {
    setOpen(!open)
  }

  const handleTabClick = (tab) => {
    setCurrentTab(tab)
    setSearchParams({ tab })
  }

  const handleLogout = () => {
    console.log('Logout')
    // Thực hiện chức năng logout ở đây
  }

  useEffect(() => {
    setCurrentTab(tabFromUrl)
  }, [tabFromUrl])

  return (
    <Box sx={{ display: 'flex', height: '100vh' }}>
      <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={toggleDrawer}
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>

          <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
            <LocalHospitalIcon sx={{ fontSize: 30 }} />
            <Typography variant="h6" noWrap component="div" sx={{ fontWeight: 700 }}>
              ATOM
            </Typography>
          </Box>
        </Toolbar>
      </AppBar>

      <Sidebar
        menuItems={SIDEBAR_DOCTOR}
        open={open}
        toggleDrawer={toggleDrawer}
        onLogout={handleLogout}
        currentTab={currentTab}
        onTabClick={handleTabClick}
      />

      <Box component="main" sx={{ flexGrow: 1, p: 3, mt: 8 }}>
        {children}
      </Box>
    </Box>
  )
}
