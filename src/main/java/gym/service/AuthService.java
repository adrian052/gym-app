package gym.service;

import gym.entities.User;

import javax.naming.AuthenticationException;

public interface AuthService {
    void authorize(Credentials credentials, User userOwner) throws AuthenticationException;

    void authenticate(Credentials credentials) throws AuthenticationException;

    void authenticationFlow(Credentials credentials, User userOwner) throws AuthenticationException;
}
