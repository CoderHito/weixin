/*
 * File: CFrame/store/bas/ccyStore.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('CFrame.store.sys.sysParamStore', {
    extend: 'Ext.data.Store',

    requires: [
        'CFrame.model.sys.sysParamModel'
    ],
    pageSize : 10,//每页以10条记录为限
    model: 'CFrame.model.sys.sysParamModel',
    proxy : new Ext.data.HttpProxy( {//通过//HttpProxy（）请求后台数据资源
        url : 'sysParamMaintainAction!queryParam',
        actionMethods: {  
            read: 'POST'  
        }, 
        reader: {
            type: 'json',//以json格式装入MODEL
            root: 'sysParamList',//后台返回的是一个List集合
            totalProperty: 'total'//从后台传回总数据条数
        }
    })
});