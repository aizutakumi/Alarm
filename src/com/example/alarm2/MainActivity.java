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
	long[] pattern = {2000, 4000, 2000, 4000, 2000, 4000};	//OFF.ON.OFF.ON �c
	public static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//�I�u�W�F�N�g�擾
	    start = (Button)findViewById(R.id.button1);
	    stop = (Button)findViewById(R.id.button2);
	    reset = (Button)findViewById(R.id.button3);
	    
	    txt_timer = (TextView)findViewById(R.id.txt_timer);
	    final EditText timer_input = (EditText)findViewById(R.id.input);
	    
	    
	    //�o�C�u���[�V����
        vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        //�A���[����
        sound = MediaPlayer.create(this, R.raw.cycling);
        sound.setLooping(true);
        sound.seekTo(0);
        
        //�n���h���擾
        handler = new Handler();
        
        //�N���b�N���X�i�[�ݒ�
        start.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
			    //���l�̎擾
			    Editable getText = timer_input.getText();
			    timer_count = Integer.parseInt(getText.toString());
		        
		        //�J�E���g�_�E���̏����l
		        count = new CountDown(timer_count * 1000, 1000);
		        
				//�J�E���g�X�^�[�g
				count.start();
	        }
        });
        
        stop.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
				//�J�E���g�X�g�b�v
        		count.cancel();
        		//txt_timer.setText(String.valueOf(timer_count));
        	}
        });
        
        reset.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		//���X�g�b�v
	        	sound.stop();
	        	sound.release();
        		//�o�C�u�X�g�b�v
	        	vib.cancel();
	        	//�������Z�b�g
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
    		// �J�E���g�_�E���I����ɌĂ΂��
    		
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

			// �J�E���g���ɕ\��
    		txt_timer.setText(Long.toString(millisUntilFinished/1000));
		}
    }
}
