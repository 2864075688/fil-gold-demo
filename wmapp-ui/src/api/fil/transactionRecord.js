import request from '@/utils/request'

// 查询交易记录列表
export function listTransactionRecord(query) {
  return request({
    url: '/fil/transactionRecord/list',
    method: 'get',
    params: query
  })
}

// 查询交易记录详细
export function getTransactionRecord(id) {
  return request({
    url: '/fil/transactionRecord/' + id,
    method: 'get'
  })
}
