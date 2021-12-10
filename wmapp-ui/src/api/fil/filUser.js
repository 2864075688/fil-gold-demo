import request from '@/utils/request'

// 查询用户列表
export function listFilUser(query) {
  return request({
    url: '/fil/filUser/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getFilUser(id) {
  return request({
    url: '/fil/filUser/' + id,
    method: 'get'
  })
}

// 新增用户
export function addFilUser(data) {
  return request({
    url: '/fil/filUser',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateFilUser(data) {
  return request({
    url: '/fil/filUser',
    method: 'put',
    data: data
  })
}
