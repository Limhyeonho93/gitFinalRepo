package kr.or.iei.tracking.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyWarehouseSummary {

    private String wareCd;
    private String wareName;
    private String manageNo;
    private String warehouseMoveid;

    private int totalWeight; // sum(gw)
    private int totalQty; // sum(no)
    private int allCount; // cargoCount
    private int tbCount; // impbondedCount

    private int inCount;
    private int outCount;

}
