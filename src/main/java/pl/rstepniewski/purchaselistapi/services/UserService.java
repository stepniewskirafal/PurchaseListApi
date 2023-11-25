package pl.rstepniewski.purchaselistapi.services;

import pl.rstepniewski.purchaselistapi.domain.User;
import pl.rstepniewski.purchaselistapi.exception.EtAuthException;

import java.util.Map;

public interface UserService {
    User validateUser(User user) throws EtAuthException;
    User registerUser(User user) throws EtAuthException;
    User processUserRequestBody(Map<String, Object> userMap);
}
