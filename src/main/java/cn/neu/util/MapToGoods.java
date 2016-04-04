package cn.neu.util;

import java.util.ArrayList;
import java.util.List;
import cn.neu.recv.Goods;
import cn.neu.vo.JXGoodsVo;

public class MapToGoods {
	public static List<JXGoodsVo> to(List<Goods> list) {
		List<JXGoodsVo> goods = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Goods g = list.get(i);
			int t = g.getType();
			JXGoodsVo jg =null;
			if(t==1){
				jg = new JXGoodsVo(g.getId() + "", g.getPrice() + "", g.getName(), g.getCount() + "","生产的商品");
			}else if(t==2){
				jg = new JXGoodsVo(g.getId() + "", g.getPrice() + "", g.getName(), g.getCount() + "","购入的商品");
			}
			goods.add(jg);
		}
		return goods;
	}
}
