package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@DataJpaTest  // Annotation pour les tests de la couche JPA
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;




    @Test
    void shouldSaveAndFindEmployeeById(){

        // 1. Préparer un objet Employee
        Employee employee=new Employee("jamila","benben","jbb@gmail.com");

        // 2. Sauvegarder l'employé dans la base
        Employee savedEmployee=employeeRepository.save(employee);

        // 3. Rechercher l'employé par son ID
        Optional<Employee> retrievedEmployee=employeeRepository.findById(savedEmployee.getId());

        // 4. Vérifier que les données correspondent
        assertTrue(retrievedEmployee.isPresent());
        assertEquals("jamila",retrievedEmployee.get().getFirstName());
        assertEquals("benben",retrievedEmployee.get().getLastName());

    }


    @Test
    void shouldFindEmployeeByLastName(){
        // 1. Ajouter des employés
        Employee emp1=new Employee("mina","mimi","mmm@gmail.com");
        Employee emp2=new Employee("sihame","sisi","shsh@gmail.com");
        employeeRepository.saveAll(List.of(emp1,emp2));

        // 2. Rechercher les employés par nom de famille
        List<Employee> employees=employeeRepository.findByLastName("mimi");

        // 3. Vérifier le résultat
        assertEquals(1,employees.size());
        assertEquals("mina",employees.get(0).getFirstName());
    }


    @Test
    void testSaveAndFindBy(){
        Employee employee=new Employee("Alice","Cena","abcd@gmail.com");

        // Sauvegarde dans la base de données
        employeeRepository.save(employee);


        Employee retrievedEmployee=employeeRepository.findById(employee.getId()).orElse(null);

        // Vérifier si l'ID est généré et que l'employé est bien récupéré
        assertThat(retrievedEmployee).isNotNull();
        assertThat(retrievedEmployee.getLastName()).isEqualTo("Cena");

    }


    @Test
    void testFindByEmail(){
        Employee employee1=new Employee("Bob","Alex","BaBa@gmail.com");
        Employee employee2=new Employee("Sali","ABCD","ABCD@gmail.com");

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Recherche par email
        List<Employee> employeesFound=employeeRepository.findByEmailId("BaBa@gmail.com");

        // Vérifications
        assertThat(employeesFound).hasSize(1); // nombre des employés quin ont l'email : "BaBa@gmail.com"
        assertThat(employeesFound.get(0).getFirstName()).isEqualTo("Bob");
    }


    @Test
    void testUpdatePatient(){
        Employee employee=new Employee("amine","adnane","afd@gmail.com");

        // Sauvegarde initiale
        employeeRepository.save(employee);

        // Mise à jour de lastname
        employee.setLastName("afd");
        employeeRepository.save(employee);

        // Vérification de la mise à jour
        Employee updatedEmployee=employeeRepository.findById(employee.getId()).orElse(null);
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getLastName()).isEqualTo("afd");
    }


    @Test
    void testDeleteEmployee(){
        Employee employee=new Employee("asmae","faracha","usa@gmail.com");

        employeeRepository.save(employee);

        // Suppression
        employeeRepository.delete(employee);

        // Vérifier si l'entité a été supprimée
        Employee deletedEmployee=employeeRepository.findById(employee.getId()).orElse(null);

        assertThat(deletedEmployee).isNull();

    }
}



