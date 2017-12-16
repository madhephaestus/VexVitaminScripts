import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getMotor() {
	String type = "vexMotor"
	if (args==null) {
		args=["393"]
	}
	
	StringParameter size = new StringParameter(type + " Default", args.get(0), Vitamins.listVitaminSizes(type))
										
	Map<String, Object> vexMotorConfig = Vitamins.getConfiguration(type, size.getStrValue())

	double sizeX = vexMotorConfig.sizeX;
	double sizeY = vexMotorConfig.sizeY;
	double sizeZ = vexMotorConfig.sizeZ;
	double axleInset = vexMotorConfig.axleInset;
	double postInset = vexMotorConfig.postInset;

	CSG motor = new Cube(sizeX, sizeY, sizeZ).toCSG();
	
	return motor
		.setParameter(size)
		.setRegenerate({getMotor()})
}

return getMotor()
