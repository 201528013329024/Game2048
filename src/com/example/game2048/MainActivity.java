/**
 * @author Haoguang Xu
 * Copyright (c) 2016, UCAS
 * All rights reserved. 
 * 
 * MainActivity Class {@link MainActivity}  
 */

package com.example.game2048;

import com.example.game2048.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity { // ��������

	private TextView tvScore; // ������ʾ��Ϸ���÷�����TextView
	private int score = 0; // ��Ϸ�÷�
	private static MainActivity mainActivity = null;
	private Button btnNewGame; // ���ڿ�ʼ����Ϸ�İ�ť
	private GameView gameView;

	public MainActivity() {
		mainActivity = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvScore = (TextView) findViewById(R.id.tvScore);
		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gameView = (GameView) findViewById(R.id.GameView);
				gameView.startGame(); // ��ʼһ������Ϸ
			}
		});
	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	public void clearScore() {
		score = 0;
		showScore();
	}

	public void showScore() {
		tvScore.setText(score + "");

	}

	public void addScore(int s) {
		score += s;
		showScore();
	}

	/** ʵ�����������κ��˼��˳���Ϸ���� **/
	private int clickCount = 0;
	private long lastClickTime = 0;

	@Override
	public void onBackPressed() {

		if (lastClickTime <= 0) {
			Toast.makeText(this, "�ٰ�һ�κ��˼��˳���Ϸ��", Toast.LENGTH_SHORT).show();
			;
			lastClickTime = System.currentTimeMillis();
		} else {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - lastClickTime) < 1000) {
				finish();
			} else {
				lastClickTime = currentTime;
			}
		}
	}
}
