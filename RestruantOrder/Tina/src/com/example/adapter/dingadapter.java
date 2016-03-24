package com.example.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.model.goods;
import com.example.tina.MainActivity;
import com.example.tina.R;
import com.example.util.dingDBManager;

public class dingadapter extends SimpleAdapter {

	private MainActivity context;
	private dingDBManager dingDBManager1;
	private List<Map<String, String>> list;
	private ImageLoader mImageLoader;
	private boolean mBusy = false; // 预加载图片的判断

	public void setFlagBusy(boolean busy) {
		this.mBusy = busy;
	}

	public dingadapter(MainActivity context, List<Map<String, String>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		dingDBManager1 = new dingDBManager(context);
		this.context = context;
		list = data;
		mImageLoader = new ImageLoader(); // 声明图片文件流
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		dingViewHolder viewHolder = null;
		View v = super.getView(position, convertView, parent);
		try {

			viewHolder = new dingViewHolder();
			// 菜品图片
			viewHolder.mImageView = (ImageView) v
					.findViewById(R.id.activity_main_listView1_items_imageView1);
			// 菜品标题
			viewHolder.textview1 = (TextView) v
					.findViewById(R.id.activity_main_listView1_items_textView1);
			// 价格
			viewHolder.textview2 = (TextView) v
					.findViewById(R.id.activity_main_listView1_items_textView2);
			// 分享
			viewHolder.Button1 = (Button) v
					.findViewById(R.id.activity_main_listView1_items_button1);
			// 收藏
			viewHolder.Button2 = (Button) v
					.findViewById(R.id.activity_main_listView1_items_button2);
			// 点菜
			viewHolder.Button3 = (Button) v
					.findViewById(R.id.activity_main_listView1_items_button3);
			try {
				if (!mBusy) {
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

					// mImageLoader.dingloadImage(, this, viewHolder); // 图片地址
					viewHolder.textview1.setText(list.get(position)
							.get("_title").toString());// 标题
					viewHolder.textview2.setText("价格:"
							+ list.get(position).get("_sell_price").toString()
							+ "元");// 价格
					try {
						if (dingDBManager1.queryid(Integer.parseInt(list
								.get(position).get("_id").toString()))) {
							viewHolder.Button3.setText("已点");
							viewHolder.Button3.setEnabled(false);
						} else {
							viewHolder.Button3.setText("点菜");
							viewHolder.Button3.setEnabled(true);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					// Bitmap bitmap = mImageLoader.getBitmapFromCache("");//
					// 图片地址
					// if (bitmap != null) {
					// viewHolder.mImageView.setImageBitmap(bitmap);
					// } else {
					// viewHolder.mImageView
					// .setImageResource(R.drawable.ic_launcher);
					// }
				}
				viewHolder.mImageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						context.viewactivity(Integer.parseInt(list
								.get(position).get("_id").toString()));
					}
				});
				viewHolder.Button3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addgoods(position); // 当单击点菜按钮后进行sqlite添加操作
						Message mes = handler.obtainMessage(1);
						handler.sendMessage(mes);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 点菜
	 * 
	 * @param position
	 */
	private void addgoods(int position) {
		int id = Integer.parseInt(list.get(position).get("_id").toString());
		dingDBManager1.add(id, "5588", "postdep");
	}

	/***
	 * html图片替换
	 */
	// ImageGetter imgGetter = new Html.ImageGetter() {
	// public Drawable getDrawable(String source) {
	// Drawable drawable = null;
	// Log.d("Image Path", source);
	// URL url;
	// try {
	// url = new URL(source);
	// drawable = Drawable.createFromStream(url.openStream(), "");
	// } catch (Exception e) {
	// return null;
	// }
	// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight());
	// return drawable;
	// }
	// };

	/**
	 * 当进行了点菜操作命令后，要重新刷新一下点菜页面的购物车和listview状态。
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				context.reloadadapter();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	// 页面布局
	static class dingViewHolder {
		TextView textview1;
		TextView textview2;
		ImageView mImageView;
		Button Button1;
		Button Button2;
		Button Button3;		
	}
}
