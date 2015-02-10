package com.Github.ShinChven.materialdemomenu.menu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.Github.ShinChven.materialdemomenu.app.R;
import com.Github.ShinChven.materialdemomenu.menu.repo.CategoryService;
import com.Github.ShinChven.materialdemomenu.menu.repo.ItemService;
import com.Github.ShinChven.materialdemomenu.menu.repo.MenuDBHelper;
import com.Github.ShinChven.materialdemomenu.menu.repo.entity.CategoryEntity;
import com.Github.ShinChven.materialdemomenu.menu.repo.entity.ItemEntity;
import com.Github.ShinChven.materialdemomenu.util.LogUtil;
import com.Github.ShinChven.materialdemomenu.util.ViewHolder;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.gc.materialdesign.widgets.SnackBar;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import java.util.*;

public class MenuActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener, DrawerLayout.DrawerListener {

    public static final String YUAN = "元";
    public static final String LOG_TAG = "MenuActivity";
    private StickyListHeadersListView mList;
    private List<ItemEntity> items = new ArrayList<ItemEntity>();
    private Map<Integer, CategoryEntity> categoryMap = new LinkedHashMap<Integer, CategoryEntity>();
    private ProgressBarCircularIndeterminate mProgress;
    private SwipeRefreshLayout mSwipeLayout;
    private MenuItem mOrderedAction;
    private LinearLayout mSubmit;
    private TextView mTextTotalPrice;
    private DrawerLayout mDrawer;
    private View mOrderListContainer;
    private StickyListHeadersListView mOrderListView;
    private OrderStickyAdapter mOrderedAdapter;
    private LinearLayout mDrawerMenuContainer;
    private DrawerArrowDrawable mDrawerArror;
    private ActionBarDrawerToggle mABDrawerToggle;
    private ViewPager mPager;
    private ListView mMajors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mTextTotalPrice = ((TextView) findViewById(R.id.text_total_price));
        mMajors = ((ListView) findViewById(R.id.drawer_menu_list));
        mPager = ((ViewPager) findViewById(R.id.pager));
        setupDrawer();
        setupOrderedList();
        setupDrawerMenu();

