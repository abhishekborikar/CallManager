package abhishek.redvelvet.com.callmanager.view_model.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.pdfview.PDFView;

import java.net.URI;


import abhishek.redvelvet.com.callmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsageFragment extends Fragment {


    public UsageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usage, container, false);

//        PDFView pdfView = (PDFView)view.findViewById(R.id.pdfView);
//        pdfView.fromUri(Uri.parse("file://"+getActivity().getFilesDir()+"/usage.pdf"));


        // Inflate the layout for this fragment
        return view;
    }

}
