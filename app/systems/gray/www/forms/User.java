package systems.gray.www.forms;

import play.data.validation.Constraints.Required;

public class User {
    
    @Required
    private String username;
    
    @Required
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
