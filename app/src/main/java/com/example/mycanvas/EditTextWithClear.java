package com.example.mycanvas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText
{
    Drawable mClearButtonImage;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }
    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public EditTextWithClear(Context context,
                             AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_clear_opaque_24dp, null);
        // If the clear (X) button is tapped, clear the text.
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart; // Used for LTR languages
                    float clearButtonEnd;  // Used for RTL languages
                    boolean isClearButtonClicked = false;
                    // Detect the touch in RTL or LTR layout direction.
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // If RTL, get the end of the button on the left side.
                        clearButtonEnd = mClearButtonImage
                                .getIntrinsicWidth() + getPaddingStart();
                        // If the touch occurred before the end of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }
                    } else {
                        // Layout is LTR.
                        // Get the start of the button on the right side.
                        clearButtonStart = (getWidth() - getPaddingEnd()
                                - mClearButtonImage.getIntrinsicWidth());
                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }
                    // TODO: Check for actions if the button is tapped.
                }
                return false;
            }
        });
        // If the text changes, show or hide the clear (X) button.
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearButton();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

     // Shows the clear (X) button
    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                      // Start of text.
                        null,               // Above text.
                        mClearButtonImage,  // End of text.
                        null);              // Below text.
    }

     // Hides the clear button
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Start of text.
                        null,      // Above text.
                        null,      // End of text.
                        null);     // Below text.
    }
}
