import { Box, Container, Grid, Typography, Link, Divider } from '@mui/material'
import LocalHospitalIcon from '@mui/icons-material/LocalHospital'
import { useTheme } from '@mui/material/styles'

const Footer = () => {
  const theme = useTheme()

  return (
    <Box
      sx={{
        bgcolor: theme.palette.grey[100],
        pt: 6,
        pb: 4,
        mt: 8,
        borderTop: `1px solid ${theme.palette.divider}`
      }}
    >
      <Container maxWidth="lg">
        <Grid container spacing={4}>
          {/* Cột 1: Logo & mô tả */}
          <Grid item xs={12} md={4}>
            <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
              <LocalHospitalIcon color="primary" sx={{ fontSize: 32 }} />
              <Typography variant="h6" sx={{ ml: 1, fontWeight: 600 }} color="primary">
                ATOM
              </Typography>
            </Box>
            <Typography variant="body2" color="text.secondary">
              Hospital management system, connect all employee and patients in ATOM together
            </Typography>
          </Grid>

          {/* Cột 2: Thông tin */}
          <Grid item xs={6} md={2}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
              About
            </Typography>
            <Link href="/ve-chung-toi" underline="hover" color="text.secondary" display="block">About Us</Link>
            <Link href="/doi-ngu" underline="hover" color="text.secondary" display="block">Team</Link>
            <Link href="/tuyen-dung" underline="hover" color="text.secondary" display="block">Careers</Link>
          </Grid>

          {/* Cột 3: Dịch vụ */}
          <Grid item xs={6} md={3}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
              Services
            </Typography>
            <Link href="/dat-lich" underline="hover" color="text.secondary" display="block">Book Appointment</Link>
            <Link href="/ho-so" underline="hover" color="text.secondary" display="block">Medical Records</Link>
            <Link href="/tu-van" underline="hover" color="text.secondary" display="block">Online Consultation</Link>
          </Grid>

          {/* Cột 4: Liên hệ */}
          <Grid item xs={12} md={3}>
            <Typography variant="subtitle1" fontWeight="bold" gutterBottom>
              Contact
            </Typography>
            <Typography variant="body2" color="text.secondary">123 Nguyen Van Bao St, Go Vap, HCMC</Typography>
            <Typography variant="body2" color="text.secondary">Email: lienhe@atom.vn</Typography>
            <Typography variant="body2" color="text.secondary">Hotline: 1900 1234</Typography>
          </Grid>
        </Grid>

        {/* Gạch ngang + bản quyền */}
        <Divider sx={{ my: 4 }} />
        <Typography variant="body2" color="text.secondary" align="center">
          © {new Date().getFullYear()} Hệ thống Bệnh viện ABC. Mọi quyền được bảo lưu.
        </Typography>
      </Container>
    </Box>
  )
}

export default Footer
