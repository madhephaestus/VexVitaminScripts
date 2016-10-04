import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getStandoff(){
	String type= "vexStandoff"
	if(args==null)
		args=["0.5 inch"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
                    
	double length = Vitamins.getConfiguration( type,length.getStrValue())
	double width = 6.35
  double diameter = 4.15
  
  CSG standoff = new Cylinder(width, width, length, (int)6).toCSG()
  CSG hole = new Cylinder(diameter, diameter, length, (int)30).toCSG()
  standoff = standoff.difference(hole)

  return standoff
     .setParameter(size)
		.setRegenerate({getStandoff()})
}
return getStandoff()
