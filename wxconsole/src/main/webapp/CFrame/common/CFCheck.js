var CFCheck = CFCheck || {};
(function() {		
	Ext.apply(CFCheck,{
		
		/**
		 * 字符串非空检查
		 * 
		 */
		checkNull:function(str){
			  if(str==null) return true;
			  if(str.length==0) return true;
			  return false;
		},
		
		/**
		 * 检查电话号码
		 * FormateValidate:['0'..'9','-']---for phone number
		 * @param str
		 * @returns {Boolean}
		 */
		checkPhoneNum:function(str){
		 if(str==null) return false;
		  if(str.length==0) return false;
		  	var re=/[^0-9\-\+]/;
		 	if (re.test(str)) {
				return true;
			}
			return false; 
		},
		
		/**
		 * 检查数字
		 * FormateValidate:['0'..'9']---for numbers
		 * @param str
		 * @returns
		 */
		checkNum:function(str){
		 if(str==null) return false;
		  if(str.length==0) return false;
		  	var re=/[^0-9]/;
		 	if (re.test(str)) {
				return true;
			}
			return false; 
		},
		
		/**
		 * 检查数字
		 * FormateValidate:['0'..'9']---for numbers
		 * @param str
		 * @returns
		 */
		checkNum:function(str){
		 if(str==null) return false;
		  if(str.length==0) return false;
		  	var re=/^[0-9]+$/;
		 	if (re.test(str)) {
				return true;
			}
			return false; 
		},
		/**
		 * 检查字母
		 * FormateValidate:26个英文字母
		 * @param str-校验字符串
		 * @param caseFlag-大小写判断，LOWER：小写；UPPER：大写；NOCASE：忽略大小写
		 * @returns
		 */
		checkAlph:function(str,caseFlag){
			if(str==null) return false;
			if(str.length==0) return false;
			
			if (caseFlag == null) return false;
			if (caseFlag.length == 0) return false;
		  	
		  	if (caseFlag == "LOWER") {
		  		var re=/^[a-z]+$/;
			 	if (re.test(str)) {
					return true;
				} 
		  	} else if (caseFlag == "UPPER") {
		  		var re=/^[A-Z]+$/;
			 	if (re.test(str)) {
					return true;
				} 
		  	} else if (caseFlag == "NOCASE") {
		  		var re=/^[A-Za-z]+$/;
			 	if (re.test(str)) {
					return true;
				} 
		  	}

			return false; 
		},
		/**
		 * 检查字节长度，汉字2位
		 * @param str
		 * @param length
		 * @returns {Boolean}
		 */
		checkLength:function(str,length){
			  var endvalue=0;
			  var sourcestr=new String(str);
			  var tempstr;
			  for (var strposition = 0; strposition < sourcestr.length; strposition ++) {
			    tempstr=sourcestr.charAt(strposition);
			    if (tempstr.charCodeAt(0)>255 || tempstr.charCodeAt(0)<0) {
			      //endvalue=endvalue+3;
			      endvalue=endvalue+2;
			    } else {
			      endvalue=endvalue+1;
			    }
			  }
			  if(endvalue>length){
			  	return true;
			  }
			  return false;
		},
		/**
		 * 检查邮件地址格式
		 * @param email
		 * @returns {Boolean}
		 */
		checkEmail:function(email){	
			 if(email==null) return false;
			  if(email.length==0) return false;
			var regularExpression = /^[^\s]+@[^\s]+\.[^\s]{2,3}$/;
			if (regularExpression.test(email)) {
				return false;
			}else{
				return true;
			}
		},
			
		/**
		 * 检查国家地区代码是否符合要求
		 * @param str
		 * @param length
		 * @returns {Boolean}
		 */
		checkCtyCode:function(str){
			if (str.length == 3) {//国家代码判断
				if (CFCheck.checkAlph(str,"UPPER")) {
					return true;
				} else {
					return false;
				}
			} else if (str.length == 6) {//地区代码判断
				if (CFCheck.checkNum(str)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		},
		//银行卡号校验

	    //Description:  银行卡号Luhm校验

	    //Luhm校验规则：16位银行卡号（19位通用）:
	    
	    // 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
	    // 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
	    // 3.将加法和加上校验位能被 10 整除。
	    checkBankCardNo:function(bankno){
			if (bankno.length < 16 || bankno.length > 19) {
				return false;
			}
			var num = /^\d*$/;  //全数字
			if (!num.exec(bankno)) {
				//银行卡号必须全为数字
				return false;
			}
			//开头6位
			var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
			if (strBin.indexOf(bankno.substring(0, 2))== -1) {
				//银行卡号开头6位不符合规范
				return false;
			}
	        var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）
	        var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
	        var newArr=new Array();
	        for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
	            newArr.push(first15Num.substr(i,1));
	        }
	        var arrJiShu=new Array();  //奇数位*2的积 <9
	        var arrJiShu2=new Array(); //奇数位*2的积 >9
	        var arrOuShu=new Array();  //偶数位数组
	        for(var j=0;j<newArr.length;j++){
	            if((j+1)%2==1){//奇数位
	                if(parseInt(newArr[j])*2<9)
	                arrJiShu.push(parseInt(newArr[j])*2);
	                else
	                arrJiShu2.push(parseInt(newArr[j])*2);
	            }else //偶数位
	            	arrOuShu.push(newArr[j]);
	        }
	        var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
	        var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
	        for(var h=0;h<arrJiShu2.length;h++){
	            jishu_child1.push(parseInt(arrJiShu2[h])%10);
	            jishu_child2.push(parseInt(arrJiShu2[h])/10);
	        }        
	        var sumJiShu=0; //奇数位*2 < 9 的数组之和
	        var sumOuShu=0; //偶数位数组之和
	        var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
	        var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
	        var sumTotal=0;
	        for(var m=0;m<arrJiShu.length;m++){
	            sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
	        }
	        for(var n=0;n<arrOuShu.length;n++){
	            sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
	        }
	        for(var p=0;p<jishu_child1.length;p++){
	            sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
	            sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
	        }      
	        //计算总和
	        sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
	        //计算Luhm值
	        var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
	        var luhm= 10-k;
	        if(lastNum==luhm){
	        	return true;
	        }else{
	        	return false;
	        }  
	    }
    });
}());