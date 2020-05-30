package com.rr.opensource.nativesqlhelper;

import static org.junit.jupiter.api.Assertions.*;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class NativeSQLHelperTest {

  @Test
  public void testBuildStringForInQuery() {
    List<String> parts = Arrays.asList("WISCHE","WSD","SERT");
    String query = MessageFormat.format("Select * from master where part in {0}",
        NativeSQLHelper.buildStringForInQuery(parts));
    String finalQr = "Select * from master where part in ('WISCHE','WSD','SERT')";
    assertEquals(finalQr,query);
  }

  @Test
  public void testMapToModel() throws InstantiationException, IllegalAccessException {
    Object[] rs = {"rahul","raj",112,"sample"};
    Object[] rs1 = {"rangan","basu",113,"sample"};
    Object[] rs2 = {"rohan","sharma",114,"sample"};

    String[] columnInOrder = {"firstName","lastName","id","company"};
    List<Employee> employees = NativeSQLHelper
        .mapToModel(Arrays.asList(rs,rs1,rs2), Employee.class, columnInOrder);
    assertEquals("rahul",employees.get(0).firstName);
    assertEquals("raj",employees.get(0).lastName);
    assertEquals(Integer.valueOf(112),employees.get(0).id);
    assertEquals("sample",employees.get(0).company);
  }

  static class Employee{
    Integer id;
    String firstName;
    String lastName;
    String company;

    public Employee() {
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getCompany() {
      return company;
    }

    public void setCompany(String company) {
      this.company = company;
    }

    @Override
    public String toString() {
      return "Employee{" +
          "id=" + id +
          ", firstName='" + firstName + '\'' +
          ", lastName='" + lastName + '\'' +
          ", company='" + company + '\'' +
          '}';
    }
  }
}