# weixin
**微信开发框架--java版**

##工程结构

整个项目有4个工程


* wxserver
	* 用来连接腾讯服务，校验微信公众号是否可用
* wxweb
	* 前端，微信浏览器显示，比如说点击公众号下面的菜单，显示的内容
* wxconsole
	* 配置公众号菜单。只需要调用微信API[ https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN]( https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN)即可创建菜单到腾讯的服务，手机客户端会自动显示出菜单。客户端是5分钟发一次请求，开发阶段可以取消关注再重新关注。
* wxpservice
	* XX