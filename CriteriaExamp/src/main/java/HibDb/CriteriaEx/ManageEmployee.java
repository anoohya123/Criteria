package HibDb.CriteriaEx;



import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class ManageEmployee {
	
	SessionFactory factory;
public static void main(String[] args) {
	SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	Session session=factory.openSession();
	Transaction tx=session.beginTransaction();
	
	ManageEmployee me=new ManageEmployee();
	
	Integer empID1 = me.addEmployee("Zara", "Ali", 2000);
    Integer empID2 = me.addEmployee("Daisy", "Das", 5000);
    Integer empID3 = me.addEmployee("John", "Paul", 5000);
    Integer empID4 = me.addEmployee("Mohd", "Yasee", 3000);
    
    
		
		
		  me.listEmployees();
		  
		  
		  me.countEmployee();
		  
		  
		  
		  me.totalSalary();
		 
    	
	
	tx.commit();
	session.close();
}
public Integer addEmployee(String first_name , String last_name, int salary) {
	SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	Session session = factory.openSession();
    Transaction tx = null;
    Integer employeeID = null;
    
    try {
       tx = session.beginTransaction();
       Employee1 employee = new Employee1( first_name,last_name, salary);
       employeeID = (Integer) session.save(employee); 
       tx.commit();
    } catch (HibernateException e) {
       if (tx!=null) tx.rollback();
       e.printStackTrace(); 
    } finally {
       session.close(); 
    }
    return employeeID;
 }

public void totalSalary() {
	SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	Session session=factory.openSession();
	Transaction tx = null;
    
    try {
       tx = session.beginTransaction();
       Criteria cr = session.createCriteria(Employee1.class);

       // To get total salary.
       cr.setProjection(Projections.sum("salary"));
       List<Employee1> totalSalary = cr.list();

       System.out.println("Total Salary: " + totalSalary.get(0) );
       tx.commit();
    } catch (HibernateException e) {
       if (tx!=null) tx.rollback();
       e.printStackTrace(); 
    } finally {
       session.close();
    }
    }

public void countEmployee() {
	SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	Session session=factory.openSession();
	Transaction tx = null;
	try {
        tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Employee1.class);

        // To get total row count.
        cr.setProjection(Projections.rowCount());
        List<Employee1> rowCount = cr.list();

        System.out.println("Total Count: " + rowCount.get(0) );
        tx.commit();
     } catch (HibernateException e) {
        if (tx!=null) tx.rollback();
        e.printStackTrace(); 
     } finally {
        session.close(); 
     }
  }
@SuppressWarnings({ "deprecation", "unchecked" })
public void listEmployees() {
	SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

		Session session=factory.openSession();
		Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Criteria cr = session.createCriteria(Employee1.class);
	         // Add restriction.
	         cr.add(Restrictions.gt("salary", 2000));
	         List<Employee1> employees = cr.list();

	         for (Iterator<Employee1> iterator = employees.iterator(); iterator.hasNext();){
	            Employee1 employee = (Employee1) iterator.next(); 
	            System.out.print("First Name: " + employee.getFirst_name()); 
	            System.out.print("  Last Name: " + employee.getLast_name()); 
	            System.out.println("  Salary: " + employee.getSalary()); 
	         }
	         tx.commit();
	         session.close();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } 
	          
	      }
}


