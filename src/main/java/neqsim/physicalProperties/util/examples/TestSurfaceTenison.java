/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neqsim.physicalProperties.util.examples;

import neqsim.thermo.system.SystemInterface;
import neqsim.thermo.system.SystemSrkEos;
import neqsim.thermodynamicOperations.ThermodynamicOperations;

/**
 *
 * @author esol //
 */
public class TestSurfaceTenison {

    private static final long serialVersionUID = 1000;

    public static void main(String args[]) {
        SystemInterface testSystem = new SystemSrkEos(273.15 + 28.66, 12.2);
        // SystemInterface testSystem = new SystemPrEos(273.15 + 10.0, 50.0);
        //  testSystem.addComponent("CO2", 2.826);
        // testSystem.addComponent("nitrogen", 0.546);

        //SystemInterface testSystem = new SystemPrEos(273.15 + 30, 1);
        //SystemInterface testSystem = new SystemSrkCPAs(273.65, 30.3);
        //testSystem.getCharacterization().setTBPModel("PedersenSRK");//(RiaziDaubert  PedersenPR  PedersenSRK
        //testSystem.getCharacterization().setLumpingModel("no"); //"abLumping";
        //testSystem.getCharacterization().getLumpingModel().setNumberOfLumpedComponents(3);
        testSystem.getCharacterization().setTBPModel("PedersenSRK");
        testSystem.addComponent("nitrogen", 0.037);
        testSystem.addComponent("CO2", 0.475);
        testSystem.addComponent("methane", 8.135);
        testSystem.addComponent("ethane", 3.477);
        testSystem.addComponent("propane", 1.64);
        testSystem.addComponent("i-butane", 1.22);
        testSystem.addComponent("n-butane", 6.857);
        testSystem.addComponent("i-pentane", 6.492);
        testSystem.addComponent("n-pentane", 7.515);
        testSystem.addTBPfraction("C6", 9.123, 84.9 / 1000.0, 0.6675);
        testSystem.addTBPfraction("C7", 13.162, 90.0 / 1000.0, 0.7509);
        testSystem.addTBPfraction("C8", 12.641, 101.8 / 1000.0, 0.7782);
        testSystem.addTBPfraction("C9", 7.126, 115.3 / 1000.0, 0.7928);
        testSystem.addTBPfraction("C10", 22.098, 230.3 / 1000.0, 0.8454);
        testSystem.setHeavyTBPfractionAsPlusFraction();
        testSystem.getCharacterization().characterisePlusFraction();

        testSystem.createDatabase(true);
        testSystem.setMixingRule(2);

        //  testSystem.setMultiPhaseCheck(true);
        testSystem.useVolumeCorrection(true);
        // testSystem.getInterphaseProperties().setInterfacialTensionModel(1); // GT == 1  Parac==0
        testSystem.init(0);

        ThermodynamicOperations testOps = new ThermodynamicOperations(testSystem);
        try {
            //  testOps.bubblePointPressureFlash(false);
            // testOps.bubblePointTemperatureFlash();
            testOps.TPflash();
            testSystem.display();
            testSystem.getInterphaseProperties().setInterfacialTensionModel("gas", "oil", "Linear Gradient Theory");
            testOps.TPflash();
            testSystem.display();
            // testOps.dewPointMach("n-pentane", "dewPointTemperature", testSystem.getTemperature());
            //  testOps.dewPointTemperatureFlash();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //testSystem.getPhase(1).getComponent(1).getChemicalPotentialdNTV(0, testSystem.getPhase(0));
        //testSystem.getInterphaseProperties().setInterfacialTensionModel(2); // GT == 1  Parac==0
        // testSystem.initPhysicalProperties();
        //   System.out.println("influence n-pentane " + ((GTSurfaceTension) testSystem.getInterphaseProperties().getSurfaceTensionModel(0)).getInfluenceParameter((2.74 * 1e-3), 1));
        //    System.out.println("z " + ((GTSurfaceTension) testSystem.getInterphaseProperties().getSurfaceTensionModel(0)).getz()[40]);
        //    testSystem.getInterphaseProperties().getInterfacialTensionModel(i)
        //testSystem.getInterphaseProperties().getSurfaceTensionModel(0).getMolarDensity(0); //density profile for comp 0
        // testSystem.getInterphaseProperties().getSurfaceTensionModel(0).getz(); //density profile for comp 0
        // testSystem.getInterphaseProperties().getSurfaceTensionModel(0).getPressure(); //pressure profile for comp 0
        // testSystem.getInterphaseProperties().getSurfaceTensionModel(0).getMolarDensityTotal(); //total density profile for comp 0
        testSystem.display();
        //System.out.println("tension gas-water " + testSystem.getInterphaseProperties().getSurfaceTension(0, 1));

    }
}