package com.example.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example.hibernate.demo.entity.Student;

public class QueryStudentDemo {

	public static void main(String[] args) {
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			List<Student> theStudents = session.createQuery("from Student").list();
			
			displayStudents(theStudents);
			 
			theStudents = session.createQuery("from Student s where s.lastName='Duck'").getResultList();
			
			System.out.println("\n\nStudents who have last name of Duck");
			displayStudents(theStudents);
			
			theStudents =
					session.createQuery("from Student s where s.lastName='Duck' OR s.firstName ='Daffy'").getResultList();
			System.out.println("\n\nStudents who have last name of Duck OR first name Daffy");
			displayStudents(theStudents);
			
			theStudents = session.createQuery("from Student s where s.email LIKE '%gmail.com'").getResultList();
			System.out.println("\n\nStudents whose email ends with gmail.com");
			displayStudents(theStudents);
			session.getTransaction().commit();
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
	}

	private static void displayStudents(List<Student> theStudents) {
		for (Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}
	}
}