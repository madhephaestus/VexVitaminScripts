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
	double postDiamBottom = vexMotorConfig.postDiamBottom
	double postDiamTop = vexMotorConfig.postDiamTop

	CSG motor = new Cube(sizeX, sizeY, sizeZ).toCSG();
	
	CSG post = new Cylinder(postDiamTop/2, postDiamBottom/2, 14.8, 80).toCSG();
	post = post.union(post.movey(13));
	post = post.movez(motor.getMaxZ());
	post = post.hull();
	post = post.movey(motor.getMaxY() - post.getMaxY()).movey(-postInset);
	
        LengthParameter axleLength = new LengthParameter("Axle Length", 50.8, [10, 152.4]);
        CSG axle = new Cylinder(7.2/2, 7.2/2, axleLength.getMM(), 80).toCSG();
        axle = axle.movey(motor.getMinY() + axle.getTotalY()/2 + axleInset).movez(motor.getMaxZ());

        motor = motor.union(post);
        motor = motor.union(axle);

        return motor
                .setParameter(size)
                .setParameter(axleLength)
                .setRegenerate({getMotor()})
}

return getMotor()
