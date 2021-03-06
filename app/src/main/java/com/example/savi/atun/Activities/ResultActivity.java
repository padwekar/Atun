package com.example.savi.atun.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savi.atun.Constatnts.Constants;
import com.example.savi.atun.Database.DataHelper;
import com.example.savi.atun.Fragments.ConfirmClassFrag;
import com.example.savi.atun.Fragments.ListFragmentClassCreate;
import com.example.savi.atun.R;


public class ResultActivity extends AppCompatActivity {
    ConfirmClassFrag confirmFrag ;
    ListFragmentClassCreate ClassListCreateFrag ;
    View coordinatorLayoutView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Fill Student List");
        setContentView(R.layout.activity_result);
        confirmFrag = new ConfirmClassFrag();
        ClassListCreateFrag = new ListFragmentClassCreate();
        coordinatorLayoutView = (View)findViewById(R.id.snackbarPosition);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_result_top, confirmFrag);
        fragmentTransaction.add(R.id.fragment_result_middle,ClassListCreateFrag);
        fragmentTransaction.commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final ProgressDialog progressDialog = new ProgressDialog(ResultActivity.this);
        progressDialog.setMessage("Saving..");
        switch (item.getItemId()){

            case R.id.btn_done :
                progressDialog.show();
                new Thread(new Runnable() {
                @Override
                public void run() {
                    createSuccessDialog();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                }

            }).start();
                return true ;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void createAttendanceTable(String TABLE_NAME) {
        DataHelper dataHelper = new DataHelper(ResultActivity.this);
        dataHelper.createClassTable(TABLE_NAME);
    }

    private void createSuccessDialog(){


        int total = Constants.updatedTotalStudent ;

        for(int i=0 ; i< total ; i++){
                if(Constants.studentname[i]==null||Constants.studentname[i].equals("")){
                    Snackbar snackbar = Snackbar.make(coordinatorLayoutView, "Names cannot be empty", Snackbar.LENGTH_SHORT);
                    View view = snackbar.getView();
                    TextView snackbar_msg = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    snackbar_msg.setTextColor(Color.RED);
                    snackbar.show();
                    return;
                }
        }

        createStudentTable();
        createAttendanceTable(Constants.updatedClassID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .hide(confirmFrag).hide(ClassListCreateFrag).commit();

        final AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Status");
        builder.setMessage("Success");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builder.create();
                builder.show();
            }
        });

    }


    private void createStudentTable(){
        String nameString ="";
        for(int i=0 ; i< Constants.studentname.length ; i++)
                 nameString =nameString+ Constants.studentname[i] + "," ;
        nameString =nameString.substring(0,nameString.length()-1);

        String classId =  Constants.updatedClassID;
        String className = Constants.updatedClassName;
        String section = Constants.updatedSection;
        String department = Constants.updatedDepartment;
        int strength = Constants.updatedTotalStudent;
        String studentList = nameString;

        DataHelper dataHelper = new DataHelper(ResultActivity.this);
        dataHelper.insertData(classId,className,section,department,strength,studentList);


    }
}
