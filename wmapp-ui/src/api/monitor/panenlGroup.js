import request from '@/utils/request';
// 查询首页数据
export function getIndexData() {
  return request({
    url: '/fil/index',
    method: 'get'
  })
}

