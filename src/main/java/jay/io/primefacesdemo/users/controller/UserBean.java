package jay.io.primefacesdemo.users.controller;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jay.io.primefacesdemo.users.model.User;
import jay.io.primefacesdemo.users.repository.UserDAO;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserDAO userDAO;

    private List<User> users;
    private User selectedUser;
    private String searchKeyword; // Add search keyword property

    @PostConstruct
    public void init() {
        loadUsers();
    }

    // Load all users
    public void loadUsers() {
        users = userDAO.findAll(); // No need to stream().toList() kung List na ang return
    }

    public void prepareNewUser() {
        selectedUser = new User();
        System.out.println("Preparing new user...");
    }

    // Search users based on the searchKeyword
//    public void searchUsers() {
//        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
//            users = userDAO.searchByUsername(searchKeyword); // Call DAO method to search by username
//        } else {
//            loadUsers();  // Load all users if no search keyword is entered
//        }
//    }

    // Search users based on the searchKeyword
    public void searchUsers() {
        System.out.println("[DEBUG] searchUsers() called.");
        System.out.println("[DEBUG] searchKeyword: " + searchKeyword);

        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            System.out.println("[DEBUG] Searching for users with keyword: " + searchKeyword);
            users = userDAO.searchByUsername(searchKeyword); // Call DAO method to search by username
            System.out.println("[DEBUG] Users found: " + (users != null ? users.size() : "null"));
        } else {
            System.out.println("[DEBUG] Empty keyword, loading all users.");
            loadUsers();  // Load all users if no search keyword is entered
            System.out.println("[DEBUG] Total users loaded: " + (users != null ? users.size() : "null"));
        }
    }


    public void saveUser() {
        try {
            boolean isNew = (selectedUser.getId() == null);

            if (isNew) {
                userDAO.create(selectedUser);
                addMessage(FacesMessage.SEVERITY_INFO, "User Created", "User created successfully.");
            } else {
                userDAO.update(selectedUser);
                addMessage(FacesMessage.SEVERITY_INFO, "User Updated", "User updated successfully.");
            }

            loadUsers();
            selectedUser = new User();

        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save user: " + e.getMessage());
        }
    }

    public void deleteUser(User user) {
        if (user == null || user.getId() == null || user.getId() <= 0) {
            addMessage(FacesMessage.SEVERITY_WARN, "Warning", "Invalid user selected.");
            return;
        }

        try {
            userDAO.delete(user.getId());
            loadUsers();
            addMessage(FacesMessage.SEVERITY_INFO, "Deleted", "User deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete user: " + e.getMessage());
        }
    }

    public void editUser() {
        System.out.println("Edit triggered: " + selectedUser);
        if (selectedUser != null && selectedUser.getId() != null) {
            selectedUser = userDAO.find(selectedUser.getId());
        }
    }



    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters and Setters
    public List<User> getUsers() {
        return users;
    }

    public User getSelectedUser() {
        if (selectedUser == null) {
            selectedUser = new User(); // para hindi mag-null pointer sa UI
        }
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}
