package abhishek.redvelvet.com.callmanager.model.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;


import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;

import abhishek.redvelvet.com.callmanager.R;

/**
 * Created by abhishek on 8/8/18.
 */

public class UsageDialog extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_usage,null);

        WebView web  = (WebView)view.findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
//        web.loadUrl("https://drive.google.com/open?id=1te-wA2E3t2JyOYZRNDz0syJz5Sct-hzi");
//        File file = getActivity().getFilesDir();
//
        PDFView pdfView = (PDFView)view.findViewById(R.id.pdfview);
        pdfView.fromAsset("usage.pdf")
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {

                    }
                })
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Toast.makeText(getActivity(), ""+page, Toast.LENGTH_SHORT).show();
                    }
                })
                .load();

//        Toast.makeText(getActivity(), ""+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.fragment_usage);
        return builder.create();
    }
}
