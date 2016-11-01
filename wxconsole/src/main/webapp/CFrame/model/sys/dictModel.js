Ext.define('CFrame.model.sys.dictModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'GROUP_ID',
            type : 'string'
        },
        {
            name: 'GROUP_NAME',
            type : 'string'
        },
        {
            name: 'DICT_VALUE',
            type : 'string'
        },
        {
            name: 'DICT_NAME',
            type : 'string'
        },
        {
            name: 'MODIFY_TIME',
            type : 'string'
        },
        {
            name: 'MODIFY_USER',
            type : 'string'
        },
        {
            name: 'CHECK_TIME',
            type : 'string'
        },
        {
            name: 'CHECK_TLRNO',
            type : 'string'
        },
        {
        	name : 'OP_FLAG',
    		type : 'string'
        }
    ]
});