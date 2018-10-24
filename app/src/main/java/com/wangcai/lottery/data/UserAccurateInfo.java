package com.wangcai.lottery.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Sakura on 2018/7/2.
 */

public class UserAccurateInfo
{
    private int currentUserPrizeGroup;
    private String userQuota;
    private ArrayList<PrizeGroupChild> allPossibleAgentPrizeGroups;
    private ArrayList<PrizeGroupChild> allPossiblePrizeGroups;
    private LinkedHashMap<Integer,Integer> userAllPrizeSetQuota;
    private int agentCurrentPrize;
    private int currentPrize;
    private int agentMinPrizeGroupoup;
    private int playerMinPrizeGroup;
    private float userSingle;
    private float userMulti;
    private float userAG;
    private float userGA;
    private int is_top_agent;
    private int possibleAgentPrizeGroup;
    private int possiblePlayerPrizeGroup;
    private LinkedHashMap<Integer, Float> aPlayers;
    private LinkedHashMap<Integer, Float> aAgents;
    
    public int getCurrentUserPrizeGroup()
    {
        return currentUserPrizeGroup;
    }
    
    public void setCurrentUserPrizeGroup(int currentUserPrizeGroup)
    {
        this.currentUserPrizeGroup = currentUserPrizeGroup;
    }
    
    public String isUserQuota()
    {
        return userQuota;
    }
    
    public void setUserQuota(String userQuota)
    {
        this.userQuota = userQuota;
    }
    
    public int getPossibleAgentPrizeGroup()
    {
        return possibleAgentPrizeGroup;
    }
    
    public void setPossibleAgentPrizeGroup(int possibleAgentPrizeGroup)
    {
        this.possibleAgentPrizeGroup = possibleAgentPrizeGroup;
    }
    
    public int getPossiblePlayerPrizeGroup()
    {
        return possiblePlayerPrizeGroup;
    }
    
    public void setPossiblePlayerPrizeGroup(int possiblePlayerPrizeGroup)
    {
        this.possiblePlayerPrizeGroup = possiblePlayerPrizeGroup;
    }
    
    public ArrayList<PrizeGroupChild> getAllPossibleAgentPrizeGroups()
    {
        return allPossibleAgentPrizeGroups;
    }
    
    public void setAllPossibleAgentPrizeGroups(ArrayList<PrizeGroupChild> allPossibleAgentPrizeGroups)
    {
        this.allPossibleAgentPrizeGroups = allPossibleAgentPrizeGroups;
    }
    
    public ArrayList<PrizeGroupChild> getAllPossiblePrizeGroups()
    {
        return allPossiblePrizeGroups;
    }
    
    public void setAllPossiblePrizeGroups(ArrayList<PrizeGroupChild> allPossiblePrizeGroups)
    {
        this.allPossiblePrizeGroups = allPossiblePrizeGroups;
    }
    
    public LinkedHashMap<Integer,Integer> getUserAllPrizeSetQuota()
    {
        return userAllPrizeSetQuota;
    }
    
    public void setUserAllPrizeSetQuota(LinkedHashMap<Integer,Integer> userAllPrizeSetQuota)
    {
        this.userAllPrizeSetQuota = userAllPrizeSetQuota;
    }
    
    public int getAgentCurrentPrize()
    {
        return agentCurrentPrize;
    }
    
    public void setAgentCurrentPrize(int agentCurrentPrize)
    {
        this.agentCurrentPrize = agentCurrentPrize;
    }
    
    public int getCurrentPrize()
    {
        return currentPrize;
    }
    
    public void setCurrentPrize(int currentPrize)
    {
        this.currentPrize = currentPrize;
    }
    
    public int getAgentMinPrizeGroupoup()
    {
        return agentMinPrizeGroupoup;
    }
    
    public void setAgentMinPrizeGroupoup(int agentMinPrizeGroupoup)
    {
        this.agentMinPrizeGroupoup = agentMinPrizeGroupoup;
    }
    
    public int getPlayerMinPrizeGroup()
    {
        return playerMinPrizeGroup;
    }
    
    public void setPlayerMinPrizeGroup(int playerMinPrizeGroup)
    {
        this.playerMinPrizeGroup = playerMinPrizeGroup;
    }
    
    public float getUserSingle()
    {
        return userSingle;
    }
    
    public void setUserSingle(float userSingle)
    {
        this.userSingle = userSingle;
    }
    
    public float getUserMulti()
    {
        return userMulti;
    }
    
    public void setUserMulti(float userMulti)
    {
        this.userMulti = userMulti;
    }
    
    public int isIs_top_agent()
    {
        return is_top_agent;
    }
    
    public void setIs_top_agent(int is_top_agent)
    {
        this.is_top_agent = is_top_agent;
    }
    
    public float getUserAG()
    {
        return userAG;
    }
    
    public void setUserAG(float userAG)
    {
        this.userAG = userAG;
    }
    
    public float getUserGA()
    {
        return userGA;
    }
    
    public void setUserGA(float userGA)
    {
        this.userGA = userGA;
    }
    
    public String getUserQuota()
    {
        return userQuota;
    }
    
    public int getIs_top_agent()
    {
        return is_top_agent;
    }
    
    public LinkedHashMap<Integer, Float> getaPlayers()
    {
        return aPlayers;
    }
    
    public void setaPlayers(LinkedHashMap<Integer, Float> aPlayers)
    {
        this.aPlayers = aPlayers;
    }
    
    public LinkedHashMap<Integer, Float> getaAgents()
    {
        return aAgents;
    }
    
    public void setaAgents(LinkedHashMap<Integer, Float> aAgents)
    {
        this.aAgents = aAgents;
    }
}
