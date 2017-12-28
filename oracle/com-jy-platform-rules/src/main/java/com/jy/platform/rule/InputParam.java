package com.jy.platform.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 规则引擎的输入参数，通过Map封装多个简单类型的参数值。
 * 实现了Map接口
 * @author zhangyu<br>
 * 使用方法<br>
 * <code>
 * import com.jy.platform.rule.InputParam;<br>
 * 
 * rule "test"<br>
 * when<br>
 *     &nbsp;&nbsp;&nbsp;&nbsp; // 从参数的Map中取值进行判断，取值可以有两种方法this.get("key1") 或者 this["key2"]<br>
 *     &nbsp;&nbsp;&nbsp;&nbsp; m: InputParam(this.get("key1") == "value1", value2: this["key2"])<br>
 * then<br>
 *     &nbsp;&nbsp;&nbsp;&nbsp; // 从参数的Map中取值<br>
 *     &nbsp;&nbsp;&nbsp;&nbsp; System.out.println(m.get("key1"));<br>
 *     &nbsp;&nbsp;&nbsp;&nbsp; System.out.println(value2);<br>
 * end<br>
 * </code>
 */
public class InputParam implements Map<String, Object> {
	
	private Map<String, Object> map = new HashMap<String, Object>();

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public Object put(String key, Object value) {
		return map.put(key, value);
	}

	public Object remove(Object key) {
		return map.remove(key);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
	}

	public void clear() {
		map.clear();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Collection<Object> values() {
		return map.values();
	}

	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		return map.equals(o);
	}

	public int hashCode() {
		return map.hashCode();
	}
	
//	public Object get(String key) {
//		return map.get(key);
//	}
//	
//	public void put(String k, Object v) {
//		map.put(k, v);
//	}

}
