import { authorizedAxios } from '../utils/authorizedAxios'

//bonus
export const getAccountantProfileAPI = async () => {
  const response = authorizedAxios.get('/accountants/profile')
  return response.data
}

export const updateAccountantProfileAPI = async () => {
  const response = authorizedAxios.put('/accountants/profile')
  return response.data
}

export const getListInvoicesAPI = async (startDate, endDate, status) => {
  const response = authorizedAxios.get(`/accountants/invoices?startDate=${startDate}&endDate=${endDate}&status=${status}`)
  return response.data
}

export const exportToPdfAPI = async (invoiceId) => {
  const response = authorizedAxios.get(`/accountants/invoices/${invoiceId}/export`)
  return response.data
}

export const ClaimAPI = async (claimId, data) => {
  const response = authorizedAxios.post(`/accountants/insurance-claims/${claimId}/reconcile`, data)
  return response.data
}

export const getClaimAPI = async (startDate, endDate, status) => {
  const response = authorizedAxios.get(`accountants/insurance-claims?startDate=${startDate}&endDate=${endDate}&status=${status}`)
  return response.data
}
