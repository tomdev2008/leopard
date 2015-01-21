package io.leopard.biz.lucky;

import io.leopard.burrow.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

public class LuckyLottery {

	private List<Integer> weightList = new ArrayList<Integer>();
	private List<Integer> boundaryList = new ArrayList<Integer>();

	public void add(int weight) {
		this.weightList.add(weight);

		int boundary = 0;
		for (int num : weightList) {
			boundary += num;
		}
		boundaryList.add(boundary);
	}

	public int getIndexCount() {
		return this.weightList.size();
	}

	protected int getTotalWeight(int index) {
		int totalWeight = 0;
		for (int i = 0; i <= index; i++) {
			totalWeight += weightList.get(i);
		}
		return totalWeight;
	}

	public int randomWeight(int index) {
		int totalWeight = getTotalWeight(index);
		// System.out.println("totalWeight:" + totalWeight);
		int random = NumberUtil.random(totalWeight);
		for (int i = 0; i <= index; i++) {
			int boundary = boundaryList.get(i);
			if (random <= boundary) {
				return i;
			}
		}
		throw new RuntimeException("怎么会随机不到权重[" + random + "]?");
	}
}
