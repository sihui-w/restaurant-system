package mhl.service;

import mhl.dao.BillDAO;
import mhl.dao.MultiTableDAO;
import mhl.domain.Bill;
import mhl.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

//处理账单相关业务
public class BillService {

    private BillDAO billDAO = new BillDAO();

    private  MenuService menuService = new MenuService();

    private  DiningTableService diningTableService = new DiningTableService();

    private MultiTableDAO multiTableDAO = new MultiTableDAO();

//    点餐方法：1.生成账单；2.更新餐桌状态
    public boolean orderMenu(int menuId, int nums,int diningTableId){
//        生成账单号
        String billId = UUID.randomUUID().toString();

        int update = billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未结账')",
                billId,menuId,nums,menuService.getMenuById(menuId).getPrice()*nums,diningTableId);

        if(update <= 0){
            return false;
        }

//        更新餐桌状态
        return diningTableService.updateDiningTableState(diningTableId,"就餐中");



    }

    //        返回所有账单

    public List<Bill> list(){
        return billDAO.queryMulti("select * from bill", Bill.class);
    }

    public List<MultiTableBean>list2(){
        return multiTableDAO.queryMulti("select bill.*,name from bill,menu where bill.menuId = menu.id ",MultiTableBean.class);
    }


//    查看某餐桌是否结账

    public boolean hasPayByDiningTableId(int diningTableId){
        Bill bill =  billDAO.querySingle("select * from bill where diningTableId = ? and state = '未结账' limit 0,1",
                Bill.class,diningTableId);
        return bill != null;
    }

//    结账后，修改bill,diningTable的状态
    public boolean payBill(int diningTableId,String payMode){
//        bill
        int update = billDAO.update("update bill set state=? where diningTableId = ? and state='未结账'",payMode,diningTableId);
        if(update <= 0){
            return false;
        }

//        diningService

        if(!diningTableService.updateDiningTableToFree(diningTableId,"emtry")){
            return false;
        }

        return true;

    }



}

































































