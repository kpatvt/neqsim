/*
 * bubblePointFlash.java
 *
 * Created on 14. oktober 2000, 16:30
 */
package neqsim.thermodynamicOperations.flashOps.saturationOps;

import neqsim.thermo.system.SystemInterface;
import neqsim.thermodynamicOperations.ThermodynamicOperations;

public class SolidComplexTemperatureCalc extends constantDutyTemperatureFlash {

    private static final long serialVersionUID = 1000;

    String comp1, comp2;
    public static double Kcomplex = 0.133736021815520500;
    public static double HrefComplex = 4598.717135;
    public static double TrefComplex = 244.19;

    /**
     * Creates new bubblePointFlash
     */
    public SolidComplexTemperatureCalc() {
    }

    public SolidComplexTemperatureCalc(SystemInterface system) {
        super(system);
    }

    public SolidComplexTemperatureCalc(SystemInterface system, String comp1, String comp2) {
        this(system);
        this.comp1 = comp1;
        this.comp2 = comp2;

      
        if (comp1.equals("MEG") && comp2.equals("water")) {
            Kcomplex = 0.168048;
            HrefComplex = 14450.0;
            TrefComplex = 223.95;
        }
        // TEGwater parameters
        if (comp1.equals("TEG") && comp2.equals("water")) {
            //Kcomplex = 0.157232243643;
            Kcomplex = 0.148047309951772870;
           // HrefComplex = 4863.59239495220;
            HrefComplex = 6629.1366952637;
            TrefComplex = 244.19;
           
        }

        if (comp1.equals("methanol") && comp2.equals("water")) {
            Kcomplex = 0.208059;
            HrefComplex = 8540.0;
            TrefComplex = 171.25;
        }
        

    }

    public void runOld() {
        double sumx = 0.0;
        //system.setHydrateCheck(true);
        ThermodynamicOperations ops = new ThermodynamicOperations(system);
        int iter = 0;
        double funkOld = 0.0, deltaT = 1.0;
        double[] Ksolid = new double[system.getPhase(0).getNumberOfComponents()];
        for (int i = 0; i < system.getPhase(0).getNumberOfComponents(); i++) {
            Ksolid[i] = 1.0;
        }

        do {
            iter++;
            ops.TPflash();
            for (int i = 0; i < system.getPhase(0).getNumberOfComponents(); i++) {
                if (system.getPhases()[5].getComponent(i).getName().equals("water") || system.getPhases()[5].getComponent(i).getName().equals("MEG")) {
                    system.getPhases()[5].getComponent(i).setx(0.5);
                } else {
                    system.getPhases()[5].getComponent(i).setx(1e-20);
                }
            }
            System.out.println("Temperaure  " + system.getTemperature() + " sumx " + sumx);
            sumx = 0.0;
            for (int i = 0; i < system.getPhase(0).getNumberOfComponents(); i++) {
                //system.getPhases()[5].getComponent(i).setx(Ksolid[i] * system.getPhase(0).getComponent(i).getx());
                //               if (system.getPhases()[5].getComponent(i).getx() > 0.000001) {
                Ksolid[i] = system.getPhase(0).getComponent(i).getFugasityCoefficient() / system.getPhases()[5].getComponent(i).fugcoef(system.getPhases()[5]);
                sumx += Ksolid[i] * system.getPhase(0).getComponent(i).getx();
                //               }
            }
            double funk = sumx - 1.0;
            double dfunkdt = (funk - funkOld) / deltaT;
            funkOld = funk;
            double dT = -funk / dfunkdt;
            double oldTemp = system.getTemperature();
            if (iter > 1) {
                system.setTemperature(system.getTemperature() + dT * iter * 1.0 / (5.0 + iter));
            } else {
                system.setTemperature(system.getTemperature() + 0.01);
            }
            deltaT = system.getTemperature() - oldTemp;
            System.out.println("Temperaure  " + system.getTemperature() + " sumx " + sumx);
        } while (Math.abs(sumx - 1.0) > 1e-8);
        System.out.println("sumx " + sumx);

        system.setNumberOfPhases(system.getNumberOfPhases() + 1);
        system.setPhaseIndex(system.getNumberOfPhases() - 1, 5);
        system.setBeta(system.getNumberOfPhases() - 1, 1e-10);
        system.init(3);
    }

    public void run() {
        double sumx = 0.0;
        //system.setHydrateCheck(true);
        ThermodynamicOperations ops = new ThermodynamicOperations(system);
        int iter = 0;
        double funkOld = 0.0, deltaT = 1.0;

        //System.out.println("starting.... ");
        int compNumber_1 = system.getPhase(0).getComponent(comp1).getComponentNumber(), compNumber_2 = system.getPhase(0).getComponent(comp2).getComponentNumber();

        // MEGwater parameters

        double temperature = 0.0, oldTemperature = 0.0, oldError = 0.0, error = 0.0;
        boolean testFalse = true;
        int iteration = 0;
        do {
            iteration++;
            temperature = system.getTemperature();
            ops.TPflash();

            // reading activity coefficients

            double complexActivity =
                    system.getPhaseOfType("aqueous").getActivityCoefficient(compNumber_1) * system.getPhaseOfType("aqueous").getComponent(compNumber_1).getx()
                    * system.getPhaseOfType("aqueous").getActivityCoefficient(compNumber_2) * system.getPhaseOfType("aqueous").getComponent(compNumber_2).getx();

            if (complexActivity < 1e-5) {
                complexActivity = 1e-5;
            }
            //System.out.println("activityMix.... " + complexActivity);

            double rightSide = neqsim.thermo.ThermodynamicConstantsInterface.R * Math.log(complexActivity);
           // System.out.println("right.... " + rightSide);
            double leftSide = neqsim.thermo.ThermodynamicConstantsInterface.R * Math.log(Kcomplex) + HrefComplex * (1.0 / TrefComplex - 1.0 / system.getTemperature());
           // System.out.println("left.... " + leftSide);
            error = rightSide - leftSide;
            double dErrordT = (error - oldError) / (temperature - oldTemperature);
            if (iteration >= 2 && testFalse) {
                deltaT = -error / dErrordT;
            } else {
                deltaT = (rightSide - leftSide);
            }
            if (Math.abs(deltaT) > 10.0) {
                testFalse = !testFalse;
                deltaT = Math.signum(deltaT) * 10.0;
            } else {
                testFalse = true;
            }
            oldTemperature = temperature;
            oldError = error;
           // System.out.println("temperature " + temperature);
            system.setTemperature(system.getTemperature() + deltaT);

        } while (Math.abs(deltaT) > 0.001 && iteration < 50);


    }

    public void printToFile(String name) {
    }

    /**
     * @return the Kcomplex
     */
    public double getKcomplex() {
        return Kcomplex;
    }

    /**
     * @param Kcomplex the Kcomplex to set
     */
    public void setKcomplex(double Kcomplex) {
        SolidComplexTemperatureCalc.Kcomplex = Kcomplex;
    }

    /**
     * @return the HrefComplex
     */
    public double getHrefComplex() {
        return HrefComplex;
    }

    /**
     * @param HrefComplex the HrefComplex to set
     */
    public void setHrefComplex(double HrefComplex) {
        SolidComplexTemperatureCalc.HrefComplex = HrefComplex;
    }

    /**
     * @return the TrefComplex
     */
    public double getTrefComplex() {
        return TrefComplex;
    }

    /**
     * @param TrefComplex the TrefComplex to set
     */
    public void setTrefComplex(double TrefComplex) {
        SolidComplexTemperatureCalc.TrefComplex = TrefComplex;
    }
}