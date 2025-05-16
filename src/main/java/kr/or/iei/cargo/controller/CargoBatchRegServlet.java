package kr.or.iei.cargo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import kr.or.iei.common.RenamePolicy;

/**
 * Servlet implementation class CargoBatchRegServlet
 */
@WebServlet("/cargo/cargoBatchReg")
@MultipartConfig
public class CargoBatchRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargoBatchRegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 InputStream is = request.getPart("cargoFile").getInputStream();  // "cargoFile" 파라미터 이름으로 파일을 받음
		 
		// CargoMainImport 클래스 객체 생성
         CargoMainImport cargoImport = new CargoMainImport();

         // 엑셀 파일을 처리하는 메서드 호출
         // InputStream을 직접 전달
         cargoImport.readExcel(is);

         // 처리 완료 후 응답 메시지
         response.getWriter().write("엑셀 파일이 성공적으로 처리되었습니다.");
		 
		/*String toDay=new SimpleDateFormat("yyyyMMdd").format(new Date()); //"20250509"
		
		//C드라이브부터 webapp 폴더까지 경로 : C:serverworkspace
		String rootPath=request.getSession().getServletContext().getRealPath("/");
		
		//실제 파일 저장 경로 지정
		String savePath=rootPath+"resources/upload/"+toDay+"/";
		
		//업로드 파일의 최대 크기 지정
		int maxSize=1024*1024*100; //100 Mega Byte
		
		File dir=new File(savePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		//cargoFile
		MultipartRequest mRequest=new MultipartRequest(request, savePath,maxSize,"UTF-8", new RenamePolicy());
		
		String fileName= (String) mRequest.getFileNames().nextElement();
		String originalFileName = mRequest.getOriginalFileName(fileName); //사용자가 업로드한 파일명
		String savedFileName = mRequest.getFilesystemName(fileName);//변경된 파일명
		
		// 파일이 정상적으로 업로드 되었을 때
		if (savedFileName != null) {
		    // 파일 처리 로직 (예: DB에 파일 정보 저장)
		    System.out.println("원본 파일명: " + originalFileName);
		    System.out.println("저장된 파일명: " + savedFileName);
		} else {
		    System.out.println("파일 업로드 실패");
		}
		*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
