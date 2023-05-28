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
@RequestMapping("admin/stock/*")
public class StockController extends AbstractRestController{	
	@Autowired
	CmmDao dao;
	
	private String tbNm = "stock";
	private String tbNmPrdocut = "product";
	
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public String getPageList(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		pMap.put("tbNm", this.tbNm);
		pMap.put("yn", "Y");
		if (MapUtil.isContains(pMap, "name")) {
			pMap.put("where", " name like '%" + pMap.get("name") + "%'");
			pMap.put("name", "");
		}
		Page page = dao.getPage(pMap);
		List<Map<String, Object>> dataList = page.getDataList();
		if (dataList!=null) {
			for(Map<String, Object> map : dataList) {
				Map<String, Object> prdMap = dao.getInfo("select * from "+tbNmPrdocut+" where no='"+map.get("prd_no")+"'");
				map.put("prd_noNm", prdMap.get("name"));
				map.put("typeNm", dao.getCodeNm("STOCK_TYPE", String.valueOf(map.get("type"))));
				map.put("iuidNm", dao.getUsername(map.get("iuid")));
				map.put("itime", DateUtil.getDateStrByTimestamp(map.get("itime")));
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
	
	@RequestMapping(value = "add.do")
	@ResponseBody
	public String add(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		Map<String, Object> tObjMap = new HashMap<>();
		tObjMap.put("no", pMap.get("prd_no"));
		List<Map<String, Object>> haveList = dao.getList(tObjMap, tbNmPrdocut);
		if(haveList.size()==0) {
			return WebUtils.errorResp("服务编码不存在！");
		}
		int num = Integer.valueOf(String.valueOf(pMap.get("num")));
		if (!"10".equals(pMap.get("type"))) {
			if (num<Integer.valueOf(String.valueOf(haveList.get(0).get("num")))) {
				WebUtils.errorResp("服务库存不足！");
			}
			num = -num;
		}
		dao.runSql("update "+tbNmPrdocut+" set num=num+"+num+" where no='"+pMap.get("prd_no")+"'");
		pMap.put("num", num);
		pMap.put("iuid", request.getSession().getAttribute("uid"));
		dao.add(pMap, this.tbNm);
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
		dao.del(pMap.get("id").toString(), tbNm);
		return WebUtils.successResp(null,"操作成功");
	}
	
	
	
	
	

}
