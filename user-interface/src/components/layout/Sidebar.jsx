import { Drawer, Box, IconButton, Typography, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Divider, useMediaQuery } from '@mui/material'
import { ChevronLeft, Logout, Menu } from '@mui/icons-material'
import { useTheme } from '@mui/material/styles'

const drawerWidth = 240

const Sidebar = ({ menuItems = [], onLogout, open, toggleDrawer, currentTab, onTabClick }) => {
  const theme = useTheme()
  const isMobile = useMediaQuery(theme.breakpoints.down('md'))

  return (
    <Drawer
      variant={isMobile ? 'temporary' : 'persistent'}
      open={open}
      onClose={toggleDrawer}
      sx={{
        width: drawerWidth,
        flexShrink: 0,
        ['& .MuiDrawer-paper']: {
          width: drawerWidth,
          boxSizing: 'border-box',
          bgcolor: 'primary.main',
          color: 'primary.contrastText'
        }
      }}
    >
      {/* Logo + Toggle */}
      <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', p: 2 }}>
        <Typography variant="h6" noWrap component="div" sx={{ fontWeight: 'bold' }}>
          ATOM
        </Typography>
        <IconButton onClick={toggleDrawer} sx={{ color: 'primary.contrastText' }}>
          {open ? <ChevronLeft /> : <Menu />}
        </IconButton>
      </Box>

      <Divider sx={{ bgcolor: 'primary.contrastText', opacity: 0.2 }} />

      {/* Menu Items */}
      <List>
        {menuItems.map((item) => (
          <ListItem key={item.text} disablePadding>
            <ListItemButton
              onClick={() => onTabClick(item.tab)}
              sx={{
                bgcolor: currentTab === item.tab ? 'primary.dark' : 'transparent',
                '&:hover': {
                  bgcolor: 'primary.dark'
                }
              }}
            >
              <ListItemIcon sx={{ color: 'primary.contrastText' }}>
                {item.icon}
              </ListItemIcon>
              <ListItemText primary={item.text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>

      <Box sx={{ flexGrow: 1 }} />

      {/* Logout Button */}
      <Box sx={{ p: 2 }}>
        <Divider sx={{ bgcolor: 'primary.contrastText', opacity: 0.2, mb: 2 }} />
        <ListItem disablePadding onClick={onLogout}>
          <ListItemButton>
            <ListItemIcon sx={{ color: 'primary.contrastText' }}>
              <Logout />
            </ListItemIcon>
            <ListItemText primary="Logout" />
          </ListItemButton>
        </ListItem>
      </Box>
    </Drawer>
  )
}

export default Sidebar