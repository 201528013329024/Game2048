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

public class GameView extends GridLayout { // 游戏界面类

	/** 为防止程序出错，GameView的所有构造方法都要重写 **/
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

	/** 初始化游戏界面 **/
	public void initGameView() {
		setColumnCount(4); // 设置GameView为四列
		setBackgroundColor(0xffffff); // 设置GameView背景色

		// 判断用户的手势动作的方向：上、下、左、右
		setOnTouchListener(new View.OnTouchListener() {
			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: // 用户开始触摸屏幕
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP: // 用户离开屏幕
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;

					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) { // 解读手势为向左滑动，为增强用户体验，用5作为阈值
							swipeLeft(); // 调用向左滑动所触发的方法
						} else if (offsetX > 5) { // 解读手势为向右滑动
							swipeRight(); // 调用向右滑动所触发的方法
						}
					} else {
						if (offsetY < -5) { // 解读手势为向上滑动，为增强用户体验，用5作为阈值
							swipeUp(); // 调用向上滑动所触发的方法
						} else if (offsetY > 5) { // 解读手势为下左滑动，为增强用户体验，用5作为阈值
							swipeDown(); // 调用向下滑动所触发的方法
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

	/** 当用户向左滑动时，需要完成的相应 **/
	private void swipeLeft() {
		boolean merge = false; // 标记是否合并

		// 逐行合并卡片，或者移动卡片
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {

				for (int x1 = x + 1; x1 < 4; x1++) { // 从左往右扫描
					if (cardsMap[x1][y].getNum() > 0) { // 若右边为空白卡片则跳过，继续处理右边一张卡片
						if (cardsMap[x][y].getNum() <= 0) { // 左边为空卡片，右边为数字卡片
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum()); // 将右边卡片的数字赋给左边卡片
							cardsMap[x1][y].setNum(0); // 重置右边卡片数字为0

							x--;
							merge = true; // 已合并
						} else if (cardsMap[x][y].equals(cardsMap[x1][y])) { // 若左右两个方块的数字相同
							cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2); // 将左边方块的数字乘以2
							cardsMap[x1][y].setNum(0); // 重置右边卡片数字为0

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum()); // 游戏加分
							merge = true; // 已合并
						}

						break;
					}
				}
			}
		}

		if (merge) {
			addRandonNum(); // 有方块合并则需随机添加一个方块
			checkComplete(); // 检查游戏是否结束
		}
	}

	/** 当用户向右滑动时，需要完成的相应 **/
	private void swipeRight() {
		boolean merge = false;

		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {

				for (int x1 = x - 1; x1 >= 0; x1--) { // 从右往左扫描
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

	/** 当用户向上滑动时，需要完成的相应 **/
	private void swipeUp() {
		boolean merge = false;

		// 逐列合并卡片，或者移动卡片
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {

				for (int y1 = y + 1; y1 < 4; y1++) { // 从上往下扫描
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

	/** 当用户向下滑动时，需要完成的相应 **/
	private void swipeDown() {
		boolean merge = false;

		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {

				for (int y1 = y - 1; y1 >= 0; y1--) { // 从下往上扫描
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

		int cardWidth = (Math.min(w, h) - 10) / 4; // 设置卡片边长

		if (canChangeSize == true) {
			addCards(cardWidth, cardWidth);
			startGame();
		}

		canChangeSize = false;
	}

	private Card[][] cardsMap = new Card[4][4];

	private void addCards(int cardWidth, int cardHeight) {
		Card card;

		// 逐行逐列添加卡片
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
					emptyPoints.add(new Point(x, y)); // 统计游戏中那些卡片是空白的，将从这些空白卡片中随机抽取一个添加数字
				}
			}
		}

		Point point = emptyPoints.remove((int) (Math.random() * emptyPoints.size())); // 随机添加卡片
		cardsMap[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4); // 卡片数字为4的概率为0.1，卡片数字为1的概率为0.9
	}

	public void startGame() {
		MainActivity.getMainActivity().clearScore(); // 游戏得分清0

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsMap[x][y].setNum(0);
			}
		}

		// 游戏开始时随机添加两个卡片
		addRandonNum();
		addRandonNum();
	}

	/** 检查游戏是否失败 **/
	public void checkComplete() {
		boolean complete = true;
		ALL: for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				//符合以下条件则表示游戏界面可以继续滑动
				if (cardsMap[x][y].getNum() == 0 || (x > 1 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
						|| (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
						|| (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
						|| (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {
					complete = false;
					break ALL;
				}
			}
		}

		//游戏失败时，弹出对话框
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
