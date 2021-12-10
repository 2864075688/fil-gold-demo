import request from '@/utils/request'

// 查询投入记录列表
export function listInvestLog(query) {
  return request({
    url: '/fil/investLog/list',
    method: 'get',
    params: query
  })
}

// 查询投入记录详细
export function getInvestLog(id) {
  return request({
    url: '/fil/investLog/' + id,
    method: 'get'
  })
}

// 新增投入记录
export function addInvestLog(data) {
  return request({
    url: '/fil/investLog',
    method: 'post',
    data: data
  })
}

// 修改投入记录
export function updateInvestLog(data) {
  return request({
    url: '/fil/investLog',
    method: 'put',
    data: data
  })
}

// 删除投入记录
export function delInvestLog(id) {
  return request({
    url: '/fil/investLog/' + id,
    method: 'delete'
  })
}

// 导出投入记录
export function exportInvestLog(query) {
  return request({
    url: '/fil/investLog/export',
    method: 'get',
    params: query
  })
}