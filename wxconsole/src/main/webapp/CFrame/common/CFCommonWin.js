var sw_area;   //地区选择窗体
var sw_city;   //城市选择窗体
var sw_district;   //商圈选择窗体
var sw_subject;   //商户类别选择窗体
var CFCommonWin = CFCommonWin || {};

(function() {		
	Ext.apply(CFCommonWin,{
		
		/**
		 * 弹出选择地区选择窗体
		 * obj.AREA_CODE		地区代码
		 * obj.CITY_CODE		城市代码
		 * obj.PROVINCE_CODE	省代码
		 */
		selectAreaWinShow:function(obj){
			Ext.require('CFrame.view.common.selectAreaWinView');
			sw_area = top.Ext.getCmp('selectAreaWin');
			if(!sw_area){
				sw_area = Ext.create('CFrame.view.common.selectAreaWinView',{layout : 'fit',
					width : 720,
					height : 540,
					resizable : false,
					draggable : true,
					closeAction : 'hide',
					modal : true,
					title : '地区选择'
					});
			}
			sw_area.ret = '';
			var tree = sw_area.child('#areaForm').child('#areaTreePanel');
			tree.store.load();
			sw_area.show(obj.parent);
			tree.getRootNode().collapseChildren(true);
			sw_area.on('hide',fn =function(){
				if('confirm'==sw_area.ret){
					sw_area.ret = '';
					if(obj.AREA_CODE){
						obj.AREA_CODE.setValue(sw_area.model.get('id'));
					}
					if(obj.CITY_CODE){
						obj.CITY_CODE.setValue(sw_area.model.get('id').substring(0,6));
					}
					if(obj.PROVINCE_CODE){
						obj.PROVINCE_CODE.setValue(sw_area.model.get('id').substring(0,3));
					}
					tree.getRootNode().collapseChildren(true);
					sw_area.un('hide',fn);
				}
			});
		},
		
		/**
		 * 弹出选择城市选择窗体
		 * obj.CITY_CODE		城市代码
		 * obj.PROVINCE_CODE	省代码
		 */
		selectCityWinShow:function(obj){
			Ext.require('CFrame.view.common.selectCityWinView');
			sw_city = top.Ext.getCmp('selectCityWin');
			if(!sw_city){
				sw_city = top.Ext.create('CFrame.view.common.selectCityWinView',{layout : 'fit',
					width : 720,
					height : 540,
					resizable : false,
					draggable : true,
					closeAction : 'hide',
					modal : true,
					title : '城市选择'
					});
			}
			var tree = sw_city.child('#cityForm').child('#cityTreePanel');
			sw_city.ret = '';
			sw_city.show(obj.parent);
			tree.getRootNode().collapseChildren(true);
			sw_city.on('hide',fn =function(){
				if('confirm'==sw_city.ret){
					sw_city.ret = '';
					if(obj.CITY_CODE){
						obj.CITY_CODE.setValue(sw_city.model.get('id'));
					}
					if(obj.PROVINCE_CODE){
						obj.PROVINCE_CODE.setValue(sw_city.model.get('id').substring(0,3));
					}
					tree.getRootNode().collapseChildren(true);
					sw_city.un('hide',fn);
				}
			});
		},
		
		/**
		 * 弹出选择商圈选择窗体
		 * obj.DISTRICT_CODE		商圈代码
		 * obj.AREA_CODE		地区代码
		 * obj.CITY_CODE		城市代码
		 * obj.PROVINCE_CODE	省代码
		 */
		selectDistrictWinShow:function(obj){
			Ext.require('CFrame.view.common.selectDistrictWinView');
			sw_district = top.Ext.getCmp('selectDistrictWin');
			if(!sw_district){
				sw_district = top.Ext.create('CFrame.view.common.selectDistrictWinView',{layout : 'fit',
					width : 720,
					height : 540,
					resizable : false,
					draggable : true,
					closeAction : 'hide',
					modal : true,
					title : '商圈选择'
					});
			}
			var tree = sw_district.child('#districtForm').child('#districtTreePanel');
			tree.getRootNode().collapseChildren(true);
			sw_district.show(obj.parent);
			sw_district.on('hide',fn =function(){
				if('confirm'==sw_district.ret){
					sw_district.ret = '';
					if(obj.DISTRICT_CODE){
						obj.DISTRICT_CODE.setValue(sw_district.model.get('id'));
					}
					if(obj.AREA_CODE){
						obj.AREA_CODE.setValue(sw_district.model.get('id').substring(0,9));
					}
					if(obj.CITY_CODE){
						obj.CITY_CODE.setValue(sw_district.model.get('id').substring(0,6));
					}
					if(obj.PROVINCE_CODE){
						obj.PROVINCE_CODE.setValue(sw_district.model.get('id').substring(0,3));
					}
					var tree = sw_district.child('#districtForm').child('#districtTreePanel');
					tree.getRootNode().collapseChildren(true);
					sw_district.un('hide',fn);
				}
				console.log(parent);
			});
		},
		/**
		 * 弹出选择城市选择窗体
		 * obj.SUBJECT		类别代码
		 * obj.SUB_SUBJECT	子类别代码
		 */
		selectSubjectWinShow:function(obj){
			Ext.require('CFrame.view.common.selectSubjectWinView');
			sw_subject = top.Ext.getCmp('selectSubjectWin');
			if(!sw_subject){
				sw_subject = top.Ext.create('CFrame.view.common.selectSubjectWinView',{layout : 'fit',
					width : 720,
					height : 540,
					resizable : false,
					draggable : true,
					closeAction : 'hide',
					modal : true,
					title : '商户类别选择'
					});
			}
			var tree = sw_subject.child('#subjectForm').child('#subjectTreePanel');
			sw_subject.ret = '';
			sw_subject.show(obj.parent);
			
			tree.getRootNode().collapseChildren(true);
			sw_subject.on('hide',fn =function(){
				if('confirm'==sw_subject.ret){
					sw_subject.ret = '';
					if(obj.SUB_SUBJECT){
						obj.SUB_SUBJECT.setValue(sw_subject.model.get('id'));
					}
					if(obj.SUBJECT){
						obj.SUBJECT.setValue(sw_subject.model.get('id').substring(0,2));
					}
					tree.getRootNode().collapseChildren(true);
					sw_subject.un('hide',fn);
				}
				//console.log(parent);
			});
		}
		
    });
	
}());