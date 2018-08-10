package abhishek.redvelvet.com.callmanager.view_model.fragments.inner_fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.adapters.ContactListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    ListView contact_listview = null;
    Context context;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        contact_listview = (ListView)view.findViewById(R.id.contact_list);

        context = getActivity().getApplicationContext();

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        ContactListAdapter contactListAdapter = new ContactListAdapter(getActivity().getApplicationContext(),R.layout.contact_item_style);
        contact_listview.setAdapter(contactListAdapter);


    }
}
