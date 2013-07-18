package othaim.iktissab;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import othaim.iktissab.adapter.ProfileListAdapter;
import othaim.iktissab.adapter.model.LabelValue;
import othaim.iktissab.util.HttpJson;
import othaim.iktissab.util.IktissabConstant;
import othaim.iktissab.util.NotificationActivity;
import othaim.iktissab.util.Notifier;

public class ProfileActivity extends NotificationActivity {

    //View
	private View mProgressView;
	private ListView mIdList;
    private ListView mMeList;
    private ListView mHistoryList;
    private ToggleButton mIdButton;
    private ToggleButton mMeButton;
    private ToggleButton mHistoryButton;
    private FrameLayout mContentLayout;
    private Button mEditButton;
    private Button mLastUseButton;

    private String state = "id";
    //list adapter used
    ProfileListAdapter idProfileListAdapter;
    ProfileListAdapter meProfileListAdapter;
    ProfileListAdapter historyProfileListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.profile_layout);
            mContentLayout = (FrameLayout) findViewById(R.id.contentLayout);
			mProgressView = findViewById(R.id.progressView);
			mIdList = (ListView) findViewById(R.id.idList);
            mMeList = (ListView) findViewById(R.id.meList);
            mHistoryList = (ListView) findViewById(R.id.historyList);

            mIdButton = (ToggleButton) findViewById(R.id.idButton);
            mMeButton = (ToggleButton) findViewById(R.id.meButton);
            mHistoryButton = (ToggleButton) findViewById(R.id.historyButton);

            //add listener
            mIdButton.setOnClickListener(onToggle);
            mMeButton.setOnClickListener(onToggle);
            mHistoryButton.setOnClickListener(onToggle);

            mEditButton = (Button) findViewById(R.id.editButton);

			showProgress(true);

			JSONObject params = new JSONObject();
			params.put("Class", "Webservice");
			params.put("method", "get_cust_info");
			SharedPreferences prefrences = getSharedPreferences(
					IktissabConstant.USER_INFO, 0);
			params.put("v_cid", prefrences.getString("iktissab_card", ""));

			Notifier notify = new Notifier(this, params.toString());

			new HttpJson().execute(notify);

		}
        catch (JSONException e) {

			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

    private ToggleButton.OnClickListener onToggle = new ToggleButton.OnClickListener(){
        @Override
        public void onClick(View view){
            int id = view.getId();
            ToggleButton btn = (ToggleButton)view;
            hideUi();
            switch (id){
                case R.id.idButton:
                    mIdList.setVisibility(View.VISIBLE);
                    break;
                case R.id.meButton:
                    mMeList.setVisibility(View.VISIBLE);
                    break;
                case R.id.historyButton:
                    mHistoryList.setVisibility(View.VISIBLE);
                    break;
            }

        }
    };
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_profile, menu);
		return true;
	}

	@Override
	public void handleException(Exception e) {
		showProgress(false);
		super.handleException(e);
	}

	@Override
	public void handleResult(String jsonString) {
		showProgress(false);
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONObject userInfo = json.getJSONObject("cust_info");

            List<LabelValue> idValue = new ArrayList<LabelValue>();

            LabelValue c_id = new LabelValue("Iktissab Id:", userInfo.getString("c_id")) ;
            LabelValue cname = new LabelValue("Name:", userInfo.getString("cname")) ;
            LabelValue street = new LabelValue("Street", userInfo.getString("street")) ;
            LabelValue area = new LabelValue("Area:", userInfo.getString("area") ) ;
            LabelValue houseno = new LabelValue("House #:", userInfo.getString("houseno")) ;
            LabelValue pobox = new LabelValue("Post Box:", userInfo.getString("pobox")) ;
            LabelValue zip = new LabelValue("Zip:", userInfo.getString("zip") ) ;
            LabelValue city_no = new LabelValue("City #", userInfo.getString("city_no")) ;
            LabelValue tel_home = new LabelValue("Phone(Home):", userInfo.getString("tel_home")) ;
            LabelValue tel_office = new LabelValue("Phone(Office):", userInfo.getString("tel_office")) ;
            LabelValue mobile = new LabelValue("Mobile", userInfo.getString("mobile")) ;
            LabelValue nat_no = new LabelValue("Nation #", userInfo.getString("nat_no")) ;
            LabelValue marital_status = new LabelValue("Martial Status:", userInfo.getString("marital_status")) ;
            LabelValue id_no = new LabelValue("SSID/Iqama:", userInfo.getString("id_no")) ;
            LabelValue job_no = new LabelValue("Job #:", userInfo.getString("job_no")) ;
            LabelValue lang = new LabelValue("Language:", userInfo.getString("lang")) ;
            LabelValue gender = new LabelValue("Gender", userInfo.getString("gender")) ;
            //LabelValue pur_grp = new LabelValue("") ;
            LabelValue cust_status = new LabelValue("Customer Status:", userInfo.getString("cust_status")) ;
            LabelValue card_status = new LabelValue("Card Status:", userInfo.getString("card_status")) ;
            //LabelValue cust_typ = new LabelValue("") ;
            //LabelValue special_notes = new LabelValue() ;
            LabelValue balance = new LabelValue("Balance", userInfo.getString("balance")) ;
            //LabelValue old_card_id = new LabelValue() ;
            //LabelValue pass = new LabelValue() ;
            LabelValue email = new LabelValue("Email:", userInfo.getString("email")) ;
            LabelValue g_birthdate = new LabelValue("Birth Date:", userInfo.getString("g_birthdate")) ;

            idValue.add(c_id);
            idValue.add(balance);
            idValue.add(mobile);
            idValue.add(area);
            idValue.add(city_no);
            idProfileListAdapter = new ProfileListAdapter(this, 0, idValue);

            ListView idList = (ListView) findViewById(R.id.idList);
            idList.setAdapter(idProfileListAdapter);

            List<LabelValue> meValue = new ArrayList<LabelValue>();
            meValue.add(cname);
            meValue.add(area);
            meValue.add(city_no);
            meValue.add(mobile);
            meValue.add(email);
            meValue.add(nat_no);
            meValue.add(marital_status);
            meValue.add(g_birthdate);
            meValue.add(job_no);
            meValue.add(balance);
            meValue.add(lang);
            meValue.add(gender);
            meProfileListAdapter = new ProfileListAdapter(this, 0, meValue);
            mMeList.setAdapter(meProfileListAdapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		super.handleResult(jsonString);
	}

	public void profilePersonalButtonClicked(View v){
		Intent intent = new Intent(this, PersonalInfo.class);
		this.startActivity(intent);
	}

    private void hideUi()
    {
        mIdList.setVisibility(View.GONE);
        mMeList.setVisibility(View.GONE);
        mHistoryList.setVisibility(View.GONE);

    }
}
