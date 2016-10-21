/**
 * 
 */
package com.crt.export;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crt.excel.exports.Export;
import com.crt.excel.exports.IExportCallback;
import com.crt.excel.models.ExportColumn;

/**
 * @author UOrder
 *
 */
public class ExportTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] keys = { "name", "code", "status", "memo", "free1", "free2", "marketPrice", "price", "saledate",
				"ufree1", "ufree2" };
		List<ExportColumn> exportColumns = new ArrayList<ExportColumn>();
		for (int i = 0; i < 13; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(keys[0], "列" + i);
			map.put(keys[1], "列" + i);
			map.put(keys[2], "列" + i);
			map.put(keys[3], "列" + i);
			map.put(keys[4], "列" + i);
			map.put(keys[5], "列" + i);
			map.put(keys[6], 25 + i);
			map.put(keys[7], 12.245);
			map.put(keys[8], new Date());
			map.put(keys[9], "列" + i);
			map.put(keys[10], "列" + i);
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
			ExportColumn exportColumn = new ExportColumn(i, "ab" + i, keys[i], 150, type, (short) 2);
			exportColumns.add(exportColumn);
		}

		IExportCallback<String> callback=new IExportCallback<String>(){

			public void onSuccess(String result) {
				System.out.println("Future:" + result);
			}

			public void onFailure(Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
		};
//		IJsonConverter converter=Export.getInstance().getJsonConverter();
		Export.getInstance().asyncExport(exportColumns, list, "测试Excel", callback);
		
	}

}
