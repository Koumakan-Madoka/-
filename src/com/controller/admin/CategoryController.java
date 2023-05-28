package com.controller.admin;

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
import com.util.MapUtil;
import com.util.WebUtils;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("admin/category/*")
public class CategoryController extends AbstractRestController{	
	@Autowired
	CmmDao dao;
	
	private String tbNm = "category";
	
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public String getPageList(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		pMap.put("tbNm", this.tbNm);
		if (MapUtil.isContains(pMap, "name")) {
			pMap.put("where", " name like '%" + pMap.get("name") + "%'");
			pMap.put("name", "");
		}
		pMap.put("orderby", "sort asc");
		Page page = dao.getPage(pMap);
		List<Map<String, Object>> dataList = page.getDataList();
		if (dataList!=null) {
			for(Map<String, Object> map : dataList) {
			}
		}
		return WebUtils.responseLayuiJson(page);
	}
	
	@RequestMapping(value = "getInfo")
	@ResponseBody
	public String getInfo(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		Map<String, Object> info = dao.getInfoById(String.valueOf(pMap.get("id")), this.tbNm);
		String rtStr = JSON.toJSONString(info);
		return rtStr;
	}
	
	@RequestMapping(value = "update.do")
	@ResponseBody
	public String update(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		if (!MapUtil.isContains(pMap, "id")) {
			dao.add(pMap, this.tbNm);
		} else {
			dao.update(pMap, this.tbNm);
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
		dao.del(String.valueOf(pMap.get("id")), tbNm);
		return WebUtils.successResp(null,"操作成功");
	}
}
