Ext.define('CFrame.store.account.subjectStore', {
    extend: 'Ext.data.Store',
    requires: [
        'CFrame.model.account.subjectModel'
    ],
    model: 'CFrame.model.account.subjectModel',
    proxy : new Ext.data.HttpProxy( {
        url : 'subjectMaintainAction!querySubjectPage',
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