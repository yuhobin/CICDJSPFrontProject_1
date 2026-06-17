package com.sist.model;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.FoodDAO;
import com.sist.vo.FoodVO;

import java.io.PrintWriter;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// => Model 클래스 => 알려준다 (Spring)
@Controller
public class FoodModel {
	
	@RequestMapping("food/find.do")
	// invoke(request, response)
	public String food_find(HttpServletRequest request, HttpServletResponse response) {
		/*
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		String column=request.getParameter("column");
		if(column==null)
			column="address";
		String ss=request.getParameter("ss");
		if(ss==null)
			ss="마포";
		String[] types=request.getParameterValues("type");
		
		int curpage=Integer.parseInt(page);
		final int ROWSIZE=12;
		int start=(curpage*ROWSIZE)-ROWSIZE;
		
		Map map=new HashMap();
		map.put("ss", ss);
		map.put("start", start);
		map.put("column", column);
		map.put("fdArr", types);
		List<FoodVO> list=FoodDAO.foodFindData(map);
		int count=FoodDAO.foodFindCount(map);
		
		int totalpage=(int)(Math.ceil(count/(double)ROWSIZE));
		
		final int BLOCK=10;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		if(endPage>totalpage)
			endPage=totalpage;
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("count", count);
		request.setAttribute("ss", ss);
		// => 문제 발생 
		*/
		return "find.jsp";
	}
	@RequestMapping("food/find_ajax.do")
	public void food_find_ajax(HttpServletRequest request, HttpServletResponse response) {
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		String column=request.getParameter("column");
		if(column==null)
			column="address";
		String ss=request.getParameter("ss");
		if(ss==null)
			ss="마포";
		String[] types=request.getParameterValues("type");
		System.out.println(Arrays.toString(types));
		int curpage=Integer.parseInt(page);
		final int ROWSIZE=12;
		int start=(curpage*ROWSIZE)-ROWSIZE;
		
		Map map=new HashMap();
		map.put("ss", ss);
		map.put("start", start);
		map.put("column", column);
		map.put("fdArr", types);
		List<FoodVO> list=FoodDAO.foodFindData(map);
		int count=FoodDAO.foodFindCount(map);
		
		int totalpage=(int)(Math.ceil(count/(double)ROWSIZE));
		
		final int BLOCK=10;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		if(endPage>totalpage)
			endPage=totalpage;
		
		int i=0;
		// 자바 = >자바스크립트 형태로 변경 => JSON
		JSONArray arr=new JSONArray(); // List
		for(FoodVO vo:list) {
			JSONObject obj=new JSONObject();
			obj.put("no", vo.getNo());
			obj.put("name", vo.getName());
			obj.put("poster", vo.getPoster());
			obj.put("address", vo.getAddress());
			obj.put("type", vo.getType());
			// {no:1, name:'', poster:''...}
			if(i==0) {
				obj.put("curpage", curpage);
				obj.put("totalpage", totalpage);
				obj.put("startPage", startPage);
				obj.put("endPage", endPage);
				obj.put("count", count);
			}
			arr.add(obj); // [{},{}...]
			i++;
		}
		try {
			response.setContentType("text/plain;charset=UTF-8");
			//					일반 문자열 / HTML => text/html
			//					JSON => text/plain
			PrintWriter out=response.getWriter();
			out.write(arr.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
