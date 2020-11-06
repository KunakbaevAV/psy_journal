package ru.geekbrains.psy_journal.presentation.view.utilities;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

class UnderlayButton {
    private final String text;
    private final int buttonColor;
    private final int textColor;
    private int pos;
    private RectF clickRegion;
    private ItemTouchHelperCallback.UnderlayButtonClickListener clickListener;

    UnderlayButton(String text, int buttonColor, int textColor, ItemTouchHelperCallback.UnderlayButtonClickListener clickListener) {
        this.text = text;
        this.buttonColor = buttonColor;
        this.textColor = textColor;
        this.clickListener = clickListener;
    }

    boolean onClick(float x, float y) {
        if (clickRegion != null && clickRegion.contains(x, y)) {
            clickListener.onClick(pos);
            return true;
        }
        return false;
    }

    void onDraw(Canvas c, RectF rect, int pos) {
        Paint p = new Paint();
        p.setColor(buttonColor);
        c.drawRect(rect, p);
        p.setColor(textColor);
        p.setTextSize(36);

        Rect r = new Rect();
        float cHeight = rect.height();
        float cWidth = rect.width();
        p.setTextAlign(Paint.Align.LEFT);
        p.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        c.drawText(text, rect.left + x, rect.top + y, p);

        clickRegion = rect;
        this.pos = pos;
    }
}

