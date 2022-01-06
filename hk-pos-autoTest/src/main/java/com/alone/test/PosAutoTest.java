package com.alone.test;

import com.alone.core.*;
import com.alone.enums.PosTypeEnum;
import com.alone.pojo.collection.CollectionTicketInfo;
import com.alone.pojo.print.UploadPrintInfo;
import com.alone.report.Report;
import com.alone.util.EncryptSha256Util;
import com.alone.util.JsonUtil;
import com.alone.util.UploadPrintUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Author: hetilong
 * @Date: 2022/1/2 12:14
 */
@Listeners({Report.class})
public class PosAutoTest extends AutomationTest {

    public String claimId;
    public String tranNumber;

    @Test(priority = 2,description = "normalTicket")
    public void normalTicket(){
        NormalTicket normalTicket = new NormalTicket(terminalInfo,environmentInfo,
                loginInfo,cookies, PosTypeEnum.valueOf("REG").getStatus(),eventId);
        tranNumber = normalTicket.createOrder();
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

    @Test(priority = 6,description = "bulkTicket")
    public void bulkTicket(){
        BulkTicket bulkTicket = new BulkTicket(terminalInfo,environmentInfo,
                loginInfo,cookies,PosTypeEnum.valueOf("BUK").getStatus(),eventId);
        bulkTicket.creatOrder();
    }

    @Test(priority = 7,description = "upgradeTicket",dependsOnMethods = "normalTicket")
    public void upgradeTicket(){
        UpgradeTicket upgradeTicket = new UpgradeTicket(terminalInfo,environmentInfo,loginInfo,cookies,
                PosTypeEnum.valueOf("UPG").getStatus());
        tranNumbers.add(upgradeTicket.creatOrder(tranNumber));
    }

    @Test(priority = 8,dependsOnMethods = "upgradeTicket",description = "reprintTicket")
    public void reprintTicket(){
        ReprintTicket reprintTicket = new ReprintTicket(terminalInfo,environmentInfo,loginInfo,cookies,
                PosTypeEnum.valueOf("REPRINT").getStatus());
        reprintTicket.reprintOrder();
    }

    @Test(priority = 9,description = "changeHolder",dependsOnMethods = "normalTicket")
    public void changeHolder(){
        ChangeHolder changeHolder = new ChangeHolder(terminalInfo,environmentInfo,loginInfo,cookies,
                PosTypeEnum.valueOf("REN").getStatus());
        tranNumbers.add(changeHolder.creatOrder(tranNumber));
    }

    @Test(priority = 10,description = "exchangeTicket",dependsOnMethods = "normalTicket")
    public void exchangeTicket(){
        ExchangeTicket exchangeTicket = new ExchangeTicket(terminalInfo,environmentInfo,loginInfo,cookies,
                PosTypeEnum.valueOf("EXC").getStatus(),eventId);
        tranNumbers.add(exchangeTicket.exchangeOrder(tranNumber));
    }
    @Test(priority = 11,description = "telephoneTicket")
    public void telephoneTicket(){
        TelephoneTicket telephoneTicket = new TelephoneTicket(terminalInfo,environmentInfo,
                loginInfo,cookies,PosTypeEnum.valueOf("TELEPHONE_TICKET").getStatus(),eventId);
        tranNumber = telephoneTicket.creatOrder();
        tranNumbers.add(tranNumber);
    }

    @Test(priority = 12,dependsOnMethods = "telephoneTicket",description = "collectionTicket")
    public void collectionTicket(){
        JsonUtil jsonUtil = new JsonUtil();
        CollectionTicket ct = new CollectionTicket(loginInfo,terminalInfo,environmentInfo,cookies);
        CollectionTicketInfo c = new CollectionTicketInfo("","","0","","",
                new EncryptSha256Util().getSha256Str("6250947000000014"),"");
        ct.getListCollectionTicket(c);
        String printRes = ct.print(tranNumber);

        List<String> ticketIdList = jsonUtil.getValueByKeyFromJson(printRes,"ticketId");
        String taskId = jsonUtil.getValueByKeyReturnString(printRes,"taskId");

        List<UploadPrintInfo> uploadPrintInfoList = new UploadPrintUtil().getUploadPrintList(taskId,ticketIdList);

        ct.uploadPrintResult(uploadPrintInfoList);
    }

    @Test(priority =13,dependsOnMethods = {"normalTicket","collectionTicket"},description = "refundTicket")
    public void refundTicket(){
        RefundTicket refundTicket = new RefundTicket(terminalInfo,environmentInfo,loginInfo,
                cookies,PosTypeEnum.valueOf("REFUND_TICKET").getStatus());
        refundTicket.refundOrder(tranNumbers);
    }

    @Test(priority =14,dependsOnMethods = "refundTicket",description = "queryAndSummary")
    public void trans(){
        Transaction tran = new Transaction(cookies,terminalInfo,environmentInfo);
        tran.queryTran(tranNumbers);
        tran.summary();
    }

    @Test(priority =15,description = "poundageTran")
    public void poundageTran(){
        PoundageTran poundageTran = new PoundageTran(terminalInfo,environmentInfo,loginInfo,
                cookies,PosTypeEnum.valueOf("POUNDAGE").getStatus());
        poundageTran.poundageOrder();
    }
}
