package abhishek.redvelvet.com.callmanager.model.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.Arrays;

import abhishek.redvelvet.com.callmanager.model.boradcastreceiver.SmsStatus;
import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;
import abhishek.redvelvet.com.callmanager.model.message.Message;

/**
 * Created by abhishek on 7/8/18.
 */

public class DndAlertMessage extends DialogFragment {

    CharSequence[] items;
    final CharSequence[] choices = new CharSequence[]{
            "Finance/Banking",
            "Real Estate",
            "Education",
            "Health",
            "Consumer goods and automobile",
            "Entertainment",
            "Tourism"
    };



    final boolean[] choices_boolean = new boolean[7];

    StringBuilder msg_body = new StringBuilder("START ");
    FragmentActivity activity;

    public DndAlertMessage(){
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Arrays.fill(choices_boolean,Boolean.FALSE);
        items = new CharSequence[]{"Fully","Partially"};

        activity = getActivity();

        SharedPreferences preferences = getActivity().getSharedPreferences(MessageConstant.DND_PREFRENCE,Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        final FragmentActivity activity = getActivity();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case 0:{
                                //-------------fully block-------------
                                editor.putString(MessageConstant.KEY_FULL_PAR,"Fully Block");
                                editor.commit();
//                                new StatusDialog(activity).updateUI();
                                msg_body.append("0");
                                dialog.dismiss();
                                showAlert(msg_body.toString());
                                break;
                            }
                            case 1:{
                                //-------------partially---------------
                                editor.putString(MessageConstant.KEY_FULL_PAR,"Partially Block");
                                editor.commit();
//                                new StatusDialog(activity).updateUI();
                                AlertDialog choice = new AlertDialog.Builder(activity)
                                        .setMultiChoiceItems(choices, choices_boolean, new DialogInterface.OnMultiChoiceClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                choices_boolean[which] = isChecked;
                                            }
                                        })
                                        .setPositiveButton("Activate", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                //-------------check items-----------------

                                                int count = 1;
                                                for (boolean item:
                                                     choices_boolean) {
                                                    count++;

                                                    if (item)
                                                        msg_body.append(count+",");
                                                }

                                                msg_body.deleteCharAt(msg_body.length()-1);
                                                //-----------------------------------------

                                                showAlert(msg_body.toString());

                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .create();
                                choice.show();

                                break;
                            }
                        }
                    }
                })
                .create();
        dialog.show();

        return dialog;
    }

    private void showAlert(final String msg_body){
        AlertDialog final_dialog = new AlertDialog.Builder(activity)
                .setTitle("Confirmation")
                .setMessage("Do you want to activate dnd ?")
                .setPositiveButton("Activate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle bundle = new Bundle();
                        bundle.putString("msg_body",msg_body);


                        SimSelectionDialog select_sim = new SimSelectionDialog();
                        select_sim.setArguments(bundle);
                        select_sim.show(activity.getSupportFragmentManager(),"sim selection");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        final_dialog.show();
    }
}
