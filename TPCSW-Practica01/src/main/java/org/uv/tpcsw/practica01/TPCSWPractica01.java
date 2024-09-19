package org.uv.tpcsw.practica01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uv.tpcsw.practica01.dao.ConexionDB;
import org.uv.tpcsw.practica01.dao.EmpleadoPojo;
import org.uv.tpcsw.practica01.dao.TransactionDB;

public class TPCSWPractica01 {

    public static void main(String[] args) {

        ConexionDB conexion = ConexionDB.getInstance();
        EmpleadoPojo pojo = new EmpleadoPojo();
        pojo.setClave("01");
        pojo.setNombre("Gabriel");
        pojo.setDireccion("Av.1");
        pojo.setTelefono("7777");

        TransactionDB<EmpleadoPojo> transaction = new TransactionDB<EmpleadoPojo>(pojo) {
            @Override
            public boolean execute(Connection con) {
                try {
                    System.out.println("Se llena el statement");
                    String sql = "insert into empreados (clave,nombre,direccion,telefono) values"
                            + " (?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, pojo.getClave());
                    pst.setString(2, pojo.getNombre());
                    pst.setString(3, pojo.getDireccion());
                    pst.setString(4, pojo.getTelefono());
                    return pst.execute();
                } catch (SQLException ex) {
                    Logger.getLogger(TPCSWPractica01.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }

            }
        };
        conexion.execute(transaction);

//        MensajeController controller = new MensajeController();
//        SaludoConcreto saludo = new SaludoConcreto();
//        DespedidaConcreto despedida = new DespedidaConcreto();
//        
//        controller.mostrar(saludo);
//        
//        DAOEmpleadoConcreto dao = new DAOEmpleadoConcreto();
//
//        EmpleadoPojo empleado = new EmpleadoPojo();
//        empleado.setClave("11");
//        empleado.setNombre("Isaias");
//        empleado.setDireccion("Av. 2");
//        empleado.setTelefono("2727777777");
//
//        dao.save(empleado);
//        
        /**
         * Singleton s1 = Singleton.getInstance(); Singleton s2 =
         * Singleton.getInstance(); Logger.getLogger(Despedida.class.getName()).
         * log(Level.INFO, s1);
         *
         * // Singleton s1 = new Singleton(); // Singleton s2 = new
         * Singleton(); // System.out.println(s1); // System.out.println(s2);
         *
         * // IMensaje msgv = new Saludo2(); // msgv.imprimir(); // // msgv=new
         * Despedida2(); // msgv.imprimir(); // // //Lamda // msgv=() -> { //
         * System.out.println("Otro mensaje..."); // }; // msgv.imprimir();
         *
         * // Mensaje msgV=null; // // msgV=new Saludo(); // msgV.Imprimir();
         * // // msgV=new Despedida(); // msgV.Imprimir(); // // msgV=new
         * Mensaje(){ // @Override // protected void msg() { //
         * System.out.println("Otro mensaje"); // } // }; // msgV.Imprimir();
         *
         * // MensajeConcreto msg=null; // msg = new MensajeConcreto("Otro..");
         * // msg.imprimir(); // System.out.println("Hello World!");
         *
         */
    }
}
