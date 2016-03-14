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

public class MainActivity extends Activity {
	private TextView tvScore;
	private int score = 0;
	private static MainActivity mainActivity = null;
	private Button btnNewGame;
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
				gameView.startGame();
			}
		});
	}
	
	public static MainActivity getMainActivity() {
		return mainActivity;
	}
	
	public void clearScore() {
		score  = 0;
		showScore();
	}
	
	public void showScore() {
		tvScore.setText(score+"");
		
	}
	
	public void  addScore( int s) {
		score += s;
		showScore();
	}
	
	private int clickCount = 0;
	private long lastClickTime = 0;
	@Override
	public void onBackPressed() {
		
		if(lastClickTime<=0){
			Toast.makeText(this,"再按一次后退键退出游戏！", Toast.LENGTH_SHORT).show();;
			lastClickTime = System.currentTimeMillis();
		}else {
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastClickTime) <1000){
				finish();
			}else {
				lastClickTime = currentTime;
			}
		}
	}
}
