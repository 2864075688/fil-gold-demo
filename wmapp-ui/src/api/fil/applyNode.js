import request from '@/utils/request'

// 查询节点申购订单列表
export function listApplyNode(query) {
  return request({
    url: '/fil/applyNode/list',
    method: 'get',
    params: query
  })
}

// 查询节点申购订单详细
export function getApplyNode(id) {
  return request({
    url: '/fil/applyNode/' + id,
    method: 'get'
  })
}
