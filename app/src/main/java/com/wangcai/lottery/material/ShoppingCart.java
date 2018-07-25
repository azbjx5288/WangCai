package com.wangcai.lottery.material;

import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.data.Balls;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.TraceIssue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ACE-PC on 2016/1/26.
 */
public class ShoppingCart {

    private static ShoppingCart instance = new ShoppingCart();
    private ShoppingCart() {
    }
    public static ShoppingCart getInstance() {
        return instance;
    }
    private Lottery lottery;    /** 彩种ID */
    private String issue;   /** 奖期 */
    private LucreMode lucreMode = LucreMode.fromCode(WangCaiApp.getUserCentre().getLucreMode()); /** 当前金额模式 */
    private long planNotes = 0;  /** 计划注数 */
    private int multiple = 1;   /** 倍数 */
    private int traceNumber = 0;    /** 追号奖期 */
    private boolean stopOnWin = true;   /** 追中即停 */
    private double planAmount = 0.00;   /** 计划金额 */
    private int prizeGroup = WangCaiApp.getUserCentre().getPrizeGroup();    /** 奖金组 **/
    private ArrayList<Balls> codeData=new ArrayList<>();    /** 投注组合字符串 */
    private Map<String,Integer> ordersMap=new HashMap<>();
    private List<Ticket> codesMap = new ArrayList<>();      /** 投注号 */
    private ArrayList<TraceIssue> traceIssue=new ArrayList<>();
    public int getMultiple() {
        return multiple;
    }

    public int getTraceNumber() {
        return traceNumber;
    }

    public boolean isStopOnWin() {
        return stopOnWin;
    }

    public double getPlanAmount() {
        return planAmount;
    }

    public boolean isEmpty() {
        return codesMap.size() == 0;
    }

    public String getIssue() { return issue; }

    public void setIssue(String issue) { this.issue = issue; ordersPurify(); }

    public void addTicket(Ticket ticket) {
        codesMap.add(ticket);
        codePurify();
        ruleCount();
    }

    public void addTraceIssue(ArrayList<TraceIssue> traceIssue){
        if(this.traceIssue.size()>0){
            this.traceIssue.clear();
        }
        this.traceIssue.addAll(traceIssue);
        ordersPurify();
    }

    public List<Ticket> getCodesMap() {
        return codesMap;
    }

    public long getPlanNotes() {
        return planNotes;
    }

    public LucreMode getLucreMode() {
        return lucreMode;
    }

    public ArrayList<Balls> getCodeData() { return codeData; }

    public Map<String, Integer> getOrdersMap() { return ordersMap; }

    public int getPrizeGroup() {
        return prizeGroup;
    }

    public void setPrizeGroup(int prizeGroup) {
        this.prizeGroup = prizeGroup;
        codePurify();
    }

    public void init(Lottery lottery) {
        if (this.lottery == null || this.lottery.getId() != lottery.getId()) {
            clear();
        }
        this.lottery = lottery;
        this.multiple = 1;
        this.traceNumber = 0;
        codePurify();
        ordersPurify();
        ruleCount();
    }

    private void codePurify() {
        codeData.clear();
        for (int i=0,size=codesMap.size();i<size;i++) {
            Ticket ticket =codesMap.get(i);
            codeData.add(new Balls(i+1,ticket.getChooseMethod().getId(),ticket.getCodes(),ticket.getChooseNotes(),lucreMode.getFactor(),multiple,prizeGroup,new ArrayList()));
        }
    }

    private void ordersPurify() {
        ordersMap.clear();
        /*if(traceNumber>0) {
            if(traceIssue.size()>=traceNumber) {
                for (int i = 0; i < traceNumber; i++) {
                    ordersMap.put(traceIssue.get(i).getNumber(), multiple);
                }
            }else{
                for (int i = 0; i < traceIssue.size(); i++) {
                    ordersMap.put(traceIssue.get(i).getNumber(), multiple);
                }
                this.traceNumber=traceIssue.size();
            }
        }else{
            ordersMap.put(issue, multiple);
        }*/

        if(traceNumber>0) {
            if(traceIssue.size()>=traceNumber) {
                for (int i = 0; i < traceNumber; i++) {
                    ordersMap.put(traceIssue.get(i).getNumber(), 1);
                }
            }else{
                for (int i = 0; i < traceIssue.size(); i++) {
                    ordersMap.put(traceIssue.get(i).getNumber(), 1);
                }
                this.traceNumber=traceIssue.size();
            }
        }else{
            ordersMap.put(issue, 1);
        }
    }

    public void setPlanBuyRule() {
        this.stopOnWin = WangCaiApp.getUserCentre().getStopOnWin();
        this.lucreMode = LucreMode.fromCode(WangCaiApp.getUserCentre().getLucreMode());
        this.prizeGroup = WangCaiApp.getUserCentre().getPrizeGroup();
        ruleCount();
        codePurify();
        ordersPurify();
    }

    public void setPlanBuyRule(int multiple, int tracenum) {
        this.multiple = multiple;
        this.traceNumber = tracenum;
        this.stopOnWin = WangCaiApp.getUserCentre().getStopOnWin();
        this.lucreMode = LucreMode.fromCode(WangCaiApp.getUserCentre().getLucreMode());
        this.prizeGroup = WangCaiApp.getUserCentre().getPrizeGroup();
        ruleCount();
        codePurify();
        ordersPurify();
    }

    public void setPlanBuyRule(int multiple, int tracenum, boolean stopOnWin) {
        this.multiple = multiple;
        this.traceNumber = tracenum;
        this.stopOnWin = stopOnWin;
        this.lucreMode = LucreMode.fromCode(WangCaiApp.getUserCentre().getLucreMode());
        this.prizeGroup = WangCaiApp.getUserCentre().getPrizeGroup();
        ruleCount();
        codePurify();
        ordersPurify();
    }

    public void ruleCount() {
        long notes = 0;
        for (Ticket ticket : codesMap) {
            notes += ticket.getChooseNotes();
        }

        double totalOrder = lucreMode.getFactor() * 2 * getMultiple() * notes;
        if (traceNumber > 0) {
            totalOrder *= traceNumber;
        }

        this.planNotes = notes;
        this.planAmount = totalOrder;
    }

    public void deleteCode(int position) {
        if (position >= 0 && position < codesMap.size()) {
            codesMap.remove(position);
            codePurify();
            ruleCount();
            ordersPurify();
        }
    }

    public void clear() {
        this.lottery = null;
        this.codeData.clear();
        this.planNotes = 0;
        this.multiple = 1;
        this.traceNumber = 0;
        this.stopOnWin = true;
        this.planAmount = 0.00;
        this.codesMap.clear();
        this.ordersMap.clear();
        this.traceIssue.clear();
        this.prizeGroup=0;
    }
}
