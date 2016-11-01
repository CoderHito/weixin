var CFControlWin = CFControlWin || {};

(function() {		
	Ext.apply(CFControlWin,{
		/******begin**********弹出窗体处理************************************************8**************/
		/**
		 * 查询处理
		 */
		type2QueryHandler:function(store,formid,gridid,tbarid){
			store.load({
				params:CFControlWin.getQueryParamsForTopWindow(formid),
				callback:function(r,options,success){
					var text =  store.getProxy().getReader().rawData ;
					if(success){
						var dg = top.Ext.getCmp(gridid);
						dg.getSelectionModel().select(0,true);
						if(text.total==0){
							top.Ext.MessageBox.alert("提示", text.retMessage);
						}
		    		}else{
		    			top.Ext.MessageBox.alert("错误", text.retMessage);
		    		};
		       },
			scope:this
			});
		saveFlag = "";
		return saveFlag;
		},
		
		/**
		 * 新增处理
		 * 
		 */
		type2AddHandler:function(wingrid,detailWin,detailformIid,modelid){
			tg = Ext.getCmp(wingrid);
			var record = Ext.create(modelid);
			record.set('GUID',CFControl.genGuid());
			detailWin.oldmodel = record;
			detailWin.newmodel = record;
			var detailForm = detailWin.child('#'+detailformIid);
			detailForm.loadRecord(detailWin.newmodel);
			detailWin.operation='add';
			detailWin.center();
			detailWin.show(this);
			detailWin.on('hide',fn =function(){
				if ("save" == detailWin.ret){
					 //处理主窗体控制
				 }
				detailForm.form.reset();
				detailWin.ret = '';		
				detailWin.un('hide',fn);
			});
		},
		
		
		/**
		 * 修改处理
		 * 
		 */
		type2ModHandler:function(url,wingrid,detailWin,detailformIid,modelid){
			tg = Ext.getCmp(wingrid);
			var tmpmodel = tg.getSelectionModel().getSelection()[0];
			//console.log(tmpmodel);
			
			if(tmpmodel){
			//tg.store.load({ id: tmpmodel.getId(), addRecords: true });
			Ext.getClass(tmpmodel).setProxy(new Ext.data.HttpProxy( {
		        url : url,
		        reader: {
		            type: 'json',
		            root: 'dataList',
		            totalProperty: 'total'
		        }
		    }));
			Ext.getClass(tmpmodel).load(tmpmodel.getId(), { 
				success: function(r,operation) {
					var obj = Ext.JSON.decode(operation.response.responseText);
					if(obj.success){
						var detailForm = detailWin.child('#'+detailformIid);
						detailForm.loadRecord(r); 
						detailWin.oldmodel = r;
						detailWin.operation='mod';
						detailWin.keyfields={};
						detailWin.center();
						detailWin.show(this);
						detailWin.on('hide',fn =function(){
							if ("save" == detailWin.ret){
								//处理主窗体控制
								Ext.getClass(tmpmodel).load(tmpmodel.getId());
							}
							detailForm.form.reset();
							detailWin.ret = '';		
							detailWin.un('hide',fn);
					});
					}else{
						Ext.MessageBox.alert("提示", obj.retMessage);
					}
				},
				failure:function(r,operation){
					Ext.MessageBox.alert("错误", "后台处理异常");
				}});
			}else{
				Ext.MessageBox.alert("提示", "没有需要修改的记录");
			}
			
		},
		
		/**
		 * 保存处理
		 * 
		 */
		type2SaveHandler:function(detailWin,formid){
			var df = Ext.getCmp(formid)||top.Ext.getCmp(formid);//==
			if (df.form.isValid()){
				//拼接参数
				if('save'==detailWin.ret){
					var params = CFControlWin.getSubmitParamsForWindow(formid,detailWin.operation);
					var tmp = CFControlWin.getSubmitParamsForWindowForDataLog(formid,detailWin.oldmodel,detailWin.operation);
					CFControl.extend(params,tmp);
					if(detailWin.keyfields){
						CFControl.extend(params,detailWin.keyfields);
					}
					CFControlWin.submitAjaxRequest2(detailWin,params,"");
				}
			}else{
				top.Ext.MessageBox.alert("提示","输入有误，无法提交！");
			}
		},
		/**
		 * 重置方法 查询条件、查询结果、明细置为空
		 */
		type2ResetHandler:function(queryformid,gridid,tbarid,pbarid,detailformid){
			if(queryformid){
				var qrForm = top.Ext.getCmp(queryformid); 
				qrForm.form.reset();
			}
			if(gridid){
				var grid = top.Ext.getCmp(gridid);
				grid.store.removeAll();
			}
			if(pbarid){
				var pbar = top.Ext.getCmp(pbarid);
				pbar.currentPage = 0;
			}
			if(tbarid){
				CFControlWin.setToolBarStatusForWin(tbarid,true);
			}
			if(detailformid){
				var detailForm = top.Ext.getCmp(detailformid);
				CFControlWin.setFormReadOnlyForWin(detailformid,true);
				detailForm.form.reset();
			}
		},
		
		/**
		 * 向后台提交请求
		 * 参数1为
		 * 参数2为参数对象
		 */
		submitAjaxRequest2:function(detailWin,params,saveFlag){
//			var myMask = new top.Ext.LoadMask(detailWin, {  
//                msg: '正在处理数据，请稍候',  
//                removeMask: true //完成后移除  
//            });  
//            myMask.show();
			 Ext.Ajax.request({
				 url:detailWin.submiturl,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:params, // 提交参数
				 success: function(response, options){
//					 myMask.destroy();
					 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 top.Ext.MessageBox.alert("提示", obj.retMessage);
						 detailWin.close();
					 }else{
						 top.Ext.MessageBox.alert("错误", obj.retMessage);
					 }
				 }, 
				 failure:function(response, options){
//					 myMask.destroy();
					 var obj = Ext.JSON.decode(response.responseText);
					 top.Ext.MessageBox.alert("错误", obj.retMessage);
				 }
			 });
		},		/**
		 * 用于反馈录入页面 取消/返回按钮的 解锁
		 * 向后台提交请求
		 * 参数1为
		 * 参数2为参数对象
		 */
		submitAjaxRequest3:function(detailWin,params,saveFlag){
			 Ext.Ajax.request({
				 url:detailWin.submiturl,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:params, // 提交参数
			 });
		},
		/**
		 * queryForm里面查询条件拼接，目前只支持combo,textfield,
		 * numberfield,datefield,triggerfield
		 * 参数1为form的id
		 * 控件为textfield时，控件名称为tf_qry_字段名称
		 * 控件为combo时，控件名称为tf_qry_字段名称
		 * 控件为numberfield时，控件名称为nf_qry_字段名称
		 * 控件为datefield时，控件名称为df_qry_字段名称,8位日期
		 * 控件为datefield时，控件名称为dt_qry_字段名称,14位时间
		 * 控件为triggerfield时，控件名称为tg_qry_字段名称
		 * 控件为checkbox时，控件名称为ck_qry_字段名称
		 */
		getQueryParamsForTopWindow : function(formid) {
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
			});
				
				
			var params = '{}';
			if (tmp.length>0){
				tmp = tmp.substring(1);
				eval('params={'+tmp+'}');
			}
			return params;
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
		getSubmitParamsForWindow : function(formid,saveFlag) {
			var el = Ext.getCmp(formid)||top.Ext.getCmp(formid);//==
			var tmp = '';
			//console.log(el);
			//console.log(el.items);
			el.items.each(function(item,index,length){ 
				if('panel'==item.xtype){
					tmp = tmp + ','+ CFControlWin.getParamsInSubForm(item);
				}
                if('fieldset'==item.xtype){// && !(item.getValue()===null)
                    tmp = tmp + ','+ CFControlWin.getParamsInSubForm(item);
                }
				if('textfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
				if('textarea'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
				if('combo'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
				if('numberfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
				if('datefield'==item.xtype && !(item.getValue()===null)){
					if('df_'==item.id.substring(0,3)){
						tmp = tmp + ','+item.id.substring(3) + ':\''+Ext.util.Format.date(item.getValue(),'Ymd')+'\'';
					}
					if('dt_'==item.id.substring(0,3)){
						tmp = tmp + ','+item.id.substring(3) + ':\''+Ext.util.Format.date(item.getValue(),'YmdHis')+'\'';
					}
				}
				if('triggerfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
				if('checkbox'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
				if('textareafield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
                if('radio'==item.xtype && !(item.getValue()===null)){
                    tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
                }
                if('radiogroup'==item.xtype && !(item.getChecked()[0]===null)){
                	tmp = tmp + ','+item.id.substring(3) + ':\''+item.getChecked()[0].inputValue+'\'';
                }
			});
				
			var params = '{}';
			if (tmp.length>0){
				tmp = 'saveFlag'+ ':\''+saveFlag+'\''+tmp;
				eval('params={'+tmp+'}');
			}
			return params;
			
		},
		getParamsInSubForm : function(el){
			var tmp = '';
			el.items.each(function(item,index,length){ 
				if('panel'==item.xtype){// && !(item.getValue()===null)
					tmp = tmp + ','+ CFControlWin.getParamsInSubForm(item);
				}
				if('textfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
				if('textarea'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
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
					if('dt_'==item.id.substring(0,3) && !(item.getValue()===null)){
						tmp = tmp + ','+item.id.substring(3) + ':"'+Ext.util.Format.date(item.getValue(),'YmdHis')+'"';
					}
				}
				if('triggerfield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
				if('checkbox'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':"'+item.getValue()+'"';
				}
                if('radio'==item.xtype && !(item.getValue()===null)){                	
                    tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
                }
				if('textareafield'==item.xtype && !(item.getValue()===null)){
					tmp = tmp + ','+item.id.substring(3) + ':\''+item.getValue()+'\'';
				}
				if('radiogroup'==item.xtype && !(item.getChecked()[0]===null)){
                	tmp = tmp + ','+item.id.substring(3) + ':\''+item.getChecked()[0].inputValue+'\'';
                }
			});
			return tmp.substring(1);
		},
		
		/**
		 * detailForm里面提交参数拼接，用于数据日志目前只支持textfield,combo,numberfield,
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
		getSubmitParamsForWindowForDataLog : function(formid,oldmodel,saveFlag) {
			var el = Ext.getCmp(formid)||top.Ext.getCmp(formid);//==
			var oldValue = '';
			var newValue = '';
			el.items.each(function(item,index,length){ 
				if('panel'==item.xtype){
					var tmp = CFControlWin.getParamsInSubFormForDataLog(item,oldmodel);
					oldValue = oldValue + ','+ tmp.oldValue+'\n';
					newValue = newValue + ','+ tmp.newValue+'\n';
					//newValue = newValue + ','+ CFControl.getParamsInSubForm(item,detailWin.oldmodel);
				}
				if('textfield'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('combo'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('numberfield'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('datefield'==item.xtype){
					if('df_'==item.id.substring(0,3)){
						oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
						newValue = newValue + ','+item.fieldLabel + ':"'+Ext.util.Format.date(item.getValue(),'Ymd')+'"'+'\n';
					}
					if('dt_'==item.id.substring(0,3)){
						oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
						newValue = newValue + ','+item.fieldLabel + ':"'+Ext.util.Format.date(item.getValue(),'YmdHis')+'"'+'\n';
					}
				}
				if('triggerfield'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('checkbox'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
			});
				
			
			var params = {'oldValue':oldValue,'newValue':newValue};
			
			return params;
			
		},
		getParamsInSubFormForDataLog : function(el,oldmodel){
			var oldValue = '';
			var newValue = '';
			el.items.each(function(item,index,length){ 
				if('panel'==item.xtype){
					var tmp = CFControlWin.getParamsInSubForm(item,detailWin.oldmodel);
					oldValue = oldValue + ','+ tmp.oldValue;
					newValue = newValue + ','+ tmp.newValue;
				}
				if('textfield'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('combo'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('numberfield'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('datefield'==item.xtype){
					if('df_'==item.id.substring(0,3)){
						oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
						newValue = newValue + ','+item.fieldLabel + ':"'+Ext.util.Format.date(item.getValue(),'Ymd')+'"'+'\n';
					}
					if('dt_'==item.id.substring(0,3)){
						oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
						newValue = newValue + ','+item.fieldLabel + ':"'+Ext.util.Format.date(item.getValue(),'YmdHis')+'"'+'\n';
					}
				}
				if('triggerfield'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
				if('checkbox'==item.xtype){
					oldValue = oldValue + ','+item.fieldLabel + ':"'+oldmodel.get(item.id.substring(3))+'"'+'\n';
					newValue = newValue + ','+item.fieldLabel + ':"'+item.getValue()+'"'+'\n';
				}
			});
			var params = {'oldValue':oldValue.substring(1),'newValue':newValue.substring(1)};
			return params;
		},
		/**
		 * 控制form里面所有控件只读属性，目前只支持textfield,combo,numberfield,
		 * datefield,triggerfield,checkbox
		 * 参数1为form的id
		 * 参数2为是否只读 
		 */
		setFormReadOnlyForWin : function(formid,readlOnly) {
			var el = top.Ext.getCmp(formid);
			el.items.each(function(item,index,length){ 
				if('panel'==item.xtype){
					CFControlWin.setFormReadOnlyForWin(item.id,readlOnly);
				}
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
		setFormReadOnlyForModForWin : function(formid,ModCtlArray) {
			var el = top.Ext.getCmp(formid);
			el.items.each(function(item,index,length){
				if('panel'==item.xtype){
					CFControlWin.setFormReadOnlyForWin(item.id,false);
				}
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
				var item = top.Ext.getCmp(ModCtlArray[i]);
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
		 * 修改时控制form里面所有控件只读属性，目前只支持textfield,combo,numberfield,
		 * datefield,triggerfield,checkbox
		 * 参数1为form的id
		 * 参数2为具有可写属性的控件组成的数组 
		 */
		setFormReadOnlyForModForWin2 : function(formid,ModCtlArray) {
			var el = top.Ext.getCmp(formid);
			el.items.each(function(item,index,length){
				if('panel'==item.xtype){
					CFControlWin.setFormReadOnlyForWin(item.id,true);
				}
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
			});
			for(var i=0;i<ModCtlArray.length;i++){
				var item = top.Ext.getCmp(ModCtlArray[i]);
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
			}  
		},
		/**
		 * 控制ToolBar里面所有控件是否可用，目前只支持btton
		 * 参数1为form的id
		 * 参数2为是否只读 
		 * 设置保存状态属性
		 */
		setToolBarStatusForWin : function(toolbarid,disable) {
			var el = top.Ext.getCmp(toolbarid);
			el.items.each(function(item,index,length){                           
				if('button'==item.xtype)
					if("btn_query_win"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_add_win"==item.id){
						item.setDisabled(!disable);
					}
					if("btn_addsub_win"==item.id){
						item.setDisabled(!disable);
					}
					if("btn_mod_win"==item.id){
						item.setDisabled(!disable); 
					}
					if("btn_save_win"==item.id){
						item.setDisabled(disable); 
					}
					if("btn_del_win"==item.id){
						item.setDisabled(!disable); 
					}
					
			});
		},
		/**
		 * 新增处理
		 * 
		 */
		type1AddHandlerForWin:function(saveFlag,modelid,detailformid,gridid,tbarid){
			var record = top.Ext.create(modelid);
			var dg = top.Ext.getCmp(gridid);
			dg.setDisabled(true);
            dg.store.insert(0,record);
			dg.getSelectionModel().select(0,true);
			CFControlWin.setFormReadOnlyForWin(detailformid,false);
			var df = top.Ext.getCmp(detailformid);
			df.form.reset();
			df.loadRecord(record);
			CFControlWin.setToolBarStatusForWin(tbarid,false);
			saveFlag = "add";
			return saveFlag;
		},
		/**
		 * 修改处理
		 * 
		 */
		type1ModHandlerForWin:function(saveFlag,formid,gridid,tbarid,ModCtlArray){
			var tg = top.Ext.getCmp(gridid);
			var tmpmodel = tg.getSelectionModel().getSelection()[0];
			if(tmpmodel){
				tg.setDisabled(true);
				CFControlWin.setToolBarStatusForWin(tbarid,false);
				CFControlWin.setFormReadOnlyForModForWin(formid,ModCtlArray);
				saveFlag = "mod";
			}else{
				top.Ext.MessageBox.alert("提示", "没有需要修改的记录");
			}
			return saveFlag;
		},
		/**
		 * 验证表达式
		 */
		type1CheckExpForWin:function(url,formid,gridid){
			var tg = top.Ext.getCmp(gridid);
			var tmpmodel = tg.getSelectionModel().getSelection()[0];
			if(tmpmodel){
				CFControlWin.setFormReadOnlyForWin(formid, true);
				CFControlWin.submitAjaxRequestForWin(url,
						CFControlWin.getSubmitParamsForWindow(formid,""),"",formid,gridid,"");
			}else{
				top.Ext.MessageBox.alert("提示","没有需要验证的记录");
			}
			
			
		},
		/**
		 * 删除处理
		 * 
		 */
		type1DelHandlerForWin:function(saveFlag,url,formid,gridid,guid){
			var tg = top.Ext.getCmp(gridid);
			var tmpmodel = tg.getSelectionModel().getSelection()[0];
			if(tmpmodel&&guid){
				saveFlag = "del";
			
				CFControlWin.submitAjaxRequestForWin(url,
						CFControlWin.getSubmitParamsForWinAndGuid(formid,saveFlag,guid),saveFlag,formid,gridid,"");
			}else{
				top.Ext.MessageBox.alert("提示", "没有需要删除的记录");
			}
			return saveFlag;
		},
		/**
		 * 保存处理
		 * 
		 */
		type1SaveHandlerForWin:function(saveFlag,url,detailformid,gridid,tbarid,guid){
			var df = top.Ext.getCmp(detailformid);
			if (df.form.isValid()&&guid){
				CFControlWin.submitAjaxRequestForWin(url,
						CFControlWin.getSubmitParamsForWinAndGuid(detailformid,saveFlag,guid),saveFlag,detailformid,gridid,tbarid);
			}else{
				top.Ext.MessageBox.alert("提示","输入有误，无法提交！");
			}
			return saveFlag;
		},
		/**
		 * 取消处理
		 * 
		 */
		type1CancelHandlerForWin:function(saveFlag,formid,gridid,tbarid){
			var dg = top.Ext.getCmp(gridid);
			if("add" == saveFlag){
				dg.getSelectionModel().select(1,true);
//				if(dg.store.getCount()==1){//为什么需要这几行
//					dg.store.removeAt(0);
//				}
			}else{
				saveFlag="";
				CFControlWin.setToolBarStatusForWin(tbarid,true);
				CFControlWin.setFormReadOnlyForWin(formid,true);
			}
			dg.setDisabled(false);
			return saveFlag; 
		}, /**
		 * 向后台提交请求
		 * 参数1为
		 * 参数2为参数对象
		 */
		submitAjaxRequestForWin:function(url,params,saveFlag,formid,gridid,tbarid){
			 Ext.Ajax.request({
				 url:url,// 要跳转的url,此为属性必须要有
				 method:'POST',
				 params:params, // 提交参数
				 success: function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 if(obj.success){
						 top.Ext.MessageBox.alert("提示", obj.retMessage);
						 var df = top.Ext.getCmp(formid);
						 var grid = top.Ext.getCmp(gridid);
						 if ("del" == saveFlag){
							 var record = grid.getSelectionModel().getLastSelected();//获取选中的第一条记录,返回record类型
							 grid.store.remove(record);//参数是record类型的,移除该数据
							 df.form.reset();
							 grid.getSelectionModel().select(0,true);
						 }
						 if("mod" == saveFlag){
							 CFControlWin.setToolBarStatusForWin(tbarid,true);
							 CFControlWin.setFormReadOnlyForWin(formid,true);
							 var record = grid.getSelectionModel().getLastSelected();
							 var linenum = grid.store.indexOf(record);
							 grid.store.reload();
							 grid.getSelectionModel().select(linenum,true); 
						 }
						 
						 if("add" == saveFlag){
							 CFControlWin.setToolBarStatusForWin(tbarid,true);
							 CFControlWin.setFormReadOnlyForWin(formid,true);
							 grid.store.reload();
							 //将detailForm的值付给store
						 }
						 grid.setDisabled(false);
					 }else{
						 top.Ext.MessageBox.alert("错误", obj.retMessage);
					 }
				 }, 
				 failure:function(response, options){
					 var obj = Ext.JSON.decode(response.responseText);
					 top.Ext.MessageBox.alert("错误", obj.retMessage);
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
		getSubmitParamsForWinAndGuid : function(formid,saveFlag,guid) {
			var el = top.Ext.getCmp(formid);
			var tmp = '';
			//console.log(el);
			//console.log(el.items);
			el.items.each(function(item,index,length){ 
				if('panel'==item.xtype){
					tmp = tmp + ','+ CFControlWin.getParamsInSubForm(item);
				}
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
			if(guid){
				tmp = tmp + ',GUID:"'+guid+'"';
			}
				
				
			var params = '{}';
			if (tmp.length>0){
				tmp = 'saveFlag'+ ':"'+saveFlag+'"'+tmp;
				eval('params={'+tmp+'}');
			}
			return params;
			
		},
		/**
		 * 修改处理 含有ueditor
		 * @param url
		 * @param wingrid
		 * @param detailWin
		 * @param detailformIid
		 * @param modelid
		 */
		modHandler : function (url,wingrid,detailWin,detailformIid,modelid,ueditorid,logoid){
			tg = Ext.getCmp(wingrid);
			var tmpmodel = tg.getSelectionModel().getSelection()[0];
			//console.log(tmpmodel);
			
			if(tmpmodel){
			//tg.store.load({ id: tmpmodel.getId(), addRecords: true });
			Ext.getClass(tmpmodel).setProxy(new Ext.data.HttpProxy( {
		        url : url,
		        reader: {
		            type: 'json',
		            root: 'dataList',
		            totalProperty: 'total'
		        }
		    }));
			Ext.getClass(tmpmodel).load(tmpmodel.getId(), { 
				success: function(r,operation) {
					var obj = Ext.JSON.decode(operation.response.responseText);
					if(obj.success){
						var detailForm = detailWin.child('#'+detailformIid);
						detailForm.loadRecord(r); 
						detailWin.topic_text = r.data.TOPIC_TEXT;
						detailWin.logo_src = r.data.LOGO;
						detailWin.oldmodel = r;
						detailWin.operation='mod';
						detailWin.keyfields={};
						detailWin.center();
						detailWin.show(this);
						detailWin.on('hide',fn =function(){
							if ("save" == detailWin.ret){
								//处理主窗体控制
								Ext.getClass(tmpmodel).load(tmpmodel.getId());
							}
							detailForm.form.reset();
							detailWin.ret = '';		
							detailWin.un('hide',fn);
					});
					}else{
						Ext.MessageBox.alert("提示", obj.retMessage);
					}
				},
				failure:function(r,operation){
					Ext.MessageBox.alert("错误", "后台处理异常");
				}});
			}else{
				Ext.MessageBox.alert("提示", "没有需要修改的记录");
			}
			
		}
		
    });
	
}());