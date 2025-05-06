import { Box, Typography, Divider, Grid, TextField, Button, MenuItem, Select, InputLabel, FormControl, Chip, Pagination } from '@mui/material'
import { useState, useEffect } from 'react'
import { getClaimAPI, ClaimAPI } from '../../apis/accountantAPI'
import { toast } from 'react-toastify'
import ClaimItem from './ClaimItem'

const Insurance = () => {
  const [claims, setClaims] = useState([{
    'claimId': 'e70d4160-2a72-4d3c-a9d5-cf4cf42fa597',
    'patientId': 'd75c5f67-8f82-4c45-8764-e4b3fa9e9518',
    'patientName': 'Nguyễn Văn A',
    'insuranceProvider': 'BaoViet Insurance',
    'policyNumber': 'BV123456789',
    'amount': 5000000,
    'status': 'PENDING',
    'createdAt': '2025-04-25T12:30:00Z'
  }
  ])
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate] = useState('')
  const [status, setStatus] = useState('APPROVED')
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)

  const handleFetchClaims = async () => {
    toast.promise(getClaimAPI(startDate, endDate, status, page), {
      pending: 'getting claim...'
    }).then((response) => {
      setClaims(response.claims)
      setTotalPages(response.totalPages)
    })
  }

  const handleReconcileClaim = async (claimId) => {
    toast.promise(ClaimAPI(claimId, { status: 'APPROVED', notes: 'Reconciled' }), {
      pending: 'loading...',
      success: 'Claim reconciled successfully'
    }).then((response) => {
      handleFetchClaims()
    })
  }

  const handleChangePage = (event, value) => {
    setPage(value)
    handleFetchClaims(page)
  }

  return (
    <>
      <Typography variant="h2" gutterBottom>
        Insurance Claims
      </Typography>
      <Divider/>
      <Box sx={{ maxWidth: 1000, mx: 'auto', p: 3 }}>
        {/* Filter Section */}
        <Box sx={{ mb: 3 }}>
          <Grid container spacing={2} alignItems="center">
            <Grid item xs={12} sm={4}>
              <TextField
                fullWidth
                label="Start Date"
                type="date"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item xs={12} sm={3}>
              <TextField
                fullWidth
                label="End Date"
                type="date"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                InputLabelProps={{ shrink: true }}
              />
            </Grid>
            <Grid item xs={12} sm={3}>
              <FormControl fullWidth>
                <InputLabel>Status</InputLabel>
                <Select
                  value={status}
                  onChange={(e) => setStatus(e.target.value)}
                  label="Status"
                >
                  <MenuItem value="">All</MenuItem>
                  <MenuItem value="PENDING">PENDING</MenuItem>
                  <MenuItem value="APPROVED">APPROVED</MenuItem>
                  <MenuItem value="REJECTED">REJECTED</MenuItem>
                </Select>
              </FormControl>
            </Grid>

            <Grid item xs={12} sm={3} sx={{ display: 'flex', justifyContent: 'center' }}>
              <Button
                variant="contained"
                color="primary"
                onClick={handleFetchClaims}
                sx={{ height: '100%', width: '100%' }}
              >
                Search
              </Button>
            </Grid>
          </Grid>
        </Box>

        <Divider sx={{ mb: 3 }} />

        {/* Insurance Claims List */}
        {claims.length > 0 ? (
          claims.map((claim) => (
            <ClaimItem
              key={claim.claimId}
              claim={claim}
              handleReconcileClaim={handleReconcileClaim}
            />
          ))
        ) : (
          <Typography variant="body1" color="text.secondary">
          No claims found.
          </Typography>
        )}

        {/* Pagination */}
        <Grid container justifyContent="center" sx={{ mt: 3 }}>
          <Pagination
            count={totalPages}
            page={page}
            onChange={handleChangePage}
            color="primary"
          />
        </Grid>
      </Box>
    </>

  )
}

export default Insurance
