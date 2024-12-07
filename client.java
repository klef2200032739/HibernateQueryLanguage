import test.Customer;

import test.Invoice;
import util.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class Client {

	public static void main(String[] a) {
		SessionFactory sf = null;
		Session session = null;
		try {
			sf = util.HibernateUtil.getSessionFactory();
			session = sf.openSession();
			// Simple inner join HQL
			Query query = session.createQuery(
					"Select i.customerid, c.firstname,i.total from Invoice i ,Customer c where i.customerid=c.id and i.customerid<1000");
			ScrollableResults rs = query.scroll();
			while (rs.next()) {
				System.out.println(rs.get(0) + "  " + rs.get(1) + "  " + rs.get(2));
			}
			// Subquery HQL
			query = session.createQuery(
					"from Customer c where c.id in( select i.customerid from Invoice i where i.total<1000)");
			rs = query.scroll();
			while (rs.next()) {
				System.out.println(rs.get(0));
			}
			query = session.createQuery("select c.firstname,c.lastname from Customer c where c.id  between 0 and 10");
			rs = query.scroll();
			while (rs.next()) {
				System.out.println("First Name: " + rs.get(0) + " Last Name: " + rs.get(1));
			}
			query = session.getNamedQuery("simplelist");
			rs = query.scroll();
			while (rs.next()) {
				System.out.println("First Name: " + rs.get(0) + " Last Name: " + rs.get(1));
			}
			query = session.getNamedQuery("customlist");
			query.setInteger("id", 30);
			rs = query.scroll();
			while (rs.next()) {
				System.out.println("First Name: " + rs.get(0) + " Last Name: " + rs.get(1));
			}
		} catch (Exception e) {
			System.out.println(e + "Error with client block");
			session.close();
		}

	}
}
