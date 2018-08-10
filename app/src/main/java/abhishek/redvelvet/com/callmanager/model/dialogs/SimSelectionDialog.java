package abhishek.redvelvet.com.callmanager.model.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import abhishek.redvelvet.com.callmanager.model.boradcastreceiver.SmsStatus;
import abhishek.redvelvet.com.callmanager.model.message.Message;

/**
 * Created by abhishek on 8/8/18.
 */

public class SimSelectionDialog extends DialogFragment {

    public static int op_seq = -1;
    Message msg = null;
    FragmentActivity activity;

    public SimSelectionDialog(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        activity = getActivity();

        Bundle bundle = getArguments();
        msg = new Message(bundle.getString("msg_body"),getActivity());
        msg.getSim();

        AlertDialog sim_selection = new AlertDialog.Builder(getActivity())
                .setSingleChoiceItems(Message.operators, -1, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SmsStatus(activity).registerSentReceiver();
//                        new SmsStatus(context).registerDeliveredReceiver();
                        msg.sendMsg(which);
                        dialog.dismiss();
                    }
                })
                .create();
        sim_selection.show();


        return sim_selection;
    }
}
