/**
 * Created by liuzz on 2018/7/1.
 */
public class Test {


    @org.junit.Test
    public void test(){
       String routing = "{\"adultPrice\":843,\"adultTax\":376,\"adultTaxType\":0,\"applyType\":0,\"childPrice\":843,\"childTax\":376,\"childTaxType\":0,\"codeShare\":false,\"combineIndex\":[[]],\"currency\":\"RMB\",\"data\":\"H4sIAAAAAAAAAEWOO1MDMQyE/4tqz40t2bLPXcIlEN7JJeTRXHG5DioqhuG/s4gJNOtPK3mlT5pRpe4wH1b9dmAfis+hkKNZoFqiAPgCQtXjSVQlK0AvkH8bBUmSJbL4MWv0MZ5L4VBE/JQmmXQ6eyTPMbY8odMOC5RXKN8+3l/HEUWHYtd3oAVVVWk4O1pa/DXVwDFq6+jGjBUMTfJz2i3VVpSlOLqzsUbg3lNN2iSYD//m45/5ZCnPpmvsXWPrxm4D9PjhaGu6s5EX073pwfyj8QmcxMcUVFk5fH0DQgKHyFMBAAA=\",\"fareType\":1,\"fromSegments\":[{\"aircraftCode\":\"\",\"arrAirport\":\"SAW\",\"arrTime\":\"201807181430\",\"cabin\":\"Q\",\"cabinClass\":1,\"carrier\":\"FZ\",\"codeshare\":false,\"depAirport\":\"DXB\",\"depTime\":\"201807181040\",\"flightNumber\":\"FZ8829\",\"operatingCarrier\":\"FZ\",\"stopCities\":\"\"}],\"nationalType\":0,\"priceType\":0,\"retSegments\":[],\"rule\":{\"baggage\":\"-\",\"endorse\":\"*-0-*\",\"endorsement\":0,\"hasBaggage\":0,\"hasEndorse\":0,\"hasNoShow\":0,\"hasRefund\":0,\"noShowLimitTime\":72,\"other\":\"1.涉及多程和多国的签证须自理，若因签证等原因产生的损失自行承担。2.境外开票，无法打印纸质版行程单，有电子版报销凭证。3.退改期请起飞前72小时，否则进入误机状态无法退票和改期。周一到周五09：00-16：00提交（周六日及节假日不受理）4.行李额按照航司规定 5.请提前值机并打印值机凭证随身携带。6.订单支付成功后，无法更改乘机信息。7.购票后，请关注您的邮箱邮件，我们会把您机票的相关资料给您发送到邮箱，提醒关注您的垃圾邮箱，避免遗漏您的重要机票资料而影响登机。 \",\"partEndorse\":0,\"partEndorsePrice\":0,\"partRefund\":0,\"partRefundPrice\":0,\"penalty\":0,\"refund\":\"*-0-*\",\"specialNoShow\":3},\"sameCarrier\":true,\"sellLuggage\":false,\"ticketInvoiceType\":1,\"ticketTimeLimit\":10000,\"validatingCarrier\":\"FZ\"}";
        System.out.println(routing.getBytes().length*50/1024);
    }
}
