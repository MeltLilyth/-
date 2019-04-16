package com.yuri.pojo;
import java.util.Arrays;
import java.util.List;

public class PageBean<T>{
    private int totalRecord;   //总记录数
    private int totalPage;     //总页数
    private int pageSize;      //每一页的纪录数
    private int startIndex;    //查询的索引(用于数据库)
    private int currentnNum;   //当前页码
    private List<T> dataList;  //当前页显示的数据
    private int start;         //分页的起始页码
    private int end;           //分页的终止页码
    private int[] column;      //存储分页栏的数组
    private String key;        //关键字

    public PageBean(int pageSize, int totalRecord, int currentNum){
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.currentnNum = currentNum;

        if(totalRecord%pageSize!=0){
            this.totalPage = totalRecord/pageSize+1;
        }
        else{
            this.totalPage = totalRecord/pageSize;
        }

        this.startIndex = (currentNum-1)*pageSize;
        this.start =1;
        this.end = 5;

        if(totalPage<5){
            this.end = this.totalPage;
        }
        else{
            this.start = currentNum-2;
            this.end = currentNum+2;

            if(start<0){
                this.start =1;
                this.end = 5;
            }
            if(end > this.totalPage){
                this.end = totalPage;
                this.start = end - 5;
            }
        }


        if(this.start!=this.end){
            int[] target = new int[end];
            int i =start;
            int num = 0;
            while(i<=end){
                if(i>end){
                    break;
                }
                target[num] = i;
                i++;
                num++;
            }

            this.column = target;
        }
        else{
            int[] target = new int[1];
            target[0] = start;
            this.column = target;
        }
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getCurrentnNum() {
        return currentnNum;
    }

    public void setCurrentnNum(int currentnNum) {
        this.currentnNum = currentnNum;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int[] getColumn() {
        return column;
    }

    public void setColumn(int[] column) {
        this.column = column;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalRecord=" + totalRecord +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", startIndex=" + startIndex +
                ", currentnNum=" + currentnNum +
                ", dataList=" + dataList +
                ", start=" + start +
                ", end=" + end +
                ", column=" + Arrays.toString(column) +
                ", key='" + key + '\'' +
                '}';
    }
}
