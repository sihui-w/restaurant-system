package mhl.service;

import mhl.dao.DiningTableDAO;
import mhl.domain.DiningTable;

import java.util.List;

public class DiningTableService {

    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    //    返回所有餐桌信息
    public List<DiningTable> list() {
        return diningTableDAO.queryMulti("select id,state from diningTable", DiningTable.class);
    }

    //    根据id，查询餐桌
    public DiningTable getDiningTableById(int id) {
        return diningTableDAO.querySingle("select * from diningTable where id=?", DiningTable.class, id);
    }

//    如果餐桌可预定，调用方法，更新状态

    public boolean orderDiningTable(int id, String orderName, String orderTel) {
        int update = diningTableDAO.update("update diningTable set state='ordered',orderName=?,orderTel=? where id=?", orderName, orderTel, id);

        return update > 0;
    }

//    提供一个更新餐桌状态的方法
    public boolean updateDiningTableState(int id,String state){
        int update = diningTableDAO.update("update diningTable set state=? where id=?",state,id);
        return update > 0;
    }

//    结账后，修改餐桌状态

    public boolean updateDiningTableToFree(int id,String state){
        int update = diningTableDAO.update("update diningTable set state=?,orderName ='',orderTel='' where id=?",state,id);
        return update>0;
    }


}














































