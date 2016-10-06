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

	CSG round = new Cylinder(vexGearConfig.diameter/2, vexGearConfig.diameter/2, vexGearConfig.height, (int)30).toCSG()

	CSG tooth = new Cylinder(10, 5, 10, (int)3).toCSG()
	
	return tooth
		.setParameter(size)
		.setRegenerate({getGear()})
}
return getGear()