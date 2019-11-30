package com.sda.MyShoppingList.entities.order;

import com.sda.MyShoppingList.abstractclasses.AbstractController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/command")
public class OrderController extends AbstractController<Long, Order, OrderRepository, OrderService> {

    public OrderController(OrderService service) {
        super(service);
    }
}
