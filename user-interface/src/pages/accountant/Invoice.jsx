import { Box, Typography, Divider, Grid, Card, CardContent, Chip, Button, TextField, MenuItem, Select, InputLabel, FormControl, Pagination } from '@mui/material'
import { useState } from 'react'
import { getListInvoicesAPI, exportToPdfAPI } from '../../apis/accountantAPI'
import { toast } from 'react-toastify'
import InvoiceItem from './InvoiceItem'

const Invoice = () => {
  const [listInvoices, setListInvoices] = useState([{
    invoiceId: 'b1e7c3d2-d3ad-4f36-a28a-cb760440e8e1',
    patientName: 'Nguyễn Văn B',
    totalAmount: 150000,
    status: 'PAID',
    createdAt: '2025-04-25T14:30:00Z',
    breakdown: {
      serviceFee: 100000,
      medicineFee: 30000,
      insuranceCoverage: 20000
    }
  }])
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate] = useState('')
  const [status, setStatus] = useState('PAID')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)

  const getListInvoices = async (page) => {
    toast.promise(getListInvoicesAPI(startDate, endDate, status, page), {
      pending: 'Fetching invoices...'
    }).then((res) => {
      setListInvoices(res.invoices)
      setTotalPages(res.totalPages)
    })
  }

  const handleChangePage = (event, value) => {
    setPage(value)
    getListInvoices(page)
  }

  const handleFetchInvoices = () => {
    if (!startDate || !endDate || !status) {
      toast.error('Please select start date, end date, and status before fetching invoices.')
      return
    }
    getListInvoices(page)
  }

  return (
    <>
      <Typography variant="h2" gutterBottom>
        Invoice Details
      </Typography>
      <Divider/>
      <Box sx={{ maxWidth: 1000, mx: 'auto', p: 3 }}>

        {/* Filter Section */}
        <Box sx={{ mb: 3 }}>
          <Grid container spacing={2} alignItems="center">
            <Grid item xs={12} sm={3}>
              <TextField
                label="Start Date"
                type="date"
                fullWidth
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                InputLabelProps={{
                  shrink: true
                }}
              />
            </Grid>

            <Grid item xs={12} sm={3}>
              <TextField
                label="End Date"
                type="date"
                fullWidth
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                InputLabelProps={{
                  shrink: true
                }}
              />
            </Grid>

            <Grid item xs={12} sm={3}>
              <FormControl fullWidth>
                <InputLabel>Status</InputLabel>
                <Select
                  value={status}
                  label="Status"
                  onChange={(e) => setStatus(e.target.value)}
                >
                  <MenuItem value="">
                    <em>All</em>
                  </MenuItem>
                  <MenuItem value="PENDING">PENDING</MenuItem>
                  <MenuItem value="PAID">PAID</MenuItem>
                  <MenuItem value="CANCELLED">CANCELLED</MenuItem>
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={3} sx={{ display: 'flex', justifyContent: 'center' }}>
              <Button
                variant="contained"
                color="primary"
                onClick={handleFetchInvoices}
                sx={{ height: '100%', width: '100%' }}
              >
                Search
              </Button>
            </Grid>
          </Grid>
        </Box>

        <Divider sx={{ mb: 3 }} />

        {/* Invoice List */}
        {listInvoices.length === 0 ? (
          <Typography variant="h6" color="text.secondary">
          No invoices found.
          </Typography>
        ) : (
          listInvoices.map((invoiceData) => (
            <InvoiceItem key={invoiceData.invoiceId} invoiceData={invoiceData} />
          ))
        )}

        {/* Pagination */}
        <Box sx={{ mt: 3, display: 'flex', justifyContent: 'center' }}>
          <Pagination
            count={totalPages}
            page={page}
            onChange={handleChangePage}
            color="primary"
          />
        </Box>
      </Box>
    </>

  )
}

export default Invoice
