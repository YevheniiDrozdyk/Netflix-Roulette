package com.indev.netflixroulette.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Item click listener of recycler view.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private GestureDetector mGestureDetector;

    public RecyclerViewItemClickListener(Context context, OnItemClickListener clickListener) {
        mClickListener = clickListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
            mClickListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}