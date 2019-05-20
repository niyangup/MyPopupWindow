package com.example.mypopupwindow;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText mEtNum;
	private ImageButton mIbChoose;
	private List<String> numList;
	private PopupWindow popupWindow;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initUI();
		initData();
		initAdapter();
	}

	private void initAdapter() {
		
	}

	private void initData() {
		numList = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
		numList.add("1000"+i);
		}
		
	}

	private void initUI() {
		mEtNum = (EditText) findViewById(R.id.et_num);
		mIbChoose = (ImageButton) findViewById(R.id.ib_choose);
		
		
		mIbChoose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopupWindow();
			}
		});
	}
	protected void showPopupWindow() {
		initListView();
		
		popupWindow = new PopupWindow(listView, mEtNum.getWidth(), 300, true);
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		popupWindow.showAsDropDown(mEtNum, 0, -5);
	}

	public void initListView() {
		listView = new ListView(MainActivity.this);
		listView.setDividerHeight(0);
		listView.setBackgroundResource(R.drawable.listview_background);
		listView.setAdapter(new MyAdapter());
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mEtNum.setText(numList.get(position));
				popupWindow.dismiss();				
			}
		});
	}
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return numList.size();
		}

		@Override
		public Object getItem(int position) {
			return numList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if (convertView==null) {
				convertView=View.inflate(getApplicationContext(), R.layout.list_item, null);
				
				holder=new ViewHolder();
				holder.mTv = (TextView) convertView.findViewById(R.id.tv_content);
				holder.mIb = (ImageButton) convertView.findViewById(R.id.ib_delete);
				convertView.setTag(holder);
				
			}else {
				 holder = (ViewHolder) convertView.getTag();
			}
			holder.mTv.setText(numList.get(position));
			
			holder.mIb.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					numList.remove(position);
					notifyDataSetChanged();
					if (numList.size()==0) {
						popupWindow.dismiss();
					}
					
				}
			});
			
			return convertView;
		}
		
	}
	static class ViewHolder{
		public TextView mTv;
		public ImageButton mIb;
	}
}
