import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getGear() {
	String type = "vexGear"
	if (args==null) {
		args=["84T"]
	}
	StringParameter size = new StringParameter(type + " Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
										
	HashMap<String,Object> vexGearConfig = Vitamins.getConfiguration(type, size.getStrValue())

	double diameter = vexGearConfig.diameter
	double height = vexGearConfig.height
	double numTeeth = vexGearConfig.numTeeth
	double toothLen = vexGearConfig.toothLen

	CSG round = new Cylinder(diameter/2, diameter/2, height, (int)84).toCSG()
	
	if (!(size.getStrValue() == "HS12T" || size.getStrValue() == "12T")) {
		CSG roundDepthBottom = new Cylinder(3*diameter/7, 3*diameter/7, height/4, (int)84).toCSG()
		CSG roundDepthTop = new Cylinder(3*diameter/7, 3*diameter/7, height/4, (int)84).toCSG().movez(height - height/4)
		
		round = round.difference(roundDepthBottom).difference(roundDepthTop)
	}

	CSG tooth = new Cube(toothLen, toothLen, height).toCSG().movez(height/2)
	tooth = tooth.difference(new Cube(7, 8, height).toCSG().rotz(-20).movex(-5).movez(height/2)).difference(new Cube(7, 8, height).toCSG().rotz(20).movex(5).movez(height/2)).movey(-diameter/2)
	round = round.union(tooth)

	CSG axleHole = new Cube(3.175, 3.175, height).toCSG().movez(height/2)
	CSG axleCylinder = new Cylinder(4, 4, height, (int)84).toCSG()
	round = round.union(axleCylinder).difference(axleHole)
	
	for (int i = 0; i < numTeeth; i++) {
		round = round.union(tooth.rotz(360/numTeeth*i))
	}
	//round = round.movey(diameter)
	round = round.setColor(javafx.scene.paint.Color.GREEN)
	
	return round
		.setParameter(size)
		.setRegenerate({getGear()})
}
return getGear()
