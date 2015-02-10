package com.Github.ShinChven.materialdemomenu.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.Github.ShinChven.materialdemomenu.app.R;
import com.Github.ShinChven.materialdemomenu.util.DisplayUtil;
import com.Github.ShinChven.materialdemomenu.util.LogUtil;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

public class ItemViewActivity extends ActionBarActivity {

    private ImageView mHeaderImage;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.teal_actionbar_bg)
                .headerLayout(R.layout.header_item_view_fading_header)
                .contentLayout(R.layout.activity_item_view_body).parallax(true);
        setContentView(helper.createView(this));
        helper.initActionBar(this);

        displayHeaderImage();


        getSupportActionBar().setTitle("清蒸鱼");

    }

    private void displayHeaderImage() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sample_large_0);
        mHeaderImage = ((ImageView) findViewById(R.id.header_image));
        mHeaderImage.setImageBitmap(bmp);
        DisplayUtil.DisplayMatrix displayMatrix = DisplayUtil.zoomWithWidth(getWindow().getWindowManager().getDefaultDisplay().getWidth(), bmp.getWidth(), bmp.getHeight());
        LogUtil.i("display_img", getWindow().getWindowManager().getDefaultDisplay().getWidth() + "");
        LogUtil.i("display_img", displayMatrix.width + " " + bmp.getWidth());
        LogUtil.i("display_img", displayMatrix.height + " " + bmp.getHeight());


        DisplayUtil.fit(mHeaderImage, displayMatrix);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_view, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        this.mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareHistoryFileName("item_view_share_history");
        mShareActionProvider.setShareIntent(getDefaultIntent());
        return true;
    }

    public Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_share) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
