package com.goldenapple.lottery.view.adapter;

/**
 * Created by Sakura on 2018/7/12.
 */

public class LinkDetailAdapter //@@extends BaseAdapter
{
    /*private ArrayList<RebateOptionsBean> data;
    private String type;
    
    public void setData(ArrayList<RebateOptionsBean> data, String type)
    {
        this.data = data;
        this.type = type;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        if (data == null)
            return null;
        if (position >= 0 && position < data.size())
            return data.get(position);
        return null;
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_detail, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        
        if ("normal_rebate_options".equals(type))
            viewHolder.name.setText(data.get(position).getProperty_name());
        else
            viewHolder.name.setText(data.get(position).getMethod_name());
        viewHolder.option.setText(data.get(position).getRebate());
        
        return convertView;
    }
    
    static class ViewHolder
    {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.option)
        TextView option;
        
        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }*/
}
