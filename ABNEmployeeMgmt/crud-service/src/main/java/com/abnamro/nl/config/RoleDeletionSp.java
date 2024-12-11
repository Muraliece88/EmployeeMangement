package com.abnamro.nl.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Stored procedure to delete all employees and assign proj to default emp
 */

public class RoleDeletionSp {
    public static void deleteRoleSp(Connection conn, Long roleId, Long defaultEmpId) throws SQLException {
        try (PreparedStatement deleteProjects = conn.prepareStatement(
                "UPDATE project SET employee_id = ? WHERE employee_id IN (SELECT id FROM employee WHERE role_id = (SELECT id FROM role WHERE id = ?))");
             PreparedStatement deleteEmployeesStmt = conn.prepareStatement(
                     "DELETE FROM employee WHERE role_id = (SELECT id FROM role WHERE id = ?)");
             PreparedStatement deleteRoleStmt = conn.prepareStatement(
                     "DELETE FROM role WHERE id = ?")) {

            deleteProjects.setLong(1, defaultEmpId);
            deleteProjects.setLong(2, roleId);
            deleteProjects.executeUpdate();

            deleteEmployeesStmt.setLong(1, roleId);
            deleteEmployeesStmt.executeUpdate();

            deleteRoleStmt.setLong(1, roleId);
            deleteRoleStmt.executeUpdate();
        }
    }
}
