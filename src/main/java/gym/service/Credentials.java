package gym.service;

public record Credentials(String username, String password) {
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
