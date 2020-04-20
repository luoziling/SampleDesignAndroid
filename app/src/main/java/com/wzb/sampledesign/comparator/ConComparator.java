package com.wzb.sampledesign.comparator;

import com.wzb.sampledesign.pojo.Conclusion;

import java.util.Comparator;

/**
 * Date: 2019/10/17
 * Author:Satsuki
 * Description:
 */
public class ConComparator implements Comparator<Conclusion> {
	@Override
	public int compare(Conclusion conclusion, Conclusion t1) {
		if (conclusion.getPriority()>t1.getPriority()){
			return 1;
		}else if (conclusion.getPriority()==t1.getPriority()){
			return 0;
		}else{
			return -1;
		}

	}
}
