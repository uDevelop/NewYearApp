package org.uDevelop.newyearapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity {
	private StorageAdapter mStorageAdapter; 
	private FragmentPagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
		super.onCreate(null);
        setContentView(R.layout.activity_main);
        
        //mStorageAdapter = new DatabaseAdapter(this);
        mStorageAdapter = new JSonStorageAdapter(this);
        Page[] pages = getPages();
        mAdapter = new PagesAdapter(this.getSupportFragmentManager(), pages);
        
        ViewPager pager = (ViewPager)findViewById(R.id.pages);
        pager.setAdapter(mAdapter);  

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.page_indicator);
        indicator.setViewPager(pager);  
        indicator.setOnTouchListener(new OnTouch()); //убираем скролл при касании
    }
	
	private class OnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return true;
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mStorageAdapter.close();
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		mStorageAdapter.syncronize();
	}
		
	public void onClick(View view) {
		if (view.getId() == R.id.list_view) {
			
		}
		
	}
	
	private Page[] getPages() {
    	int tabCount = mStorageAdapter.getCategoryCount();
        Page[] pages = new Page[tabCount];                
        for(int i = 0; i < tabCount; i++) {
        	String icon = mStorageAdapter.getCategoryIcon(i);
        	Class<R.drawable> res = R.drawable.class;
            int imageId = 0;
            try {
            	imageId= res.getField(icon).getInt(null);
            }
            catch (Exception ex) {
            	Log.w("MainActivity[getResIdByName]", ex.getMessage());
            }
            pages[i] = new Page();
        	pages[i].iconId = imageId;
        	pages[i].page = new PageFragment(this, mStorageAdapter, i);
        } 
        return pages;
    }
}


