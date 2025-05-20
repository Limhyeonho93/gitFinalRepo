package kr.or.iei.invoice.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.CargoUnitInvoice;
import kr.or.iei.invoice.model.vo.Invoice;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class InvoiceExcelDownServlet
 */
@WebServlet("/InvoiceExcelDownServlet")
public class InvoiceExcelDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvoiceExcelDownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String compCd = request.getParameter("compCd");
		Date from = Date.valueOf(request.getParameter("from"));
		Date to = Date.valueOf(request.getParameter("to"));
		
		InvoiceService service = new InvoiceService();
		ArrayList<Invoice> sheet1Arr = new ArrayList<Invoice>();

		User loginUser = new User();
		loginUser.setCompCd(compCd);
		sheet1Arr = service.allSelerInvoice(from,to,loginUser);
		
		ArrayList<CargoUnitInvoice> sheet2Arr = service.trackingNoInvoice(request.getParameter("from"), request.getParameter("to"), compCd);

		 Workbook wb = new XSSFWorkbook();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
