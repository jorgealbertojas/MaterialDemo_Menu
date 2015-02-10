package com.Github.ShinChven.materialdemomenu.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.Github.ShinChven.materialdemomenu.app.R;
import com.Github.ShinChven.materialdemomenu.menu.repo.ItemService;
import com.Github.ShinChven.materialdemomenu.menu.repo.MenuDBHelper;
import com.Github.ShinChven.materialdemomenu.menu.repo.entity.CategoryEntity;
import com.Github.ShinChven.materialdemomenu.menu.repo.entity.ItemEntity;
import com.Github.ShinChven.materialdemomenu.util.LogUtil;
import com.Github.ShinChven.materialdemomenu.util.ViewHolder;
import com.gc.materialdesign.views.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShinChven on 2014/12/16.
 */
public class MenuListFragment extends Fragment {


    private int mCategoryId;
    private MenuActivity mAttachedContext;
    private List<ItemEntity> mItems;
    private CategoryEntity mCategory;
    private ListView mMenuList;
    private SwipeRefreshLayout mSwipe;
    private ItemAdapter mAdapter;
    private Handler mHandler;

    public MenuListFragment() {
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mCategoryId = args.getInt(MenuDBHelper.CATEGORY_ID);
        LogUtil.i(MenuActivity.LOG_TAG, "setArguments mCategoryId=" + mCategoryId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, null);
        mMenuList = ((ListView) view.findViewById(R.id.menu_list));
        mSwipe = ((SwipeRefreshLayout) view.findViewById(R.id.swipe));
        mSwipe.setColorSchemeColors(R.color.material_deep_teal_500);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipe.setRefreshing(false);
                    }
                }, 3 * 1000);
            }
        });

        new DataTask().execute();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAttachedContext = ((MenuActivity) activity);

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler = new Handler();
    }

    private class DataTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            mCategory = mAttachedContext.getLiveCategory(mCategoryId);
            LogUtil.i(MenuActivity.LOG_TAG, "load item mCategoryId: " + mCategoryId);
            if (mCategory.getItems().size() == 0) {
                List<ItemEntity> found = ItemService.findItems(mAttachedContext, null, MenuDBHelper.CATEGORY_ID + "=?",
                        new String[]{String.valueOf(mCategoryId)}, null, null, null);
                mCategory.getItems().addAll(found);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mAdapter = new ItemAdapter();

            mMenuList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }


    public class ItemAdapter extends BaseAdapter {

        @Override
        public void notifyDataSetChanged() {
            for (int i = 0; i < flagCarriers.size(); i++) {
                flagCarriers.get(i).setTag(null);
            }
            super.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            try {
                return mCategory.getItems().size();
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
                return 0;
            }
        }

        @Override
        public ItemEntity getItem(int position) {
            try {
                return mCategory.getItems().get(position);
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mAttachedContext.getLayoutInflater().inflate(R.layout.item_menu, null);
            }
            final ItemEntity item = getItem(position);
            final CheckBox check = (CheckBox) ViewHolder.get(convertView, R.id.mtrl_checkBox);
            ViewHolder.get(convertView, R.id.item_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intt = new Intent(mAttachedContext, ItemViewActivity.class);
                    startActivity(intt);
                }
            });
            check.setChecked(mAttachedContext.hasOrdered(item.getITEM_ID()));
            check.setOncheckListener(new CheckBox.OnCheckListener() {
                @Override
                public void onCheck(final boolean isChecked) {
                    if (isChecked) {
                        mAttachedContext.addOrdered(item);
                        mAttachedContext.sortOrder();
                    } else {
                        mAttachedContext.removeOrdered(item);
                    }
                    mAttachedContext.updateOrderList();
                }
            });
            TextView textDesc = (TextView) ViewHolder.get(convertView, R.id.text_desc);
            TextView textItemName = (TextView) ViewHolder.get(convertView, R.id.text_item_name);
            TextView textPrice = (TextView) ViewHolder.get(convertView, R.id.text_price);
            textDesc.setText("介绍：" + item.getDESCRIPTION());
            textItemName.setText(item.getITEM_NAME());
            textPrice.setText("时价：" + String.valueOf(item.getPRICE()));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check.isChecked()) {
                        mAttachedContext.removeOrdered(item);
                    } else {
                        mAttachedContext.addOrdered(item);
                        mAttachedContext.sortOrder();
                    }
                    mAttachedContext.updateOrderList();
                    check.setChecked(!check.isChecked());
                }
            });

            View flagCarrier = ViewHolder.get(convertView, R.id.flag_carrier);

            if (!flagCarriers.contains(flagCarrier)) {
                flagCarriers.add(flagCarrier);
            }

            if (flagCarrier.getTag() != null) {
                Animation animation = AnimationUtils.loadAnimation(mAttachedContext,
                        (position > lastPosition) ? R.anim.item_view_up_from_bottom : R.anim.item_view_down_from_up);
                convertView.startAnimation(animation);
            } else {
                flagCarrier.setTag(0);
            }

            lastPosition = position;

            return convertView;
        }

        private List<View> flagCarriers = new ArrayList<View>();

        private int lastPosition = -1;

    }
}
