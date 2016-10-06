import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getStandoff(){
	String type= "vexStandoff"
	if(args==null)
		args=["0.5 inch"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
                    
	HashMap<String, Object> vexStandoffConfig = Vitamins.getConfiguration(type, size.getStrValue())
	double length = vexStandoffConfig.length
	double width = 6.35
  double diameter = 3.75
  
  CSG standoff = new Cylinder(width/2, width/2, length, (int)6).toCSG()
  CSG hole = new Cylinder(diameter/2, diameter/2, length, (int)30).toCSG()
  standoff = standoff.difference(hole)

  return standoff
     .setParameter(size)
		.setRegenerate({getStandoff()})
}
return getStandoff()