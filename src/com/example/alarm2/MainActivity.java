package com.example.alarm2;

import com.example.alarm2.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.app.Activity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	static int timer_count;
	TextView txt_timer;
	DigitalClock clock;
	CountDownTimer count;
    Button start,stop,reset;
	MediaPlayer sound;
	Vibrator vib;
	long[] pattern = {2000, 4000, 2000, 4000, 2000, 4000};	//OFF.ON.OFF.ON …
	public static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//オブジェクト取得
	    start = (Button)findViewById(R.id.button1);
	    stop = (Button)findViewById(R.id.button2);
	    reset = (Button)findViewById(R.id.button3);
	    
	    txt_timer = (TextView)findViewById(R.id.txt_timer);
	    final EditText timer_input = (EditText)findViewById(R.id.input);
	    
	    
	    //バイブレーション
        vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        //アラーム音
        sound = MediaPlayer.create(this, R.raw.cycling);
        sound.setLooping(true);
        sound.seekTo(0);
        
        //ハンドラ取得
        handler = new Handler();
        
        //クリックリスナー設定
        start.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
			    //数値の取得
			    Editable getText = timer_input.getText();
			    timer_count = Integer.parseInt(getText.toString());
		        
		        //カウントダウンの初期値
		        count = new CountDown(timer_count * 1000, 1000);
		        
				//カウントスタート
				count.start();
	        }
        });
        
        stop.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
				//カウントストップ
        		count.cancel();
        		//txt_timer.setText(String.valueOf(timer_count));
        	}
        });
        
        reset.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		//音ストップ
	        	sound.stop();
	        	sound.release();
        		//バイブストップ
	        	vib.cancel();
	        	//数字リセット
        		count.cancel();
				txt_timer.setText(String.valueOf(timer_count));
        	}
        });
	}

	public class CountDown extends CountDownTimer{
    	public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
         }
    	
    	@Override
        public void onFinish() {
    		// カウントダウン終了後に呼ばれる
    		
    		handler.post(new Runnable() {
    			public void run() {
	    			sound.start();
    			}
    		});
    		
    		handler.post(new Runnable() {
    			public void run() {
	    			vib.vibrate(pattern, 0);
    			}
    		});
    	}

		@Override
		public void onTick(final long millisUntilFinished) {

			// カウント中に表示
    		txt_timer.setText(Long.toString(millisUntilFinished/1000));
		}
    }
}
