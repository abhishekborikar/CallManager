package abhishek.redvelvet.com.callmanager.model.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.constant.ContactConstant;
import abhishek.redvelvet.com.callmanager.model.contacts.Contacts;

import static abhishek.redvelvet.com.callmanager.model.constant.ContactConstant.contacts;
import static abhishek.redvelvet.com.callmanager.model.constant.ContactConstant.recent_contacts;

/**
 * Created by abhishek on 5/8/18.
 */

public class RecentContactListAdapter extends ArrayAdapter {

    Context context;
    int resource;
    static ArrayList<HashMap> recent_contact_list = null;

    public RecentContactListAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.context = context;
        this.resource = resource;

        init();
    }

    private void init(){
        if (recent_contact_list == null){
            new ContactConstant(context).init_recent();
            recent_contact_list = recent_contacts.getRecentContacts();
        }
    }

    @Override
    public int getCount() {
        return recent_contact_list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recent_contact_item_style,parent,false);
        }

        //---------------extract data---------------------------
        HashMap hm = recent_contact_list.get(position);
        String display_name = hm.get("display_name").toString();
        String phone_number = hm.get("phone_number").toString();
        String time         = hm.get("time").toString();
//
//    String type         = hm.get("type").toString();
        //------------------------------------------------------

        //----------------widget--------------------------------
        ImageView profile_pic = (ImageView)convertView.findViewById(R.id.recent_image_letter);
        TextView tv_display_name = (TextView)convertView.findViewById(R.id.recent_display_name);
        TextView tv_info = (TextView)convertView.findViewById(R.id.recent_info);
        //------------------------------------------------------

        //----------------fill widget----------------------------
        new Contacts(context).getProfilePic(display_name, phone_number,profile_pic);

        if (display_name.contains("No Name"))
            tv_display_name.setText(phone_number);
        else
            tv_display_name.setText(display_name);
        tv_info.setText(time+" | "+phone_number);
        //-------------------------------------------------------

        return convertView;
    }
}
