package com.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.core.AbstractRestController;
import com.core.Page;
import com.dao.CmmDao;
import com.util.DateUtil;
import com.util.MapUtil;
import com.util.WebUtils;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("admin/userinfo20/*")
public class UserInfo20Controller extends AbstractRestController{	
	@Autowired
	CmmDao dao;
	
	private String tbNm = "userinfo20";
	private String tbNmUser = "user";
	
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public String getPageList(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		String sql = "Select a.id,b.name name1,b.* From "+tbNmUser+" a left join "+tbNm+" b on a.id=b.id where utype!='10' ";
		if (MapUtil.isContains(pMap, "name")) {
			sql += " and b.name like '%" + pMap.get("name") + "%'";
		}
		if (MapUtil.isContains(pMap, "id")) {
			sql += " and a.id="+pMap.get("id");
		}
		if (MapUtil.isContains(pMap, "no")) {
			sql += " and b.no='"+pMap.get("no")+"'";
		}
		if (MapUtil.isContains(pMap, "cid")) {
			sql += " and b.cid="+pMap.get("cid");
		}
		pMap.put("sql", sql);
		Page page = dao.getPage(pMap);
		List<Map<String, Object>> dataList = page.getDataList();
		if (dataList!=null) {
			for(Map<String, Object> map : dataList) {
				map.put("cidNm", dao.getCategoryNm(map.get("cid")));
				if(!MapUtil.isContains(map, "icon")) {
					map.put("icon", "../../resource/img/defaultHead.png");
				}
			}
		}
		return WebUtils.responseLayuiJson(page);
	}
	
	@RequestMapping(value = "getTPageList")
	@ResponseBody
	public String getTPageList(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		String uid = request.getSession().getAttribute("uid").toString();
		String sql = "Select a.id,b.name name1,b.* From "+tbNmUser+" a left join "+tbNm+" b on a.id=b.id  left join userinfo30 c on b.cid=c.cid where utype='20' and c.id="+uid;
		if (MapUtil.isContains(pMap, "name")) {
			sql += " and b.name like '%" + pMap.get("name") + "%'";
		}
		if (MapUtil.isContains(pMap, "id")) {
			sql += " and a.id="+pMap.get("id");
		}
		if (MapUtil.isContains(pMap, "cid")) {
			sql += " and b.cid="+pMap.get("cid");
		}
		pMap.put("sql", sql);
		Page page = dao.getPage(pMap);
		List<Map<String, Object>> dataList = page.getDataList();
		if (dataList!=null) {
			for(Map<String, Object> map : dataList) {
				map.put("cidNm", dao.getCategoryNm(map.get("cid")));
				if(!MapUtil.isContains(map, "icon")) {
					map.put("icon", "../../resource/img/defaultHead.png");
				}
			}
		}
		return WebUtils.responseDataToJson(page);
	}
	
	@RequestMapping(value = "getInfo.do")
	@ResponseBody
	public String getInfo(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		if (!MapUtil.isContains(pMap, "id")) {
			pMap.put("id", request.getSession().getAttribute("uid"));
		}
		Map<String, Object> info = dao.getInfoById(String.valueOf(pMap.get("id")), this.tbNm);
		info.put("itime", DateUtil.getDateStrByTimestamp(info.get("itime")));
		info.put("cidNm", dao.getCategoryNm(info.get("cid")));
		if(!MapUtil.isContains(info, "icon")) {
			info.put("icon", "../../resource/img/defaultHead.png");
		}
		String rtStr = JSON.toJSONString(info);
		return rtStr;
	}
	
	@RequestMapping(value = "update.do")
	@ResponseBody
	public String update(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		if (!MapUtil.isContains(pMap, "id")) {
			pMap.put("id", request.getSession().getAttribute("uid"));
		}
		
		Map<String, Object> info = dao.getInfoById(String.valueOf(pMap.get("id")), this.tbNm);
		if (!MapUtil.isContains(info, "id")) {
			dao.add(pMap, this.tbNm);
		} else {
			dao.update(pMap, this.tbNm);
			Map<String, Object> tObjMap = new HashMap<String, Object>();
			tObjMap.put("id", pMap.get("id"));
			tObjMap.put("name", pMap.get("name"));
			dao.update(tObjMap, "user");
		}
		return WebUtils.successResp(null,"操作成功");
	}

	/**
	 * 删除
	 * @param pMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		dao.del(String.valueOf(pMap.get("id")), tbNmUser);
		dao.del(String.valueOf(pMap.get("id")), tbNm);
		return WebUtils.successResp(null,"操作成功");
	}
	
}
