var CFMaintainControl = CFMaintainControl || {};

(function() {		
	Ext.apply(CFMaintainControl,{

		/******begin**********queryform***toolbar***grid***pagebar***detailForm**************/
		/**
		 * 查询处理
		 */
		type10QueryHandler:function(saveFlag, deptStore,deptManGrid, qbbar){
			store.load({
				params:CFMaintainControl.getQueryParams(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						var dg = Ext.getCmp(gridid);
						dg.getSelectionModel().select(0,true);
						CFMaintainControl.setToolBarStatus(tbarid,true);
						
						Ext.MessageBox.alert("提示", text.retMessage);
		    		}else{
		    			Ext.MessageBox.alert("错误", text.retMessage);
		    		};
		       },
			scope:this
			});
		saveFlag = "";
		return saveFlag;
		},
		
		/**
		 * 与查询处理，不同是不显示查询成功提示框
		 */		
		type1RefreshHandler:function(store,formid,gridid,tbarid){
			store.load({
				params:CFMaintainControl.getQueryParams(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						var dg = Ext.getCmp(gridid);
						dg.getSelectionModel().select(0,true);
						CFMaintainControl.setToolBarStatus(tbarid,true);
		    		}else{
		    			Ext.MessageBox.alert("错误", text.retMessage);
		    		};
		       },
			scope:this
			});
		},
		
		/**
		 * 新增处理
		 * 
		 */
		type1AddHandler:function(saveFlag,modelid,detailformid,gridid,tbarid){
			var record = Ext.create(modelid);
			var dg = Ext.getCmp(gridid);
			dg.setDisabled(true);
            dg.store.insert(0,record);
			dg.getSelectionModel().select(0,true);
			CFMaintainControl.setFormReadOnly(detailformid,false);
			var df = Ext.getCmp(detailformid);
			df.form.reset();
			df.loadRecord(record);
			CFMaintainControl.setToolBarStatus(tbarid,false);
			saveFlag = "add";
			return saveFlag;
		},
		
		/**
		 * 修改处理
		 * 
		 */
		type1ModHandler:function(saveFlag,formid,gridid,tbarid,ModCtlArray){
			var tg = Ext.getCmp(gridid);
			var tmpmodel = tg.getSelectionModel().getSelection()[0];
			if(tmpmodel){
				tg.setDisabled(true);
				CFMaintainControl.setToolBarStatus(tbarid,false);
				CFMaintainControl.setFormReadOnlyForMod(formid,ModCtlArray);
				saveFlag = "mod";
			}else{
				Ext.MessageBox.alert("提示", "没有需要修改的记录");
			}
			return saveFlag;
		},

        /**
		 * 删除处理
		 * 
		 */
		type1DelHandler:function(saveFlag,url,formid,gridid){
			var tg = Ext.getCmp(gridid);
			var tmpmodel = tg.getSelectionModel().getSelection()[0];
			if(tmpmodel){
				saveFlag = "del";
			
				CFMaintainControl.submitAjaxRequest(url,
					CFMaintainControl.getSubmitParams(formid,saveFlag),saveFlag,formid,gridid,"");
			}else{
				Ext.MessageBox.alert("提示", "没有需要删除的记录");
			}
			return saveFlag;
		},
		
		/**
		 * 保存处理
		 * 
		 */
		type1SaveHandler:function(saveFlag,url,detailformid,gridid,tbarid){
			var df = Ext.getCmp(detailformid);
			if (df.form.isValid()){
				CFMaintainControl.submitAjaxRequest(url,
						CFMaintainControl.getSubmitParams(detailformid,saveFlag),saveFlag,detailformid,gridid,tbarid);
			}else{
				Ext.MessageBox.alert("提示","输入有误，无法提交！");
			}
			return saveFlag;
		},
		
		/**
		 * 取消处理
		 * 
		 */
		type1CancelHandler:function(saveFlag,formid,gridid,tbarid){
			var dg = Ext.getCmp(gridid);
			if("add" == saveFlag){
				dg.getSelectionModel().select(1,true);
				if(dg.store.getCount()==1){
					dg.store.removeAt(0);
				}
			}else{
				saveFlag="";
				CFMaintainControl.setToolBarStatus(tbarid,true);
				CFMaintainControl.setFormReadOnly(formid,true);
			}
			dg.setDisabled(false);
			return saveFlag; 
		},
		
		/******end**********queryform***toolbar***grid***pagebar***detailForm**************/
		
		
		
		/**
		 * queryForm里面查询条件拼接，目前只支持combo,textfield,
		 * numberfield,datefield,triggerfield
		 * 参数1为form的id
		 * 控件为textfield时，控件名称为tf_qry_字段名称
		 * 控件为combo时，控件名称为tf_qry_字段名称
		 * 控件为numberfield时，控件名称为nf_qry_字段名称
		 * 控件为datefield时，控件名称为df_qry_字段名称，8位
		 * 控件为datefield时，控件名称为dt_qry_字段名称，14位
		 * 控件为triggerfield时，控件名称为tg_qry_字段名称
		 * 控件为checkbox时，控件名称为ck_qry_字段名称
		 */
		getQueryParams : function(formid) {
			var el = Ext.getCmp(formid);
			var tmp = '';
			el.items.each(function(item,index,length){                           
				if('textfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
				if('combo'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
				if('numberfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
				if('datefield'==item.xtype && !(item.getValue()===null)){
					if('df_qry_'==item.id.substring(0,7)){
						tmp = tmp + ','+item.id.substring(7) + ':"'+Ext.util.Format.date(item.getValue(),'Ymd')+'"';
					}
					if('dt_qry_'==item.id.substring(0,7)){
						tmp = tmp + ','+item.id.substring(7) + ':"'+Ext.util.Format.date(item.getValue(),'YmdHis')+'"';
					}
				}
				if('triggerfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
				if('checkbox'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
			});
				
				
			var params = '{}';
			if (tmp.length>0){
				tmp = tmp.substring(1);
				eval('params={'+tmp+'}');
			}
			return params;
		},
		
		/**
		 * 控制form里面所有控件只读属性，目前只支持textfield,combo,numberfield,
		 * datefield,triggerfield,checkbox
		 * 参数1为form的id
		 * 参数2为是否只读 
		 */
		setFormReadOnly : function(formid,readlOnly) {
			var el = Ext.getCmp(formid);
			el.items.each(function(item,index,length){                           
				if('textfield'==item.xtype)
					item.setReadOnly(readlOnly);
				if('combo'==item.xtype)
					item.setReadOnly(readlOnly);
				if('numberfield'==item.xtype)
					item.setReadOnly(readlOnly);
				if('datefield'==item.xtype)
					item.setReadOnly(readlOnly);
				if('triggerfield'==item.xtype)
					item.setReadOnly(readlOnly);
				if('checkbox'==item.xtype)
					item.setReadOnly(readlOnly);
			});
		},
		
		/**
		 * 修改时控制form里面所有控件只读属性，目前只支持textfield,combo,numberfield,
		 * datefield,triggerfield,checkbox
		 * 参数1为form的id
		 * 参数2为是否只读 
		 */
		setFormReadOnlyForMod : function(formid,ModCtlArray) {
			var el = Ext.getCmp(formid);
			el.items.each(function(item,index,length){                           
				if('textfield'==item.xtype)
					item.setReadOnly(false);
				if('combo'==item.xtype)
					item.setReadOnly(false);
				if('numberfield'==item.xtype)
					item.setReadOnly(false);
				if('datefield'==item.xtype)
					item.setReadOnly(false);
				if('triggerfield'==item.xtype)
					item.setReadOnly(false);
				if('checkbox'==item.xtype)
					item.setReadOnly(false);
			});
			for(var i=0;i<ModCtlArray.length;i++){
				var item = Ext.getCmp(ModCtlArray[i]);
				if('textfield'==item.xtype)
					item.setReadOnly(true);
				if('combo'==item.xtype)
					item.setReadOnly(true);
				if('numberfield'==item.xtype)
					item.setReadOnly(true);
				if('datefield'==item.xtype)
					item.setReadOnly(true);
				if('triggerfield'==item.xtype)
					item.setReadOnly(true);
				if('checkbox'==item.xtype)
					item.setReadOnly(true);
			}  
		},
		/**
		 * detailForm赋值
		 * 参数1为form的id
		 * 参数2为Ext.data.model
		 * 控件为textfield时，控件名称为tf_字段名称
		 * 控件为combo时，控件名称为cb_字段名称
		 * 控件为numberfield时，控件名称为nf_字段名称
		 * 控件为datefield时，控件名称为df_字段名称
		 * 控件为triggerfield时，控件名称为tg_字段名称
		 * 控件为checkbox时，控件名称为tg_字段名称
		 */
		setFormFieldValue : function(formid,model) {
			var el = Ext.getCmp(formid);
			el.items.each(function(item,index,length){                           
				if('textfield'==item.xtype){
					item.setValue(model.get(item.id.substring(3)));
				}
				if('combo'==item.xtype){
					item.setValue(model.get(item.id.substring(3)));
				}
				if('numberfield'==item.xtype){
					item.setValue(model.get(item.id.substring(3)));
				}
				if('datefield'==item.xtype){
					item.setValue(model.get(item.id.substring(3)));
				}
				if('triggerfield'==item.xtype){
					item.setValue(model.get(item.id.substring(3)));
				}
				if('checkbox'==item.xtype){
					item.setValue(model.get(item.id.substring(3)));
				}
			});
		},
		
		/**
		 * detailForm里面提交参数拼接，目前只支持textfield,combo,numberfield,
		 * datefield,triggerfield
		 * 参数1为form的id
		 * 控件为textfield时，控件名称为tf_字段名称
		 * 控件为cmbo时，控件名称为cb_字段名称
		 * 控件为numberfield时，控件名称为nf_字段名称
		 * 控件为datefield时，控件名称为df_字段名称，8位
		 * 控件为datefield时，控件名称为dt_字段名称，14位
		 * 控件为triggerfield时，控件名称为tg_字段名称
		 * 控件为checkbox时，控件名称为ck_字段名称
		 */
		getSubmitParams : function(formid,saveFlag) {
			var el = Ext.getCmp(formid);
			var tmp = '';
			el.items.each(function(item,index,length){                           
				if('textfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
				if('combo'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
				if('numberfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
				if('datefield'==item.xtype && !(item.getValue()===null)){
					if('df_'==item.id.substring(0,3)){
						tmp = tmp + ','+item.id.substring(3) + ':"'+Ext.util.Format.date(item.getValue(),'Ymd')+'"';
					}
					if('dt_'==item.id.substring(0,3)){
						tmp = tmp + ','+item.id.substring(3) + ':"'+Ext.util.Format.date(item.getValue(),'YmdHis')+'"';
					}
				}
				if('triggerfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
				if('checkbox'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
			});
				
				
			var params = '{}';
			if (tmp.length>0){
				tmp = 'saveFlag'+ ':"'+saveFlag+'"'+tmp;
				eval('params={'+tmp+'}');
			}
			return params;
			
		},
		/**
		 * 控制ToolBar里面所有控件是否可用，目前只支持btton
		 * 参数1为form的id
		 * 参数2为是否只读 
		 * 设置保存状态属性
		 */
		setToolBarStatus : function(toolbarid,disable) {
			var el = Ext.getCmp(toolbarid);
			el.items.each(function(item,index,length){                           
				if('button'==item.xtype)
					if("btn_query"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_add"==item.id){
						item.setDisabled(!disable);
					}
					if("btn_addsub"==item.id){
						item.setDisabled(!disable);
					}
					if("btn_mod"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_save"==item.id){
						item.setDisabled(disable); 
					}
					if("btn_del"==item.id){
						item.setDisabled(!disable); 
					}
					
			});
		},
		/**
		 * 绑定pagetoolbar里面refresh事件
		 * 参数1为pagetoolbar的id
		 * 参数2为自定义的操作函数名 
		 */
	    setRefreshHandelerForPagebar : function(pagebarid,functionname) {
	    	Ext.getCmp(pagebarid).child('#refresh').setHandler(functionname);
	    },
	    	    
		
	    /**
		 * 向后台提交请求
		 * 参数1为
		 * 参数2为参数对象
		 */
		submitAjaxRequest:function(url,params,saveFlag,formid,gridid,tbarid){
			 Ext.Ajax.request({
				 url:url,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:params, // 提交参数
				 success: function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 Ext.MessageBox.alert("提示", obj.retMessage);
						 var df = Ext.getCmp(formid);
						 var grid = Ext.getCmp(gridid);
						 if ("del" == saveFlag){
							 var record = grid.getSelectionModel().getLastSelected();//获取选中的第一条记录,返回record类型
                             grid.store.reload();
							 df.form.reset();
							 grid.getSelectionModel().select(0,true);
						 }
						 if("mod" == saveFlag){
							 CFMaintainControl.setToolBarStatus(tbarid,true);
							 CFMaintainControl.setFormReadOnly(formid,true);
							 var record = grid.getSelectionModel().getLastSelected();
							 var linenum = grid.store.indexOf(record);
							 grid.store.reload();
							 grid.getSelectionModel().select(linenum,true); 
						 }
						 
						 if("add" == saveFlag){
							 CFMaintainControl.setToolBarStatus(tbarid,true);
							 CFMaintainControl.setFormReadOnly(formid,true);
							 grid.store.reload();
							 //将detailForm的值付给store
						 }
						 grid.setDisabled(false);
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }
				 }, 
				 failure:function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 Ext.MessageBox.alert("错误", obj.retMessage);
				 }
			 });
		},
		
		/**
		 * 向后台提交请求
		 * 参数1为
		 * 参数2为参数对象
		 */
		submitAjaxRequestCommon:function(url,params){
			 Ext.Ajax.request({
				 url:url,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:params, // 提交参数
				 success: function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 Ext.MessageBox.alert("提示", obj.retMessage);
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }
				 }, 
				 failure:function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 Ext.MessageBox.alert("错误", obj.retMessage);
				 }
			 });
		},
		
		/**
		 * 格式化日期和时间
		 * 8位的转成yyyy-mm-dd
		 * 14位转成yyyy-mm-dd hh:mm:ss
		 * @param value
		 * @returns
		 */
		formatDate:function(value){
			if(value.length==8){
				value = Ext.String.insert(value, "-", -4); 
				value = Ext.String.insert(value, "-", -2);
				return value;
			} else if(value.length==14){
				value = Ext.String.insert(value, "-", -10); 
				value = Ext.String.insert(value, "-", -8);
				value = Ext.String.insert(value, " ", -6);
				value = Ext.String.insert(value, ":", -4);
				value = Ext.String.insert(value, ":", -2);
				return value;
			} else {
				return value;
			}	
				
		},
		/**
		 * 合并对象
		 */
		extend:function(o,n,override){
			   for(var p in n)if(n.hasOwnProperty(p) && (!o.hasOwnProperty(p) || override))o[p]=n[p];
		},
		
		/**
		 * 生成GUID
		 */
		genGuid:function(o,n,override){
			var uuid = Ext.data.IdGenerator.get('uuid').generate();
			return uuid.replace(/-/ig,'');
		}
    });
	
}());