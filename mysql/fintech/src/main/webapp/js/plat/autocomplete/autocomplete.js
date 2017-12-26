
(function($) {
  var create = function(endObj, isMulti) {
    function split(val) {
      return val.split(/,\s*/);
    }
    $.widget("custom.combobox", {
      _create: function() {
        this.wrapper = $("<span>")
          .addClass("custom-combobox")
          .insertAfter(this.element);
        this.element.hide();
        if (isMulti) {
          this._createMultiAutocomplete()
        } else {
          this._createAutocomplete();
          this._createShowAllButton();
        }
      },
      _createMultiAutocomplete: function() {
        var selected = this.element.children(":selected"),
            value = selected.val() ? selected.text() : "";
        this.input = $('<input id="' + endObj.id + '_">')
          .appendTo(this.wrapper)
          .val(value)
          .attr("title", "")
          .attr("notnull", endObj.attr("notnull"))
          .attr("nulltip", endObj.attr("nulltip"))
          .attr("checkFun", endObj.attr("checkFun"))
          .attr("checkType", endObj.attr("checkType"))
          .attr("regExp", endObj.attr("regExp"))
          .addClass("custom-combobox-input")
          .css("width", (endObj.width() - 20) + "px").css("padding", "3px")
          .bind("keydown", function(event) { // 当选择一个条目时不离开文本域
            if (event.keyCode === $.ui.keyCode.TAB &&
              $(this).data("ui-autocomplete").menu.active) {
              event.preventDefault();
            }
          })
          .autocomplete({
            minLength: 0,
            source: $.proxy(this, "_multisource"),
            focus: function() { // 防止在获得焦点时插入值
              return false;
            },
            select: function(event, ui) {
              var terms = split(this.value);
              terms.pop(); // 移除当前输入
              terms.push(ui.item.label); // 添加被选项
              terms.push(""); // 添加占位符，在结尾添加逗号
              this.value = terms.join(",");
              return false;
            }
          })
          this._on(this.input, {
            autocompletechange: "_multiremoveIfInvalid"
          });
      },

      _createAutocomplete: function() {
        var selected = this.element.children(":selected"),
          value = selected.val() ? selected.text() : "";

        this.input = $('<input id="' + endObj.id + '_">')
          .appendTo(this.wrapper)
          .val(value)
          .attr("title", "")
          .attr("notnull", endObj.attr("notnull"))
          .attr("nulltip", endObj.attr("nulltip"))
          .attr("checkFun", endObj.attr("checkFun"))
          .attr("checkType", endObj.attr("checkType"))
          .attr("regExp", endObj.attr("regExp"))
          .addClass("custom-combobox-input")
          .css("width", (endObj.width() - 20) + "px").css("padding", "3px")
          .autocomplete({
            delay: 0,
            minLength: 0,
            source: $.proxy(this, "_source")
          })
          .tooltip({
            tooltipClass: "ui-state-highlight"
          });

        this._on(this.input, {
          autocompleteselect: function(event, ui) {
            ui.item.option.selected = true;
            this._trigger("select", event, {
              item: ui.item.option
            });
          },

          autocompletechange: "_removeIfInvalid"
        });
      },

      _createShowAllButton: function() {
        var input = this.input,
          wasOpen = false;

        $("<a>")
          .attr("tabIndex", -1)
          .tooltip()
          .appendTo(this.wrapper)
          .button({
            icons: {
              primary: "ui-icon-triangle-1-s"
            },
            text: false
          })
          .removeClass("ui-corner-all")
          .addClass("custom-combobox-toggle")
          .css("width", "16px").css("padding", "3px")
          .mousedown(function() {
            wasOpen = input.autocomplete("widget").is(":visible");
          })
          .click(function() {
            input.focus();

            // 如果已经可见则关闭
            if (wasOpen) {
              return;
            }

            // 传递空字符串作为搜索的值，显示所有的结果
            input.autocomplete("search", "");
          });
      },

      _source: function(request, response) {
        var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
        response(this.element.children("option").map(function() {
          var text = $(this).text();
          if (this.value && (!request.term || matcher.test(text)))
            return {
              label: text,
              value: text,
              option: this
            };
        }));
      },

      _multisource: function(request, response) {
        var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
        function extractLast(term) {
          return split(term).pop();
        }
        request.term = request.term || ''
                var responseVal = $.ui.autocomplete.filter(
          this.element.children("option").map(function() {
            return $(this).text()
          }), extractLast(request.term))
        // 回到 autocomplete，但是提取最后的条目
        response(responseVal);
      },

      _removeIfInvalid: function(event, ui) {

        // 选择一项，不执行其他动作
        if (ui.item) {
          return;
        }

        // 搜索一个匹配（不区分大小写）
        var value = this.input.val(),
          valueLowerCase = value.toLowerCase(),
          valid = false;
        this.element.children("option").each(function() {
          if ($(this).text().toLowerCase() === valueLowerCase) {
            this.selected = valid = true;
            return false;
          }
        });

        // 找到一个匹配，不执行其他动作
        if (valid) {
          return;
        }

        // 移除无效的值
        this.input
          .val("")
          .attr("title", value + " 未找到匹配项")
          .tooltip("open");
        this.element.val("");
        this._delay(function() {
          this.input.tooltip("close").attr("title", "");
        }, 2500);
        this.input.data("ui-autocomplete").term = "";
      },

      _multiremoveIfInvalid: function(event, ui) {
        var that = this;
        var value = this.input.val(),
          valueLowerCase = value.toLowerCase(),
          valueArr = valueLowerCase.split(",");
          valueHidden = '';
          that.element.children("option").each(function(index,value) {
            var that = this;
            that.selected = false;
            $.each(valueArr,function(index,value){
              if ($(that).text().toLowerCase() === value) {
                that.selected = valid = true;
                valueHidden +=$(that).val()+',';
                return false;
              }
            })
          });
          that.element.siblings('input[type="hidden"]').val(valueHidden);
      },

      _destroy: function() {
        this.wrapper.remove();
        this.element.show();
      }
    });

  }
  $.fn.newAutoComplete = function(isMulti) {
    var obj = $(this);
    create(obj, isMulti);
    obj.combobox();
  }
})(jQuery);
