Ext.define('CFrame.store.account.accReconciliationLogStore', {
    extend: 'Ext.data.Store',
    requires: [
        'CFrame.model.account.accReconciliationLogModel'
    ],
    pageSize : 50,
    model: 'CFrame.model.account.accReconciliationLogModel',
    proxy : new Ext.data.HttpProxy( {
        url : 'accReconciliationAction!queryReconciliationLogPage',
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