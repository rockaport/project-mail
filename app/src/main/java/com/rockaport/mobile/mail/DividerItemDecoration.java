package com.rockaport.mobile.mail;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final float DEFAULT_STROKE_WIDTH = 1f;
    private float strokeWidth;

    public DividerItemDecoration() {
        strokeWidth = DEFAULT_STROKE_WIDTH;
    }

    public DividerItemDecoration(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int paddingLeft = parent.getPaddingLeft();
        int paddingRight = parent.getPaddingRight();

        Paint paint = new Paint();

        paint.setColor(ContextCompat.getColor(parent.getContext(), R.color.material_grey_500));
        paint.setStrokeWidth(strokeWidth);

        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            int width = parent.getChildAt(i).getWidth();
            int height = parent.getChildAt(i).getBottom();

            c.drawLine(paddingLeft, height, width - paddingRight, height, paint);
        }
    }
}
