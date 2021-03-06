package com.example.savi.atun.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.savi.atun.Constatnts.Constants;
import com.example.savi.atun.Fragments.ConfirmClassFrag;
import com.example.savi.atun.Fragments.ConfirmClassModified;
import com.example.savi.atun.Fragments.ShowClassListFragment;
import com.example.savi.atun.R;

import java.util.ArrayList;

public class TakeAttendanceActivity extends AppCompatActivity {
    ArrayList<String> studentList = new ArrayList<String>();
    ShowClassListFragment attendanceFragment;   ConfirmClassModified confirmClassFrag ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        Constants.deleteiconFlag=true;
        Intent intent = getIntent();
        String studentString =intent.getStringExtra("studentString");

       Bundle bundle = new Bundle();
       bundle.putString("studentString", studentString);

        attendanceFragment = new ShowClassListFragment();
        attendanceFragment.setArguments(bundle);
        confirmClassFrag = new ConfirmClassModified();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_confirmCLass,confirmClassFrag);
        fragmentTransaction.add(R.id.fragment_studentList, attendanceFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_attaindance,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        attendanceFragment.callSaveAttendance();
        return true;
    }

}
