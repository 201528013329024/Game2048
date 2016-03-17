/**
 * @author Haoguang Xu
 * Copyright (c) 2016, UCAS
 * All rights reserved. 
 * 
 * Card Class {@link Card}  
 */

package com.example.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout { // 游戏卡片类

	private int num = 0; // 卡片上的数字
	private TextView lable; // 卡片显示容器

	public Card(Context context) {
		super(context);
		lable = new TextView(getContext());
		lable.setTextSize(35); // 设置字体大小
		lable.setGravity(Gravity.CENTER); // 设置卡片对齐方式
		lable.setBackgroundColor(0xff33aaa0); // 设置卡片初始背景颜色
		LayoutParams lParams = new LayoutParams(-1, -1);// -1，-1表示铺满整个窗口
		lParams.setMargins(10, 10, 0, 0); // 设置卡片与四周的边距
		addView(lable, lParams); // 显示卡片

		setNum(0); // 初始时设置卡片数字为0
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;

		// 只有当卡片数字大于0时，才在卡片上显示卡片数字
		if (num <= 0) {
			lable.setText("");
		} else {
			lable.setText(num + "");
		}

		// 根据卡片的数字大小来设置卡片的背景颜色，数字越大颜色越深
		switch (num) {
		case 0:
			lable.setBackgroundColor(0xffbbada0);
			break;
		case 2:
			lable.setBackgroundColor(0xffeee4da);
			break;
		case 4:
			lable.setBackgroundColor(0xffede0c8);
			break;
		case 8:
			lable.setBackgroundColor(0xfff2b179);
			break;
		case 16:
			lable.setBackgroundColor(0xfff59563);
			break;
		case 32:
			lable.setBackgroundColor(0xfff67c5f);
			break;
		case 64:
			lable.setBackgroundColor(0xfff65e3b);
			break;
		case 128:
			lable.setBackgroundColor(0xffedcf72);
			break;
		case 256:
			lable.setBackgroundColor(0xffedcc61);
			break;
		case 512:
			lable.setBackgroundColor(0xffedc850);
			break;
		case 1024:
			lable.setBackgroundColor(0xffedc53f);
			break;
		case 2048:
			lable.setBackgroundColor(0xffedc22e);
			break;
		case 4096:
			lable.setBackgroundColor(0xffedc66e);
			break;
		default:
			lable.setBackgroundColor(0xff3c3a32);
			break;
		}
	}

	public boolean equals(Card card) {
		return getNum() == card.getNum();
	}

}
