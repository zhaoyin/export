# export-excel
支持任何数据输出到Excel，适用于前端导出／输出到Excel功能

例子

		```
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
			map.put(keys[0], "列" + i);
			map.put(keys[1], "列" + i);
			map.put(keys[2], "列" + i);
			map.put(keys[3], "列" + i);
			map.put(keys[4], "列" + i);
			map.put(keys[5], "列" + i);
			map.put(keys[6], 25);
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
			Column column = new Column(i, "ab" + i, keys[i], 150, type, (short) 2, false);
			columns.add(column);
		}

		Export export = new Export();
		export.export(columns, list, "测试Excel", "C:/");
		
		```
