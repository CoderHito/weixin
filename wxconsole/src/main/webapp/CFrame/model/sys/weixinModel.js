Ext.define('CFrame.model.sys.weixinModel', {
    extend: 'Ext.data.Model',

    fields: [
             {
                 name: 'menu_id',
                 type : 'string'
             },
        {
            name: 'type',
            type : 'string'
        },
        {
            name: 'menu_name',
            type : 'string'
        },
        {
            name: 'menu_key',
            type : 'string'
        },
        {
            name: 'url',
            type : 'string'
        },
        {
            name: 'media_id',
            type : 'string'
        },
        {
            name: 'level',
            type : 'string'
        },
        {
            name: 'parent_id',
            type : 'string'
        },
        {
            name: 'has_sub_button',
            type : 'string'
        },
        {
            name: 'sort',
            type : 'string'
        }        
    ]
});