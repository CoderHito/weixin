Ext.define('CFrame.common.CFUploadPanel', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.CFUploadPanel',
	requires : [  ],
	
	store : Ext.create('Ext.data.JsonStore',{
    	autoLoad : false,
    	fields : ['id','name','type','size','percent','status','isexists','filename'],
    	storeId: 'name'
    }),
    
    
    savePath:'upload',
	queryBtnText : '检查',
	addFileBtnText : '选择文件',
	uploadBtnText : '上传',
	removeBtnText : '清空文件',
	cancelBtnText : '取消上传',
	debug : false,
	file_size_limit : 100,//MB
	file_types : '*.*',
	file_types_description : 'All Files',
	file_upload_limit : 50,
	file_queue_limit : 0,
	post_params : {},
	flash_url : "swfupload/swfupload.swf",
	flash9_url : "swfupload/swfupload_fp9.swf",
	
	initComponent : function() {
		var me = this;
		this.ensureDefault = function (settingName, defaultValue) {
			var setting = userSettings[settingName];
			if (setting != undefined) {
				this.settings[settingName] = setting;
			} else {
				this.settings[settingName] = defaultValue;
			}
		};
		
		Ext.applyIf(me, {
			tbar : {
				//id : 'tbar',
				items : [ {
					text : this.queryBtnText,
					iconCls : 'tbar_queryIcon',
					id : 'btn_query',
					handler : this.onQuery
				},'-', { 
			        xtype:'button',
			        itemId: 'btn_add',
			        iconCls : 'fileupload_add',
			        id : '_btn_for_swf_',
			        text : this.addFileBtnText
		        },'-',{
		        	xtype : 'button',
		        	itemId : 'btn_upload',
		        	iconCls : 'fileupload_up',
		        	text : this.uploadBtnText,
		        	scope : this,
		        	handler : this.onUpload
		        },'-',{
		        	xtype : 'button',
		        	itemId : 'btn_remove',
		        	iconCls : 'fileupload_trash',
		        	text : this.removeBtnText,
		        	scope : this,
		        	handler : this.onRemove
		        },'-',{
		        	xtype : 'button',
		        	itemId : 'btn_cancel',
		        	iconCls : 'fileupload_cancel',
		        	disabled : true,
		        	text : this.cancelBtnText,
		        	scope : this,
		        	handler : this.onCancelUpload
		        }]
			},columns : [
			               {xtype: 'rownumberer'},
			          		{text: '文件名', width: 220,dataIndex: 'name'},
			          		{text: '是否已上传', width: 100,dataIndex: 'isexists',renderer:function(v){
			          			var status;
			          			if(v==1){
			          				status = "已上传";
			          			}else{ 
			          				status =  "未上传";
			          			}		
			          			return status;
			          			}
			          		},
			          		{text: '本地文件名', width: 270,dataIndex: 'filename'},
			                  {text: '类型', width: 70,dataIndex: 'type'},
			                  {text: '大小', width: 70,dataIndex: 'size',renderer:function(v){
			                  	return Ext.util.Format.fileSize(v);
			                  }},
			                  {text: '进度', width: 130,dataIndex: 'percent',renderer:function(v){        	
			          			var stml =
			          				'<div>'+
			          					'<div style="border:1px solid #008000;height:10px;width:115px;margin:2px 0px 1px 0px;float:left;">'+		
			          						'<div style="float:left;background:#FFCC66;width:'+v+'%;height:8px;"><div></div></div>'+
			          					'</div>'+
			          			'</div>';
			          			return stml;
			                  }},
			                  {text: '状态', width: 80,dataIndex: 'status',renderer:function(v){
			          			var status;
			          			if(v==-1){
			          				status = "等待上传";
			          			}else if(v==-2){
			          				status =  "上传中...";
			          			}else if(v==-3){
			          				status =  "<div style='color:red;'>上传失败</div>";
			          			}else if(v==-4){
			          				status =  "上传成功";
			          			}else if(v==-5){
			          				status =  "停止上传";
			          			}		
			          			return status;
			          		}}
//			                  , {
//			               	   xtype:'actioncolumn',
//			               	   text:'下载',
//			                      width:50,
//			                      items: [{
//			                   	   iconCls: 'fileupload_down',
//			                       tooltip: 'download',
//			                       handler: this.onDownLoad, 
//			                      }]
//			                  }
			              ],
		});
		me.callParent(arguments);
		this.down('button[itemId=btn_add]').on({			
			afterrender : function(btn){
				var config = this.getSWFConfig(btn);		
				this.swfupload = new SWFUpload(config);
				Ext.get(this.swfupload.movieName).setStyle({
					position : 'absolute',
					top : 0,
					left : -2
				});	
			},
			scope : this,
			buffer:300
		});
	},
	getSWFConfig : function(btn){
		var me = this;
		var placeHolderId = Ext.id();
		var em = btn.getEl().child('em');
		if(em==null){
			em = Ext.get(btn.getId()+'-btnWrap');
		}		
		em.setStyle({
			position : 'relative',
			display : 'block'
		});
		em.createChild({
			tag : 'div',
			id : placeHolderId
		});
		return {
			debug: me.debug,
			flash_url : me.flash_url,
			flash9_url : me.flash9_url,	
			upload_url: me.upload_url,
			post_params: me.post_params||{savePath:me.savePath},
			file_size_limit : (me.file_size_limit*1024),
			use_query_string : true,
			file_types : me.file_types,
			file_types_description : me.file_types_description,
			file_upload_limit : me.file_upload_limit,
			file_queue_limit : me.file_queue_limit,
			button_width: em.getWidth(),
			button_height: em.getHeight(),
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			button_placeholder_id: placeHolderId,
			custom_settings : {
				scope_handler : me
			},
			swfupload_preload_handler : me.swfupload_preload_handler,
			file_queue_error_handler : me.file_queue_error_handler,
			swfupload_load_failed_handler : me.swfupload_load_failed_handler,
			upload_start_handler : me.upload_start_handler,
			upload_progress_handler : me.upload_progress_handler,
			upload_error_handler : me.upload_error_handler,
			upload_success_handler : me.upload_success_handler,
			upload_complete_handler : me.upload_complete_handler,
			file_queued_handler : me.file_queued_handler
			/*,
			file_dialog_complete_handler : me.file_dialog_complete_handler*/
		};
	},
	swfupload_preload_handler : function(){
		if (!this.support.loading) {
			Ext.Msg.show({
				title : '提示',
				msg : '浏览器Flash Player版本太低,不能使用该上传功能！',
				width : 250,
				icon : Ext.Msg.ERROR,
				buttons :Ext.Msg.OK
			});
			return false;
		}
	},
	file_queue_error_handler : function(file, errorCode, message){
		switch(errorCode){
			case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED : msg('上传文件列表数量超限,不能选择！');
			break;
			case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT : msg('文件大小超过限制, 不能选择！');
			break;
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE : msg('该文件大小为0,不能选择！');
			break;
			case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE : msg('该文件类型不允许上传！');
			break;
		}
		function msg(info){
			Ext.Msg.show({
				title : '提示',
				msg : info,
				width : 250,
				icon : Ext.Msg.WARNING,
				buttons :Ext.Msg.OK
			});
		}
	},
	swfupload_load_failed_handler : function(){
		Ext.Msg.show({
			title : '提示',
			msg : 'SWFUpload加载失败！',
			width : 180,
			icon : Ext.Msg.ERROR,
			buttons :Ext.Msg.OK
		});
	},
	upload_start_handler : function(file){
		var me = this.settings.custom_settings.scope_handler;
		me.down('#btn_cancel').setDisabled(false);
		me.down('#btn_query').setDisabled(false);
		var rec = me.store.getById(file.id);
		this.setPostParams(me.post_params);
		this.setFilePostName(encodeURIComponent('txt'));
		//this.setFilePostName(encodeURIComponent(rec.get('fileName')));
	},
	upload_progress_handler : function(file, bytesLoaded, bytesTotal){
		var me = this.settings.custom_settings.scope_handler;		
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		percent = percent == 100? 99 : percent;
       	var rec = me.store.getById(file.id);
       	rec.set('percent', percent);
		rec.set('status', file.filestatus);
		rec.commit();
	},
	upload_error_handler : function(file, errorCode, message){
		var me = this.settings.custom_settings.scope_handler;		
		var rec = me.store.getById(file.id);
       	rec.set('percent', 0);
		rec.set('status', file.filestatus);
		rec.commit();
	},
	upload_success_handler : function(file, serverData, responseReceived){
		var me = this.settings.custom_settings.scope_handler;		
		var rec = me.store.getById(file.id);
		if(Ext.JSON.decode(serverData).success){			
	       	rec.set('percent', 100);
			rec.set('status', file.filestatus);	
			rec.set('isexists', '1');
		}else{
			rec.set('percent', 0);
			rec.set('status', SWFUpload.FILE_STATUS.ERROR);
		}
		rec.commit();
		if (this.getStats().files_queued > 0 && this.uploadStopped == false) {
			this.startUpload();
		}else{
			me.showBtn(me,true);
		}
	},
	upload_complete_handler : function(file){
		
	},
	file_queued_handler : function(file){
		var me = this.settings.custom_settings.scope_handler;
		//根据上传文件名第一下划线部分与配置部分进行匹配
		//Cash _YYYYMMDD_Brcode_Dept_System_ VAT
		var keyWord = file.name.substr(0,file.name.indexOf("."));
	//	var record = me.store.getAt(me.store.find('name',keyWord));;
		for (var int = 0; int < me.store.getCount(); int++) {
			if(keyWord.indexOf(me.store.getAt(int).data["name"])!=-1){
				var record=me.store.getAt(int)
			}
		}
		if(record){
			record.set('id',file.id);
			record.set('filename',file.name);
			record.set('size',file.size);
			record.set('type',file.type);
			record.set('status',file.filestatus);
			record.set('percent',0);
		}else{
			Ext.Msg.show({
				title : '提示',
				msg : '该文件不允许上传！',
				width : 180,
				icon : Ext.Msg.ERROR,
				buttons :Ext.Msg.OK
			});
		}
		
/*		me.store.add({
			id : file.id,
			name : file.name,
			fileName : file.name,
			size : file.size,
			type : file.type,
			status : file.filestatus,
			percent : 0
		});*/
	},
	onUpload : function(){
		if (this.swfupload&&this.store.getCount()>0) {
			if (this.swfupload.getStats().files_queued > 0) {
				this.showBtn(this,false);
				this.swfupload.uploadStopped = false;		
				this.swfupload.startUpload();
			}
		}
	},
	showBtn : function(me,bl){
		me.down('#btn_add').setDisabled(!bl);
		me.down('#btn_upload').setDisabled(!bl);
		me.down('#btn_remove').setDisabled(!bl);
		me.down('#btn_cancel').setDisabled(bl)
//		if(bl){
//			me.down('actioncolumn').show();
//		}else{
//			me.down('actioncolumn').hide();
//		}		
	},
	onRemove : function(){
		var ds = this.store;
		for(var i=0;i<ds.getCount();i++){
			var record =ds.getAt(i);
			var file_id = record.get('id');
			this.swfupload.cancelUpload(file_id,false);
			record.set('id','');
			record.set('filename','');
			record.set('size','');
			record.set('type','');
			record.set('status','');
			record.set('percent',0);
		}
		//ds.removeAll();
		this.swfupload.uploadStopped = false;
	},
	onCancelUpload : function(){
		if (this.swfupload) {
			this.swfupload.uploadStopped = true;
			this.swfupload.stopUpload();
			this.showBtn(this,true);
		}
	},
	onQuery: function(){	
		var ds = this.store;
		for(var i=0;i<ds.getCount();i++){
			var record =ds.getAt(i);
			var file_id = record.get('id');
			this.swfupload.cancelUpload(file_id,false);
			record.set('id','');
			record.set('filename','');
			record.set('size','');
			record.set('type','');
			record.set('status','');
			record.set('percent',0);
		}
	  
		if (this.swfupload) {
			this.swfupload.uploadStopped = true;
			this.swfupload.stopUpload();
			this.showBtn(this,true);
		}
	},
	onDownLoad: function(grid, rowIndex, colIndex) {
       	//var id = grid.store.getAt(rowIndex).get('id');
    	var record = grid.store.getAt(rowIndex);
    	console.log(record);
    	if(record){
    	}
	}
})
