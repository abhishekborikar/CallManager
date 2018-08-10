package abhishek.redvelvet.com.callmanager.model.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;
import abhishek.redvelvet.com.callmanager.model.message.Message;
import abhishek.redvelvet.com.contactlib.ContactOperation;
import abhishek.redvelvet.com.contactlib.LoadContacts;

/**
 * Created by abhishek on 7/8/18.
 */

public class AlertMessage extends DialogFragment {

    Context context;
    String display_name;
    String phone_number;
    String date;
    String dialog_body;
    String msg_body;

    @SuppressLint("ValidFragment")
    public AlertMessage( ) {



    }


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {


        final CharSequence[] items = new CharSequence[]{"Report Spam"};
        Log.e("alertmsg",getActivity()+"   ");
        final FragmentActivity activity = getActivity();

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog final_dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("Confirmation")
                                .setMessage("Do you want to report this as spam number")
                                .setPositiveButton("Report", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Bundle bundle = getArguments();
                                        Log.e("bundle",bundle+"");
                                        String status       = bundle.getString("status");
                                        String operator     = bundle.getString("operator");
                                        String phone_number = getNumber(bundle);
                                        String info         = getInfo(bundle);
                                        String msg_body     = "the unsolicited commercial communication, "+phone_number+", "+info;

                                        if (status!= null && operator!=null) {
                                            if (status.matches("(.*)Sucessful(.*)")) {
                                                new Message(msg_body, getActivity().getApplicationContext())
                                                        .sendMsg(Integer.parseInt(operator.split("\\|")[1]));
                                            } else {
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), "Request is not processed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(activity, "Register Complaint first", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setCancelable(false)
                                .create();

                        final_dialog.show();
                        dialog.dismiss();
                    }
                })
                .create();

        return alertDialog;
    }


    public String getNumber(Bundle savedInstance){
        String display_name = savedInstance.get("display_name").toString();

        if (display_name.matches("[0-9+]+")){
            return display_name;
        }
        else{
            ContactOperation operation  = new ContactOperation(getActivity().getContentResolver(),getActivity().getApplicationContext());
            ArrayList<HashMap> numbers = operation.getNumber(display_name);
            if (numbers == null)
                return display_name;
            for (HashMap hm:
                 numbers) {
                return (String)hm.get("number");
            }
        }
        return null;
    }

    public String getInfo(Bundle savedInstance){

        String info = savedInstance.get("info").toString();

        return info.split("\\|")[0].split("\\s+")[0];
    }
}
