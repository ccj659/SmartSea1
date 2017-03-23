package com.ccj.smartsea.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ccj.smartsea.R;
import com.ccj.smartsea.adapter.base.BaseRecycleAdapter;
import com.ccj.smartsea.adapter.base.ItemClickListener;
import com.ccj.smartsea.adapter.base.ItemLongClickListener;
import com.ccj.smartsea.bean.FishTank;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by ccj on 2017/3/15.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> {


    protected List<FishTank> tList;
    protected Context context;

    public ItemClickListener onItemClickListener;
    public ItemLongClickListener onItemLongClickListener;

    public TestAdapter(List<FishTank> tList, Context context) {
        this.tList = tList;
        this.context = context;
    }

    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_fishtank_adapter, parent, false));
    }




    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MyViewHolder constantHolder = holder;

        if (onItemClickListener != null) {
            constantHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
        }
        constantHolder.tvId.setText(tList.get(position).id);
        constantHolder.tvTemp.setText("水温: "+tList.get(position).temp+" ℃");

        constantHolder.tvId.setText(tList.get(position).id);

        constantHolder.depth.setText("深度: "+tList.get(position).depth+"cm");

        constantHolder.turbid.setText("浊度"+tList.get(position).turbidness+"%");

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_id)
        TextView tvId;
        @Bind(R.id.tv_temp)
        TextView tvTemp;
        @Bind(R.id.depth)
        TextView depth;
        @Bind(R.id.turbid)
        TextView turbid;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }




}