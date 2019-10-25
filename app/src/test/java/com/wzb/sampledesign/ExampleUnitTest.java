package com.wzb.sampledesign;

import com.alibaba.fastjson.JSON;
import com.wzb.sampledesign.pojo.MatrixStorage;
import com.wzb.sampledesign.ui.home.HomeFragment;
import com.wzb.sampledesign.util.Constant;
import com.wzb.sampledesign.util.FastjsonUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void testJson(){
		MatrixStorage matrixStorage = new MatrixStorage();

		// 此Demo中只有一层准则层那么value就是根节点内容就是项目名
		matrixStorage.setValue(Constant.PROJECT_NAME);
		matrixStorage.setProjectName(Constant.PROJECT_NAME);
		matrixStorage.setI(1);
		matrixStorage.setJ(1);
		matrixStorage.setMatrixValue(1.0);
//		JSON.toJSONString()

		System.out.println(FastjsonUtil.to(matrixStorage));
//		HomeFragment.SaveTask saveTask = new HomeFragment.SaveTask();


	}
}