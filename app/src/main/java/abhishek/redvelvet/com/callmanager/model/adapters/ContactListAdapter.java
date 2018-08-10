package abhishek.redvelvet.com.callmanager.model.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.constant.ContactConstant;
import abhishek.redvelvet.com.callmanager.model.variable.Colors;
import abhishek.redvelvet.com.contactlib.LoadContacts;

import static abhishek.redvelvet.com.callmanager.model.constant.ContactConstant.contacts;

/**
 * Created by abhishek on 5/8/18.
 */

public class ContactListAdapter extends ArrayAdapter {

    Context context;
    int resource;
    static ArrayList<HashMap> contact_list = null;

    public static final String[] contact_keys = {"display_name","contact_photo"};
    public static final int[] contact_ids = {R.id.display_name,R.id.image_letter};

    public ContactListAdapter(@NonNull Context context, int resource) {
        super(context, resource);

        this.context = context;
        this.resource = resource;
        init();
    }

    private void init(){

        if (contact_list==null) {
            new ContactConstant(context).init();
            contact_list = contacts.getAllContacts(0);
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contact_item_style,parent,false);
        }

        //---------assign the values----------
        ImageView profile_pic = (ImageView)convertView.findViewById(R.id.image_letter);
        TextView display_name = (TextView)convertView.findViewById(R.id.display_name);

        HashMap hm = contact_list.get(position);
        String name = hm.get(contact_keys[0]).toString();
        Bitmap image_bitmap = (Bitmap)hm.get(contact_keys[1]);
        if (image_bitmap==null){
            String char_ = null;
            if (name.matches("[0-9+]+")){
                char_ = "#";
            }
            else{
                char_ = name.toUpperCase().charAt(0)+"";
            }

            TextDrawable drawable = TextDrawable.builder().buildRect(char_+"",new Colors().getColor());
            profile_pic.setImageDrawable(drawable);
        }
        else{
            profile_pic.setImageBitmap(image_bitmap);
        }
        display_name.setText(name);

        return convertView;
    }

    @Override
    public int getCount() {
        if (contact_list==null)
            init();
        return contact_list.size();
    }
}
