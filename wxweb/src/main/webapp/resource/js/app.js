var app = app || {};
(function() {
	$.extend(app,{
		msgTip:function(text){//消息提示
			var instance = false;
			if(!instance){
				var $container=$('<div class="msg-tooltipWrap"><div class="msg-tooltipInner"><div class="msg-tooltip msgTipBounceIn"><div class="zvalid-resultformat">'+text+'</div></div></div></div>');
    			$container.appendTo($("body"));
    			instance=true;
    			setTimeout(function(){
					$container.remove();
					instance=false;
				},1500);
			}
		},
		showLoading:function(){
			$('body').append('<div id="app-loading-div" class="app-loading-div"><i class="icon-spinner icon-spin icon-large app-loading-i"></i><div>');
			return $('#app-loading-div');
		},
		destroyLoading:function(loadingEl){
			loadingEl.remove();
		}
	});
})();