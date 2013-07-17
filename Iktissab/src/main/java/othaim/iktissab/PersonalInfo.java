package othaim.iktissab;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import othaim.iktissab.util.HttpJson;
import othaim.iktissab.util.IktissabConstant;
import othaim.iktissab.util.NotificationActivity;
import othaim.iktissab.util.Notifier;

public class PersonalInfo extends NotificationActivity {

	ListView mUserInfoList;
	View mProgressView; 
	LinearLayout mContentLayout;
	
	ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.personal_info_layout);
			mProgressView = findViewById(R.id.progressView);
			mContentLayout = (LinearLayout) findViewById(R.id.contentLayout);

			mUserInfoList = (ListView) findViewById(R.id.userInfoList);
			
			showProgress(true);

			JSONObject params = new JSONObject();
			params.put("Class", "Webservice");
			params.put("method", "get_cust_info");
			SharedPreferences prefrenc = getSharedPreferences(
					IktissabConstant.USER_INFO, 0);
			params.put("v_cid", prefrenc.getString("iktissab_card", ""));

			Notifier notifi = new Notifier(this, params.toString());

			new HttpJson().execute(notifi);

		} catch (JSONException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public void handleResult(String jsonstring) {
		showProgress(false);
		JSONObject userInfo;
		try {
			userInfo = new JSONObject(jsonstring).getJSONObject("cust_info");
			listItems.add("Customer Name: " + userInfo.getString("cname"));
			listItems.add("Area: " + userInfo.getString("area"));
			listItems.add("House#: " + userInfo.getString("houseno"));
			listItems.add("PO BOX: " + userInfo.getString("pobox"));
			listItems.add("Zip: " + userInfo.getString("zip"));
			listItems.add("Tel(HOME): " + userInfo.getString("tel_home"));
			listItems.add("Tel(Office): " + userInfo.getString("tel_office"));
			listItems.add("Mobile: " + userInfo.getString("mobile"));
			listItems.add("Email: " + userInfo.getString("email"));
			listItems.add("Marital Status: " + userInfo.getString("marital_status"));
			listItems.add("Birth Date: " + userInfo.getString("g_birthdate"));
			listItems.add("Balance: " + userInfo.getString("balance"));
			listItems.add("Language: " + userInfo.getString("lang"));
			listItems.add("Gender: " + userInfo.getString("gender"));
			
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
			mUserInfoList.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		super.handleResult(jsonstring);
	}
}
