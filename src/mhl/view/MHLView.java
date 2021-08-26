package mhl.view;

//主界面

import mhl.domain.*;
import mhl.service.BillService;
import mhl.service.DiningTableService;
import mhl.service.EmployeeService;
import mhl.service.MenuService;
import mhl.utils.Utility;

import java.util.List;

public class MHLView {

    private  boolean loop = true;
//    接收用户输入
    private String key = "";

//    new一个employee service对象，调用数据库

    private EmployeeService employeeService = new EmployeeService();

//    调用diningtableservice属性
    private DiningTableService diningTableService = new DiningTableService();

//    定义menuservice属性
    private MenuService menuService = new MenuService();

//    定义billservice属性
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

//    结账
    public void payBill(){
        System.out.println("================结账服务=================");
        System.out.println("请选择结账餐桌号（-1退出）： ");
        int diningTableId = Utility.readInt();
        if(diningTableId == -1){
            System.out.println("================取消结账=================");
            return;
        }

//        验证餐桌是否存在
        DiningTable diningTable = diningTableService.getDiningTableById(diningTableId);
        if(diningTable == null){
            System.out.println("================餐桌不存在=================");
            return;
        }

//        验证餐桌是否需要结账
        if(!billService.hasPayByDiningTableId(diningTableId)){
            System.out.println("================餐桌不需结账=================");
            return;
        }

        System.out.println("结账方式（现金/支付宝/微信）回车表示退出： ");
        String payMode = Utility.readString(20,"");
        if("".equals(payMode)){
            System.out.println("================取消结账=================");
            return;
        }

        char key = Utility.readConfirmSelection();
        if(key=='Y'){

            if(billService.payBill(diningTableId,payMode)){
                System.out.println("================结账成功=================");
            }else{
                System.out.println("================结账失败=================");
            }

        }else{
            System.out.println("================取消结账=================");
        }
    }

//    显示账单
    public void viewBill(){
        List<MultiTableBean> multiTableBeans = billService.list2();
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜名");
        for(MultiTableBean multiTableBean : multiTableBeans){
            System.out.println(multiTableBean);

        }
        System.out.println("================显示完毕=================");
    }

//    点餐服务
    public void orderMenu(){
        System.out.println("================点餐服务=================");

        System.out.println("请输入点餐桌号（-1退出）： ");
        int orderDiningTableId = Utility.readInt();
        if(orderDiningTableId == -1){
            System.out.println("================取消点餐=================");
            return;
        }

        //        检验餐桌
        DiningTable diningTable= diningTableService.getDiningTableById(orderDiningTableId);
        if(diningTable == null){
            System.out.println("================餐桌不存在=================");
            return;
        }

        System.out.println("请输入点餐菜品号（-1退出）： ");
        int orderMenuId = Utility.readInt();
        if(orderMenuId == -1){
            System.out.println("================取消点餐=================");
            return;
        }

        //        检验菜品
        Menu menu = menuService.getMenuById(orderMenuId);
        if(menu == null){
            System.out.println("================菜品不存在=================");
            return;
        }


        System.out.println("请输入点餐菜品量（-1退出）： ");
        int orderNums =Utility.readInt();


        if(billService.orderMenu(orderMenuId,orderNums,orderDiningTableId)){
            System.out.println("================点餐成功=================");
        }else{
            System.out.println("================点餐失败=================");
        }

    }

//    显示所有菜品

    public void listMenu(){
        List <Menu>list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for(Menu menu: list){
            System.out.println(menu);
        }
        System.out.println("================显示完毕=================");
    }


//    完成订座
    public void orderDiningTable(){
        System.out.println("================预定餐桌=================");
        System.out.println("请选择预定餐桌编号（-1退出)： ");
        int orderId = Utility.readInt();
        if(orderId == -1){
            System.out.println("================预定取消=================");
            return;
        }
        char key = Utility.readConfirmSelection();
        if(key == 'Y'){
//            根据orderId返回对象
            DiningTable diningTable = diningTableService.getDiningTableById(orderId);
            if(diningTable == null){
                System.out.println("================餐桌不存在=================");
                return;
            }
            if(!("emtry".equals(diningTable.getState()))){
                System.out.println("================无法预定餐桌=================");
                return;
            }
            System.out.println("预定人姓名： ");
            String orderName =Utility.readString(50);

            System.out.println("预定人电话： ");
            String orderTel = Utility.readString(50);

//            接收预定信息
            if(diningTableService.orderDiningTable(orderId,orderName,orderTel)){
                System.out.println("================预定成功=================");
            }else{
                System.out.println("================预定失败=================");
            };

        }else{
            System.out.println("================预定取消=================");
        }
    }

//    显示所有餐桌状态
    public void listDiningTable(){
        List<DiningTable>list = diningTableService.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for(DiningTable diningTable : list){
            System.out.println(diningTable);
        }
        System.out.println("================显示完毕=================");
    }

//    显示主菜单
    public void mainMenu(){
        while(loop){
            System.out.println("=======================满汉楼===========================");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.println("请输入你的选择： ");
            key = Utility.readString(1);
            switch (key){
                case "1":
                    System.out.println("输入员工号： ");
                    String empID = Utility.readString(50);
                    System.out.println("输入密  码： ");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployeeByIdAndPwd(empID,pwd);

                    if(employee != null){
                        System.out.println("=========================登录成功["+employee.getName()+ "]=========================");
//                       显示二级菜单
                        while (loop){
                            System.out.println("===================满汉楼二级菜单==================");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 7 退出满汉楼");
                            System.out.println("请输入你的选择： ");

                            key = Utility.readString(1);
                            switch (key){
                                case "1":
                                    listDiningTable();
                                    break;
                                case "2":
                                    orderDiningTable();
                                    break;
                                case "3":
                                    listMenu();
                                    break;
                                case "4":
                                    orderMenu();
                                    break;
                                case "5":
                                    viewBill();
                                    break;
                                case "6":
                                    payBill();
                                    break;
                                case "7":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入有误，请重新输入： ");


                            }
                        }
                    }else{
                        System.out.println("==========================登录失败==========================");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入： ");
            }

        }
    }
}
