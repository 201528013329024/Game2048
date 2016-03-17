/**
 * @author Haoguang Xu
 * Copyright (c) 2016, UCAS
 * All rights reserved. 
 * 
 * GameView Class {@link GameView}  
 */

package com.example.game2048;

import java.util.ArrayList;
import java.util.List;

import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout { // ��Ϸ������

	/** Ϊ��ֹ�������GameView�����й��췽����Ҫ��д **/
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	/** ��ʼ����Ϸ���� **/
	public void initGameView() {
		setColumnCount(4); // ����GameViewΪ����
		setBackgroundColor(0xffffff); // ����GameView����ɫ

		// �ж��û������ƶ����ķ����ϡ��¡�����
		setOnTouchListener(new View.OnTouchListener() {
			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: // �û���ʼ������Ļ
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP: // �û��뿪��Ļ
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;

					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) { // �������Ϊ���󻬶���Ϊ��ǿ�û����飬��5��Ϊ��ֵ
							swipeLeft(); // �������󻬶��������ķ���
						} else if (offsetX > 5) { // �������Ϊ���һ���
							swipeRight(); // �������һ����������ķ���
						}
					} else {
						if (offsetY < -5) { // �������Ϊ���ϻ�����Ϊ��ǿ�û����飬��5��Ϊ��ֵ
							swipeUp(); // �������ϻ����������ķ���
						} else if (offsetY > 5) { // �������Ϊ���󻬶���Ϊ��ǿ�û����飬��5��Ϊ��ֵ
							swipeDown(); // �������»����������ķ���
						}
					}

					break;
				default:
					break;
				}
				return true;
			}
		});
	}

	/** ���û����󻬶�ʱ����Ҫ��ɵ���Ӧ **/
	private void swipeLeft() {
		boolean merge = false; // ����Ƿ�ϲ�

		// ���кϲ���Ƭ�������ƶ���Ƭ
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {

				for (int x1 = x + 1; x1 < 4; x1++) { // ��������ɨ��
					if (cardsMap[x1][y].getNum() > 0) { // ���ұ�Ϊ�հ׿�Ƭ�����������������ұ�һ�ſ�Ƭ
						if (cardsMap[x][y].getNum() <= 0) { // ���Ϊ�տ�Ƭ���ұ�Ϊ���ֿ�Ƭ
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum()); // ���ұ߿�Ƭ�����ָ�����߿�Ƭ
							cardsMap[x1][y].setNum(0); // �����ұ߿�Ƭ����Ϊ0

							x--;
							merge = true; // �Ѻϲ�
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) { // ���������������������ͬ
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); // ����߷�������ֳ���2
							cardsMap[x1][y].setNum(0); // �����ұ߿�Ƭ����Ϊ0

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum()); // ��Ϸ�ӷ�
							merge = true; // �Ѻϲ�
						}

						break;
					}
				}
			}
		}

		if (merge) {
			addRandonNum(); // �з���ϲ�����������һ������
			checkComplete(); // �����Ϸ�Ƿ����
		}
	}

	/** ���û����һ���ʱ����Ҫ��ɵ���Ӧ **/
	private void swipeRight() {
		boolean merge = false;

		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {

				for (int x1 = x - 1; x1 >= 0; x1--) { // ��������ɨ��
					if (cardsMap[x1][y].getNum() > 0) {
						if (cardsMap[x][y].getNum() <= 0) {
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);

							x++;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							cardsMap[x1][y].setNum(0);

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}

						break;
					}
				}
			}
		}

		if (merge) {
			addRandonNum();
			checkComplete();
		}
	}

	/** ���û����ϻ���ʱ����Ҫ��ɵ���Ӧ **/
	private void swipeUp() {
		boolean merge = false;

		// ���кϲ���Ƭ�������ƶ���Ƭ
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {

				for (int y1 = y + 1; y1 < 4; y1++) { // ��������ɨ��
					if (cardsMap[x][y1].getNum() > 0) {

						if (cardsMap[x][y].getNum() <= 0) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y--;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							cardsMap[x][y1].setNum(0);

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}

						break;
					}
				}
			}
		}

		if (merge) {
			addRandonNum();
			checkComplete();
		}
	}

	/** ���û����»���ʱ����Ҫ��ɵ���Ӧ **/
	private void swipeDown() {
		boolean merge = false;

		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {

				for (int y1 = y - 1; y1 >= 0; y1--) { // ��������ɨ��
					if (cardsMap[x][y1].getNum() > 0) {

						if (cardsMap[x][y].getNum() <= 0) {
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y++;
							merge = true;
						} else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
							cardsMap[x][y1].setNum(0);

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}

						break;
					}
				}
			}
		}

		if (merge) {
			addRandonNum();
			checkComplete();
		}
	}

	private boolean canChangeSize = true;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int cardWidth = (Math.min(w, h) - 10) / 4; // ���ÿ�Ƭ�߳�

		if (canChangeSize == true) {
			addCards(cardWidth, cardWidth);
			startGame();
		}

		canChangeSize = false;
	}

	private Card[][] cardsMap = new Card[4][4];

	private void addCards(int cardWidth, int cardHeight) {
		Card card;

		// ����������ӿ�Ƭ
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				card = new Card(getContext());
				card.setNum(0);
				addView(card, cardWidth, cardHeight);
				cardsMap[x][y] = card;
			}
		}
	}

	private List<Point> emptyPoints = new ArrayList<Point>();

	private void addRandonNum() {
		emptyPoints.clear();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardsMap[x][y].getNum() <= 0) {
					emptyPoints.add(new Point(x, y)); // ͳ����Ϸ����Щ��Ƭ�ǿհ׵ģ�������Щ�հ׿�Ƭ�������ȡһ���������
				}
			}
		}

		Point point = emptyPoints.remove((int) (Math.random() * emptyPoints.size())); // �����ӿ�Ƭ
		cardsMap[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4); // ��Ƭ����Ϊ4�ĸ���Ϊ0.1����Ƭ����Ϊ1�ĸ���Ϊ0.9
	}

	public void startGame() {
		MainActivity.getMainActivity().clearScore(); // ��Ϸ�÷���0

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsMap[x][y].setNum(0);
			}
		}

		// ��Ϸ��ʼʱ������������Ƭ
		addRandonNum();
		addRandonNum();
	}

	/** �����Ϸ�Ƿ�ʧ�� **/
	public void checkComplete() {
		boolean complete = true;
		ALL: for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				//���������������ʾ��Ϸ������Լ�������
				if (cardsMap[x][y].getNum() == 0 || (x > 1 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
						|| (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
						|| (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
						|| (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {
					complete = false;
					break ALL;
				}
			}
		}

		//��Ϸʧ��ʱ�������Ի���
		if (complete) {
			new AlertDialog.Builder(getContext()).setTitle("Notify").setMessage("Game Over")
					.setPositiveButton("New Game", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							startGame();
						}
					}).show();
		}
	}
}
