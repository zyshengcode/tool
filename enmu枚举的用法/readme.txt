调用链：
MainUtils---> commonutils--->ErrorCode/Successcode


返回结果：

{msg=查询失*……*败了, code=20001}------>可用于数据库查询失败，
将信息利用@ResponseBody返回给调用者，可以自定义错误码和错误消息


{msg=查询成功, obj=BeanProperties{age='12', sex='female'}, returncode=000001}

--->可用于数据库查询成功，
将查询的个人信息利用@ResponseBody返回给调用者，可以自定义正确查询的消息code