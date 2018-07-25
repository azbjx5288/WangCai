package com.wangcai.lottery.pattern;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wangcai.lottery.R;

/**
 * 修改于：2013-2-28 17:03:35
 * 	修正 ListView item 点击响应失败！
 *
 * @author ace
 *
 */
public class DraftPlansMenu implements OnItemClickListener {
	private static final String TAG = DraftPlansMenu.class.getSimpleName();

	public interface OnItemClickListener {
		public void onItemClick(int index);
	}

	private ArrayList<String> itemList;
	private ArrayList<String> promptList;
	private Context context;
	private PopupWindow popupWindow;
	private ListView listView;
	private OnItemClickListener listener;
	private LayoutInflater inflater;


	public DraftPlansMenu(Context context) {
		this.context = context;

		itemList = new ArrayList<String>(5);
		promptList= new ArrayList<String>(5);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.draftplansmenu, null);

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(new PopAdapter());
		listView.setOnItemClickListener(this);

		popupWindow = new PopupWindow(view,
				context.getResources().getDimensionPixelSize(R.dimen.draftplans_width),  //这里宽度需要自己指定，使用 WRAP_CONTENT 会很大
				LayoutParams.WRAP_CONTENT);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (listener != null) {
			listener.onItemClick(position);
		}
		dismiss();
	}

	// 设置菜单项点击监听器
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	// 批量添加菜单项
	public void addItems(String[] items,String[] prompt) {
		if (items != null && prompt != null) {
			if (items.length != prompt.length) {
				Log.e(TAG, "Item labels and value arrays must be the same size");
				return;
			}
		}
		for (String s : items)
			itemList.add(s);
		for (String s : prompt)
			promptList.add(s);
	}

	// 单个添加菜单项
	public void addItem(String item) {
		itemList.add(item);
	}

	// 下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
		// 保证尺寸是根据屏幕像素密度来的
		popupWindow.showAsDropDown(parent, 10, context.getResources().getDimensionPixelSize(R.dimen.draftplans_yoff));
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	// 适配器
	private final class PopAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.draftplansmenu_item, null);
				holder = new ViewHolder();
				convertView.setTag(holder);
				holder.groupItem = (TextView) convertView.findViewById(R.id.textView);
				holder.groupPrompt = (TextView) convertView.findViewById(R.id.textView_prompt);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.groupItem.setText(itemList.get(position));
			holder.groupPrompt.setText(promptList.get(position));
			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
			TextView groupPrompt;
		}
	}
}