import React from 'react'
import { useRegisterForm } from '../../hooks/useRegisterForm'
import {
  Container,
  Box,
  Typography,
  TextField,
  Button,
  Link,
  Paper,
  useTheme,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  FormHelperText
} from '@mui/material'
import { Person as PersonIcon } from '@mui/icons-material'
import { Link as RouterLink } from 'react-router-dom'
import { USER_TYPES } from '../../utils/constant'
import useTitleDocument from '../../hooks/useTitleDocument'

const RegisterForm = () => {
  const theme = useTheme()
  const {
    register,
    handleSubmit,
    onSubmit,
    errors,
    isSubmitting
  } = useRegisterForm()

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        background: `linear-gradient(135deg, ${theme.palette.primary.main} 0%, ${theme.palette.primary.dark} 100%)`,
        py: 4
      }}
    >
      <Container maxWidth="sm">
        <Paper
          elevation={6}
          sx={{
            p: { xs: 2, sm: 4 },
            borderRadius: 2,
            background: 'rgba(255, 255, 255, 0.95)'
          }}
        >
          <Box sx={{ textAlign: 'center', mb: 4 }}>
            <PersonIcon sx={{ fontSize: 40, color: 'primary.main', mb: 1 }} />
            <Typography variant="h4" component="h2" gutterBottom>
              Create Account
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Please fill in your information to register
            </Typography>
          </Box>

          <Box component="form" onSubmit={handleSubmit(onSubmit)} noValidate>
            <TextField
              fullWidth
              label="Username"
              {...register('username')}
              error={!!errors.username}
              helperText={errors.username?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Full Name"
              {...register('fullName')}
              error={!!errors.fullName}
              helperText={errors.fullName?.message}
              sx={{ mb: 2 }}
            />

            <FormControl fullWidth sx={{ mb: 2 }} error={!!errors.role}>
              <InputLabel id="role-label">Role</InputLabel>
              <Select
                labelId="role-label"
                label="Role"
                defaultValue=""
                {...register('role')}
              >
                {USER_TYPES.map((type) => (
                  <MenuItem key={type} value={type}>
                    {type.charAt(0) + type.slice(1).toLowerCase()}
                  </MenuItem>
                ))}
              </Select>
              <FormHelperText>{errors.role?.message}</FormHelperText>
            </FormControl>

            <FormControl fullWidth sx={{ mb: 2 }} error={!!errors.gender}>
              <InputLabel id="gender-label">Gender</InputLabel>
              <Select
                labelId="gender-label"
                label="Gender"
                defaultValue=""
                {...register('gender')}
              >
                <MenuItem value="MALE">Male</MenuItem>
                <MenuItem value="FEMALE">Female</MenuItem>
              </Select>
              <FormHelperText>{errors.gender?.message}</FormHelperText>
            </FormControl>

            <TextField
              fullWidth
              label="Date of Birth"
              type="date"
              InputLabelProps={{ shrink: true }}
              {...register('dob')}
              error={!!errors.dob}
              helperText={errors.dob?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Email Address"
              {...register('email')}
              error={!!errors.email}
              helperText={errors.email?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Phone Number"
              {...register('phone')}
              error={!!errors.phone}
              helperText={errors.phone?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Address"
              {...register('address')}
              error={!!errors.address}
              helperText={errors.address?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Insurance Number"
              {...register('insuranceNumber')}
              error={!!errors.insuranceNumber}
              helperText={errors.insuranceNumber?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Emergency Contact"
              {...register('emergencyContact')}
              error={!!errors.emergencyContact}
              helperText={errors.emergencyContact?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Password"
              type="password"
              {...register('password')}
              error={!!errors.password}
              helperText={errors.password?.message}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Confirm Password"
              type="password"
              {...register('confirmPassword')}
              error={!!errors.confirmPassword}
              helperText={errors.confirmPassword?.message}
              sx={{ mb: 3 }}
            />

            <Button
              type="submit"
              fullWidth
              variant="contained"
              size="large"
              disabled={isSubmitting}
              sx={{
                mb: 2,
                py: 1.5,
                borderRadius: 2,
                textTransform: 'none',
                fontSize: '1.1rem'
              }}
            >
              {isSubmitting ? 'Creating Account...' : 'Create Account'}
            </Button>

            <Box sx={{ textAlign: 'center', mt: 2 }}>
              <Link
                component={RouterLink}
                to="/login"
                variant="body1"
                sx={{
                  color: 'primary.main',
                  textDecoration: 'none',
                  '&:hover': { textDecoration: 'underline' }
                }}
              >
                Already have an account? Sign in
              </Link>
            </Box>
          </Box>
        </Paper>
      </Container>
    </Box>
  )
}

export default RegisterForm