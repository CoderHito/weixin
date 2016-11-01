var saveFlag = "";
var detailWin = "";
Ext.define('CFrame.view.sys.weixinMaintainView', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.weixinMaintainView',
    id : 'weixinMaintainView-panel',
    requires : [ 'CFrame.model.sys.weixinModel' ],
    layout : {
		type : 'border'
	},
	title : '微信菜单维护',
	model :{},
	ret :'',
	initComponent : function() {
		var me = this;		
		var dict023= CFControl.getDictData('023',Ext.create('CFrame.store.commonDictStore'));
		var dict024= CFControl.getDictData('024',Ext.create('CFrame.store.commonDictStore'));
    	var dict025= CFControl.getDictData('025',Ext.create('CFrame.store.commonDictStore'));
		var weixinStore = Ext.create('CFrame.store.sys.weixinStore');
		weixinStore.on("beforeload", function () {
			weixinStore.proxy.extraParams = {};
	        Ext.apply(weixinStore.proxy.extraParams, CFControl.getQueryParams('queryform'));
		});
		Ext.applyIf(me, {
			items:[{
				xtype : 'form',
				region : 'north',
				id : 'queryform',
				height : 100,
				autoScroll : true,
				layout : {
					columns : 4,
					type : 'table'
				},
				bodyPadding : 10,
				title : '查询条件',
                defaults:{
               	 width:260
                },items : [ {
                    xtype: 'combo',
                    id: 'tf_qry_TYPE',
                    name: 'TYPE',
                    fieldLabel: '类型',
                    labelWidth : 95,
                    width:300,
                    labelAlign:'right',
                    store: dict023, 
                    displayField : 'DICT_NAME',   
                    valueField : 'DICT_VALUE',
                    queryMode:'local',
                    editable:false
                }
              ],
				bbar : {
					id : 'qbbar',
					items : [ {
						text : '查询',
						iconCls : 'tbar_queryIcon',
						id : 'btn_query',
						handler : function() {
							saveFlag = CFControl.type1QueryHandler(saveFlag, weixinStore, 'queryform', 'weixinGrid', 'qbbar');
						}
					}, '-',{
    					text : '增加',
    					id: 'btn_add',
    					iconCls : 'tbar_addIcon',
    					handler : function() {
    						detailWin = Ext.getCmp('weixinAddWinView');
     					    if(!detailWin){
     							 detailWin = Ext.create('CFrame.view.sys.weixinAddWinView',{
     								 modal : true,
     								 operation:'add'
     							 });
     						 }
     					    detailWin.show();
     					    detailWin.child('#addForm').child('#cb_MENU_LEVEL').disabled=false;
     					    detailWin.on('close',fn =function(){
     					    	if(detailWin.ret == 'save'){
     					    		Ext.getCmp('weixinGrid').store.reload();
     					    	}
 								detailWin.un('close',fn);
 						    });
    					}
    				}, '-',{
    					text : '删除',
    					id: 'btn_del',
    					iconCls : 'tbar_delIcon',
    					handler : function() {
    						var selectedModel = Ext.getCmp('weixinGrid').getSelectionModel().getSelection()[0];
                            if(!selectedModel){
                                Ext.Msg.alert('提示','请选择待删除的菜单！');
                                return;
                            }
                 
    						Ext.MessageBox.confirm('确认','请确认是否删除该菜单？',function(e) {
								if (e == 'yes') {
                                var result=CFControl.syncAjaxRequest('weixinMaintainAction!IsDelWeiXinMenu',{'MENU_ID':selectedModel.get('menu_id')});
									
									if(result.success){
										CFControl.submitAjaxRequestCommon('weixinMaintainAction!delWeiXinMenu',{'MENU_ID':selectedModel.get('menu_id')});
			    						Ext.getCmp('weixinGrid').store.reload();
									}else{
										Ext.MessageBox.alert("错误",result.retMessage);
									}
								
								}
							});
    					}
    				},'-', {
    					text : '修改',
    					id: 'btn_mod',
    					iconCls : 'tbar_modIcon',
    					handler : function() {
    						var selectedModel = Ext.getCmp('weixinGrid').getSelectionModel().getSelection()[0];
                            if(!selectedModel){
                                Ext.Msg.alert("提示",'请选择修改的菜单！');
                                return;
                            }
                          
    						detailWin = Ext.getCmp('weixinAddWinView');
     					    if(!detailWin){
     							 detailWin = Ext.create('CFrame.view.sys.weixinAddWinView',{
     								 modal : true,
     								 operation:'mod'
     							 });
     						 }
     					    detailWin.show();
     					    detailWin.child('#addForm').loadRecord(selectedModel);
     					    var selectedModel = Ext.getCmp('weixinGrid').getSelectionModel().getSelection()[0];
     					    var type=selectedModel.get('type');
     					    var level=selectedModel.get('level');
     					    //var has_sub_button=selectedModel.get('has_sub_button');
     					    if(level==0){
     					    	  if(type=='click'||type=='scancode_waitmsg'||type=='scancode_push'||type=='pic_sysphoto'||type=='pic_photo_or_album'
     	    							||type=='pic_weixin'||type=='location_select'||type=='location_select'){
     	     					    	detailWin.child('#addForm').child('#tf_MENU_KEY').show();
     	     					    	detailWin.child('#addForm').child('#cb_TYPE').show();
     	     					    	detailWin.child('#addForm').child('#cb_has_sub_button').allowBlank=true;
     	     					    	detailWin.child('#addForm').child('#tf_MEDIA_ID').allowBlank=true;
     	     					    	detailWin.child('#addForm').child('#tf_URL').allowBlank=true;
     	     					    	 detailWin.child('#addForm').child('#cb_TYPE').setValue(selectedModel.get('type'));
     	     					    	detailWin.child('#addForm').child('#tf_MENU_KEY').setValue(selectedModel.get('menu_key'));
     	     					    }else if(type=='media_id'||type=='view_limited'){
     	     					  	detailWin.child('#addForm').child('#cb_has_sub_button').allowBlank=true;
     	     					    	detailWin.child('#addForm').child('#tf_MEDIA_ID').show();
     	     					  	detailWin.child('#addForm').child('#tf_URL').allowBlank=true;
     	     					  detailWin.child('#addForm').child('#tf_MENU_KEY').allowBlank=true;
     	     					    	detailWin.child('#addForm').child('#cb_TYPE').show();
     	     					  	detailWin.child('#addForm').child('#tf_MEDIA_ID').setValue(selectedModel.get('media_id'));
     	     					    	 detailWin.child('#addForm').child('#cb_TYPE').setValue(selectedModel.get('type'));
     	     					    }else if(type=='view'){
     	     					  	detailWin.child('#addForm').child('#cb_has_sub_button').allowBlank=true;
     	     					  	detailWin.child('#addForm').child('#tf_MEDIA_ID').allowBlank=true;
       	     					    detailWin.child('#addForm').child('#tf_MENU_KEY').allowBlank=true;
     	     					    	detailWin.child('#addForm').child('#tf_URL').show();
     	     					    	detailWin.child('#addForm').child('#cb_TYPE').show();
     	     					    	 detailWin.child('#addForm').child('#cb_TYPE').setValue(selectedModel.get('type'));
     	     					    	detailWin.child('#addForm').child('#tf_URL').setValue(selectedModel.get('url'));
     	     					    }else{
     	     						detailWin.child('#addForm').child('#tf_MEDIA_ID').allowBlank=true;
       	     					    detailWin.child('#addForm').child('#tf_MENU_KEY').allowBlank=true;
       	     					     detailWin.child('#addForm').child('#tf_URL').allowBlank=true;
       	     					 detailWin.child('#addForm').child('#cb_TYPE').allowBlank=true;
      					    	     detailWin.child('#addForm').child('#cb_has_sub_button').show();
 	     					    	 detailWin.child('#addForm').child('#cb_has_sub_button').setValue(selectedModel.get('has_sub_button'));
     	     					    }
     					    	 detailWin.child('#addForm').child('#cb_MENU').allowBlank=true;  
     					    }else{
     					    	 if(type=='click'||type=='scancode_waitmsg'||type=='scancode_push'||type=='pic_sysphoto'||type=='pic_photo_or_album'
  	    							||type=='pic_weixin'||type=='location_select'||type=='location_select'){
  	     					    	detailWin.child('#addForm').child('#tf_MENU_KEY').show();
  	     					    	detailWin.child('#addForm').child('#cb_TYPE').show();
  	     					 	detailWin.child('#addForm').child('#tf_MEDIA_ID').allowBlank=true;
	     					    	detailWin.child('#addForm').child('#tf_URL').allowBlank=true;
  	     					    	 detailWin.child('#addForm').child('#cb_TYPE').setValue(selectedModel.get('type'));
  	     					    	detailWin.child('#addForm').child('#tf_MENU_KEY').setValue(selectedModel.get('menu_key'));
  	     					    }else if(type=='media_id'||type=='view_limited'){
  	     					 	detailWin.child('#addForm').child('#tf_URL').allowBlank=true;
   	     					  detailWin.child('#addForm').child('#tf_MENU_KEY').allowBlank=true;
  	     					    	detailWin.child('#addForm').child('#tf_MEDIA_ID').show();
  	     					    	detailWin.child('#addForm').child('#cb_TYPE').show();
  	     					  	detailWin.child('#addForm').child('#tf_MEDIA_ID').setValue(selectedModel.get('media_id'));
  	     					    	 detailWin.child('#addForm').child('#cb_TYPE').setValue(selectedModel.get('type'));
  	     					    }else if(type=='view'){
  	     					   	detailWin.child('#addForm').child('#tf_MEDIA_ID').allowBlank=true;
     	     					  detailWin.child('#addForm').child('#tf_MENU_KEY').allowBlank=true;
  	     					    	detailWin.child('#addForm').child('#tf_URL').show();
  	     					    	detailWin.child('#addForm').child('#cb_TYPE').show();
  	     					    	 detailWin.child('#addForm').child('#cb_TYPE').setValue(selectedModel.get('type'));
  	     					    	detailWin.child('#addForm').child('#tf_URL').setValue(selectedModel.get('url'));
  	     					    }else{
  	     					    	detailWin.child('#addForm').child('#cb_has_sub_button').show();
  	     					    	 detailWin.child('#addForm').child('#cb_has_sub_button').setValue(selectedModel.get('has_sub_button'));
  	     					    }
     					    	 detailWin.child('#addForm').child('#cb_has_sub_button').allowBlank=true;
     					    	detailWin.child('#addForm').child('#cb_MENU').show();
	     					    detailWin.child('#addForm').child('#cb_MENU').setValue(selectedModel.get('parent_id'));
     					    }
     					    detailWin.child('#addForm').child('#cb_MENU_LEVEL').disabled=true;
     					    detailWin.child('#addForm').child('#cb_has_sub_button').disabled=true;
     					    detailWin.child('#addForm').child('#tf_sort').setValue(selectedModel.get('sort'));
     					    detailWin.child('#addForm').child('#cb_MENU_LEVEL').setValue(selectedModel.get('level'));
  					    	detailWin.child('#addForm').child('#tf_MENU_NAME').setValue(selectedModel.get('menu_name'));
  					    	detailWin.child('#addForm').child('#tf_MENU_ID').setValue(selectedModel.get('menu_id'));
     					    detailWin.on('close',fn =function(){
     					    	if(detailWin.ret == 'save'){
     					    		Ext.getCmp('weixinGrid').store.reload(selectedModel);
     					    	}
 								detailWin.un('close',fn);
 						    });
    					}
    				},'-',{
    					text : '激活',
    					id: 'btn_act',
    					iconCls : 'tbar_delIcon',
    					handler : function() {
    						Ext.MessageBox.confirm('确认','请确认是否激活菜单？',function(e) {
								if (e == 'yes') {
									var result=CFControl.syncAjaxRequest('weixinMaintainAction!IsActWeiXinMenu');
									
									if(result.success){
										CFControl.submitAjaxRequestCommon('weixinMaintainAction!actWeiXinMenu');
									}else{
										Ext.MessageBox.alert("错误",result.retMessage);
									}
								}
							});
    					}
    				},'-', {
						text : '重置',
						id : 'btn_reset',
						iconCls : 'tbar_buildingIcon2',
						handler : function() {
							CFControl.type1ResetHandler('queryform','weixinGrid', 'qbbar','pageToolBar','');
						}
					}
    				/*,
    				'-', {
						text : '测试',
						id : 'btn_reset1',
						iconCls : 'tbar_buildingIcon2',
						handler : function() {
							CFControl.submitAjaxRequestCommon('accountMaintainAction!checkFun','');
						}
					}*/
    				]
				}
			},{
				xtype : 'gridpanel',
				height : 650,
				region : 'center',
				id : 'weixinGrid',
				store : weixinStore,
				selModel : Ext.create('Ext.selection.RowModel', {
					mode : "SINGLE"
				}),
				viewConfig:{  
	                enableTextSelection:true  
	            }, 
				columns : [ {
					xtype : 'gridcolumn',
					dataIndex : 'menu_id',
					width: 100,
					text : '菜单ID'
				},{
					xtype : 'gridcolumn',
					dataIndex : 'type',
					width: 150,
					text : '菜单类型',
					renderer : function rendererItem(value){
						dict023.clearFilter();
						if(dict023.getCount()==0){
							dict023.reload();
						}
						if(dict023.getCount()==0){
							Ext.MessageBox.alert("错误",'[菜单类型]数据字典挂载失败');
						}
				        var index = dict023.find('DICT_VALUE',value,0,false,true,true); 
				        if(index == -1){
				        	return "";
				        }
				        var record = dict023.getAt(index).get('DICT_NAME'); 
				        return record;
					}
				},{
					xtype : 'gridcolumn',
					dataIndex : 'menu_name',
					width: 150,
					text : '菜单标题'
				},{
					xtype : 'gridcolumn',
					dataIndex : 'level',
					width: 120,
					text : '菜单等级',
					renderer : function rendererItem(value){
						dict024.clearFilter();
						if(dict024.getCount()==0){
							dict024.reload();
						}
						if(dict024.getCount()==0){
							Ext.MessageBox.alert("错误",'[菜单等级]数据字典挂载失败');
						}
				        var index = dict024.find('DICT_VALUE',value,0,false,true,true); 
				        if(index == -1){
				        	return "";
				        }
				        var record = dict024.getAt(index).get('DICT_NAME'); 
				        return record;
					}		
				},{
					xtype : 'gridcolumn',
					dataIndex : 'has_sub_button',
					width: 150,
					text : '是否有二级菜单',
					renderer : function rendererItem(value){
						dict025.clearFilter();
						if(dict025.getCount()==0){
							dict025.reload();
						}
						if(dict025.getCount()==0){
							Ext.MessageBox.alert("错误",'[是否有二级菜单]数据字典挂载失败');
						}
				        var index = dict025.find('DICT_VALUE',value,0,false,true,true); 
				        if(index == -1){
				        	return "";
				        }
				        var record = dict025.getAt(index).get('DICT_NAME'); 
				        return record;
					}	
				},{
					xtype : 'gridcolumn',
					dataIndex : 'url',
					width: 250,
					text : '网页链接'
				},{
					xtype : 'gridcolumn',
					dataIndex : 'menu_key',
					width: 100,   
					text : '菜单KEY值'
				},{
					xtype : 'gridcolumn',
					dataIndex : 'media_id',
					width: 100,
					text : '永久素材ID'
				},{
					xtype : 'gridcolumn',
					dataIndex : 'parent_id',
					width: 100,
					text : '所属一级菜单ID'
				},{
					xtype : 'gridcolumn',
					dataIndex : 'sort',
					width: 100,
					text : '菜单排序号',
					hidden : true
				}],
				bbar : {
					xtype : 'pagingtoolbar',
					region : 'south',
					id : 'pageToolBar',
					height : 30,
					store : weixinStore,
					displayInfo : true,
					displayMsg : '当前显示的记录 {0} - {1} 共计 {2}',
					emptyMessage : '没有可显示的记录',
				}
			}]
		});
		me.callParent(arguments);
	}
});
Ext.onReady(function() {	
	var win = Ext.create('CFrame.view.sys.weixinMaintainView');
	win.show();
	CFControl.setToolBarStatus('qbbar', true);
});
