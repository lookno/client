package cn.neu.util;

import java.util.ArrayList;
import java.util.List;

import cn.neu.global.Container;
import cn.neu.recv.RType;
import cn.neu.recv.Record;
import cn.neu.vo.JXRecordsVo;

public class MapToRecords {
	public static List<JXRecordsVo> to(List<Record> list) {
		List<JXRecordsVo> goods = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Record g = list.get(i);
			int t = g.getType();
			JXRecordsVo jg = null;
			for (RType rt : Container.rType) {
				if (t == rt.getId()) {
					jg = new JXRecordsVo(g.getId() + "", g.getGoods_name(), g.getCreate_time(), g.getUpdate_time(),
							g.getComment(), g.getPrice() + "", rt.getR_name());
				}
			}
			goods.add(jg);
		}
		return goods;
	}
}
