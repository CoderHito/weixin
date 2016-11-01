var CFControl = CFControl || {};

(function() {		
	Ext.apply(CFControl,{
		/**
		 * 根据段号去数字字典
		 * 
		 */
		getDictData:function(GROUP_CODE,store){
			Ext.apply(Ext.data.Connection.prototype, { async: false  });
			store.load({
				params: {'groupcode':GROUP_CODE},
				callback:function(){  
					flag = true;
				}  
			});
			Ext.apply(Ext.data.Connection.prototype, { async: true  });	
			return store;
		},
		
		getDictMapData:function(groupArray,store){
			Ext.apply(Ext.data.Connection.prototype, { async: false  });
			store.load({
				params: {'groupcode':groupArray},
				callback:function(){  
					flag = true;
				}  
			});
			Ext.apply(Ext.data.Connection.prototype, { async: true  });		
			return store;
		},
		
		/******begin**********queryform***toolbar***grid***pagebar***detailForm**************/
		/**
		 * 查询处理
		 */
		type1QueryHandler:function(saveFlag,store,formid,gridid,tbarid){
			store.currentPage=1;
			store.load({
				params:CFControl.getQueryParams(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						var dg = Ext.getCmp(gridid);
						dg.getSelectionModel().select(0,true);
						CFControl.setToolBarStatus(tbarid,true);
						if(text.total==0){
							Ext.MessageBox.alert("提示", text.retMessage);
						}
		    		}else{
		    			Ext.MessageBox.alert("错误", text.retMessage);
		    		};
		       },
			scope:this
			});
		saveFlag = "";
		return saveFlag;
		},		
		type2QueryHandler:function(saveFlag,store,formid,gridid,tbarid){
			store.currentPage=1;
			store.load({
				params:CFControl.getQueryParams(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						//var dg = Ext.getCmp(gridid);
						CFControl.setToolBarStatus(tbarid,true);
						if(text.total==0){
							Ext.MessageBox.alert("提示", text.retMessage);
						}
		    		}else{
		    			Ext.MessageBox.alert("错误", text.retMessage);
		    		};
		       },
			scope:this
			});
		saveFlag = "";
		return saveFlag;
		},
		typeResQueryHandler:function(saveFlag,store,formid,gridid,tbarid){
			store.currentPage=1;
			store.load({
				params:CFControl.getQueryParams(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						var dg = Ext.getCmp(gridid);
						dg.getSelectionModel().select(0,true);
						CFControl.setToolBarStatus(tbarid,true);
						if(text.total==0){
							Ext.MessageBox.alert("提示", text.retMessage);
							saveFlag = 0;
						}
						else{
							saveFlag = 1;
						}
		    		}else{
		    			Ext.MessageBox.alert("错误", text.retMessage);
		    			saveFlag = 0;
		    		};
		       },
			scope:this
			});
		
		return saveFlag;
		},
		
		/******begin**********queryform***toolbar***grid***pagebar***detailForm**************/
		/**
		 * @author helin
		 * 查询处理，用于弹层form提交搜索
		 */
		type1TopQueryHandler:function(saveFlag,store,formid,gridid,tbarid){
			store.currentPage=1;
			store.load({
				params:CFControl.getTopQueryParams(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						var dg = Ext.getCmp(gridid);
						dg.getSelectionModel().select(0,true);
						CFControl.setToolBarStatus(tbarid,true);
						if(text.total==0){
							Ext.MessageBox.alert("提示", text.retMessage);
						}
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
			store.currentPage=1;
			store.load({
				params:CFControl.getQueryParams(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						var dg = Ext.getCmp(gridid);
						dg.getSelectionModel().select(0,true);
						CFControl.setToolBarStatus(tbarid,true);
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
			CFControl.setFormReadOnly(detailformid,false);
			var df = Ext.getCmp(detailformid);
			df.form.reset();
			df.loadRecord(record);
			CFControl.setToolBarStatus(tbarid,false);
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
				CFControl.setToolBarStatus(tbarid,false);
				CFControl.setFormReadOnlyForMod(formid,ModCtlArray);
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
			
				CFControl.submitAjaxRequest(url,
					CFControl.getSubmitParams(formid,saveFlag),saveFlag,formid,gridid,"");
			}else{
				Ext.MessageBox.alert("提示", "没有需要删除的记录");
			}
			return saveFlag;
		},
		type2DelHandler:function(url,params,store,qryForm,grid,qbbar){
			Ext.Ajax.request({
				 url:url,
				 method:'POST',
				 params:params, // 提交参数
				 success: function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 Ext.MessageBox.alert("提示", obj.retMessage);
						 CFControl.type1RefreshHandler(store, qryForm, grid, qbbar);
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }
				 }, 
				 failure:function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 Ext.MessageBox.alert("错误", "系统异常请联系管理员");
				 }
			 });
		},
		
		/**
		 * 保存处理
		 * 
		 */
		type1SaveHandler:function(saveFlag,url,detailformid,gridid,tbarid){
			var df = Ext.getCmp(detailformid);
			if (df.form.isValid()){
				CFControl.submitAjaxRequest(url,
						CFControl.getSubmitParams(detailformid,saveFlag),saveFlag,detailformid,gridid,tbarid);
			}else{
				Ext.MessageBox.alert("提示","输入有误，无法提交！");
			}
			return saveFlag;
		},
		
		type2SaveHandler:function(saveFlag,url,detailformid,gridid,tbarid,record){
			var df = Ext.getCmp(detailformid);		
			if (df.form.isValid()){
				var tmp3='';
				if(record!=''){
				for(var i=0;i<record.length;i++)
					{
						var t="{";
						Ext.Object.each(record[i].data,function(key, value, myself) {
							t=t+'"'+key+'":"'+value+'",';	    				
						});
						tmp3=tmp3+t.substring(0,t.length-1)+"},";
					}
				}
				tmp3="["+tmp3.substring(0,tmp3.length-1)+"]";
				CFControl.submitAjaxRequestNew(url,detailformid,gridid,tbarid,
						CFControl.getSubmitParams(detailformid,saveFlag),tmp3,null);
			}else{
				Ext.MessageBox.alert("提示","输入有误，无法提交！");
				return -1;
			}
			return saveFlag;
		},

		type3SaveHandler:function(saveFlag,url,detailformid,gridid,tbarid,record){
			var df = Ext.getCmp(detailformid);		
			if (df.form.isValid()){
				var tmp2='';	
				if(record!=''){
					for(var i=0;i<record.data.items.length;i++)
						{
						var t="{";
						Ext.Object.each(record.data.items[i].data, function(key, value, myself) {
								t=t+'"'+key+'":"'+value+'",';
						});
						tmp2=tmp2+t.substring(0,t.length-1)+"},";
						}
				}
				tmp2="["+tmp2.substring(0,tmp2.length-1)+"]";
				CFControl.submitAjaxRequestNew(url,detailformid,gridid,tbarid,
						CFControl.getSubmitParams(detailformid,saveFlag),tmp2,null);
			}else{
				Ext.MessageBox.alert("提示","输入有误，无法提交！");
				return -1;
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
				if(dg.store.getCount()>1){
					dg.getSelectionModel().select(1,true);
				}else{
					dg.getSelectionModel().deselectAll();
				}
//				if(dg.store.getCount()==1){
//					dg.store.removeAt(0);
//				}
			}
			saveFlag="";
			CFControl.setToolBarStatus(tbarid,true);
			CFControl.setFormReadOnly(formid,true);
			dg.setDisabled(false);
			return saveFlag; 
		},
		/**
		 * 重置方法 查询条件、查询结果、明细置为空
		 */
		type1ResetHandler:function(queryformid,gridid,tbarid,pbarid,detailformid){
			if(queryformid){
				var qrForm = Ext.getCmp(queryformid); 
				qrForm.form.reset();
			}
			if(gridid){
				var grid = Ext.getCmp(gridid);
				var store = grid.store;
				store.removeAll();
			}
			if(pbarid){
				var pbar = Ext.getCmp(pbarid);
				pbar.currentPage = 0;
			}
			if(tbarid){
				CFControl.setToolBarStatus(tbarid,true);
			}
			if(detailformid){
				var detailForm = Ext.getCmp(detailformid);
				CFControl.setFormReadOnly(detailformid,true);
				detailForm.form.reset();
			}
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
			if(!el){
				return '{}';
			}
			var tmp = '';
			el.items.each(function(item,index,length){  
				if('textfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+ Ext.String.trim(item.getValue())+'"';
				}
				if('combo'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+Ext.String.trim(item.getValue())+'"';
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
				if('datetimefield'==item.xtype && !(item.getValue()===null)){
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
				if('textarea'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
				if('treecombo'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
			});
				
				
			var params = '{}';
			if (tmp.length>0){
				tmp = tmp.substring(1).replace(/\\/g,'\\\\');//需要把\替换成\\，不然eval会出错
				eval('params={'+tmp+'}');
			}
			return params;
		},
		
		
		/**
		 * @author helin
		 * 用于弹层数据请求
		 * 
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
		getTopQueryParams : function(formid) {
			var el = top.Ext.getCmp(formid);
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
				if('textarea'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(7) + ':"'+item.getValue()+'"';
				}
			});
			
			
			var params = '{}';
			if (tmp.length>0){
				tmp = tmp.substring(1).replace(/\\/g,'\\\\');
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
					item.setDisabled(readlOnly);
				if('combo'==item.xtype)
					item.setDisabled(readlOnly);
				if('numberfield'==item.xtype)
					item.setDisabled(readlOnly);
				if('datefield'==item.xtype)
					item.setDisabled(readlOnly);
				if('triggerfield'==item.xtype)
					item.setDisabled(readlOnly);
				if('checkbox'==item.xtype)
					item.setDisabled(readlOnly);
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
					item.setDisabled(false);
				if('combo'==item.xtype)
					item.setDisabled(false);
				if('numberfield'==item.xtype)
					item.setDisabled(false);
				if('datefield'==item.xtype)
					item.setDisabled(false);
				if('triggerfield'==item.xtype)
					item.setDisabled(false);
				if('checkbox'==item.xtype)
					item.setDisabled(false);
			});
			for(var i=0;i<ModCtlArray.length;i++){
				var item = Ext.getCmp(ModCtlArray[i]);
				if('textfield'==item.xtype)
					item.setDisabled(true);
				if('combo'==item.xtype)
					item.setDisabled(true);
				if('numberfield'==item.xtype)
					item.setDisabled(true);
				if('datefield'==item.xtype)
					item.setDisabled(true);
				if('triggerfield'==item.xtype)
					item.setDisabled(true);
				if('checkbox'==item.xtype)
					item.setDisabled(true);
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
				if('datetimefield'==item.xtype && !(item.getValue()===null)){
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
				if('textarea'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
			});
				
				
			var params = '{}';
			if (tmp.length>0){
				tmp = 'saveFlag'+ ':"'+saveFlag+'"'+tmp;
				
				eval('params={'+tmp.replace(/\\/g,'\\\\')+'}');
			}
			return params;
			
		},
		
		getSubmitParamsAll : function(formids,saveFlag) {
			
			var tmp = '';
			
			for(var i=0; i<formids.length;i++){
				var formid =formifs[i];
				var el = Ext.getCmp(formid);
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
					
					if(i==formids.length-1){
						tmp = '"' +formid+ '":'+tmp+'}]';
					}else{
						tmp = '"' +formid+ '":'+tmp+'}],';
					}
					
				});
			}
				
			var params = '{}';
			if (tmp.length>0){
				tmp = 'saveFlag'+ ':"'+saveFlag+'"'+tmp;
				eval('params={'+tmp.replace(/\\/g,'\\\\')+'}');
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
					if("btn_reset"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_func"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_copy"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_role"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_resetpass"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_setactive"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_syn"==item.id){
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
							 CFControl.setToolBarStatus(tbarid,true);
							 CFControl.setFormReadOnly(formid,true);
							 var record = grid.getSelectionModel().getLastSelected();
							 var linenum = grid.store.indexOf(record);
							 grid.store.reload();
							 grid.getSelectionModel().select(linenum,true); 
						 }
						 
						 if("add" == saveFlag){
							 CFControl.setToolBarStatus(tbarid,true);
							 CFControl.setFormReadOnly(formid,true);
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
		
		submitAjaxRequestNew:function(url,formid,gridid,tbarid,param1,param2,param3){
			var tmp='';
			Ext.Object.each(param1, function(key, value, myself) {
			    tmp=tmp+key+":"+value+",";	    				
			});
			tmp="{"+tmp;
			tmp=tmp.substring(0,tmp.length-1)+"}";
			Ext.Ajax.request({
				 url:url,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:{param1:tmp,param2:param2,param3:param3}, // 提交参数
				 success: function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 Ext.MessageBox.alert("提示", obj.retMessage);
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }var df = Ext.getCmp(formid);
					 var grid = Ext.getCmp(gridid);
					 if ("del" == saveFlag){
						 var record = grid.getSelectionModel().getLastSelected();//获取选中的第一条记录,返回record类型
                         grid.store.reload();
						 df.form.reset();
						 grid.getSelectionModel().select(0,true);
					 }
					 if("mod" == saveFlag){
						 CFControl.setToolBarStatus(tbarid,true);
						 CFControl.setFormReadOnly(formid,true);
						 var record = grid.getSelectionModel().getLastSelected();
						 var linenum = grid.store.indexOf(record);
						 grid.store.reload();
						 grid.getSelectionModel().select(linenum,true); 
					 }
					 
					 if("add" == saveFlag){
						 console.log('213124124');
						 CFControl.setToolBarStatus(tbarid,true);
						 CFControl.setFormReadOnly(formid,true);
						 grid.store.reload();
						 //将detailForm的值付给store
					 }
					 grid.setDisabled(false);
				 }, 
				 failure:function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 Ext.MessageBox.alert("错误", obj.retMessage);
				 }
			 });
		},
		/**
		 * 向后台提交请求
		 * 参数1为 请求url
		 * 参数2,参数3,参数4为参数对象
		 */
		submitAjaxRequestNew2:function(url,param1,param2,param3){
			Ext.Ajax.request({
				 url:url,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:{param1:param1,param2:param2,param3:param3}, // 提交参数
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
		 * 向后台提交请求
		 * 参数1为 请求url
		 * 参数2,参数3,参数4,参数5,参数6为参数对象
		 */
		submitAjaxRequestNew3:function(url,param1,param2,param3,param4,param5){
			Ext.Ajax.request({
				 url:url,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:{param1:param1,param2:param2,param3:param3,param4:param4,param5:param5}, // 提交参数
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
			if(value==null ){
				return '';
			}
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
		 * 格式化时间
		 * 6位的转成hh:mm:ss
		 * @param value
		 * @returns
		 */
		formatTime:function(value){
			if(value==null ){
				return '';
			}
			if(value.length==6){
				value = Ext.String.insert(value, ":", -4);
				value = Ext.String.insert(value, ":", -2);
				return value;
			} else {
				return value;
			}	
				
		},
		
		/**
		 * 将数值四舍五入(保留2位小数)后格式化成金额形式
		 *
		 * @param num 数值(Number或者String)
		 * @return 金额格式的字符串,如'1,234,567.45'
		 * @type String
		 */
		formatCurrency:function(num){
			num = num.toString().replace(/\$|\,/g,'');
		    if(isNaN(num))
		    num = "0";
		    sign = (num == (num = Math.abs(num)));
		    num = Math.floor(num*100+0.50000000001);
		    cents = num%100;
		    num = Math.floor(num/100).toString();
		    if(cents<10)
		    cents = "0" + cents;
		    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		    num = num.substring(0,num.length-(4*i+3))+','+
		    num.substring(num.length-(4*i+3));
		    return (((sign)?'':'-') + num + '.' + cents);
		},
		formatCurrency2:function(num){
			num = num/100;
			num = num.toString().replace(/\$|\,/g,'');
		    if(isNaN(num))
		    num = "0";
		    sign = (num == (num = Math.abs(num)));
		    num = Math.floor(num*100+0.50000000001);
		    cents = num%100;
		    num = Math.floor(num/100).toString();
		    if(cents<10)
		    cents = "0" + cents;
		    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		    num = num.substring(0,num.length-(4*i+3))+','+
		    num.substring(num.length-(4*i+3));
		    return (((sign)?'':'-') + num + '.' + cents);
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
		},
		isShowButton:function(bbar,key){
			var el = Ext.getCmp(bbar);
			showButtons = Ext.decode(showButtons);
			var keyButtons = showButtons[key];
			console.log(showButtons);
			console.log(keyButtons);
			el.items.each(function(item,index,length){  
				if('button'==item.xtype){
					if(keyButtons.indexOf(item.id)>-1){
						item.show();
					}else{
						item.hide();
						//隐藏分割符
						if(item.previousSibling()&&'tbseparator' == item.previousSibling().xtype){
							item.previousSibling().hide();
						}else if(item.nextSibling()&&'tbseparator' == item.nextSibling().xtype){
							item.nextSibling().hide();
						}
					}
				}
			});
		},
		modDataLocked:function(keyCode,keyDesc,sqlMap,sqlKey){
			 var isMod = false;
			 Ext.Ajax.request({
	            url: "commonAction!modDataLocked",    
	            method: "POST",
	            async: false, 
	            params: {"KEY_CODE":keyCode,"KEY_DESC":keyDesc,"SQL_MAP":sqlMap,"SQL_KEY":sqlKey},
	            success: function(response, opts) {
	            	 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 isMod = true;
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }
	            }, 
	            failure: function() { 
	            	Ext.MessageBox.alert("错误", "系统异常请联系管理员");
	            } 
	        });
			return isMod;
		},
		delDataLocked:function(keyCode,keyDesc,sqlMap,sqlKey){
			 var isDel = false;
			 Ext.Ajax.request({
	            url: "commonAction!delDataLocked",    
	            method: "POST",
	            async: false, 
	            params: {"KEY_CODE":keyCode,"KEY_DESC":keyDesc,"SQL_MAP":sqlMap,"SQL_KEY":sqlKey},
	            success: function(response, opts) {
	            	 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 isDel = true;
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }
	            }, 
	            failure: function() { 
	            	Ext.MessageBox.alert("错误", "系统异常请联系管理员");
	            } 
	        });
			return isDel;
		},
		checkDataLocked:function(keyCode,keyDesc,sqlMap,sqlKey){
			 var isCheck = false;
			 Ext.Ajax.request({
	            url: "commonAction!checkDataLocked",    
	            method: "POST",
	            async: false, 
	            params: {"KEY_CODE":keyCode,"KEY_DESC":keyDesc,"SQL_MAP":sqlMap,"SQL_KEY":sqlKey},
	            success: function(response, opts) {
	            	 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 isCheck = true;
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }
	            }, 
	            failure: function() { 
	            	Ext.MessageBox.alert("错误", "系统异常请联系管理员");
	            } 
	        });
			 return isCheck;
		},
		unLockData:function(keyCode,keyDesc){
			 var isSuccess = false;
			 Ext.Ajax.request({
	            url: "commonAction!unLockData",    
	            method: "POST",
	            async: false, 
	            params: {"KEY_CODE":keyCode},
	            success: function(response, opts) {
	            	 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 isSuccess = true;
					 }else{
						 Ext.MessageBox.alert("错误", obj.retMessage);
					 }
	            }, 
	            failure: function() { 
	            	Ext.MessageBox.alert("错误", "系统异常请联系管理员");
	            } 
	        });
			 return isSuccess;
		},
		/**
		 * 向后台提交同步请求
		 * 参数1为
		 * 参数2为参数对象
		 */
	    syncAjaxRequest:function(url,params){
	    	 var obj = {};
			 Ext.Ajax.request({
				 url:url,
				 method:'POST',
				 params:params,
				 async: false, 
				 success: function(response, options){
					 obj = Ext.JSON.decode(response.responseText);
				 }, 
				 failure:function(response, options){
					 obj.success = false;
					 obj.retMessage = "系统异常请联系管理员";
				 }
			 });
			 return obj;
		}
    });
	/**
	 * 必填项加红色的*
	 */
	Ext.override(Ext.form.field.Base,{     
		initComponent:function(){         
			if(this.allowBlank!==undefined && !this.allowBlank){ 
				if(this.fieldLabel){ 
					this.fieldLabel += '<font style="color:red;font-size:18px;vertical-align:middle;">*</font>';  
					}         
				}         
			this.callParent(arguments);     
			} 
	});
	Ext.override(Ext.container.Container,{     
		//针对form中的容器组件     
		initComponent:function(){         
			if(this.allowBlank!==undefined && !this.allowBlank){  
				if(this.fieldLabel){                 
					this.fieldLabel += '<font style="color:red;font-size:18px;vertical-align:middle">*</font>'; 
				}         
			} 
			this.callParent(arguments);     
		} 
	});
	//设置store默认分页
	Ext.override(Ext.data.Store,{     
		pageSize:20
	});
	Ext.Ajax.on('requestcomplete',checkUserSessionStatus, this);
	function checkUserSessionStatus(conn,response,options){
	    //Ext重新封装了response对象
		if(response.responseXML == null){
			 if(response.getResponseHeader("sessionstatus")=="timeOut"){
			        //发现请求超时，退出处理代码...
			    	window.top.location.href = App.basePath + "/tologin";
			 }
		}
	   
	}
}());
Ext.ux.ToolbarKeyMap = Ext.extend(Object, (function() {
    var kb,
        owner,
        mappings;
 
    function addKeyBinding(c) {
        if (kb = c.keyBinding) {
            delete c.keyBinding;
            if (!kb.fn && c.handler) {
                kb.fn = function(k, e) {
                    e.preventDefault();
                    e.stopEvent();
                    //loadMenu(c.data);
                    c.handler.call(c.scope, c, e);
                }
            }
            mappings.push(kb);
            var t = [];
            if (kb.ctrl) t.push('Ctrl');
            if (kb.alt) t.push('Alt');
            if (kb.shift) t.push('Shift');
            t.push(kb.key.toUpperCase());
            c.hotKey = t.join('+');
            c.showHotKey = kb.showHotKey;
            if (c instanceof Ext.menu.Item) {
			    c.onRender = Ext.Function.createSequence(c.onRender,addMenuItemHotKey);
            } else if ((c instanceof Ext.Button) && (c.showHotKey)) {
                c.onRender = Ext.Function.createSequence(c.onRender,addButtonHotKey);
            }
        }
        if ((c instanceof Ext.Button) && c.menu) {
            c.menu.cascade(addKeyBinding);
        }
    }
 
    function findKeyNavs() {
        delete this.onRender;
        if (owner = this.ownerCt) {
            mappings = [];
            this.cascade(addKeyBinding);
            if (!owner.menuKeyMap) {
                //owner.menuKeyMap = new Ext.KeyMap(owner.el, mappings);
                var target = document;
                if((mappings[0].global!=null)&&(mappings[0].global==false))
                {
                    target=owner.el;
                }
                owner.menuKeyMap = new Ext.KeyMap(target, mappings);
                owner.el.dom.tabIndex = 0;
            } else {
                owner.menuKeyMap.addBinding(mappings);
            }
        }
    }
 
    function addMenuItemHotKey() {
        delete this.onRender;
        if((this.showHotKey!=null)&&(this.showHotKey == false))
           return;
 
        this.el.child('.x-menu-item-link').setStyle({
            overflow: 'hidden',
            zoom: 1
        });
 
        this.el.child('.x-menu-item-link').child('.x-menu-item-text').setStyle({
                  'float': 'left'
                  });
 
        this.el.child('.x-menu-item-link').createChild({
            style: {
                padding: '0px 0px 0px 15px',
                float: 'right',
            },
            html: this.hotKey
        });
    }
 
    function addButtonHotKey() {
        delete this.onRender;
        if((this.showHotKey!=null)&&(this.showHotKey == false))
           return;
        var p = this.btnEl.up('');
        p.setStyle({
            overflow: 'hidden',
            zoom: 1
        });
		if(p.up('td')!=null)
           p.up('td').setStyle('text-align', 'left');
        this.btnEl.setStyle('.x-menu-item-text').setStyle({
            'float': 'left'
        });
        p = p.createChild({
                style: {
                padding: '0px 0px 0px 15px',
                float: 'right',
                position: 'relative',
                bottom: Ext.isWebKit ? '-1px' : '-2px'
            },
            html: this.hotKey
        });
    }
 
    return {
        init: function(toolbar) {
		 	toolbar.onRender = Ext.Function.createSequence(toolbar.onRender,findKeyNavs);
			toolbar.doLayout = Ext.Function.createSequence(toolbar.doLayout,findKeyNavs);
 
        }
    }
})());
