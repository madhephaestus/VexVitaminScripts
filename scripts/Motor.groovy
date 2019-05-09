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
    axle = axle.movey(motor.getMinY() + axle.getTotalY()/2 + axleInset).movez(-motor.getMaxZ());
    def grid =(1.0/2.0)*25.4
    def bolt = Vitamins.get("capScrew","6#32")
    				.toZMin()
    def bolt1 = bolt.movey(grid+motor.getMinY() + axle.getTotalY()/2 + axleInset)
    def bolt2 = bolt.movey(grid*2+motor.getMinY() + axle.getTotalY()/2 + axleInset)
    motor = motor.union(post)
    			.toZMax()
    motor = motor.union([axle,bolt1,bolt2]);
    motor = motor.movey(-axle.getCenterY())
    			.rotz(180)

    return motor
	    .setParameter(size)
	    .setParameter(axleLength)
	    .setRegenerate({getMotor()})
}

return getMotor()
