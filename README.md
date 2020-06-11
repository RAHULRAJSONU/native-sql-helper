# native-sql-helper
Native SQL helper is a java library for convenient native SQL querying.
- (~8KB, no dependencies).

### Maven Dependency
```xml
<dependency>
    <groupId>io.github.rahulrajsonu</groupId>
    <artifactId>native-sql-helper</artifactId>
    <version>1.0</version>
</dependency>
```
### Gradle Dependency
```groovy
compile group: 'io.github.rahulrajsonu', name: 'native-sql-helper', version: '1.0'
```
### SBT Dependency
```scala
libraryDependencies += "io.github.rahulrajsonu" % "native-sql-helper" % "1.0"
```
### Example
You can use this utility to map your native SQL resultList to a List of Pojo.

```java
class Employee {
  String id;
  String firstName;
  String lastName;
  String email;
  String department;
  Date doj;
  boolean active;
}

class EmployeeRepository {
  @PersistenceContext
  private EntityManager em;
  
  public List<Employee> getActiveEmployeeList(){
    Query queryString = em.createNativeQuery("select firstName, lastName, email from employee where active=1");
    List<Object[]> resultList = queryString.getResultList();
    // build a list of columns (should be in same order as select query)
    String[] columns = {"firstName","lastName","email"};
    List<Employee> response = NativeSQLHelper
            .mapToModel(resultList, Employee.class, columns);
    return response;
  }

}
```
#### More interesting features to come...