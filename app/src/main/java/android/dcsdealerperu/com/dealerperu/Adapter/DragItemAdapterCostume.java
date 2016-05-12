package android.dcsdealerperu.com.dealerperu.Adapter;

import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public abstract class DragItemAdapterCostume<T, VH extends DragItemAdapterCostume.ViewHolder> extends RecyclerView.Adapter<VH> {

    interface DragStartCallback {
        boolean startDrag(View itemView, long itemId);
        boolean isDragging();
    }

    private DragStartCallback mDragStartCallback;
    private long mDragItemId = -1;
    private boolean mDragOnLongPress;
    protected List<ResponseMarcarPedido> mItemList;

    public DragItemAdapterCostume(boolean dragOnLongPress) {
        mDragOnLongPress = dragOnLongPress;
    }

    public void setItemList(List<ResponseMarcarPedido> itemList) {
        mItemList = itemList;
        notifyDataSetChanged();
    }

    public List<ResponseMarcarPedido> getItemList() {
        return mItemList;
    }

    public Object removeItem(int pos) {
        if (mItemList != null && mItemList.size() > pos) {
            Object item = mItemList.remove(pos);
            notifyItemRemoved(pos);
            return item;
        }
        return null;
    }

    public void addItem(int pos, ResponseMarcarPedido item) {
        if (mItemList != null && mItemList.size() >= pos) {
            mItemList.add(pos, item);
            notifyItemInserted(pos);
        }
    }

    public void changeItemPosition(int fromPos, int toPos) {
        if (mItemList != null && mItemList.size() > fromPos && mItemList.size() > toPos) {
            ResponseMarcarPedido item = mItemList.remove(fromPos);
            mItemList.add(toPos, item);
            notifyItemMoved(fromPos, toPos);
        }
    }

    public int getPositionForItemId(long id) {
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            if (id == getItemId(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        long itemId = getItemId(position);
        holder.mItemId = itemId;
        holder.itemView.setVisibility(mDragItemId == itemId ? View.INVISIBLE : View.VISIBLE);
    }

    void setDragStartedListener(DragStartCallback dragStartedListener) {
        mDragStartCallback = dragStartedListener;
    }

    void setDragItemId(long dragItemId) {
        mDragItemId = dragItemId;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public View mGrabView;
        public long mItemId;

        public ViewHolder(final View itemView, int handleResId) {
            super(itemView);
            mGrabView = itemView.findViewById(handleResId);

            if (mDragOnLongPress) {
                mGrabView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (mDragStartCallback.startDrag(itemView, mItemId)) {
                            return true;
                        }
                        if (itemView == mGrabView) {
                            return onItemLongClicked(view);
                        }
                        return false;
                    }
                });
            } else {
                mGrabView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN && mDragStartCallback.startDrag(itemView, mItemId)) {
                            return true;
                        }
                        if (!mDragStartCallback.isDragging() && itemView == mGrabView) {
                            return onItemTouch(view, event);
                        }
                        return false;
                    }
                });
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked(view);
                }
            });

            if (itemView != mGrabView) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return onItemLongClicked(view);
                    }
                });
                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        return onItemTouch(view, event);
                    }
                });
            }
        }

        public void onItemClicked(View view) {
        }

        public boolean onItemLongClicked(View view) {
            return false;
        }

        public boolean onItemTouch(View view, MotionEvent event) {
            return false;
        }
    }
}

