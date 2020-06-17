/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.Conexion;
import dao.MunicipalidadJpaController;
import model.Municipalidad;

/**
 *
 * @author Ariel
 */
public class MunicipalidadController {

    //DAO
    private final MunicipalidadJpaController municipalidadDAO;

    //Model
    private static Municipalidad organismoInstanciaUnica = null;

    public MunicipalidadController() {
        //Inicializacion de DAO
        this.municipalidadDAO = new MunicipalidadJpaController(Conexion.getEmf());

        //Singleton Municipalidad
        this.organismoInstanciaUnica = LoginController.getInstanceMunicipalidad();
    }

    public static Municipalidad geMunicipalidadInstanciaUnica() {
        return organismoInstanciaUnica;
    }
    
   

}
