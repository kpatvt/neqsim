/*
 * AtractiveTermSrk.java
 *
 * Created on 13. mai 2001, 21:59
 */
package neqsim.thermo.component.atractiveEosTerm;

import neqsim.thermo.component.ComponentEosInterface;

/**
 *
 * @author esol
 * @version
 */
public class AtractiveTermMatCop extends AtractiveTermSrk {

    private static final long serialVersionUID = 1000;


    double orgpar = 0.0;

    /**
     * Creates new AtractiveTermSrk
     */
    public AtractiveTermMatCop(ComponentEosInterface component) {
        super(component);
        m = (0.48 + 1.574 * component.getAcentricFactor() - 0.175 * component.getAcentricFactor() * component.getAcentricFactor());
    }

    /**
     * Creates new AtractiveTermSrk
     */
    public AtractiveTermMatCop(ComponentEosInterface component, double[] params) {
        this(component);
        System.arraycopy(params, 0, this.parameters, 0, params.length);
        orgpar = parameters[0];
        if (Math.abs(parameters[0]) < 1e-12) {
            parameters[0] = m;
        }
    }

    public Object clone() {
        AtractiveTermMatCop atractiveTerm = null;
        try {
            atractiveTerm = (AtractiveTermMatCop) super.clone();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return atractiveTerm;
    }

    public void init() {
        super.init();
        parameters[0] = m;
    }

    public double alpha(double temperature) {
        double Tr = temperature / component.getTC();
        return Math.pow(1.0 + parameters[0] * (1.0 - Math.sqrt(Tr)) + parameters[1] * Math.pow(1.0 - Math.sqrt(Tr), 2.0) + parameters[2] * Math.pow(1.0 - Math.sqrt(Tr), 3.0), 2.0);



    }

    public double aT(double temperature) {
        if (temperature / component.getTC() > 10000.0) {
            return super.aT(temperature);

        } else {
            return component.geta() * alpha(temperature);
        }
    }

    public double diffalphaT(double temperature) {
        double Tr = temperature / component.getTC();
        double TC = component.getTC();
        return 2.0 * (1.0 + parameters[0] * (1.0 - Math.sqrt(Tr)) + parameters[1] * Math.pow(1.0 - Math.sqrt(Tr), 2.0) + parameters[2] * Math.pow(1.0 - Math.sqrt(Tr), 3.0)) * (-parameters[0] / Math.sqrt(Tr) / TC / 2.0
                - parameters[1] * (1.0 - Math.sqrt(Tr)) / Math.sqrt(Tr) / TC - 3.0 / 2.0 * parameters[2] * Math.pow(1.0 - Math.sqrt(Tr), 2.0) / Math.sqrt(Tr) / TC);



    }

    public double diffdiffalphaT(double temperature) {
        double Tr = temperature / component.getTC();
        double TC = component.getTC();
        return 2.0 * Math.pow(-parameters[0] / Math.sqrt(Tr) / TC / 2.0 - parameters[1] * (1.0 - Math.sqrt(Tr)) / Math.sqrt(Tr) / TC - 3.0 / 2.0 * parameters[2] * Math.pow(1.0 - Math.sqrt(Tr), 2.0) / Math.sqrt(Tr) / TC, 2.0) + 2.0 * (1.0 + parameters[0] * (1.0 - Math.sqrt(Tr)) + parameters[1] * Math.pow(1.0 - Math.sqrt(Tr), 2.0) + parameters[2] * Math.pow(1.0 - Math.sqrt(Tr), 3.0)) * (parameters[0] / Math.sqrt(Tr * Tr * Tr) / (TC * TC) / 4.0 + parameters[1] / temperature / TC / 2.0 + parameters[1] * (1.0 - Math.sqrt(Tr)) / Math.sqrt(Tr * Tr * Tr) / (TC * TC) / 2.0
                + 3.0 / 2.0 * parameters[2] * (1.0 - Math.sqrt(Tr)) / temperature / TC + 3.0 / 4.0 * parameters[2] * Math.pow(
                1.0 - Math.sqrt(Tr), 2.0) / Math.sqrt(Tr * Tr * Tr) / (TC * TC));
    }

    public double diffaT(double temperature) {
        if (temperature / component.getTC() > 10000.0) {
            return super.diffaT(temperature);
        } else {
            return component.geta() * diffalphaT(temperature);
        }
    }

    public double diffdiffaT(double temperature) {
        if (temperature / component.getTC() > 10000.0) {
            return super.diffdiffaT(temperature);
        } else {
            return component.geta() * diffdiffalphaT(temperature);
        }
    }
}