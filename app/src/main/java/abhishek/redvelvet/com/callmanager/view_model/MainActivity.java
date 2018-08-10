package abhishek.redvelvet.com.callmanager.view_model;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abhishek.redvelvet.com.callmanager.R;
import abhishek.redvelvet.com.callmanager.model.boradcastreceiver.SmsStatus;
import abhishek.redvelvet.com.callmanager.model.message.Message;
import abhishek.redvelvet.com.callmanager.view_model.fragments.MainFragment;
import abhishek.redvelvet.com.callmanager.view_model.fragments.SettingFragment;
import abhishek.redvelvet.com.contactlib.LoadContacts;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    LoadContacts contacts;
    ArrayList names;
    Class fragmentClass;
    public static Fragment fragment;
    SNavigationDrawer navigationDrawer;

    static final int REQUESTCODE = 755;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        navigationDrawer = (SNavigationDrawer)findViewById(R.id.navigationDrawer);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();

//        new SmsStatus(getApplicationContext()).registerSentReceiver();
//        new SmsStatus(getApplicationContext()).registerDeliveredReceiver();

        //------------------------Permission Block-----------------------------------------
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            Log.w("main","Permission granted");

            navigationManager();
        }
        else {

            requestPermissions(new String[]{
                            Manifest.permission_group.SMS,
                            Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.WRITE_CALL_LOG,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.INTERNET
                    },
                    REQUESTCODE);

        }

        //----------------------------------------------------------------------------------


    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUESTCODE){
            navigationManager();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("REQUEST Code",requestCode+"");
    }

    private void navigationManager(){
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("Contact",R.drawable.phone));
        menuItems.add(new MenuItem("Setting",R.drawable.settings));
//        menuItems.add(new MenuItem("Usage",R.drawable.phone));


        navigationDrawer.setMenuItemList(menuItems);

        fragmentClass =  MainFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }


        navigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {

                switch (position){
                    case 0:{
                        fragmentClass = MainFragment.class;
                        break;
                    }

                    case 1: {
                        fragmentClass = SettingFragment.class;
                    }

                }

                //Listener for drawer events such as opening and closing.
                navigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });
    }
}
