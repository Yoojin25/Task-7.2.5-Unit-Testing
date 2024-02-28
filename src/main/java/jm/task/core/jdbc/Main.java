package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    private static UserServiceImpl userService = new UserServiceImpl();
    public static void main(String[] args) {
//        Util util = new Util();
//        util.getConnection();

        userService.createUsersTable();

        userService.saveUser("Bree", "Van de Kamp", (byte) 42);
        userService.saveUser("Lynette", "Scavo", (byte) 38);
        userService.saveUser("Susan", "Mayer", (byte) 37);
        userService.saveUser("Gabrielle", "Solis", (byte) 28);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
