package com.alone.main;

import com.alone.core.*;
import com.alone.enums.PosTypeEnum;
import com.alone.environment.GetEnvironment;
import com.alone.pojo.base.EnvironmentInfo;
import com.alone.pojo.base.LoginInfo;
import com.alone.pojo.collection.CollectionTicketInfo;
import com.alone.pojo.terminal.TerminalInfo;
import com.alone.util.EncryptSha256Util;
import com.alone.util.LoginUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: hetilong
 * @Date: 2021/12/18 21:44
 */
public class DemoTest {
    public static void main(String[] args) throws IOException {
        String environment = "TEST";
        int eventId = 782;

        String path="/Users/maoyan/work/pos-test/hk-pos-autoTest/src/main/resources/loginInfo.properties";
        LoginUtil loginUtil = new LoginUtil();
        Map<String, LoginInfo> loginInfoMap = loginUtil.getLoginInfo(path);

        LoginInfo loginInfo = loginInfoMap.get(environment);

        GetEnvironment env = new GetEnvironment();
        EnvironmentInfo environmentInfo = env.getEnvironmentCurl(environment);

        User user = new User(environmentInfo,loginInfo);
        //查询terminal信息
        TerminalInfo terminalInfo = user.getTerminalInfo(environment);
        //获取登录cookies
        String cookies = user.login();
        List<String> tranNumbers = new ArrayList<>();
        try{
            System.out.println("=======购买门票=========");
            NormalTicket normalTicket = new NormalTicket(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("REG").getStatus(),eventId);
            String tranNumber = normalTicket.createOrder();
            tranNumbers.add(tranNumber);
            /*
            System.out.println("========电话购票=========");
            TelephoneTicket telephoneTicket = new TelephoneTicket(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("TELEPHONE_TICKET").getStatus(),eventId);
            telephoneTicket.creatOrder();
             */

            System.out.println("======预留内销门票========");
            ReservationConsignment rc = new ReservationConsignment(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("RESERVATION_CONSIGNMENT").getStatus(),eventId);
            String claimId = rc.creatOrder();

            System.out.println("=======取消预留========");
            ReserveClaimAndRelease release = new ReserveClaimAndRelease(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("RLS").getStatus());
            String resReserve = release.queryResTransaction(claimId,5);
            release.releaseReserve(resReserve);

            System.out.println("=======领取预留========");
            ReserveClaimAndRelease claim = new ReserveClaimAndRelease(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("CLM").getStatus());
            tranNumbers.add(claim.claimOrder(claimId,4));


            System.out.println("=======大量购票========");
            BulkTicket bulkTicket = new BulkTicket(terminalInfo,environmentInfo,
                    loginInfo,cookies,PosTypeEnum.valueOf("BUK").getStatus(),eventId);
            bulkTicket.creatOrder();
            System.out.println("=======领取已购======");
            CollectionTicket ct = new CollectionTicket(loginInfo,terminalInfo,environmentInfo,cookies);
            CollectionTicketInfo c = new CollectionTicketInfo("","","0","","",
                    new EncryptSha256Util().getSha256Str("6250947000000014"),"");
            ct.getListCollectionTicket(c);

            System.out.println("=======升级门票========");
            UpgradeTicket upgradeTicket = new UpgradeTicket(terminalInfo,environmentInfo,loginInfo,cookies,
                    PosTypeEnum.valueOf("UPG").getStatus());
            tranNumbers.add(upgradeTicket.creatOrder(tranNumber));

            System.out.println("=======重印门票======");
            ReprintTicket reprintTicket = new ReprintTicket(terminalInfo,environmentInfo,loginInfo,cookies,
                    PosTypeEnum.valueOf("REPRINT").getStatus());
            reprintTicket.reprintOrder();

            System.out.println("=======更改持票人========");
            ChangeHolder changeHolder = new ChangeHolder(terminalInfo,environmentInfo,loginInfo,cookies,
                    PosTypeEnum.valueOf("REN").getStatus());
            tranNumbers.add(changeHolder.creatOrder(tranNumber));

            System.out.println("=======换票========");
            ExchangeTicket exchangeTicket = new ExchangeTicket(terminalInfo,environmentInfo,loginInfo,cookies,
                    PosTypeEnum.valueOf("EXC").getStatus(),eventId);
            tranNumbers.add(exchangeTicket.exchangeOrder(tranNumber));

            System.out.println("=====离线退款====");
            RefundTicket refundTicket = new RefundTicket(terminalInfo,environmentInfo,loginInfo,
                    cookies,PosTypeEnum.valueOf("REFUND_TICKET").getStatus());
            refundTicket.refundOrder(tranNumbers);

            System.out.println("======交易查询======");
            Transaction tran = new Transaction(cookies,terminalInfo,environmentInfo);
            tran.queryTran(tranNumber);
            System.out.println("======报表预览======");
            tran.summary();

            System.out.println("======收取手续费=====");
            PoundageTran poundageTran = new PoundageTran(terminalInfo,environmentInfo,loginInfo,
                    cookies,PosTypeEnum.valueOf("POUNDAGE").getStatus());
            poundageTran.poundageOrder();

        }catch (Exception e){
            e.printStackTrace();
        }
        //登出。
        user.logout();
    }
}
