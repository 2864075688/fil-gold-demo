import request from '@/utils/request'

// 查询提现记录列表
export function listWithdrawRecord(query) {
  return request({
    url: '/fil/withdrawRecord/list',
    method: 'get',
    params: query
  })
}
