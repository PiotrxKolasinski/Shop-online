package com.onlineshop.dao.cart;

import com.onlineshop.entity.Customer;
import com.onlineshop.entity.Cart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public CartDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Cart> getCartList(Customer customer) {

        Session session = sessionFactory.getCurrentSession();

        String sql = "from Cart where customer_id=:customerId and status=:status";

        Query<Cart> query = session.createQuery(sql, Cart.class);

        query.setParameter("customerId", customer.getId());

        query.setParameter("status", "Product in cart");

        List<Cart> carts = query.getResultList();

        return carts;
    }

    @Override
    public void deleteProductFromCart(int id) {
        Session session = sessionFactory.getCurrentSession();

        String sql = "delete from Cart where id=:id and status=:status";

        Query query = session.createQuery(sql);

        query.setParameter("id", id);

        query.setParameter("status", "Product in cart");

        query.executeUpdate();
    }

    @Override
    public void updateCart(Cart customerCart) {

        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(customerCart);
    }

    @Override
    public List<Cart> findCartByOrderId(int orderId) {

        String sql = "from Cart where order_id=:orderId";

        Session session = sessionFactory.getCurrentSession();

        Query<Cart> query = session.createQuery(sql, Cart.class);

        query.setParameter("orderId", orderId);

        List<Cart> carts = query.getResultList();

        return carts;
    }

    @Override
    public List<Cart> findCartByCustomerIdAndStatus(int customerId) {
        String sql = "from Cart where customer_id=:orderId and status!=:status";

        Session session = sessionFactory.getCurrentSession();

        Query<Cart> query = session.createQuery(sql, Cart.class);

        query.setParameter("orderId", customerId);

        query.setParameter("status", "Product in cart");

        List<Cart> carts = query.getResultList();

        return carts;
    }
}
