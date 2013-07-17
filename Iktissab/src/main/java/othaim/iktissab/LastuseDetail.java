package othaim.iktissab;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LastuseDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lastuse_detail_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lastuse_detail, menu);
		return true;
	}

}
