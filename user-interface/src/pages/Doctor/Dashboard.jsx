import { Card, CardContent, Typography, Box, Grid, Divider, TextField, Button } from '@mui/material'
import ScheduleItem from './ScheduleItem'
import { useEffect, useState } from 'react'
import { toast } from 'react-toastify'
import { getListScheduleAPI } from '../../apis/doctorAPI'
import { useSelector } from 'react-redux'
import { selectUser } from '../../redux/slice/userSlice'

const formattedDate = (date) => {
  return date ? new Date(date).toLocaleDateString('vi-VN') : ''
}

const Dashboard = () => {
  const [listSchedule, setListSchedule] = useState([
    {
      date: '2025-04-27',
      slots: [
        {
          startTime: '23:36',
          endTime: '14:45',
          status: 'AVAILABLE'
        },
        {
          startTime: '23:36',
          endTime: '14:45',
          status: 'AVAILABLE'
        },
        {
          startTime: '23:36',
          endTime: '14:45',
          status: 'AVAILABLE'
        },
        {
          startTime: '23:36',
          endTime: '14:45',
          status: 'AVAILABLE'
        }, {
          startTime: '23:36',
          endTime: '14:45',
          status: 'AVAILABLE'
        }
      ]
    },
    {
      date: '2025-04-27',
      slots: [
        {
          startTime: '23:36',
          endTime: '14:45',
          status: 'AVAILABLE'
        }
      ]
    }
  ])
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate] = useState('')
  const CurrentUser = useSelector(selectUser)

  const handleFetchSchedule = async () => {
    if (!startDate || !endDate) {
      toast.error('Please select both start and end dates!')
      return
    }
    await toast.promise(
      getListScheduleAPI(CurrentUser?.userId, startDate, endDate),
      {
        pending: 'Fetching schedule...'
      }
    ).then((res) => {
      setListSchedule(res)
    })

  }

  return (
    <Box sx={{ p: 2 }}>
      <Typography variant="h4" gutterBottom>
        Doctor's Dashboard
      </Typography>

      <Divider sx={{ mb: 3 }} />

      {/* Chọn khoảng ngày */}
      <Grid container spacing={2} sx={{ mb: 3 }}>
        <Grid item xs={12} sm={5}>
          <TextField
            label="Start Date"
            type="date"
            fullWidth
            InputLabelProps={{ shrink: true }}
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={5}>
          <TextField
            label="End Date"
            type="date"
            fullWidth
            InputLabelProps={{ shrink: true }}
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={2} sx={{ display: 'flex', alignItems: 'center' }}>
          <Button
            variant="contained"
            color="primary"
            fullWidth
            onClick={handleFetchSchedule}
          >
            Search
          </Button>
        </Grid>
      </Grid>

      {/* Hiển thị lịch */}
      {listSchedule.length === 0 ? (
        <Typography variant="body1" color="text.secondary">
          No schedule. Please select a date range.
        </Typography>
      ) : (
        <Grid container spacing={2}>
          {listSchedule.map((day, index) => (
            <Grid item xs={12} key={index}>
              <Card sx={{ borderRadius: 2 }}>
                <CardContent>
                  <Typography variant="h6" sx={{ mb: 2 }}>
                    {formattedDate(day.date)}
                  </Typography>

                  <Grid container spacing={2}>
                    {day.slots.map((slot, idx) => (
                      <Grid item key={idx} xs={12} sm={6} md={4} lg={3}>
                        <ScheduleItem slot={slot} date={day.date} />
                      </Grid>
                    ))}
                  </Grid>
                </CardContent>

              </Card>
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  )
}

export default Dashboard
