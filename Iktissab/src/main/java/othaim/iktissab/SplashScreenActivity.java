package othaim.iktissab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import othaim.iktissab.util.SystemUiHider;


public class SplashScreenActivity extends Activity {

	/**
	 * If {@link #AUTO_HIDE_DELAY_MILLIS} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 1000;
    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash_screen_layout);
        final View contentView = findViewById(R.id.fullscreen_content);
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.hide();
        delayedHide(AUTO_HIDE_DELAY_MILLIS);
	}
	
	protected void onRestart() {
		super.onRestart();
		this.finish();
	}
	
	Handler handler = new Handler();
	Runnable runnable = new Runnable(){
		@Override
		public void run(){
			gotoNextActivity();
			
		}
	};

	protected void gotoNextActivity() {

		Intent intent = new Intent(this, LoginActivity.class);
		this.startActivity(intent);
		overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

	}
	private void delayedHide(int delayMillis) {
		handler.removeCallbacks(runnable);
		handler.postDelayed(runnable, delayMillis);
	}
}
