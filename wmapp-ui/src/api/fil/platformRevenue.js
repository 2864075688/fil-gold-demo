import request from '@/utils/request'

// 查询平台收入列表
export function listPlatformRevenue(query) {
  return request({
    url: '/fil/platformRevenue/list',
    method: 'get',
    params: query
  })
}

// 查询平台收入详细
export function getPlatformRevenue(id) {
  return request({
    url: '/fil/platformRevenue/' + id,
    method: 'get'
  })
}
