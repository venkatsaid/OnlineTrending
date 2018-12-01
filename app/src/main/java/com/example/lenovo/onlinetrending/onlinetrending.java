package com.example.lenovo.onlinetrending;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class onlinetrending extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,LoaderManager.LoaderCallbacks {
    public static RecyclerView rv;
    public static String ONSAVEBUNDLESTATE;
    public static String state="top_headlines";
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public static String email_id;
    ArrayList<Article> articleinfo;
    public static int LOADER_ID=3;
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rv=(RecyclerView)findViewById(R.id.recycler);
        Toast.makeText(this, state, Toast.LENGTH_SHORT).show();
        auth = FirebaseAuth.getInstance();
        srl=(SwipeRefreshLayout)findViewById(R.id.swipe);
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email_id=user.getEmail();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(onlinetrending.this, LoginActivity.class));
                    finish();
                }
            }
        };

        if(deviseonline())
        {
            if(savedInstanceState!=null)
            {
                if(savedInstanceState.containsKey(ONSAVEBUNDLESTATE))
                {
                    switch (savedInstanceState.getString(ONSAVEBUNDLESTATE))
                    {
                        case "top_headlines":
                            state="top_headlines";
                            currentstate(state);
                            // Toast.makeText(this, state, Toast.LENGTH_SHORT).show();
                            break;
                        case "technology":
                            state="technology";
                            currentstate(state);
                            Toast.makeText(this, state, Toast.LENGTH_SHORT).show();break;
                        case "business":
                            state="business";
                            currentstate(state);
                            Toast.makeText(this, state, Toast.LENGTH_SHORT).show();break;
                        case "entertainment":
                            state="entertainment";
                            currentstate(state);
                            Toast.makeText(this, state, Toast.LENGTH_SHORT).show();break;
                        case "sports":
                            state="sports";
                            currentstate(state);
                            Toast.makeText(this, state, Toast.LENGTH_SHORT).show();break;
                        case "health":
                            state="health";
                            currentstate(state);   Toast.makeText(this, state, Toast.LENGTH_SHORT).show();break;
                        case "science":
                            state="science";
                            currentstate(state);
                            Toast.makeText(this, state, Toast.LENGTH_SHORT).show();break;
                        case "bookmark":
                            state="bookmark";
                            currentstate(state);   Toast.makeText(this, state, Toast.LENGTH_SHORT).show();break;
                    }
                }
                else {
                    currentstate(state);
                }
            }
            else
            {
                currentstate(state);
            }
        }
        else
        {
            if(savedInstanceState!=null)
            {
                if(savedInstanceState.containsKey(ONSAVEBUNDLESTATE)) {
                    switch (savedInstanceState.getString(ONSAVEBUNDLESTATE)) {
                        case "bookmark":
                            state = "bookmark";

                        default:
                            new AlertDialog.Builder(this).setTitle("No Internet").setMessage("Watch out internet connection").setIcon(R.mipmap.ic_launcher).setNeutralButton("Close", null).show();
                    }
                }
                else {
                    new AlertDialog.Builder(this).setTitle("No Internet").setMessage("Watch out internet connection").setIcon(R.mipmap.ic_launcher).setNeutralButton("Close", null).show();
                }
            }
            else {

                new AlertDialog.Builder(this).setTitle("No Internet").setMessage("Watch out internet connection").setIcon(R.mipmap.ic_launcher).setNeutralButton("Close", null).show();

            }


        }

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(onlinetrending.this, "swipe", Toast.LENGTH_SHORT).show();
                currentstate(state);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
    public void currentstate(String value)
    {
        switch (value)
        {
            case "top_headlines":
                state="top_headlines";
                Toast.makeText(onlinetrending.this, "swipe", Toast.LENGTH_SHORT).show();
                srl.setRefreshing(false);
                Myasynctask mk = new Myasynctask(this," https://newsapi.org/v2/top-headlines?country=in&apiKey=1502412d354c419b867a91a99f9ebdd6");
                mk.execute();


                //Toast.makeText(this, state, Toast.LENGTH_SHORT).show();
                break;
            case "technology":
                state="technology";
                srl.setRefreshing(false);
                Myasynctask mk1 = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=1502412d354c419b867a91a99f9ebdd6");
                mk1.execute();
                Toast.makeText(this, state, Toast.LENGTH_SHORT).show();
                break;

            case "business":
                state="business";
                srl.setRefreshing(false);
                Myasynctask mk2 = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=1502412d354c419b867a91a99f9ebdd6");
                mk2.execute();

                break;
            case "entertainment":
                state="entertainment";
                srl.setRefreshing(false);
                Myasynctask mk3= new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=1502412d354c419b867a91a99f9ebdd6");
                mk3.execute();break;
            case "sports":
                state="sports";
                srl.setRefreshing(false);
                Myasynctask mk4 = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=1502412d354c419b867a91a99f9ebdd6");
                mk4.execute();break;
            case "health":
                state="health";
                srl.setRefreshing(false);
                Myasynctask mk5 = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=1502412d354c419b867a91a99f9ebdd6");
                mk5.execute();break;
            case "science":
                state="science";
                srl.setRefreshing(false);
                Myasynctask mk6 = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=1502412d354c419b867a91a99f9ebdd6");
                mk6.execute();break;
            case "bookmark":
                state="bookmark";
                srl.setRefreshing(false);
                bookmarkmethod();break;

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ONSAVEBUNDLESTATE,state);

    }
    public boolean deviseonline()
    {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.technology) {
            state="technology";
            srl.setRefreshing(false);
            Myasynctask mk = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=1502412d354c419b867a91a99f9ebdd6");
            mk.execute();

        } else if (id == R.id.business) {
            state="business";
            srl.setRefreshing(false);

            Myasynctask mk = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=1502412d354c419b867a91a99f9ebdd6");
            mk.execute();

        } else if (id == R.id.sports) {
            state="sports";
            srl.setRefreshing(false);

            Myasynctask mk = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=1502412d354c419b867a91a99f9ebdd6");
            mk.execute();
        } else if (id == R.id.science) {
            state="science";
            srl.setRefreshing(false);

            Myasynctask mk = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=1502412d354c419b867a91a99f9ebdd6");
            mk.execute();
        } else if (id == R.id.health) {
            state="health";
            srl.setRefreshing(false);

            Myasynctask mk = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=1502412d354c419b867a91a99f9ebdd6");
            mk.execute();
        } else if (id == R.id.entertainment) {
            state="entertainment";
            srl.setRefreshing(false);

            Myasynctask mk = new Myasynctask(this,"https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=1502412d354c419b867a91a99f9ebdd6");
            mk.execute();
        } else if (id == R.id.topheadlines) {
            state="top_headlines";
            srl.setRefreshing(false);

            Myasynctask mk = new Myasynctask(this," https://newsapi.org/v2/top-headlines?country=in&apiKey=1502412d354c419b867a91a99f9ebdd6");
            mk.execute();}
        else if (id == R.id.Bookmark) {
            state="bookmark";
            srl.setRefreshing(false);

            bookmarkmethod();
        }else if (id == R.id.signout) {
            auth.signOut();

// this listener will be called when there is change in firebase user session

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void bookmarkmethod()
    {
        getLoaderManager().restartLoader(LOADER_ID,null,onlinetrending.this);
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        return new AsyncTaskLoader(this) {
            @Override
            public Object loadInBackground() {
                Cursor newsdetails=getContentResolver().query(Uri.parse(NewsDatabase.CONTENT_URI+"/*"),null,NewsDatabase.Email+" LIKE ?",new String[]{email_id},null);

                articleinfo=new ArrayList<>();
                if(newsdetails.getCount()>0)
                {
                    if(newsdetails.moveToFirst()) {
                        do {
                            /*Source source, String author, String title, String description, String url, String urlToImage, String publishedAt)*/
                            Source source=new Source(newsdetails.getString(6),newsdetails.getString(7));
                            Article articlearray = new Article(source,newsdetails.getString(8),newsdetails.getString(2),newsdetails.getString(3),newsdetails.getString(5),newsdetails.getString(4),newsdetails.getString(9));
                            articleinfo.add(articlearray);
                        } while (newsdetails.moveToNext());
                    }

                }

                return newsdetails ;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        RecyclerView recycler;
        recycler=(RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        recycler.setLayoutManager(lm);
        recycler.setAdapter(new NewsAdapter(articleinfo,this));
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
