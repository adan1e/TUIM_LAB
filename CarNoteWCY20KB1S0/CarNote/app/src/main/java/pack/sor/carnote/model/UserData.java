package pack.sor.carnote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model danych usera
 */

public class UserData implements Serializable
{
    /**
     * Model usera
     */
    @JsonProperty("login")
    private String login;
    /**
     * Login usera
     */
    @JsonProperty("password")
    private String password;
    /**
     * Haslo usera
     */

    public UserData(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean validatePassword(String login, String password){
        return (this.login == login && this.password == password);
    }
    @Override
    public String toString()
    {
        return login + " " + password;
    }
}
