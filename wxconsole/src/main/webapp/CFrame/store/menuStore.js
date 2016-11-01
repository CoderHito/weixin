Ext.define('CFrame.store.menuStore', {
	extend: 'Ext.data.TreeStore',
    alias: 'widget.menuStore',
    
    model : 'CFrame.model.menuNodeModel',
   
    proxy : {
    	type : 'ajax',
    	url : App.basePath + '/mainMenuAction'
    }
});