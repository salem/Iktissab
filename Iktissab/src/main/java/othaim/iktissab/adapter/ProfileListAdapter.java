package othaim.iktissab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.List;


import othaim.iktissab.R;
import othaim.iktissab.adapter.model.LabelValue;

/**
 * Created by S.Aman on 7/15/13.
 */
public class ProfileListAdapter extends ArrayAdapter<LabelValue> {

    public ProfileListAdapter(Context context, int textViewResourceId, List<LabelValue> list){
        super(context, textViewResourceId, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.name_value_layout, null);
        }
        LabelValue map = getItem(position);
        TextView name = (TextView)view.findViewById(R.id.profile_name);
        name.setText(map.getLabel());

        TextView value = (TextView)view.findViewById(R.id.profile_value);
        value.setText(map.getValue());
        return view;
    }
}
