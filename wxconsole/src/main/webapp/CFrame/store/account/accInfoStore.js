Ext.define('CFrame.store.account.accInfoStore', {
    extend: 'Ext.data.Store',
    requires: [
        'CFrame.model.account.accInfoModel'
    ],
    model: 'CFrame.model.account.accInfoModel',
    proxy : new Ext.data.HttpProxy( {
        url : 'accInfoMaintainAction!queryAccPage',
        actionMethods: {  
            read: 'POST'  
        }, 
        reader: {
            type: 'json',
            root: 'storeList',
            totalProperty: 'total'
        }
    })
});