package com.example.nafeekhan.firstapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
//import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
//import android.widget.EditText;
import android.widget.Toast;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class AddEditActivity extends AppCompatActivity {
    // String regex = "^\\d{4}-\\d{2}-\\d{2}$";

    EditText editName;
    EditText editComment;
    EditText editMonthly;
    EditText pickDate;
    Button btnSubmit, btnDelete, btnEdit;
    //private static ArrayList<Subscription> subBook = new ArrayList<Subscription>();
    //private Subscription toBeEdited;
    //private Subscription newEdited;
    private final Calendar myCalender = Calendar.getInstance();

    //private boolean isName, isMonthly, isDate;

    static final int ADD_INIT = 0;
    static final int EDIT_TXT = 1;
    static final int ADD_NEW = 2;
    static int type;
    String oldName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_form);
        // get current subscription list and get the subscription to be edited from Intent
        // if Add was clicked then expect toBeEdited.isNull()
        editName = (EditText) findViewById(R.id.et_name_text);
        editMonthly = (EditText) findViewById(R.id.et_monthly);
        editComment = (EditText) findViewById(R.id.et_comment);
        pickDate = (EditText) findViewById(R.id.et_date_started);

        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        pickDate.setKeyListener(null);
        pickDate.setClickable(false);
        Intent received = getIntent();
        type = received.getExtras().getInt("editType");
        if (type == ADD_INIT) {
            /*isName = false;
            isDate = false;
            isMonthly = false;*/
            pickDate.setClickable(true);
            handleEditing();
        } else if (type == ADD_NEW) {
            //subBook.addAll((ArrayList<Subscription>) getIntent().getSerializableExtra("CurrentSubbook"));

            /*isName = false;
            isDate = false;
            isMonthly = false;*/
            pickDate.setClickable(true);

            handleEditing();
        } else if (type == EDIT_TXT) {
            //subBook.addAll((ArrayList<Subscription>) getIntent().getSerializableExtra("CurrentSubbook"));//only if not init
            //toBeEdited = (Subscription) getIntent().getSerializableExtra("SelectedSub");// only if edit

            oldName = received.getExtras().getString("oldname");
            String oldDate = received.getExtras().getString("olddate");
            double oldMonthly = received.getExtras().getDouble("oldmonthly");
            String oldComment = received.getExtras().getString("oldcomment");

            editName.setText(oldName);
            editComment.setText(oldComment);
            editMonthly.setText(String.valueOf(oldMonthly));
            pickDate.setText(oldDate);

            editName.setEnabled(false);
            editMonthly.setEnabled(false);
            editComment.setEnabled(false);

            btnSubmit.setVisibility(View.INVISIBLE);
            btnSubmit.setClickable(false);
            pickDate.setClickable(false);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!editName.isEnabled() && !editMonthly.isEnabled() && !editComment.isEnabled()) {

                        editName.setEnabled(true);
                        editComment.setEnabled(true);
                        editMonthly.setEnabled(true);
                        //pickDate.setClickable(true);

                        //btnEdit.setVisibility(View.GONE);
                        //btnDelete.setVisibility(View.GONE);
                        btnDelete.setVisibility(View.INVISIBLE);
                        btnDelete.setClickable(false);
                        btnEdit.setVisibility(View.INVISIBLE);
                        btnEdit.setClickable(false);
                        btnSubmit.setVisibility(View.VISIBLE);


                        /*isName = true;
                        isDate = true;
                        isMonthly = true;*/

                        handleEditing();
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    for (int i = 0; i < subBook.size(); i++) {
                        Subscription s = subBook.get(i);
                        if (s.equals(toBeEdited)) {
                            subBook.remove(i);
                        }
                    }
                    try {
                        FileWriter writer = new FileWriter("subBook.json", false);
                        writer.write("");
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    //storeUpdatedSubbook();

                    Intent result = new Intent();
                    result.putExtra("newname",oldName);
                    result.putExtra("editOrRemove", -1);

                    setResult(RESULT_OK, result);
                    finish();
                }
            });


        }
    }

    private void updateLabel() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        pickDate.setText(df.format(myCalender.getTime()));
        handleEditing();
        //isDate = true;
    }

    private void handleEditing() {

        //if(!pickDate.isClickable()){
        //pickDate.setClickable(true);
        //}
        //final long longDate = (long) myCalender.getTimeInMillis();
        //btnDelete.setVisibility(View.GONE);
        //btnEdit.setVisibility(View.GONE);
        btnDelete.setVisibility(View.INVISIBLE);
        btnDelete.setClickable(false);
        btnEdit.setVisibility(View.INVISIBLE);
        btnEdit.setClickable(false);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }
        };

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddEditActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
                        myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        //Editing Name field below
        int nameLength = 20;
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i))) {
                        if (!Character.isSpaceChar(source.charAt(i)))
                            return "";
                    }
                }
                return null;
            }
        };
        editName.setFilters(new InputFilter[]{
                filter
        });
        editName.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(nameLength)
        });
        editName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (editName.getText().toString().length() == 0) {
                    Toast.makeText(AddEditActivity.this, "Required Field", Toast.LENGTH_SHORT).show();
                    //isName = false;
                } /*else {
                    isName = true;
                }*/
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Editing Comment

        int commentLength = 30;
        editName.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(commentLength)
        });

        //Editing Monthly
        InputFilter filter2 = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i)) && !Character.toString(source.charAt(i)).equals(".")) {
                        //if (!Character.isSpaceChar(source.charAt(i)))
                        return "";
                    }
                }
                return null;
            }
        };

        editMonthly.setFilters(new InputFilter[]{
                filter2
        });

        editMonthly.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (editMonthly.getText().toString().length() == 0) {
                    Toast.makeText(AddEditActivity.this, "Required Field", Toast.LENGTH_SHORT).show();
                    //isMonthly = false;
                } /*else {
                    //isMonthly = true;
                }*/
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //SubmitButton Click event

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {


                    //newEdited.setName(editName.getText().toString());
                    //newEdited.setDate(pickDate.getText().toString());
                    //int newMonthly = Integer.parseInt(editMonthly.getText().toString());

                    //newEdited.setMonthly(result);
                    //newEdited.setComment(editComment.getText().toString());
                    //subBook.add(newEdited);
                    if (type == ADD_INIT || type == ADD_NEW) {

                        //clear jSON file
                        /*FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("subBook.json", Context.MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        PrintWriter writer = new PrintWriter(fos);
                        writer.print("");
                        writer.close();*/
                        String monthly = editMonthly.getText().toString();
                        double m = Double.parseDouble(monthly);
                        Intent result = new Intent();
                        result.putExtra("newname",editName.getText().toString());
                        result.putExtra("newdate",pickDate.getText().toString());
                        result.putExtra("newmonthly",m);
                        result.putExtra("newcomment",editComment.getText().toString());
                        result.putExtra("OLDNAME", oldName );
                        setResult(RESULT_OK, result);
                        finish();

                        //storeUpdatedSubbook();

                    } else {
                        String monthly = editMonthly.getText().toString();
                        double m = Double.parseDouble(monthly);
                        Intent result = new Intent();
                        result.putExtra("newname",editName.getText().toString());
                        result.putExtra("newdate",pickDate.getText().toString());
                        result.putExtra("newmonthly",m);
                        result.putExtra("newcomment",editComment.getText().toString());
                        result.putExtra("OLDNAME", oldName );

                        result.putExtra("editOrRemove", 0);
                        setResult(RESULT_OK, result);

                        finish();
                        //storeUpdatedSubbook();
                    }

                }
            }
        });


    }
    private boolean validation(){
        boolean nameValid = false;
        boolean monthlyValid = false;
        boolean dateValid = false;

        if (editName.getText().toString().length() > 0) {
            nameValid = true;
        }
        if (editMonthly.getText().toString().length() > 0) {
            monthlyValid = true;
        }
        if (pickDate.getText().toString().length() > 0) {
            dateValid = true;
        }

        return (nameValid && monthlyValid && dateValid);
    }
/*
    private void storeUpdatedSubbook() {
        /*String data = Subscription.subToJson(subBook);
        try{
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("subBook.json", Context.MODE_PRIVATE));
            osw.write(data);
            osw.close();

            Intent result = new Intent();
            result.putExtra("FinalList", subBook);
            setResult(Activity.RESULT_OK, result);
            finish();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*//*
        Type type = new TypeToken<ArrayList<Subscription>>() {
        }.getType();
        Gson gson = new Gson();
        try {
            JsonWriter writer = new JsonWriter(new FileWriter("subBook.json", false));
            gson.toJson(subBook, type, writer);
            writer.close();
/*
            Intent result = new Intent();
            result.putExtra("FinalList", subBook);
            setResult(Activity.RESULT_OK, result);
            finish();
*//*
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent result = new Intent();
        result.putExtra("FinalList", subBook);
        setResult(Activity.RESULT_OK, result);
        finish();
*/
    //}
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent result = new Intent();
        setResult(Activity.RESULT_CANCELED, result);
        finish();

    }
}
