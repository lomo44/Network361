/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network361;

/**
 *
 * @author lizhuan1
 */
public abstract class ConnectionFactory {
    abstract Connection makeConnection();
    abstract Thread makeConnectionThread();
}
