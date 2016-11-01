Ext.define('CFrame.store.menuButtonStore', {
	extend: 'Ext.data.Store',
    alias: 'widget.menuButtonStore',
    
    model : 'CFrame.model.menuButtonModel',
   
    proxy : 
    	new Ext.data.HttpProxy( {
            url : 'weixinMaintainAction!dicWeiXinMenu',
            actionMethods: {  
                read: 'POST'  
            }, 
            reader: {
                type: 'json',
                root: 'storeList'
            }
        })
    
});