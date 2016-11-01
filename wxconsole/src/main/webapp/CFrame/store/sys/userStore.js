/*
 * File: CFrame/store/userStore.js
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

Ext.define('CFrame.store.sys.userStore', {
    extend: 'Ext.data.Store',

    requires: [
        'CFrame.model.sys.userModel'
    ],
    pageSize : 10,
    model: 'CFrame.model.sys.userModel',
    proxy : new Ext.data.HttpProxy( {
        url : 'userMaintainAction!queryUser',
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