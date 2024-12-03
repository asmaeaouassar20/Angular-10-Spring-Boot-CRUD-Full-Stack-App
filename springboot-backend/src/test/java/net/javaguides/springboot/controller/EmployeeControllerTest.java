package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(EmployeeController.class)
// utilisée pour tester les contrôleurs
// Spring MVC en chargeant uniquement
// les composants liés au web (comme les contrôleurs)
// sans initialiser toute l'application Spring


class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    // la classe "MockMvc" permet de
    // simuler des requêtes HTTP
    // et de tester les contrôleurs
    // Spring MVC sans démarrer un serveur web

    @MockBean // cette annotation permet de
             // créer et d'injecter des objets
            // fictifs (mock) dans le contexte
           // de "test Spring", souvent utilisés
          // pour simuler des dépendances dans les tests
    private EmployeeRepository repository;


    // Test get all employees

    @Test
    void shouldReturnListOfEmployees() throws Exception {
        List<Employee> employees = List.of(
                new Employee("fatima", "afd", "fatma@gmail.com"),
                new Employee("yns", "mmch", "youyou@gmail.com")
            );
        Mockito.when(repository.findAll()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk()) // vérifier que le statut HTTP est 200
                .andExpect(view().name("employees")) // vérifier que la vue rendue est "employees"
                .andExpect(model().attributeExists("ListEmployees")) // Vérifie que l'attribut "employees" est présent dans le modèle
                .andExpect(model().attribute("ListEmployees", employees)); // vérifie que le modèle contient la liste attendue
        }




    // Test create employee
    /**
     * C'est une méthode qui utilise PostMapping
     * On doit donc configurer un test qui simule l'envoie d'un formulaire à l'URL de votre méthode
     */
    @Test
    void shouldCreateEmployeeAndRedirect() throws Exception{

        // Simuler un nouvel employé
        Employee employee=new Employee("ali","afd","alal@gmail.com");

        // Simuler l'enregistrement du nouvel employé
        Mockito.when(repository.save(Mockito.any(Employee.class))).thenReturn(employee);
        // POURQUOI Mockito.any(Employee.class)
        /* => pour spécifier que la méthode save() peut être appelée avec
            n'importe quel objet de type Employee
            ==> Cela signifie qu'on ne teste pas les arguments précis passés à la méthode
                Mais seulement qu'elle est appelée
        */

        // Effectuer un POST avec les données du formulaire
        mockMvc.perform(post("/api/v1/employees")
                .param("firstname","ali")
                .param("lastname","afd")
                .param("emailId","alal@gmail.com"))
                .andExpect(status().is3xxRedirection()) // Pour vérifier la redirection
                .andExpect(redirectedUrl("employees")); // vérifier que l'utilisateur est redirigé vers "employees"

        // Vérifier que la méthode save a été appelée
        Mockito.verify(repository,Mockito.times(1)).save(Mockito.any(Employee.class));

    }




    // Test get employee by id (used to show details about a specific employee)
    /**
     * ****** On écrit un test unitaire utilisant MockMvc pour simuler un appel HTTP GET ****
     * et vérifier que:
     *  - La méthode renvoie la vue correcte
     *  - Le modèle contient l'attribut attendu
     *  - La méthode du repository est correctement appelée
     */
    @Test
    void shouldReturnEmployeeUsingId() throws Exception{

        // 1. Créer un objet Employee simulé
        Employee employee=new Employee("Salma","Salmita","salama@gmail.com");
        employee.setId(1);

        // 2. Simuler le comportement du repository
        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(employee)); // Retourner l'employé si l'ID correspond

        // 3. Simuler une requête GET à notre URL
        mockMvc.perform(get("/api/v1//showDetails/1"))
                .andExpect(status().isOk()) // vérifier que le statut HTTP est 200
                .andExpect(view().name("showdetails")) // vérifier que la vue renvoyée est "showdetails"
                .andExpect(model().attributeExists("employee")) // Vérifier que le modèle contient "employee"
                .andExpect(model().attribute("employee",employee)); // Vérifier que l'attribut "employee" est celui attendu

        // 4. Vérifier que la méthode findById a été appelée une fois
        Mockito.verify(repository,Mockito.times(1)).findById(1L);
    }


    // Test update employee
    /**
     * 1. Simuler un appel GET à l'URL "/api/v1/update/{id}
     * 2. Vérifier la vue rendue par la méthode
     * 3. Vérifier que le modèle contient bien l'employé correspondant à l'ID
     * 4. S'assurer que le repository est appelé pour récupérer l'employé
     */
    @Test
    void shouldReturnUpdateEmployeeView() throws Exception{

        // 1. Préparer un employé simulé
        Employee employee=new Employee("souad","dodo","sdod@gmail.com");
        employee.setId(2);

        // 2. Simuler le comportement du repository
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(employee));

        // 3. Simuler une requête GET et vérifier les résultats
        mockMvc.perform(get("/api/v1/update/2"))
                .andExpect(status().isOk()) // Vérifier que le statut HTTP est 200
                .andExpect(view().name("updateEmployee")) // Vérifier que la vue rendue est "updateEmployee"
                .andExpect(model().attributeExists("employee")) // vérifier que l'attribut "employee" existe
                .andExpect(model().attribute("employee",employee)); // vérifier que l'attribut "employee" est correct

        // 4. Vérifier que le repository a été appelé une fois
        Mockito.verify(repository,Mockito.times(1)).findById(2L);
    }



    // Test update employee
    /**
     * 1. Simuler une requête POST pour mettre à jour un employé
     * 2. Vérifier que la méthode redirige correctement vers "/api/v1/employees"
     * 3. Vérifier que l'employé est bien sauvegardé via le repository
     */
    @Test
    void shouldUpdateEmployeeAndRedirect() throws Exception{

        // 1. Préparer un employé simulé
        Employee employee=new Employee("karima","didi","kdid@gmail.com");

        // 2. Simuler la sauvegarde de l'employé dans le repository
        Mockito.when(repository.save(Mockito.any(Employee.class))).thenReturn(employee);

        // 3. Simuler une requête POST
        mockMvc.perform(post("/api/v1/updateEmployee")
                        .flashAttr("employee",employee) // Simuler l'attribut envoyé via @ModelAttribute
                )
                .andExpect(status().is3xxRedirection()) // Vérifier qu'il y a une redirection
                .andExpect(redirectedUrl("/api/v1/employees")); // Vérifier que l'URL de redirection est correcte

        // 4. Vérifier que la méthode save a été appelée une fois
        Mockito.verify(repository,Mockito.times(1)).save(employee);

    }



    // Test delete Employee
    /**
     *  1. Simuler une requête HTTP GET pour supprimer un employé
     *  2. Vérifier que la suppression a été correctement effectuée
     *  3. Vérifier que la redirection est effectuée vers "/api/v1/employees"
     */
    @Test
    void shouldDeleteEmployee() throws Exception{
        // 1. Préparer un employé simulé
        Employee employee=new Employee("yns","sny","yyy@gmail.com");
        employee.setId(5);

        // 2. Simuler le comportement du repository por findById
        Mockito.when(repository.findById(5L)).thenReturn(Optional.of(employee));

        // 3. Simuler une requête GET pour supprimer l'employé
        mockMvc.perform(get("/api/v1/delete/5")) // Appeler la méthode via GET
                .andExpect(status().is3xxRedirection()) // vérifier qu'il y a une redirection
                .andExpect(redirectedUrl("/api/v1/employees")); // vérifier que la redirection est correcte

        // 4. Vérifier que "delete" a été appelé avec l'employé correct
        Mockito.verify(repository,Mockito.times(1)).delete(employee);
    }



    // Test Return form to add new Employee
    /**
     * -----  Objectifs de la méthode -----
     *  1. Créer un nouvel objet "Employee"
     *  2. Ajouter cet objet au modèle sous l'attribut "employee"
     *  3. Retourner la vue nommée "newemployee"
     *
     *  ----- Points à tester ---------
     *  1. Simuler la requête HTTP GET : cela simule un utilisateur qui visite cette URL pour afficher le formulaire
     *  2. Vérifier que la méthode retourne un statut HTTP 200 (ok) : pour s'assurer que la méthode fonctionne correctement
     *  3. Vérifier que la méthode retourne la vue "newemployee"
     *  4. Vérifier que l'attribut "employee" est ajouté au modèle : cet attribut est nécessaire pour que le formulaire puisse fonctionner corrctement
     *  5. Vérifier que l'objet associé à l'attribut "employee" est une instance de la classe "Employee"
     */
    @Test
    void shouldReturnFormToAddNewEmployee() throws Exception{

        mockMvc.perform(get("/api/v1/addNewEmployee")) // Simuler une reqête GET vers notre URL
                .andExpect(status().isOk()) // Vérifier que le statut de la réponse est HTTP 200 (ok)
                .andExpect(view().name("newemployee")) // Vérifier que la vue rendue est "newemployee"
                .andExpect(model().attributeExists("employee")) // Vérifier que le modèle contient l'attribut "employee"
                .andExpect(model().attribute("employee", Matchers.instanceOf(Employee.class))); // Vérifier que l'objet "employee" dans le modèle est une instance de Employee
    }
}




