package zhm.Train.domain;

import java.io.Serializable;

public class Ticket implements Serializable {

    private int TOrderID;
    private int TrainID;
    private int UserID;
    private int BeginPosID;
    private int EndPosID;
    private int SeatID;
    private Boolean ChangeOrder;
    private int ChangeOrderID;
    private Boolean CancelOrder;
    private Boolean TicketPrint;

    public int getTOrderID() {
        return TOrderID;
    }

    public void setTOrderID(int TOrderID) {
        this.TOrderID = TOrderID;
    }

    public int getTrainID() {
        return TrainID;
    }

    public void setTrainID(int trainID) {
        TrainID = trainID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getBeginPosID() {
        return BeginPosID;
    }

    public void setBeginPosID(int beginPosID) {
        BeginPosID = beginPosID;
    }

    public int getEndPosID() {
        return EndPosID;
    }

    public void setEndPosID(int endPosID) {
        EndPosID = endPosID;
    }

    public int getSeatID() {
        return SeatID;
    }

    public void setSeatID(int seatID) {
        SeatID = seatID;
    }

    public Boolean getChangeOrder() {
        return ChangeOrder;
    }

    public void setChangeOrder(Boolean changeOrder) {
        ChangeOrder = changeOrder;
    }

    public int getChangeOrderID() {
        return ChangeOrderID;
    }

    public void setChangeOrderID(int changeOrderID) {
        ChangeOrderID = changeOrderID;
    }

    public Boolean getTicketPrint() {
        return TicketPrint;
    }

    public void setTicketPrint(Boolean ticketPrint) {
        TicketPrint = ticketPrint;
    }

    public Boolean getCancelOrder() {
        return CancelOrder;
    }

    public void setCancelOrder(Boolean cancelOrder) {
        CancelOrder = cancelOrder;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "TOrderID=" + TOrderID +
                ", TrainID=" + TrainID +
                ", UserID=" + UserID +
                ", BeginPosID=" + BeginPosID +
                ", EndPosID=" + EndPosID +
                ", SeatID=" + SeatID +
                ", ChangeOrder=" + ChangeOrder +
                ", ChangeOrderID=" + ChangeOrderID +
                ", CancelOrder=" + CancelOrder +
                ", TicketPrint=" + TicketPrint +
                '}';
    }
}
