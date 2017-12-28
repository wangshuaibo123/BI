﻿  (function( $ ) {
	  var create=function(endObj){
		  $.widget( "custom.combobox", {
			  _create: function() {
				  this.wrapper = $( "<span>" )
				  .addClass( "custom-combobox" )
				  .insertAfter( this.element );
				  
				  this.element.hide();
				  this._createAutocomplete();
				  this._createShowAllButton();
			  },
			  
			  _createAutocomplete: function() {
				  var selected = this.element.children( ":selected" ),
				  value = selected.val() ? selected.text() : "";
				  
				  this.input = $( '<input id="'+endObj.id+'_">' )
				  .appendTo( this.wrapper )
				  .val( value )
				  .attr( "title", "" )
				  .attr( "notnull",endObj.attr("notnull"))
				  .attr("nulltip",endObj.attr("nulltip"))
				  .attr("checkFun",endObj.attr("checkFun"))
				  .attr("checkType",endObj.attr("checkType"))
				  .attr("regExp",endObj.attr("regExp"))
				  .addClass( "custom-combobox-input" )
				  .css("width",(endObj.width()-20)+"px").css("padding","3px")
				  
				  .autocomplete({
					  delay: 0,
					  minLength: 0,
					  source: $.proxy( this, "_source" )
				  })
				  .tooltip({
					  tooltipClass: "ui-state-highlight"
				  });
				  
				  this._on( this.input, {
					  autocompleteselect: function( event, ui ) {
						  ui.item.option.selected = true;
						  this._trigger( "select", event, {
							  item: ui.item.option
						  });
					  },
					  
					  autocompletechange: "_removeIfInvalid"
				  });
			  },
			  
			  _createShowAllButton: function() {
				  var input = this.input,
				  wasOpen = false;
				  
				  $( "<a>" )
				  .attr( "tabIndex", -1 )
				  .tooltip()
				  .appendTo( this.wrapper )
				  .button({
					  icons: {
						  primary: "ui-icon-triangle-1-s"
					  },
					  text: false
				  })
				  .removeClass( "ui-corner-all" )
				  .addClass( "custom-combobox-toggle" )
				  .css("width","16px").css("padding","3px")
				  .mousedown(function() {
					  wasOpen = input.autocomplete( "widget" ).is( ":visible" );
				  })
				  .click(function() {
					  input.focus();
					  
					  // 如果已经可见则关闭
					  if ( wasOpen ) {
						  return;
					  }
					  
					  // 传递空字符串作为搜索的值，显示所有的结果
					  input.autocomplete( "search", "" );
				  });
			  },
			  
			  _source: function( request, response ) {
				  var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
				  response( this.element.children( "option" ).map(function() {
					  var text = $( this ).text();
					  if ( this.value && ( !request.term || matcher.test(text) ) )
						  return {
						  label: text,
						  value: text,
						  option: this
					  };
				  }) );
			  },
			  
			  _removeIfInvalid: function( event, ui ) {
				  
				  // 选择一项，不执行其他动作
				  if ( ui.item ) {
					  return;
				  }
				  
				  // 搜索一个匹配（不区分大小写）
				  var value = this.input.val(),
				  valueLowerCase = value.toLowerCase(),
				  valid = false;
				  this.element.children( "option" ).each(function() {
					  if ( $( this ).text().toLowerCase() === valueLowerCase ) {
						  this.selected = valid = true;
						  return false;
					  }
				  });
				  
				  // 找到一个匹配，不执行其他动作
				  if ( valid ) {
					  return;
				  }
				  
				  // 移除无效的值
				  this.input
				  .val( "" )
				  .attr( "title", value + " 未找到匹配项" )
				  .tooltip( "open" );
				  this.element.val( "" );
				  this._delay(function() {
					  this.input.tooltip( "close" ).attr( "title", "" );
				  }, 2500 );
				  this.input.data( "ui-autocomplete" ).term = "";
			  },
			  
			  _destroy: function() {
				  this.wrapper.remove();
				  this.element.show();
			  }
		  });
		  
	  }
    $.fn.newAutoComplete=function(){
    	var obj=$(this);
    	create(obj);
    	obj.combobox();
    }
  })( jQuery );