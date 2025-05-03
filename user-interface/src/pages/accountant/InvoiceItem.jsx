import { Card, Grid, CardContent, Typography, Chip, Divider, Button } from '@mui/material'
import { exportToPdfAPI } from '../../apis/accountantAPI'

const formatCurrency = (amount) => amount?.toLocaleString() + ' VND'

const InvoiceItem = ({ invoiceData }) => {
  if (!invoiceData) return null

  const { invoiceId, patientName, status, createdAt, breakdown, totalAmount } = invoiceData

  return (
    <Card key={invoiceId} elevation={2} sx={{ mb: 2 }}>
      <CardContent>
        <Grid container spacing={2}>
          {[
            { label: 'Invoice ID', value: invoiceId },
            { label: 'Patient Name', value: patientName },
            {
              label: 'Status',
              value: (
                <Chip
                  size="small"
                  label={status}
                  color={status === 'PAID' ? 'success' : 'warning'}
                  variant="outlined"
                />
              )
            },
            { label: 'Created At', value: new Date(createdAt).toLocaleString() }
          ].map((item, index) => (
            <Grid item xs={12} sm={6} key={index}>
              <Typography variant="subtitle1" color="primary" fontWeight={600}>
                {item.label}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {item.value}
              </Typography>
            </Grid>
          ))}
        </Grid>

        <Divider sx={{ my: 2 }} />

        <Typography variant="subtitle1" color="primary" fontWeight={600} gutterBottom>
          Breakdown
        </Typography>

        <Grid container spacing={2}>
          {[
            { label: 'Service Fee', value: formatCurrency(breakdown?.serviceFee) },
            { label: 'Medicine Fee', value: formatCurrency(breakdown?.medicineFee) },
            {
              label: 'Insurance Coverage',
              value: formatCurrency(breakdown?.insuranceCoverage ?? 0)
            }
          ].map((item, index) => (
            <Grid item xs={12} sm={6} key={index}>
              <Typography variant="body2" color="text.secondary">
                {item.label}: {item.value}
              </Typography>
            </Grid>
          ))}
        </Grid>

        <Divider sx={{ my: 2 }} />

        <Grid container justifyContent="space-between" alignItems="center">
          <Typography variant="h6" color="primary" fontWeight={700}>
            Total: {formatCurrency(totalAmount)}
          </Typography>
          <Button
            variant="contained"
            color="primary"
            size="small"
            onClick={() => exportToPdfAPI(invoiceId)}
          >
            Export PDF
          </Button>
        </Grid>
      </CardContent>
    </Card>
  )
}

export default InvoiceItem
