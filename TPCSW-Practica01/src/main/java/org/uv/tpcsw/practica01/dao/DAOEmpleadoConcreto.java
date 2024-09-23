package org.uv.tpcsw.practica01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOEmpleadoConcreto implements IDAOGeneral<EmpleadoPojo, String> {

    @Override
    public boolean save(EmpleadoPojo pojo) {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "insert into empleados (clave, nombre, direccion, telefono) values (?, ?, ?, ?)";
        TransactionDB<EmpleadoPojo> transaction = new TransactionDB<EmpleadoPojo>(pojo) {
            @Override
            public boolean execute(Connection connection) {
                try {
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, pojo.getClave());
                    pst.setString(2, pojo.getNombre());
                    pst.setString(3, pojo.getDireccion());
                    pst.setString(4, pojo.getTelefono());
                    return pst.executeUpdate() > 0;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleadoConcreto.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        return con.execute(transaction);
    }

    @Override
    public boolean delete(EmpleadoPojo pojo) {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "DELETE FROM empleados WHERE clave = ?";
        TransactionDB<EmpleadoPojo> transaction = new TransactionDB<EmpleadoPojo>(pojo) {
            @Override
            public boolean execute(Connection connection) {
                try {
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, pojo.getClave());
                    return pst.executeUpdate() > 0;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleadoConcreto.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        return con.execute(transaction);
    }

    @Override
    public boolean update(EmpleadoPojo pojo, String id) {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "UPDATE empleados SET nombre = ?, direccion = ?, telefono = ? WHERE clave = ?";
        TransactionDB<EmpleadoPojo> transaction = new TransactionDB<EmpleadoPojo>(pojo) {
            @Override
            public boolean execute(Connection connection) {
                try {
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, pojo.getNombre());
                    pst.setString(2, pojo.getDireccion());
                    pst.setString(3, pojo.getTelefono());
                    pst.setString(4, id);
                    return pst.executeUpdate() > 0;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleadoConcreto.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        return con.execute(transaction);
    }

    @Override
    public List<EmpleadoPojo> findAll() {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "SELECT * FROM empleados";
        List<EmpleadoPojo> empleados = new ArrayList<>();

        TransactionDB<List<EmpleadoPojo>> transaction = new TransactionDB<List<EmpleadoPojo>>(empleados) {
            @Override
            public boolean execute(Connection connection) {
                try {
                    PreparedStatement pst = connection.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        EmpleadoPojo pojo = new EmpleadoPojo();
                        pojo.setClave(rs.getString("clave"));
                        pojo.setNombre(rs.getString("nombre"));
                        pojo.setDireccion(rs.getString("direccion"));
                        pojo.setTelefono(rs.getString("telefono"));
                        empleados.add(pojo);
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleadoConcreto.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        con.execute(transaction);
        return empleados;
    }

    @Override
    public EmpleadoPojo findById(String id) {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "SELECT * FROM empleados WHERE clave = ?";
        EmpleadoPojo pojo = new EmpleadoPojo();

        TransactionDB<EmpleadoPojo> transaction = new TransactionDB<EmpleadoPojo>(pojo) {
            @Override
            public boolean execute(Connection connection) {
                try {
                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        pojo.setClave(rs.getString("clave"));
                        pojo.setNombre(rs.getString("nombre"));
                        pojo.setDireccion(rs.getString("direccion"));
                        pojo.setTelefono(rs.getString("telefono"));
                    } else {
                        return false; // No se encontró el empleado con esa clave
                    }
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(DAOEmpleadoConcreto.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        };
        con.execute(transaction);
        return pojo;
    }
}