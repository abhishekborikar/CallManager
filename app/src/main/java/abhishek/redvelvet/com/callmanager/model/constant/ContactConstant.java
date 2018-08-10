package abhishek.redvelvet.com.callmanager.model.constant;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import abhishek.redvelvet.com.callmanager.model.contacts.Contacts;
import abhishek.redvelvet.com.contactlib.ContactOperation;
import abhishek.redvelvet.com.contactlib.LoadContacts;

/**
 * Created by abhishek on 5/8/18.
 */

public class ContactConstant {

    Context context;
    public static LoadContacts contacts = null;
    public static Contacts recent_contacts = null;
    public static Cursor cursor_Android_Contacts = null;

    public ContactConstant(Context context) {
        this.context = context;
    }

    public void init(){
        if (contacts==null)
        contacts = new LoadContacts(context.getContentResolver(), context);
    }

    public void init_recent(){
        if (recent_contacts == null){
            recent_contacts = new Contacts(context);
        }
    }

    public String initContactCursor(String display_name){

        try {
            cursor_Android_Contacts = context.getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts._ID},
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                    new String[]{display_name},
                    null);
            cursor_Android_Contacts.moveToFirst();
            return cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts._ID));
        }
        catch (Exception e){

        }
        return null;



    }

}
