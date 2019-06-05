package neqsim.thermodynamicOperations.util.example;

import neqsim.thermo.system.SystemInterface;
import neqsim.thermo.system.SystemSrkCPAstatoil;
import neqsim.thermo.system.SystemSrkEos;
import neqsim.thermodynamicOperations.ThermodynamicOperations;
import org.apache.log4j.Logger;

/*
 * PhaseEnvelope.java
 *
 * Created on 27. september 2001, 10:21
 * Updated on May 2019 by Nefeli
 */
/*
 * @author esol
 * @version
 */
public class PhaseEnvelope {

    private static final long serialVersionUID = 1000;
    static Logger logger = Logger.getLogger(PhaseEnvelope.class);

    /*
     * Creates new PhaseEnvelope
     */
    public PhaseEnvelope() {
    }

    public static void main(String args[]) {
        
        //String[] components=neqsim.util.database.NeqSimDataBase.getComponentNames();
        
        SystemInterface testSystem = new SystemSrkEos(180, 4.00);

        ThermodynamicOperations testOps = new ThermodynamicOperations(testSystem);
        
        int sstms= 10;
    
        System.out.println(sstms);
        
        if  (sstms==1) {            
        /*TEST SYSTEM 1        */         
        testSystem.addComponent("methane", 90.465);                             
        testSystem.addComponent("ethane", 5.58);                                
        testSystem.addComponent("propane", 0.1736);
        testSystem.addComponent("i-butane", 0.008);
        testSystem.addComponent("n-pentane", 1.033);
        testSystem.addComponent("n-heptane", 1.033);
        
        }else if (sstms==2) {
        /* TEST SYSTEM 2    */
        testSystem.addComponent("methane", 60.465);
        testSystem.addComponent("ethane", 5.58);
        testSystem.addComponent("propane", 0.1736);
        testSystem.addComponent("i-butane", 0.008);
        testSystem.addComponent("n-pentane", 10.033);
        testSystem.addComponent("n-heptane", 10.033);
        
        }else if  (sstms==3) {
        /* TEST SYSTEM 3     */        
        testSystem.addComponent("methane", 80);
        testSystem.addComponent("ethane", 12);
        testSystem.addComponent("propane", 6);
        testSystem.addComponent("i-butane", 3);
        testSystem.addComponent("n-pentane", 2);
        testSystem.addComponent("n-heptane", 1);          
  
        }else if  (sstms==4) {            
        /* TEST SYSTEM 4     */
        testSystem.addComponent("methane", 60);
        testSystem.addComponent("ethane", 12);
        testSystem.addComponent("propane", 6);
        testSystem.addComponent("i-butane", 3);
        testSystem.addComponent("n-pentane", 2);
        testSystem.addComponent("n-heptane", 1);          
        
        }else if  (sstms==5) {    
        /* TEST SYSTEM 5        */
        testSystem.addComponent("methane", 60);
        testSystem.addComponent("ethane", 12);
        testSystem.addComponent("propane", 6);
        testSystem.addComponent("i-butane", 3);
        testSystem.addComponent("n-pentane", 20);
        testSystem.addComponent("n-heptane", 10); 
        
        }else if  (sstms==6) {    
        /* TEST SYSTEM 6      */
        testSystem.addComponent("methane", 10);
        testSystem.addComponent("ethane", 12);
        testSystem.addComponent("propane", 6);
        testSystem.addComponent("i-butane", 3);
        testSystem.addComponent("n-pentane", 20);
        testSystem.addComponent("n-heptane", 10);          
        
        }else if  (sstms==7) {    
        /* TEST SYSTEM 7     */
        testSystem.addComponent("methane", 200);
        testSystem.addComponent("ethane", 20);
        testSystem.addComponent("propane", 6);
        testSystem.addComponent("i-butane", 3);
        testSystem.addComponent("n-pentane", 20);        

        }else if  (sstms==8) {    
        /* TEST SYSTEM 8     */
        testSystem.addComponent("methane", 80);
        testSystem.addComponent("ethane", 15);
        testSystem.addComponent("n-octane", 5);        

        }else if  (sstms==9) {    
        /* TEST SYSTEM 9      */
        testSystem.addComponent("methane", 80);
        testSystem.addComponent("ethane", 15);
        testSystem.addComponent("n-octane", 5);
        testSystem.addComponent("nC10", 5);
        testSystem.addComponent("nC16", 5);        
        
        }else if  (sstms==10) {    
        /* TEST SYSTEM 10 : typical rich gas Even   */
        testSystem.addComponent("CO2", 2.1);
        testSystem.addComponent("nitrogen", 1);
        testSystem.addComponent("methane", 80);
        testSystem.addComponent("ethane", 5);
        testSystem.addComponent("propane", 1);
        testSystem.addComponent("n-butane", 1);
        testSystem.addComponent("i-butane", 1);
        testSystem.addComponent("n-hexane", 0.1);
        testSystem.addComponent("n-heptane", 0.1);
        testSystem.addComponent("n-octane", 0.05);
        testSystem.addComponent("nC10", 0.01);   
        testSystem.addComponent("nC12", 0.001);          
        testSystem.addComponent("water", 0.000001);   
        
        }else if  (sstms==11) {    
        /* TEST SYSTEM 11 : open ended phase envelope Even    */
        testSystem.addComponent("methane", 87);
        testSystem.addComponent("ethane", 4.35);
        testSystem.addComponent("propane", 1 );
        testSystem.addComponent("n-butane", 1);
        testSystem.addComponent("i-butane", 1);  
        testSystem.addTBPfraction("C7", 5.5, 0.25, 0.900);
        
        }else if  (sstms==12) {    
        /* TEST SYSTEM 12   */
        testSystem.addComponent("methane", 100);
        testSystem.addComponent("ethane", 15);
        testSystem.addComponent("n-octane", 5);
        testSystem.addComponent("nC10", 5);
        testSystem.addComponent("nC16", 5);        

        }else if  (sstms==13) {    
        /* TEST SYSTEM 13         */
        testSystem.addComponent("methane", 80);
        testSystem.addComponent("ethane", 10);
        testSystem.addComponent("n-octane", 5);
        testSystem.addComponent("nC10", 1);
        testSystem.addComponent("nC16", 1);  
        
        } 
 
        testSystem.setMixingRule(1);
        
        try {
        
        // from bubble point side
        //testOps.calcPTphaseEnvelope(true, 1.);            
        
        // from dew point side
        testOps.calcPTphaseEnvelope(false, 1.); 
        
        testOps.displayResult();
        
        } catch (Exception e) {
            logger.error("error", e);
            testOps.displayResult();
        }
 
        logger.info("Cricondenbar " + testOps.get("cricondenbar")[0] + " " + testOps.get("cricondenbar")[1]);
        logger.info("Cricondentherm " + testOps.get("cricondentherm")[0] + " " + testOps.get("cricondentherm")[1]);
    }

}