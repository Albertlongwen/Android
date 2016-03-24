package com.example.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.adapter.dingadapter.dingViewHolder;
import com.example.model.goods;
import com.example.tina.MenuActivity;
import com.example.tina.R;
import com.example.util.dingnumDBManager;

public class menuadapter extends SimpleAdapter {

	private MenuActivity context;
	private dingnumDBManager dingnumDBManager1;
	private List<Map<String, String>> list;

	public menuadapter(MenuActivity context, List<Map<String, String>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub

		dingnumDBManager1 = new dingnumDBManager(context);
		this.context = context;
		list = data;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		menuViewHolder viewHolder = null;
		View v = super.getView(position, convertView, parent);
		try {
			viewHolder = new menuViewHolder();
			// 菜品图片
			viewHolder.mImageView = (ImageView) v
					.findViewById(R.id.activity_menu_listview1_items_imageView1);
			// 菜品标题
			viewHolder.textview1 = (TextView) v
					.findViewById(R.id.activity_menu_listview1_items_textView1);
			// 数量
			viewHolder.textview2 = (TextView) v
					.findViewById(R.id.activity_menu_listview1_items_textView2);
			// 数量
			viewHolder.textview3 = (TextView) v
					.findViewById(R.id.activity_menu_listview1_items_textView3);
			// 增加
			viewHolder.Button1 = (Button) v
					.findViewById(R.id.activity_menu_listview1_items_button1);
			// 减少
			viewHolder.Button2 = (Button) v
					.findViewById(R.id.activity_menu_listview1_items_button2);

			try {
				int i = Integer.valueOf(list.get(position).get("_img_url")
						.toString());
				switch (i) {
				case 0:
					viewHolder.mImageView.setImageResource(R.drawable.img0);
					break;
				case 1:
					viewHolder.mImageView.setImageResource(R.drawable.img1);
					break;
				case 2:
					viewHolder.mImageView.setImageResource(R.drawable.img2);
					break;
				case 3:
					viewHolder.mImageView.setImageResource(R.drawable.img3);
					break;
				case 4:
					viewHolder.mImageView.setImageResource(R.drawable.img4);
					break;
				case 5:
					viewHolder.mImageView.setImageResource(R.drawable.img5);
					break;
				case 6:
					viewHolder.mImageView.setImageResource(R.drawable.img6);
					break;
				case 7:
					viewHolder.mImageView.setImageResource(R.drawable.img7);
					break;
				case 8:
					viewHolder.mImageView.setImageResource(R.drawable.img8);
					break;
				case 9:
					viewHolder.mImageView.setImageResource(R.drawable.img9);
					break;
				default:
					viewHolder.mImageView.setImageResource(R.drawable.img0);
					break;
				}
				// 菜品名称
				viewHolder.textview1.setText(list.get(position).get("_title")
						.toString());
				// 购买数量
				viewHolder.textview2.setText(dingnumDBManager1.getbuynumber(
						"postdep",
						"5588",
						Integer.parseInt(list.get(position).get("_id")
								.toString())));
				// 价格
				viewHolder.textview3.setText("价格:"
						+ list.get(position).get("_sell_price").toString()
						+ "元/份");
				// 增加事件
				viewHolder.Button1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int sbuynumber = Integer.parseInt(dingnumDBManager1
								.getbuynumber(
										"postdep",
										"5588",
										Integer.parseInt(list.get(position)
												.get("_id").toString())));
						int sid = Integer.parseInt(list.get(position)
								.get("_id").toString());
						dingnumDBManager1.addbuynumber(sbuynumber + 1,
								"postdep", "5588", sid);
						context.Handler2.sendEmptyMessage(0);
					}
				});

				// 减少事件
				viewHolder.Button2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int sbuynumber = Integer.parseInt(dingnumDBManager1
								.getbuynumber(
										"postdep",
										"5588",
										Integer.parseInt(list.get(position)
												.get("_id").toString())));
						final int sid = Integer.parseInt(list.get(position)
								.get("_id").toString());
						if ("1".equals(String.valueOf(sbuynumber))) {
							new AlertDialog.Builder(context)
									.setTitle("移除这道菜?")
									.setIcon(android.R.drawable.ic_dialog_info)

									.setPositiveButton(
											"取消",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// 无需操作
												}
											})
									.setNegativeButton(
											"确定",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dingnumDBManager1.delete(
															"postdep", "5588",
															sid);
													context.threadstart();
												}
											}).show();
						} else {
							dingnumDBManager1.addbuynumber(sbuynumber - 1,
									"postdep", "5588", sid);
							context.Handler2.sendEmptyMessage(0);
						}
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				Log.v("ccc", e.toString());

			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.v("ccc", e.toString());
		}
		return v;
	}

	public void bindlist(List<goods> goods) {
		for (goods dt_goods1 : goods) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("_id", String.valueOf(dt_goods1.get_id()));
			map.put("_channel_id", String.valueOf(dt_goods1.get_channel_id()));
			map.put("_category_id", String.valueOf(dt_goods1.get_category_id()));
			map.put("_title", dt_goods1.get_title());
			map.put("_goods_no", dt_goods1.get_goods_no());
			map.put("_stock_quantity",
					String.valueOf(dt_goods1.get_stock_quantity()));
			map.put("_market_price", dt_goods1.get_market_price());
			map.put("_sell_price", dt_goods1.get_sell_price());
			map.put("_point", String.valueOf(dt_goods1.get_point()));
			map.put("_link_url", dt_goods1.get_link_url());
			map.put("_img_url", dt_goods1.get_img_url());
			map.put("_seo_title", dt_goods1.get_seo_title());
			map.put("_seo_keywords", dt_goods1.get_seo_keywords());
			map.put("_seo_description", dt_goods1.get_seo_description());
			map.put("_content", dt_goods1.get_content());
			map.put("_sort_id", String.valueOf(dt_goods1.get_sort_id()));
			map.put("_click", String.valueOf(dt_goods1.get_click()));
			map.put("_is_msg", String.valueOf(dt_goods1.get_is_msg()));
			map.put("_is_top", String.valueOf(dt_goods1.get_is_top()));
			map.put("_is_red", String.valueOf(dt_goods1.get_is_red()));
			map.put("_is_hot", String.valueOf(dt_goods1.get_is_hot()));
			map.put("_is_slide", String.valueOf(dt_goods1.get_is_slide()));
			map.put("_is_lock", String.valueOf(dt_goods1.get_is_lock()));
			map.put("_user_id", String.valueOf(dt_goods1.get_user_id()));
			map.put("_add_time", String.valueOf(dt_goods1.get_add_time()));
			map.put("_digg_good", String.valueOf(dt_goods1.get_digg_good()));
			map.put("_digg_bad", String.valueOf(dt_goods1.get_digg_bad()));
			map.put("_buynumber", String.valueOf(dt_goods1.get_buynumber()));
			map.put("_user", dt_goods1.get_user());
			list.add(map);
		}

	}

	// 页面布局
	static class menuViewHolder {
		TextView textview1;
		TextView textview2;
		TextView textview3;
		ImageView mImageView;
		Button Button1;
		Button Button2;
	}
}
