package org.uv.tpcsw.practica01.dao;

import java.util.List;

public class DAOEmpleadoConcreto implements IDAOGeneral<EmpleadoPojo, String> {

    @Override
    public boolean save(EmpleadoPojo pojo) {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "insert into empleados (clave, nombre, direccion, telefono) values "
                + " ('" + pojo.getClave() + "','" + pojo.getNombre() + "','"
                + pojo.getDireccion() + "','" + pojo.getTelefono() + "')";
        return con.execute(sql);
    }

    @Override
    public boolean delete(EmpleadoPojo pojo) {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "DELETE FROM empleados WHERE clave = '" + pojo.getClave() + "'";
        return con.execute(sql);
    }

    @Override
    public boolean update(EmpleadoPojo pojo, String id) {
        ConexionDB con = ConexionDB.getInstance();
        String sql = "UPDATE empleados SET nombre = '" + pojo.getNombre()
                + "', direccion = '" + pojo.getDireccion()
                + "', telefono = '" + pojo.getTelefono()
                + "' WHERE clave = '" + id + "'";
        return con.execute(sql);
    }

    @Override
    public List<EmpleadoPojo> findAll() {
        return null;
    }

    @Override
    public EmpleadoPojo findById(String id) {
        return null;
    }

}
