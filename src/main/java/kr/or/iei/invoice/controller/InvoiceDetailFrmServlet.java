package kr.or.iei.invoice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.CompInfo;

/**
 * Servlet implementation class InvoiceDetailFrmServlets
 */
@WebServlet("/invoice/detailFrm")
public class InvoiceDetailFrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceDetailFrmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String compCd = request.getParameter("compCd");
		String from = request.getParameter("from");
		String to = request.getParameter("to");

		InvoiceService service = new InvoiceService();
		
		ArrayList<CompInfo> compInfoArr = service.getAllSeellerComp();
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/invoice/detailSearch.jsp");
		
		request.setAttribute("compCd", compCd);
		request.setAttribute("from", from);
		request.setAttribute("to", to);
		request.setAttribute("compInfoArr", compInfoArr);
		
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
