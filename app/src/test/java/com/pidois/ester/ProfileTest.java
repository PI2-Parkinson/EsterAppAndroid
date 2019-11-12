package com.pidois.ester;

import com.pidois.ester.Models.Profile;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileTest {
    @Test
    public void shouldValidateUserName(){
        Profile user = new Profile();
        user.setName("João da Silva");
        assertEquals("João da Silva", user.getName());
    }

    @Test
    public void shouldValidateUserEmail(){
        Profile user = new Profile();
        user.setEmail("teste@teste.com");
        assertEquals("teste@teste.com", user.getEmail());
    }

    @Test
    public void shouldValidateUserBirthday(){
        Profile user = new Profile();
        user.setBirthday("01/01/2000");
        assertEquals("01/01/2000", user.getBirthday());
    }
}
