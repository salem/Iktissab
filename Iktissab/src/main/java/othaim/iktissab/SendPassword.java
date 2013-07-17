package othaim.iktissab;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import othaim.iktissab.util.HttpJson;
import othaim.iktissab.util.NotificationActivity;
import othaim.iktissab.util.Notifier;

public class SendPassword extends NotificationActivity {
	View mProgressView;
	LinearLayout mContentLayout;

	String mEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_password_layout);

		mProgressView = findViewById(R.id.progressView);
		mContentLayout = (LinearLayout) findViewById(R.id.contentLayout);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_send_password, menu);
		return true;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mProgressView.setVisibility(View.VISIBLE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mContentLayout.setVisibility(View.VISIBLE);
			mContentLayout.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mContentLayout.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public void handleException(Exception e) {
		// TODO Auto-generated method stub
		super.handleException(e);
	}

	public void sendPasswordButtonClicked(View v){
		try{
			showProgress(true);

			JSONObject params = new JSONObject();
			params.put("Class", "Webservice");
			params.put("method", "forgot_password");
			
			EditText email = (EditText) findViewById(R.id.emailText);
			mEmail = email.getText().toString();
			if(TextUtils.isEmpty(mEmail) || !mEmail.contains("@")){
				email.setError(getString(R.string.errorInvalidEmail));
				email.requestFocus();
				return;
			}
			params.put("email", mEmail);

			Notifier notifi = new Notifier(this, params.toString());

			new HttpJson().execute(notifi);

		} catch (JSONException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void handleResult(String jsonstring) {
		showProgress(false);
		
		
		try {
			JSONObject json = new JSONObject(jsonstring);
			boolean success = json.getBoolean("success");
			if(success){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("A passord is send to the email addreess");
				builder.show();
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Invalid email address");
				builder.show();
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		super.handleResult(jsonstring);
	}
}
