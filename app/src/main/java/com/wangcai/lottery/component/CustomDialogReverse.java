package com.wangcai.lottery.component;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;

/**
 * Created on 2016/01/20.
 * @author ACE
 * @功能描述: 定义弹出窗布局
 */
@SuppressLint("NewApi")
public class CustomDialogReverse extends Dialog {

    public CustomDialogReverse(Context context) {
        super(context);
    }

    public CustomDialogReverse(Context context, int theme) {
        super(context, theme);
    }

    @SuppressLint("NewApi")
    public static class Builder {
        private Context context;
        private String title;
        private boolean titleHideOrShow = true;
        private boolean bottomHideOrShow = true;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private View displayLayout;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private DialogLayout layoutSet = DialogLayout.LEFT_AND_RIGHT;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public boolean isTitleHideOrShow() {
            return titleHideOrShow;
        }

        public void setTitleHideOrShow(boolean titleHideOrShow) {
            this.titleHideOrShow = titleHideOrShow;
        }

        public boolean isBottomHideOrShow() {
            return bottomHideOrShow;
        }

        public void setBottomHideOrShow(boolean bottomHideOrShow) {
            this.bottomHideOrShow = bottomHideOrShow;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setDisplayLayout(View displayLayout) {
            this.displayLayout = displayLayout;
            return this;
        }

        /**
         * Set the layout styles
         *
         * @param layoutSet
         */
        public Builder setLayoutSet(DialogLayout layoutSet) {
            this.layoutSet = layoutSet;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        @SuppressLint("NewApi")
        public CustomDialogReverse create() {
            CustomDialogReverse dialog = new CustomDialogReverse(context, R.style.Dialog);
            View layout;
            if (displayLayout == null) {
                layout = LayoutInflater.from(context).inflate(R.layout.alert_dialog_reverse_normal_layout, null);
            } else {
                layout = displayLayout;
            }
            dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            if (isTitleHideOrShow()) {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            } else {
                layout.findViewById(R.id.title).setVisibility(View.GONE);
            }
            chooselayout(layout, dialog);
            return dialog;
        }

        private View chooselayout(View layout, CustomDialogReverse dialog) {
            View newLayout = null;
            switch (layoutSet) {
                case UP_AND_DOWN:
                    newLayout = setUpAndDown(layout, dialog);
                    break;
                case LEFT_AND_RIGHT_LEVEL:
                    newLayout = setLeftAndRightLevel(layout, dialog);
                    break;
                case LEFT_AND_RIGHT:
                    newLayout = setLeftAndRight(layout, dialog);
                    break;
                case SINGLE:
                    newLayout = setSingle(layout, dialog);
                    break;
            }
            return newLayout;
        }

        /**
         * set button the up and down Layout
         *
         * @param layout
         * @return
         */
        private View setUpAndDown(View layout, final CustomDialogReverse dialog) {
            //设置布局上下显示
            LinearLayout belowLayout = (LinearLayout) layout.findViewById(R.id.alert_below_layout);
            belowLayout.setBackgroundResource(R.drawable.notidialog_below_bg);
            belowLayout.setOrientation(LinearLayout.VERTICAL);
            belowLayout.setVisibility(isBottomHideOrShow() ? View.VISIBLE : View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            belowLayout.setLayoutParams(params);

            if (positiveButtonText != null) {

                Button positiveButton = (Button) layout.findViewById(R.id.positiveButton);
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener((View v) -> {
                        positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                Button negativeButton = (Button) layout.findViewById(R.id.negativeButton);
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener((View v) -> {
                        negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    });
                }
            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
            }

            View dialogView = layout.findViewById(R.id.alert_dialog_view);
            LinearLayout.LayoutParams dialogViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 20);
            dialogView.setLayoutParams(dialogViewParams);
            dialogView.setBackgroundColor(Color.TRANSPARENT);
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            }

            return layout;
        }

        /**
         * set button the left and right
         * @param layout
         * @return
         */
        private View setLeftAndRight(View layout, final CustomDialogReverse dialog) {
            //设置布局左右显示
            LinearLayout belowLayout = (LinearLayout) layout.findViewById(R.id.alert_below_layout);
            belowLayout.setBackgroundResource(R.drawable.notidialog_below_bg);
            belowLayout.setOrientation(LinearLayout.HORIZONTAL);
            belowLayout.setVisibility(isBottomHideOrShow() ? View.VISIBLE : View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            belowLayout.setLayoutParams(params);

            if (positiveButtonText != null) {
                Button positiveButton = (Button) layout.findViewById(R.id.positiveButton);
                positiveButton.setText(positiveButtonText);
                positiveButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener((View v) -> {
                        positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                Button negativeButton = (Button) layout.findViewById(R.id.negativeButton);
                negativeButton.setText(negativeButtonText);
                negativeButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2));
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener((View v) -> {
                        negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    });
                }
            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
            }

            View dialogView = layout.findViewById(R.id.alert_dialog_view);
            dialogView.setLayoutParams(new LinearLayout.LayoutParams(10, LinearLayout.LayoutParams.MATCH_PARENT));
            dialogView.setBackgroundColor(Color.TRANSPARENT);
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            }
            return layout;
        }

        /**
         * set button the left and right Level
         * @param layout
         * @return
         */
        private View setLeftAndRightLevel(View layout, final CustomDialogReverse dialog) {
            //设置布局左右显示
            LinearLayout belowLayout = (LinearLayout) layout.findViewById(R.id.alert_below_layout);
            belowLayout.setPadding(0, 0, 0, 0);
            belowLayout.setBackgroundResource(R.drawable.notidialog_below_level_bg);
            belowLayout.setOrientation(LinearLayout.HORIZONTAL);
            belowLayout.setVisibility(isBottomHideOrShow() ? View.VISIBLE : View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            belowLayout.setLayoutParams(params);

            if (positiveButtonText != null) {
                Button positiveButton = (Button) layout.findViewById(R.id.positiveButton);
                positiveButton.setText(positiveButtonText);
                positiveButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
                positiveButton.setBackgroundResource(R.drawable.notidialog_leftbtn_half_selector);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener((View v) -> {
                        positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }
            if (negativeButtonText != null) {
                Button negativeButton = (Button) layout.findViewById(R.id.negativeButton);
                negativeButton.setText(negativeButtonText);
                negativeButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                negativeButton.setBackgroundResource(R.drawable.notidialog_rightbtn_half_selector);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener((View v) -> {
                        negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    });
                }
            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
            }

            View dialogView = layout.findViewById(R.id.alert_dialog_view);
            dialogView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT));
            dialogView.setBackgroundColor(Color.TRANSPARENT);
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            }
            return layout;
        }

        /**
         * set button the single
         *
         * @param layout
         * @return
         */
        private View setSingle(View layout, final CustomDialogReverse dialog) {
            LinearLayout belowLayout = (LinearLayout) layout.findViewById(R.id.alert_below_layout);
            belowLayout.setPadding(0, 0, 0, 0);
            belowLayout.setBackgroundResource(R.drawable.notidialog_below_level_bg);
            belowLayout.setVisibility(isBottomHideOrShow() ? View.VISIBLE : View.GONE);
            if (positiveButtonText != null) {
                Button positiveButton = (Button) layout.findViewById(R.id.positiveButton);
                //positiveButton.setBackgroundResource(R.drawable.notidialog_all_selector);
                positiveButton.setBackgroundResource(R.drawable.background_rounded_button_s);
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener((View v) -> {
                        positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
            }

            layout.findViewById(R.id.alert_dialog_view).setVisibility(View.GONE);
            layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);

            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            }
            return layout;
        }

        /**
         * set button the more
         *
         * @param layout
         * @return
         */
        private View setMoreButton(View layout, CustomDialogReverse dialog) {

            return layout;
        }
    }


}
