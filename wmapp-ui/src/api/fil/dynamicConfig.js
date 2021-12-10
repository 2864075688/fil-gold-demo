import request from '@/utils/request'

// 查询动态比例配置列表
export function listDynamicConfig(query) {
  return request({
    url: '/fil/dynamicConfig/list',
    method: 'get',
    params: query
  })
}

// 查询动态比例配置详细
export function getDynamicConfig(id) {
  return request({
    url: '/fil/dynamicConfig/' + id,
    method: 'get'
  })
}

// 新增动态比例配置
export function addDynamicConfig(data) {
  return request({
    url: '/fil/dynamicConfig',
    method: 'post',
    data: data
  })
}

// 修改动态比例配置
export function updateDynamicConfig(data) {
  return request({
    url: '/fil/dynamicConfig',
    method: 'put',
    data: data
  })
}

// 删除动态比例配置
export function delDynamicConfig(id) {
  return request({
    url: '/fil/dynamicConfig/' + id,
    method: 'delete'
  })
}
