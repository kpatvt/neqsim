/*
 * PhaseSrkEos.java
 *
 * Created on 3. juni 2000, 14:38
 */
package neqsim.thermo.phase;

import neqsim.thermo.component.ComponentElectrolyteCPAstatoil;

/**
 *
 * @author  Even Solbraa
 * @version
 */
public class PhaseElectrolyteCPAstatoil extends PhaseElectrolyteCPA implements PhaseCPAInterface {

    private static final long serialVersionUID = 1000;

    /** Creates new PhaseSrkEos */
    public PhaseElectrolyteCPAstatoil() {
        super();
    }

    public void addcomponent(String componentName, double moles, double molesInPhase, int compNumber) {
        super.addcomponent(componentName, moles, molesInPhase, compNumber);
        componentArray[compNumber] = new ComponentElectrolyteCPAstatoil(componentName, moles, molesInPhase, compNumber);
    }

    public Object clone() {
        PhaseElectrolyteCPAstatoil clonedPhase = null;
        try {
            clonedPhase = (PhaseElectrolyteCPAstatoil) super.clone();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return clonedPhase;
    }


    public double calc_g() {
        double x = 1.9 / 4.0 * getB() / getTotalVolume();
        double g = 1.0 / (1.0 - x);
       // System.out.println("g " + g);
        return g;
    }

    public double calc_lngV() {
        double x = 1.9 / 4.0 * getB() / getTotalVolume();
        double gv = (x / getTotalVolume()) / (1.0 - x);
        return -gv;
    }



    public double calc_lngVV() {
        double x = 1.9 / 4.0 * getB() / getTotalVolume();
        double xV = -1.9 / 4.0 * getB() / Math.pow(getTotalVolume(), 2.0);
        double u = 1.0 - x;

        double val = -x / (Math.pow(getTotalVolume(), 2.0) * u) + xV / (getTotalVolume() * u) - x / (getTotalVolume() * u * u) * (-1.0) * xV;
        return -val;
//
//        double gvv =0.225625/Math.pow(1.0-0.475*getB()/getTotalVolume(),2.0)*Math.pow(getB(),2.0)/(Math.pow(getTotalVolume(),4.0))+0.95/(1.0-0.475*getB()/getTotalVolume())*getB()/(Math.pow(getTotalVolume(),3.0));
//         System.out.println("val2 " + gvv);
//        return gvv;
    }

    public double calc_lngVVV() {
        double gvv = -0.21434375 / Math.pow(1.0 - 0.475 * getB() / getTotalVolume(), 3.0) * Math.pow(getB(), 3.0) / (Math.pow(getTotalVolume(), 6.0)) - 0.135375E1 / Math.pow(1.0 - 0.475 * getB() / getTotalVolume(), 2.0) * Math.pow(getB(), 2.0) / (Math.pow(getTotalVolume(), 5.0)) - 0.285E1 / (1.0 - 0.475 * getB() / getTotalVolume()) * getB() / (Math.pow(getTotalVolume(), 4.0));
        return gvv;
    }

}