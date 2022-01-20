/**
 * 
 */
package neqsim.processSimulation.processEquipment.stream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import neqsim.processSimulation.processSystem.ProcessSystem;
import neqsim.standards.gasQuality.Standard_ISO6976;
import neqsim.thermo.system.SystemSrkEos;
import neqsim.thermo.system.SystemSrkEos;
import neqsim.processSimulation.processEquipment.stream.Stream;
import neqsim.processSimulation.processSystem.ProcessSystem;
/**
 * @author ESOL
 *
 */
class StreamTest {
		static neqsim.thermo.system.SystemInterface testSystem = null;
	 	double pressure_inlet = 85.0;
	    double temperature_inlet = 35.0;
	    double gasFlowRate = 5.0;
	    ProcessSystem processOps = null;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		  	testSystem = new SystemSrkEos(298.0, 10.0);
	        testSystem.addComponent("methane", 100.0);
	        processOps = new ProcessSystem();
	        Stream inletStream = new Stream(testSystem);
	        inletStream.setName("inlet stream");
	        inletStream.setPressure(pressure_inlet, "bara");
	        inletStream.setTemperature(temperature_inlet, "C");
	        inletStream.setFlowRate(gasFlowRate, "MSm3/day");
	       
	        processOps.add(inletStream);
	        processOps.run();
	}
	
	@Test
	public void testLCV() {
		processOps.run();
		((Stream)processOps.getUnit("inlet stream")).LCV();
        assertEquals(3.58980282482032E7, ((Stream)processOps.getUnit("inlet stream")).LCV(), 1.0);
        // 18978 J/Sm3
    }

}