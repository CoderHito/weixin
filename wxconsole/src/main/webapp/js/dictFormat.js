function dictFormat(value,dictName){
    if(dictName.getCount()==0){
        dictName.reload();
    }
    if(dictName.getCount()==0){
        Ext.MessageBox.alert("错误",'[用户状态]数据字典挂载失败');
    }
    var index = dictName.find('DICT_VALUE',value);
    if(index == -1){
        return "";
    }
    var record = dictName.getAt(index).get('DICT_NAME');

    return record;
}