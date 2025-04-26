import { createTheme } from '@mui/material/styles'

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#1976d2', // Xanh dương chủ đạo
      light: '#63a4ff',
      dark: '#004ba0',
      contrastText: '#fff'
    },
    secondary: {
      main: '#00bfa5', // Xanh ngọc phụ
      light: '#5df2d6',
      dark: '#008e76',
      contrastText: '#fff'
    },
    background: {
      default: '#f5f7fa', // Màu nền sáng, nhẹ nhàng
      paper: '#ffffff' // Nền các card/form
    },
    text: {
      primary: '#2e3c50',
      secondary: '#6c757d'
    },
    error: {
      main: '#d32f2f'
    },
    warning: {
      main: '#ffa000'
    },
    info: {
      main: '#0288d1'
    },
    success: {
      main: '#388e3c'
    }
  },
  typography: {
    color: '#ffffff',
    fontFamily: 'Roboto, "Helvetica Neue", Arial, sans-serif',
    h1: {
      fontSize: '2.2rem',
      fontWeight: 700
    },
    h2: {
      fontSize: '1.8rem',
      fontWeight: 600
    },
    h3: {
      fontSize: '1.5rem',
      fontWeight: 600
    },
    body1: {
      fontSize: '1rem'
    },
    button: {
      textTransform: 'none',
      fontWeight: 500
    }
  },
  shape: {
    borderRadius: 8
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 8
        }
      }
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          borderRadius: 12
        }
      }
    }
  }
})

export default theme
