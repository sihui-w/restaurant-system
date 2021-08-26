package mhl.service;

import mhl.dao.MenuDAO;
import mhl.domain.Menu;

import java.util.List;

public class MenuService {

    private MenuDAO menuDAO = new MenuDAO();

    public List<Menu>list(){
       return menuDAO.queryMulti("select * from menu", Menu.class);


    }

//    根据id，返回menu对象
    public Menu getMenuById(int id){
        return menuDAO.querySingle("select * from menu where id = ?",Menu.class,id);
    }


}
























































