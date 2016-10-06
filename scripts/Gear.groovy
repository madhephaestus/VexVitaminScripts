import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getGear(){
	String type= "vexGear"
	if (args==null) {
		args=["84T"]
	}
	StringParameter size = new StringParameter(type + " Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexGearConfig = Vitamins.getConfiguration( type,size.getStrValue())

	double diameter = vexGearConfig.diameter
	double height = vexGearConfig.height
	
	CSG round = new Cylinder(vexGearConfig.diameter/2, vexGearConfig.diameter/2, vexGearConfig.height, (int)84).toCSG()

	CSG tooth = new Cube(10, 10, vexGearConfig.height).toCSG().movez(height/2)
	tooth = tooth.difference(new Cube(7, 15, 10).toCSG().rotz(-20).movex(-5).movez(height/2)).difference(new Cube(7, 15, 10).toCSG().rotz(20).movex(5).movez(height/2)).movey(-diameter/2-4)
	round = round.union(tooth)
	
	return round
		.setParameter(size)
		.setRegenerate({getGear()})
}
return getGear()