package pe.bstest.android.common.ui.md_custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CustomStepperView extends FrameLayout {
    private RecyclerView mListView;
    private ItemAdapter mAdapter;

    /**
     * View State
     */
    private IStepperAdapter mStepperAdapter;
    private int mCurrentStep = 0;
    private CharSequence[] mErrorTexts = null;

    /**
     * View attributes
     */
    private boolean mAnimationEnabled;
    private int mAnimationDuration;
    private int mNormalColor, mActivatedColor, mLineColor, mErrorColor;
    private Drawable mDoneIcon;
    private boolean mAlwaysShowSummary = false;


    public CustomStepperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        prepareListView(context);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomStepperView,
                    defStyleAttr, R.style.Widget_Stepper);

            mNormalColor = a.getColor(R.styleable.CustomStepperView_step_normal_color, mNormalColor);
            mActivatedColor = a.getColor(R.styleable.CustomStepperView_step_activated_color, mActivatedColor);
            mAnimationDuration = a.getInt(R.styleable.CustomStepperView_step_animation_duration, mAnimationDuration);
            mAnimationEnabled = a.getBoolean(R.styleable.CustomStepperView_step_enable_animation, true);
            mLineColor = a.getColor(R.styleable.CustomStepperView_step_line_color, mLineColor);
            mErrorColor = a.getColor(R.styleable.CustomStepperView_step_error_highlight_color, mErrorColor);
            mAlwaysShowSummary = a.getBoolean(R.styleable.CustomStepperView_step_show_summary_always, mAlwaysShowSummary);

            if (a.hasValue(R.styleable.CustomStepperView_step_done_icon)) {
                mDoneIcon = a.getDrawable(R.styleable.CustomStepperView_step_done_icon);
            }

            a.recycle();
        }

        setAnimationEnabled(mAnimationEnabled);
    }

    public void setAnimationEnabled(boolean enabled) {
        mAnimationEnabled = enabled;
    }

    private void prepareListView(Context context) {
        mListView = new RecyclerView(context);
        mAdapter = new ItemAdapter();

        mListView.setClipToPadding(false);
        mListView.setPadding(0, getResources().getDimensionPixelSize(R.dimen.stepper_margin_top), 0, 0);

        mListView.addItemDecoration(new CustomItemDecoration(getResources().getDimensionPixelSize(R.dimen.stepper_item_space_height)));
        mListView.setLayoutManager(new LinearLayoutManager(context));
        mListView.setAdapter(mAdapter);

        addView(mListView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }



    public IStepperAdapter getStepperAdapter() {
        return mStepperAdapter;
    }
    public int getCurrentStep() {
        return mCurrentStep;
    }
    public int getStepCount() {
        return mStepperAdapter != null ? mStepperAdapter.size() : 0;
    }
    class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(new CustomStepperItemView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.mItemView.setIndex(position + 1);
            holder.mItemView.setIsLastStep(position == getItemCount() - 1);
            holder.mItemView.setTitle(getStepperAdapter().getTitle(position));
            holder.mItemView.setSummary(getStepperAdapter().getSummary(position));
            holder.mItemView.setNormalColor(mNormalColor);
            holder.mItemView.setActivatedColor(mActivatedColor);
            holder.mItemView.setAnimationDuration(mAnimationDuration);
            holder.mItemView.setDoneIcon(mDoneIcon);
            holder.mItemView.setAnimationEnabled(mAnimationEnabled);
            holder.mItemView.setLineColor(mLineColor);
            holder.mItemView.setErrorColor(mErrorColor);
            holder.mItemView.setErrorText(mErrorTexts[position]);
            holder.mItemView.setAlwaysShowSummary(mAlwaysShowSummary);
            if (getCurrentStep() > position) {
                holder.mItemView.setState(CustomStepperItemView.STATE_DONE);
            } else if (getCurrentStep() < position) {
                holder.mItemView.setState(CustomStepperItemView.STATE_NORMAL);
            } else {
                holder.mItemView.setState(CustomStepperItemView.STATE_SELECTED);
            }
            holder.mItemView.removeCustomView();
            View customView = getStepperAdapter().onCreateCustomView(position, getContext(), holder.mItemView);
            if (customView != null) {
                holder.mItemView.addView(customView);
            }
        }

        @Override
        public int getItemCount() {
            return getStepCount();
        }

        class ItemHolder extends RecyclerView.ViewHolder {
            CustomStepperItemView mItemView;
            ItemHolder(CustomStepperItemView itemView) {
                super(itemView);
                mItemView = itemView;

                ViewGroup.LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mItemView.setLayoutParams(lp);
            }
        }
    }
}
