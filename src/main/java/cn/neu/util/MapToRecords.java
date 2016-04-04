package cn.neu.util;

import java.util.ArrayList;
import java.util.List;
import cn.neu.recv.Record;
import cn.neu.vo.JXRecordsVo;

public class MapToRecords {
	public static List<JXRecordsVo> to(List<Record> list) {
		List<JXRecordsVo> goods = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Record g = list.get(i);
			int t = g.getType();
			JXRecordsVo jg = null;
			if (t == 1) {
				jg = new JXRecordsVo(g.getId() + "", g.getGoods_name(), g.getCreate_time(), g.getUpdate_time(),
						g.getComment(), g.getPrice() + "", "销售出库");
			} else if (t == 2) {
				jg = new JXRecordsVo(g.getId() + "", g.getGoods_name(), g.getCreate_time(), g.getUpdate_time(),
						g.getComment(), g.getPrice() + "","花销");
			} else if (t == 3) {
				jg = new JXRecordsVo(g.getId() + "", g.getGoods_name(), g.getCreate_time(), g.getUpdate_time(),
						g.getComment(), g.getPrice() + "", "生产入库");
			} else if (t == 4) {
				jg = new JXRecordsVo(g.getId() + "", g.getGoods_name(), g.getCreate_time(), g.getUpdate_time(),
						g.getComment(), g.getPrice() + "", "修改商品价格");
			}
			goods.add(jg);
		}
		return goods;
	}
}
