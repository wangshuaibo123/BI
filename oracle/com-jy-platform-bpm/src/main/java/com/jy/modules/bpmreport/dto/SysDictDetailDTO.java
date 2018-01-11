package com.jy.modules.bpmreport.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 * 字典查询相关字典描述信息
 * @author xyz
 * @date 2016-3-1
 */
public class SysDictDetailDTO extends BaseDTO{
  private static final long serialVersionUID = 1L;
  private Long id;
  private Long dictId;
  private String dictDetailName;
  private String dictDetailValue;
  private String orderBy;
  private String validateState;
  private Long version;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getDictId()
  {
    return this.dictId;
  }

  public void setDictId(Long dictId)
  {
    this.dictId = dictId;
  }

  public String getDictDetailName()
  {
    return this.dictDetailName;
  }

  public void setDictDetailName(String dictDetailName)
  {
    this.dictDetailName = dictDetailName;
  }

  public String getDictDetailValue()
  {
    return this.dictDetailValue;
  }

  public void setDictDetailValue(String dictDetailValue)
  {
    this.dictDetailValue = dictDetailValue;
  }

  public String getOrderBy()
  {
    return this.orderBy;
  }

  public void setOrderBy(String orderBy)
  {
    this.orderBy = orderBy;
  }

  public String getValidateState()
  {
    return this.validateState;
  }

  public void setValidateState(String validateState)
  {
    this.validateState = validateState;
  }

  public Long getVersion()
  {
    return this.version;
  }

  public void setVersion(Long version)
  {
    this.version = version;
  }
}
