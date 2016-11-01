# weixin
**微信开发框架--java版**

这个项目主要是开发微信jssdk的接口，如果需要开发微信其他接口，需要在 wxserver扩展。

##工程结构

整个项目有4个工程


* wxserver
	* 负责和外部微信服务通信
* wxweb
	* 前端，微信浏览器显示，比如说点击公众号下面的菜单，显示的内容
* wxconsole
	* 配置公众号菜单。内部实现了调用微信API[ https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN]( https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN)创建菜单到腾讯的服务，手机客户端显示出菜单的功能。客户端是5分钟发一次请求，开发阶段可以取消关注再重新关注。
* wxpservice
	* 处理web页面发出的请求，连接mybatis


##使用
* 微信公众号
* 用微信公众号里的token、appid、encodingAESKey、appsecret代替wxserver下weixin.properties中对应的属性值