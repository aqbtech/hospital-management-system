import {
  Dashboard as DashboardIcon,
  People as PeopleIcon,
  LocalHospital as HospitalIcon,
  CalendarMonth as CalendarIcon,
  Settings as SettingsIcon,
  Person as PersonIcon,
  Assignment as AssignmentIcon,
  MedicalServices as MedicalServicesIcon,
  Payment as PaymentIcon,
  Receipt as ReceiptIcon
} from '@mui/icons-material'
import AccountCircleIcon from '@mui/icons-material/AccountCircle'
import MedicationIcon from '@mui/icons-material/Medication'

export const BASE_URL = 'https://localhost:8080'

export const SIDEBAR_DOCTOR = [
  { text: 'My Profile', icon: <AccountCircleIcon/>, tab: 'myProfile' },
  { text: 'Dashboard', icon: <DashboardIcon/>, tab: 'dashboard' },
  { text: 'My Patients', icon: <PeopleIcon/>, tab: 'myPatients' },
  { text: 'Prescriptions', icon: <MedicationIcon/>, tab: 'prescriptions' }
]

export const SIDEBAR_PATIENT = [
  { text: 'My Profile', icon: <AccountCircleIcon/>, tab: 'profile' },
  { text: 'Appointment', icon: <CalendarIcon/>, tab: 'Appointment' },
  { text: 'Medical Record', icon: <MedicalServicesIcon/>, tab: 'dashboard' }
]

export const SIDEBAR_ACCOUNTANT = [
  { text: 'My Profile', icon: <AccountCircleIcon/>, tab: 'profile' },
  { text: 'Invoice', icon: <ReceiptIcon/>, tab: 'invoice' },
  { text: 'Insurance', icon: <MedicalServicesIcon/>, tab: 'insurance' }
]

export const SIDEBAR_ADMIN = [

]

export const SIDEBAR_NURSE = [

]

export const USER_TYPES = ['PATIENT', 'DOCTOR', 'NURSE', 'ADMIN', 'ACCOUNTANT']


