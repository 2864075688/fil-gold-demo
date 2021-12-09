import constants from "../utils/constants.js";

const Method = {
	POST: "POST",
	GET: "GET",
	DELETE: "DELETE",
	PUT: "PUT",
}

async function request(path, method, data){
	return new Promise((resolve, reject) => {
		uni.request({
			url: constants.baseUrl + path,
			method: method,
			data: data,
			success: (res) => {
				resolve(res.data);
			},
			fail: (e) => {
				reject(e);
			}
		})
	})
}


const API = {
	/**
	 * 新建或查新用户信息
	 * @param {String} userCode 用户地址
	 * @param {String} parentCode 父级用户地址
	 */
	async createAndGetUserInfo(userCode, parentCode){
		return await request("/api/user", Method.POST, {userCode, parentCode});
	},
	/**
	 * 获取节点列表
	 */
	async getNodeList(){
		return await request("/api/config/node", Method.GET);
	},
	
	/**
	 * 申购节点
	 * @param {String} nodeId 节点id
	 * @param {String} userCode 用户地址
	 * @param {String} transactionHash 交易hash
	 */
	async buyNode(nodeId, userCode, transactionHash){
		return await request("/api/apply/node", Method.POST, {nodeId, userCode, transactionHash});
	},
	
	/**
	 * 根据用户地址获取用户信息
	 * @param {String} userCode
	 */
	async getUserInfo(userCode){
		return await request("/api/user", Method.GET, {userCode});
	},
	/**
	 * 获取通知公告
	 */
	async getNotice(){
		return await request("/api/config/notice", Method.GET);
	},
	
	/**
	 * 用户投入
	 * @param {String} userCode 用户地址
	 * @param {String} transactionHash 交易hash
	 */
	async inputInvest(userCode, transactionHash){
		return await request("/api/invest", Method.POST, {userCode, transactionHash});
	},
	/**
	 * 提现
	 * @param {String} userCode 用户地址
	 * @param {String} amount 金额
	 */
	async withdrawal(userCode, amount){
		return await request("/api/withdrawal", Method.POST, {userCode, amount})
	},
	
	/**
	 * 授权
	 * @param {String} transactionHash 交易hash
	 */
	async authorization(transactionHash){
		return await request("/api/user", Method.PUT, {transactionHash});
	}
}
export default API;