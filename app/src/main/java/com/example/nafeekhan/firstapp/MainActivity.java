package com.example.nafeekhan.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

//.8.2.jar.com.google.gson;
//import com.example.nafeekhan.subscriptionmanager.MyApp;
//import org.apache.commons.io.IOUtils;


public class MainActivity extends AppCompatActivity {
    /*
    No writing to storage is done on this activity. This activity only loads from storage and displays
    for selection.
    Clicks allowed on either:
        a. Items
        b. "Add" Button
     Both leads user to AddEditActivity Page which receives the ArrayList of subscriptions and the
     clicked on  subscription item, if an item was clicked. If the Add button is clicked then
     this Activity sends the ArrayList of subscriptions and a pseudo-nullObject subscription (developer
     defined subscription with all required fields blank and comment field containing "null".)

     everytime OnResume is called the ArrayList subscriptions gets refreshed(and so does the screen
     through the adapter)
     */
    public static final String APP_DATA_FILE = "subBook.json";
    static final int INIT_REQUEST = 0;
    static final int ADD_REQUEST = 2;
    static final int EDIT_REQUEST = 1;
    static ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
    SubbookAdapter adapter;// = new SubbookAdapter(MainActivity.this, subscriptions);
    ListView lvSubBook;
    //private static ArrayList<Subscription> subscriptions = new ArrayList<>();
    Button btnAdd;
    TextView altText;
    TextView tvCalc;
    //double TOTAL = (double) 0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //final ListView lvSubBook = (ListView) findViewById(R.id.lv_subbook);
        File appFile = new File(getFilesDir(), "subBook.json");
        if (!appFile.exists()) {
            try {
                appFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean init = false;
        if (init) {//subscriptions.size()==0||!appFile.exists()) {
            ////setContentView(R.layout.activity_main_alt);
            //altText = findViewById(R.id.tv_row);

            altText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent initIntent = new Intent(MainActivity.this, AddEditActivity.class);
                    initIntent.putExtra("editType", 0);
                    startActivityForResult(initIntent, INIT_REQUEST);
                }
            });

        } else {

            //lvSubBook = findViewById(R.id.lv_subbook);
            setContentView(R.layout.activity_main);
            btnAdd = findViewById(R.id.add_button);
            tvCalc = findViewById(R.id.tv_calculate);
            lvSubBook = findViewById(R.id.lv_subbook);

            //String text = String.format("Total Monthly = %.2f", TOTAL);
            //tvCalc.setText(text);
            //CalcTotalMongthly(subscriptions);

            appFile = new File(getFilesDir(), APP_DATA_FILE);
            if (!appFile.exists()){
                try {
                    appFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*if (appFile.exists()) {
                try {
                    readJsonFromFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }*/
            try {
                readJsonFromFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            adapter = new SubbookAdapter(MainActivity.this, R.layout.subscription_item, subscriptions);
            lvSubBook.setAdapter(adapter);
            CalcTotalMongthly(subscriptions);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    if (subscriptions.size() == 0) {
                        intent.putExtra("editType", 0);
                        startActivityForResult(intent, INIT_REQUEST);
                    } else {
                        //intent.putExtra("CurrentSubbook", subscriptions);
                        intent.putExtra("editType", 2);
                        startActivityForResult(intent, ADD_REQUEST);
                    }
                }
            });

            lvSubBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = lvSubBook.getItemAtPosition(position);
                    Subscription s = (Subscription) o;
                    //Subscription s = (Subscription) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    intent.putExtra("oldname", s.getName());
                    intent.putExtra("oldmonthly", s.getMonthly());
                    intent.putExtra("olddate", s.getDate());
                    intent.putExtra("oldcomment", s.getComment());
                    intent.putExtra("editType", 1);
                    startActivityForResult(intent, EDIT_REQUEST);
                /*
                Subscription s = (Subscription) lvSubBook.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("CurrentSubbook", subscriptions);
                intent.putExtra("SelectedSub", s);
                intent.putExtra("editType", 1);
                startActivityForResult(intent, EDIT_REQUEST);*/

                }
            });
        }
    }

    public void readJsonFromFile() throws FileNotFoundException {


        Type type = new TypeToken<ArrayList<Subscription>>() {
        }.getType();
        Gson gson = new Gson();
        //JsonReader reader = new JsonReader(APP_DATA_FILE);
        InputStream is = new FileInputStream(APP_DATA_FILE);

        ArrayList<Subscription> sub = gson.fromJson(String.valueOf(is), type);
        subscriptions.addAll(sub);
    }

    /*
    public String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }*/
    @Override
    protected void onPause() {
        super.onPause();
        Type type = new TypeToken<ArrayList<Subscription>>() {
        }.getType();
        Gson gson = new Gson();
        File file = new File(getFilesDir(), APP_DATA_FILE);
        if (subscriptions.size()!=0) {
            /*try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            try {

                //OutputStream os = new FileOutputStream(APP_DATA_FILE)

                JsonWriter writer = new JsonWriter(new FileWriter(APP_DATA_FILE));
                gson.toJson(subscriptions, type, writer);

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }/*else if (file.exists()){
            try {
                JsonWriter writer = new JsonWriter(new FileWriter(file,false));
                gson.toJson(subscriptions, type, writer);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        //super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Type type = new TypeToken<ArrayList<Subscription>>() {
        }.getType();
        Gson gson = new Gson();
        File file = new File(getFilesDir(), APP_DATA_FILE);
        if (subscriptions.size()!=0) {
            /*
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            try {
                JsonWriter writer = new JsonWriter(new FileWriter(file,false));
                gson.toJson(subscriptions, type, writer);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }/*else if (file.exists()){
            try {
                JsonWriter writer = new JsonWriter(new FileWriter(file,false));
                gson.toJson(subscriptions, type, writer);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        //super.onPause();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == EDIT_REQUEST) {//|| requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                /*
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        intent.putExtra("CurrentSubbook", subscriptions);
                        intent.putExtra("editType", 2);
                        startActivityForResult(intent, ADD_REQUEST);
                    }
                });
                lvSubBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Subscription s = (Subscription) lvSubBook.getItemAtPosition(position);
                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        intent.putExtra("CurrentSubbook", subscriptions);
                        intent.putExtra("SelectedSub", s);
                        intent.putExtra("editType", 1);
                        startActivityForResult(intent, EDIT_REQUEST);

                    }
                });*/
                //adapter.refreshRoster(subscriptions);

            } else if (resultCode == RESULT_OK) {

                //subscriptions.clear();
                //ArrayList<Subscription> sub = (ArrayList<Subscription>) data.getSerializableExtra("FinalList");
                //subscriptions.addAll(sub);
                /*try {
                    readJsonFromFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/


                int edt = data.getExtras().getInt("editOrRemove");
                if (edt == -1) {
                    String newName = data.getExtras().getString("newname");
                    /*int newMonthly = data.getExtras().getInt("newmonthly");
                    String newDate = data.getExtras().getString("newdate");
                    String newComment = data.getExtras().getString("newcomment");*/

                    for (int x = 0; x < subscriptions.size(); x++) {
                        Subscription s = subscriptions.get(x);
                        if (s.getName().equals(newName)) {
                            subscriptions.remove(x);
                            break;
                        }
                    }
                    //adapter.notifyDataSetChanged();
                    if (lvSubBook.getAdapter() == null) {
                        adapter = new SubbookAdapter(MainActivity.this, R.layout.subscription_item, subscriptions);
                        lvSubBook.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    CalcTotalMongthly(subscriptions);
                    File file = new File(APP_DATA_FILE);
                    Gson gson = new Gson();
                    try {
                        JsonWriter writer = new JsonWriter(new FileWriter(file,false));
                        Type type = new TypeToken<ArrayList<Subscription>>() {
                        }.getType();
                        gson.toJson(subscriptions, type, writer);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (edt == 0) {
                    String newName = data.getExtras().getString("newname");
                    double newMonthly = data.getExtras().getDouble("newmonthly");
                    String newDate = data.getExtras().getString("newdate");
                    String newComment = data.getExtras().getString("newcomment");
                    String old = data.getExtras().getString("OLDNAME");
                    for (int x = 0; x < subscriptions.size(); x++) {
                        Subscription s = subscriptions.get(x);
                        if (s.getName().equals(old)) {
                            subscriptions.remove(x);
                            s.setName(newName);
                            s.setMonthly(newMonthly);
                            s.setDate(newDate);
                            s.setComment(newComment);
                            subscriptions.add(s);
                            break;
                        }

                    }
                    //adapter.notifyDataSetChanged();
                    if (lvSubBook.getAdapter() == null) {
                        adapter = new SubbookAdapter(MainActivity.this, R.layout.subscription_item, subscriptions);
                        lvSubBook.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    CalcTotalMongthly(subscriptions);



                    File file = new File(APP_DATA_FILE);
                    Gson gson = new Gson();
                    try {
                        JsonWriter writer = new JsonWriter(new FileWriter(file,false));
                        Type type = new TypeToken<ArrayList<Subscription>>() {
                        }.getType();
                        gson.toJson(subscriptions, type, writer);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }
        } else if (requestCode == INIT_REQUEST || requestCode == ADD_REQUEST) {


            if (resultCode == RESULT_CANCELED) {
                //adapter.refreshRoster(subscriptions);
        /*
                setContentView(R.layout.activity_main_alt);
                altText = findViewById(R.id.tv_row);
                altText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent initIntent = new Intent(MainActivity.this, AddEditActivity.class);
                        initIntent.putExtra("editType", 0);
                        startActivityForResult(initIntent, INIT_REQUEST);
                    }
                });*/
            } else if (resultCode == RESULT_OK) {


                String newName = data.getExtras().getString("newname");
                double newMonthly = data.getExtras().getDouble("newmonthly");
                String newDate = data.getExtras().getString("newdate");
                String newComment = data.getExtras().getString("newcomment");
                Subscription s = new Subscription(newName, newDate, newMonthly, newComment);
                subscriptions.add(s);
                CalcTotalMongthly(subscriptions);

                File file = new File(APP_DATA_FILE);
                Gson gson = new Gson();
                try {
                    JsonWriter writer = new JsonWriter(new FileWriter(file,false));
                    Type type = new TypeToken<ArrayList<Subscription>>() {
                    }.getType();
                    gson.toJson(subscriptions, type, writer);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (lvSubBook.getAdapter() == null) {
                    adapter = new SubbookAdapter(MainActivity.this, R.layout.subscription_item, subscriptions);
                    lvSubBook.setAdapter(adapter);
                } else {
                    //lvSubBook.invalidateViews();
                    //adapter.refreshRoster(subscriptions);
                    adapter.notifyDataSetChanged();
                    //lvSubBook.invalidateViews();
                    //lvSubBook.refreshDrawableState();
                    //adapter = new SubbookAdapter(MainActivity.this, subscriptions);
                    //lvSubBook.setAdapter(adapter);
                    //adapter.add(s);
                    //adapter.notifyDataSetChanged();
                }

                //ArrayList<Subscription> sub = (ArrayList<Subscription>) data.getSerializableExtra("FinalList");
                //subscriptions.addAll(sub);/*
                //setContentView(R.layout.activity_main);
                //btnAdd = findViewById(R.id.add_button);
                //lvSubBook = findViewById(R.id.lv_subbook);
                /*try {
                    readJsonFromFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
                //adapter = new SubbookAdapter(MainActivity.this, subscriptions);

                //lvSubBook.setAdapter(adapter);
                //adapter.refreshRoster(subscriptions);

/*
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        if (subscriptions.size() == 0) {
                            intent.putExtra("editType", 0);
                            startActivityForResult(intent, INIT_REQUEST);
                        } else {
                            //intent.putExtra("CurrentSubbook", subscriptions);
                            intent.putExtra("editType", 2);
                            startActivityForResult(intent, ADD_REQUEST);
                        }
                    }
                });

                lvSubBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Subscription s = (Subscription) lvSubBook.getItemAtPosition(position);
                        Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                        //intent.putExtra("CurrentSubbook", subscriptions);
                        //intent.putExtra("SelectedSub", s);
                        intent.putExtra("oldname", s.getName());
                        intent.putExtra("oldmonthly", s.getMonthly());
                        intent.putExtra("olddate", s.getDate());
                        intent.putExtra("oldcomment", s.getComment());
                        intent.putExtra("editType", 1);
                        startActivityForResult(intent, EDIT_REQUEST);

                    }
                });
*/

            }
        }

    }

    public void CalcTotalMongthly(ArrayList<Subscription> roster) {

        double total = (double) 0.0;
        if (roster.size() > 0) {

            for (int n = 0; n < roster.size(); n++) {
                Subscription s = roster.get(n);
                total = total + s.getMonthly();
            }
        }
        String text = String.format("Total Monthly = %.2f", total);
        tvCalc.setText(text);
    }
}
/*
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.add_button);
        lvSubBook = findViewById(R.id.lv_subbook);
        adapter.refreshRoster(subscriptions);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                if (subscriptions.size() == 0) {
                    intent.putExtra("editType", 0);
                    startActivityForResult(intent, INIT_REQUEST);
                } else {
                    //intent.putExtra("CurrentSubbook", subscriptions);
                    intent.putExtra("editType", 2);
                    startActivityForResult(intent, ADD_REQUEST);
                }
            }
        });

        lvSubBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subscription s = (Subscription) lvSubBook.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("oldname", s.getName());
                intent.putExtra("oldmonthly", s.getMonthly());
                intent.putExtra("olddate", s.getDate());
                intent.putExtra("oldcomment", s.getComment());
                intent.putExtra("editType", 1);
                startActivityForResult(intent, EDIT_REQUEST);
                /*
                Subscription s = (Subscription) lvSubBook.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("CurrentSubbook", subscriptions);
                intent.putExtra("SelectedSub", s);
                intent.putExtra("editType", 1);
                startActivityForResult(intent, EDIT_REQUEST);*/

            //}
        //});

    //}


/*
        super.onResume();
        File appFile = new File(APP_DATA_FILE);
        if(!appFile.exists()){
            altText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent initIntent = new Intent(MainActivity.this, AddEditActivity.class);
                    initIntent.putExtra("editType", 0);
                    startActivityForResult(initIntent, INIT_REQUEST);
                }
            });

        } else {
            /*try {
                readJsonFromFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            //adapter.refreshRoster(subscriptions);
            //try {
            //  String jsonStr = readFile(MyApp.APP_DATA_FILE);
            //subscriptions = Subscription.jsonToSub(jsonStr);
            //adapter.refreshRoster(subscriptions);
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}
            /*
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    intent.putExtra("CurrentSubbook", subscriptions);
                    intent.putExtra("editType", 2);
                    startActivityForResult(intent, ADD_REQUEST);
                }
            });

            lvSubBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Subscription s = (Subscription) lvSubBook.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                    intent.putExtra("CurrentSubbook", subscriptions);
                    intent.putExtra("SelectedSub", s);
                    intent.putExtra("editType", 1);
                    startActivityForResult(intent, EDIT_REQUEST);

                }
            });*/
        //}

    //}


//}
