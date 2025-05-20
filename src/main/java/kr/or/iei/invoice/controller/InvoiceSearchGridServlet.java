package kr.or.iei.invoice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.Invoice;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class InvoiceSearchGrids
 */
@WebServlet("/invoice/dataGrid")
public class InvoiceSearchGridServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceSearchGridServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Date from = Date.valueOf(request.getParameter("from"));
		Date to = Date.valueOf(request.getParameter("to"));
		
		InvoiceService service = new InvoiceService();
		
		HttpSession session = request.getSession(false); // 기존 세션만 가져옴
		User loginUser = (User) session.getAttribute("user");
		
		ArrayList<Invoice> arr = new ArrayList<Invoice>();
		if(loginUser.getUserLevel().equals("1")) {
			arr = service.allInvoice(from,to);
		}else {
			arr = service.allSelerInvoice(from,to,loginUser);
		}
		
		// 3. 응답 데이터 JSON 변환
	    response.setContentType("application/json; charset=UTF-8");
	    Gson gson = new Gson();
	    response.getWriter().print(gson.toJson(arr));  // 클라이언트에 JSON 응답

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
