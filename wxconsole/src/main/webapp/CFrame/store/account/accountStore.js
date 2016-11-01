Ext.define('CFrame.store.account.accountStore', {
    extend: 'Ext.data.Store',
    requires: [
        'CFrame.model.account.accountModel'
    ],
    pageSize : 10,
    model: 'CFrame.model.account.accountModel',
    proxy : new Ext.data.HttpProxy( {
        url : 'accountMaintainAction!queryAccountPage',
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