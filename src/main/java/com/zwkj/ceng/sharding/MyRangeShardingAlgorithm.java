package com.zwkj.ceng.sharding;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import com.google.common.collect.Range;

/**
 * Created by zwhd on 2020/3/25
 */
public class MyRangeShardingAlgorithm implements RangeShardingAlgorithm<Integer> {
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Integer> shardingValue) {
		Collection<String> collect = new ArrayList<>();
		Range<Integer> valueRange = shardingValue.getValueRange();
		for (Integer i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
			for (String each : availableTargetNames) {
				if (each.endsWith(i % 2 + "")) {
					collect.add(each);
				} else if (each.endsWith(i % 2 + 2 + "")) {
					collect.add(each);
				}
			}
		}
		return collect;
	}
}
