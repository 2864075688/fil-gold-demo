const Base64 = {
	encode(str){
		return window.btoa(str);
	},
	
	decode(str){
		return window.atob(str);
	}
}

export default Base64;