/**
 * 
 */
package com.crt.export;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crt.export.core.Export;
import com.crt.export.models.Column;

/**
 * @author UOrder
 *
 */
public class ExportTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		/*
		 * for(int i=0;i<13;i++){ list.add(new
		 * Object[]{"列1","列2","列3",800+i,"列5",10000+i,800+i,10000+i,"列9","列10",
		 * "列11","列12","列13"}); }
		 */
		String[] keys = { "branchSequence", "tradeCode", "tradeStatus", "traderDate", "merchantId", "userInfo",
				"totalFee", "userFee", "userFee1", "userFe23e", "user232Fee" };
		List<Column> columns = new ArrayList<Column>();
		for (int i = 0; i < 13; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(keys[0], "列1" + i);
			map.put(keys[1], "列1" + i);
			map.put(keys[2], "列1" + i);
			map.put(keys[3], "列1" + i);
			map.put(keys[4], "列1" + i);
			map.put(keys[5], "列1" + i);
			map.put(keys[6], 11111);
			map.put(keys[7], 12.245);
			map.put(keys[8], new Date());
			map.put(keys[9], "列1" + i);
			map.put(keys[10], "列1" + i);
			list.add(map);

		}
		for (int i = 0; i < 11; i++) {
			short type = (short) 1;
			if (i == 6)
				type = (short) 2;
			if (i == 7)
				type = (short) 3;
			if (i == 8)
				type = (short) 4;
			Column column = new Column(i, "ab" + i, keys[i], 150, type, (short) 2, false);
			columns.add(column);
		}

		Export export = new Export();
		export.export(columns, list, "测试Excel");
	}

}
