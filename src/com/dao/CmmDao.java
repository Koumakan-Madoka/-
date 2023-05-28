package com.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.core.AbstractDao;
import com.util.MapUtil;
import com.util.StringUtil;

@Service
public class CmmDao extends AbstractDao{
	/**
	 * 查询code名称
	 * @param codetype
	 * @param code
	 * @return
	 */
	public String getCodeNm(String codetype, String code) {
		if (StringUtil.isEmpty(codetype) || StringUtil.isEmpty(code)) {
			return "";
		}
		String sql = "select * from code where codetype='"+codetype+"' and code='"+code+"'";
		Map<String, Object> rtMap = this.getInfo(sql);
		String codenm = MapUtil.isContains(rtMap, "name")?rtMap.get("name").toString():"未知("+code+")";
		return codenm;
	}
	
	/**
	 * 查询分类名称
	 * @param id
	 * @return
	 */
	public String getCategoryNm(Object id) {
		if (id==null ||"".equals(id)) {
			return "";
		}
		String sql = "select * from category where id='"+id+"'";
		Map<String, Object> rtMap = this.getInfo(sql);
		String name = MapUtil.isContains(rtMap, "name")?rtMap.get("name").toString():"已删除(id:"+id+")";
		return name;
	}
	
	public Map<String, Object> getUserInfo(Object id) {
		if (StringUtil.isEmpty(id)) {
			throw new RuntimeException("查询参数不能为空！");
		}
		String sql = "select * from user where id="+id;
		Map<String, Object> map = this.getInfo(sql);
		return map;
	}
	
	public String getUsername(Object id) {
		String username = "";
		if (!StringUtil.isEmpty(String.valueOf(id)) && !"0".equals(String.valueOf(id))) {
			String sql = "select * from user where id="+id;
			Map<String, Object> user = this.getInfo(sql);
			if (!MapUtil.isContains(user, "id")) {
				return "("+id+")已删除";
			}
			username = String.valueOf(user.get("name"));
			try {
				sql = "select * from userinfo20 where id="+id;
				Map<String, Object> userinfo = this.getInfo(sql);
				if (MapUtil.isContains(userinfo, "name")) {
					username = String.valueOf(userinfo.get("name"));
				}
			}catch (Exception e) {}
		}
		return username;
	}
	
	public String getUsericon(Object id) {
		String usericon = "";
		if (!StringUtil.isEmpty(String.valueOf(id)) && !"0".equals(String.valueOf(id))) {
			String sql = "select * from userinfoa where id="+id;
			Map<String, Object> map = this.getInfo(sql);
			usericon = "../../resource/img/defaultHead.png";
			if (MapUtil.isContains(map, "icon")) {
				usericon = String.valueOf(map.get("icon"));
			}
		}
		return usericon;
	}
	
}
