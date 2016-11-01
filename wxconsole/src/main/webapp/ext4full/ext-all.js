﻿/*
This file is part of Ext JS 4.2

Copyright (c) 2011-2013 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as
published by the Free Software Foundation and appearing in the file LICENSE included in the
packaging of this file.

Please review the following information to ensure the GNU General Public License version 3.0
requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department
at http://www.sencha.com/contact.

Build date: 2013-05-16 14:36:50 (f9be68accb407158ba2b1be2c226a6ce1f649314)
*/
Ext.override(Ext.grid.RowEditor,
    {
        addFieldsForColumn: function (column, initial) {
            var me = this, i, length, field;
            if (Ext.isArray(column)) {
                for (i = 0, length = column.length; i < length; i++) {
                    me.addFieldsForColumn(column[i], initial);
                }
                return;
            }
            if (column.getEditor) {
                field = column.getEditor(null, {
                    xtype: 'displayfield',
                    getModelData: function () {
                        return null;
                    }
                });
                if (column.align === 'right') {
                    field.fieldStyle = 'text-align:right';
                }
                if (column.xtype === 'actioncolumn') {
                    field.fieldCls += ' ' + Ext.baseCSSPrefix + 'form-action-col-field';
                }
                if (me.isVisible() && me.context) {
                    if (field.is('displayfield')) {
                        me.renderColumnData(field, me.context.record, column);
                    } else {
                        field.suspendEvents();
                        field.setValue(me.context.record.get(column.dataIndex));
                        field.resumeEvents();
                    }
                }
                if (column.hidden) {
                    me.onColumnHide(column);
                } else if (column.rendered && !initial) {
                    me.onColumnShow(column);
                }

                // -- start edit
                me.mon(field, 'change', me.onFieldChange, me);
                // -- end edit
            }
        }
    });