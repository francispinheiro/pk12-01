/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.efransys.erp.infra.model;

import java.io.Serializable;

/**
 *
 * @author j r zielinski
 */
public interface BaseEntity extends Serializable{


    <T extends Serializable> T getId();

}
