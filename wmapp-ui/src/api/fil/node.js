import request from '@/utils/request'

// 查询节点列表
export function listNode(query) {
  return request({
    url: '/fil/node/list',
    method: 'get',
    params: query
  })
}

// 查询节点详细
export function getNode(id) {
  return request({
    url: '/fil/node/' + id,
    method: 'get'
  })
}

// 新增节点
export function addNode(data) {
  return request({
    url: '/fil/node',
    method: 'post',
    data: data
  })
}

// 修改节点
export function updateNode(data) {
  return request({
    url: '/fil/node',
    method: 'put',
    data: data
  })
}

// 删除节点
export function delNode(id) {
  return request({
    url: '/fil/node/' + id,
    method: 'delete'
  })
}
