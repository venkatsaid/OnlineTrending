package com.example.lenovo.onlinetrending;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

public class detailactivty extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
/*
    TextView title_tv;
    ImageView image_im;
*/
   static int i;
   public static int LOADERDETAIL_ID;
    ArrayList<Article> articlearraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivty);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        i=getIntent().getIntExtra("layoutposition",0);
        Log.i("position1",Integer.toString(i));
        articlearraylist=getIntent().getParcelableArrayListExtra("position");
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(i);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailactivty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           String Text=articlearraylist.get(i).getDescription()+"\n";
           Text+=articlearraylist.get(i).getDescription();
           NewsAppWidget nw=new NewsAppWidget();
           nw.text=Text;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        onlinetrending on;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Article article) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putParcelable("article", article);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final String title;
            String url;
            final View rootView = inflater.inflate(R.layout.fragment_detailactivty, container, false);
            AdView mAdView;
            mAdView=rootView.findViewById(R.id.banner);
            MobileAds.initialize(getContext(),"ca-app-pub-6336523906622902~6817819328");
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("C3528C0925D3AB8424433F9070BD2A5F")
                    .build();
            mAdView.loadAd(adRequest);
            on=new onlinetrending();

            TextView newstitle= (TextView) rootView.findViewById(R.id.detailtitle);
            TextView newsdescription=(TextView) rootView.findViewById(R.id.detaildescription);
            TextView newsurl=(TextView) rootView.findViewById(R.id.detailurl);
            ImageView imageView_im=(ImageView) rootView.findViewById(R.id.newsimage);
            final Article article1=getArguments().getParcelable("article");
             url=article1.getUrlToImage();
             title=article1.getTitle();
            if(!title.isEmpty()) {
                newstitle.setText(title);
            }
            if(article1.getDescription().isEmpty()||article1.getDescription().equalsIgnoreCase("null")){
                newsdescription.setText("Click on url for description");
            }else{newsdescription.setText(article1.getDescription());;}
            if(!article1.getUrl().isEmpty()){
            newsurl.setText(article1.getUrl());
            }
            if(!article1.getUrlToImage().isEmpty()){
            Picasso.with(getContext()).load(url).error(R.drawable.news).into(imageView_im);}
            else{
                Picasso.with(getContext()).load(R.drawable.news).into(imageView_im);
            }


        newsurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri newsuri=Uri.parse(article1.getUrl());
                Intent intent=new Intent(getContext(),webviewactivity.class);
                intent.putExtra("newsuri",article1.getUrl());
                Log.i("url",article1.getUrl());
                getContext().startActivity(intent);
            }
        });
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onlinetrending on=new onlinetrending();
                    Bundle b=new Bundle();
                    b.putInt("posi",i);
                    Cursor cursor=getActivity().getContentResolver().query(Uri.parse(NewsDatabase.CONTENT_URI+"/*"),null,NewsDatabase.Title+" LIKE ? AND "+NewsDatabase.Email+" LIKE ?",new String[]{title,onlinetrending.email_id},null);

                    if(cursor.getCount()>=1)
                    {
                        Snackbar.make(view, "Removed from Bookmark", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        Snackbar.make(view, "Added to Bookmark", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    getActivity().getLoaderManager().restartLoader(LOADERDETAIL_ID,b,PlaceholderFragment.this);

                }
            });
            return rootView;
        }


        @Override
        public Loader onCreateLoader(int i, Bundle bundle) {
            return new AsyncTaskLoader(getContext()) {
                @Override
                public Object loadInBackground() {

                    Article articlelist=getArguments().getParcelable("article");
                    Cursor cursor=getContext().getContentResolver().query(Uri.parse(NewsDatabase.CONTENT_URI+"/*"),null,NewsDatabase.Title+" LIKE ? AND "+NewsDatabase.Email+" LIKE ?",new String[]{articlelist.getTitle(),onlinetrending.email_id},null);

                    if(cursor.getCount()>=1)
                    {
                        getContext().getContentResolver().delete(Uri.parse(NewsDatabase.CONTENT_URI+"/*"),NewsDatabase.Title + " LIKE ?",new String[]{articlelist.getTitle()});
                    }
                    else
                    {
                        ContentValues Newsdetails=new ContentValues();
                        Newsdetails.put(NewsDatabase.Email,on.email_id);
                        Newsdetails.put(NewsDatabase.Title,articlelist.getTitle());
                        Log.i("getarguments",articlelist.getTitle()+"title");
                        Newsdetails.put(NewsDatabase.Description,articlelist.getDescription());
                        Newsdetails.put(NewsDatabase.UrlToImage,articlelist.getUrlToImage());
                        Newsdetails.put(NewsDatabase.Url,articlelist.getUrl());
                        Newsdetails.put(NewsDatabase.sourceId,articlelist.source.getId());
                        Newsdetails.put(NewsDatabase.sourceName,articlelist.source.getName());
                        Newsdetails.put(NewsDatabase.author,articlelist.getAuthor());
                        Newsdetails.put(NewsDatabase.publishedAt,articlelist.getPublishedAt());
                        getContext().getContentResolver().insert(Uri.parse(NewsDatabase.CONTENT_URI+""),Newsdetails);

                    }


                    return null;
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

        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(articlearraylist.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return articlearraylist.size();
        }
    }
}
