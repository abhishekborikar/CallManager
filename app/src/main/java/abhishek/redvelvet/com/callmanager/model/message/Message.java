package abhishek.redvelvet.com.callmanager.model.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import abhishek.redvelvet.com.callmanager.model.boradcastreceiver.SmsStatus;
import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;

/**
 * Created by abhishek on 7/8/18.
 */

public class Message {

    int[] sub_id = new int[2];
    public static CharSequence[] operators = new CharSequence[2];

    String msg_body;
    Context context;
    public Message(String msg_body, Context context) {
        this.msg_body = msg_body;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void sendMsg(int which){

        try{
            //-------------send sms-----------

            SmsManager.getSmsManagerForSubscriptionId(sub_id[which]).sendTextMessage("1909",null,msg_body, SmsStatus.sendPI,SmsStatus.deliveredPI);
            Log.e("msg send","msg send");

            SharedPreferences dndSmsStatus = context.getSharedPreferences(MessageConstant.DND_PREFRENCE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = dndSmsStatus.edit();
            editor.putString(MessageConstant.KEY_OPERATOR,operators[which]+" | "+sub_id[which]);
            editor.commit();
        }
        catch (Exception e){

            Log.e("message",e+"");
            e.printStackTrace();
            Toast.makeText(context, "error sending", Toast.LENGTH_SHORT).show();
        }

    }

    public void getSim(){
        //--------initalize array--------
        Arrays.fill(sub_id,-1);
        //--------------------------------

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
                List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();

                Log.e("Test", "Current list = " + subsInfoList);
                int count = 0;
                for (SubscriptionInfo subscriptionInfo : subsInfoList) {

                    String number = subscriptionInfo.getNumber();
                    String operator = subscriptionInfo.getDisplayName().toString();
                    operators[count] = operator;
                    int id = subscriptionInfo.getSubscriptionId();
                    sub_id[count++] = id;
                    Log.e("Test", " Number is  " + number + " " + operator + "  " + id);
                }
            }
        }
        catch (Exception e){
            Log.e("getSim",""+e);
        }
    }
}
