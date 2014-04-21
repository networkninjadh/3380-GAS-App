package com.project.smartpump;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultListView extends ListActivity 
{	
	private LayoutInflater inflater;
	private Vector<RowData> data;
	public void onCreate(Bundle savedInstanceState)
	{	super.onCreate(savedInstanceState);
		setContentView(R.layout.result_list_view);
		inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		RowData rd = new RowData("Test data 1","Description");
		data.add(rd);
		rd = new RowData("Test Data 2", "Description 2");
		//CustomAdapter adapter = new CustomAdapter(this,R.layout.custom_row,R.id.item,data);
		//setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}
	public void onListItemClick(ListView parent, View view, int position, long id)
	{	CustomAdapter adapter = (CustomAdapter)  parent.getAdapter();
		RowData row = adapter.getItem(position);
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(row.item);
		builder.setMessage(row.description + " -> " + position);
		builder.setPositiveButton("ok", null);
		builder.show();
	}
	
	private class CustomAdapter extends ArrayAdapter<RowData>
	{	public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects)
		{	super(context, resource, textViewResourceId, objects);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{	ViewHolder holder = null;
			TextView item = null;
			TextView description = null;
			RowData rowData= getItem(position);
			if(null == convertView)
			{	//convertView = inflater.inflate(R.layout.custom_row, null);
				//holder = new ViewHolder(convertView);
				//convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			item = holder.getItem();
			item.setText(rowData.item);
			description = holder.getDescription();		
			description.setText(rowData.description);
			return convertView;
		}
	}
	private class RowData
	{	
		protected String item;
		protected String description;
		RowData(String item, String description)
		{	this.item = item;
			this.description = description;
		}
		@Override
		public String toString()
		{	return item + " " + description;
		}
	}
	private class ViewHolder
	{	private View row;
		private TextView description = null;
		private TextView item = null;
		public ViewHolder(View row)
		{	this.row = row;
		}
		public TextView getDescription()
		{	//if(null == description)
			//	description = (TextView) row.findViewById(R.id.description);
			return description;
		}
		public TextView getItem()
		{	//if(null == item)
			//	item = (TextView)row.findViewById(R.id.item);
			return item;
		}
	}
}