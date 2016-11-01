

Ext.onReady(function() {
        Ext.QuickTips.init(); 
        var login = Ext.widget('form',{
            id : 'login',
            name : 'login',
            renderTo:'render-div',
            region:'center',
            baseCls : 'x-plain',
            bodyStyle : 'padding:5 5 5 10',
            width : 300,
            height : 120,
            border : false,
            defaults : {
                    autoFitErrors : false,
                    labelSeparator : ':',
                    labelWidth : 50,
                    labelAlign : 'left',
                    width : 280,        
                    allowBlank : false,
                    msgTarget : 'qtip'
            },
            defaultType : 'textfield',// 默认字段类型
            items : [{                        
                fieldLabel : "用户名",
                fieldCls : 'loginKeyCss',
                disabled : this.mydisabled,
                labelStyle:'color:#fff',
                name : "username",
                id:"username",
                blankText : "请输入用户名",
                margin:'0 0 12 0'

            },{
                fieldLabel : "密&nbsp;&nbsp;&nbsp;码",                
                fieldCls : 'verifyKeyCss',
                labelStyle:"color:#fff",
                disabled : this.mydisabled,
                name : "password1",
                id:"password1",
                blankText : "请输入密码",
                inputType : "password"
            },{
                title : "Center Region",                
                fieldCls : 'verifyKeyCss',
                disabled : this.mydisabled,
                name : "password",
                id:"password",
                hidden:true
                    
            }],
            buttonAlign : 'center',
            buttons : [{                        
                text : '登录',
                handler : submit_login,
                margin:"0 15 25 0"
            }, {
                text : '刷新',
                handler : function() {
                        Ext.getCmp('login').form.reset();
                }// 重置表单
            }]
        }); 
//        login.render('render-div');
//        var win = new Ext.Window({
//                id : 'win',
//                title : '<div align="center"><font size=2>管理系统--用户登录</font></div>',
//                layout : 'fit',
//                width : 300,
//                height : 160,
//                bodyStyle : 'padding:5px;',
//                maximizable : false,
//                closeAction : 'destroy',
//                closable : false,
//                resizable:false,
//                collapsible : true,
//                modal:true,
//                items : login
//        });        
        //win.show();
        function submit_login() {
        	Ext.getCmp("password").setValue(do_encrypt(Ext.getCmp("username").value,Ext.getCmp("password1").value));
                if (login.form.isValid()) {                          
                	login.form.submit({
                                url : App.basePath + "login",
                                waitTitle : '提示',
                                method : 'POST',                                
                                waitMsg : '正在登录验证,请稍候...',
                                success : function(form, action) {
                                        var loginResult = action.result.success;
                                        if (loginResult == false) {
                                                Ext.MessageBox.alert('提示', action.result.msg);
                                        } else if (loginResult == true) {
                                                window.location.href = App.basePath + "main";
                                        }
                                },
                                failure : function(form, action) {
                                	    Ext.MessageBox.alert('提示', action.result.msg);
                                        Ext.getCmp('login').form.reset();
                                }
                        });
                }
        }
        var map = new Ext.util.KeyMap('login', {
            key: 13, // or Ext.EventObject.ENTER
            fn: submit_login,
            scope: this
        });        
});