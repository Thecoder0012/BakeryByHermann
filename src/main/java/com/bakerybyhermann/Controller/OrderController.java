package com.bakerybyhermann.Controller;

import com.bakerybyhermann.Model.*;
import com.bakerybyhermann.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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

    //This method is resposible for displaying a view of all the orders in our program
    //It seperates the orders in columns of where the orders should be delivered to
    @GetMapping("/show-orders")
    public String showOrders (Model model){

        int[] departmentIds = departmentService.departmentIds();
        //This is an int Array containing every departments unique id number.

        int departmentsCount = departmentIds.length;
        //This represent the amount of departments at our disposal.

        ArrayList<ArrayList<Order>> ordersSorted =
                orderService.fetchAllSorted(departmentsCount,departmentIds);
        //This is an ArrayList, contains ArrayLists, representing each department we have.
        //In each nested-ArrayList, it contains objects of an Order.

        model.addAttribute("ordersVirum", ordersSorted.get(0));
        model.addAttribute("ordersNaerum", ordersSorted.get(1));
        model.addAttribute("ordersjaegersborg", ordersSorted.get(2));
        return "order/show-orders";
        //vi har brugt ArrayLists fordi...
    }

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

        //Denne her bruger vi til næste view, hvor vi vælger varer
        model.addAttribute("productsList", productService.fetchAll());

        ArrayList<Department> departments = (ArrayList<Department>) departmentService.fetchAll();

        //Set order-location
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            if (department.getShortName().equalsIgnoreCase(o.getOrderLocation().getShortName())){
                o.setOrderLocation(department);
            }
        }
        //Set pick-up-location
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            if (department.getShortName().equalsIgnoreCase(o.getPickupLocation().getShortName())){
                o.setPickupLocation(department);
            }
        }

        ArrayList<Cashier> cashiers = (ArrayList<Cashier>) cashierService.fetchAll();

        //Set Cashier
        for (int i = 0; i < cashiers.size(); i++) {
            if (cashiers.get(i).getFirstName().equalsIgnoreCase(o.getCashier().getFirstName())){
                o.setCashier(cashiers.get(i));
            }
        }

        ArrayList<Customer> customers = (ArrayList<Customer>) customerService.fetchAll();

        //Set Customer
        String[] toGetFirstName = o.getCustomer().getFirstName().split(" ");
        int customerPhone = Integer.valueOf(toGetFirstName[2]);

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getFirstName().equalsIgnoreCase(toGetFirstName[0]) &&
                    customers.get(i).getLastName().equalsIgnoreCase(toGetFirstName[1]) &&
                    customers.get(i).getPhoneNumber()==customerPhone){
                o.setCustomer(customers.get(i));
            }
        }

        orderService.addNew(o);
        return "order/new-orderlist";
    }

    @PostMapping("/new-orderlist")
    public String addToList(@ModelAttribute ProductList p, Model model){

        model.addAttribute("productsList", productService.fetchAll());
        String[] toProductList = p.getProduct().getProductName().split(" ");
        for (int i = 0; i < productService.fetchAll().size(); i++) {
            if (productService.fetchAll().get(i).getProductName().equalsIgnoreCase(toProductList[0])){
                p.setProduct(productService.fetchAll().get(i));
            }
        }
        orderService.addToList(p);

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


    @GetMapping("/update-order/{id}")
    public String updateOrder (@PathVariable ("id") int id, Model model, Model model2, Model model3, Model model4){
        model.addAttribute("order", orderService.findById(id));
        model2.addAttribute("cashierEmployee",cashierService.fetchAll());
        model3.addAttribute("departmentList", departmentService.fetchAll());
        model4.addAttribute("customerList", customerService.fetchAll());
        return "order/update-order";
    }

    @PostMapping("/update-order")
    public String updateOrder (@ModelAttribute Order o){
        ArrayList<Department> departments = (ArrayList<Department>) departmentService.fetchAll();
        //Set pick-up-location
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            if (department.getShortName().equalsIgnoreCase(o.getPickupLocation().getShortName())){
                o.setPickupLocation(department);
            }
        }
        orderService.updateById(o.getOrderId(), o);
        return "redirect:/";
    }


   @GetMapping("/one-order/{orderId}")
    public String archiveOrder (@PathVariable ("orderId") int orderId, Model model){
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);

       List<ProductList> productLists = order.getProductList();
       int totalPrice = 0;
       for (int i = 0; i < productLists.size(); i++) {
           int quantity = productLists.get(i).getQuantity();
           int productPrice = productLists.get(i).getProduct().getPrice();
           totalPrice = totalPrice + productPrice * quantity;
       }
       order.setTotalPrice(totalPrice);


        orderService.archiveOrder(orderId, order);
        return "redirect:/show-orders";
    }

    @GetMapping("/show-archived")
    public String showArchived (Model model){
        ArrayList<Order> archivedOrders = (ArrayList<Order>)  orderService.fetchArchived();
        model.addAttribute("archivedList", archivedOrders );
        return "order/show-archived";
    }



    @GetMapping("/order/{orderId}")
    public String cancelOrder (@PathVariable ("orderId") int orderId){
        orderService.deleteById(orderId);
        return "redirect:/show-orders";
    }
}
