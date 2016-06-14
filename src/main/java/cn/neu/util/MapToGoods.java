package cn.neu.util;

import java.util.ArrayList;
import java.util.List;

import cn.neu.global.Container;
import cn.neu.recv.GType;
import cn.neu.recv.Goods;
import cn.neu.vo.JXGoodsVo;

public class MapToGoods {
	public static List<JXGoodsVo> to(List<Goods> list) {
		List<JXGoodsVo> goods = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Goods g = list.get(i);
			int t = g.getType();
			JXGoodsVo jg = null;
			for (GType gt : Container.gType) {
				if (t == gt.getId()) {
					jg = new JXGoodsVo(g.getId() + "", g.getPrice() + "", g.getName(), g.getCount() + "",
							gt.getG_name());
				}
			}
			goods.add(jg);
		}
		return goods;
	}
}
