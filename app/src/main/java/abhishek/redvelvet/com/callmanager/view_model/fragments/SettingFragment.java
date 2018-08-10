package abhishek.redvelvet.com.callmanager.view_model.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;
import abhishek.redvelvet.com.callmanager.model.dialogs.DndAlertMessage;
import abhishek.redvelvet.com.callmanager.model.dialogs.StatusDialog;
import abhishek.redvelvet.com.callmanager.model.dialogs.UsageDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {


    View view;
    Button dnd_activation;
    Button dnd_status, test;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //---------------------------get widgets-------------------------------------
        view = inflater.inflate(R.layout.fragment_setting,container,false);
        dnd_activation = (Button)view.findViewById(R.id.activate_dnd);
        dnd_status     = (Button)view.findViewById(R.id.status);
        //----------------------------------------------------------------------------

        dnd_activation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DndAlertMessage dnd_msg = new DndAlertMessage();
                dnd_msg.show(getFragmentManager(),"activate dnd");
            }
        });

        dnd_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences preferences = getActivity().getSharedPreferences(MessageConstant.DND_PREFRENCE, Context.MODE_PRIVATE);
                String processed_status = preferences.getString(MessageConstant.KEY_PROCESSED,"NA");

                if (processed_status.contains("Contact Customer Care"))
                    Toast.makeText(getActivity(), "Contact Customer Care", Toast.LENGTH_SHORT).show();

                StatusDialog statusDialog = new StatusDialog();
                statusDialog.show(getFragmentManager(),"status ");


            }
        });

        //-----------------test---------------------------
        test = (Button)view.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsageDialog dialog = new UsageDialog();
                dialog.show(getFragmentManager(),"usage");

            }
        });
        //------------------------------------------------



        // Inflate the layout for this fragment
        return view;
    }

}
