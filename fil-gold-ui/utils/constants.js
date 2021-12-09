let isDev = process.env.NODE_ENV === 'development';
const constants = {
	providerUrl: isDev ? "https://http-testnet.hecochain.com" : "https://http-mainnet-node.huobichain.com",
	contract: isDev ? "0x******" : "0x******",
	isDev: isDev,
	baseUrl: isDev ? "http://127.0.0.1" : "http://127.0.0.1",
	chainID: isDev ? 256 : 128
}

export default constants;