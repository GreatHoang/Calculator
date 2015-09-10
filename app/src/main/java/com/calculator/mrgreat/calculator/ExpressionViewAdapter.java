package com.calculator.mrgreat.calculator;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.interfaces.RSAKey;
import java.util.List;


/**
 * Created by FRAMGIA\hoang.ngoc.dai on 09/09/2015.
 */
public class ExpressionViewAdapter extends RecyclerView.Adapter<ExpressionViewAdapter.ExpressionViewHolder> {

    private List<ExpressionFormat> expressionList;

    private OnItemClickListener listener;


    public ExpressionViewAdapter(List<ExpressionFormat> list) {
        this.expressionList = list;

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ExpressionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_layout, parent, false);
        final ExpressionViewHolder viewHolder = new ExpressionViewHolder(itemView);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, viewHolder.getAdapterPosition());

                }

            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, viewHolder.getAdapterPosition());

                }

            }
        });
        viewHolder.itemExpression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, viewHolder.getAdapterPosition());

                }
            }
        });


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ExpressionViewHolder holder, int position) {
        ExpressionFormat expression = expressionList.get(position);
        holder.tvExpression.setText(expression.expression);
        holder.tvResult.setText("=" + expression.result);
        holder.tvBase.setText("" + expression.base);
        holder.btnDelete.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return expressionList.size();
    }

    public static class ExpressionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView tvExpression;
        protected TextView tvResult;
        protected TextView tvBase;
        protected ImageView imgChoseDelete;
        protected Button btnDelete;
        protected View view;
        protected View itemExpression;

        protected RotateAnimation rotateAnimationCh;
        protected RotateAnimation rotateAnimationunCh;
        protected TranslateAnimation rightToLeft;
        protected TranslateAnimation leftToRight;

        boolean chose = true;

        public ExpressionViewHolder(View itemView) {
            super(itemView);

            view = (View) itemView.findViewById(R.id.card_view);
            itemExpression = (View) itemView.findViewById(R.id.itemView);
            tvExpression = (TextView) itemView.findViewById(R.id.expression_itemView);
            tvResult = (TextView) itemView.findViewById(R.id.result_itemView);
            tvBase = (TextView) itemView.findViewById(R.id.viewBase);
            imgChoseDelete = (ImageView) itemView.findViewById(R.id.imgChoseDelete);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

            imgChoseDelete.setOnClickListener(this);
            //btnDelete.setOnClickListener(this);

            rotateAnimationCh = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
            rotateAnimationCh.setDuration(1000);
            rotateAnimationCh.setFillAfter(true);

            rotateAnimationunCh = new RotateAnimation(90, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimationunCh.setDuration(1000);
            rotateAnimationunCh.setFillAfter(true);

            rightToLeft = new TranslateAnimation(120, 0, 0, 0);
            rightToLeft.setDuration(1000);
            //rightToLeft.setFillAfter(true);

            leftToRight = new TranslateAnimation(0, 120, 0, 0);
            leftToRight.setDuration(1000);


            final AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(rotateAnimationCh);
            animationSet.addAnimation(rotateAnimationunCh);
            animationSet.addAnimation(rightToLeft);
            animationSet.addAnimation(leftToRight);

            //setUpAnimation(buttonDelete, rightToLeft);


        }

        private void setUpAnimation(View view, final Animation animation) {
            view.startAnimation(animation);

        }

        @Override
        public void onClick(View view) {
            int i = view.getId();
            switch (i) {
                case R.id.imgChoseDelete:
                    if (chose) {
                        setUpAnimation(imgChoseDelete, rotateAnimationCh);
                        btnDelete.setVisibility(View.VISIBLE);
                        setUpAnimation(btnDelete, rightToLeft);
                        chose = false;

                    } else {
                        setUpAnimation(imgChoseDelete, rotateAnimationunCh);
                        setUpAnimation(btnDelete, leftToRight);
                        btnDelete.setVisibility(View.INVISIBLE);
                        chose = true;

                    }
                    break;
                default:
                    break;

            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

}
