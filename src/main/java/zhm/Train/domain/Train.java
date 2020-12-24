package zhm.Train.domain;

import java.io.Serializable;

public class Train implements Serializable {

    private int id;
    private String TrainName;
    private int sourStationID;
    private int destStationID;
    private int StationCnt;
    private double price;
    private String BeginTime;
    private boolean isdelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrainName() {
        return TrainName;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public int getSourStationID() {
        return sourStationID;
    }

    public void setSourStationID(int sourStationID) {
        this.sourStationID = sourStationID;
    }

    public int getDestStationID() {
        return destStationID;
    }

    public void setDestStationID(int destStationID) {
        this.destStationID = destStationID;
    }

    public int getStationCnt() {
        return StationCnt;
    }

    public void setStationCnt(int stationCnt) {
        StationCnt = stationCnt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isIsdelete() {
        return isdelete;
    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String createTime) {
        BeginTime = createTime;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", TrainName='" + TrainName + '\'' +
                ", sourStationID=" + sourStationID +
                ", destStationID=" + destStationID +
                ", StationCnt=" + StationCnt +
                ", price=" + price +
                ", BeginTime='" + BeginTime + '\'' +
                ", isdelete=" + isdelete +
                '}';
    }
}
