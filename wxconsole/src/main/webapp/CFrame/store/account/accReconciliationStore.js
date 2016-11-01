Ext.define('CFrame.store.account.accReconciliationStore', {
    extend: 'Ext.data.Store',
    requires: [
        'CFrame.model.account.accReconciliationModel'
    ],
    pageSize : 50,
    model: 'CFrame.model.account.accReconciliationModel',
    proxy : new Ext.data.HttpProxy( {
        url : 'accReconciliationAction!queryReconciliationPage',
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