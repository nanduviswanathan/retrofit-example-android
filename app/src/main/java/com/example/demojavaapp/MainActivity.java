package com.example.demojavaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

//public class MainActivity extends AppCompatActivity implements View.OnClickListener{
//
//
//    GridView coursesGV;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        findViewById(R.id.button).setOnClickListener(MainActivity.this);
//        findViewById(R.id.button2).setOnClickListener(MainActivity.this);
//
//
//
//        coursesGV = findViewById(R.id.idGVcourses);
//
//        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();
//        courseModelArrayList.add(new CourseModel("DSA", R.drawable.ic_launcher_background));
//        courseModelArrayList.add(new CourseModel("JAVA", R.drawable.ic_launcher_background));
//        courseModelArrayList.add(new CourseModel("C++", R.drawable.ic_launcher_background));
//        courseModelArrayList.add(new CourseModel("Python", R.drawable.ic_launcher_background));
//        courseModelArrayList.add(new CourseModel("Javascript", R.drawable.ic_launcher_background));
//        courseModelArrayList.add(new CourseModel("DSA", R.drawable.ic_launcher_background));
//
//        CourseGVAdapter adapter = new CourseGVAdapter(MainActivity.this, courseModelArrayList);
//        coursesGV.setAdapter(adapter);
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.button){
//            Log.d("data","ClickMe");
//            new Thread(r).start();
//
//        } else if (view.getId() == R.id.button2) {
//            Log.d("data","btn23");
//            TextView textView = (TextView) findViewById(R.id.textView);
//            textView.setText("Btn 2 Pressed");
//        }
//    }
//
//    Runnable r = new Runnable() {
//        public void run() {
//            Log.d("thread","Log started");
//
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    Log.d("thread", "UI thread");
//                    // Stuff that updates the UI
//                    TextView textView = (TextView) findViewById(R.id.textView);
//                    textView.setText("data from bg thread");
//
//                }
//            });
//        }
//    };
//}
public class MainActivity extends AppCompatActivity {


    class Spacecraft {
        /*
        INSTANCE FIELDS
         */
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("propellant")
        private String propellant;
        @SerializedName("imageurl")
        private String imageURL;
        @SerializedName("technologyexists")
        private int technologyExists;

        public Spacecraft(int id, String name, String propellant, String imageURL, int technologyExists) {
            this.id = id;
            this.name = name;
            this.propellant = propellant;
            this.imageURL = imageURL;
            this.technologyExists = technologyExists;
        }

        /*
         *GETTERS AND SETTERS
         */
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPropellant() {
            return propellant;
        }

        public String getImageURL() {
            return imageURL;
        }

        public int getTechnologyExists() {
            return technologyExists;
        }

        /*
        TOSTRING
         */
        @Override
        public String toString() {
            return name;
        }
    }

    interface MyAPIService {

        @GET("/Oclemy/SampleJSON/338d9585/spacecrafts.json")
        retrofit2.Call<List<Spacecraft>> getSpacecrafts();
    }

    static class RetrofitClientInstance {

        private static Retrofit retrofit;
        private static final String BASE_URL = "https://raw.githubusercontent.com/";

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }

    class GridViewAdapter extends BaseAdapter {

        private List<Spacecraft> spacecrafts;
        private Context context;

        public GridViewAdapter(Context context,List<Spacecraft> spacecrafts){
            this.context = context;
            this.spacecrafts = spacecrafts;
        }

        @Override
        public int getCount() {
            return spacecrafts.size();
        }

        @Override
        public Object getItem(int pos) {
            return spacecrafts.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if(view==null)
            {
                view= LayoutInflater.from(context).inflate(R.layout.fragment_card_view,viewGroup,false);
            }

            TextView nameTxt = view.findViewById(R.id.idTVCourse);
            ImageView spacecraftImageView = view.findViewById(R.id.idIVcourse);

            final Spacecraft thisSpacecraft= spacecrafts.get(position);

            nameTxt.setText(thisSpacecraft.getName());

            if(thisSpacecraft.getImageURL() != null && thisSpacecraft.getImageURL().length()>0)
            {
                Picasso.get().load(thisSpacecraft.getImageURL()).placeholder(R.drawable.ic_launcher_background).into(spacecraftImageView);
            }else {
                Toast.makeText(context, "Empty Image URL", Toast.LENGTH_LONG).show();
                Picasso.get().load(R.drawable.ic_launcher_background).into(spacecraftImageView);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, thisSpacecraft.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }

    private GridViewAdapter adapter;
    private GridView mGridView;

    private void populateGridView(List<Spacecraft> spacecraftList) {
        mGridView = findViewById(R.id.idGVcourses);
        adapter = new GridViewAdapter(this,spacecraftList);
        mGridView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Create handle for the RetrofitInstance interface*/
        MyAPIService myAPIService = RetrofitClientInstance.getRetrofitInstance().create(MyAPIService.class);

        retrofit2.Call<List<Spacecraft>> call = myAPIService.getSpacecrafts();
        call.enqueue(new Callback<List<Spacecraft>>() {

            @Override
            public void onResponse(retrofit2.Call<List<Spacecraft>> call, Response<List<Spacecraft>> response) {
                populateGridView(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Spacecraft>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("error", t.getMessage());
            }
        });
    }
}