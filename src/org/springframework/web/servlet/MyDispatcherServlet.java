package org.springframework.web.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cm.security.SqlDay;

import util.LogOut;

public class MyDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 8600797796665227188L;
	private static SqlDay sqlday = new SqlDay();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
	
	public static SqlDay getDay() {
		return sqlday;
	}
	
	@Override
	protected void doDispatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String day = request.getParameter("sqlday");
		LogOut.log.debug("sqlday=========="+day);
		if (day == null) {
			Calendar cal = Calendar.getInstance();
			day = sdf.format(cal.getTime());
		}else{
			day=day.split(" ")[0].replace("-", "_");
		}
		
		sqlday.set(day);

		super.doDispatch(request, response);
	}

}
