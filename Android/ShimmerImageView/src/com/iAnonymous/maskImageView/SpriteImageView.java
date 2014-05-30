package com.iAnonymous.maskImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.iAnonymous.shimmerImageView.R;

public class SpriteImageView extends ImageView
{
    private int offsetX = 0;
    private BitmapFactory.Options options;
    private Bitmap source;
    // private IntBuffer sourceBuffer;
    private Bitmap bitmap;
    private Canvas compoundCanvas;
    private Bitmap mask;
    private Rect clipRect;
    private Paint paint;
    private boolean sleeping = false;

    public SpriteImageView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        source = BitmapFactory.decodeResource(getResources(), R.drawable.splash_logo_text_shimmer, options);
        // sourceBuffer = IntBuffer.allocate(source.getWidth()*source.getHeight());
        // source.copyPixelsToBuffer(sourceBuffer);
        bitmap = source.copy(Bitmap.Config.ARGB_8888, true);
        compoundCanvas = new Canvas(bitmap);
        mask = BitmapFactory.decodeResource(getResources(), R.drawable.splash_logo_text_mask);
        offsetX = (-1) * mask.getWidth() / 2;
        clipRect = new Rect(0, 0, mask.getWidth(), mask.getHeight());
        paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public SpriteImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public SpriteImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();

    }

    public void release()
    {
        // TODO
        this.removeCallbacks(runnable);
        sleeping = true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (!sleeping)
        {
            canvas.save();
            if (offsetX >= source.getWidth())
            {
                offsetX = (-1) * mask.getWidth() / 2;
                sleeping = true;
                postDelayed(runnable, 1500);
                return;
            }

            // 方式一：运行时出错，所以改为单点COPY方式，效率相对较低
            // bitmap.copyPixelsFromBuffer(sourceBuffer);

            // 方式二：单点COPY
            int row = source.getWidth();
            int col = source.getHeight();
            for (int x = 0; x < row; x++)
                for (int y = 0; y < col; y++)
                {
                    bitmap.setPixel(x, y, source.getPixel(x, y));
                }
            compoundCanvas.save();
            compoundCanvas.drawBitmap(bitmap, 0, 0, null);
            compoundCanvas.translate(offsetX, 0);
            compoundCanvas.drawBitmap(mask, 0, 0, paint);
            compoundCanvas.restore();

            // clipRegion.translate(offsetX, 0);
            // canvas.clipRegion(clipRegion, Op.REPLACE);
            int rcW = clipRect.width();
            clipRect.left = offsetX;
            clipRect.right = clipRect.left + rcW;
            canvas.clipRect(clipRect, Op.INTERSECT);
            canvas.drawBitmap(bitmap, 0, 0, null);
            offsetX += 10;
            canvas.restore();
            postInvalidateDelayed(25);
        }
    }

    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            invalidate();
            sleeping = false;
        }
    };

}