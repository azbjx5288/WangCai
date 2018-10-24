package com.wangcai.lottery.data;

import java.util.ArrayList;

/**
 * Created by Sakura on 2018/7/12.
 */

public class GetLinkUserResponse
{
    private int totalUserCount;
    private int totalLinks;
    private ArrayList<LinkUserBean> data;
    
    public int getTotalUserCount()
    {
        return totalUserCount;
    }
    
    public void setTotalUserCount(int totalUserCount)
    {
        this.totalUserCount = totalUserCount;
    }
    
    public int getTotalLinks()
    {
        return totalLinks;
    }
    
    public void setTotalLinks(int totalLinks)
    {
        this.totalLinks = totalLinks;
    }
    
    public ArrayList<LinkUserBean> getData()
    {
        return data;
    }
    
    public void setData(ArrayList<LinkUserBean> data)
    {
        this.data = data;
    }
}
