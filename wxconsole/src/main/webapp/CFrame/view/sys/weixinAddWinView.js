Ext.define('CFrame.view.sys.weixinAddWinView', {
    extend: 'Ext.Window',
    alias: 'widget.weixinAddWinView',
    //singleton: true,
    id:'weixinAddWinView',
    operation:'add',  //view-只读；mod-修改；add-新增；del-删除
    type: '',
    oldmodel:{},
	newmodel:{},
    ret:'',
    submiturl:'',
    keyfields:{},
    initComponent:function(){
    	var me = this;
    	var dict023= CFControl.getDictData('023',Ext.create('CFrame.store.commonDictStore'));
    	var dict024= CFControl.getDictData('024',Ext.create('CFrame.store.commonDictStore'));
    	var dict025= CFControl.getDictData('025',Ext.create('CFrame.store.commonDictStore'));
    	var menuStore=Ext.create('CFrame.store.menuButtonStore');
    	var strRegex = '^((https|http)://){1}(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-z_!~*\'()-]+.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$';
    	var regexString='^[0-9]*$';
    	var re=new RegExp(strRegex); 
    	var regex=new RegExp(regexString); 
    	Ext.applyIf(this,{
    		layout : 'fit',
			width : 300,
			layout: 'fit',
			resizable : true,
			draggable : true,
			closeAction : 'destroy',
			modal : true,
			title : '增加菜单',
			maximizable : false,
			border : false,
			animCollapse : true,
			constrain : true,
			autoScroll: true,
			modal:true,
			items: [{
				xtype: 'form',
				layout : {
					columns : 1,
					type : 'table'
				},
			    id:'addForm',
			    bodyPadding:5,
			    items: [{
					xtype : 'textfield',
					id : 'tf_MENU_NAME',
					name : 'MENU_NAME',
					fieldLabel : '菜单名称',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					allowBlank : false,
					blankText : "名称不能为空"
				},{
					xtype : 'combo',
					id : 'cb_MENU_LEVEL',
					name : 'MENU_LEVEL',
					fieldLabel : '菜单级别',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					store: dict024, 
                    displayField : 'DICT_NAME',   
                    valueField : 'DICT_VALUE',
                    queryMode:'local',
                    allowBlank : false,
				    blankText : "菜单级别不能为空",
					editable: false,
					listeners:{ 
						   select:function(combo,record,opts) {  
							var key=Ext.getCmp("tf_MENU_KEY");
						var url=Ext.getCmp("tf_URL");
						var media_id=Ext.getCmp("tf_MEDIA_ID");
						   var has_sub_button=Ext.getCmp("cb_has_sub_button");
						   var menu=Ext.getCmp("cb_MENU");
				    	   var type=Ext.getCmp("cb_TYPE");
						      if(record[0].get("DICT_VALUE")==0){
							  has_sub_button.show();
							  has_sub_button.allowBlank=false;
							  key.hide();
							  url.hide();
							  media_id.hide();
							  menu.hide();
					    	  type.hide();
					    	  type.allowBlank=true;
					    	  url.allowBlank=true;
					    	  media_id.allowBlank=true;
					    	  key.allowBlank=true;
					    	  menu.allowBlank=true;
					    	  menu.setValue("");
					    	  type.setValue("");
					    	  key.setValue("");
							  url.setValue("");
							  media_id.setValue("");
						       }else{
						    	   menu.show();
						    	   type.show();
						    	   has_sub_button.hide();
						    	   menu.allowBlank=false;
						    	   type.allowBlank=false;
						    	   has_sub_button.allowBlank=true;
						    	   has_sub_button.setValue("");
						       }
						      
						   } 
					}
				},
				{
					xtype : 'combo',
					id : 'cb_has_sub_button',
					name : 'has_sub_button',
					fieldLabel : '有无二级菜单',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					store: dict025, 
                    displayField : 'DICT_NAME',   
                    valueField : 'DICT_VALUE',
                    queryMode:'local',
                    allowBlank : false,
				    blankText : "该项不能为空",
					editable: false,
					listeners:{ 
						   select:function(combo,record,opts) { 
						var key=Ext.getCmp("tf_MENU_KEY");
						var url=Ext.getCmp("tf_URL");
						var media_id=Ext.getCmp("tf_MEDIA_ID");
							 var type=Ext.getCmp("cb_TYPE");
						      if(record[0].get("DICT_VALUE")==1){
							  type.show();
							  type.allowBlank=false;
						       }else{
						      type.hide();
						      url.hide();
					    	  media_id.hide();
					    	  key.hide();
					    	  type.allowBlank=true;
					    	  url.allowBlank=true;
					    	  media_id.allowBlank=true;
					    	  key.allowBlank=true;
					    	  type.setValue("");
					    	   url.setValue("");
					    	   media_id.setValue("");
					    	   key.setValue("");
						       }
						      
						   },
						render:function(cb){
							cb.hide();
						}
					}
				},{
					xtype : 'combo',
					id : 'cb_MENU',
					name : 'MENU',
					fieldLabel : '所属一级菜单',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					store: menuStore, 
                    displayField : 'menu_name',   
                    valueField : 'menu_id',
                    queryMode:'remote',
                    allowBlank : false,
				    blankText : "菜单级别不能为空",
					editable: false,
					listeners:{ 
						render:function(cb){
							cb.hide();
						}
						}
				},{
					xtype : 'combo',
					id : 'cb_TYPE',
					name : 'TYPE',
					fieldLabel : '菜单类型',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					store: dict023, 
                    displayField : 'DICT_NAME',   
                    valueField : 'DICT_VALUE',
                    queryMode:'local',
                    allowBlank : false,
				    blankText : "菜单类型不能为空",
					editable: false,
					listeners:{ 
						   select:function(combo,record,opts) {  
						var key=Ext.getCmp("tf_MENU_KEY");
						var url=Ext.getCmp("tf_URL");
						var media_id=Ext.getCmp("tf_MEDIA_ID");
						var type=record[0].get("DICT_VALUE");
						      if(type=='click'||type=='scancode_waitmsg'||type=='scancode_push'||type=='pic_sysphoto'||type=='pic_photo_or_album'
							||type=='pic_weixin'||type=='location_select'||type=='location_select'){
							key.show();
							key.allowBlank=false;
							url.hide();
							url.allowBlank=true;
							media_id.allowBlank=true;
							media_id.hide();
					    	   url.setValue("");
					    	   media_id.setValue("");
						       }else if(type=='view'){
						    	   url.show();
						    	   url.allowBlank=false;
						    	   media_id.hide();
						    	   media_id.allowBlank=true;
						    	   key.hide();
						    	   key.allowBlank=true;
						    	   media_id.setValue("");
						    	   key.setValue("");
						       }else if(type=='media_id'||type=='view_limited'){
						    	   url.hide();
						    	   url.allowBlank=true;
						    	   media_id.show();
						    	   media_id.allowBlank=false;
						    	   key.allowBlank=true;
						    	   key.hide();
						    	   url.setValue("");
						    	   key.setValue("");
						       }
						   },
					render:function(cb){
						cb.hide();
					}
					}
				},{
					xtype : 'textfield',
					id : 'tf_MENU_KEY',
					name : 'MENU_KEY',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					fieldLabel : 'key值',
					allowBlank : false,
					blankText : "菜单KEY不能为空",
					listeners:{ 
						render:function(cb){
							cb.hide();
						}
						}
				},{
					xtype : 'textfield',
					id : 'tf_URL',
					name : 'URL',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					fieldLabel : '网页链接(http开头)',
					allowBlank : false,
					blankText : "网页链接不能为空",
					regex : re,
					regexText : '链接格式错误！',
					listeners:{ 
						render:function(cb){
							cb.hide();
						}
						}
				},{
					xtype : 'textfield',
					id : 'tf_MEDIA_ID',
					name : 'MEDIA_ID',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					fieldLabel : '永久素材ID',
					allowBlank : false,
					blankText : "永久素材ID不能为空",
					listeners:{ 
						render:function(cb){
							cb.hide();
						}
						}
				},{
					xtype : 'textfield',
					id : 'tf_sort',
					name : 'sort',
					labelWidth : 100, // 标签宽度
					labelAlign : 'right',
					fieldLabel : '排序号',
					regex : regex,
					regexText : '必须输入数字类型！',
					allowBlank : false,
					blankText : "排序号不能为空"
				},{
					xtype : 'textfield',
					id : 'tf_MENU_ID',
					name : 'MENU_ID',
					labelWidth : 65, // 标签宽度
					labelAlign : 'right',
					fieldLabel : '菜单ID',
					hidden :true
				}]
			}],
			bbar:['->',{
					text : '保存',
					id: 'btn_save_win',
					iconCls : 'tbar_saveIcon',
					handler : function() {
						var pc = Ext.getCmp("weixinAddWinView");
	    				pc.ret = 'save';
	    				if(pc.operation =='add'){
	    					pc.submiturl = 'weixinMaintainAction!addWeiXinMenu';
	    				}else{
	    					pc.submiturl = 'weixinMaintainAction!updateWeiXinMenu';
	    				}
	    				pc.keyfields = {};
	    				var df = Ext.getCmp('addForm');
	    				if (df.form.isValid()){
	    					if('save'==pc.ret){
	    						var params = CFControlWin.getSubmitParamsForWindow('addForm',pc.operation);
	    						CFControlWin.submitAjaxRequest2(pc,params,"");
	    					}else{
	    						var params = CFControlWin.getSubmitParamsForWindow('addForm',pc.operation);
	    						Ext.apply(params, {'MENU_ID':Ext.getCmp("tf_MENU_ID").getValue()});
	    						CFControlWin.submitAjaxRequest2(pc,params,"");
	    					}
	    				}else{
	    					Ext.MessageBox.alert("提示","输入有误，无法提交！");
	    				}
					}	
				},{
					text : '取消',
					id: 'btn_del_win',
					iconCls : 'tbar_cancelIcon',
					handler : function() {
						var pc =  Ext.getCmp("weixinAddWinView");
						pc.ret = 'cancel';
						pc.close();
					}
			},'->'],
			listeners:{  
				"close":function(panel){
					
				 }
			}
    	});
    	this.callParent(arguments);
    }
});
