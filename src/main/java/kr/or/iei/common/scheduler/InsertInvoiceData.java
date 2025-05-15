package kr.or.iei.common.scheduler;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.or.iei.invoice.model.service.InvoiceService;
import kr.or.iei.invoice.model.vo.DailyInsertInvoice;
import kr.or.iei.invoice.model.vo.ShoppingCost;

public class InsertInvoiceData implements Runnable{
	
	public void run() {
		LocalDate yesterday = LocalDate.now().minusDays(1);
		String yesterdayStr = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        InvoiceService service = new InvoiceService();
        
        
        ArrayList<DailyInsertInvoice> dailyInsertInvoice = service.getDailyInvoiceValue(yesterdayStr);
        
        if(dailyInsertInvoice.isEmpty()) {
        	System.out.println(yesterday+"의 출하된 화물은 없습니다.");
        	return;
        }
        
        ArrayList<ShoppingCost> shoppingCost = service.getShoppingCost();
        
        // 회사코드를 기준으로 가격 무게 를 넣을 map
        Map<String, int[]> summaryMap = new HashMap<>();

        
        for (DailyInsertInvoice invoice : dailyInsertInvoice) {
            String compCd = invoice.getCompCd();
            String disGrade = invoice.getDisGrade();
            int totalWeight = invoice.getTotalWeight();
            String outDate = invoice.getOutDate();

            int matchedPrice = 0;

            // ✅ 무게 기준 내림차순 정렬 후 매칭
            for (ShoppingCost sc : shoppingCost.stream()
                    .filter(sc -> sc.getDisGrade().equals(disGrade)) // disgrade를 기준으로 where
                    .sorted((a, b) -> Integer.compare(b.getWeight(), a.getWeight()))  // weight 큰 것부터 비교
                    .toList()) {

                if (totalWeight <= sc.getWeight()) {
                    matchedPrice = sc.getPrice();
                }
            }

            if (matchedPrice > 0) {
            	 int[] summary = summaryMap.getOrDefault(compCd, new int[]{0, 0});
                 summary[0] += totalWeight;
                 summary[1] += matchedPrice;
                 summaryMap.put(compCd, summary);
            } else {
            }
        }
        
        // T_invoice에 db 넣어주기
        for (Map.Entry<String, int[]> entry : summaryMap.entrySet()) {
        	String compCd = entry.getKey();
            int weight = entry.getValue()[0];
            int amount = entry.getValue()[1];

            int res = service.insertInvoice(compCd, yesterdayStr, amount, weight);
            
        }
        
        System.out.println("인보이스 테이블 작성 완료");
        
        
    }
}
