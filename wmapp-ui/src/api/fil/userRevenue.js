import request from '@/utils/request'

// 查询用户收益列表
export function listUserRevenue(query) {
  return request({
    url: '/fil/userRevenue/list',
    method: 'get',
    params: query
  })
}

// 查询用户收益详细
export function getUserRevenue(id) {
  return request({
    url: '/fil/userRevenue/' + id,
    method: 'get'
  })
}
