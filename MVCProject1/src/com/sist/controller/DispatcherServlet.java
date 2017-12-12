package com.sist.controller;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.MovieDetail;
import com.sist.model.MovieList;
import com.sist.model.*;

import java.util.*;	//Map에 저장 (요청=>클래스(모델) 매칭)

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String[] strCls = {
			"com.sist.model.MovieList",
			"com.sist.model.MovieDetail"
	};
	private String[] strCmd = {
			"list","detail"
	};
	// <bean id="list" class"com.sist.model.MovieList" />
	// csv => list,com.sist.model.MovieList
	/*
	 * 	key		   value
	 * 	list   	 new MovieList()    Class.forName()
	 *	 detail  new MovieDetail()
	 */
	private Map clsMap = new HashMap();
	//HashMap, Hashtable  HashMap을 많이 사용
	public void init(ServletConfig config) throws ServletException {	//init메소드는 생성자역할!!
		try {
			for(int i=0;i<strCls.length;i++) {		//식당의 메뉴판 기능
				Class clsName = Class.forName(strCls[i]);
				Object obj = clsName.newInstance();
				clsMap.put(strCmd[i], obj);
				//singleton
			}
			//이렇게 코딩할 수도 있지만, 데이터가 많을경우, for문 돌리는게낫지 put을 천번,만번 하는건 비효율적..문제가 생길수도있다!
			//clsMap.put("list",new MovieList());
			//clsMap.put("detail", new MovieDetail());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//list.do , detail.do(주로 이방법을쓰지)   => movie.do?cmd=list
			String cmd = request.getRequestURI();
			//URI : 사용자가 주소입력란에 요청한 파일
			// http://localhost:8080/MVCProject1/list.do
			// URI : /MVCProject1/list.do
			// 		 ============ /MVCProject1요만큼이 ContextPath
			// 사용자가 요청한 내용
			cmd = cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));
			// 요청을 처리 => 모델클래스(클래스,메소드)
			model m = (model)clsMap.get(cmd);
			// model => 실행을 한 후에 결과를 request에 담아 달라
			// Call By Reference => 주소를 넘겨주고 주소에 값을 채운다.
			String jsp = m.execute(request);
			//JSP에 request,session값을 전송
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			// jsp의 _jspService()를 호출한다.
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}













