
function toBuy(userInfo){
	setTimeout(() => {
		if(!userInfo){
			return toBuy(userInfo);
		}
		if(!userInfo.status){
			uni.navigateTo({
				url: "/pages/invest/invest"
			})
		}
		return;
	}, 100);
	
}

export default toBuy;