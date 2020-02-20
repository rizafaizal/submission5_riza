package com.faizal.aplikasidaftarfilmprojectakhir.widgets;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.faizal.aplikasidaftarfilmprojectakhir.R;
import com.faizal.aplikasidaftarfilmprojectakhir.recyclerview.model.Film;

import java.util.concurrent.ExecutionException;

import static com.faizal.aplikasidaftarfilmprojectakhir.db.DatabaseContract.MovieColoumns.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;

    private Cursor list;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }


    @Override
    public void onCreate() {
        list = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Film item = getItem(i);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(item.getPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FilmBannerWidgets.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    private Film getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new Film(list);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
