package neqsim.processsimulation.processequipment.separator;

import java.util.ArrayList;
import java.util.UUID;
import neqsim.processsimulation.mechanicaldesign.separator.GasScrubberMechanicalDesign;
import neqsim.processsimulation.processequipment.separator.sectiontype.SeparatorSection;
import neqsim.processsimulation.processequipment.stream.Stream;
import neqsim.processsimulation.processequipment.stream.StreamInterface;
import neqsim.thermo.system.SystemInterface;

/**
 * <p>
 * NeqGasScrubber class.
 * </p>
 *
 * @author Even Solbraa
 * @version $Id: $Id
 */
public class NeqGasScrubber extends Separator {
  private static final long serialVersionUID = 1000;

  SystemInterface thermoSystem;

  SystemInterface gasSystem;
  SystemInterface waterSystem;
  SystemInterface liquidSystem;
  SystemInterface thermoSystemCloned;

  StreamInterface inletStream;
  StreamInterface gasOutStream;
  StreamInterface liquidOutStream;

  ArrayList<SeparatorSection> scrubberSection = null;

  /**
   * Constructor for NeqGasScrubber.
   *
   * @param name name of unit operation
   */
  public NeqGasScrubber(String name) {
    super(name);
    this.setOrientation("vertical");
  }

  /**
   * <p>
   * Constructor for NeqGasScrubber.
   * </p>
   *
   * @param name        a {@link java.lang.String} object
   * @param inletStream a
   *                    {@link neqsim.processsimulation.processequipment.stream.StreamInterface}
   *                    object
   */
  public NeqGasScrubber(String name, StreamInterface inletStream) {
    super(name, inletStream);
    this.setOrientation("vertical");
  }

  /** {@inheritDoc} */
  @Override
  public GasScrubberMechanicalDesign getMechanicalDesign() {
    return new GasScrubberMechanicalDesign(this);
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * Setter for the field <code>inletStream</code>.
   * </p>
   */
  @Override
  public void setInletStream(StreamInterface inletStream) {
    this.inletStream = inletStream;

    thermoSystem = inletStream.getThermoSystem().clone();
    gasSystem = thermoSystem.phaseToSystem(thermoSystem.getPhases()[0]);
    gasOutStream = new Stream("gasOutStream", gasSystem);

    thermoSystem = inletStream.getThermoSystem().clone();
    liquidSystem = thermoSystem.phaseToSystem(thermoSystem.getPhases()[1]);
    liquidOutStream = new Stream("liquidOutStream", liquidSystem);
  }

  /**
   * <p>
   * addScrubberSection.
   * </p>
   *
   * @param type a {@link java.lang.String} object
   */
  public void addScrubberSection(String type) {
    scrubberSection.add(new SeparatorSection("section" + scrubberSection.size() + 1, type, this));
  }

  /** {@inheritDoc} */
  @Override
  public StreamInterface getLiquidOutStream() {
    return liquidOutStream;
  }

  /** {@inheritDoc} */
  @Override
  public StreamInterface getGasOutStream() {
    return gasOutStream;
  }

  /** {@inheritDoc} */
  @Override
  public StreamInterface getGas() {
    return getGasOutStream();
  }

  /** {@inheritDoc} */
  @Override
  public StreamInterface getLiquid() {
    return getLiquidOutStream();
  }

  /** {@inheritDoc} */
  @Override
  public void run(UUID id) {
    thermoSystem = inletStream.getThermoSystem().clone();
    gasSystem = thermoSystem.phaseToSystem(thermoSystem.getPhases()[0]);
    gasSystem.setNumberOfPhases(1);
    gasOutStream.setThermoSystem(gasSystem);

    thermoSystem = inletStream.getThermoSystem().clone();
    liquidSystem = thermoSystem.phaseToSystem(thermoSystem.getPhases()[1]);
    liquidSystem.setNumberOfPhases(1);
    liquidOutStream.setThermoSystem(liquidSystem);
    setCalculationIdentifier(id);
  }

  /** {@inheritDoc} */
  @Override
  public void displayResult() {
  }
}