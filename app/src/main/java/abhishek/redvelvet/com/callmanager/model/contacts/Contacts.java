package abhishek.redvelvet.com.callmanager.model.contacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.constant.ContactConstant;
import abhishek.redvelvet.com.callmanager.model.variable.Colors;

import static abhishek.redvelvet.com.callmanager.model.constant.ContactConstant.cursor_Android_Contacts;

/**
 * Created by abhishek on 6/8/18.
 */

public class Contacts {
    Context context;
    Cursor cursor_r_contacts;
    ContentResolver contentResolver = null;
    public static final String[] getRecentContact_keys = {"display_name", "phone_number", "time","type"};

    public Contacts(Context context) {
        this.context = context;
        if (context != null)
            contentResolver = context.getContentResolver();

    }


    /*
     *  Return call log present in mobile
     *  @param
     *  @return ArrayList<HashMap> al
     *  keys for hashmap ==>>
     *  1) String display_name = hm.get("display_name"); "No Name" if not exits in contact_list
     *  2) String phone_number = hm.get("phone_number");
     *  3) String time         = hm.get("time");
     *  4) String type         = hm.get("type");
     *
     *  type --> "OUTGOING", "INCOMING", "MISSED"
     *  time format = DD/MM/YYYY HH:MM:SS
     */
    public ArrayList<HashMap> getRecentContacts(){
        ArrayList al = new ArrayList();
        //get the recent call log
        try {

            //--------checks the permission to read contact list and recent contact list------------
            ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
            ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_CALL_LOG);
            //--------------------------------------------------------------------------------------

            //-------------get the recent contacts with in the cursor "cursor_r_contact"------------
            cursor_r_contacts = contentResolver.query(CallLog.Calls.CONTENT_URI,
                    null,
                    null,
                    null,
                    CallLog.Calls.DATE+" DESC");

            //--------------------------------------------------------------------------------------

        } catch (Exception e) {
            Log.e("e",e+"");
        }

        //------------------extract all recent contact here-----------------------------------------
        if (cursor_r_contacts != null) {
            if (cursor_r_contacts.getCount() > 0) {
                if (cursor_r_contacts.moveToFirst()) {
                    do {
                        HashMap hm = new HashMap();

                        //---------------get complete details of calls------------------------------
                        String displayName = cursor_r_contacts.getString(cursor_r_contacts.getColumnIndex(CallLog.Calls.CACHED_NAME));
                        String number = cursor_r_contacts.getString(cursor_r_contacts.getColumnIndex(CallLog.Calls.NUMBER));
                        String time = cursor_r_contacts.getString(cursor_r_contacts.getColumnIndex(CallLog.Calls.DATE));
                        String type_int = cursor_r_contacts.getString(cursor_r_contacts.getColumnIndex(CallLog.Calls.TYPE));
                        //--------------------------------------------------------------------------

                        //-----------------Add the call details HashMap-----------------------------

                        //-------1) Add display_name--------
                        if (displayName != null)
                            hm.put(getRecentContact_keys[0],displayName);
                        else
                            hm.put(getRecentContact_keys[0],"No Name");

                        //------2) Add Phone_number----------
                        hm.put(getRecentContact_keys[1],number);

                        //--------------convert millisec time---------------------------------------
                        Calendar cl = Calendar.getInstance();
                        cl.setTimeInMillis(Long.parseLong(time));  //here your time in miliseconds
                        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "/" + cl.get(Calendar.MONTH) + "/" + cl.get(Calendar.YEAR);
                        String f_time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE)+":"+cl.get(Calendar.SECOND) ;
                        //--------------------------------------------------------------------------

                        //-------3) Add Time------------------
                        hm.put(getRecentContact_keys[2],date+" "+f_time);

                        //-------4) Add Type of Call----------
                        String type = null;
                        switch (Integer.parseInt(type_int)) {
                            case CallLog.Calls.OUTGOING_TYPE:
                                type = "OUTGOING";
                                break;

                            case CallLog.Calls.INCOMING_TYPE:
                                type = "INCOMING";
                                break;

                            case CallLog.Calls.MISSED_TYPE:
                                type = "MISSED";
                                break;
                        }
                        hm.put("type",type);
                        al.add(hm);

                        //-------------------------end adding details to HashMap--------------------

                    } while (cursor_r_contacts.moveToNext());
                }
            } else {
                return null;
            }
        } else {
            return null;
        }

        //------------------------------end extraction----------------------------------------------

        return al;
    }


    public void getProfilePic(String display_name, String phone_number, ImageView profile_pic){

        Bitmap photo = null;

        try {

            boolean isAllDigits = display_name.contains("No Name");


            if (!isAllDigits) {
                //--------------Contact ID---------------------------------
                String contactId = null;
                contactId = new ContactConstant(context).initContactCursor(display_name);
                //---------------------------------------------------------

                //-------------get contact image-----------------------------

                photo = BitmapFactory.decodeResource(context.getResources(), 0);

                InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                        context.getContentResolver(),
                        ContentUris.withAppendedId(
                                ContactsContract.Contacts.CONTENT_URI,
                                new Long(contactId)
                        )
                );

                if (inputStream != null) {
                    photo = BitmapFactory.decodeStream(inputStream);
                    profile_pic.setImageBitmap(photo);

                }
                else{
                    profile_pic.setImageDrawable(applyTextDrawable(display_name,phone_number));
                }

            }
            else{
                profile_pic.setImageDrawable(applyTextDrawable(display_name,phone_number));
            }

            return ;
        }
        catch (Exception e){
            Log.e("profile pic err",e+"");
        }
        return  ;
    }

    public static Drawable applyTextDrawable(String display_name, String phone_number){

        String char_ = null;

        if (display_name.contains("No Name")){
               char_ = "#";
        }
        else{
             char_ = display_name.toUpperCase().charAt(0) + "";
        }

        TextDrawable drawable = TextDrawable.builder().buildRect(char_,new Colors().getColor());
        return drawable;
    }
}
