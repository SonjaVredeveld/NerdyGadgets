/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs2;

import java.io.IOException;

/**
 *
 * @author Niek J Nijland
 */
public class test2 {
    public static void main(String[] args) throws IOException {
        User u1 = new User("test","test");
        new PlannerScreen(u1);
    }
}
