package othaim.iktissab;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import othaim.iktissab.util.IktissabConstant;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	
	
	//public static String SERVICE_URL = "http://10.0.2.2/crons/index.php/service";
	boolean isLogin = false;
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";


	private Animation aFlipIn,aFlipOut;
	// Values for email and password at the time of the login attempt.	
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	//Dialogs
	private AlertDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		aFlipIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		aFlipOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		
		setContentView(R.layout.login_layout);
		setupActionBar();

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.loginEmail);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.loginPassword);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		//TODO: remove code for production
		mEmailView.setText("sanoobvp@gmail.com");
		mPasswordView.setText("123sanoob123");
		//----------
		mLoginFormView = findViewById(R.id.loginForm);
		mLoginStatusView = findViewById(R.id.loginStatusLayout);
		mLoginStatusMessageView = (TextView) findViewById(R.id.loginStatusMessage);

		findViewById(R.id.signInButton).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public void arabicButtonClick(View view){
		//set language setting and go to sign up view
		ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		//LinearLayout login = (LinearLayout)findViewById(R.id.loginLayout);
		viewFlipper.setAnimation(aFlipIn);
		viewFlipper.setDisplayedChild(2);
		
	}
	public void englishButtonClick(View view){
		//set language setting and go to sign up view
		ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		viewFlipper.setAnimation(aFlipOut);
		viewFlipper.setDisplayedChild(2);
		
	}
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if(isLogin){
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.errorFieldRequired));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.errorInvalidPassword));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.errorFieldRequired));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.errorInvalidEmail));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.loginProgressSignIn);
			showProgress(true);			
			
			try {
				JSONObject params = new JSONObject();
				params.put(new String("Class"), "Webservice");
				params.put(new String("method"), "login");
				params.put("user", mEmail);
				params.put("pass", mPassword);
				
				new Http().execute(params.toString());
			
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			
		    

		}
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

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	private void dismissDialog() {
		if(dialog != null)
			if(dialog.isShowing())
				dialog.dismiss();
		if(this.isLogin){
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			
			overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
			
		}
	}
	
	private void showLoginStatus(String result){
		showProgress(false);
		JSONObject jo;
		try {
			dialog = new AlertDialog.Builder(this).create();
						
			jo = new JSONObject(result);
			if(jo.has("success") && jo.getBoolean("success") == true){
				dialog.setMessage("Welcome You are signed in\n with "+mEmail);
				this.isLogin = true;
				
				SharedPreferences prefrence = getSharedPreferences(IktissabConstant.USER_INFO, 0);
				SharedPreferences.Editor editor = prefrence.edit();
				JSONObject userInfo = jo.getJSONObject("userInfo");
				
				editor.putString("iktissab_card", userInfo.getString("iktissab_card"));
				editor.putString("user_name", userInfo.getString("name"));
				editor.commit();
				//go to the next activity from here
			}
			else{
				dialog.setMessage("Invalid user information");
				this.isLogin = false;
			}
			dialog.show();
			new Handler().postDelayed(new Runnable() {				
				@Override
				public void run() {
					dismissDialog();
					
				}
			}, 1000);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class Http  extends AsyncTask<String, Void, String>{
		private Exception exception;
		@Override
		protected String doInBackground(String... params) {
			
			try{
				
				DefaultHttpClient client = new DefaultHttpClient();
				
				HttpPost post = new HttpPost(IktissabConstant.SERVICE_URL);
				post.setHeader("Accept","*/*");
				post.setHeader("Accept-Encoding", "gzip, deflate");
				post.setHeader("Accept-Language", "en-US,en;q=0.5");
				post.setHeader("Cache-Control",	"no-cache");
				post.setHeader("Connection", "keep-alive");
				//post.setHeader("Content-Length","67");
				post.setHeader("Content-Type","text/json; charset=UTF-8");
				post.setHeader("input-content-type", "text/json");
				post.setHeader("output-content-type", "text/json");
				post.setHeader("language", "en");
				
				StringEntity str = new StringEntity(params[0]);
				
				post.setEntity(str);
				HttpResponse response = client.execute(post);
				
				InputStream is = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"), 8);
				
				StringBuilder sb = new StringBuilder();
				
				String line;
				while((line = reader.readLine()) != null){
					sb.append(line+"\n");
				}
				
				
				Log.i("HtppResponse", sb.toString());
				Log.i("HtppResponse", response.toString());
				Log.i("HtppResponse", "Status = " +response.getStatusLine().getStatusCode()+ " Description: "+response.getStatusLine().getReasonPhrase());
				return sb.toString();
				
			}			
			catch(Exception e){
				this.exception = e;
				return null;
			}			
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(this.exception !=null){
				showProgress(false);
				exception.printStackTrace();
				return;
			}
			
			showLoginStatus(result);
			
		}
		
		
	}

}
