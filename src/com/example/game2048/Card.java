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

public class Card extends FrameLayout { // ��Ϸ��Ƭ��

	private int num = 0; // ��Ƭ�ϵ�����
	private TextView lable; // ��Ƭ��ʾ����

	public Card(Context context) {
		super(context);
		lable = new TextView(getContext());
		lable.setTextSize(35); // ���������С
		lable.setGravity(Gravity.CENTER); // ���ÿ�Ƭ���뷽ʽ
		lable.setBackgroundColor(0xff33aaa0); // ���ÿ�Ƭ��ʼ������ɫ
		LayoutParams lParams = new LayoutParams(-1, -1);// -1��-1��ʾ������������
		lParams.setMargins(10, 10, 0, 0); // ���ÿ�Ƭ�����ܵı߾�
		addView(lable, lParams); // ��ʾ��Ƭ

		setNum(0); // ��ʼʱ���ÿ�Ƭ����Ϊ0
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;

		// ֻ�е���Ƭ���ִ���0ʱ�����ڿ�Ƭ����ʾ��Ƭ����
		if (num <= 0) {
			lable.setText("");
		} else {
			lable.setText(num + "");
		}

		// ���ݿ�Ƭ�����ִ�С�����ÿ�Ƭ�ı�����ɫ������Խ����ɫԽ��
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