        initData();

    }

    private void initData() {
        new MenuItemLoadingTask().execute();
    }

    private void setupOrderedList() {
        mOrderListView = ((StickyListHeadersListView) findViewById(R.id.ordered_list));
        mOrderedAdapter = new OrderStickyAdapter();
        mOrderListView.setAdapter(mOrderedAdapter);
    }

    public CategoryEntity getLiveCategory(int categoryId) {
        try {
            return categoryMap.get(categoryId);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    private final List<MenuListFragment> mFragments = new ArrayList<MenuListFragment>();

    private Map<Integer, CategoryEntity> orderedCategories = new LinkedHashMap<Integer, CategoryEntity>();

    public boolean hasOrdered(int item_id) {
        for (int i = 0; i < ordered.size(); i++) {
            ItemEntity item = ordered.get(i);
            if (item.getITEM_ID() == item_id) {
                return true;
            }
        }
        return false;
    }

    public void removeOrdered(ItemEntity item) {
        for (int i = 0; i < ordered.size(); i++) {
            if (ordered.get(i).getITEM_ID() == item.getITEM_ID()) {
                ordered.remove(i);
                return;
            }
        }
    }

    public void addOrdered(ItemEntity item) {
        for (int i = 0; i < ordered.size(); i++) {
            if (ordered.get(i).getITEM_ID() == item.getITEM_ID()) {
                return;
            }
        }
        CategoryEntity category = categoryMap.get(item.getCATEGORY_ID());
        if (!orderedCategories.containsKey(category.getCATEGORY_ID())) {
            orderedCategories.put(category.getCATEGORY_ID(), category);
        }
        ordered.add(item);
    }

    public class MenuPagerAdapter extends FragmentPagerAdapter {

        private final List<CategoryEntity> mCategories;


        public MenuPagerAdapter(FragmentManager fm, List<CategoryEntity> categoryies) {
            super(fm);
            try {
                fm.getFragments().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mCategories = categoryies;
            mFragments.clear();
            for (int i = 0; i < this.mCategories.size(); i++) {
                MenuListFragment fragment = new MenuListFragment();
                Bundle args = new Bundle();
                args.putInt(MenuDBHelper.CATEGORY_ID, this.mCategories.get(i).getCATEGORY_ID());
                fragment.setArguments(args);
                mFragments.add(fragment);
            }
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.mCategories.get(position).getCATE_NAME();
        }
    }

    private void setupDrawerMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerMenuContainer = ((LinearLayout) findViewById(R.id.drawer_menu));
        mDrawerArror = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mABDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mDrawerArror, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawer.setDrawerListener(mABDrawerToggle);
        mABDrawerToggle.syncState();
    }

    private void setupDrawer() {
        mDrawer = ((DrawerLayout) findViewById(R.id.drawer));
        mDrawer.setDrawerListener(this);
        mOrderListContainer = findViewById(R.id.order_list_container);
    }

    int itemIndex = 115;

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
    }

    Handler mHandler = new Handler();

    private float totalPrice;

    public void updateOrderList() {
        mOrderedAction.setTitle("已点（" + ordered.size() + "）");
        totalPrice = 0;
        for (int i = 0; i < ordered.size(); i++) {
            ItemEntity item = ordered.get(i);
            float priceCount = item.getPRICE() * item.getOrderedCount();
            totalPrice += priceCount;
        }
        mTextTotalPrice.setText(String.valueOf(totalPrice) + YUAN);
        mOrderedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDrawerSlide(View view, float v) {

    }

    @Override
    public void onDrawerOpened(View view) {

    }

    @Override
    public void onDrawerClosed(View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    int categoryIdIndex = 344;

    private List<CategoryEntity> majorCategories = new ArrayList<CategoryEntity>();

    public class MenuItemLoadingTask extends AsyncTask {


        private MajorCategoryAdapter mMajorCategoryAdapter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            loadMajorCategories();
            if (majorCategories.size() == 0) {
                generateData();
                loadMajorCategories();

            }

            mMajorCategoryAdapter = new MajorCategoryAdapter();

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mMajors.setAdapter(mMajorCategoryAdapter);
                    mMajors.setOnItemClickListener(new MajorItemClickListener());
                    new LoadPageTask(0).execute();
                }
            });

        }
    }

    public class MajorItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.i(LOG_TAG, "onItemClick_position" + position);
            new LoadPageTask(position).execute();
        }
    }

    public class LoadPageTask extends AsyncTask {

        private final int position;

        public LoadPageTask(int position) {
            this.position = position;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            int majorId = majorCategories.get(position).getCATEGORY_ID();
            LogUtil.i(LOG_TAG, "majorId: " + majorId);
            categoryMap = CategoryService.findCategoryMap(MenuActivity.this, null,
                    MenuDBHelper.PARENT_ID + "=?", new String[]{
                            String.valueOf(majorId)
                    }
                    , null, null, null
            );

            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    ArrayList<CategoryEntity> categoryies = new ArrayList<CategoryEntity>(categoryMap.values());
                    MenuPagerAdapter adapter = new MenuPagerAdapter(getSupportFragmentManager(),
                            categoryies);
                    mPager.setAdapter(adapter);


                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDrawer.closeDrawer(mDrawerMenuContainer);
                }
            }, 300);
        }
    }

    private void generateData() {
        List<CategoryEntity> initCategoryMajors = new ArrayList<CategoryEntity>();
        CategoryEntity category = initCategory("主分类一", 1, 0);
        initCategoryMajors.add(category);
        category = initCategory("主分类二", 2, 0);
        initCategoryMajors.add(category);
        category = initCategory("主分类三", 3, 0);
        initCategoryMajors.add(category);
        category = initCategory("主分类四", 4, 0);
        initCategoryMajors.add(category);
        category = initCategory("主分类五", 5, 0);
        initCategoryMajors.add(category);

        List<CategoryEntity> liveCategories = new ArrayList<CategoryEntity>();


        for (int i = 0; i < initCategoryMajors.size(); i++) {
            CategoryEntity major = initCategoryMajors.get(i);
            for (int j = 0; j < 8; j++) {
                CategoryEntity categoryLive = initCategory("分类" + major.getCATEGORY_ID() + "_" + j,
                        categoryIdIndex++,
                        major.getCATEGORY_ID()
                );
                liveCategories.add(categoryLive);
            }
        }

        for (int i = 0; i < liveCategories.size(); i++) {
            CategoryEntity categoryLive = liveCategories.get(i);
            initItems(categoryLive);
        }
    }

    private void loadMajorCategories() {
        List<CategoryEntity> categories = CategoryService.findCategories(this, null,
                MenuDBHelper.PARENT_ID + "=?", new String[]{String.valueOf(0)},
                null, null, null
        );
        majorCategories.clear();
        majorCategories.addAll(categories);
    }

    public class MajorCategoryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return majorCategories.size();
        }

        @Override
        public CategoryEntity getItem(int position) {
            return majorCategories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
            ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position).getCATE_NAME());
            return view;
        }
    }

    private CategoryEntity initCategory(String categoryName, int categoryId, int parentId) {
        CategoryEntity category = new CategoryEntity();
        category.setCATEGORY_ID(categoryId);
        category.setCATE_NAME(categoryName);
        category.setIMG("/res");
        category.setPARENT_ID(parentId);
        category.setUPDATE_TIME(new Date());
        CategoryService.insertOrUpdateCategory(this, category);
        categoryMap.put(category.getCATEGORY_ID(), category);
        return category;
    }

    private void initItems(CategoryEntity category) {
        for (int i = 0; i < 15; i++) {
            ItemEntity item = new ItemEntity();
            item.setPRICE(100.00f);
            item.setUPDATE_TIME(new Date());
            item.setIMG("/res");
            item.setCATEGORY_ID(category.getCATEGORY_ID());
            item.setITEM_NAME(category.getCATE_NAME() + "_" + i);
            item.setITEM_ID(itemIndex++);
            item.setDESCRIPTION("这是" + item.getITEM_NAME() + "，天然取材，精心烹调。");
            item.setITEM_STATUS(0);
            ItemService.insertOrUpdateItem(this, item);
            items.add(item);
        }
    }


    private List<ItemEntity> ordered = new ArrayList<ItemEntity>(0);


    public class OrderStickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private ItemEntity mLastDeleted;

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.header_order, null);
            }
            TextView headerText = (TextView) ViewHolder.get(convertView, R.id.text_header);
            ItemEntity item = ordered.get(position);
            CategoryEntity category = orderedCategories.get(item.getCATEGORY_ID());
            try {
                headerText.setText(category.getCATE_NAME());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }


        @Override
        public long getHeaderId(int position) {
            ItemEntity item = ordered.get(position);
            CategoryEntity category = orderedCategories.get(item.getCATEGORY_ID());
            return category.getCATEGORY_ID();
        }

        @Override
        public int getCount() {
            return ordered.size();
        }

        @Override
        public ItemEntity getItem(int position) {
            ItemEntity item = null;
            try {
                item = ordered.get(position);
            } catch (Exception e) {
                LogUtil.printStackTrace(e);
            }
            return item;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_order, null);
            }
            final ItemEntity item = getItem(position);
            TextView textName = ViewHolder.get(convertView, R.id.text_item_name);
            textName.setText(item.getITEM_NAME());
            TextView textPrice = ViewHolder.get(convertView, R.id.text_price);
            textPrice.setText(String.valueOf(item.getPRICE()) + YUAN);
            TextView textCount = ViewHolder.get(convertView, R.id.text_item_count);
            textCount.setText(String.valueOf(item.getOrderedCount()));
            ViewHolder.get(convertView, R.id.item_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intt = new Intent(MenuActivity.this, ItemViewActivity.class);
                    startActivity(intt);
                }
            });

            final ButtonFloatSmall btnMinus = ViewHolder.get(convertView, R.id.btn_minus);
            if (item.getOrderedCount() <= 1) {
                btnMinus.setIconDrawable(getResources().getDrawable(R.drawable.ic_close_white_18dp));
            } else {
                btnMinus.setIconDrawable(getResources().getDrawable(R.drawable.ic_chevron_left_white_18dp));
            }
            final View finalConvertView = convertView;
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int orderedCount = item.getOrderedCount();
                    item.setOrderedCount(orderedCount - 1);
                    if (orderedCount > 1) {
                        mOrderedAdapter.notifyDataSetChanged();
                    } else {
                        Animation animation = AnimationUtils.
                                loadAnimation(MenuActivity.this,
                                        R.anim.item_move_left_out
                                );
                        finalConvertView.startAnimation(animation);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mLastDeleted = item;
                                ordered.remove(item);
                                item.setOrderedCount(1);
                                // todo notify item list update
                                updateOrderList();
                                SnackBar snackBar = new SnackBar(
                                        MenuActivity.this,
                                        "已经删除【" + item.getITEM_NAME() + "】",
                                        "取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ordered.add(mLastDeleted);
                                        updateOrderList();
                                        // todo notify item list update
                                    }
                                }
                                );
                                snackBar.setDismissTimer(2000);
                                snackBar.setColorButton(R.color.material_deep_teal_500);
                                snackBar.show();
                            }
                        }, 401);
                    }
                }
            });
            ButtonFloatSmall btnPlus = ViewHolder.get(convertView, R.id.btn_plus);
            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = item.getOrderedCount() + 1;
                    item.setOrderedCount(count);
                    sortOrder();
                    updateOrderList();
                }
            });
            return convertView;
        }

        private List<View> flagCarriers = new ArrayList<View>();

        private int lastPosition = -1;

    }

    public void sortOrder() {
        Collections.sort(ordered, new Comparator<ItemEntity>() {
            @Override
            public int compare(ItemEntity lhs, ItemEntity rhs) {
                if (lhs.getCATEGORY_ID() > rhs.getCATEGORY_ID()) {
                    return 1;
                } else if (lhs.getCATEGORY_ID() < rhs.getCATEGORY_ID()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        mOrderedAction = menu.findItem(R.id.action_ordered);
        return true;
    }


    boolean loadFlag = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ordered) {
            if (mDrawer.isDrawerOpen(mOrderListContainer)) {
                mDrawer.closeDrawer(mOrderListContainer);
            } else {
                mDrawer.openDrawer(mOrderListContainer);
            }
            return true;
        } else if (id == android.R.id.home) {
            if (mDrawer.isDrawerOpen(mDrawerMenuContainer)) {
                mDrawer.closeDrawer(mDrawerMenuContainer);
            } else if (mDrawer.isDrawerOpen(mOrderListContainer)) {
                mDrawer.closeDrawer(mOrderListContainer);
            } else {
                mDrawer.openDrawer(mDrawerMenuContainer);
            }
        }
        loadFlag = true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(mOrderListContainer)) {
            mDrawer.closeDrawer(mOrderListContainer);
        } else {
            super.onBackPressed();
        }
    }
}
