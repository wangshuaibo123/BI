// new表格
function SearchForm(formStructure, formName,container) {
	this.formStructure=formStructure || {};
	this.formName=formName;
	this.container=container;
	this.treeWidth=600;
	this.treeHeight=600;
	this.url = '';
	this.checkInit=false;
	this.treeMenus=[];
	this.init();
}
SearchForm.prototype = {
	init:function(){
			this.btns=this.formStructure.buttons||[];
			this.columns=this.formStructure.columns||[];
			this.form=this.formStructure.form;
			this.formHTML='';
			//this.treeName="treeName"+this.formName;
	},
	show : function(isShow) {
		var that=this;
		$(this.container).html(that.create());
		that.reDrawObj();
		if(that.checkInit){
			checkedInitForm(that.formName);
		}
	},
	create:function(){
		var that=this;
		var formStrs=[];
		if ( typeof this.formStructure === 'object' && $.isEmptyObject(this.formStructure)) {
			//if(that.form && typeof (that.form) =='string'){
			if($(that.container).attr("loadUrl")){
				that.url = $(that.container).attr("loadUrl");
			}
			that.formHTML = that.formHTML || $(that.container).html();
			if (that.formHTML && that.formHTML.length) {
				if(that.container.tagName === "form"){
                    this.formName = $(that.container).attr("id");
                    formStrs.push(that.formHTML);
                }else{
                    formStrs.push('<form class="searchFrom" isCheck=true id="'+that.formName+'">');
                    formStrs.push(that.formHTML);
                    $("#" + that.form).remove();
                }
			}
		} else {
			formStrs=['<form class="searchFrom" isCheck=true id="'+that.formName+'">'];
			for(var i=0;i<that.columns.length;i++){
				formStrs.push(that.createObj(this.columns[i]));	
			}
			formStrs.push('</form>');
			formStrs.push(that.createBtn());			
		}
		return formStrs.join("");
	},
	/**
	 * eachForm 遍历form,去除输入数据中前后的空格
	 */
	formTrim:function(){
		eachForm($("#"+this.formName),function(el){
			var v=$(el).val();
			if(v){
				if(typeof v!="object" && typeof v!="function") {
					$(el).val(v.trim());
				}
			}
		})
	},
	serialize:function(){
		this.formTrim();
		return $("#"+this.formName).serialize(); // 此serialize()为jQuery提供方法，序列表表格内容为字符串
	},
	serializeArray:function(){
		this.formTrim();
		return $("#"+this.formName).serializeArray();
	},
	checkFormFormat:function(){
		return submitCheckIsNull(this.formName)&&checkFormFormat(this.formName);
	},
	reDrawObj:function(){
		var that=this;
		events={	
				select : function(id) {
					//$(id).selectmenu();
				},
				date : function(id) {
					//$(id).datepicker($.datepicker.regional[ "zh-TW" ],"option","dateFormat", "yy-mm-dd" );
                    $(id).bind("click",function(){
                        WdatePicker({dateFmt:'yyyy-MM-dd'});
                    });
				},
				dateType : function(id,c) {
                    $(id).bind("click",function(){
                    	var _df = c.dateFormat||'yyyy-MM-dd';
                        WdatePicker({dateFmt:_df});
                    });
				},
				dbDate:function(id,c){
					var startId=id+"_start" ;
					var endId=id+"_end";
                    var dateObj_S={dateFmt:'yyyy-MM-dd'};
                    var dateObj_E={dateFmt:'yyyy-MM-dd'};
                    if(c.isCompare)
                    {
                    	var dd=0;
                    	if(undefined!=c.intervalDay){
                    		dd=c.intervalDay;
                    	}
                        dateObj_S.maxDate='#F{$dp.$D(\''+endId.replace("#","")+'\',{d:-'+dd+'})}';
//                        maxDate:'          #F{$dp.$D(\'d4322\',{d:-3});}'
                        dateObj_E.minDate='#F{$dp.$D(\''+startId.replace("#","")+'\',{d:'+dd+'})}';
                        
                        
                    }
                    $(startId).bind("click",function(){
                        WdatePicker(dateObj_S);
                    });
                    $(endId).bind("click",function(){
                        WdatePicker(dateObj_E);
                    });
				},
                dateTime : function(id,c) {
					var dateJson = {};
					dateJson.dateFmt = 'yyyy-MM-dd HH:mm:ss';
					if(c.maxDate){
						dateJson.maxDate = c.maxDate+" 23:59:59";
					}
					if(c.minDate){
						dateJson.minDate = c.minDate+" 00:00:01";
					}
                    $(id).bind("click",function(){	 
                        WdatePicker(dateJson);
                    });
                }, 
                dbDateTime:function(id,c){
                    var startId=id+"_start" ;
                    var endId=id+"_end";
                    var dateObj_S={dateFmt:'yyyy-MM-dd HH:mm:ss'};
                    var dateObj_E={dateFmt:'yyyy-MM-dd HH:mm:ss'};
                    if(c.isCompare)
                    {
                        dateObj_S.maxDate='#F{$dp.$D(\''+endId.replace("#","")+'\')}';
                        dateObj_E.minDate='#F{$dp.$D(\''+startId.replace("#","")+'\')}';
                    }
                     $(startId).bind("click",function(){
                        WdatePicker(dateObj_S);
                     });
                     $(endId).bind("click",function(){
                        WdatePicker(dateObj_E);
                     });
                },
				text : function(id,c) {
					 
					//that.pageIndex += 1;
				},
				dbText:function(id){
					
				},
				tree:function(id,c){
					var type=c.treeType||"radio";
					var w=c.treeWidth||that.treeWidth;
					var h=c.treeHeight||that.treeHeight;
					var dl=c.disabledLink||"false";
					var treeMenuObj=$(id+"_").treeMenu({"treeUrl":c.value,"width":w,"height":h,"treeType":type,"disabledLink":dl,"treeExtendObjs":$(id+"_nodeObj")[0],"okFun":c.okFun,"treeIdObj":$(id)[0]});
					that.treeMenus.push(treeMenuObj);
				},
				hidden:function(id){
					
				},
				radio:function(){
					
				},
				checkbox:function(){
					
				},
				autocomplete : function(id) {
					$(id).newAutoComplete();
				},
				multiselect : function(id,c) {
					//debugger;
					$(id).multiselect({
						checkAllText: '全选',
		                uncheckAllText: '全不选',
		                noneSelectedText: '-请选择-',
		                selectedText: '# 选中',
		                minWidth: 180,
						close: function(){debugger;
							var options=$(this).multiselect("getChecked").map(function(){
								var sVal=this.value;
								if(c&&c.enumType&&c.enumType=="String") {
									sVal="'"+sVal+"'";
								}
								return sVal;
							}).get();
							
							var sourceId=$(id).attr("sourcename");
							$("#"+sourceId).val(options.join(","));
					   }
					});
				}
		};
		for(var i=0;i<that.columns.length;i++){
			(function(c){
				var id="#"+c.code+that.formName;
				events[c.type](id,c);
				if(c.clickFun){
					var obj=$(id);
					if(c.clickFun){
						obj.bind("click",function(){
							c.clickFun(obj[0]);
						});
					}
				}
			})(that.columns[i]);
		}
		for(var i=0;i<that.btns.length;i++){
			var btn=that.btns[i];
			$("#"+btn["id"]).bind("click",btn.fun);
			$("#"+btn["id"]).button({
			  icons: {
				primary:btn["icon"]
			  }
			});
		}
	},
	createBtn:function(btnObj){
		var that=this;
		var btnStr=['<div class="searchBtn">'];
		for(var i=0;i<this.btns.length;i++){
			var btn=this.btns[i];
			btn["id"]=that.formName+"btn"+i;
			btnStr.push('<button id="'+btn["id"]+'">'+btn["text"]+'</button>');
		}
		btnStr.push("</div>");
		return btnStr.join("");
	},
	reset:function(){
		for(var i=0;i<this.treeMenus.length;i++){
			var treeObj=this.treeMenus[i];
			if(treeObj){
				this.treeMenus[i].recovery();
				this.treeMenus[i]=null;
				this.treeMenus.splice(i, 1);
				i--;
			}
		}
		this.show();
	},
	getObj:function(code){
		var that=this;
		var objs=[];
		for(var i=0;i<that.columns.length;i++){
			var column=that.columns[i];
			if(column["code"]===code&&(column["type"]==="dbDate"||column["type"]==="dbText")){
				objs.push($("#"+code+this.formName+"_start")[0]);
				objs.push($("#"+code+this.formName+"_end")[0]);
				return objs;
			}
		}
		return $("#"+code+this.formName)[0];
	},
	createObj:function(obj){
		var that=this;
		if(!obj){
			return;
		}
		obj["type"]=obj.type||"text";
		var name=obj["code"];
		var id=obj["code"]+that.formName;
		var value=obj["value"];
		if(value == undefined||value==null||value=="NULL"||value=="null"){
			value="";
		};
		var startName=name+"_start";
		var endName=name+"_end";
		var startId=id+"_start";
		var endId=id+"_end";
		var dbStyle='style= " width:75px; "'; 
		if(obj.width!=undefined&&obj.width!=""){
			dbStyle='style= " width:'+obj.width+'px; "'; 
		}
		var extendproperty=(function(){
			var extStr=[];
			if(obj.extendProperty){
				that.checkInit=true;
				for(var key in obj.extendProperty){
					extStr.push(' '+key+' = "'+obj.extendProperty[key]+'" ');
				}
			}
			return extStr.join("");
		})()
		var createInput=(function(){
			return function(type,name,id,value,dbStyle){
				var htmls=["<input"];
				htmls.push(' type="'+(type||"text")+'"');
				htmls.push(dbStyle);
				htmls.push(' value="'+ value+'"');
				htmls.push(' name="'+name+'" id="'+id+'"');
				htmls.push(extendproperty);
				htmls.push('>');
				return htmls.join(" ");
			};
		})();	
		var objs={
				select:function(){
					objStrs.push('<select class="fieldSelect" name="'+name+'" id="'+id+'">');
				  	for(var i=0;i<obj.value.length;i++){
						var o=obj.value[i];
						var selected="";
						if(obj.defaultValue==o.value){
							selected=' selected="selected" ';
						}
						objStrs.push('<option '+selected+' value="'+o.value+'">'+o.text+'</option>');
					}
				  	objStrs.push('</select>');
				},
				date:function(){
					objStrs.push(createInput("text",name,id,value,dbStyle));
				},
				dateType:function(){
					objStrs.push(createInput("text",name,id,value,dbStyle));
				},
				dbDate:function(){
					objStrs.push(createInput("text",startName,startId,value,dbStyle));
					objStrs.push("&nbsp;~&nbsp;");
					objStrs.push(createInput("text",endName,endId,value,dbStyle));
				},
                dateTime:function(){
                    objStrs.push(createInput("text",name,id,value,dbStyle));
                }, 
                dbDateTime:function(){
                    dbStyle='style= " width:125px; "';
                    objStrs.push(createInput("text",startName,startId,value,dbStyle));
                    objStrs.push("&nbsp;~&nbsp;");
                    objStrs.push(createInput("text",endName,endId,value,dbStyle));
                },
				text:function(){
					objStrs.push(createInput("text",name,id,value,dbStyle));
				},
				dbText:function(){
					objStrs.push(createInput("text",startName,startId,value,dbStyle));
					objStrs.push("&nbsp;~&nbsp;");
					objStrs.push(createInput("text",endName,endId,value,dbStyle));
				},
				tree:function(){
					objStrs.push(' <input type="hidden" name="'+name+'" id="'+id+'">');
					objStrs.push(' <input type="hidden" name="'+name+'_nodeObj" id="'+id+'_nodeObj">');
					objStrs.push(' <input type="text" name="'+name+'_" id="'+id+'_">&nbsp;');	
				},
				radio:function(type){
					var t=type||"radio";
					for(var i=0;i<obj.value.length;i++){
						var o=obj.value[i];
						objStrs.push('<span><input type="'+t+'" id="'+(id+i)+'" name="'+name+'" value="'+o.value+'"/><label for="'+(id+i)+'">'+o.text+'</label></span>')	
					}
				},
				checkbox:function(){
					objs["radio"]("checkbox");
				},
				hidden:function(){
					return createInput("hidden",name,id,value);
				},
				autocomplete:function(){
					objStrs.push('<select class="fieldSelect" name="'+name+'" id="'+id+'">');
				  	for(var i=0;i<obj.value.length;i++){
						var o=obj.value[i];
						var selected="";
						if(obj.defaultValue==o.value){
							selected=' selected="selected" ';
						}
						objStrs.push('<option '+selected+' value="'+o.value+'">'+o.text+'</option>');
					}
				  	objStrs.push('</select>');
				},
				multiselect:function(){
					debugger;
					objStrs.push(' <input type="hidden" name="'+name+'" id="source_'+name+'">');
					objStrs.push('<select multiple="multiple" class="fieldSelect" name="show_'+name+'" id="'+id+'" sourcename="source_'+name+'">');
				  	for(var i=0;i<obj.value.length;i++){
						var o=obj.value[i];
						var selected="";
						if(obj.defaultValue==o.value){
							selected=' selected="selected" ';
						}
						objStrs.push('<option '+selected+' value="'+o.value+'">'+o.text+'</option>');
					}
				  	objStrs.push('</select>');
				}
			};
		
		if(obj.type=="hidden"){
			return objs[obj.type]();
		}
		var objStrs=[' <div class="field"><label class="fieldName" for="speed">'+(obj["display"]||"&nbsp;")+':</label>'];
		objs[obj.type]();
		objStrs.push('</div>');
		return objStrs.join("");
	},
	
	//加载回填数据
	loadData:function(isCheck,params,Fun){
	    isCheck = isCheck||true;
		var that = this;
		if(!that.url || !that.url.length>0){
			return false
		}
		 jyAjax(that.url, params, function (result) {
             if (result && result.data) {
            	 if(Fun){
            		 that.setData(result.data,Fun);            		 
            	 }else{
            		 that.setData(result.data);    
            	 }
            	 if(isCheck){
            	    checkedInit(); 
            	 }
             }
         });
	},
	
	//回填数据
	setData:function(jsonValue,Fun){
        var that = this;
        var gettype=Object.prototype.toString;// 取得类型
        var setVal = function (name, ival) {
            var $oinput = $(that.container).find(":input[name='" + name + "']");// 取得当前字段对应的元素
            if ($oinput.attr("type") == "checkbox") {// 复选框类型
                if (ival !== null) {
                    var checkboxObj = $("[name='" + name + "']");
                    var checkArray = ival.split(",");
                    for (var i = 0; i < checkboxObj.length; i++) {
                        for (var j = 0; j < checkArray.length; j++) {
                            if (checkboxObj[i].value == checkArray[j]) {
                                checkboxObj[i].click();
                            }
                        }
                    }
                }
            }
            else if ($oinput.attr("type") == "radio") {// 单选按钮
                $oinput.each(function () {
                    var radioObj = $("[name='" + name + "']");
                    for (var i = 0; i < radioObj.length; i++) {
                        if (radioObj[i].value == ival) {
                            radioObj[i].click();
                        }
                    }
                });
            }
            else if ($oinput.attr("type") == "textarea") {// 文本域
            	$(that.container).find("[name='" + name + "']").html(ival);
            }
            else if ($oinput[0] && $oinput[0].tagName == "select") {// 下拉选择
            	$(that.container).find("[name='" + name + "']").val(ival).trigger("change");
            }
            else {// 其它
            	if($(that.container).find("[name='" + name + "']").length > 0){
            	    $(that.container).find("[name='" + name + "']").val(ival);
                }else{
                    $(that.container).find("[id='" + name + "']").html(ival);
                }
            }
        };
        $.each(jsonValue,function(name,ival){
            if (gettype.call(ival) =="[object Object]"){// 处理对象
                $.each(ival,function (key,val) {
                    setVal(name+"."+key,val);
                });
            }else if(gettype.call(ival) =="[object Array]"){// 处理数组
                console.log("["+name + "][object Array]字段是数组类型,暂不支持");
            }else {
                setVal(name, ival);// 普通类型
            }
        });
        if(Fun && typeof(Fun) === 'function'){
			Fun();
		}
    },
};
(function( $ ){  
  $.fn.newSearchForm = function(formStructure) {
	var fname="fname"+(new Date()).getTime();
   	return new SearchForm(formStructure,fname,$(this)[0]);
  };  
})(jQuery);  

