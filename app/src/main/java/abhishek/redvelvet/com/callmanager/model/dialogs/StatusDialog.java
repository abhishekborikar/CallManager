package abhishek.redvelvet.com.callmanager.model.dialogs;

import android.annotation.SuppressLint;
//import android.app.DialogFragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TextView;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;

/**
 * Created by abhishek on 8/8/18.
 */

public class StatusDialog extends DialogFragment {

    TextView request,register,processed,type;

    boolean status_request;
    String status_register;
    String status_processed;
    String delivery_date;
    String status_type;
    SharedPreferences preferences;


    public StatusDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        FragmentActivity activity = getActivity();

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_status,null);

        //----------------get widgets-----------
        request   = (TextView)view.findViewById(R.id.request);
        register  = (TextView)view.findViewById(R.id.register);
        processed = (TextView)view.findViewById(R.id.processed);
        type      = (TextView)view.findViewById(R.id.type);
        //--------------------------------------

        preferences = activity.getSharedPreferences(MessageConstant.DND_PREFRENCE,Context.MODE_PRIVATE);
        Log.e("status dialog",preferences+"");
        updateUI();

        request.setText(status_request ? "Sucessful \n "+delivery_date : "NA");
        register.setText(status_register);
        processed.setText(status_processed);
        type.setText(status_type);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    public void updateUI(){
        status_request   = preferences.getBoolean(MessageConstant.KEY_REQUEST,false);
        status_register  = preferences.getString(MessageConstant.KEY_REGISTER,"NA");
        status_processed = preferences.getString(MessageConstant.KEY_PROCESSED,"NA");
        delivery_date    = preferences.getString(MessageConstant.KEY_DELIVERY_DATE,"NA");
        status_type      = preferences.getString(MessageConstant.KEY_FULL_PAR,"NA");

        Log.e("UpdateUI",status_request+" "+status_register+" "+status_processed+" "+delivery_date);
    }

}
