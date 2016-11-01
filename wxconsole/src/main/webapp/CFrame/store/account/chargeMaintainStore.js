
Ext.define('CFrame.store.account.chargeMaintainStore', {
    extend: 'Ext.data.Store',
    requires: [
        'CFrame.model.account.chargeMaintainModel'
    ],
    model: 'CFrame.model.account.chargeMaintainModel',
    proxy : new Ext.data.HttpProxy( {
        url : 'chargeMaintainAction!queryChargePage',
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