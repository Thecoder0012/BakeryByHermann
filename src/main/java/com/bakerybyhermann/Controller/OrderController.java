package com.bakerybyhermann.Controller;

import com.bakerybyhermann.Model.Cashier;
import com.bakerybyhermann.Model.Department;
import com.bakerybyhermann.Model.Order;
import com.bakerybyhermann.Model.ProductList;
import com.bakerybyhermann.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    CashierService cashierService;
    @Autowired
    ProductService productService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    CustomerService customerService;

    @GetMapping("/show-orders")
    public String showOrders (Model model, Model model2){

        List<Order> orders = orderService.fetchAll();

        List<Department> departments = departmentService.fetchAll();
        int departmentsCount = departments.size();

        List<List<Order>> listsList = new ArrayList<>();
        for (int i = 0; i < departmentsCount; i++) {
            listsList.add(new ArrayList<Order>());
        }

        for (int i = 0; i < listsList.size(); i++) {
            for (int j = 0; j < orders.size(); j++) {
                if (orders.get(j).getPickupLocation().getDepartmentId()
                        ==departments.get(i).getDepartmentId()){
                    //System.out.println("Order "+j+" is stored in list "+i);
                    listsList.get(i).add(orders.get(j));
                }
            }
        }

        model.addAttribute("ordersVirum", listsList.get(0));
        model2.addAttribute("ordersNaerum", listsList.get(1));
        return "order/show-orders";
    }

    //SKal vi ha Lists som class fields?
    //Hvis ja, så her eller i service
    //alle loops osv. skal de være i controller eller service?


    @GetMapping("/new-order")
    public String getNewOrder(Model model, Model model2, Model model3, Model model4){
        model.addAttribute("cashierEmployee",cashierService.fetchAll());
        model2.addAttribute("productsList", productService.fetchAll());
        model3.addAttribute("departmentList", departmentService.fetchAll());
        model4.addAttribute("customerList", customerService.fetchAll());
        return "order/new-order";
    }

    @PostMapping("/new-order")
    public String newOrder (@ModelAttribute Order o, Model model){

        model.addAttribute("productsList", productService.fetchAll());

        for (int i = 0; i < departmentService.fetchAll().size(); i++) {
            if (departmentService.fetchAll().get(i).getShortName().equalsIgnoreCase(o.getOrderLocation().getShortName())){
                o.setOrderLocation(departmentService.fetchAll().get(i));
            }
        }
        for (int i = 0; i < departmentService.fetchAll().size(); i++) {
            if (departmentService.fetchAll().get(i).getShortName().equalsIgnoreCase(o.getPickupLocation().getShortName())){
                o.setPickupLocation(departmentService.fetchAll().get(i));
            }
        }

        for (int i = 0; i < cashierService.fetchAll().size(); i++) {
            if (cashierService.fetchAll().get(i).getFirstName().equalsIgnoreCase(o.getCashier().getFirstName())){
                o.setCashier(cashierService.fetchAll().get(i));
            }
        }

        String[] toGetFirstName = o.getCustomer().getFirstName().split(" ");
        System.out.println(toGetFirstName[0]);
        System.out.println(toGetFirstName[1]);
        System.out.println(toGetFirstName[2]);
        int customerPhone = Integer.valueOf(toGetFirstName[2]);
        System.out.println(customerPhone);

        for (int i = 0; i < customerService.fetchAll().size(); i++) {
            System.out.println(customerService.fetchAll().get(i).getCustomerId());
            if (customerService.fetchAll().get(i).getFirstName().equalsIgnoreCase(toGetFirstName[0]) &&
            customerService.fetchAll().get(i).getLastName().equalsIgnoreCase(toGetFirstName[1]) &&
            customerService.fetchAll().get(i).getPhoneNumber()==customerPhone){
                o.setCustomer(customerService.fetchAll().get(i));
            }
        }

        orderService.addNew(o);
        System.out.println("Redirecting...");
        return "order/new-orderlist";
    }

    @PostMapping("/new-orderlist")
    public String addToList(@ModelAttribute ProductList p, Model model){
        //System.out.println(p.getProduct().getProductName());

        model.addAttribute("productsList", productService.fetchAll());
        String[] toProductList = p.getProduct().getProductName().split(" ");
        for (int i = 0; i < productService.fetchAll().size(); i++) {
            if (productService.fetchAll().get(i).getProductName().equalsIgnoreCase(toProductList[0])){
                p.setProduct(productService.fetchAll().get(i));
            }
        }
        orderService.addToList(p);
        //lav evt en else statement
        return "order/new-orderlist";
    }

    @GetMapping("/show-orders/{orderId}")
    public String viewOne(@PathVariable("orderId") int id, Model model){
        Order order = orderService.findById(id);
        List<ProductList> productLists = order.getProductList();
        int totalPrice = 0;
        for (int i = 0; i < productLists.size(); i++) {
            int quantity = productLists.get(i).getQuantity();
            int productPrice = productLists.get(i).getProduct().getPrice();
            totalPrice = totalPrice + productPrice * quantity;
        }
        order.setTotalPrice(totalPrice);

        model.addAttribute("oneOrder", order);
        return "order/one-order";
    }
}
