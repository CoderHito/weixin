

Ext.define('CFrame.store.sysQry.qryOperateAuditsStore', {
    extend: 'Ext.data.Store',

    requires: [
        'CFrame.model.sysQry.qryOperateAuditsModel'
    ],
    pageSize : 10,
    model: 'CFrame.model.sysQry.qryOperateAuditsModel',
    proxy : new Ext.data.HttpProxy( {//通过//HttpProxy（）请求后台数据资源
        url : 'qryOperateAuditsAction!queryOperate',
        reader: {
            type: 'json',//以json格式装入MODEL
            root: 'operateList',//后台返回的是一个List集合
            totalProperty: 'total'//从后台传回总数据条数
        }
    })
});