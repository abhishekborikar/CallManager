package abhishek.redvelvet.com.callmanager.view_model.fragments.inner_fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;
import abhishek.redvelvet.com.callmanager.model.dialogs.AlertMessage;
import abhishek.redvelvet.com.callmanager.model.adapters.RecentContactListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {

    ListView r_contact_listview = null;
    Context context;
    View view;
    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recent, container, false);

        r_contact_listview = (ListView)view.findViewById(R.id.recent_contact_list);

        context = getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e("r contact list",r_contact_listview+"");
        try {

            if (r_contact_listview == null)
                r_contact_listview = (ListView)view.findViewById(R.id.recent_contact_list);
            RecentContactListAdapter adapter = new RecentContactListAdapter(context,R.layout.recent_contact_item_style);
            r_contact_listview.setAdapter(adapter);
        }
        catch (Exception e){
            Log.e("r cnt list",e+"");
        }



        r_contact_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String display_name = ((TextView)view.findViewById(R.id.recent_display_name)).getText().toString();
                String info = ((TextView)view.findViewById(R.id.recent_info)).getText().toString();

                SharedPreferences preferences = getActivity().getSharedPreferences(MessageConstant.DND_PREFRENCE,Context.MODE_PRIVATE);
                String status = null;
                String operator = null;
                if (preferences==null) {
                    status = preferences.getString(MessageConstant.KEY_PROCESSED, "NA");
                    operator = preferences.getString(MessageConstant.KEY_OPERATOR, "NA");
                }

                Bundle bundle = new Bundle();
                bundle.putString("status",status);
                bundle.putString("operator",operator);
                bundle.putString("display_name",display_name);
                bundle.putString("info",info);



                AlertMessage message = new AlertMessage();
//                message.onCreateDialog(bundle).show();
                message.setArguments(bundle);
                message.show(getFragmentManager(),"alert dialog");

//                TextView tv = (TextView)view.findViewById(R.id.recent_display_name);
//                Toast.makeText(context, ""+tv.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
