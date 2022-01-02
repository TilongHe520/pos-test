package com.alone.test;

import com.alone.core.*;
import com.alone.enums.PosTypeEnum;
import com.alone.report.Report;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * @Author: hetilong
 * @Date: 2022/1/2 12:14
 */
@Listeners({Report.class})
public class PosAutoTest extends AutomationTest {

    public String claimId;

    @Test(priority = 2,description = "normalTicket")
    public void normalTicket(){
        NormalTicket normalTicket = new NormalTicket(terminalInfo,environmentInfo,
                loginInfo,cookies, PosTypeEnum.valueOf("REG").getStatus(),eventId);
        String tranNumber = normalTicket.createOrder();
        tranNumbers.add(tranNumber);
    }

    @Test(priority =3,description = "ReservationConsignment")
    public void ReservationConsignment(){
        ReservationConsignment rc = new ReservationConsignment(terminalInfo,environmentInfo,
                loginInfo,cookies,PosTypeEnum.valueOf("RESERVATION_CONSIGNMENT").getStatus(),eventId);
        claimId = rc.creatOrder();
    }

    @Test(priority =4,dependsOnMethods = "ReservationConsignment",description = "releaseTicket")
    public void claimTicket(){
        ReserveClaimAndRelease claim = new ReserveClaimAndRelease(terminalInfo,environmentInfo,
                loginInfo,cookies,PosTypeEnum.valueOf("CLM").getStatus());
        tranNumbers.add(claim.claimOrder(claimId,4));
    }

    @Test(priority =5,dependsOnMethods = "ReservationConsignment",description = "releaseTicket")
    public void releaseTicket(){
        ReserveClaimAndRelease release = new ReserveClaimAndRelease(terminalInfo,environmentInfo,
                loginInfo,cookies,PosTypeEnum.valueOf("RLS").getStatus());
        String resReserve = release.queryResTransaction(claimId,5);
        release.releaseReserve(resReserve);
    }

    @Test(priority =6,dependsOnMethods = "normalTicket",description = "refundTicket")
    public void refundTicket(){
        RefundTicket refundTicket = new RefundTicket(terminalInfo,environmentInfo,loginInfo,
                cookies,PosTypeEnum.valueOf("REFUND_TICKET").getStatus());
        refundTicket.refundOrder(tranNumbers);
    }

    @Test(priority =7,dependsOnMethods = "refundTicket",description = "queryAndSummary")
    public void trans(){
        Transaction tran = new Transaction(cookies,terminalInfo,environmentInfo);
        tran.queryTran(tranNumbers);
        tran.summary();
    }

    @Test(priority =8,description = "poundageTran")
    public void poundageTran(){
        PoundageTran poundageTran = new PoundageTran(terminalInfo,environmentInfo,loginInfo,
                cookies,PosTypeEnum.valueOf("POUNDAGE").getStatus());
        poundageTran.poundageOrder();
    }
}
