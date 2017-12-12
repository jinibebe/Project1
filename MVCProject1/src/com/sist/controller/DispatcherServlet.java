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

import java.util.*;	//Map�� ���� (��û=>Ŭ����(��) ��Ī)

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
	//HashMap, Hashtable  HashMap�� ���� ���
	public void init(ServletConfig config) throws ServletException {	//init�޼ҵ�� �����ڿ���!!
		try {
			for(int i=0;i<strCls.length;i++) {		//�Ĵ��� �޴��� ���
				Class clsName = Class.forName(strCls[i]);
				Object obj = clsName.newInstance();
				clsMap.put(strCmd[i], obj);
				//singleton
			}
			//�̷��� �ڵ��� ���� ������, �����Ͱ� �������, for�� �����°Գ��� put�� õ��,���� �ϴ°� ��ȿ����..������ ��������ִ�!
			//clsMap.put("list",new MovieList());
			//clsMap.put("detail", new MovieDetail());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//list.do , detail.do(�ַ� �̹��������)   => movie.do?cmd=list
			String cmd = request.getRequestURI();
			//URI : ����ڰ� �ּ��Է¶��� ��û�� ����
			// http://localhost:8080/MVCProject1/list.do
			// URI : /MVCProject1/list.do
			// 		 ============ /MVCProject1�丸ŭ�� ContextPath
			// ����ڰ� ��û�� ����
			cmd = cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));
			// ��û�� ó�� => ��Ŭ����(Ŭ����,�޼ҵ�)
			model m = (model)clsMap.get(cmd);
			// model => ������ �� �Ŀ� ����� request�� ��� �޶�
			// Call By Reference => �ּҸ� �Ѱ��ְ� �ּҿ� ���� ä���.
			String jsp = m.execute(request);
			//JSP�� request,session���� ����
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			// jsp�� _jspService()�� ȣ���Ѵ�.
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}













