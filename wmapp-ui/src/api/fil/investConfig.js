import request from '@/utils/request'

// 查询复投配置列表
export function listInvestConfig(query) {
  return request({
    url: '/fil/investConfig/list',
    method: 'get',
    params: query
  })
}

// 查询复投配置详细
export function getInvestConfig(id) {
  return request({
    url: '/fil/investConfig/' + id,
    method: 'get'
  })
}

//校验数据是否重复存在
export function checkInvestNum(data) {
  return request({
    url: '/fil/check',
    method: 'put',
    data: data
  })
}

// 新增复投配置
export function addInvestConfig(data) {
  return request({
    url: '/fil/investConfig',
    method: 'post',
    data: data
  })
}

// 修改复投配置
export function updateInvestConfig(data) {
  return request({
    url: '/fil/investConfig',
    method: 'put',
    data: data
  })
}

// 删除复投配置
export function delInvestConfig(id) {
  return request({
    url: '/fil/investConfig/' + id,
    method: 'delete'
  })
}
