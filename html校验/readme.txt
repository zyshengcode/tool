	通过改变select来控制div的显示
1.当不选择的时候，此时value为0，div,全部隐藏
2.为value为1时显示第一个div，第二第三个div隐藏

	
	表单input输入框的正则校验,，控制其输入值的范围

获取最后一个input输入框的值为$("#forRewardPoints").val()

对第一个input输入框的范围进行校验  range : [0,$("#forRewardPoints").val()] 指定其范围

 $("#inputForm").validate表示对表单校验

注意html中的id，name等于js中的逻辑相对应



