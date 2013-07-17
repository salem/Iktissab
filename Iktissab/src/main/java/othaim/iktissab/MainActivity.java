package othaim.iktissab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
public class MainActivity extends TabActivity {

    private TabHost mTabHost;
    public String OFFER_TAG = "OfferActivity";
    public String PROFILE_TAG = "ProfileActivity";
    public String BRANCH_TAG = "BranchActivity";
    public String CARD_TAG = "CardActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mTabHost = getTabHost();
        mTabHost.setup();

        TabSpec offerSpec = mTabHost.newTabSpec(OFFER_TAG);
        offerSpec.setIndicator(getString(R.string.offer_title));
        Intent offerIntent = new Intent(this, OfferActivity.class);
        offerSpec.setContent(offerIntent);
        mTabHost.addTab(offerSpec);

        TabSpec profileSpec = mTabHost.newTabSpec(PROFILE_TAG);
        profileSpec.setIndicator(getString(R.string.profile_title));
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        profileSpec.setContent(profileIntent);
        mTabHost.addTab(profileSpec);

        TabSpec branchSpec = mTabHost.newTabSpec(BRANCH_TAG);
        branchSpec.setIndicator(getString(R.string.branch_title));
        Intent branchIntent = new Intent(this, BranchActivity.class);
        branchSpec.setContent(branchIntent);
        mTabHost.addTab(branchSpec);

        TabSpec cardSpec = mTabHost.newTabSpec(CARD_TAG);
        cardSpec.setIndicator(getString(R.string.card_title));
        Intent cardIntent = new Intent(this, CardActivity.class);
        cardSpec.setContent(cardIntent);
        mTabHost.addTab(cardSpec);


    }

}
