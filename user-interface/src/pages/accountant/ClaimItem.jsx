import React from 'react'
import { Box, Typography, Chip, Button } from '@mui/material'

const ClaimItem = ({ claim, handleReconcileClaim }) => {
  return (
    <Box key={claim.claimId} sx={{ mb: 3 }}>
      <Box
        sx={{
          border: '1px solid #e0e0e0',
          borderRadius: 2,
          p: 2,
          boxShadow: 1,
          display: 'flex',
          flexDirection: 'column',
          gap: 1.5
        }}
      >
        <Typography variant="h6" fontWeight="bold">{claim.patientName}</Typography>

        <Box display="flex" flexDirection="column" gap={0.5}>
          <Typography variant="body2" color="text.secondary">
            Claim ID: <Box component="span" fontWeight="medium" color="text.primary">{claim.claimId}</Box>
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Insurance: <Box component="span" fontWeight="medium" color="text.primary">{claim.insuranceProvider}</Box>
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Policy #: <Box component="span" fontWeight="medium" color="text.primary">{claim.policyNumber}</Box>
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Amount: <Box component="span" fontWeight="medium" color="text.primary">{claim.amount.toLocaleString()} VND</Box>
          </Typography>
          <Box display="flex" alignItems="center" gap={1}>
            <Typography variant="body2" color="text.secondary">Status:</Typography>
            <Chip
              label={claim.status}
              size="small"
              color={
                claim.status === 'APPROVED'
                  ? 'success'
                  : claim.status === 'REJECTED'
                    ? 'error'
                    : 'default'
              }
            />
          </Box>
          <Typography variant="body2" color="text.secondary">
            Created: <Box component="span" fontWeight="medium" color="text.primary">{new Date(claim.createdAt).toLocaleString()}</Box>
          </Typography>
        </Box>

        <Box sx={{ textAlign: 'right', mt: 2 }}>
          <Button
            variant="contained"
            color="secondary"
            disabled={claim.status !== 'PENDING'}
            size="small"
            onClick={() => handleReconcileClaim(claim.claimId)}
          >
              Reconcile
          </Button>
        </Box>

      </Box>
    </Box>
  )
}

export default ClaimItem
