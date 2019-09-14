package ru.geekbrains.psy_journal.presentation.view.utilities;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import ru.geekbrains.psy_journal.Constants;

class PlugInButton  {

	private static final int INDENT_ICON = 22;
	private final Paint paint = new Paint();
	private final String text;
	private RectF clickRegion;
	private int buttonColor;
	private int textColor;
	private int textSize;
	private Drawable icon;
	private Rect boundsIcon;
	private boolean isRightSide;

	boolean isRightSide() {
		return isRightSide;
	}

	void setRightSide(boolean rightSide) {
		isRightSide = rightSide;
	}

	@NonNull
	RectF getClickRegion() {
		return clickRegion;
	}

	private PlugInButton(String text) {
		this.text = text;
		clickRegion = new RectF();
	}

	private void setButtonColor(int buttonColor) {
		this.buttonColor = buttonColor;
	}

	private void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	private void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	private void setIcon(Drawable icon) {
		this.icon = icon;
		if (icon != null){
			boundsIcon = new Rect();
		}
	}

	boolean onClick(float x, float y) {
		return !clickRegion.isEmpty() && clickRegion.contains(x, y);
	}

	void onDraw(Canvas canvas, RectF rect) {
		if (!rect.isEmpty()){
			paint.setColor(buttonColor);
			canvas.drawRect(rect, paint);
			paint.setColor(textColor);
			paint.setTextSize(textSize);
			if (icon != null){
				int wightIcon = INDENT_ICON * 2 + icon.getIntrinsicWidth();
				if (rect.width() > wightIcon){
					setBoundsIcon(rect);
					icon.setBounds(boundsIcon);
					icon.draw(canvas);
					if (rect.width() - boundsIcon.width() > paint.measureText(text)){
						drawText(canvas, rect);
					}
				}
			} else {
				drawText(canvas, rect);
			}
			paint.reset();
		}
		clickRegion.set(rect);
	}

	private void setBoundsIcon(RectF rect){
		int leftIcon;
		int rightIcon;
		int topIcon = (int) (rect.top + INDENT_ICON);
		int bottomIcon = (int) (rect.bottom - INDENT_ICON);
		if (isRightSide){
			leftIcon = (int) (rect.left + INDENT_ICON);
			rightIcon = leftIcon + icon.getIntrinsicWidth() + INDENT_ICON;
		} else {
			rightIcon = (int) (rect.right - INDENT_ICON);
			leftIcon = rightIcon - icon.getIntrinsicWidth() - INDENT_ICON;
		}
		boundsIcon.set(leftIcon, topIcon, rightIcon,  bottomIcon);
	}

	private void drawText(Canvas canvas, RectF rect){
		Rect r = new Rect();
		float cHeight = rect.height();
		float cWidth =  rect.width();
		paint.setTextAlign(Paint.Align.CENTER);
		paint.getTextBounds(text, 0, text.length(), r);
		float y = cHeight * Constants.HALF + r.height() * Constants.HALF - r.bottom;
		float x = cWidth * Constants.HALF - r.width() * Constants.HALF - r.left;
		if (isRightSide){
			float startX = (boundsIcon != null) ? boundsIcon.right + INDENT_ICON + x : rect.left + x;
			canvas.drawText(text, startX, rect.top + y, paint);
		} else {
			canvas.drawText(text, rect.left + x, rect.top + y, paint);
		}
	}

	static class Builder{

		private PlugInButton button;

		Builder(String text) {
			button = new PlugInButton(text);
		}

		Builder setButtonColor(int buttonColor) {
			button.setButtonColor(buttonColor);
			return this;
		}

		Builder setTextColor(int textColor) {
			button.setTextColor(textColor);
			return this;
		}

		Builder setTextSize(int textSize) {
			button.setTextSize(textSize);
			return this;
		}

		Builder setIcon(Drawable icon) {
			button.setIcon(icon);
			return this;
		}

		PlugInButton build(){
			return button;
		}
	}
}
