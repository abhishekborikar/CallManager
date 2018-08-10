package abhishek.redvelvet.com.callmanager.model.boradcastreceiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;
import abhishek.redvelvet.com.callmanager.model.dialogs.StatusDialog;
import abhishek.redvelvet.com.callmanager.model.message.Message;

public class MessageReceiver extends BroadcastReceiver {
    String smsSender = "";
    String smsBody = "";

    PendingIntent sendPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;


    final String SENT_M = "SMS_SENT";
    final String DELIVERED_M = "SMS_RECEIVED";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onReceive(Context context, Intent intent) {

        try{
            //initiate intent to start service
            Intent msgService = new Intent(context,MessageReceiver.class);
            context.startService(msgService);

            //get the incoming message
            Bundle smsBundle = intent.getExtras();
            if(smsBundle!=null){
                Object[] pdus = (Object[])smsBundle.get("pdus");
                //if pdus is null throw some error message
                if(pdus==null){
                    Log.e("Pdus msg",": pdus is empty");
                    return;
                }

                SmsMessage message[] = new SmsMessage[pdus.length];
                //retrive message from object
                for (int i = 0; i < pdus.length; i++) {
                    //get the body of msg
                    message[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    smsBody += message[i].getMessageBody();
                }

                smsSender = message[0].getOriginatingAddress();

                reply(context);

            }
            else{
                Log.e("bundle msg",": bundle is null");
            }
        }
        catch(Exception e){

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void reply(Context context){

        SharedPreferences preferences = context.getSharedPreferences(MessageConstant.DND_PREFRENCE,Context.MODE_PRIVATE);
        String operator = preferences.getString(MessageConstant.KEY_OPERATOR,null);
        SharedPreferences.Editor editor = preferences.edit();
        //----------------reply to 1909----------------------
        if (smsSender.equalsIgnoreCase("1909")){

            if (smsBody.matches("(?i).*\\b(Please reply Y to confirm your request | reply Y)\\b.*")){

                context.registerReceiver(smsSentReceiver,new IntentFilter(SENT_M));
                context.registerReceiver(smsDeliveredReceiver,new IntentFilter(DELIVERED_M));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                sendMsg(smsBody,context);
            }

            if (smsBody.contains("DND") && smsBody.contains("registered")){

                Log.e("dnd register","inside");
                //---------update to status registered-----------
                editor.putString(MessageConstant.KEY_REGISTER,"Sucessfull "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
                editor.commit();

                new StatusDialog().updateUI();

            }
        }
        //----------------update if sms from operator----------------------
        else {
            Log.e("smssender",smsSender+" "+smsBody.matches("(?i).*\\b(DND && registered)\\b.*"));
            if (smsSender.contains(operator.split("\\|")[0].trim().toUpperCase())){

                if (smsBody.contains("DND") && smsBody.contains("registered")){

                    Log.e("dnd register","inside");
                    //---------update to status registered-----------
                    editor.putString(MessageConstant.KEY_REGISTER,"Sucessfull "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
                    editor.commit();

                    new StatusDialog().updateUI();

                }
                else if (smsBody.contains("DND") && smsBody.contains("request") && smsBody.contains("not") &&smsBody.contains("processed")){

                    editor.putString(MessageConstant.KEY_ACTIVE,"UnSucessful");
                    editor.commit();
                }
                else if (smsBody.contains("DND") && smsBody.contains("request") && smsBody.contains("processed")){
                    editor.putString(MessageConstant.KEY_ACTIVE,"Sucessfull");
                    editor.commit();
                }

                if ((smsBody.contains("DND Activation ") || smsBody.contains("DND request")) && smsBody.contains("successfully")){
                    Log.e("dnd activation","inside");

                    //---------update status sucessfully processed--------

                    editor.putString(MessageConstant.KEY_PROCESSED,"Sucessful \n"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
                    editor.commit();

                    new StatusDialog().updateUI();
                } else{
                    if (smsBody.contains("contact customer care"))
                    editor.putString(MessageConstant.KEY_PROCESSED,"Contact Customer Care");
                }

                new StatusDialog().updateUI();
                
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void sendMsg(String msg_body, Context context){

        sendPI = PendingIntent.getBroadcast(context,0,new Intent(SENT_M),0);
        deliveredPI = PendingIntent.getBroadcast(context,0,new Intent(DELIVERED_M),0);

        if (getResultCode() == -1){
            SharedPreferences dndSmsStatus = context.getSharedPreferences(MessageConstant.DND_PREFRENCE,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = dndSmsStatus.edit();
            editor.putBoolean(MessageConstant.KEY_REQUEST,true);
            editor.putString(MessageConstant.KEY_DELIVERY_DATE, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
            editor.commit();

        }
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e("receiver send",getResultCode()+"");
                final int m_1  = -1;
                switch (getResultCode()){
                    case -1:{


                        break;
                    }

                }
                context.unregisterReceiver(smsSentReceiver);
//                context.registerReceiver(smsDeliveredReceiver,new IntentFilter(DELIVERED_M));

            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("receiver delivery",getResultCode()+"");

                switch (getResultCode()){
                    case -1:{
                        Log.e("delivery msg 2nd","sucessful");

                        break;
                    }
                }

                context.unregisterReceiver(smsDeliveredReceiver);
            }
        };

        SharedPreferences preferences = context.getSharedPreferences(MessageConstant.DND_PREFRENCE,Context.MODE_PRIVATE);
        String operator = preferences.getString(MessageConstant.KEY_OPERATOR,null);

        int sub_id = Integer.parseInt(operator.split("\\|")[1].trim());
//        new SmsStatus(context).registerDeliveredReceiver();
//        new SmsStatus(context).registerSentReceiver();
        SmsManager.getSmsManagerForSubscriptionId(sub_id).sendTextMessage("1909",null,"Y",sendPI,deliveredPI);

    }
}
