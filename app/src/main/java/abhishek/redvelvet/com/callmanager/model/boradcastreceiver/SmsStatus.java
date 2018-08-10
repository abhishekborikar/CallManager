package abhishek.redvelvet.com.callmanager.model.boradcastreceiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import abhishek.redvelvet.com.callmanager.model.constant.ContactConstant;
import abhishek.redvelvet.com.callmanager.model.constant.MessageConstant;

/**
 * Created by abhishek on 8/8/18.
 */

public class SmsStatus {

    public static final String SENT = "SMS_SENT";
    public static final String DELIVERED = "SMS_RECEIVED";

    public static PendingIntent sendPI, deliveredPI;
    public static BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    Context context;

    public SmsStatus(Context context) {
        this.context = context;
    }

    public void initIntent(){
        sendPI = PendingIntent.getBroadcast(context,0,new Intent(SENT),0);
        deliveredPI = PendingIntent.getBroadcast(context,0,new Intent(DELIVERED),0);

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e("send receiver",getResultCode()+"");

                switch (getResultCode()){
                    case -1:{
                        Log.e("first msg","sucessful");
                        break;
                    }
                    case 1:{
                        //----------smsmanager.generic failer----------
                        break;
                    }
                    case 3:{
                        //----------smsmanager.error no pdu----------
                        break;
                    }
                    case 4:{
                        //----------smsmanager.error no service----------
                        break;
                    }
                    default:{
                        break;
                    }

                }

                context.unregisterReceiver(smsSentReceiver);
                context.registerReceiver(smsDeliveredReceiver,new IntentFilter(DELIVERED));

            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("delivery receiver",getResultCode()+"");

                switch (getResultCode()){
                    case -1:{
                            Log.e("first deliver","sucessful");
                        break;
                    }
                    case 1:{
                        //----------smsmanager.generic failer----------
                        break;
                    }
                    case 3:{
                        //----------smsmanager.error no pdu----------
                        break;
                    }
                    case 4:{
                        //----------smsmanager.error no service----------
                        break;
                    }
                    default:{

                        break;
                    }

                }

                context.unregisterReceiver(smsDeliveredReceiver);
            }
        };
    }

    public void registerSentReceiver(){
        new SmsStatus(context).initIntent();
        context.registerReceiver(SmsStatus.smsSentReceiver,new IntentFilter(SmsStatus.SENT));
        Log.e("register receiver","registered");
    }

    public void registerDeliveredReceiver(){
        new SmsStatus(context).initIntent();
        context.registerReceiver(SmsStatus.smsDeliveredReceiver,new IntentFilter(SmsStatus.DELIVERED));
    }

    public void ifSentRegistered(BroadcastReceiver broadcastReceiver){
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryBroadcastReceivers(new Intent(SmsStatus.SENT),0);
        for (ResolveInfo info:
             resolveInfos) {
            Log.e("sms status 1",info.resolvePackageName+"  / "+info.describeContents()+" / "+info.filter+" / "+info.resolvePackageName);
        }
    }

    public void ifDeliverRegistered(BroadcastReceiver broadcastReceiver){
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryBroadcastReceivers(new Intent(SmsStatus.DELIVERED),0);

    }
}
