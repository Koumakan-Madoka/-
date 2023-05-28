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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.AbstractRestController;
import com.core.Page;
import com.dao.CmmDao;
import com.util.DateUtil;
import com.util.MapUtil;
import com.util.MathUtil;
import com.util.WebUtils;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("admin/ord/*")
public class OrdController extends AbstractRestController{	
	@Autowired
	CmmDao dao;
	
	private String tbNm = "ord";
	private String tbNmPrdocut = "product";
	private String tbNmStock = "stock";
	
	@RequestMapping(value = "getPrdTree")
	@ResponseBody
	public String getPrdTree(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		JSONArray jA = new JSONArray();
		List<Map<String, Object>> cateList = dao.getList("select id,name from category where type='PRODUCT' order by sort");
		for (Map<String, Object> map : cateList) {
			String json = JSON.toJSONString(map);
			JSONObject jO = JSON.parseObject(json);
			List<Map<String, Object>> prdList = dao.getList("select * from "+tbNmPrdocut+" where status='10' and cid="+map.get("id"));
			for (Map<String, Object> m : prdList) {
				m.put("end", "true");
			}
			jO.put("children", JSONArray.parseArray(JSON.toJSONString(prdList)));
			jA.add(jO);
		}
		return jA.toJSONString();
	}
	
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
				Map<String, Object> prdMap = dao.getInfo("select * from "+tbNmPrdocut+" where no='"+map.get("prdno")+"'");
				map.put("prdnoNm", prdMap.get("name"));
				map.put("icon", prdMap.get("icon"));
				map.put("statusNm", dao.getCodeNm("ORD_STATUS", map.get("status").toString()));
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
		String[] prdnoA = String.valueOf(pMap.get("prdnos")).split(",");
		String[] numA = String.valueOf(pMap.get("nums")).split(",");
		//验证库存
		for (int i=0; i<prdnoA.length; i++) {
			Map<String, Object> prdInfo = dao.getInfo("select * from "+tbNmPrdocut+" where no='"+prdnoA[i]+"'");
			if (Integer.valueOf(numA[i]) > Integer.valueOf(String.valueOf(prdInfo.get("num")))) {
				return WebUtils.errorResp("有服务库存不足！");
			}
		}
		//保存订单
		for (int i=0; i<prdnoA.length; i++) {
			Map<String, Object> prdInfo = dao.getInfo("select * from "+tbNmPrdocut+" where no='"+prdnoA[i]+"'");
			Map<String, Object> tObjMap = new HashMap<>();
			String no = DateUtil.getCurrentDateForName1()+DateUtil.getCurrentMinute()+DateUtil.getRandNumber(4);
			tObjMap.put("no", no);
			tObjMap.put("prdno", prdnoA[i]);
			tObjMap.put("price", prdInfo.get("price"));
			tObjMap.put("num", numA[i]);
			tObjMap.put("total", Double.valueOf(prdInfo.get("price").toString())*Integer.valueOf(numA[i]));
			tObjMap.put("status", "10");
			tObjMap.put("cstnm", pMap.get("cstnm"));
			tObjMap.put("tel", pMap.get("tel"));
			tObjMap.put("addr", pMap.get("addr"));
			tObjMap.put("descr", pMap.get("descr"));
			tObjMap.put("iuid", request.getSession().getAttribute("uid"));
			dao.add(tObjMap, tbNm);
			//减库存
			tObjMap = new HashMap<>();
			tObjMap.put("descr", "订单["+no+"]销售");
			tObjMap.put("type", "20");
			tObjMap.put("prd_no", prdnoA[i]);
			tObjMap.put("num", "-"+numA[i]);
			tObjMap.put("iuid", request.getSession().getAttribute("uid"));
			dao.add(tObjMap, tbNmStock);
			dao.runSql("update "+tbNmPrdocut+" set num=num-"+numA[i]+" where no='"+ prdnoA[i] +"'");
		}
		return WebUtils.successResp(null,"操作成功");
	}
	
	/**
	 * 取消订单
	 * @param pMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "del")
	@ResponseBody
	public String del(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		Map<String, Object> ordMap = dao.getInfoById(pMap.get("id").toString(), tbNm);
		
		//加库存
		Map<String, Object> tObjMap = new HashMap<>();
		tObjMap.put("descr", "["+ordMap.get("no")+"]订单取消");
		tObjMap.put("type", "10");
		tObjMap.put("prd_no", ordMap.get("prdno"));
		tObjMap.put("num", ordMap.get("num"));
		tObjMap.put("iuid", request.getSession().getAttribute("uid"));
		dao.add(tObjMap, tbNmStock);
		dao.runSql("update "+tbNmPrdocut+" set num=num+"+ordMap.get("num")+" where no='"+ ordMap.get("prdno") +"'");
		pMap.put("status", "90");
		dao.update(pMap, tbNm);
		return WebUtils.successResp(null,"操作成功");
	}
	
	
	
	
	

}
