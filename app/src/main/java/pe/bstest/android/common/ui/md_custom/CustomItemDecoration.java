package pe.bstest.android.common.ui.md_custom;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomItemDecoration extends RecyclerView.ItemDecoration{
    private final int verticalSpaceHeight;
    public CustomItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}
