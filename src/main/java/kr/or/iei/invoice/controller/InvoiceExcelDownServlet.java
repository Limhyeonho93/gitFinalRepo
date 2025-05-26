package kr.or.iei.invoice.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.CargoUnitInvoice;
import kr.or.iei.invoice.model.vo.Invoice;
import kr.or.iei.user.model.vo.User;

/**
 * Servlet implementation class InvoiceExcelDownServlet
 */
@WebServlet("/invoice/downExcel")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String compCd = request.getParameter("compCd");
		Date from = Date.valueOf(request.getParameter("from"));
		Date to = Date.valueOf(request.getParameter("to"));

		InvoiceService service = new InvoiceService();
		ArrayList<Invoice> sheet1Arr = new ArrayList<Invoice>();

		User loginUser = new User();
		loginUser.setCompCd(compCd);
		sheet1Arr = service.allSelerInvoice(from, to, loginUser);

		ArrayList<CargoUnitInvoice> sheet2Arr = service.trackingNoInvoice(request.getParameter("from"),
				request.getParameter("to"), compCd);

		Workbook wb = new XSSFWorkbook();
		// 엑셀 폰트와 굵기 설정
		CellStyle boldStyle = wb.createCellStyle();
		Font boldFont = wb.createFont();
		boldFont.setBold(true);
		boldStyle.setFont(boldFont);

		// 숫자 콤마 설정
		DecimalFormat commaFormat = new DecimalFormat("#,###");
		// 날짜 설정
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		int totalBox = 0;
		for (CargoUnitInvoice c : sheet2Arr) {
			totalBox += c.getNo();
		}
		// 1시트 요약 청구서
		Sheet sheet1 = wb.createSheet("KH OMS 청구서");
		sheet1.createRow(0).createCell(0).setCellValue("KH OMS 청구서");
		if (!sheet1Arr.isEmpty()) {
			Invoice invoice = sheet1Arr.get(0);

			sheet1.createRow(2).createCell(0).setCellValue("청구대상 회사");
			sheet1.getRow(2).createCell(1).setCellValue(invoice.getCompName());

			sheet1.createRow(3).createCell(0).setCellValue("청구기간");
			sheet1.getRow(3).createCell(1).setCellValue(dateFormat.format(from) + " ~ " + dateFormat.format(to));

			sheet1.createRow(4).createCell(0).setCellValue("총 청구 금액");
			sheet1.getRow(4).createCell(1).setCellValue(commaFormat.format(invoice.getTotalPayment()) + " 원");

			sheet1.createRow(5).createCell(0).setCellValue("총 중량");
			sheet1.getRow(5).createCell(1).setCellValue(invoice.getTotalWeight() + " kg");

			sheet1.createRow(6).createCell(0).setCellValue("총 화물 갯수");
			sheet1.getRow(6).createCell(1).setCellValue(totalBox + " 건");
		}
		// 2시트 상세 내역
		Sheet sheet2 = wb.createSheet("청구 상세내역");
		String[] headers = { "회사명", "회사코드", "관리번호", "이동ID", "송장번호", "입고일", "출고일", "박스수", "중량", "수하인", "주소", "판매자" };

		Row headerRow = sheet2.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			headerRow.createCell(i).setCellValue(headers[i]);
		}

		int rowNum = 1;
		for (CargoUnitInvoice c : sheet2Arr) {
			Row row = sheet2.createRow(rowNum++);
			row.createCell(0).setCellValue(c.getCompName());
			row.createCell(1).setCellValue(c.getCompCd());
			row.createCell(2).setCellValue(c.getManageNo());
			row.createCell(3).setCellValue(c.getWarehouseMoveid());
			row.createCell(4).setCellValue(c.getTrackingNo());
			row.createCell(5).setCellValue(c.getInDate() != null ? c.getInDate() : "");
			row.createCell(6).setCellValue(c.getOutDate() != null ? c.getOutDate() : "");
			row.createCell(7).setCellValue(c.getNo());
			row.createCell(8).setCellValue(c.getGw());
			row.createCell(9).setCellValue(c.getReceiverName());
			row.createCell(10).setCellValue(c.getReceiverAdd());
			row.createCell(11).setCellValue(c.getSellerName());
		}

		// 다운로드 응답 설정
		String today = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
		try {
		    // ... 기존 로직 ...
		    response.reset(); // 혹시 버퍼에 남아있는 출력 제거
		    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		    response.setHeader("Content-Disposition", "attachment; filename=invoice_" + today + ".xlsx");
		    wb.write(response.getOutputStream());
		    wb.close();
		} catch (Exception e) {
		    e.printStackTrace();
		    response.setContentType("text/html;charset=UTF-8");
		    response.getWriter().write("<script>alert('엑셀 다운로드 실패');</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
