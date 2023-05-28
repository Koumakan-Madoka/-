package com.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.AbstractRestController;
import com.core.Page;
import com.dao.CmmDao;
import com.util.DateUtil;
import com.util.MapUtil;
import com.util.StringUtil;
import com.util.WebUtils;

@Controller
@SuppressWarnings("unchecked")
@RequestMapping("admin/user/*")
public class UserController extends AbstractRestController{	
	@Autowired
	CmmDao dao;
	
	private String tbNm = "user";
	
	@ResponseBody
	@RequestMapping("login")
	public String login(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		JSONObject json = new JSONObject();
		Boolean flag = false;
		String msg = "";
		String login = pMap.get("login").toString();
		String pwd = pMap.get("pwd").toString();
		String sql = "select * from user where login='"+ login +"'";
		if (MapUtil.isContains(pMap, "utype")){
			sql += " and utype='"+pMap.get("utype")+"'";
		}
		Map<String, Object> userInfo = dao.getInfo(sql);
		if (userInfo.isEmpty()) {
			msg = "登录账户不存在";
		} else {
			if ("20".equals(userInfo.get("status"))) {
				msg = "账户已冻结！";
			} else {
				if (pwd.equals(userInfo.get("pwd"))) {
					flag = true;
					HttpSession session = request.getSession();
					session.setAttribute("uid", userInfo.get("id"));
					session.setAttribute("name", userInfo.get("name"));
					session.setAttribute("login", userInfo.get("login"));
					session.setAttribute("utype", userInfo.get("utype"));
				} else {
					msg = "登录密码错误";
				}
			}
		}
		json.put("flag", flag);
		json.put("msg", msg);
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("logout")
	public String logout(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		session.setAttribute("uid", "");
		session.setAttribute("name", "");
		session.setAttribute("login", "");
		session.setAttribute("utype", "");
		json.put("flag", true);
		json.put("msg", "");
		return json.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("permission")
	public String permission(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		JSONObject json = new JSONObject();
		Boolean flag = false;
		String msg = "";
		HttpSession session = request.getSession();
		if (!StringUtil.isEmpty(session.getAttribute("uid"))) {
			flag = true;
		} else {
			msg = "请登录";
		}
		
		json.put("flag", flag);
		json.put("msg", msg);
		return json.toJSONString();
	}
	
	
	@RequestMapping(value = "getPageList")
	@ResponseBody
	public String getPageList(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		pMap.put("tbNm", "user");
		if (MapUtil.isContains(pMap, "name")) {
			pMap.put("where", " name like '%" + pMap.get("name") + "%'");
			pMap.put("name", "");
		}
		Page page = dao.getPage(pMap);
		List<Map<String, Object>> dataList = page.getDataList();
		if (dataList!=null) {
			for(Map<String, Object> map : dataList) {
				String name = dao.getCodeNm("USER_TYPE", String.valueOf(map.get("utype")));
				map.put("usertypeNm", name);
				map.put("statusNm", dao.getCodeNm("USER_STATUS", String.valueOf(map.get("status"))));
				map.put("itime", DateUtil.getDateStrByTimestamp(map.get("itime")));
			}
		}
		return WebUtils.responseLayuiJson(page);
	}
	
	@RequestMapping(value = "getInfo")
	@ResponseBody
	public String getInfo(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		if (!MapUtil.isContains(pMap, "id")) {
			pMap.put("id", request.getSession().getAttribute("uid"));
		}
		Map<String, Object> info = dao.getInfoById(String.valueOf(pMap.get("id")), this.tbNm);
		String rtStr = JSON.toJSONString(info);
		return rtStr;
	}
	
	@RequestMapping(value = "update.do")
	@ResponseBody
	public String update(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		if (!MapUtil.isContains(pMap, "id")) {
			String sql = "select 1 from " + this.tbNm + " where login='"+pMap.get("login")+"'";
			List<Map<String, Object>> havaList = dao.getList(sql);
			if (havaList!=null && havaList.size()>0) {
				return WebUtils.errorResp("登录账号已存在！");
			}
			dao.add(pMap, this.tbNm);
		} else {
			dao.update(pMap, this.tbNm);
		}
		return WebUtils.successResp(null,"操作成功");
	}
	
	@RequestMapping(value = "resetPwd.do")
	@ResponseBody
	public String resetPwd(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		dao.update(pMap, this.tbNm);
		return WebUtils.successResp(null,"操作成功");
	}
	
	@RequestMapping(value = "updateMyPwd.do")
	@ResponseBody
	public String updateMyPwd(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		HttpSession session = request.getSession();
		String id = String.valueOf(session.getAttribute("uid"));
		Map<String, Object> userInfo = dao.getInfoById(id, this.tbNm);
		if (!String.valueOf(pMap.get("pwdold")).equals(userInfo.get("pwd"))) {
			return WebUtils.errorResp("原密码错误！");
		}
		dao.update(pMap, this.tbNm);
		return WebUtils.successResp(null,"操作成功");
	}
	
	/**
	 * 注册
	 * @param pMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "regist")
	@ResponseBody
	public String regist(@RequestParam Map<String, Object> pMap, HttpServletRequest request){
		String sql = "select 1 from " + this.tbNm + " where login='"+pMap.get("login")+"'";
		List<Map<String, Object>> havaList = dao.getList(sql);
		if (havaList!=null && havaList.size()>0) {
			return WebUtils.errorResp("登录账号已存在！");
		}
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
		dao.del(String.valueOf(pMap.get("id")), tbNm);
		return WebUtils.successResp(null,"操作成功");
	}

}
