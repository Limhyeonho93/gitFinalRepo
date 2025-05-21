package kr.or.iei.cargo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.CompInfo;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class CargoRegisterFrmServlet
 */
@WebServlet("/cargo/cargoRegisterForm")
public class CargoRegisterFrmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargoRegisterFrmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회사 리스트를 얻어오기 위한 로직
		InvoiceService service = new InvoiceService();
		ArrayList<CompInfo> compInfoArr = service.getAllSeellerComp();
		request.setAttribute("compInfoArr", compInfoArr);
		//compInfoArr Request
		RequestDispatcher view=request.getRequestDispatcher("/WEB-INF/views/cargo/cargoRegister.jsp");
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
