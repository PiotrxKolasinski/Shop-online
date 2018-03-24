package com.onlineshop.dao.order;

import com.onlineshop.entity.Order;
import com.onlineshop.entity.Cart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public OrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrder(Order order) {

        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(order);
    }

    @Override
    public Set<Order> getAllUnfinishedOrders() {

        String sql = "from Cart where status!=:status1 and status!=:status2";

        Session session = sessionFactory.getCurrentSession();

        Query<Cart> query = session.createQuery(sql, Cart.class);

        query.setParameter("status1", "product in cart");

        query.setParameter("status2", "done");

        List<Cart> carts = query.getResultList();

        Set<Order> orders = new LinkedHashSet<>();

        for(Cart temp : carts){
            orders.add(temp.getOrder());
        }

        return orders;
    }

    @Override
    public Order getOrder(int id) {

        Session session = sessionFactory.getCurrentSession();

        Order order = session.get(Order.class, id);

        return order;
    }
}
